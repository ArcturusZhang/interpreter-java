package arcturus.ast;

import arcturus.ast.interfaces.Expression;
import arcturus.evaluator.env.Environment;
import arcturus.object.Object;
import arcturus.object.StringObject;
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
    public String toString() {
        return String.format(PATTERN, value);
    }

    private static final String PATTERN = "\"%s\"";

    @Override
    public Object evaluate(Environment env) {
        return new StringObject(value);
    }
}