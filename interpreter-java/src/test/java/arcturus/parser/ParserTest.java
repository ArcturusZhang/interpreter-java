package arcturus.parser;

import org.junit.Assert;
import org.junit.Test;

import arcturus.ast.LetStatement;
import arcturus.ast.interfaces.Statement;
import arcturus.lexer.Lexer;

public class ParserTest {
    @Test
    public void testLetStatement() {
        // var items = new Item[]{
        // new Item("let x = 5;", "x", 5),
        // new Item("let z = 1.3;", "z", 1.3),
        // new Item("let foobar = yl;", "foobar", "yl"),
        // };
        // for (var item : items) {
        // var parser = new Parser(new Lexer(item.input));
        // var program = parser.parse();
        // if (program == null) {

        // }
        // }
        var item = new Item("let x = 5;", "x", 5);
        letStatementTestCore(item);
    }

    private void letStatementTestCore(Item item) {
        var parser = new Parser(new Lexer(item.input));
        var program = parser.parse();
        Assert.assertNotNull("Program should not be null", program);
        Assert.assertEquals(1, program.getStatements().size());
        var statement = program.getStatements().get(0);
        assertLetStatement(item, statement);
    }

    private void assertLetStatement(Item item, Statement statement) {
        Assert.assertEquals("let", statement.tokenLiteral());
        Assert.assertEquals(LetStatement.class, statement.getClass());
        var let = (LetStatement) statement;
        Assert.assertEquals(item.expectedIdentifier, let.getName().tokenLiteral());
        Assert.assertEquals(item.expectedValue, let.getName().getValue());
    }

    static class Item {
        String input;
        String expectedIdentifier;
        Object expectedValue;

        public Item(String input, String expectedIdentifier, Object expectedValue) {
            this.input = input;
            this.expectedIdentifier = expectedIdentifier;
            this.expectedValue = expectedValue;
        }

        @Override
        public String toString() {
            return "{" + " input='" + input + "'" + ", expectedIdentifier='" + expectedIdentifier + "'"
                    + ", expectedValue='" + expectedValue + "'" + "}";
        }
    }
}
