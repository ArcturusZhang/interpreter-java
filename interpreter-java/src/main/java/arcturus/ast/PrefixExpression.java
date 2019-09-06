package arcturus.ast;

import arcturus.ast.interfaces.Expression;
import arcturus.evaluator.EvaluateException;
import arcturus.object.BooleanObject;
import arcturus.object.DecimalObject;
import arcturus.object.IntegerObject;
import arcturus.object.Object;
import arcturus.object.Object.Type;
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
    public String toString() {
        return operator + " " + right;
    }

    @Override
    public Object evaluate() {
        var rightValue = right.evaluate();
        switch (operator) {
        case "!":
            return evalBangObject(rightValue);
        case "-":
            return evalMinusObject(rightValue);
        default:
            throw new EvaluateException(String.format("Unknown operator %s", operator));
        }
    }

    private Object evalBangObject(Object right) {
        if (right.type() != Type.BOOLEAN)
            throw new EvaluateException(String.format("Operator ! cannot be used on type %s", right.type()));
        var object = (BooleanObject) right;
        return BooleanObject.getBoolean(!object.getValue());
    }

    private Object evalMinusObject(Object right) {
        switch (right.type()) {
        case INTEGER:
            var intObject = (IntegerObject) right;
            return new IntegerObject(intObject.getValue().negate());
        case DECIMAL:
            var decimalObject = (DecimalObject) right;
            return new DecimalObject(decimalObject.getValue().negate());
        default:
            throw new EvaluateException(String.format("Operator - cannot be used on type %s", right.type()));
        }
    }

}