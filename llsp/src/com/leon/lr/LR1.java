
package com.leon.lr;

import static com.leon.util.Utils.fill_first_set;
import static com.leon.util.Utils.first;
import static com.leon.util.Utils.index;
import static com.leon.util.Utils.is_terminal;
import static com.leon.util.Utils.match_lhs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.leon.grammar.Grammar;
import com.leon.grammar.Production;
import com.leon.util.Queue;
import com.leon.util.Stack;
import com.leon.util.Token;

/**
 * @author : Leon
 * @since : 2013-8-13
 * @see :
 */

public class LR1 {
    
    private Token token;
    
    public LR1() {
        
    }
    
    public LR1(Token token) {
        this.token = token;
    }
    
    public void lr1_driver(Grammar g) {
        Set<String>[] first_set = fill_first_set(g);
        List<LRState> label_list = build_label(g, first_set);
        String[][] go_to = build_goto1(g, first_set, label_list);
        System.out.println("  00 01 02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20 21 22");
        for (int i = 0; i < go_to.length; i++) {
            System.out.print(g.vocabulary[i] + " ");
            for (int j = 0; j < go_to[i].length; j++) {
                System.out.print(go_to[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("===================================================================================");
        String[][] action = build_action1(g, first_set, go_to, label_list);
        System.out.println("  0000 0001 0002 0003 0004 0005 0006 0007 0008 0009 0010 0011 0012 0013 0014 0015 0016 0017 0018 0019 0020 0021 0022");
        for (int i = 0; i < action.length; i++) {
            System.out.print(g.vocabulary[i] + " ");
            for (int j = 0; j < action[i].length; j++) {
                System.out.print(action[i][j] + " ");
            }
            System.out.println();
        }
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(0);
        String t;
        while (true) {
            int state = stack.top();
            t = token.current_token();
            System.out.println("state:" + state + ",t:" + t);
            if (action[index(t, g.vocabulary)][state].equals("AAAA")) {
                System.out.println("accecped");
                break;
            }
            else if (action[index(t, g.vocabulary)][state].equals("SSSS")) {
                stack.push(Integer.parseInt(go_to[index(t, g.vocabulary)][state]));
                System.out.println("shift:" + token.current_token());
                t = token.next_token();
            }
            else if (action[index(t, g.vocabulary)][state].startsWith("RR")) {
                int p_index = Integer.parseInt(action[index(t, g.vocabulary)][state].split(":")[1]);
                Production p = g.productions.get(p_index);
                System.out.println("when " + token.current_token() + " then reduce:" + p);
                for (int i = 0; i < p.rhs.length; i++) {
                    stack.pop();
                }
                int top = stack.top();
                stack.push(Integer.parseInt(go_to[index(p.lhs, g.vocabulary)][top]));
            }
            else {
                System.out.println("syntax error:" + t);
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
                if (term.p.rhs.length == term.index) {
                    continue;
                }
                String symbol = term.p.rhs[term.index];
                List<Production> p_list = match_lhs(symbol, g);
                for (int j = 0; j < p_list.size(); j++) {
                    Set<String> firsts = first(compute_alpha(term), first_set, g);
                    for (Iterator<String> iterator = firsts.iterator(); iterator.hasNext();) {
                        String first = iterator.next();
                        LRTerm new_term = new LRTerm(p_list.get(j), 0, first);
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
        for (Iterator<LRTerm> iterator = state.terms.iterator(); iterator.hasNext();) {
            LRTerm term = iterator.next();
            if (term.p.rhs.length == term.index) {
                continue;
            }
            if (term.p.rhs[term.index].equals(symbol)) {
                LRTerm new_term = new LRTerm(term.p, term.index + 1, term.look_ahead);
                result.terms.add(new_term);
            }
        }
        return closure1(result, g, first_set);
    }
    
    private List<LRState> build_label(Grammar g, Set<String>[] first_set) {
        LRTerm term = new LRTerm(g.start_production, 0, null);
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
    
    private String[][] build_goto1(Grammar g, Set<String>[] first_set, List<LRState> label_list) {
        String[][] go_to = new String[g.vocabulary.length][label_list.size()];
        for (int i = 0; i < go_to.length; i++) {
            for (int j = 0; j < go_to[i].length; j++) {
                go_to[i][j] = "00";
            }
        }
        for (int i = 0; i < label_list.size(); i++) {
            LRState state = label_list.get(i);
            for (int j = 0; j < g.vocabulary.length; j++) {
                LRState to_state = goto1(state, g.vocabulary[j], g, first_set);
                if (to_state.terms.size() != 0) {
                    String s = label_list.indexOf(to_state) < 10 ? "0" + label_list.indexOf(to_state)
                                                                : label_list.indexOf(to_state) + "";
                    go_to[index(g.vocabulary[j], g.vocabulary)][i] = s;
                }
            }
        }
        return go_to;
    }
    
    private String[][] build_action1(Grammar g, Set<String>[] first_set, String[][] go_to, List<LRState> label_list) {
        String[][] m = new String[g.vocabulary.length][label_list.size()];
        for (int i = 0; i < label_list.size(); i++) {
            LRState state = label_list.get(i);
            for (Iterator<LRTerm> iterator = state.terms.iterator(); iterator.hasNext();) {
                LRTerm term = iterator.next();
                if (term.p.rhs.length != term.index) {
                    String a = term.p.rhs[term.index];
                    if (is_terminal(a, g) && !go_to[index(a, g.vocabulary)][i].equals("00")) {
                        if (!a.equals("$")) {
                            m[index(a, g.vocabulary)][i] = "SSSS";
                        }
                        else {
                            m[index(a, g.vocabulary)][i] = "AAAA";
                        }
                    }
                }
                else {
                    if (term.look_ahead != null) {
                        m[index(term.look_ahead, g.vocabulary)][i] = "RR:" + g.productions.indexOf(term.p);
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
        String[] new_array = new String[term.p.rhs.length - term.index];
        int j = 0;
        for (int i = term.index + 1; i < term.p.rhs.length; i++) {
            new_array[j++] = term.p.rhs[i];
        }
        new_array[j] = term.look_ahead;
        return new_array;
    }
    
    public static void main(String[] args) {
        do_1_grammar();
        do_2_grammar();
        do_3_grammar();
        do_4_grammar();
        do_5_grammar();
    }
    
    private static void do_1_grammar() {
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("program", new String[] { "begin", "stmts", "end", "$" }));
        list.add(new Production("stmts", new String[] { "simplestmt", ";", "stmts" }));
        list.add(new Production("stmts", new String[] { "begin", "stmts", "end", ";", "stmts" }));
        list.add(new Production("stmts", new String[] {}));
        
        Grammar g = new Grammar("program", list, new String[] { "begin", "end", ";", "simplestmt", "$" });
        Set<String>[] first_set = fill_first_set(g);
        LR1 lr = new LR1();
        LRTerm term = new LRTerm(g.start_production, 0, null);
        LRState start = new LRState();
        start.terms.add(term);
        start = lr.closure1(start, g, first_set);
        System.out.println(start.terms);
        Token t = new Token(new String[] { "begin", "simplestmt", ";", "simplestmt", ";", "end", "$" });
        lr.token = t;
        lr.lr1_driver(g);
    }
    
    private static void do_2_grammar() {
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("S'", new String[] { "S", "$" }));
        list.add(new Production("S", new String[] { "C", "C" }));
        list.add(new Production("C", new String[] { "c", "C" }));
        list.add(new Production("C", new String[] { "d" }));
        Grammar g = new Grammar("S'", list, new String[] { "$", "c", "d" });
        LR1 lr = new LR1();
        Token t = new Token(new String[] { "c", "d", "c", "d", "$" });
        lr.token = t;
        lr.lr1_driver(g);
    }
    
    private static void do_3_grammar() {
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("K", new String[] { "S", "$" }));
        list.add(new Production("S", new String[] { "V", "=", "E" }));
        list.add(new Production("S", new String[] { "E" }));
        list.add(new Production("E", new String[] { "V" }));
        list.add(new Production("V", new String[] { "x" }));
        list.add(new Production("V", new String[] { "*", "E" }));
        Grammar g = new Grammar("K", list, new String[] { "x", "*", "=", "$" });
        LR1 lr = new LR1();
        Token t = new Token(new String[] { "x", "=", "*", "x", "$" });
        lr.token = t;
        lr.lr1_driver(g);
    }
    
    private static void do_4_grammar() {
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("S", new String[] { "E", "$" }));
        list.add(new Production("E", new String[] { "E", "+", "T" }));
        list.add(new Production("E", new String[] { "T" }));
        list.add(new Production("T", new String[] { "T", "*", "P" }));
        list.add(new Production("T", new String[] { "P" }));
        list.add(new Production("P", new String[] { "i" }));
        list.add(new Production("P", new String[] { "(", "E", ")" }));
        Grammar g = new Grammar("S", list, new String[] { "+", "*", "i", "(", ")", "$" });
        LR1 lr = new LR1();
        Token t = new Token(new String[] { "(", "i", "+", "i", ")", "*", "i", "$" });
        lr.token = t;
        lr.lr1_driver(g);
    }
    
    private static void do_5_grammar() {
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("S", new String[] { "E", "$" }));
        list.add(new Production("E", new String[] { "i" }));
        list.add(new Production("E", new String[] {}));
        Grammar g = new Grammar("S", list, new String[] { "i", "$" });
        LR1 lr = new LR1();
        Token t = new Token(new String[] { "$" });
        lr.token = t;
        lr.lr1_driver(g);
    }
}
