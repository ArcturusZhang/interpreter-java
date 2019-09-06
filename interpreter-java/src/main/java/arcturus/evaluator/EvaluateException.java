package arcturus.evaluator;

public class EvaluateException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 5958769520357040866L;

    public EvaluateException() {
        super();
    }
    public EvaluateException(String message) {
        super(message);
    }
    public EvaluateException(String message, Throwable cause) {
        super(message, cause);
    }
    public EvaluateException(Throwable cause) {
        super(cause);
    }
}