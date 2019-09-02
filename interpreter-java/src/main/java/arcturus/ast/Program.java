package arcturus.ast;

import java.util.ArrayList;
import java.util.List;

import arcturus.ast.interfaces.Node;
import arcturus.ast.interfaces.Statement;

public class Program implements Node {

    private List<Statement> statements;

    public Program() {
        statements = new ArrayList<>();
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public boolean addStatement(Statement s) {
        return statements.add(s);
    }

    @Override
    public String tokenLiteral() {
        if (statements.size() > 0) {
            return statements.get(0).tokenLiteral();
        }
        return "";
    }

}