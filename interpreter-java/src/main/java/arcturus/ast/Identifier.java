package arcturus.ast;

import arcturus.ast.interfaces.Expression;
import arcturus.token.Token;

public class Identifier implements Expression {

    private Token token;
    private String value;

    public Identifier(Token token) {
        this.token = token;
    }

    @Override
    public String tokenLiteral() {
        return null;
    }

    @Override
    public void expression() {

    }

}