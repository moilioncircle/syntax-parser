
package com.leon.grammar;

import java.util.Arrays;

/**
 * @author : Leon
 * @since : 2013-8-12
 * @see :
 */

public class Production {
    
    public Production(String lhs, String[] rhs, String semantic_action) {
        this(lhs, rhs);
        this.semantic_action = semantic_action;
    }
    
    public Production(String lhs, String[] rhs, Prec prec, String semantic_action) {
        this(lhs, rhs, semantic_action);
        this.prec = prec;
    }
    
    public Production(String lhs, String[] rhs, Prec prec) {
        this(lhs, rhs);
        this.prec = prec;
    }
    
    public Production(String lhs, String[] rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
    
    public boolean has_prec() {
        return prec != null && prec.perc_symbol != null;
    }
    
    public String[] rhs;
    public String   lhs;
    public Prec     prec;
    public String   semantic_action;
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lhs == null) ? 0 : lhs.hashCode());
        result = prime * result + Arrays.hashCode(rhs);
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Production other = (Production) obj;
        if (lhs == null) {
            if (other.lhs != null) return false;
        }
        else if (!lhs.equals(other.lhs)) return false;
        if (!Arrays.equals(rhs, other.rhs)) return false;
        return true;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.lhs + "->");
        for (int i = 0; i < this.rhs.length; i++) {
            sb.append(this.rhs[i] + " ");
        }
        return sb.toString();
    }
    
}
