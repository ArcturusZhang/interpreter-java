package arcturus.ast;

import java.math.BigInteger;

import arcturus.ast.interfaces.Expression;
import arcturus.evaluator.env.Environment;
import arcturus.object.IntegerObject;
import arcturus.object.Object;
import arcturus.token.Token;

public class IntegerLiteral implements Expression {

    private Token token;
    private BigInteger value;

    public IntegerLiteral(Token token, BigInteger value) {
        this.token = token;
        this.value = value;
    }

    public Token getToken() {
        return token;
    }

    public BigInteger getValue() {
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
        var result = new IntegerObject(value);
        env.setCurrent(result);
        return result;
    }

}