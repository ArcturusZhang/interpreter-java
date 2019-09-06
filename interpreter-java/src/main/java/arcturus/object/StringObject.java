package arcturus.object;

public class StringObject implements Object {
    private String value;

    public StringObject(String value) {
        this.value = value;
    }

    @Override
    public Type type() {
        return Type.STRING;
    }

    @Override
    public String inspect() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

}