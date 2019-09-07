package arcturus.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import arcturus.ast.interfaces.Statement;
import arcturus.evaluator.env.Environment;
import arcturus.object.NullObject;
import arcturus.object.Object;
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
    public String toString() {
        var list = statements.stream().map(Statement::toString).collect(Collectors.toList());
        return String.format(PATTERN, String.join("\n", list));
    }

    private static final String PATTERN = "{\n%s\n} ";

    @Override
    public Object evaluate(Environment env) {
        Object result = NullObject.NULL;
        if (statements.isEmpty()) return result;
        for (var stmt : statements) {
            result = stmt.evaluate(env);
        }
        return result;
    }

}