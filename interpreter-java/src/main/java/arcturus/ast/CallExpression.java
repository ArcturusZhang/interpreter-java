package arcturus.ast;

import java.util.List;
import java.util.stream.Collectors;

import arcturus.ast.interfaces.Expression;
import arcturus.evaluator.env.Environment;
import arcturus.object.Object;
import arcturus.token.Token;

public class CallExpression implements Expression {
    private Token token;
    private Expression function;
    private List<Expression> arguments;

    public CallExpression(Token token, Expression function, List<Expression> arguments) {
        this.token = token;
        this.function = function;
        this.arguments = arguments;
    }

    /**
     * @return the token
     */
    public Token getToken() {
        return token;
    }

    /**
     * @return the function
     */
    public Expression getFunction() {
        return function;
    }

    /**
     * @return the arguments
     */
    public List<Expression> getArguments() {
        return arguments;
    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        return function + "(\n"
                + String.join(",\n", arguments.stream().map(Expression::toString).collect(Collectors.toList())) + "\n)";
    }

    @Override
    public Object evaluate(Environment env) {
        // TODO Auto-generated method stub
        return null;
    }
}