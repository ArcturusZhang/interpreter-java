package arcturus.parser;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arcturus.ast.BooleanLiteral;
import arcturus.ast.DecimalLiteral;
import arcturus.ast.Identifier;
import arcturus.ast.InfixExpression;
import arcturus.ast.IntegerLiteral;
import arcturus.ast.LetStatement;
import arcturus.ast.PrefixExpression;
import arcturus.ast.Program;
import arcturus.ast.ReturnStatement;
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
        registerPrefix(this::parsePrefixExpression, Type.BANG, Type.MINUS);
        registerPrefix(this::parseGroupedExpression, Type.LPAREN);
        registerPrefix(this::parseIfExpression, Type.IF);
        registerPrefix(this::parseForExpression, Type.FOR);
        registerPrefix(this::parseFunctionLiteral, Type.FUNCTION);
        // registerPrefix(this::parseStringLiteral, Type.STRING);
        registerPrefix(this::parseArrayLiteral, Type.LBRACKET);
        infixParseMap = new HashMap<>();
        registerInfix(this::parseInfixExpression, Type.PLUS, Type.MINUS, Type.ASTERISK, Type.SLASH, 
        Type.EQ, Type.NE, Type.LT, Type.GT, Type.LE, Type.GE);
        // registerInfix(this::parseCallExpression, Type.LPAREN);
        // registerInfix(this::parseIndexExpression, Type.LBRACKET);
    }

    private void registerPrefix(PrefixParseFunction function, Type ... types) {
        for (var type : types) {
            prefixParseMap.put(type, function);
        }
    }

    private void registerInfix(InfixParseFunction function, Type ... types) {
        for (var type : types) {
            infixParseMap.put(type, function);
        }
    }

    public List<ParseError> getErrors() {
        return errors;
    }

    public void raiseTokenError(Type expected, Token got) {
        errors.add(new TokenError(expected, got, lexer.getLine(), lexer.getCol()));
    }

    public void raiseNoPrefixParseError(Type type) {
        errors.add(new NoPrefixParseError(type, lexer.getLine(), lexer.getCol()));
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

    private boolean expectPeek(Type type) {
        if (peekToken.getType() == type) {
            nextToken();
            return true;
        }
        raiseTokenError(type, peekToken);
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
        default:
            // todo
            return null;
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
            raiseNoPrefixParseError(currentToken.getType());
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
            errors.add(new NumberFormatError(literal, "integer", lexer.getLine(), lexer.getCol()));
            return null;
        }
    }

    private Expression parseDecimalLiteral() {
        var literal =currentToken.getLiteral();
        try {
            var value = new BigDecimal(literal);
            return new DecimalLiteral(currentToken, value);
        } catch (NumberFormatException e) {
            errors.add(new NumberFormatError(literal, "decimal", lexer.getLine(), lexer.getCol()));
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

    private Expression parseIfExpression() {
        return null;
    }

    private Expression parseForExpression() {
        return null;
    }

    private Expression parseFunctionLiteral() {
        return null;
    }

    private Expression parseArrayLiteral() {
        return null;
    }
}