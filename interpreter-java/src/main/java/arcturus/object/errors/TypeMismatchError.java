package arcturus.object.errors;

public class TypeMismatchError extends ErrorObject {

    public TypeMismatchError(String operator, Type right) {
        super(String.format("Error: type mismatch -- %s %s", operator, right));
    }

    public TypeMismatchError(String operator, Type left, Type right) {
        super(String.format("Error: type mismatch -- %s %s %s", left, operator, right));
    }

}