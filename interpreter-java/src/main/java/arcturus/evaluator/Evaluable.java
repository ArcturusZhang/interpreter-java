package arcturus.evaluator;

import arcturus.evaluator.env.Environment;
import arcturus.object.Object;

public interface Evaluable {
    Object evaluate(Environment env);
}