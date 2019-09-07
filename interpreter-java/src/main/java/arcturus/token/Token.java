package arcturus.token;

import java.util.HashMap;
import java.util.Map;

public class Token {
    private Type type;
    private String literal;

    public Token(Type type, String literal) {
        this.type = type;
        this.literal = literal;
    }

    public Token(Type type, char ch) {
        this.type = type;
        this.literal = String.valueOf(ch);
    }

    public String getLiteral() {
        return literal;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("Token{%s, %s}", this.type, this.literal);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        var other = (Token) obj;
        return this.type.equals(other.type) && this.literal.equals(other.literal);
    }

    @Override
    public int hashCode() {
        return this.literal.hashCode() * 31 + this.type.hashCode();
    }

    private static final Map<String, Type> keywords = new HashMap<>();

    static {
        keywords.put("func", Type.FUNCTION);
        keywords.put("let", Type.LET);
        keywords.put("true", Type.TRUE);
        keywords.put("false", Type.FALSE);
        keywords.put("if", Type.IF);
        keywords.put("else", Type.ELSE);
        keywords.put("for", Type.FOR);
        keywords.put("do", Type.DO);
        keywords.put("while", Type.WHILE);
        keywords.put("break", Type.BREAK);
        keywords.put("continue", Type.CONTINUE);
        keywords.put("return", Type.RETURN);
    }

    public static Type LookupKeyword(String keyword) {
        if (keywords.containsKey(keyword)) {
            return keywords.get(keyword);
        }
        return Type.IDENTIFIER;
    }

    public static enum Type {
        ILLEGAL,
        EOF,
        IDENTIFIER,
        INT,
        DECIMAL,
        STRING, 
        ASSIGN, // =
        PLUS,   // +
        MINUS,  // -
        ASTERISK,   // *
        SLASH,  // \
        BANG,   // !
        SEMICOLON,  // ;
        COMMA,  // ,
        DOT,    // .
        LT,     // <
        GT,     // >
        EQ,     // ==
        NE,     // !=
        LE,     // <=
        GE,     // >=
        LPAREN, // (
        RPAREN, // )
        LBRACE, // {
        RBRACE, // }
        LBRACKET,   // [
        RBRACKET,   // ]
        QUOTE,  // "
        SINGLEQUOTE,    // '
        ADD_BITWISE,    // &
        ADD,   // &&
        OR_BITWISE, // |
        OR,     // ||
        FUNCTION,   // func
        LET,    // let
        TRUE,   // true
        FALSE,  // false
        IF,     // if
        ELSE,   // else
        FOR,    // for
        DO,     // do
        WHILE,  // while
        BREAK,  // break
        CONTINUE,   // continue
        RETURN, // return
    }
}