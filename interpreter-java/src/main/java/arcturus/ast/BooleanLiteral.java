package arcturus.ast;

import arcturus.ast.interfaces.Expression;
import arcturus.token.Token;

public class BooleanLiteral implements Expression {

    private Token token;
    private boolean value;

    public BooleanLiteral(Token token, boolean value) {
        this.token = token;
        this.value = value;
    }

    /**
     * @return the token
     */
    public Token getToken() {
        return token;
    }

    public boolean getValue() {
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
        return String.valueOf(value);
    }

}