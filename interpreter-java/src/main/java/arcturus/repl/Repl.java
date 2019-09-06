package arcturus.repl;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import arcturus.evaluator.Evaluator;
import arcturus.lexer.Lexer;
import arcturus.parser.Parser;
import arcturus.parser.errors.ParseError;

public class Repl {
    private static final String PROMPT = ">> ";
    public static int decimalCount = 10;

    public static void Start(InputStream in, PrintStream out) {
        var scanner = new Scanner(in);
        while (true) {
            out.print(PROMPT);
            var line = scanner.nextLine();
            if (line == null)
                break;
            var parser = new Parser(new Lexer(line));
            var program = parser.parse();
            if (!checkParseErrors(parser.getErrors(), out)) {
                var evaluator = new Evaluator();
                var result = evaluator.eval(program);
                out.println(result);
            }
        }
        scanner.close();
    }

    private static boolean checkParseErrors(List<ParseError> errors, PrintStream out) {
        int count = errors.size();
        if (count == 0) return false;
        out.println(String.format("There are %d error(s) in parsing", count));
        for (var e : errors) {
            out.println(e.errorMessage());
        }
        return true;
    }
}