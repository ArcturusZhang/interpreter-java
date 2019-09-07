package arcturus.ast;

import arcturus.ast.interfaces.Statement;
import arcturus.evaluator.env.Environment;
import arcturus.object.BreakObject;
import arcturus.object.Object;
import arcturus.token.Token;

public class BreakStatement implements Statement {
    private Token token; // always break

    public BreakStatement(Token token) {
        this.token = token;
    }

    /**
     * @return the token
     */
    public Token getToken() {
        return token;
    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public Object evaluate(Environment env) {
        return new BreakObject(env.getCurrent());
    }

}