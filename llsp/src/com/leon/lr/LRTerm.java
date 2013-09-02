
package com.leon.lr;

import com.leon.grammar.Production;

/**
 * @author : Leon
 * @since : 2013-8-13
 * @see :
 */

public class LRTerm {
    
    public LRTerm(Production p, int dot, String look_ahead) {
        this.p = p;
        this.dot = dot;
        this.look_ahead = look_ahead;
    }
    
    public Production p;
    public int        dot;
    public String     look_ahead;
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + dot;
        result = prime * result + ((look_ahead == null) ? 0 : look_ahead.hashCode());
        result = prime * result + ((p == null) ? 0 : p.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        LRTerm other = (LRTerm) obj;
        if (dot != other.dot) return false;
        if (look_ahead == null) {
            if (other.look_ahead != null) return false;
        }
        else if (!look_ahead.equals(other.look_ahead)) return false;
        if (p == null) {
            if (other.p != null) return false;
        }
        else if (!p.equals(other.p)) return false;
        return true;
    }
    
    @Override
    public String toString() {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while (i <= p.rhs.length) {
            if (i == dot) {
                if (sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                sb.append(".");
            }
            if (i < p.rhs.length) {
                sb.append(p.rhs[i] + " ");
            }
            i++;
        }
        
        return "LRTerm [p=" + p.lhs + "->" + sb.toString() + "  look_ahead=" + look_ahead + "]";
    }
}
