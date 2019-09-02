package arcturus.lexer.exception;

public class LexerException extends RuntimeException {
    private static final long serialVersionUID = 3003068915666078500L;

    public LexerException() {
        super();
    }

    public LexerException(String message) {
        super(message);
    }

    public LexerException(Throwable cause) {
        super(cause);
    }

    public LexerException(String message, Throwable cause) {
        super(message, cause);
    }
}