package arcturus.ast;

import arcturus.ast.interfaces.Expression;
import arcturus.token.Token;

public class InfixExpression implements Expression {
    private Token token;
    private Expression left;
    private String operator;
    private Expression right;
    public InfixExpression(Token token, String operator) {
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
     * @return the left
     */
    public Expression getLeft() {
        return left;
    }

    /**
     * @return the right
     */
    public Expression getRight() {
        return right;
    }

    /**
     * @param left the left to set
     */
    public void setLeft(Expression left) {
        this.left = left;
    }

    /**
     * @param right the right to set
     */
    public void setRight(Expression right) {
        this.right = right;
    }

    @Override
    public String tokenLiteral() {
        return null;
    }

    @Override
    public void expression() {

    }

    @Override
    public String toString() {
        return String.format(PATTERN, left, operator, right);
    }

    private static final String PATTERN = "( %s %s %s )";

}