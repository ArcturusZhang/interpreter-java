package arcturus.parser;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

import arcturus.evaluator.env.Environment;
import arcturus.lexer.Lexer;
import arcturus.object.IntegerObject;
import arcturus.object.Object;

public class StatementTest {
    @Test
    public void testIfStatement() {
        var items = new Item[] {
            new Item("if (5 == 5) { 1; } else {2; }", new IntegerObject(new BigInteger("1"))),
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