package arcturus.object;

public class NullObject implements Object {

    private NullObject() {}

    @Override
    public Type type() {
        return Type.NULL;
    }

    @Override
    public String inspect() {
        return "null";
    }

    @Override
    public String toString() {
        return inspect();
    }

    public static final NullObject NULL = new NullObject();

}