
package com.leon.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
                    if (p.rhs.length == 0) {
                        derives_lambda[index(p.lhs, g.vocabulary)] = true;
                        changes = true;
                        continue;
                    }
                    boolean rhs_derives_lambda = derives_lambda[index(p.rhs[0], g.vocabulary)];
                    for (int j = 1; j < p.rhs.length; j++) {
                        rhs_derives_lambda = rhs_derives_lambda && derives_lambda[index(p.rhs[j], g.vocabulary)];
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
                first_set[index(p.lhs, g.vocabulary)].addAll(compute_first(p.rhs, first_set, g));
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
                for (int j = 0; j < p.rhs.length; j++) {
                    String symbol = p.rhs[j];
                    if (index(symbol, g.nonterminals) != -1) {
                        String[] beta = build_beta(j, p.rhs);
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
    
    public static Set<String> first(String[] alpha, Set<String>[] first_set, Grammar g) {
        Set<String> set = new HashSet<String>();
        for (int i = 0; i < alpha.length; i++) {
            if (first_set[index(alpha[i], g.vocabulary)].contains(lambda)) {
                continue;
            }
            else {
                set.addAll(first_set[index(alpha[i], g.vocabulary)]);
                break;
            }
        }
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
    
    public static boolean is_nonterminal(String symbol, Grammar g) {
        for (int i = 0; i < g.nonterminals.length; i++) {
            if (g.nonterminals[i].equals(symbol)) {
                return true;
            }
        }
        return false;
    }
    
    public static List<Production> match_lhs(String symbol,Grammar g){
        List<Production> result = new ArrayList<Production>();
        for (int i = 0; i < g.productions.size(); i++) {
            if (g.productions.get(i).lhs.equals(symbol)) {
                result.add(g.productions.get(i));
            }
        }
        return result;
    }
    
    public static boolean is_terminal(String symbol, Grammar g) {
        for (int i = 0; i < g.terminals.length; i++) {
            if (g.terminals[i].equals(symbol)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean deep_equals(Set<?> c1,Set<?> c2){
        if(c1.size() != c2.size()){
            return false;
        }
        for (Iterator<?> iterator = c1.iterator(); iterator.hasNext();) {
            Object obj1 = iterator.next();
            if(!c2.contains(obj1)){
                return false;
            }
        }
        return true;
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
            if(pi[index].equals(pj[index])){
                index++;
            }
        }
        return index;
    }
    
    public static ProductionSet substitute(ProductionSet aj, ProductionSet ai) {
        ProductionSet result = new ProductionSet(ai.lhs);
        String symbol = aj.lhs;
        for (int i = 0; i < ai.rhs_set.size(); i++) {
            String[] rhs = ai.rhs_set.get(i);
            if (rhs.length != 0 && rhs[0].equals(symbol)) {
                List<Production> aj_ps = aj.get_productions();
                for (int j = 0; j < aj_ps.size(); j++) {
                    result.add_rhs(cut_array_add_before(rhs, 1, rhs.length, aj_ps.get(j).rhs));
                }
            }
            else {
                result.add_rhs(rhs);
            }
        }
        
        return result;
    }
    
    public static List<ProductionSet> remove_direct_left_recursion(ProductionSet ai) {
        System.out.println("ai:"+ai);
        List<Production> ai_ps = ai.get_productions();
        ProductionSet new_ai = new ProductionSet(ai.lhs);
        ProductionSet new_ai_tail = new ProductionSet(ai.lhs + "_tail");
        for (int i = 0; i < ai_ps.size(); i++) {
            Production p = ai_ps.get(i);
            if (p.rhs.length != 0 && p.rhs[0].equals(p.lhs)) {
                String[] new_rhs = cut_array_add_end(p.rhs, 1, p.rhs.length, new_ai_tail.lhs);
                new_ai_tail.add_rhs(new_rhs);
                new_ai_tail.add_rhs(new String[] {});
            }
            else {
                String[] new_rhs = cut_array_add_end(p.rhs, 0, p.rhs.length, new_ai_tail.lhs);
                new_ai.add_rhs(new_rhs);
            }
        }
        List<ProductionSet> rs = new ArrayList<ProductionSet>();
        if (new_ai_tail.rhs_set.size() != 0) {
            rs.add(new_ai);
            rs.add(new_ai_tail);
        }
        else {
            rs.add(ai);
        }
        return rs;
    }
    
    private static boolean have_derives(String nonterminal, String terminal, Grammar g) {
        for (int i = 0; i < g.productions.size(); i++) {
            Production production = g.productions.get(i);
            if (production.lhs.equals(nonterminal) && production.rhs.length > 0 && production.rhs[0].equals(terminal)) {
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
    
    public static void main(String[] args) {
        ProductionSet ai = new ProductionSet("A");
        ProductionSet aj = new ProductionSet("B");
        ai.add_rhs(new String[]{"a","B"});
        ai.add_rhs(new String[]{"B","b"});
        aj.add_rhs(new String[]{"A","c"});
        aj.add_rhs(new String[]{"d"});
        ProductionSet rs = substitute(ai, aj);
        System.out.println(rs);
        System.out.println("=============");
        List<ProductionSet> list = remove_direct_left_recursion(ai);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}
