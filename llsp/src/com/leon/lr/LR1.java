
package com.leon.lr;

import static com.leon.util.Utils.fill_first_set;
import static com.leon.util.Utils.first;
import static com.leon.util.Utils.index;
import static com.leon.util.Utils.is_terminal;
import static com.leon.util.Utils.match_lhs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.leon.grammar.Assoc;
import com.leon.grammar.Grammar;
import com.leon.grammar.Production;
import com.leon.util.ISymbol;
import com.leon.util.Queue;
import com.leon.util.Stack;
import com.leon.util.IToken;

/**
 * @author : Leon
 * @since : 2013-8-13
 * @see :
 */

public class LR1 {
    
    public void lr1_driver(Grammar g, IToken<?> token) throws IOException {
        Set<String>[] first_set = fill_first_set(g);
        List<LRState> label_list = build_label(g, first_set);
        int[][] go_to = build_goto1(g, first_set, label_list);
        ActionItem[][] action = build_action1(g, first_set, go_to, label_list);
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(0);
        ISymbol<?> t = token.next_token();
        while (true) {
            int state = stack.top();
            System.out.println("state:" + state + ",token:'" + t + "'");
            if (action[index(t.get_type().toString(), g.vocabulary)][state] == null) {
                System.out.println("syntax error:" + t + ",line:" + t.get_line() + ",column:" + t.get_column());
                break;
            }
            else if (action[index(t.get_type().toString(), g.vocabulary)][state].type == ActionType.A) {
                System.out.println("accecped");
                break;
            }
            else if (action[index(t.get_type().toString(), g.vocabulary)][state].type == ActionType.S) {
                stack.push(go_to[index(t.get_type().toString(), g.vocabulary)][state]);
                System.out.println("shift:" + action[index(t.get_type().toString(), g.vocabulary)][state].symbol);
                t = token.next_token();
            }
            else if (action[index(t.get_type().toString(), g.vocabulary)][state].type == ActionType.R) {
                Production p = action[index(t.get_type().toString(), g.vocabulary)][state].p;
                System.out.println("reduce:" + p);
                for (int i = 0; i < p.rhs.length; i++) {
                    stack.pop();
                }
                int top = stack.top();
                stack.push(go_to[index(p.lhs, g.vocabulary)][top]);
            }
            System.out.println(stack);
        }
        
    }
    
    private LRState closure1(LRState state, Grammar g, Set<String>[] first_set) {
        int before_size = 0;
        int after_size = 0;
        List<LRTerm> list = new ArrayList<LRTerm>(state.terms);
        do {
            before_size = list.size();
            for (int i = 0; i < list.size(); i++) {
                LRTerm term = list.get(i);
                if (term.p.rhs.length == term.dot) {
                    continue;
                }
                String symbol = term.p.rhs[term.dot];
                List<Production> p_list = match_lhs(symbol, g);
                for (int j = 0; j < p_list.size(); j++) {
                    Set<String> firsts = first(compute_alpha(term), first_set, g);
                    for (String first : firsts) {
                        LRTerm new_term = new LRTerm(new LRCoreTerm(p_list.get(j), 0), first);
                        if (!list.contains(new_term)) {
                            list.add(new_term);
                        }
                    }
                }
            }
            after_size = list.size();
        }
        while (before_size != after_size);
        state.terms = new HashSet<LRTerm>(list);
        return state;
    }
    
    private LRState goto1(LRState state, String symbol, Grammar g, Set<String>[] first_set) {
        LRState result = new LRState();
        for (LRTerm term : state.terms) {
            if (term.p.rhs.length == term.dot) {
                continue;
            }
            if (term.p.rhs[term.dot].equals(symbol)) {
                LRTerm new_term = new LRTerm(new LRCoreTerm(term.p, term.dot + 1), term.look_ahead);
                result.terms.add(new_term);
            }
        }
        return closure1(result, g, first_set);
    }
    
    private List<LRState> build_label(Grammar g, Set<String>[] first_set) {
        LRTerm term = new LRTerm(new LRCoreTerm(g.start_production, 0), null);
        LRState start = new LRState();
        start.terms.add(term);
        start = closure1(start, g, first_set);
        Queue<LRState> queue = new Queue<LRState>();
        queue.put(start);
        List<LRState> label_list = new ArrayList<LRState>();
        label(start, label_list);
        while (!queue.is_empty()) {
            LRState state = queue.poll();
            for (int i = 0; i < g.vocabulary.length; i++) {
                LRState to_state = goto1(state, g.vocabulary[i], g, first_set);
                if (to_state.terms.size() != 0) {
                    if (!is_labeled(to_state, label_list)) {
                        label(to_state, label_list);
                        queue.put(to_state);
                    }
                }
            }
        }
        for (int i = 0; i < label_list.size(); i++) {
            LRState state = label_list.get(i);
            System.out.println("state i:" + i);
            System.out.println(state.toString());
        }
        return label_list;
    }
    
    private int[][] build_goto1(Grammar g, Set<String>[] first_set, List<LRState> label_list) {
        int[][] go_to = new int[g.vocabulary.length][label_list.size()];
        for (int i = 0; i < go_to.length; i++) {
            for (int j = 0; j < go_to[i].length; j++) {
                go_to[i][j] = -1;
            }
        }
        for (int i = 0; i < label_list.size(); i++) {
            LRState state = label_list.get(i);
            for (int j = 0; j < g.vocabulary.length; j++) {
                LRState to_state = goto1(state, g.vocabulary[j], g, first_set);
                if (to_state.terms.size() != 0) {
                    go_to[index(g.vocabulary[j], g.vocabulary)][i] = label_list.indexOf(to_state);
                }
            }
        }
        return go_to;
    }
    
    private ActionItem[][] build_action1(Grammar g, Set<String>[] first_set, int[][] go_to, List<LRState> label_list) {
        ActionItem[][] m = new ActionItem[g.vocabulary.length][label_list.size()];
        for (int i = 0; i < label_list.size(); i++) {
            LRState state = label_list.get(i);
            for (LRTerm term : state.terms) {
                if (term.p.rhs.length != term.dot) {
                    String symbol = term.p.rhs[term.dot];
                    if (is_terminal(symbol, g.terminals) && go_to[index(symbol, g.vocabulary)][i] != -1) {
                        if (!symbol.equals(g.eof)) {
                            if (m[index(symbol, g.vocabulary)][i] != null) {
                                ActionItem ai = m[index(symbol, g.vocabulary)][i];
                                if (ai.type == ActionType.R) {
                                    System.out.println("state:" + i + ";shift:" + symbol + ";reduce:" + ai.p
                                                       + " conflict");
                                }
                            }
                            m[index(symbol, g.vocabulary)][i] = new ActionItem(ActionType.S, symbol);
                        }
                        else {
                            m[index(symbol, g.vocabulary)][i] = new ActionItem(ActionType.A, symbol);
                        }
                    }
                }
                else {
                    if (term.look_ahead != null) {
                        if (m[index(term.look_ahead, g.vocabulary)][i] != null) {
                            ActionItem ai = m[index(term.look_ahead, g.vocabulary)][i];
                            if (ai.type == ActionType.S) {
                                System.out.println("state:" + i + ";shift:" + ai.symbol + ";reduce:" + term.p
                                                   + " conflict");
                            }
                            else if (ai.type == ActionType.R) {
                                System.out.println("state:" + i + ";reduce:" + ai.p + ";reduce:" + term.p + " conflict");
                            }
                        }
                        m[index(term.look_ahead, g.vocabulary)][i] = new ActionItem(ActionType.R, term.p);
                        
                    }
                }
            }
        }
        return m;
    }
    
    private void label(LRState state, List<LRState> list) {
        list.add(state);
    }
    
    private boolean is_labeled(LRState state, List<LRState> list) {
        if (list.size() == 0) {
            return false;
        }
        if (!list.contains(state)) {
            return false;
        }
        return true;
    }
    
    private String[] compute_alpha(LRTerm term) {
        String[] new_array = new String[term.p.rhs.length - term.dot];
        int j = 0;
        for (int i = term.dot + 1; i < term.p.rhs.length; i++) {
            new_array[j++] = term.p.rhs[i];
        }
        new_array[j] = term.look_ahead;
        return new_array;
    }
    
    private void resolveShiftReduceConflict(ActionItem[][] m, String symbol, List<Assoc> assoc) {
    }
    
    private void resolveReduceReduceConflict(ActionItem[][] m, String symbol, List<Assoc> assoc) {
    }
    
}
