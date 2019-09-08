package arcturus.evaluator;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

import arcturus.evaluator.env.Environment;
import arcturus.lexer.Lexer;
import arcturus.object.BooleanObject;
import arcturus.object.DecimalObject;
import arcturus.object.IntegerObject;
import arcturus.object.Object.Type;
import arcturus.object.errors.TypeMismatchError;
import arcturus.parser.Parser;

public class EvaluatorTest {
    @Test
    public void testSingleStatementErrors() {
        var items = new Item[] {
            new Item("5+true;", new TypeMismatchError("+", Type.INTEGER, Type.BOOLEAN)),
            new Item("-true", new TypeMismatchError("-", Type.BOOLEAN)),
            new Item("true*false", new TypeMismatchError("*", Type.BOOLEAN, Type.BOOLEAN)),
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
        var rootEnv = new Environment(null);
        var result = program.evaluate(rootEnv);
        Assert.assertEquals(item.expected, result);
    }

    @Test
    public void testCalculation() {
        var items = new Item[] {
            new Item("let x = 5 * 5;", new IntegerObject(new BigInteger("25"))),
            new Item("let x = 3.5 * 4;", new DecimalObject(new BigDecimal("14")))
        };
        for (var item : items) {
            testOne(item);
        }
    }

    @Test
    public void testVaraibleBinding() {
        var items = new Item[] {
            new Item("let x = 10; x;", new IntegerObject("10")),
            new Item("let x = 6; let y = x * 9.2;", new DecimalObject("55.2")),
            new Item("let x = 10; x + +9 - -2.3;", new DecimalObject("21.3")),
            new Item("let x = 2.3 * -10; 8 / (1 / x);", new DecimalObject("-183.9999998712"))
        };
        for (var item : items) {
            testOne(item);
        }
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