package arcturus.object;

import java.math.BigInteger;

public class IntegerObject implements Object {
    private BigInteger value;
    public IntegerObject(BigInteger value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public BigInteger getValue() {
        return value;
    }

    @Override
    public Type type() {
        return Type.INTEGER;
    }

    @Override
    public String inspect() {
        return value.toString();
    }

    @Override
    public String toString() {
        return value.toString();
    }

}