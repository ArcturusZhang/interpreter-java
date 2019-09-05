package arcturus.ast;

import arcturus.ast.interfaces.Expression;
import arcturus.token.Token;

public class StringLiteral implements Expression {

    private Token token;
    private String value;
    public StringLiteral(Token token, String value) {
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
    public String getValue() {
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
        return String.format(PATTERN, value);
    }

    private static final String PATTERN = "\"%s\"";
}