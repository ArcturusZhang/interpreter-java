package arcturus.lexer;

import org.junit.Test;

import arcturus.token.Token;
import arcturus.token.Token.Type;

import org.junit.Assert;

public class LexerTest {
    @Test
    public void tokensShouldIdentical() {
        var input = "let x = 4.5;";
        var results = new Token[] {
            new Token(Type.LET, "let"),
            new Token(Type.IDENTIFIER, "x"),
            new Token(Type.ASSIGN, "="),
            new Token(Type.DECIMAL, "4.5"),
            new Token(Type.SEMICOLON, ";"),
            new Token(Type.EOF, "")
        };
        testCore(input, results);
    }

    private static void testCore(String input, Token[] results) {
        var lexer = new Lexer(input);
        for (var expected : results) {
            var token = lexer.nextToken();
            Assert.assertEquals(expected, token);
        }
    }
}