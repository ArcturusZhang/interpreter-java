package arcturus.ast;

import arcturus.ast.interfaces.Expression;
import arcturus.token.Token;

public class PrefixExpression implements Expression {

    private Token token;
    private String operator;
    private Expression right;

    public PrefixExpression(Token token, String operator) {
        this.token = token;
        this.operator = operator;
    }

    /**
     * @return the token
     */
    public Token getToken() {
        return token;
    }

    /**
     * @return the operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @return the right
     */
    public Expression getRight() {
        return right;
    }
    
    /**
     * @param right the right to set
     */
    public void setRight(Expression right) {
        this.right = right;
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
        return operator + " " + right;
    }

}