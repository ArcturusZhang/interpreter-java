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
}