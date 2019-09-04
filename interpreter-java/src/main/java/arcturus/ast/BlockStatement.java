package arcturus.ast;

import java.util.ArrayList;
import java.util.List;

import arcturus.ast.interfaces.Statement;
import arcturus.token.Token;

public class BlockStatement implements Statement {

    private Token token;
    private List<Statement> statements;

    public BlockStatement(Token token) {
        this.token = token;
        this.statements = new ArrayList<>();
    }

    /**
     * @return the token
     */
    public Token getToken() {
        return token;
    }

    /**
     * @return the statements
     */
    public List<Statement> getStatements() {
        return statements;
    }

    public boolean addStatement(Statement e) {
        return statements.add(e);
    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public void statement() {
    }

}