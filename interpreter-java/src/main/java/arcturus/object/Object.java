package arcturus.object;

public abstract class Object {
    public abstract Type type();

    public abstract String inspect();

    @Override
    public String toString() {
        return this.inspect();
    }

    public static enum Type {
        NULL, INTEGER, DECIMAL, BOOLEAN, STRING, FUNCTION, RETURN_VALUE, ERROR;

        public boolean isNumber() {
            return this == INTEGER || this == DECIMAL;
        }

        public static Type conform(Type t1, Type t2) {
            switch (t1) {
            case INTEGER:
            case DECIMAL:
                return conformNumber(t1, t2);
            case BOOLEAN:
                return conformBoolean(t1, t2);
            default:
                return NULL;
            }
        }

        private static Type conformNumber(Type t1, Type t2) {
            if (!t1.isNumber() || !t2.isNumber())
                return NULL;
            if (t1 == DECIMAL || t2 == DECIMAL)
                return DECIMAL;
            return INTEGER;
        }

        private static Type conformBoolean(Type t1, Type t2) {
            if (t1 != BOOLEAN || t2 != BOOLEAN)
                return NULL;
            return BOOLEAN;
        }
    }
}