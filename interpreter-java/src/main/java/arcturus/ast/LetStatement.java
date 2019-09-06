package arcturus.ast;

import arcturus.ast.interfaces.Expression;
import arcturus.ast.interfaces.Statement;
import arcturus.object.Object;
import arcturus.token.Token;

public class LetStatement implements Statement {

    private Token token;
    private Identifier name;
    private Expression expression;

    public LetStatement(Token token) {
        this.token = token;
    }

    public LetStatement(Token token, Identifier name, Expression expression) {
        this.token = token;
        this.name = name;
        this.expression = expression;
    }

    public Token getToken() {
        return token;
    }

    public Identifier getName() {
        return name;
    }

    public void setName(Identifier name) {
        this.name = name;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        return String.format(PATTERN, name, expression);
    }

    private static final String PATTERN = "let %s = %s; ";

    @Override
    public Object evaluate() {
        return expression.evaluate();
    }
}