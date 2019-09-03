package arcturus.parser.errors;

import arcturus.token.Token.Type;

public class NoPrefixParseError implements ParseError {
    public static final String ERROR_FORMAT = "Parsing error, do not find prefix function for type %s, but got %s: line %d column %d";
    private Type type;
    private int line;
    private int col;
    private String message;
    public NoPrefixParseError(Type type, int line, int col) {
        this.type = type;
        this.line = line;
        this.col = col;
        this.message = String.format(ERROR_FORMAT, type, line, col);
    }

    @Override
    public String errorMessage() {
        return message;
    }

    public Type getType() {
        return this.type;
    }

    public int getLine() {
        return this.line;
    }

    public int getCol() {
        return this.col;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return "{" +
            " type='" + type + "'" +
            ", line='" + line + "'" +
            ", col='" + col + "'" +
            ", message='" + message + "'" +
            "}";
    }

}