package arcturus.object;

public class ContinueObject extends Object {

    private ContinueObject() {}

    @Override
    public Type type() {
        return Type.CONTINUE;
    }

    @Override
    public String inspect() {
        return "continue";
    }

    public static final ContinueObject CONTINUE = new ContinueObject();

}