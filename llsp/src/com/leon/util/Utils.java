
package com.leon.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.leon.grammar.Assoc;
import com.leon.grammar.Associativity;
import com.leon.grammar.Grammar;
import com.leon.grammar.Production;
import com.leon.grammar.ProductionSet;

/**
 * @author : Leon
 * @since : 2013-8-13
 * @see :
 */

public class Utils {
    
    private static String lambda = "lambda";
    
    public static boolean[] mark_lambda(Grammar g) {
        boolean[] derives_lambda = new boolean[g.vocabulary.length];
        boolean changes;
        do {
            changes = false;
            for (int i = 0; i < g.productions.size(); i++) {
                Production p = g.productions.get(i);
                if (!derives_lambda[index(p.lhs, g.vocabulary)]) {
                    if (p.right.rhs.length == 0) {
                        derives_lambda[index(p.lhs, g.vocabulary)] = true;
                        changes = true;
                        continue;
                    }
                    boolean rhs_derives_lambda = derives_lambda[index(p.right.rhs[0], g.vocabulary)];
                    for (int j = 1; j < p.right.rhs.length; j++) {
                        rhs_derives_lambda = rhs_derives_lambda && derives_lambda[index(p.right.rhs[j], g.vocabulary)];
                    }
                    if (rhs_derives_lambda) {
                        derives_lambda[index(p.lhs, g.vocabulary)] = true;
                        changes = true;
                    }
                }
            }
        }
        while (changes);
        return derives_lambda;
    }
    
    public static Set<String> compute_first(String[] alpha, Set<String>[] first_set, Grammar g) {
        Set<String> result = new HashSet<String>();
        int k = alpha.length;
        if (k == 0) {
            result.add(lambda);
        }
        else {
            result.addAll(first_set[index(alpha[0], g.vocabulary)]);
            int i;
            for (i = 1; i < alpha.length && first_set[index(alpha[i - 1], g.vocabulary)].contains(lambda); i++) {
                result.addAll(first_set[index(alpha[i], g.vocabulary)]);
                if (result.contains(lambda)) {
                    result.remove(lambda);
                }
            }
            
            if (i == alpha.length && first_set[index(alpha[alpha.length - 1], g.vocabulary)].contains(lambda)) {
                result.add(lambda);
            }
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public static Set<String>[] fill_first_set(Grammar g) {
        Set<String>[] first_set = new Set[g.vocabulary.length];
        for (int i = 0; i < first_set.length; i++) {
            if (first_set[i] == null) {
                first_set[i] = new HashSet<String>();
            }
        }
        boolean[] derives_lambda = mark_lambda(g);
        for (int i = 0; i < g.nonterminals.length; i++) {
            String nonterminal = g.nonterminals[i];
            if (derives_lambda[index(nonterminal, g.vocabulary)]) {
                first_set[index(nonterminal, g.vocabulary)].add(lambda);
            }
        }
        for (int i = 0; i < g.terminals.length; i++) {
            String terminal = g.terminals[i];
            first_set[index(terminal, g.vocabulary)].add(terminal);
            for (int j = 0; j < g.nonterminals.length; j++) {
                String nonterminal = g.nonterminals[j];
                if (have_derives(nonterminal, terminal, g)) {
                    first_set[index(nonterminal, g.vocabulary)].add(terminal);
                }
            }
        }
        boolean changes;
        do {
            changes = false;
            for (int i = 0; i < g.productions.size(); i++) {
                Production p = g.productions.get(i);
                int before_size = first_set[index(p.lhs, g.vocabulary)].size();
                first_set[index(p.lhs, g.vocabulary)].addAll(compute_first(p.right.rhs, first_set, g));
                int after_size = first_set[index(p.lhs, g.vocabulary)].size();
                if (before_size != after_size) {
                    changes = true;
                }
            }
        }
        while (changes);
        return first_set;
    }
    
    @SuppressWarnings("unchecked")
    public static Set<String>[] fill_follow_set(Grammar g, Set<String>[] first_set) {
        
        Set<String>[] follow_set = new Set[g.nonterminals.length];
        for (int i = 0; i < follow_set.length; i++) {
            if (follow_set[i] == null) {
                follow_set[i] = new HashSet<String>();
            }
        }
        follow_set[index(g.start_symbol, g.nonterminals)].add(lambda);
        boolean changes;
        do {
            changes = false;
            for (int i = 0; i < g.productions.size(); i++) {
                Production p = g.productions.get(i);
                for (int j = 0; j < p.right.rhs.length; j++) {
                    String symbol = p.right.rhs[j];
                    if (index(symbol, g.nonterminals) != -1) {
                        String[] beta = build_beta(j, p.right.rhs);
                        Set<String> compute_first = compute_first(beta, first_set, g);
                        Set<String> set = new HashSet<String>(compute_first);
                        set.remove(lambda);
                        int before_size = follow_set[index(symbol, g.nonterminals)].size();
                        follow_set[index(symbol, g.nonterminals)].addAll(set);
                        if (compute_first.contains(lambda)) {
                            follow_set[index(symbol, g.nonterminals)].addAll(follow_set[index(p.lhs, g.nonterminals)]);
                        }
                        int after_size = follow_set[index(symbol, g.nonterminals)].size();
                        if (before_size != after_size) {
                            changes = true;
                        }
                    }
                }
            }
        }
        while (changes);
        return follow_set;
    }
    
    public static Set<String> first(String[] alpha, Set<String>[] first_set, final Grammar g) {
        Set<String> set = new LinkedHashSet<String>();
        for (int i = 0; i < alpha.length; i++) {
            if (first_set[index(alpha[i], g.vocabulary)].contains(lambda)) {
                continue;
            }
            else {
                set.addAll(first_set[index(alpha[i], g.vocabulary)]);
                break;
            }
        }
        List<String> list = new ArrayList<String>(set);
        Collections.sort(list, new Comparator<String>() {
            
            @Override
            public int compare(String o1, String o2) {
                int x = g.get_terminal_by_name(o1).insert_cost;
                int y = g.get_terminal_by_name(o2).insert_cost;
                return (x < y) ? -1 : ((x == y) ? 0 : 1);
            }
        });
        set = new LinkedHashSet<String>(list);
        return set;
    }
    
    public static int index(String symbol, String[] vocabulary) {
        for (int i = 0; i < vocabulary.length; i++) {
            if (symbol.equals(vocabulary[i])) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean is_nonterminal(String symbol, String[] nonterminals) {
        return is_in(symbol, nonterminals);
    }
    
    public static List<Production> match_lhs(String symbol, Grammar g) {
        List<Production> result = new ArrayList<Production>();
        for (int i = 0; i < g.productions.size(); i++) {
            if (g.productions.get(i).lhs.equals(symbol)) {
                result.add(g.productions.get(i));
            }
        }
        return result;
    }
    
    public static boolean is_terminal(String symbol, String[] terminals) {
        return is_in(symbol, terminals);
    }
    
    public static boolean is_in(String symbol, String[] symbols) {
        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i].equals(symbol)) {
                return true;
            }
        }
        return false;
    }
    
    @SafeVarargs
    public static <T> T[] cut_array_add_end(T[] original, int from, int to, T... t) {
        T[] array = Arrays.copyOfRange(original, from, to);
        T[] result = Arrays.copyOf(array, array.length + t.length);
        int j = 0;
        for (int i = array.length; i < result.length; i++) {
            result[i] = t[j++];
        }
        return result;
    }
    
    @SafeVarargs
    public static <T> T[] cut_array_add_before(T[] original, int from, int to, T... t) {
        T[] array = Arrays.copyOfRange(original, from, to);
        T[] result = Arrays.copyOf(t, array.length + t.length);
        int j = 0;
        for (int i = t.length; i < result.length; i++) {
            result[i] = array[j++];
        }
        return result;
    }
    
    public static int longest_common_perfix(String[] pi, String[] pj) {
        int index = 0;
        for (int i = 0; i < Math.min(pi.length, pj.length); i++) {
            if (pi[index].equals(pj[index])) {
                index++;
            }
        }
        return index;
    }
    
    public static ProductionSet derivation(ProductionSet aj, ProductionSet ai) {
        ProductionSet result = new ProductionSet(ai.lhs);
        String symbol = aj.lhs;
        for (int i = 0; i < ai.rhs_set.size(); i++) {
            String[] rhs = ai.rhs_set.get(i).rhs;
            if (rhs.length != 0 && rhs[0].equals(symbol)) {
                List<Production> aj_ps = aj.get_productions();
                for (int j = 0; j < aj_ps.size(); j++) {
                    result.or(cut_array_add_before(rhs, 1, rhs.length, aj_ps.get(j).right.rhs));
                }
            }
            else {
                result.or(rhs);
            }
        }
        
        return result;
    }
    
    public static List<ProductionSet> remove_direct_left_recursion(ProductionSet ai, ProductionSet temp) {
        System.out.println("ai:" + ai);
        List<Production> ai_ps = ai.get_productions();
        ProductionSet new_ai = new ProductionSet(ai.lhs);
        ProductionSet new_ai_tail = new ProductionSet(ai.lhs + "_tail");
        for (int i = 0; i < ai_ps.size(); i++) {
            Production p = ai_ps.get(i);
            if (p.right.rhs.length != 0 && p.right.rhs[0].equals(p.lhs)) {
                String[] new_rhs = cut_array_add_end(p.right.rhs, 1, p.right.rhs.length, new_ai_tail.lhs);
                new_ai_tail.or(new_rhs);
            }
            else {
                String[] new_rhs = cut_array_add_end(p.right.rhs, 0, p.right.rhs.length, new_ai_tail.lhs);
                new_ai.or(new_rhs);
            }
        }
        List<ProductionSet> rs = new ArrayList<ProductionSet>();
        if (new_ai_tail.rhs_set.size() != 0) {
            new_ai_tail.or(new String[] {});
            rs.add(new_ai);
            rs.add(new_ai_tail);
        }
        else {
            rs.add(temp);
        }
        return rs;
    }
    
    public static Assoc get_production_assoc(Production p, List<Assoc> assoc_list, String[] terminals) {
        int precedence = 0;
        Associativity association = Associativity.NONASSOC;
        if (p.has_prec()) {
            return get_symbol_assoc(p.right.prec.perc_symbol, assoc_list);
        }
        for (int i = 0; i < p.right.rhs.length; i++) {
            if (is_terminal(p.right.rhs[i], terminals)) {
                Assoc assoc = get_symbol_assoc(p.right.rhs[i], assoc_list);
                precedence = assoc.precedence;
                association = assoc.association;
            }
        }
        return new Assoc(precedence, association);
    }
    
    public static Assoc get_symbol_assoc(String symbol, List<Assoc> assoc_list) {
        for (Assoc assoc : assoc_list) {
            for (String assoc_symbol : assoc.symbol_list) {
                if (symbol.equals(assoc_symbol)) {
                    return assoc;
                }
            }
        }
        return new Assoc(0, Associativity.NONASSOC);
    }
    
    private static boolean have_derives(String nonterminal, String terminal, Grammar g) {
        for (int i = 0; i < g.productions.size(); i++) {
            Production production = g.productions.get(i);
            if (production.lhs.equals(nonterminal) && production.right.rhs.length > 0 && production.right.rhs[0].equals(terminal)) {
                return true;
            }
        }
        return false;
    }
    
    private static String[] build_beta(int j, String[] rhs) {
        String[] result = new String[rhs.length - (j + 1)];
        int k = 0;
        for (int i = j + 1; i < rhs.length; i++) {
            result[k++] = rhs[i];
        }
        return result;
    }
}
