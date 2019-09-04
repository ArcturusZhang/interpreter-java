package arcturus.ast;

import java.util.ArrayList;
import java.util.List;

import arcturus.ast.interfaces.Expression;
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
    public void expression() {
    }
}