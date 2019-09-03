package arcturus.repl;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import arcturus.lexer.Lexer;
import arcturus.token.Token.Type;

public class Repl {
    private static final String PROMPT = ">> ";

    public static void Start(InputStream in, PrintStream out) {
        var scanner = new Scanner(in);
        for (;;) {
            out.print(PROMPT);
            var line = scanner.nextLine();
            if (line == null) break;
            var lexer = new Lexer(line);
            for (var token = lexer.nextToken(); token.getType() != Type.EOF; token = lexer.nextToken()) {
                out.println(token);
            }
        }
        scanner.close();
    }
}