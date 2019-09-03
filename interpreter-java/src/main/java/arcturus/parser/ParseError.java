package arcturus.parser;

import arcturus.token.Token;
import arcturus.token.Token.Type;

public final class ParseError {
    public static final String PARSEERROR_FORMAT = "Parsing error, expecting token %s, but got %s: line %d column %d";
    private int line;
    private int col;
    private Type expected;
    private Token got;

    public ParseError(Type expected, Token got, int line, int col) {
        this.expected = expected;
        this.got = got;
        this.line = line;
        this.col = col;
    }

    public int getLine() {
        return this.line;
    }

    public int getCol() {
        return this.col;
    }

    public Type getExpected() {
        return this.expected;
    }

    public Token getGot() {
        return this.got;
    }

    @Override
    public String toString() {
        return String.format(PARSEERROR_FORMAT, expected, got, line, col);
    }
}