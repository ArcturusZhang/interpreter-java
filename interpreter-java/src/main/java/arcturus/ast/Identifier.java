package arcturus.ast;

import arcturus.ast.interfaces.Expression;
import arcturus.token.Token;

public class Identifier implements Expression {

    private Token token;
    private String value;

    public Identifier(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public void expression() {

    }

}