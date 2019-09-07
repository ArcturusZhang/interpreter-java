package arcturus.object;

public class NullObject extends Object {

    private NullObject() {}

    @Override
    public Type type() {
        return Type.NULL;
    }

    @Override
    public String inspect() {
        return "null";
    }

    public static final NullObject NULL = new NullObject();

}