package arcturus.ast;

import arcturus.ast.interfaces.Statement;
import arcturus.evaluator.env.Environment;
import arcturus.object.ContinueObject;
import arcturus.object.Object;
import arcturus.token.Token;

public class ContinueStatement implements Statement {

    private Token token; // always continue
    public ContinueStatement(Token token) {
        this.token = token;
    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public Object evaluate(Environment env) {
        return ContinueObject.CONTINUE;
    }

}