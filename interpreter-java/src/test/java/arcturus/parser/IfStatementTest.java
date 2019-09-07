package arcturus.parser;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

import arcturus.evaluator.env.Environment;
import arcturus.lexer.Lexer;
import arcturus.object.IntegerObject;
import arcturus.object.NullObject;
import arcturus.object.Object;

public class IfStatementTest {
    @Test
    public void testIfStatement() {
        var items = new Item[] {
            new Item("if (5 == 5) { 1; } else {2; }", new IntegerObject("1")),
            new Item("if (5 > 4) {1; }", new IntegerObject("1")),
            new Item("if (4 > 4) {1; } 2;", new IntegerObject("2")),
            new Item("if (4 > 4) {1; }", NullObject.NULL)
        };
        for(var item : items) {
            testItem(item);
        }
    }

    private void testItem(Item item) {
        var parser = new Parser(new Lexer(item.input));
        var program = parser.parse();
        var root = new Environment(null);
        var result = program.evaluate(root);
        Assert.assertEquals(item.expect, result);
    }

    private static class Item {
        String input;
        Object expect;
        Item(String input, Object expect) {
            this.input = input;
            this.expect = expect;
        }
    }
}