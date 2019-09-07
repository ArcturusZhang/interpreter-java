package arcturus.object;

import java.math.BigInteger;

public class IntegerObject extends Object {
    private BigInteger value;

    public IntegerObject(BigInteger value) {
        this.value = value;
    }

    public IntegerObject(String value) {
        this.value = new BigInteger(value);
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
    public boolean equals(java.lang.Object obj) {
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        return value.compareTo(((IntegerObject) obj).value) == 0;
    }

}