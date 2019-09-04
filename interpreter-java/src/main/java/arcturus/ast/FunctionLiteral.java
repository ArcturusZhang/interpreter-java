package arcturus.ast;

import java.util.List;

import arcturus.ast.interfaces.Expression;
import arcturus.token.Token;

public class FunctionLiteral implements Expression {

    private Token token;
    private List<Identifier> parameters;
    private BlockStatement body;

    public FunctionLiteral(Token token) {
        this.token = token;
    }

    /**
     * @return the token
     */
    public Token getToken() {
        return token;
    }
    
    /**
     * @return the parameters
     */
    public List<Identifier> getParameters() {
        return parameters;
    }

    /**
     * @return the body
     */
    public BlockStatement getBody() {
        return body;
    }
    
    /**
     * @param parameters the parameters to set
     */
    public void setParameters(List<Identifier> parameters) {
        this.parameters = parameters;
    }

    /**
     * @param body the body to set
     */
    public void setBody(BlockStatement body) {
        this.body = body;
    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public void expression() {
    }

}