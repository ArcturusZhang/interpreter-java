package arcturus.ast;

import arcturus.ast.interfaces.Expression;
import arcturus.ast.interfaces.Statement;
import arcturus.token.Token;

public class ReturnStatement implements Statement {
    private Token token;
    private Expression returnValue;

    public ReturnStatement(Token token) {
        this.token = token;
    }

    public ReturnStatement(Token token, Expression expression) {
        this(token);
        this.returnValue = expression;
    }

    public Token getToken() {
        return token;
    }

    public Expression getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Expression value) {
        this.returnValue = value;
    }

    @Override
    public String tokenLiteral() {
        return this.token.getLiteral();
    }

    @Override
    public void statement() {

    }

    @Override
    public String toString() {
        return String.format(PATTERN, returnValue);
    }

    private static final String PATTERN = "return %s; ";
}