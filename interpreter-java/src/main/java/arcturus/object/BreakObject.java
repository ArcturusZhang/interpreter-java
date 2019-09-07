package arcturus.object;

public class BreakObject extends Object {
    private BreakObject() {
    }

    @Override
    public Type type() {
        return Type.BREAK;
    }

    @Override
    public String inspect() {
        return "break";
    }

    public static final BreakObject BREAK = new BreakObject();

}