
package com.leon.lr;

import com.leon.grammar.Production;

/**
 * @author : Leon
 * @since : 2013-8-23
 * @see :
 */

public class ActionItem {
    
    public ActionType type;
    public Production p;
    public String     symbol;
    
    public ActionItem(ActionType type, Production p) {
        this(type, (String) null);
        this.p = p;
    }
    
    public ActionItem(ActionType type, String symbol) {
        this.type = type;
        this.symbol = symbol;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(type.toString());
        if (symbol != null) {
            sb.append(":" + symbol);
        }
        if (p != null) {
            sb.append(":" + p);
        }
        return sb.toString();
    }
}
