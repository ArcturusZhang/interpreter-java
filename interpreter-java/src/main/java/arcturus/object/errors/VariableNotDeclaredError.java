package arcturus.object.errors;

import arcturus.ast.Identifier;

public class VariableNotDeclaredError extends ErrorObject {

    public VariableNotDeclaredError(Identifier variable) {
        super(String.format("Error: variable %s used before declaration", variable));
    }

}