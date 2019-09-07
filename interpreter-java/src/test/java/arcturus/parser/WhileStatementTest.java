package arcturus.parser;

import org.junit.Assert;
import org.junit.Test;

import arcturus.evaluator.env.Environment;
import arcturus.lexer.Lexer;
import arcturus.object.IntegerObject;
import arcturus.object.Object;

public class WhileStatementTest {
    @Test
    public void testWhileStatement() {
        var items = new Item[] {
            new Item("let sum = 0; let i = 0; while (i <= 10) { sum = sum + i; i = i + 1;} sum;", new IntegerObject("55"))
        };
        for (var item : items) {
            testItem(item);
        }
    }

    private void testItem(Item item) {
        var parser = new Parser(new Lexer(item.input));
        var program = parser.parse();
        var result = program.evaluate(new Environment(null));
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