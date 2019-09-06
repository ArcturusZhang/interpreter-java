package arcturus.parser.errors;

public class NumberFormatError implements ParseError {
    private String message;

    public NumberFormatError(String literal, String type, int line, int col) {
        message = String.format("Parse error, cannot parse %s as %s: line %d, column %d", literal, type, line, col);
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