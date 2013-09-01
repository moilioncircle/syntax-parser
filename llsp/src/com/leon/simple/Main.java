
package com.leon.simple;

import static com.leon.util.Utils.fill_first_set;
import static com.leon.util.Utils.fill_follow_set;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.leon.grammar.Grammar;
import com.leon.grammar.Production;
import com.leon.ll.LL1;
import com.leon.lr.LR1;
import com.leon.simple.LexerType;
import com.leon.simple.Token;
import com.leon.tree.cst.CST;
import com.leon.tree.cst.CSTNode;
import com.leon.tree.cst.NodeType;
import com.leon.util.IToken;
import com.leon.util.Stack;

/**
 * @author : Leon
 * @since : 2013-8-11
 * @see :
 */

public class Main {
    
    public static void main(String[] args) throws IOException {
        new Main().do_1_grammar();
        new Main().do_2_grammar();
        new Main().do_3_grammar();
        new Main().do_4_grammar();
        new Main().do_5_grammar();
        new Main().do_6_grammar();
        new Main().do_7_grammar();
        new Main().do_8_grammar();
        new Main().do_9_grammar();
        new Main().do_10_grammar();
        new Main().do_11_grammar();
        new Main().do_12_grammar();
        new Main().do_13_grammar();
        new Main().do_14_grammar();
        new Main().do_15_grammar();
        new Main().do_16_grammar();
        new Main().do_17_grammar();
    }
    
    public void do_1_grammar() throws IOException {
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("S", new String[] { "E", "EOF" }, "new S($0)"));
        list.add(new Production("E", new String[] { "E", "PLUS", "T" }, "new E($0,$2)"));
        list.add(new Production("E", new String[] { "T" }, "new E($0)"));
        list.add(new Production("T", new String[] { "T", "TIMES", "P" }));
        list.add(new Production("T", new String[] { "P" }, "new T($0)"));
        list.add(new Production("P", new String[] { "NUM" }, "new P(\"$0\")"));
        list.add(new Production("P", new String[] { "LPAREN", "E", "RPAREN" }, "new P($1)"));
        Grammar g = new Grammar(list,"S");
        IToken<LexerType> t = new Token(new StringReader("(2+3)*5"));
        LR1 lr = new LR1();
        lr.lr1_driver(g, t);
    }
    
    public void do_2_grammar() throws IOException {
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("S", new String[] { "E", "EOF" }));
        list.add(new Production("E", new String[] { "ID" }));
        list.add(new Production("E", new String[] {}));
        Grammar g = new Grammar(list,"S");
        IToken<LexerType> t = new Token(new StringReader(""));
        LR1 lr = new LR1();
        lr.lr1_driver(g, t);
    }
    
    public void do_3_grammar() throws IOException {
        LL1 c = new LL1();
        List<Production> list = new ArrayList<Production>();
        String start_symbol = "system_goal";
        list.add(new Production("program", new String[] { "BEGIN", "statement_list", "END" }));
        list.add(new Production("statement_list", new String[] { "statement", "statement_tail" }));
        list.add(new Production("statement_tail", new String[] { "statement", "statement_tail" }));
        list.add(new Production("statement_tail", new String[] {}));
        list.add(new Production("statement", new String[] { "ID", "ASSIGN", "expression", "SEMI" }));
        list.add(new Production("statement", new String[] { "READ", "LPAREN", "id_list", "RPAREN", "SEMI" }));
        list.add(new Production("statement", new String[] { "WRITE", "LPAREN", "expr_list", "RPAREN", "SEMI" }));
        list.add(new Production("id_list", new String[] { "ID", "id_tail" }));
        list.add(new Production("id_tail", new String[] { "COMMA", "ID", "id_tail" }));
        list.add(new Production("id_tail", new String[] {}));
        list.add(new Production("expr_list", new String[] { "expression", "expr_tail" }));
        list.add(new Production("expr_tail", new String[] { "COMMA", "expression", "expr_tail" }));
        list.add(new Production("expr_tail", new String[] {}));
        list.add(new Production("expression", new String[] { "primary", "primary_tail" }));
        list.add(new Production("primary_tail", new String[] { "add_op", "primary", "primary_tail" }));
        list.add(new Production("primary_tail", new String[] {}));
        list.add(new Production("primary", new String[] { "LPAREN", "expression", "RPAREN" }));
        list.add(new Production("primary", new String[] { "ID" }));
        list.add(new Production("primary", new String[] { "NUM" }));
        list.add(new Production("add_op", new String[] { "PLUS" }));
        list.add(new Production("add_op", new String[] { "TIMES" }));
        list.add(new Production("system_goal", new String[] { "program", "EOF" }));
        Grammar g = new Grammar(list,start_symbol);
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
            c.predict(g.productions.get(i), first_set, follow_set, g, i + 1, m);
        }
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                System.out.print(m[i][j] + ",");
            }
            System.out.println();
        }
        
        for (int i = 0; i < g.nonterminals.length; i++) {
            c.make_parsing_proc(g.nonterminals[i], m, g);
        }
        IToken<LexerType> t = new Token(new StringReader("begin a:=b*5+c;end"));
        CSTNode root = c.ll1_driver(g, m, t);
        System.out.println("digraph g {");
        System.out.println("\tnode[shape = record, width = .1, height = .1];");
        Stack<CSTNode> stack = new Stack<CSTNode>();
        int k = 0;
        Stack<Integer> k_stack = new Stack<Integer>();
        stack.push(root);
        k_stack.push(k);
        System.out.println("\tnode" + k + "[label = \"{<n> " + root.name + " }\", color = lightgray, style = filled];");
        while (!stack.is_empty()) {
            CSTNode parent = stack.pop();
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
    
    public void do_4_grammar() {
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("stmt", new String[] { "if", "expr", "then", "stmt_list", "end_if" }));
        list.add(new Production("stmt",
                new String[] { "if", "expr", "then", "stmt_list", "else", "stmt_list", "end_if" }));
        Grammar g = new Grammar(list,"stmt");
        Grammar g1 = l.factor(g);
        for (int i = 0; i < g1.productions.size(); i++) {
            System.out.println(g1.productions.get(i));
        }
    }
    
    public void do_5_grammar() {
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("A", new String[] { "a", "b", "B" }));
        list.add(new Production("A", new String[] { "a", "D" }));
        list.add(new Production("A", new String[] { "a", "b", "c", "C" }));
        list.add(new Production("E", new String[] { "a", "b" }));
        Grammar g = new Grammar(list,"A");
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
    
    public void do_6_grammar() {
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("A", new String[] { "a", "D" }));
        list.add(new Production("E", new String[] { "a", "b" }));
        list.add(new Production("A", new String[] { "a", "b", "C" }));
        Grammar g = new Grammar(list,"A");
        Grammar g1 = l.factor(g);
        System.out.println("===============");
        for (int i = 0; i < g1.productions.size(); i++) {
            System.out.println(g1.productions.get(i));
        }
    }
    
    public void do_7_grammar() {
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("A", new String[] { "a" }));
        list.add(new Production("E", new String[] { "a", "b" }));
        list.add(new Production("A", new String[] { "a", "b", "C" }));
        Grammar g = new Grammar(list,"A");
        Grammar g1 = l.factor(g);
        System.out.println("===============");
        for (int i = 0; i < g1.productions.size(); i++) {
            System.out.println(g1.productions.get(i));
        }
    }
    
    public void do_8_grammar() {
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("A", new String[] { "a", "B" }));
        list.add(new Production("A", new String[] { "B", "b" }));
        list.add(new Production("B", new String[] { "A", "c" }));
        list.add(new Production("B", new String[] { "d" }));
        Grammar g = new Grammar(list,"A");
        Grammar g1 = l.remove_left_recursion(g);
        System.out.println("===============");
        for (int i = 0; i < g1.productions.size(); i++) {
            System.out.println(g1.productions.get(i));
        }
    }
    
    public void do_9_grammar() {
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("E", new String[] { "E", "+", "T" }));
        list.add(new Production("E", new String[] { "T" }));
        list.add(new Production("T", new String[] { "T", "*", "P" }));
        list.add(new Production("T", new String[] { "P" }));
        list.add(new Production("P", new String[] { "ID" }));
        Grammar g = new Grammar(list,"E");
        Grammar g1 = l.remove_left_recursion(g);
        System.out.println("===============");
        for (int i = 0; i < g1.productions.size(); i++) {
            System.out.println(g1.productions.get(i));
        }
    }
    
    public void do_10_grammar() {
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("S", new String[] { "E", "$" }));
        list.add(new Production("E", new String[] { "E", "+", "T" }));
        list.add(new Production("E", new String[] { "E", "-", "T" }));
        list.add(new Production("E", new String[] { "T" }));
        list.add(new Production("T", new String[] { "T", "*", "F" }));
        list.add(new Production("T", new String[] { "T", "/", "F" }));
        list.add(new Production("T", new String[] { "F" }));
        list.add(new Production("F", new String[] { "ID" }));
        list.add(new Production("F", new String[] { "NUM" }));
        list.add(new Production("F", new String[] { "(", "E", ")" }));
        Grammar g = new Grammar(list,"S");
        for (int i = 0; i < g.production_set.size(); i++) {
            System.out.println(g.production_set.get(i));
        }
        Grammar g1 = l.remove_left_recursion(g);
        System.out.println("===============");
        for (int i = 0; i < g1.productions.size(); i++) {
            System.out.println(g1.productions.get(i));
        }
    }
    
    public void do_11_grammar() {
        System.out.println("do_11_grammar");
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("B", new String[] { "C", "b" }));
        list.add(new Production("A", new String[] { "B", "a" }));
        list.add(new Production("C", new String[] { "A", "c" }));
        list.add(new Production("C", new String[] {}));
        Grammar g = new Grammar(list,"A");
        Grammar g1 = l.remove_left_recursion(g);
        System.out.println("===============");
        for (int i = 0; i < g1.productions.size(); i++) {
            System.out.println(g1.productions.get(i));
        }
    }
    
    public void do_12_grammar() {
        System.out.println("do_12_grammar");
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("A", new String[] { "B", "a" }));
        list.add(new Production("B", new String[] { "C", "b" }));
        list.add(new Production("C", new String[] { "A", "c" }));
        list.add(new Production("C", new String[] {}));
        Grammar g = new Grammar(list,"A");
        Grammar g1 = l.remove_left_recursion(g);
        System.out.println("===============");
        for (int i = 0; i < g1.productions.size(); i++) {
            System.out.println(g1.productions.get(i));
        }
    }
    
    public void do_13_grammar() {
        System.out.println("do_13_grammar");
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("C", new String[] { "A", "c" }));
        list.add(new Production("A", new String[] { "B", "a" }));
        list.add(new Production("B", new String[] { "C", "b" }));
        list.add(new Production("B", new String[] {}));
        Grammar g = new Grammar(list,"A");
        Grammar g1 = l.remove_left_recursion(g);
        System.out.println("===============");
        for (int i = 0; i < g1.productions.size(); i++) {
            System.out.println(g1.productions.get(i));
        }
    }
    
    public void do_14_grammar() {
        System.out.println("do_14_grammar");
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("B", new String[] { "C", "b" }));
        list.add(new Production("C", new String[] { "B", "c" }));
        list.add(new Production("C", new String[] { "A" }));
        list.add(new Production("A", new String[] { "C", "a" }));
        list.add(new Production("A", new String[] {}));
        Grammar g = new Grammar(list,"A");
        Grammar g1 = l.remove_left_recursion(g);
        System.out.println("===============");
        for (int i = 0; i < g1.productions.size(); i++) {
            System.out.println(g1.productions.get(i));
        }
    }
    
    public void do_15_grammar() throws IOException {
        System.out.println("do_15_grammar");
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("S", new String[] { "E", "EOF" }));
        list.add(new Production("E", new String[] { "T", "E_tail" }));
        list.add(new Production("E_tail", new String[] { "PLUS", "T", "E_tail" }));
        list.add(new Production("E_tail", new String[] { "MINUS", "T", "E_tail" }));
        list.add(new Production("E_tail", new String[] {}));
        list.add(new Production("T", new String[] { "LPAREN", "E", "RPAREN" }));
        list.add(new Production("T", new String[] { "ID" }));
        list.add(new Production("T", new String[] { "NUM" }));
        Grammar g = new Grammar(list,"S");
        int[][] m = l.predict_table(g);
        for (int i = 0; i < g.nonterminals.length; i++) {
            l.make_parsing_proc(g.nonterminals[i], m, g);
        }
        IToken<LexerType> t = new Token(new StringReader("a+(a-4)"));
        CST cpt = new CST(t, g);
        System.out.println(cpt.toString());
    }
    
    public void do_16_grammar() throws IOException {
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("K", new String[] { "S", "EOF" }));
        list.add(new Production("S", new String[] { "V", "ASSIGN", "E" }));
        list.add(new Production("S", new String[] { "E" }));
        list.add(new Production("E", new String[] { "V" }));
        list.add(new Production("V", new String[] { "ID" }));
        list.add(new Production("V", new String[] { "TIMES", "E" }));
        Grammar g = new Grammar(list,"K");
        IToken<LexerType> t = new Token(new StringReader("x:=*x"));
        LR1 lr = new LR1();
        lr.lr1_driver(g, t);
    }
    
    public void do_17_grammar() throws IOException {
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("S", new String[] { "E", "EOF" }));
        list.add(new Production("E", new String[] { "E", "PLUS", "E" }));
        list.add(new Production("E", new String[] { "E", "MINUS", "T" }));
        list.add(new Production("E", new String[] { "E", "TIMES", "E" }));
        list.add(new Production("E", new String[] { "E", "DIVIDE", "E" }));
        list.add(new Production("E", new String[] { "ID" }));
        list.add(new Production("E", new String[] { "NUM" }));
        Grammar g = new Grammar(list,"S");
        IToken<LexerType> t = new Token(new StringReader("5+4/3"));
        LR1 lr = new LR1();
        lr.lr1_driver(g, t);
    }
    
}
