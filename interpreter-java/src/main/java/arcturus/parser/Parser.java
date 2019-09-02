package arcturus.parser;

import arcturus.ast.Program;
import arcturus.lexer.Lexer;
import arcturus.token.Token;

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
        // todo
        return null;
    }
}