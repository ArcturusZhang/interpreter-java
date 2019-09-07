package arcturus.ast;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import arcturus.ast.interfaces.Expression;
import arcturus.evaluator.env.Environment;
import arcturus.object.BooleanObject;
import arcturus.object.DecimalObject;
import arcturus.object.IntegerObject;
import arcturus.object.Object;
import arcturus.object.Object.Type;
import arcturus.object.errors.TypeMismatchError;
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

    // todo -- short cut evaluation for boolean
    @Override
    public Object evaluate(Environment env) {
        var leftObj = left.evaluate(env);
        var rightObj = right.evaluate(env);
        var type = Type.conform(leftObj.type(), rightObj.type());
        switch (type) {
        case INTEGER:
            return evalInteger((IntegerObject) leftObj, (IntegerObject) rightObj);
        case DECIMAL:
            return evalDecimal(leftObj, rightObj);
        case BOOLEAN:
            return evalBoolean((BooleanObject) leftObj, (BooleanObject) rightObj);
        default:
            return new TypeMismatchError(operator, leftObj.type(), rightObj.type());
        }
    }

    private Object evalInteger(IntegerObject left, IntegerObject right) {
        if (ARITH_OPERATORS.contains(operator)) {
            var func = intArithMap.get(operator);
            if (func == null)
                return new TypeMismatchError(operator, left.type(), right.type());
            return new IntegerObject(func.apply(left.getValue(), right.getValue()));
        } else {
            var func = intBoolMap.get(operator);
            if (func == null)
                return new TypeMismatchError(operator, left.type(), right.type());
            return BooleanObject.getBoolean(func.apply(left.getValue(), right.getValue()));
        }
    }

    private Object evalDecimal(Object left, Object right) {
        if (ARITH_OPERATORS.contains(operator)) {
            var func = decArithMap.get(operator);
            if (func == null)
                return new TypeMismatchError(operator, left.type(), right.type());
            return new DecimalObject(func.apply(castValue(left).getValue(), castValue(right).getValue()));
        } else {
            var func = decBoolMap.get(operator);
            if (func == null)
                return new TypeMismatchError(operator, left.type(), right.type());
            return BooleanObject.getBoolean(func.apply(castValue(left).getValue(), castValue(right).getValue()));
        }
    }

    private Object evalBoolean(BooleanObject left, BooleanObject right) {
        switch (operator) {
        case "&&":
            return left == BooleanObject.FALSE ? left : right;
        case "||":
            return left == BooleanObject.TRUE ? left : right;
        default:
            return new TypeMismatchError(operator, left.type(), right.type());
        }
    }

    private DecimalObject castValue(Object obj) {
        if (obj instanceof IntegerObject) {
            var intObj = (IntegerObject) obj;
            return new DecimalObject(new BigDecimal(intObj.getValue()));
        }
        return (DecimalObject) obj;
    }

    private static final Map<String, BiFunction<BigInteger, BigInteger, BigInteger>> intArithMap = new HashMap<>();
    private static final Map<String, BiFunction<BigDecimal, BigDecimal, BigDecimal>> decArithMap = new HashMap<>();
    private static final Map<String, BiFunction<BigInteger, BigInteger, Boolean>> intBoolMap = new HashMap<>();
    private static final Map<String, BiFunction<BigDecimal, BigDecimal, Boolean>> decBoolMap = new HashMap<>();

    static {
        intArithMap.put("+", (a, b) -> a.add(b));
        intArithMap.put("-", (a, b) -> a.subtract(b));
        intArithMap.put("*", (a, b) -> a.multiply(b));
        intArithMap.put("/", (a, b) -> a.divide(b));
        decArithMap.put("+", (a, b) -> a.add(b));
        decArithMap.put("-", (a, b) -> a.subtract(b));
        decArithMap.put("*", (a, b) -> a.multiply(b));
        decArithMap.put("/", (a, b) -> a.divide(b, Repl.decimalCount, RoundingMode.HALF_UP));
        intBoolMap.put("==", (a, b) -> a.compareTo(b) == 0);
        intBoolMap.put("!=", (a, b) -> a.compareTo(b) != 0);
        intBoolMap.put(">", (a, b) -> a.compareTo(b) > 0);
        intBoolMap.put(">=", (a, b) -> a.compareTo(b) >= 0);
        intBoolMap.put("<", (a, b) -> a.compareTo(b) < 0);
        intBoolMap.put("<=", (a, b) -> a.compareTo(b) <= 0);
        decBoolMap.put("==", (a, b) -> a.compareTo(b) == 0);
        decBoolMap.put("!=", (a, b) -> a.compareTo(b) != 0);
        decBoolMap.put(">", (a, b) -> a.compareTo(b) > 0);
        decBoolMap.put(">=", (a, b) -> a.compareTo(b) >= 0);
        decBoolMap.put("<", (a, b) -> a.compareTo(b) < 0);
        decBoolMap.put("<=", (a, b) -> a.compareTo(b) <= 0);
    }

    private static final String ARITH_OPERATORS = "+-*/";

}