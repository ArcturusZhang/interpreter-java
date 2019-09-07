package arcturus.object.errors;

import arcturus.ast.Identifier;

public class VariableExistsError extends ErrorObject {

    public VariableExistsError(Identifier variable) {
        super(String.format("Error: variable %s already exists", variable));
    }

}