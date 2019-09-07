package arcturus.ast;

import arcturus.ast.interfaces.Expression;
import arcturus.ast.interfaces.Statement;
import arcturus.evaluator.env.Environment;
import arcturus.object.Object;
import arcturus.object.errors.VariableNotDeclaredError;
import arcturus.token.Token;

public class AssignStatement implements Statement {

    private Token token;
    private Identifier variable;
    private Expression value;

    public AssignStatement(Token token, Identifier variable) {
        this.token = token;
        this.variable = variable;
    }

    /**
     * @return the token
     */
    public Token getToken() {
        return token;
    }

    /**
     * @return the variable
     */
    public Identifier getVariable() {
        return variable;
    }

    /**
     * @return the value
     */
    public Expression getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Expression value) {
        this.value = value;
    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        return String.format(PATTERN, variable, value);
    }

    private static final String PATTERN = "%s = %s; ";

    @Override
    public Object evaluate(Environment env) {
        // if variable does not exist, return an error
        if (!env.contains(variable.getValue()))
        return new VariableNotDeclaredError(variable);
        // if variable exists, evaluate its value, and put it into env
        var result = value.evaluate(env);
        env.put(variable.getValue(), result);
        env.setCurrent(result);
        return result;
    }
}