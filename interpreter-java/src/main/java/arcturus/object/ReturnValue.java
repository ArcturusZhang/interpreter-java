package arcturus.object;

public class ReturnValue extends Object {

    private Object value;
    public ReturnValue(Object value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    @Override
    public Type type() {
        return Type.RETURN_VALUE;
    }

    @Override
    public String inspect() {
        return value.inspect();
    }

}