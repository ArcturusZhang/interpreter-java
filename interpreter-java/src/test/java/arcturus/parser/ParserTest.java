package arcturus.parser;

import org.junit.Assert;
import org.junit.Test;

import arcturus.ast.LetStatement;
import arcturus.ast.Program;
import arcturus.ast.ReturnStatement;
import arcturus.lexer.Lexer;

public class ParserTest {
    @Test
    public void testLetStatement() {
        var item = new Item("let x = 5;", "x", 5);
        letStatementTestCore(item);
    }

    private void letStatementTestCore(Item item) {
        var program = parseProgram(item.input);
        Assert.assertEquals(1, program.getStatements().size());
        var statement = program.getStatements().get(0);
        Assert.assertEquals("let", statement.tokenLiteral());
        Assert.assertEquals(LetStatement.class, statement.getClass());
        var let = (LetStatement) statement;
        Assert.assertEquals(item.expectedIdentifier, let.getName().tokenLiteral());
    }

    @Test
    public void testReturnStatement() {
        var item = new Item("return y;", "return", "y");
        returnStatementTestCore(item);
    }

    private void returnStatementTestCore(Item item) {
        var program = parseProgram(item.input);
        Assert.assertEquals(1, program.getStatements().size());
        var statement = program.getStatements().get(0);
        Assert.assertEquals(item.expectedIdentifier, statement.tokenLiteral());
        Assert.assertEquals(ReturnStatement.class, statement.getClass());
        var returnStatement = (ReturnStatement) statement;
        // Assert.assertEquals(returnStatement.getReturnValue().tokenLiteral(), item.expectedValue);
    }

    private Program parseProgram(String input) {
        var parser = new Parser(new Lexer(input));
        var program = parser.parse();
        Assert.assertNotNull("Program should not be null", program);
        Assert.assertTrue("There should not be any error", parser.getErrors().isEmpty());
        return program;
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
