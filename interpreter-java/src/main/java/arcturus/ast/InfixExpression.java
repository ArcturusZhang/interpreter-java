package arcturus.ast;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import arcturus.ast.interfaces.Expression;
import arcturus.evaluator.EvaluateException;
import arcturus.object.DecimalObject;
import arcturus.object.IntegerObject;
import arcturus.object.Object;
import arcturus.object.Object.Type;
import arcturus.repl.Repl;
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
    public String toString() {
        return String.format(PATTERN, left, operator, right);
    }

    private static final String PATTERN = "( %s %s %s )";

    @Override
    public Object evaluate() {
        var leftObj = left.evaluate();
        var rightObj = right.evaluate();
        var type = Type.max(leftObj.type(), rightObj.type());
        switch (type) {
            case INTEGER:
                return evalInteger((IntegerObject)leftObj, (IntegerObject)rightObj);
            case DECIMAL:
                return evalDecimal(castValue(leftObj), castValue(rightObj));
            case BOOLEAN:
            default:
                throw evaluateException(operator, leftObj, rightObj);
        }
    }
    
    private IntegerObject evalInteger(IntegerObject left, IntegerObject right) {
        var func = intMap.get(operator);
        return new IntegerObject(func.apply(left.getValue(), right.getValue()));
    }

    private DecimalObject evalDecimal(DecimalObject left, DecimalObject right) {
        var func = decMap.get(operator);
        return new DecimalObject(func.apply(left.getValue(), right.getValue()));
    }

    private EvaluateException evaluateException(String operator, Object left, Object right) {
        return new EvaluateException(String.format("Error - operator %s cannot be used between type %s and %s", operator, left.type(), right.type()));
    }

    private DecimalObject castValue(Object obj) {
        if (obj instanceof IntegerObject) {
            var intObj = (IntegerObject) obj;
            return new DecimalObject(new BigDecimal(intObj.getValue()));
        }
        return (DecimalObject) obj;
    }

    private static Map<String, BiFunction<BigInteger, BigInteger, BigInteger>> intMap = new HashMap<>();
    private static Map<String, BiFunction<BigDecimal, BigDecimal, BigDecimal>> decMap = new HashMap<>();

    static {
        intMap.put("+", (a, b) -> a.add(b));
        intMap.put("-", (a, b) -> a.subtract(b));
        intMap.put("*", (a, b) -> a.multiply(b));
        intMap.put("/", (a, b) -> a.divide(b));
        decMap.put("+", (a, b) -> a.add(b));
        decMap.put("-", (a, b) -> a.subtract(b));
        decMap.put("*", (a, b) -> a.multiply(b));
        decMap.put("/", (a, b) -> a.divide(b, Repl.decimalCount, RoundingMode.HALF_UP));
    }

}