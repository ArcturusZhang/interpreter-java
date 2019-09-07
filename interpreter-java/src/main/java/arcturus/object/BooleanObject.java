package arcturus.object;

public class BooleanObject extends Object {
    private boolean value;

    private BooleanObject(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Type type() {
        return Type.BOOLEAN;
    }

    @Override
    public String inspect() {
        return String.valueOf(value);
    }

    public static final BooleanObject TRUE = new BooleanObject(true);
    public static final BooleanObject FALSE = new BooleanObject(false);

    public static BooleanObject getBoolean(boolean value) {
        return value ? TRUE : FALSE;
    }
}