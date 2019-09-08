package arcturus.ast;

import arcturus.ast.interfaces.Expression;
import arcturus.evaluator.env.Environment;
import arcturus.object.BooleanObject;
import arcturus.object.DecimalObject;
import arcturus.object.IntegerObject;
import arcturus.object.Object;
import arcturus.object.Object.Type;
import arcturus.object.errors.TypeMismatchError;
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
    public Object evaluate(Environment env) {
        var rightValue = right.evaluate(env);
        Object result;
        switch (operator) {
        case "!":
            result = evalBangObject(rightValue);
            break;
        case "-":
            result = evalMinusObject(rightValue);
            break;
        case "+":
            result = evalPlusObject(rightValue);
            break;
        default:
            return new TypeMismatchError(operator, rightValue.type());
        }
        env.setCurrent(result);
        return result;
    }

    private Object evalBangObject(Object right) {
        if (right.type() != Type.BOOLEAN)
            return new TypeMismatchError(operator, right.type());
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
            return new TypeMismatchError(operator, right.type());
        }
    }

    private Object evalPlusObject(Object right) {
        switch (right.type()) {
        case INTEGER:
        case DECIMAL:
            return right;
        default:
            return new TypeMismatchError(operator, right.type());
        }
    }

}