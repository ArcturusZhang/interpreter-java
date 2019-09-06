package arcturus.object;

public interface Object {
    Type type();
    String inspect();

    public static enum Type {
        NULL,
        INTEGER,
        DECIMAL,
        BOOLEAN,
        STRING,
        FUNCTION;

        public boolean isNumber() {
            return this == INTEGER || this == DECIMAL;
        }

        public static Type max(Type t1, Type t2) {
            if (!t1.isNumber() && !t2.isNumber()) return NULL;
            if (t1 == DECIMAL || t2 == DECIMAL) return DECIMAL;
            return INTEGER;
        }
    }
}