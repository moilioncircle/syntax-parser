
package com.leon.lr;

import static com.leon.util.Utils.first;
import static com.leon.util.Utils.match_lhs;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.leon.grammar.Grammar;
import com.leon.grammar.Production;

/**
 * @author : Leon
 * @since : 2013-8-13
 * @see :
 */

public class LR1 {
    
    LRState closure1(LRState state, Grammar g, Set<String>[] first_set) {
        int before_size = 0;
        int after_size = 0;
        do {
            before_size = state.terms.size();
            for (Iterator<LRTerm> iterator = state.terms.iterator(); iterator.hasNext();) {
                LRTerm term = iterator.next();
                String symbol = term.p.rhs[term.index];
                List<Production> p_list = match_lhs(symbol, g);
                for (int i = 0; i < p_list.size(); i++) {
                    Set<String> firsts = first(compute_alpha(term), first_set, g);
                    for (Iterator<String> iterator2 = firsts.iterator(); iterator2.hasNext();) {
                        String first = iterator2.next();
                        LRTerm new_term = new LRTerm(p_list.get(i), 0, first);
                        state.terms.add(new_term);
                    }
                }
            }
            after_size = state.terms.size();
        }
        while (before_size == after_size);
        return state;
    }
    
    LRState goto1(LRState state,String symbol,Grammar g, Set<String>[] first_set){
        LRState result = new LRState();
        for (Iterator<LRTerm> iterator = state.terms.iterator(); iterator.hasNext();) {
            LRTerm term = iterator.next();
            if(term.p.rhs[term.index].equals(symbol)){
                LRTerm new_term = new LRTerm(term.p, term.index+1, term.look_ahead);
                result.terms.add(new_term);
            }
        }
        return closure1(result,g,first_set);
    }

    private String[] compute_alpha(LRTerm term) {
        String[] new_array = new String[term.p.rhs.length - term.index];
        int j = 0;
        for (int i = term.index + 1; i < term.p.rhs.length; i++) {
            new_array[j++] = term.p.rhs[i];
        }
        new_array[j] = term.look_ahead;
        return new_array;
    }
}
