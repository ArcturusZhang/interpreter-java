package arcturus.lexer;

import arcturus.lexer.exception.LexerException;
import arcturus.token.Token;
import arcturus.token.Token.Type;

public class Lexer {
    private int position;
    private int readPosition;
    private int line;
    private int col;
    private char ch;
    private String input;

    public Lexer(String input) {
        this.input = input;
        this.line = 1;
        readChar();
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }

    public char getCh() {
        return ch;
    }

    private void readChar() {
        if (this.readPosition >= input.length()) {
            this.ch = (char) 0;
        } else {
            this.ch = input.charAt(this.readPosition);
        }
        this.position = this.readPosition;
        this.readPosition++;
        this.col++;
        if (this.ch == '\n') {
            this.line++;
            this.col = 0;
        }
    }

    private char peekChar() {
        if (this.readPosition >= input.length()) {
            return (char) 0;
        }
        return input.charAt(this.readPosition);
    }

    public Token nextToken() {
        Token token;
        skipWhitespaces();
        switch (this.ch) {
        case '=':
            token = readEqual();
            break;
        case '<':
            token = readLessThan();
            break;
        case '>':
            token = readGreaterThan();
            break;
        case '(':
            token = new Token(Type.LPAREN, this.ch);
            break;
        case ')':
            token = new Token(Type.RPAREN, this.ch);
            break;
        case '[':
            token = new Token(Type.LBRACKET, this.ch);
            break;
        case ']':
            token = new Token(Type.RBRACKET, this.ch);
            break;
        case '{':
            token = new Token(Type.LBRACE, this.ch);
            break;
        case '}':
            token = new Token(Type.RBRACE, this.ch);
            break;
        case '+':
            token = new Token(Type.PLUS, this.ch);
            break;
        case '-':
            token = new Token(Type.MINUS, this.ch);
            break;
        case '*':
            token = new Token(Type.ASTERISK, this.ch);
            break;
        case '/':
            token = new Token(Type.SLASH, this.ch);
            break;
        case '!':
            token = readBang();
            break;
        case ';':
            token = new Token(Type.SEMICOLON, this.ch);
            break;
        case ',':
            token = new Token(Type.COMMA, this.ch);
            break;
        case '.':
            return readFloat();
        case '"':
            token = new Token(Type.STRING, readString());
            break;
        case '\'':
            token = new Token(Type.SINGLEQUOTE, this.ch);
            break;
        case '&':
            token = readAnd();
            break;
        case '|':
            token = readOr();
            break;
        case (char) 0:
            token = new Token(Type.EOF, "");
            break;
        default:
            if (isIdentifierStart(this.ch)) {
                var literal = readIdentifier();
                var type = Token.LookupKeyword(literal);
                return new Token(type, literal);
            } else if (isDigit(this.ch)) {
                return readDecimal();
            } else {
                token = new Token(Type.ILLEGAL, this.ch);
            }
            break;
        }
        readChar();
        return token;
    }

    private void skipWhitespaces() {
        while (this.ch != (char) 0 && isWhitespace(this.ch)) {
            readChar();
        }
    }

    private String readUntilWhitespace() {
        var start = this.position;
        while (this.ch != (char) 0 && !isWhitespace(this.ch)) {
            readChar();
        }
        return input.substring(start, this.position);
    }

    private static boolean isIdentifierStart(char ch) {
        return Character.isJavaIdentifierStart(ch);
    }

    private static boolean isIdentifierPart(char ch) {
        return Character.isJavaIdentifierPart(ch);
    }

    private static boolean isDigit(char ch) {
        return Character.isDigit(ch);
    }

    private static boolean isWhitespace(char ch) {
        return Character.isWhitespace(ch);
    }

    private static boolean isEmpty(char ch) {
        return ch == (char) 0;
    }

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private static boolean isComparison(char ch) {
        return ch == '=' || ch == '!' || ch == '>' || ch == '<';
    }

    private static boolean isCompound(char ch) {
        return ch == ',' || ch == ':' || ch == '"' || ch == ';';
    }

    private static boolean isDelimiter(char ch) {
        return isParen(ch) || isBrace(ch) || isBracket(ch);
    }

    private static boolean isParen(char ch) {
        return ch == '(' || ch == ')';
    }

    private static boolean isBracket(char ch) {
        return ch == '[' || ch == ']';
    }

    private static boolean isBrace(char ch) {
        return ch == '{' || ch == '}';
    }

    private static boolean isDelimiterOfNumber(char ch) {
        return isEmpty(ch) || isWhitespace(ch) || isOperator(ch) || isComparison(ch) || isCompound(ch)
                || isDelimiter(ch);
    }

    private Token readEqual() {
        if (this.ch != '=')
            throw new LexerException("This should not happen");
        if (peekChar() == '=') {
            readChar();
            return new Token(Type.EQ, "==");
        }
        return new Token(Type.ASSIGN, this.ch);
    }

    private Token readLessThan() {
        if (this.ch != '<')
            throw new LexerException("This should not happen");
        if (peekChar() == '=') {
            readChar();
            return new Token(Type.LE, "<=");
        }
        return new Token(Type.LT, this.ch);
    }

    private Token readGreaterThan() {
        if (this.ch != '>')
            throw new LexerException("This should not happen");
        if (peekChar() == '=') {
            readChar();
            return new Token(Type.GE, ">=");
        }
        return new Token(Type.GT, this.ch);
    }

    private Token readBang() {
        if (this.ch != '!')
            throw new LexerException("This should not happen");
        if (peekChar() == '=') {
            readChar();
            return new Token(Type.NE, "!=");
        }
        return new Token(Type.BANG, this.ch);
    }

    private Token readAnd() {
        if (this.ch != '&')
        throw new LexerException("This should not happen");
        if (peekChar() == '&') {
            readChar();
            return new Token(Type.ADD, "&&");
        }
        return new Token(Type.ADD_BITWISE, this.ch);
    }

    private Token readOr() {
        if (this.ch != '|')
        throw new LexerException("This should not happen");
        if (peekChar() == '|') {
            readChar();
            return new Token(Type.OR, "||");
        }
        return new Token(Type.OR_BITWISE, this.ch);
    }

    private String readIdentifier() {
        var start = this.position;
        while (this.ch != (char) 0 && isIdentifierPart(this.ch)) {
            readChar();
        }
        return input.substring(start, this.position);
    }

    private Token readDecimal() {
        var integer = readNumber();
        if (this.ch == '.') {
            readChar();
            var fraction = readNumber();
            if (isDelimiterOfNumber(this.ch)) {
                return new Token(Type.DECIMAL, integer + "." + fraction);
            } else {
                var illegalPart = readUntilWhitespace();
                return new Token(Type.ILLEGAL, integer + "." + fraction + illegalPart);
            }
        } else if (isDelimiterOfNumber(this.ch)) {
            return new Token(Type.INT, integer);
        } else {
            var illegalPart = readUntilWhitespace();
            return new Token(Type.ILLEGAL, integer + illegalPart);
        }
    }

    private Token readFloat() {
        if (this.ch != '.')
            throw new LexerException("This should not happen");
        readChar();
        var fraction = readNumber();
        if (fraction.length() == 0) {
            return new Token(Type.DOT, ".");
        } else {
            if (isDelimiterOfNumber(this.ch)) {
                return new Token(Type.DECIMAL, "0." + fraction);
            } else {
                var illegalPart = readUntilWhitespace();
                return new Token(Type.ILLEGAL, "." + fraction + illegalPart);
            }
        }
    }

    private String readNumber() {
        var start = this.position;
        while (isDigit(this.ch)) {
            readChar();
        }
        return input.substring(start, this.position);
    }

    private String readString() {
        var start = this.position + 1;
        while (true) {
            readChar(); // skip first "
            if (this.ch == '"') break;
        }
        return input.substring(start, this.position);
    }
}