package arcturus.ast;

import java.math.BigDecimal;

import arcturus.ast.interfaces.Expression;
import arcturus.token.Token;

public class DecimalLiteral implements Expression {

    private Token token;
    private BigDecimal value;
    public DecimalLiteral(Token token, BigDecimal value) {
        this.token = token;
        this.value = value;
    }

    /**
     * @return the token
     */
    public Token getToken() {
        return token;
    }

    /**
     * @return the value
     */
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public void expression() {

    }

    @Override
    public String toString() {
        return value.toString();
    }

}