package arcturus.object;

public interface Object {
    Type type();
    String inspect();

    public static enum Type {
        NULL,
        INTEGER,
        DECIMAL,
        BOOLEAN,
        FUNCTION,
    }
}