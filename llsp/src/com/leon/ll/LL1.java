
package com.leon.ll;

import static com.leon.util.Utils.cut_array_add_end;
import static com.leon.util.Utils.derivation;
import static com.leon.util.Utils.fill_first_set;
import static com.leon.util.Utils.fill_follow_set;
import static com.leon.util.Utils.first;
import static com.leon.util.Utils.index;
import static com.leon.util.Utils.is_nonterminal;
import static com.leon.util.Utils.is_terminal;
import static com.leon.util.Utils.longest_common_perfix;
import static com.leon.util.Utils.remove_direct_left_recursion;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.leon.grammar.Grammar;
import com.leon.grammar.Production;
import com.leon.grammar.ProductionSet;
import com.leon.tree.cst.CRTNode;
import com.leon.tree.cst.InternalNode;
import com.leon.tree.cst.LeafNode;
import com.leon.util.Stack;
import com.leon.util.Token;

/**
 * @author : Leon
 * @since : 2013-8-11
 * @see :
 */

public class LL1 {
    
    public Token token;
    
    public LL1() {
        
    }
    
    public LL1(Token token) {
        this.token = token;
    }
    
    public int[][] predict_table(Grammar g) {
        Set<String>[] first_set = fill_first_set(g);
        Set<String>[] follow_set = fill_follow_set(g, first_set);
        int[][] m = new int[g.terminals.length][g.nonterminals.length];
        for (int i = 0; i < g.productions.size(); i++) {
            predict(g.productions.get(i), first_set, follow_set, g, i + 1, m);
        }
        return m;
    }
    
    public void predict(Production p, Set<String>[] first_set, Set<String>[] follow_set, Grammar g, int p_index,
                        int[][] m) {
        Set<String> set = first(p.rhs, first_set, g);
        if (set.size() == 0) {
            set.addAll(follow_set[index(p.lhs, g.nonterminals)]);
        }
        for (String symbol : set) {
            m[index(symbol, g.terminals)][index(p.lhs, g.nonterminals)] = p_index;
        }
    }
    
    public void gen_action(String[] symbol, Grammar g) {
        if (symbol.length == 0) {
            System.out.println("\t\t\t/* null */");
        }
        else {
            for (int i = 0; i < symbol.length; i++) {
                if (is_terminal(symbol[i], g.terminals)) {
                    System.out.println("\t\t\tnode.childs.add(match(\"" + symbol[i] + "\"));");
                }
                else {
                    System.out.println("\t\t\tnode.childs.add(" + symbol[i] + "());");
                }
            }
        }
    }
    
    public void make_parsing_proc(String nonterminal, int[][] m, Grammar g) {
        System.out.println("public CRTNode " + nonterminal + "(){");
        System.out.println("\tCRTNode node = new InternalNode(\"" + nonterminal + "\");");
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
    
    public CRTNode ll1_driver(Grammar g, int[][] m) {
        Stack<CRTNode> stack = new Stack<CRTNode>();
        CRTNode root = new InternalNode(g.start_symbol);
        stack.push(root);
        String a = token.next_token();
        while (!stack.is_empty()) {
            CRTNode node = stack.pop();
            if (is_nonterminal(node.name, g.nonterminals)
                && m[index(a, g.terminals)][index(node.name, g.nonterminals)] > 0) {
                Production p = g.productions.get(m[index(a, g.terminals)][index(node.name, g.nonterminals)] - 1);
                String[] rhs = p.rhs;
                for (int i = rhs.length - 1; i >= 0; i--) {
                    CRTNode child = is_nonterminal(rhs[i], g.nonterminals) ? new InternalNode(rhs[i]) : new LeafNode(
                            rhs[i]);
                    stack.push(child);
                    node.childs.add(0, child);
                }
            }
            else if (node.name.equals(a)) {
                a = token.next_token();
            }
            else {
                System.out.println("syntax error");
            }
        }
        return root;
    }
    
    public Grammar factor(Grammar g) {
        CommonPerfixStruct struct;
        Grammar temp = g;
        int count = 0;
        while ((struct = common_perfix(temp.productions)) != null) {
            String new_nonterminal = struct.lhs + "" + (count++);
            
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
        List<ProductionSet> substitute_list = new ArrayList<ProductionSet>();
        for (int i = 0; i < g.production_set.size(); i++) {
            ProductionSet ai = g.production_set.get(i);
            ProductionSet temp = ai;
            for (int j = 0; j < i; j++) {
                ProductionSet aj = substitute_list.get(j);
                ai = derivation(aj, ai);
            }
            substitute_list.add(ai);
            list.addAll(remove_direct_left_recursion(ai, temp));
        }
        return new Grammar(g.start_symbol, g.terminals, list);
    }
    
    private CommonPerfixStruct common_perfix(List<Production> productions) {
        int max_len = 0;
        Production max_len_p = null;
        for (int i = 0; i < productions.size() - 1; i++) {
            int index = 0;
            Production pi = productions.get(i);
            for (int j = i + 1; j < productions.size(); j++) {
                Production pj = productions.get(j);
                if (pi.lhs.equals(pj.lhs)) {
                    index = longest_common_perfix(pi.rhs, pj.rhs);
                }
            }
            if (index > max_len) {
                max_len = index;
                max_len_p = pi;
            }
        }
        if (max_len > 0) {
            List<Production> list = new ArrayList<Production>();
            for (int i = 0; i < productions.size(); i++) {
                Production p = productions.get(i);
                if (p.lhs.equals(max_len_p.lhs)) {
                    int k = 0;
                    for (int j = 0; j < max_len; j++) {
                        if (p.rhs[j].equals(max_len_p.rhs[j])) {
                            k++;
                        }
                    }
                    
                    if (k == max_len) {
                        list.add(p);
                    }
                }
            }
            return new CommonPerfixStruct(max_len, list);
        }
        return null;
    }
    
}
