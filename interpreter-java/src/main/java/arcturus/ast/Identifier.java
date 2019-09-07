package arcturus.ast;

import arcturus.ast.interfaces.Expression;
import arcturus.evaluator.env.Environment;
import arcturus.object.Object;
import arcturus.object.errors.VariableNotDeclaredError;
import arcturus.token.Token;

public class Identifier implements Expression {

    private Token token;
    private String value;

    public Identifier(Token token, String value) {
        this.token = token;
        this.value = value;
    }

    public Token getToken() {
        return token;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public Object evaluate(Environment env) {
        // if variable does not exist, return an error.
        if (!env.contains(value)) return new VariableNotDeclaredError(this);
        // if variable exists, retrieve its value from env.
        return env.get(value);
    }

}