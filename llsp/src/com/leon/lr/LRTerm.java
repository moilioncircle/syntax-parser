
package com.leon.lr;

import com.leon.grammar.Production;

/**
 * @author : Leon
 * @since : 2013-8-13
 * @see :
 */

public class LRTerm {
    
    public LRTerm(LRCoreTerm core_item, String look_ahead) {
        this.core_item = core_item;
        this.look_ahead = look_ahead;
        this.p = core_item.p;
        this.dot = core_item.dot;
    }
    
    public LRCoreTerm core_item;
    public String     look_ahead;
    public Production p;
    public int        dot;
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((core_item == null) ? 0 : core_item.hashCode());
        result = prime * result + ((look_ahead == null) ? 0 : look_ahead.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        LRTerm other = (LRTerm) obj;
        if (core_item == null) {
            if (other.core_item != null) return false;
        }
        else if (!core_item.equals(other.core_item)) return false;
        if (look_ahead == null) {
            if (other.look_ahead != null) return false;
        }
        else if (!look_ahead.equals(other.look_ahead)) return false;
        return true;
    }
    
    @Override
    public String toString() {
        return core_item.toString() + "  look_ahead [" + look_ahead + "]";
    }
}
