package arcturus.parser;

import org.junit.Assert;
import org.junit.Test;

import arcturus.evaluator.env.Environment;
import arcturus.lexer.Lexer;
import arcturus.object.IntegerObject;

public class DoStatementTest {
    @Test
    public void testWhileStatement() {
        var items = new Item[] { 
            new Item(
                "let sum = 0; let i = 0; do { sum = sum + i; i = i + 1;} while (i <= 10); sum;", new IntegerObject("55"))
        };
        for (var item : items) {
            testItem(item);
        }
    }

    private void testItem(Item item) {
        var parser = new Parser(new Lexer(item.input));
        var program = parser.parse();
        Assert.assertTrue(parser.getErrors().isEmpty());
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