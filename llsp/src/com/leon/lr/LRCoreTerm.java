
package com.leon.lr;

import com.leon.grammar.Production;

/**
 * @author : Leon
 * @since : 2013-9-2
 * @see :
 */

public class LRCoreTerm {
    
    public LRCoreTerm(Production p, int dot) {
        this.p = p;
        this.dot = dot;
    }
    
    public Production p;
    public int        dot;
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + dot;
        result = prime * result + ((p == null) ? 0 : p.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        LRCoreTerm other = (LRCoreTerm) obj;
        if (dot != other.dot) return false;
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
        
        return "LRCoreTerm [p=" + p.lhs + "->" + sb.toString() + "]";
    }
    
}
