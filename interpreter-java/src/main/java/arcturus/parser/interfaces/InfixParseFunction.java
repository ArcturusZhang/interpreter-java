package arcturus.parser.interfaces;

import arcturus.ast.interfaces.Expression;

@FunctionalInterface
public interface InfixParseFunction {
    Expression parse(Expression left);
}