package arcturus.parser;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arcturus.ast.AssignStatement;
import arcturus.ast.BlockStatement;
import arcturus.ast.BooleanLiteral;
import arcturus.ast.BreakStatement;
import arcturus.ast.CallExpression;
import arcturus.ast.ContinueStatement;
import arcturus.ast.DecimalLiteral;
import arcturus.ast.DoStatement;
import arcturus.ast.ExpressionStatement;
import arcturus.ast.ForStatement;
import arcturus.ast.FunctionLiteral;
import arcturus.ast.Identifier;
import arcturus.ast.IfStatement;
import arcturus.ast.InfixExpression;
import arcturus.ast.IntegerLiteral;
import arcturus.ast.LetStatement;
import arcturus.ast.PrefixExpression;
import arcturus.ast.Program;
import arcturus.ast.ReturnStatement;
import arcturus.ast.StringLiteral;
import arcturus.ast.WhileStatement;
import arcturus.ast.interfaces.Expression;
import arcturus.ast.interfaces.Statement;
import arcturus.lexer.Lexer;
import arcturus.parser.errors.NoPrefixParseError;
import arcturus.parser.errors.NumberFormatError;
import arcturus.parser.errors.ParseError;
import arcturus.parser.errors.TokenError;
import arcturus.parser.interfaces.InfixParseFunction;
import arcturus.parser.interfaces.PrefixParseFunction;
import arcturus.token.Token;
import arcturus.token.Token.Type;

public class Parser {
    private Lexer lexer;
    private Token currentToken;
    private Token peekToken;
    private List<ParseError> errors;
    private Map<Type, PrefixParseFunction> prefixParseMap;
    private Map<Type, InfixParseFunction> infixParseMap;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        nextToken();
        nextToken();
        errors = new ArrayList<>();
        prefixParseMap = new HashMap<>();
        registerPrefix(this::parseIdentifier, Type.IDENTIFIER);
        registerPrefix(this::parseIntegerLiteral, Type.INT);
        registerPrefix(this::parseDecimalLiteral, Type.DECIMAL);
        registerPrefix(this::parseBooleanLiteral, Type.TRUE, Type.FALSE);
        registerPrefix(this::parsePrefixExpression, Type.BANG, Type.MINUS, Type.PLUS);
        registerPrefix(this::parseGroupedExpression, Type.LPAREN);
        registerPrefix(this::parseFunctionLiteral, Type.FUNCTION);
        registerPrefix(this::parseStringLiteral, Type.STRING);
        registerPrefix(this::parseArrayLiteral, Type.LBRACKET);
        infixParseMap = new HashMap<>();
        registerInfix(this::parseInfixExpression, Type.PLUS, Type.MINUS, Type.ASTERISK, Type.SLASH); // arithemic
        registerInfix(this::parseInfixExpression, Type.EQ, Type.NE, Type.LT, Type.GT, Type.LE, Type.GE); // relation
        registerInfix(this::parseInfixExpression, Type.ADD, Type.OR); // logical
        registerInfix(this::parseCallExpression, Type.LPAREN);
        // registerInfix(this::parseIndexExpression, Type.LBRACKET);
    }

    private void registerPrefix(PrefixParseFunction function, Type... types) {
        for (var type : types) {
            prefixParseMap.put(type, function);
        }
    }

    private void registerInfix(InfixParseFunction function, Type... types) {
        for (var type : types) {
            infixParseMap.put(type, function);
        }
    }

    public List<ParseError> getErrors() {
        return errors;
    }

    private void raiseError(ParseError error) {
        errors.add(error);
    }

    public void nextToken() {
        currentToken = peekToken;
        peekToken = lexer.nextToken();
    }

    private boolean currentTokenIs(Type type) {
        return currentToken.getType() == type;
    }

    private boolean peekTokenIs(Type type) {
        return peekToken.getType() == type;
    }

    private boolean expectCurrent(Type type) {
        if (currentToken.getType() == type) {
            nextToken();
            return true;
        }
        raiseError(new TokenError(type, currentToken, lexer.getLine(), lexer.getCol()));
        return false;
    }

    private boolean expectPeek(Type type) {
        if (peekToken.getType() == type) {
            nextToken();
            return true;
        }
        raiseError(new TokenError(type, peekToken, lexer.getLine(), lexer.getCol()));
        return false;
    }

    private Precedence peekPrecedence() {
        return Precedence.get(peekToken.getType());
    }

    private Precedence currentPrecedence() {
        return Precedence.get(currentToken.getType());
    }

    public Program parse() {
        var program = new Program();
        while (currentToken.getType() != Type.EOF) {
            var statement = parseStatement();
            if (statement != null)
                program.addStatement(statement);
            nextToken();
        }
        return program;
    }

    private Statement parseStatement() {
        switch (currentToken.getType()) {
        case LET:
            return parseLetStatement();
        case RETURN:
            return parseReturnStatement();
        case IDENTIFIER:
            return parseIdentifierStatement();
        case IF:
            return parseIfStatement();
        case FOR:
            return parseForStatement();
        case DO:
            return parseDoStatement();
        case WHILE:
            return parseWhileStatement();
        case BREAK:
            return parseBreakStatement();
        case CONTINUE:
            return parseContinueStatement();
        default:
            return parseExpressionStatement();
        }
    }

    private LetStatement parseLetStatement() {
        var statement = new LetStatement(currentToken);
        if (!expectPeek(Type.IDENTIFIER)) {
            return null;
        }
        statement.setName(new Identifier(currentToken, currentToken.getLiteral()));
        if (!expectPeek(Type.ASSIGN)) {
            return null;
        }
        nextToken(); // skip assign sign
        statement.setExpression(parseExpression(Precedence.LOWEST));
        if (!expectPeek(Type.SEMICOLON)) {
            return null;
        }
        return statement;
    }

    private ReturnStatement parseReturnStatement() {
        var returnStatement = new ReturnStatement(currentToken);
        nextToken(); // skip the return keyword
        returnStatement.setReturnValue(parseExpression(Precedence.LOWEST));
        if (!expectPeek(Type.SEMICOLON)) {
            return null;
        }
        return returnStatement;
    }

    private Expression parseExpression(Precedence precedence) {
        var prefix = prefixParseMap.get(currentToken.getType());
        if (prefix == null) {
            raiseError(new NoPrefixParseError(currentToken.getType(), currentToken, lexer.getLine(), lexer.getCol()));
            return null;
        }
        var leftExp = prefix.parse();
        while (!peekTokenIs(Type.SEMICOLON) && precedence.lessThan(peekPrecedence())) {
            var infix = infixParseMap.get(peekToken.getType());
            if (infix == null) {
                return leftExp;
            }
            nextToken();
            leftExp = infix.parse(leftExp);
        }
        return leftExp;
    }

    private Expression parseIdentifier() {
        return new Identifier(currentToken, currentToken.getLiteral());
    }

    private Expression parseIntegerLiteral() {
        var literal = currentToken.getLiteral();
        try {
            var value = new BigInteger(literal);
            return new IntegerLiteral(currentToken, value);
        } catch (NumberFormatException e) {
            raiseError(new NumberFormatError(literal, "integer", lexer.getLine(), lexer.getCol()));
            return null;
        }
    }

    private Expression parseDecimalLiteral() {
        var literal = currentToken.getLiteral();
        try {
            var value = new BigDecimal(literal);
            return new DecimalLiteral(currentToken, value);
        } catch (NumberFormatException e) {
            raiseError(new NumberFormatError(literal, "decimal", lexer.getLine(), lexer.getCol()));
            return null;
        }
    }

    private Expression parseBooleanLiteral() {
        return new BooleanLiteral(currentToken, currentTokenIs(Type.TRUE));
    }

    private Expression parsePrefixExpression() {
        var expression = new PrefixExpression(currentToken, currentToken.getLiteral());
        nextToken();
        expression.setRight(parseExpression(Precedence.PREFIX));
        return expression;
    }

    private Expression parseInfixExpression(Expression left) {
        var expression = new InfixExpression(currentToken, currentToken.getLiteral());
        expression.setLeft(left);
        var precedence = currentPrecedence();
        nextToken();
        expression.setRight(parseExpression(precedence));
        return expression;
    }

    private Expression parseGroupedExpression() {
        nextToken(); // skip left paren
        var expression = parseExpression(Precedence.LOWEST);
        if (!expectPeek(Type.RPAREN)) {
            return null;
        }
        return expression;
    }

    private Expression parseFunctionLiteral() {
        var funcLiteral = new FunctionLiteral(currentToken);
        if (!expectPeek(Type.LPAREN)) {
            return null;
        }
        funcLiteral.setParameters(parseFunctionParameters());
        if (!expectPeek(Type.LBRACE)) {
            return null;
        }
        funcLiteral.setBody(parseBlockStatement());
        return funcLiteral;
    }

    private List<Identifier> parseFunctionParameters() {
        List<Identifier> identifiers = new ArrayList<>();
        if (peekTokenIs(Type.RPAREN)) { // no parameters
            nextToken();
            return identifiers;
        }
        nextToken();
        if (!currentTokenIs(Type.IDENTIFIER)) {
            raiseError(new TokenError(Type.IDENTIFIER, currentToken, lexer.getLine(), lexer.getCol()));
            return null;
        }
        identifiers.add(new Identifier(currentToken, currentToken.getLiteral()));
        while (peekTokenIs(Type.COMMA)) {
            nextToken(); // current token is comma
            nextToken(); // skip comma
            if (!currentTokenIs(Type.IDENTIFIER)) {
                raiseError(new TokenError(Type.IDENTIFIER, currentToken, lexer.getLine(), lexer.getCol()));
                return null;
            }
            identifiers.add(new Identifier(currentToken, currentToken.getLiteral()));
        }
        if (!expectPeek(Type.RPAREN))
            return null;
        return identifiers;
    }

    private BlockStatement parseBlockStatement() {
        var block = new BlockStatement(currentToken);
        nextToken(); // skip {
        while (!currentTokenIs(Type.RBRACE) && !currentTokenIs(Type.EOF)) {
            var statement = parseStatement();
            if (statement != null) {
                block.addStatement(statement);
            }
            nextToken();
        }
        return block;
    }

    private Expression parseStringLiteral() {
        return new StringLiteral(currentToken, currentToken.getLiteral());
    }

    private Expression parseArrayLiteral() {
        // todo
        return null;
    }

    private Expression parseCallExpression(Expression function) {
        return new CallExpression(currentToken, function, parseExpressionList(Type.RPAREN));
    }

    private List<Expression> parseExpressionList(Type end) {
        var results = new ArrayList<Expression>();
        if (peekTokenIs(end)) {
            nextToken();
            return results;
        }
        nextToken(); // skip start token
        results.add(parseExpression(Precedence.LOWEST));
        while (peekTokenIs(Type.COMMA)) {
            nextToken();
            nextToken();
            results.add(parseExpression(Precedence.LOWEST));
        }
        if (!expectPeek(end))
            return null;
        return results;
    }

    private Statement parseIdentifierStatement() {
        switch (peekToken.getType()) {
        case ASSIGN:
            return parseAssignStatement(); // todo -- array element assignment
        default:
            return parseExpressionStatement();
        }
    }

    private ExpressionStatement parseExpressionStatement() {
        var stmt = new ExpressionStatement(currentToken);
        stmt.setExpression(parseExpression(Precedence.LOWEST));
        while (peekTokenIs(Type.SEMICOLON))
            nextToken();
        return stmt;
    }

    private AssignStatement parseAssignStatement() {
        var assign = new AssignStatement(currentToken, new Identifier(currentToken, currentToken.getLiteral()));
        if (!expectPeek(Type.ASSIGN))
            return null; // this should not happen
        nextToken(); // skip =
        assign.setValue(parseExpression(Precedence.LOWEST));
        if (!expectPeek(Type.SEMICOLON))
            return null;
        return assign;
    }

    private IfStatement parseIfStatement() {
        var ifStatement = new IfStatement(currentToken);
        if (!expectPeek(Type.LPAREN))
            return null;
        nextToken(); // skip (
        ifStatement.setCondition(parseExpression(Precedence.LOWEST));
        if (!expectPeek(Type.RPAREN))
            return null;
        nextToken(); // skip )
        if (currentTokenIs(Type.LBRACE))
            ifStatement.setThenStatement(parseBlockStatement());
        else
            ifStatement.setThenStatement(parseStatement());
        if (peekTokenIs(Type.ELSE)) {
            nextToken();
            nextToken(); // skip else -- current token is lbrace
            if (currentTokenIs(Type.LBRACE))
                ifStatement.setElseStatement(parseBlockStatement());
            else
                ifStatement.setElseStatement(parseStatement());
        }
        return ifStatement;
    }

    private ForStatement parseForStatement() {
        return null;
    }

    private DoStatement parseDoStatement() {
        var doStatement = new DoStatement(currentToken);
        nextToken(); // skip do keyword
        doStatement.setBody(parseBlockStatement());
        if (!expectPeek(Type.WHILE))
            return null;
        if (!expectPeek(Type.LPAREN))
            return null;
        nextToken(); // skip (
        doStatement.setCondition(parseExpression(Precedence.LOWEST));
        if (!expectPeek(Type.RPAREN))
            return null;
        if (!expectPeek(Type.SEMICOLON))
            return null;
        return doStatement;
    }

    private WhileStatement parseWhileStatement() {
        var whileStatement = new WhileStatement(currentToken);
        if (!expectPeek(Type.LPAREN))
            return null;
        nextToken(); // skip (
        whileStatement.setCondition(parseExpression(Precedence.LOWEST));
        if (!expectPeek(Type.RPAREN))
            return null;
        nextToken(); // skip )
        whileStatement.setBody(parseBlockStatement());
        return whileStatement;
    }

    private BreakStatement parseBreakStatement() {
        var breakStatement = new BreakStatement(currentToken);
        if (!expectPeek(Type.SEMICOLON))
            return null;
        return breakStatement;
    }

    private ContinueStatement parseContinueStatement() {
        var continueStatement = new ContinueStatement(currentToken);
        if (!expectPeek(Type.SEMICOLON))
            return null;
        return continueStatement;
    }
}