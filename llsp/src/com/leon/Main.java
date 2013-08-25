package com.leon;

import static com.leon.util.Utils.fill_first_set;
import static com.leon.util.Utils.fill_follow_set;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.leon.grammar.Grammar;
import com.leon.grammar.Production;
import com.leon.ll.LL1;
import com.leon.tree.cpt.CPNode;
import com.leon.tree.cpt.CPT;
import com.leon.tree.cpt.NodeType;
import com.leon.util.Stack;
import com.leon.util.Token;

/** @author : Leon
 * @since   : 2013-8-11
 * @see    : 
 */

public class Main {
    public static void main(String[] args) {
        new Main().do_1_gammar();
        new Main().do_2_gammar();
        new Main().do_3_gammar();
        new Main().do_4_gammar();
        new Main().do_5_gammar();
        new Main().do_6_gammar();
        new Main().do_7_gammar();
        new Main().do_8_gammar();
        new Main().do_9_gammar();
        new Main().do_10_gammar();
        new Main().do_11_gammar();
        new Main().do_12_gammar();
        new Main().do_13_gammar();
        new Main().do_14_gammar();
        new Main().do_15_gammar();
    }
    
    public void do_1_gammar(){
        List<Production> list = new ArrayList<Production>();
        String[] terminals = new String[]{"a","b","c","d","e"};
        String start_symbol = "S";
        list.add(new Production("S",new String[]{"a","S","e"}));
        list.add(new Production("S",new String[]{"B"}));
        list.add(new Production("B",new String[]{"b","B","e"}));
        list.add(new Production("B",new String[]{"C"}));
        list.add(new Production("C",new String[]{"c","C","e"}));
        list.add(new Production("C",new String[]{"d"}));
        Grammar g = new Grammar(start_symbol,list,terminals);
        Set<String>[] first_set = fill_first_set(g);
        for (int i = 0; i < first_set.length; i++) {
            Set<String> set = first_set[i];
            System.out.println(set);
        }
        Set<String>[] follow_set = fill_follow_set(g, first_set);
        for (int i = 0; i < follow_set.length; i++) {
            Set<String> set = follow_set[i];
            System.out.println(set);
        }
    }
    
    public void do_2_gammar(){
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("S",new String[]{"A","B","c"}));
        list.add(new Production("A",new String[]{"a"}));
        list.add(new Production("A",new String[]{}));
        list.add(new Production("B",new String[]{"b"}));
        list.add(new Production("B",new String[]{}));
        Grammar g = new Grammar("S",list,new String[]{"a","b","c"});
        Set<String>[] first_set = fill_first_set(g);
        for (int i = 0; i < first_set.length; i++) {
            Set<String> set = first_set[i];
            System.out.println(set);
        }
        Set<String>[] follow_set = fill_follow_set(g, first_set);
        for (int i = 0; i < follow_set.length; i++) {
            Set<String> set = follow_set[i];
            System.out.println(set);
        }
    }
    
    public void do_3_gammar(){
        LL1 c= new LL1();
        List<Production> list = new ArrayList<Production>();
        String[] terminals = new String[]{"ID","INTLIT",":=",",",";","+","*","(",")","begin","end","read","write","$"};
        String start_symbol = "system_goal";
        list.add(new Production("program",new String[]{"begin","statement_list","end"}));
        list.add(new Production("statement_list",new String[]{"statement","statement_tail"}));
        list.add(new Production("statement_tail",new String[]{"statement","statement_tail"}));
        list.add(new Production("statement_tail",new String[]{}));
        list.add(new Production("statement",new String[]{"ID",":=","expression",";"}));
        list.add(new Production("statement",new String[]{"read","(","id_list",")",";"}));
        list.add(new Production("statement",new String[]{"write","(","expr_list",")",";"}));
        list.add(new Production("id_list",new String[]{"ID","id_tail"}));
        list.add(new Production("id_tail",new String[]{",","ID","id_tail"}));
        list.add(new Production("id_tail",new String[]{}));
        list.add(new Production("expr_list",new String[]{"expression","expr_tail"}));
        list.add(new Production("expr_tail",new String[]{",","expression","expr_tail"}));
        list.add(new Production("expr_tail",new String[]{}));
        list.add(new Production("expression",new String[]{"primary","primary_tail"}));
        list.add(new Production("primary_tail",new String[]{"add_op","primary","primary_tail"}));
        list.add(new Production("primary_tail",new String[]{}));
        list.add(new Production("primary",new String[]{"(","expression",")"}));
        list.add(new Production("primary",new String[]{"ID"}));
        list.add(new Production("primary",new String[]{"INTLIT"}));
        list.add(new Production("add_op",new String[]{"+"}));
        list.add(new Production("add_op",new String[]{"*"}));
        list.add(new Production("system_goal",new String[]{"program","$"}));
        Grammar g = new Grammar(start_symbol,list,terminals);
        for (int i = 0; i < g.production_set.size(); i++) {
            System.out.println(g.production_set.get(i));
        }
        Set<String>[] first_set = fill_first_set(g);
        for (int i = 0; i < first_set.length; i++) {
            Set<String> set = first_set[i];
            System.out.println(set);
        }
        System.out.println("===============================");
        Set<String>[] follow_set = fill_follow_set(g, first_set);
        for (int i = 0; i < follow_set.length; i++) {
            Set<String> set = follow_set[i];
            System.out.println(set);
        }
        System.out.println("===============================");
        System.out.println(g.terminals.length);
        System.out.println(g.nonterminals.length);
        System.out.println("===============================");
        int[][] m = new int[g.terminals.length][g.nonterminals.length];
        for (int i = 0; i < g.productions.size(); i++) {
            c.predict(g.productions.get(i), first_set, follow_set, g, i+1, m);
        }
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                System.out.print(m[i][j]+",");
            }
            System.out.println();
        }
        
        for (int i = 0; i < g.nonterminals.length; i++) {
            c.make_parsing_proc(g.nonterminals[i], m, g);
        }
        c.token = new Token("begin ID := ID * INTLIT + ID ; end $".split(" "));

        CPNode root = c.ll1_driver(g, m);
        System.out.println("digraph g {");
        System.out.println("\tnode[shape = record, width = .1, height = .1];");
        Stack<CPNode> stack = new Stack<CPNode>();
        int k = 0;
        Stack<Integer> k_stack = new Stack<Integer>();
        stack.push(root);
        k_stack.push(k);
        System.out.println("\tnode" + k + "[label = \"{<n> " + root.name + " }\", color = lightgray, style = filled];");
        while (!stack.is_empty()) {
            CPNode parent = stack.pop();
            String parentNode = "node" + k_stack.pop();
            for (int i = 0; i < parent.childs.size(); i++) {
                String childNode = "node" + (++k);
                if (parent.childs.get(i).type == NodeType.LEAF) {
                    System.out.println("\t" + childNode + "[label = \"{<n> " + parent.childs.get(i).name
                                       + " }\", color = lightblue, style = filled];");
                }
                else {
                    System.out.println("\t" + childNode + "[label = \"{<n> " + parent.childs.get(i).name
                                       + " }\", color = lightgray, style = filled];");
                }
                System.out.println("\t" + parentNode + ":n->" + childNode + ":n;");
                stack.push(parent.childs.get(i));
                k_stack.push(k);
            }
        }
        System.out.println("}");
    }
    
    public void do_4_gammar(){
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("stmt", new String[]{"if","expr","then","stmt_list","end_if"}));
        list.add(new Production("stmt", new String[]{"if","expr","then","stmt_list","else","stmt_list","end_if"}));
        Grammar g = new Grammar("stmt", list,new String[]{"if","expr","then","stmt_list","end_if","else"});
        Grammar g1 = l.factor(g);
        for (int i = 0; i < g1.productions.size(); i++) {
            System.out.println(g1.productions.get(i));
        }
    }
    
    public void do_5_gammar(){
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("A", new String[]{"a","b","B"}));
        list.add(new Production("A", new String[]{"a","D"}));
        list.add(new Production("A", new String[]{"a","b","c","C"}));
        list.add(new Production("E", new String[]{"a","b"}));
        
        Grammar g = new Grammar("A", list, new String[]{"a","b","c","B","C","D"});
        Grammar g1 = l.factor(g);
        System.out.println("=========");
        for (int i = 0; i < g1.productions.size(); i++) {
            System.out.println(g1.productions.get(i));
        }
        System.out.println("=========");
        Grammar g2 = l.factor(g1);
        for (int i = 0; i < g2.productions.size(); i++) {
            System.out.println(g2.productions.get(i));
        }
    }
    
    public void do_6_gammar(){
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("A", new String[]{"a","D"}));
        list.add(new Production("E", new String[]{"a","b"}));
        list.add(new Production("A", new String[]{"a","b","C"}));
        Grammar g = new Grammar("A", list, new String[]{"a","b","C","D"});
        Grammar g1 = l.factor(g);
        System.out.println("===============");
        for (int i = 0; i < g1.productions.size(); i++) {
            System.out.println(g1.productions.get(i));
        }
    }
    
    public void do_7_gammar(){
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("A", new String[]{"a"}));
        list.add(new Production("E", new String[]{"a","b"}));
        list.add(new Production("A", new String[]{"a","b","C"}));
        Grammar g = new Grammar("A", list, new String[]{"a","b","C","D"});
        Grammar g1 = l.factor(g);
        System.out.println("===============");
        for (int i = 0; i < g1.productions.size(); i++) {
            System.out.println(g1.productions.get(i));
        }
    }
    
    public void do_8_gammar(){
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("A", new String[]{"a","B"}));
        list.add(new Production("A", new String[]{"B","b"}));
        list.add(new Production("B", new String[]{"A","c"}));
        list.add(new Production("B", new String[]{"d"}));
        Grammar g = new Grammar("A", list, new String[]{"a","b","c","d"});
        Grammar g1 = l.remove_left_recursion(g);
        System.out.println("===============");
        for (int i = 0; i < g1.productions.size(); i++) {
            System.out.println(g1.productions.get(i));
        }
    }
    
    public void do_9_gammar(){
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("E", new String[]{"E","+","T"}));
        list.add(new Production("E", new String[]{"T"}));
        list.add(new Production("T", new String[]{"T","*","P"}));
        list.add(new Production("T", new String[]{"P"}));
        list.add(new Production("P", new String[]{"ID"}));
        Grammar g = new Grammar("E", list, new String[]{"+","*","ID"});
        Grammar g1 = l.remove_left_recursion(g);
        System.out.println("===============");
        for (int i = 0; i < g1.productions.size(); i++) {
            System.out.println(g1.productions.get(i));
        }
    }
    
    public void do_10_gammar(){
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("S", new String[]{"E","$"}));
        list.add(new Production("E", new String[]{"E","+","T"}));
        list.add(new Production("E", new String[]{"E","-","T"}));
        list.add(new Production("E", new String[]{"T"}));
        list.add(new Production("T", new String[]{"T","*","F"}));
        list.add(new Production("T", new String[]{"T","/","F"}));
        list.add(new Production("T", new String[]{"F"}));
        list.add(new Production("F", new String[]{"ID"}));
        list.add(new Production("F", new String[]{"NUM"}));
        list.add(new Production("F", new String[]{"(","E",")"}));
        Grammar g = new Grammar("S", list, new String[]{"$","+","-","*","/","ID","NUM","(",")"});
        for (int i = 0; i < g.production_set.size(); i++) {
            System.out.println(g.production_set.get(i));
        }
        Grammar g1 = l.remove_left_recursion(g);
        System.out.println("===============");
        for (int i = 0; i < g1.productions.size(); i++) {
            System.out.println(g1.productions.get(i));
        }
    }

    public void do_11_gammar(){
        System.out.println("do_11_gammar");
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("B", new String[]{"C","b"}));
        list.add(new Production("A", new String[]{"B","a"}));
        list.add(new Production("C", new String[]{"A","c"}));
        list.add(new Production("C", new String[]{}));
        Grammar g = new Grammar("A", list, new String[]{"a","b","c"});
        Grammar g1 = l.remove_left_recursion(g);
        System.out.println("===============");
        for (int i = 0; i < g1.productions.size(); i++) {
            System.out.println(g1.productions.get(i));
        }
    }
    
    public void do_12_gammar(){
        System.out.println("do_12_gammar");
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("A", new String[]{"B","a"}));
        list.add(new Production("B", new String[]{"C","b"}));
        list.add(new Production("C", new String[]{"A","c"}));
        list.add(new Production("C", new String[]{}));
        Grammar g = new Grammar("A", list, new String[]{"a","b","c"});
        Grammar g1 = l.remove_left_recursion(g);
        System.out.println("===============");
        for (int i = 0; i < g1.productions.size(); i++) {
            System.out.println(g1.productions.get(i));
        }
    }
    
    public void do_13_gammar(){
        System.out.println("do_13_gammar");
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("C", new String[]{"A","c"}));
        list.add(new Production("A", new String[]{"B","a"}));
        list.add(new Production("B", new String[]{"C","b"}));
        list.add(new Production("B", new String[]{}));
        
        Grammar g = new Grammar("A", list, new String[]{"a","b","c"});
        Grammar g1 = l.remove_left_recursion(g);
        System.out.println("===============");
        for (int i = 0; i < g1.productions.size(); i++) {
            System.out.println(g1.productions.get(i));
        }
    }
    
    public void do_14_gammar(){
        System.out.println("do_14_gammar");
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("B", new String[]{"C","b"}));
        list.add(new Production("C", new String[]{"B","c"}));
        list.add(new Production("C", new String[]{"A"}));
        list.add(new Production("A", new String[]{"C","a"}));
        list.add(new Production("A", new String[]{}));
        Grammar g = new Grammar("A", list, new String[]{"a","b","c"});
        Grammar g1 = l.remove_left_recursion(g);
        System.out.println("===============");
        for (int i = 0; i < g1.productions.size(); i++) {
            System.out.println(g1.productions.get(i));
        }
    }
    
    public void do_15_gammar(){
        System.out.println("do_15_gammar");
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("S", new String[]{"E","$"}));
        list.add(new Production("E", new String[]{"T","E_tail"}));
        list.add(new Production("E_tail", new String[]{"+","T","E_tail"}));
        list.add(new Production("E_tail", new String[]{"-","T","E_tail"}));
        list.add(new Production("E_tail", new String[]{}));
        list.add(new Production("T", new String[]{"(","E",")"}));
        list.add(new Production("T", new String[]{"id"}));
        list.add(new Production("T", new String[]{"num"}));
        
        Grammar g = new Grammar("S", list, new String[]{"+","-","(",")","id","num","$"});
        int[][] m = l.predict_table(g);
        for (int i = 0; i < g.nonterminals.length; i++) {
            l.make_parsing_proc(g.nonterminals[i], m, g);
        }
        CPT cpt = new CPT(new Token("id + ( id - num ) $".split(" ")),g);
        System.out.println(cpt.toString());
    }
}