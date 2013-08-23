
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
    
    public ActionItem(ActionType type, Production p) {
        this(type);
        this.p = p;
    }
    
    public ActionItem(ActionType type) {
        this.type = type;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(type.toString());
        if(p!=null){
            sb.append(":"+p);
        }
        return sb.toString();
    }
}
