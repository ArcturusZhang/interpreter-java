package arcturus.parser.interfaces;

import arcturus.ast.interfaces.Expression;

@FunctionalInterface
public interface PrefixParseFunction {
    Expression parse();
}