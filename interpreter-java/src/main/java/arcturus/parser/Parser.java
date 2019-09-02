package arcturus.parser;

import arcturus.ast.LetStatement;
import arcturus.ast.Program;
import arcturus.ast.interfaces.Statement;
import arcturus.lexer.Lexer;
import arcturus.token.Token;
import arcturus.token.Token.Type;

public class Parser {
    private Lexer lexer;
    private Token currentToken;
    private Token peekToken;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        nextToken();
        nextToken();
    }

    public void nextToken() {
        currentToken = peekToken;
        peekToken = lexer.nextToken();
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
        default:
            return null;
        }
    }

    private LetStatement parseLetStatement() {
        var statement = new LetStatement(currentToken);
        // todo
        return statement;
    }
}