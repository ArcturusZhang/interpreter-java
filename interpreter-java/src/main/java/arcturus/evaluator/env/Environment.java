package arcturus.evaluator.env;

import java.util.HashMap;
import java.util.Map;

import arcturus.object.Object;

public class Environment {
    private Environment parent;
    private Map<String, Object> symbolTable;
    private Object current;

    public Environment(Environment parent) {
        this.parent = parent;
        symbolTable = new HashMap<>();
    }

    /**
     * @return the current
     */
    public Object getCurrent() {
        return current;
    }

    /**
     * @param current the current to set
     */
    public void setCurrent(Object current) {
        this.current = current;
    }

    /**
     * @return the parent
     */
    public Environment getParent() {
        return parent;
    }
    
    public Object get(String identifier) {
        if (symbolTable.containsKey(identifier)) return symbolTable.get(identifier);
        if (parent!= null) return parent.get(identifier);
        return null;
    }

    public boolean contains(String identifier) {
        return symbolTable.containsKey(identifier);
    }

    public Object put(String identifier, Object value) {
        return symbolTable.put(identifier, value);
    }
}