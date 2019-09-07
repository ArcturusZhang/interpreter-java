package arcturus.object;

public class ContinueObject extends Object {
    private Object previous;
    public ContinueObject(Object previous) {
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
        return Type.CONTINUE;
    }

    @Override
    public String inspect() {
        return "continue";
    }
}