
package com.leon.util;

/**
 * @author : Leon
 * @since : 2013-9-9
 * @see :
 */

public class FakeSymbol implements ISymbol {
    
    private String type;
    
    public FakeSymbol(String type) {
        this.type = type;
    }
    
    @Override
    public int get_line() {
        return 0;
    }
    
    @Override
    public int get_column() {
        return 0;
    }
    
    @Override
    public String get_type_name() {
        return type;
    }
    
    @Override
    public Object get_value() {
        return null;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("type:" + type);
        if (get_value() != null) {
            sb.append(",value:" + get_value().toString());
        }
        return sb.toString();
    }
    
}
