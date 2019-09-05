package arcturus.ast;

import java.math.BigInteger;

import arcturus.ast.interfaces.Expression;
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
    public void expression() {

    }

    @Override
    public String toString() {
        return value.toString();
    }

}