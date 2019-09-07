package arcturus.ast;

import java.math.BigDecimal;

import arcturus.ast.interfaces.Expression;
import arcturus.evaluator.env.Environment;
import arcturus.object.DecimalObject;
import arcturus.object.Object;
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
    public String toString() {
        return value.toString();
    }

    @Override
    public Object evaluate(Environment env) {
        return new DecimalObject(value);
    }

}