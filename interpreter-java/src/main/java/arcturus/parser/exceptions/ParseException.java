package arcturus.parser.exceptions;

public class ParseException extends RuntimeException {
    private static final long serialVersionUID = 797290940191470790L;

    public ParseException() {
        super();
    }

    public ParseException(String message) {
        super(message);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}