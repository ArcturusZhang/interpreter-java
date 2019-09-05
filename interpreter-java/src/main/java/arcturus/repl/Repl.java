package arcturus.repl;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import arcturus.lexer.Lexer;
import arcturus.parser.Parser;

public class Repl {
    private static final String PROMPT = ">> ";

    public static void Start(InputStream in, PrintStream out) {
        var scanner = new Scanner(in);
        while (true) {
            out.print(PROMPT);
            var line = scanner.nextLine();
            if (line == null)
                break;
            var parser = new Parser(new Lexer(line));
            var program = parser.parse();
            for (var stmt : program.getStatements()) {
                System.out.println(stmt);
            }
        }
        scanner.close();
    }
}