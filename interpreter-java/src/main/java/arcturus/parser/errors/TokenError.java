package arcturus.parser.errors;

import arcturus.token.Token;
import arcturus.token.Token.Type;

public final class TokenError implements ParseError {
    public static final String PARSEERROR_FORMAT = "Parse error, expecting token %s, but got %s: line %d column %d";
    private int line;
    private int col;
    private Type expected;
    private Token got;
    private String message;

    public TokenError(Type expected, Token got, int line, int col) {
        this.expected = expected;
        this.got = got;
        this.line = line;
        this.col = col;
        message = String.format(PARSEERROR_FORMAT, expected, got, line, col);
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
    public String errorMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "{" +
            " line='" + line + "'" +
            ", col='" + col + "'" +
            ", expected='" + expected + "'" +
            ", got='" + got + "'" +
            ", message='" + message + "'" +
            "}";
    }
}