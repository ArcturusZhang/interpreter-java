package arcturus.parser;

import java.util.HashMap;
import java.util.Map;

import arcturus.token.Token.Type;

public enum Precedence {
    LOWEST,
    EQUALS,
    LESSGREATER,
    LOGICAL,
    SUM,
    PRODUCT,
    PREFIX,
    CALL,
    INDEX;

    public boolean lessThan(Precedence other) {
        return this.compareTo(other) < 0;
    }
    public static Map<Type, Precedence> precedenceMap = new HashMap<>();
    static {
        precedenceMap.put(Type.EQ, EQUALS);
        precedenceMap.put(Type.NE, EQUALS);
        precedenceMap.put(Type.LT, LESSGREATER);
        precedenceMap.put(Type.GT, LESSGREATER);
        precedenceMap.put(Type.LE, LESSGREATER);
        precedenceMap.put(Type.GE, LESSGREATER);
        precedenceMap.put(Type.ADD, LOGICAL);
        precedenceMap.put(Type.OR, LOGICAL);
        precedenceMap.put(Type.PLUS, SUM);
        precedenceMap.put(Type.MINUS, SUM);
        precedenceMap.put(Type.ASTERISK, PRODUCT);
        precedenceMap.put(Type.SLASH, PRODUCT);
        precedenceMap.put(Type.LPAREN, CALL);
        precedenceMap.put(Type.LBRACKET, INDEX);
    }

    public static Precedence get(Type type) {
        if (precedenceMap.containsKey(type)) return precedenceMap.get(type);
        return LOWEST;
    }
}