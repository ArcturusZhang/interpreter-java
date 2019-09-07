package arcturus.ast;

import arcturus.ast.interfaces.Expression;
import arcturus.ast.interfaces.Statement;
import arcturus.evaluator.env.Environment;
import arcturus.object.BooleanObject;
import arcturus.object.NullObject;
import arcturus.object.Object;
import arcturus.object.errors.ErrorObject;
import arcturus.token.Token;

public class IfStatement implements Statement {

    private Token token;
    private Expression condition;
    private BlockStatement thenStatement;
    private BlockStatement elseStatement;

    public IfStatement(Token token) {
        this.token = token;
    }

    /**
     * @return the token
     */
    public Token getToken() {
        return token;
    }

    /**
     * @return the condition
     */
    public Expression getCondition() {
        return condition;
    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(Expression condition) {
        this.condition = condition;
    }

    /**
     * @return the thenStatement
     */
    public BlockStatement getThenStatement() {
        return thenStatement;
    }

    /**
     * @param thenStatement the thenStatement to set
     */
    public void setThenStatement(BlockStatement thenStatement) {
        this.thenStatement = thenStatement;
    }

    /**
     * @param elseStatement the elseStatement to set
     */
    public void setElseStatement(BlockStatement elseStatement) {
        this.elseStatement = elseStatement;
    }

    /**
     * @return the elseStatement
     */
    public BlockStatement getElseStatement() {
        return elseStatement;
    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public Object evaluate(Environment env) {
        var result = condition.evaluate(env);
        if (result instanceof ErrorObject) return result;
        if (result == BooleanObject.TRUE) {
            return thenStatement.evaluate(env);
        } else {
            return elseStatement != null ? elseStatement.evaluate(env) : NullObject.NULL;
        }
    }

}