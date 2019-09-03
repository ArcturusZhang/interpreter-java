package arcturus.parser;

import java.util.ArrayList;
import java.util.List;

import arcturus.ast.Identifier;
import arcturus.ast.LetStatement;
import arcturus.ast.Program;
import arcturus.ast.ReturnStatement;
import arcturus.ast.interfaces.Statement;
import arcturus.lexer.Lexer;
import arcturus.token.Token;
import arcturus.token.Token.Type;

public class Parser {
    private Lexer lexer;
    private Token currentToken;
    private Token peekToken;
    private List<ParseError> errors;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        nextToken();
        nextToken();
        errors = new ArrayList<>();
    }

    public List<ParseError> getErrors() {
        return errors;
    }

    public void raiseParseError(Type expected, Token got, int line, int col) {
        errors.add(new ParseError(expected, got, line, col));
    }

    public void raiseParseError(Type expected, Token got) {
        errors.add(new ParseError(expected, got, lexer.getLine(), lexer.getCol()));
    }

    public void nextToken() {
        currentToken = peekToken;
        peekToken = lexer.nextToken();
    }

    private boolean currentTokenIs(Type type) {
        return currentToken.getType() == type;
    }

    private boolean expectPeek(Type type) {
        if (peekToken.getType() == type) {
            nextToken();
            return true;
        }
        raiseParseError(type, peekToken);
        return false;
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
        // todo -- expression
        while (!currentTokenIs(Type.SEMICOLON)) {
            nextToken();
        }
        return statement;
    }

    private ReturnStatement parseReturnStatement() {
        var returnStatement = new ReturnStatement(currentToken);
        nextToken(); // skip the return keyword
        // todo -- expression
        while (!currentTokenIs(Type.SEMICOLON)) {
            nextToken();
        }
        return returnStatement;
    }
}