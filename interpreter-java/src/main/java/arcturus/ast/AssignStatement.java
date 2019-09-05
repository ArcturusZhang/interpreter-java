package arcturus.ast;

import arcturus.ast.interfaces.Expression;
import arcturus.ast.interfaces.Statement;
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
    public void statement() {
    }

    @Override
    public String toString() {
        return String.format(PATTERN, variable, value);
    }

    private static final String PATTERN = "%s = %s; ";
}