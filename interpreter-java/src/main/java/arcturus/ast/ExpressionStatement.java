package arcturus.ast;

import arcturus.ast.interfaces.Expression;
import arcturus.ast.interfaces.Statement;
import arcturus.evaluator.env.Environment;
import arcturus.object.Object;
import arcturus.token.Token;

public class ExpressionStatement implements Statement {
    private Token token;
    private Expression expression;

    public ExpressionStatement(Token token) {
        this.token = token;
    }

    /**
     * @return the token
     */
    public Token getToken() {
        return token;
    }

    /**
     * @return the expression
     */
    public Expression getExpression() {
        return expression;
    }

    /**
     * @param expression the expression to set
     */
    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public Object evaluate(Environment env) {
        return expression.evaluate(env);
    }

    @Override
    public String toString() {
        return expression.toString() + "; ";
    }
}