package arcturus.ast;

import arcturus.ast.interfaces.Expression;
import arcturus.ast.interfaces.Statement;
import arcturus.evaluator.env.Environment;
import arcturus.object.BooleanObject;
import arcturus.object.BreakObject;
import arcturus.object.ContinueObject;
import arcturus.object.NullObject;
import arcturus.object.Object;
import arcturus.object.ReturnValue;
import arcturus.object.errors.ErrorObject;
import arcturus.token.Token;

public class WhileStatement implements Statement {

    private Token token; // always while
    private Expression condition;
    private BlockStatement body;

    public WhileStatement(Token token) {
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
     * @return the body
     */
    public BlockStatement getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(BlockStatement body) {
        this.body = body;
    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public Object evaluate(Environment env) {
        try {
            Object result = NullObject.NULL;
            while (evaluateCondition(env)) {
                result = body.evaluate(env);
                if (result instanceof ErrorObject)
                    return result;
                if (result instanceof ContinueObject) {
                    result = ((ContinueObject) result).getPrevious();
                    continue;
                }
                if (result instanceof BreakObject) {
                    result = ((BreakObject) result).getPrevious();
                    break;
                }
                if (result instanceof ReturnValue)
                    return result;
            }
            return result;
        } catch (ConditionException e) {
            return new ErrorObject("Error: condition of while statement must be boolean");
        }
    }

    private boolean evaluateCondition(Environment env) {
        var result = this.condition.evaluate(env);
        if (result instanceof BooleanObject) {
            return ((BooleanObject) result).getValue();
        }
        throw new ConditionException();
    }

    private static class ConditionException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

}