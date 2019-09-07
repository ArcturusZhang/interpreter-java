package arcturus.object.errors;

import arcturus.object.Object;

public class ErrorObject extends Object {
    protected String message;
    public ErrorObject(String message) {
        this.message = message;
    }

    @Override
    public Type type() {
        return Type.ERROR;
    }

    @Override
    public String inspect() {
        return message;
    }

    @Override
    public boolean equals(java.lang.Object obj) {
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        return message.equals(((ErrorObject)obj).message);
    }

}