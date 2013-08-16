
package com.leon.ll;

import static com.leon.util.Utils.longest_common_perfix;
import static com.leon.util.Utils.first;
import static com.leon.util.Utils.index;
import static com.leon.util.Utils.is_nonterminal;
import static com.leon.util.Utils.is_terminal;
import static com.leon.util.Utils.cut_array_add_end;
import static com.leon.util.Utils.substitute;
import static com.leon.util.Utils.remove_direct_left_recursion;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.leon.grammar.Grammar;
import com.leon.grammar.Production;
import com.leon.grammar.ProductionSet;
import com.leon.util.Stack;

/**
 * @author : Leon
 * @since : 2013-8-11
 * @see :
 */

public class LL1 {
    
    public String[] grammar_str;
    
    private int     token_i;
    
    public void predict(Production p, Set<String>[] first_set, Set<String>[] follow_set, Grammar g, int p_index,
                        int[][] m) {
        Set<String> set = first(p.rhs, first_set, g);
        if (set.size() == 0) {
            set.addAll(follow_set[index(p.lhs, g.nonterminals)]);
        }
        for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
            String symbol = iterator.next();
            m[index(symbol, g.terminals)][index(p.lhs, g.nonterminals)] = p_index;
        }
    }
    
    public void gen_action(String[] symbol, Grammar g) {
        if (symbol.length == 0) {
            System.out.println("\t\t\t/* null */");
        }
        else {
            for (int i = 0; i < symbol.length; i++) {
                if (is_terminal(symbol[i], g)) {
                    System.out.println("\t\t\tnode.childs.add(match(\"" + symbol[i] + "\"));");
                }
                else {
                    System.out.println("\t\t\tnode.childs.add(" + symbol[i] + "());");
                }
            }
        }
    }
    
    public void make_parsing_proc(String nonterminal, int[][] m, Grammar g) {
        System.out.println("public CPNode " + nonterminal + "(){");
        System.out.println("\tCPNode node = new InternalNode(\"" + nonterminal + "\");");
        System.out.println("\tString tok = current_token();");
        System.out.println("\tswitch(tok){");
        for (int j = 0; j < g.terminals.length; j++) {
            for (int i = 0; i < g.nonterminals.length; i++) {
                if (g.nonterminals[i].equals(nonterminal)) {
                    if (m[j][i] > 0) {
                        System.out.println("\t\tcase \"" + g.terminals[j] + "\":");
                        Production selected_p = g.productions.get(m[j][i] - 1);
                        gen_action(selected_p.rhs, g);
                        System.out.println("\t\t\tbreak;");
                    }
                    break;
                }
            }
        }
        System.out.println("\t\tdefault:");
        System.out.println("\t\t\tsyntax_error(tok);");
        System.out.println("\t\t\tbreak;");
        System.out.println("\t}");
        System.out.println("\treturn node;");
        System.out.println("}");
    }
    
    public void ll1_driver(Grammar g, int[][] m) {
        Stack<String> stack = new Stack<String>();
        stack.push(g.start_symbol);
        String a = next_token();
        while (!stack.is_empty()) {
            System.out.println(stack);
            String symbol = stack.pop();
            if (is_nonterminal(symbol, g) && m[index(a, g.terminals)][index(symbol, g.nonterminals)] > 0) {
                Production p = g.productions.get(m[index(a, g.terminals)][index(symbol, g.nonterminals)] - 1);
                String[] rhs = p.rhs;
                for (int i = rhs.length - 1; i >= 0; i--) {
                    stack.push(rhs[i]);
                }
            }
            else if (symbol.equals(a)) {
                a = next_token();
            }
            else {
                System.out.println("syntax error");
            }
        }
    }
    
    public Grammar factor(Grammar g) {
        CommonPerfixStruct struct;
        Grammar temp = g;
        int count = 0;
        while ((struct = common_perfix(temp.productions))!=null) {
            String new_nonterminal = struct.lhs + ""+(count++);
            
            List<Production> new_productions = temp.productions;
            Production p = new Production(struct.lhs, cut_array_add_end(struct.perfix, 0, struct.perfix.length,
                    new_nonterminal));
            new_productions.add(p);
            for (int i = 0; i < struct.list.size(); i++) {
                Production pi = new Production(new_nonterminal, cut_array_add_end(struct.list.get(i).rhs, struct.index,
                        struct.list.get(i).rhs.length));
                new_productions.add(pi);
            }
            for (int i = 0; i < struct.list.size(); i++) {
                new_productions.remove(struct.list.get(i));
            }
            temp = new Grammar(temp.start_symbol, new_productions, temp.terminals);
        }
        return temp;
    }
    
    public Grammar remove_left_recursion(Grammar g) {
        List<ProductionSet> list = new ArrayList<ProductionSet>();
        for (int i = 0; i < g.production_set.size(); i++) {
            ProductionSet ai = g.production_set.get(i);
            for (int j = 0; j < i; j++) {
                ProductionSet aj = g.production_set.get(j);
                ai = substitute(aj, ai);
            }
            list.addAll(remove_direct_left_recursion(ai));
        }
        return new Grammar(g.start_symbol, g.terminals, list);
    }
    
    private CommonPerfixStruct common_perfix(List<Production> productions) {
        int max_len = 0;
        Production max_len_p = null;
        for (int i = 0; i < productions.size()-1; i++) {
            int index = 0;
            Production pi = productions.get(i);
            for (int j = i+1; j < productions.size(); j++) {
                Production pj = productions.get(j);
                if(pi.lhs.equals(pj.lhs)){
                    index = longest_common_perfix(pi.rhs,pj.rhs);
                }
            }
            if(index > max_len){
                max_len = index;
                max_len_p = pi;
            }
        }
        if(max_len >0){
            List<Production> list = new ArrayList<Production>();
            for (int i = 0; i < productions.size(); i++) {
                Production p = productions.get(i);
                if(p.lhs.equals(max_len_p.lhs)){
                    int k = 0;
                    for (int j = 0; j < max_len; j++) {
                        if(p.rhs[j].equals(max_len_p.rhs[j])){
                            k++;
                        }
                    }
                    
                    if(k == max_len){
                        list.add(p);
                    }
                }
            }
            return new CommonPerfixStruct(max_len, list);
        }
        return null;
    }
    
    private String next_token() {
        if (token_i < grammar_str.length) {
            return grammar_str[token_i++];
        }
        return null;
    }
    
}


