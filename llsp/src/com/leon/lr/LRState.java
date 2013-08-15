
package com.leon.lr;

import static com.leon.util.Utils.deep_equals;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : Leon
 * @since : 2013-8-13
 * @see :
 */

public class LRState {
    
    Set<LRTerm> terms = new HashSet<LRTerm>();
    
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
        return deep_equals(terms,other.terms);
    }
}


