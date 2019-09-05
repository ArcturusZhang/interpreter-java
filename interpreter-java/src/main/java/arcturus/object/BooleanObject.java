package arcturus.object;

public class BooleanObject implements Object {
    private boolean value;

    public BooleanObject(boolean value) {
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

}