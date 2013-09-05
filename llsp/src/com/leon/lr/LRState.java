
package com.leon.lr;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : Leon
 * @since : 2013-8-13
 * @see :
 */

public class LRState {
    
    public Set<LRTerm> terms = new LinkedHashSet<LRTerm>();
    
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
                for (LRTerm t1_term : t1) {
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
    
    public String toString1() {
        Map<LRCoreTerm, List<String>> mergeTerm = new LinkedHashMap<LRCoreTerm, List<String>>();
        for (LRTerm term : terms) {
            LRCoreTerm core = term.core_item;
            List<String> value = mergeTerm.get(core);
            if (value == null) {
                value = new ArrayList<String>();
            }
            value.add(term.look_ahead);
            mergeTerm.put(core, value);
        }
        StringBuilder sb = new StringBuilder();
        for (LRCoreTerm core : mergeTerm.keySet()) {
            sb.append(core.toString() + ",look_ahead " + mergeTerm.get(core) + "\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (LRTerm term : terms) {
            sb.append(term+"\n");
        }
        return sb.toString();
    }
}
