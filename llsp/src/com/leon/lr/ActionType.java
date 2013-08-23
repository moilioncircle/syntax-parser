
package com.leon.lr;

/**
 * @author : Leon
 * @since : 2013-8-22
 * @see :
 */

public enum ActionType {
    A("A"), S("S"), R("R"), G("G");
    
    private ActionType(String str) {
        this.str = str;
    }
    
    private String str;
    
    public String toString() {
        return str;
    }
}
