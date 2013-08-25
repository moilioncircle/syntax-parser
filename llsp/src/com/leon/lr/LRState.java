
package com.leon.lr;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author : Leon
 * @since : 2013-8-13
 * @see :
 */

public class LRState {
    
    public Set<LRTerm> terms = new HashSet<LRTerm>();
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((terms == null) ? 0 : terms.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        LRState other = (LRState) obj;
        if (terms == null) {
            if (other.terms != null) return false;
        }
        else if (!is_equals(terms, other.terms)) return false;
        return true;
    }
    
    private boolean is_equals(Set<LRTerm> t1, Set<LRTerm> t2) {
        if (t1 == null && t2 == null) {
            return true;
        }
        else if (t1 != null && t2 != null) {
            if (t1.size() == t2.size()) {
                boolean is_contains = true;
                for (Iterator<LRTerm> iterator = t1.iterator(); iterator.hasNext();) {
                    LRTerm t1_term = iterator.next();
                    if (!t2.contains(t1_term)) {
                        is_contains = false;
                        break;
                    }
                }
                if (is_contains) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Iterator<LRTerm> iterator = terms.iterator(); iterator.hasNext();) {
            LRTerm term = iterator.next();
            sb.append(term.toString() + "\n");
        }
        return sb.toString();
    }
    
}
