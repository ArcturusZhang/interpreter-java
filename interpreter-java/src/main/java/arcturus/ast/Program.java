package arcturus.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import arcturus.ast.interfaces.Node;
import arcturus.ast.interfaces.Statement;
import arcturus.evaluator.Evaluable;
import arcturus.object.NullObject;
import arcturus.object.Object;

public class Program implements Node, Evaluable {

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

    @Override
    public String toString() {
        return String.join("\n", statements.stream().map(Statement::toString).collect(Collectors.toList()));
    }

    @Override
    public Object evaluate() {
        Object result = NullObject.NULL;
        if (statements.size() > 0) {
            for (var stmt : statements) {
                result = stmt.evaluate();
            }
            return result;
        } 
        return result;
    }

}