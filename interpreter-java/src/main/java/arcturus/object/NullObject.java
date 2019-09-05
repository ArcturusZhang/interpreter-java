package arcturus.object;

public class NullObject implements Object {

    @Override
    public Type type() {
        return Type.NULL;
    }

    @Override
    public String inspect() {
        return "null";
    }

}