package arcturus.evaluator;

import org.junit.Assert;
import org.junit.Test;

import arcturus.lexer.Lexer;
import arcturus.object.BooleanObject;
import arcturus.object.TypeMismatchError;
import arcturus.object.Object.Type;
import arcturus.parser.Parser;

public class EvaluatorTest {
    @Test
    public void testSingleStatementErrors() {
        var items = new Item[] {
            // new Item("5+true;", new TypeMismatchError("+", Type.INTEGER, Type.BOOLEAN)),
            // new Item("-true", new TypeMismatchError("-", Type.BOOLEAN)),
            // new Item("true*false", new TypeMismatchError("*", Type.BOOLEAN, Type.BOOLEAN)),
            new Item("true&&false", BooleanObject.FALSE),
        };
        for (var item : items) {
            testOne(item);
        }
    }

    @Test
    public void testMultipleStatementErrors() {
        var items = new Item[] {
            new Item("5+true; 5;", new TypeMismatchError("+", Type.INTEGER, Type.BOOLEAN)),
        };
        for (var item : items) {
            testOne(item);
        }
    }

    private void testOne(Item item) {
        var parser = new Parser(new Lexer(item.input));
        var program = parser.parse();
        var evaluator = new Evaluator();
        var result = evaluator.eval(program);
        Assert.assertEquals(item.expected, result);
    }

    private static class Item {
        String input;
        Object expected;
        Item(String input, Object expected) {
            this.input = input;
            this.expected = expected;
        }
    }
}