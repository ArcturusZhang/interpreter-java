package arcturus.lexer;

import org.junit.Test;

import arcturus.token.Token;
import arcturus.token.Token.Type;

import org.junit.Assert;

public class LexerTest {
    @Test
    public void testTokens0() {
        var input = "let x = 4.5;";
        var results = new Token[] { new Token(Type.LET, "let"), new Token(Type.IDENTIFIER, "x"),
                new Token(Type.ASSIGN, "="), new Token(Type.DECIMAL, "4.5"), new Token(Type.SEMICOLON, ";"),
                new Token(Type.EOF, "") };
        testCore(input, results);
    }

    @Test
    public void testTokens1() {
        var input = "let five = 5;\n\tlet a1 = 10;\n\tlet add = func(x, y) { x + y;\n\t};";
        var results = new Token[] { new Token(Type.LET, "let"), new Token(Type.IDENTIFIER, "five"),
                new Token(Type.ASSIGN, "="), new Token(Type.INT, "5"), new Token(Type.SEMICOLON, ";"),
                new Token(Type.LET, "let"), new Token(Type.IDENTIFIER, "a1"), new Token(Type.ASSIGN, "="),
                new Token(Type.INT, "10"), new Token(Type.SEMICOLON, ";"), new Token(Type.LET, "let"),
                new Token(Type.IDENTIFIER, "add"), new Token(Type.ASSIGN, "="), new Token(Type.FUNCTION, "func"),
                new Token(Type.LPAREN, "("), new Token(Type.IDENTIFIER, "x"), new Token(Type.COMMA, ","),
                new Token(Type.IDENTIFIER, "y"), new Token(Type.RPAREN, ")"), new Token(Type.LBRACE, "{"),
                new Token(Type.IDENTIFIER, "x"), new Token(Type.PLUS, "+"), new Token(Type.IDENTIFIER, "y"),
                new Token(Type.SEMICOLON, ";"), new Token(Type.RBRACE, "}"), new Token(Type.SEMICOLON, ";"),
                new Token(Type.EOF, ""), };
        testCore(input, results);
    }

    @Test
    public void testTokens2() {
        var input = "let f = func(x, y) {\n\tif (x == y) return 0;\n\tif (x <= y) return -1;\n\treturn 1;\n};";
        var results = new Token[] { new Token(Type.LET, "let"), new Token(Type.IDENTIFIER, "f"),
                new Token(Type.ASSIGN, "="), new Token(Type.FUNCTION, "func"), new Token(Type.LPAREN, "("),
                new Token(Type.IDENTIFIER, "x"), new Token(Type.COMMA, ","), new Token(Type.IDENTIFIER, "y"),
                new Token(Type.RPAREN, ")"), new Token(Type.LBRACE, "{"), new Token(Type.IF, "if"),
                new Token(Type.LPAREN, "("), new Token(Type.IDENTIFIER, "x"), new Token(Type.EQ, "=="),
                new Token(Type.IDENTIFIER, "y"), new Token(Type.RPAREN, ")"), new Token(Type.RETURN, "return"),
                new Token(Type.INT, "0"), new Token(Type.SEMICOLON, ";"), new Token(Type.IF, "if"),
                new Token(Type.LPAREN, "("), new Token(Type.IDENTIFIER, "x"), new Token(Type.LE, "<="),
                new Token(Type.IDENTIFIER, "y"), new Token(Type.RPAREN, ")"), new Token(Type.RETURN, "return"),
                new Token(Type.MINUS, "-"), new Token(Type.INT, "1"), new Token(Type.SEMICOLON, ";"),
                new Token(Type.RETURN, "return"), new Token(Type.INT, "1"), new Token(Type.SEMICOLON, ";"),
                new Token(Type.RBRACE, "}"), new Token(Type.SEMICOLON, ";"), new Token(Type.EOF, ""), };
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