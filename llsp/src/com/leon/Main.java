package com.leon;

import static com.leon.util.Utils.fill_first_set;
import static com.leon.util.Utils.fill_follow_set;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.leon.grammar.Grammar;
import com.leon.grammar.Production;
import com.leon.ll.LL1;

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
        c.grammar_str = "begin ID := ID * INTLIT + ID ; end $".split(" ");
        for (int i = 0; i < c.grammar_str.length; i++) {
            System.out.println(c.grammar_str[i]);
        }
        c.ll1_driver(g, m);
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
}