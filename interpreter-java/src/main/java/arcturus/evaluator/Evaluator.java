package arcturus.evaluator;

import arcturus.object.Object;

public class Evaluator {
    public Evaluator() {}

    public Object eval(Evaluable node) {
        return node.evaluate();
    }
}