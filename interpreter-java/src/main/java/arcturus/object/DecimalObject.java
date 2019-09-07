package arcturus.object;

import java.math.BigDecimal;

public class DecimalObject extends Object {
    private BigDecimal value;

    public DecimalObject(BigDecimal value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public Type type() {
        return Type.DECIMAL;
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
        return value.compareTo(((DecimalObject) obj).value) == 0;
    }
}