package arcturus.object;

public class BreakObject extends Object {

    private Object previous;

    public BreakObject(Object previous) {
        this.previous = previous;
    }

    /**
     * @return the previous
     */
    public Object getPrevious() {
        return previous;
    }

    @Override
    public Type type() {
        return Type.BREAK;
    }

    @Override
    public String inspect() {
        return "break";
    }

}