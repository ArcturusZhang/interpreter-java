package arcturus.ast;

import arcturus.ast.interfaces.Expression;
import arcturus.ast.interfaces.Statement;
import arcturus.token.Token;

public class LetStatement implements Statement {

    private Token token;
    private Identifier name;
    private Expression expression;

    public LetStatement(Token token, Identifier name, Expression expression) {
        this.token = token;
        this.name = name;
        this.expression = expression;
    }

    @Override
    public String tokenLiteral() {
        return null;
    }

    @Override
    public void statement() {

    }

}