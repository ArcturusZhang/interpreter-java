package arcturus.parser.errors;

import arcturus.token.Token;

public class IllegalTokenError implements ParseError {

    private String message;
    public IllegalTokenError(Token token, int line, int col) {
        message = String.format("Parse error, illegal token %s: line %d, column %d", token, line, col);
    }

    @Override
    public String errorMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }

}