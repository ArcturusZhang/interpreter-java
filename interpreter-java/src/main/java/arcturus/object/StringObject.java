package arcturus.object;

public class StringObject extends Object {
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

}