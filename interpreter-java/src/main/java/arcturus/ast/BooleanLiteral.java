package arcturus.ast;

import arcturus.ast.interfaces.Expression;
import arcturus.evaluator.env.Environment;
import arcturus.object.BooleanObject;
import arcturus.object.Object;
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
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public Object evaluate(Environment env) {
        var result = BooleanObject.getBoolean(value);
        env.setCurrent(result);
        return result;
    }

}