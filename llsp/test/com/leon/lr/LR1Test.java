
package com.leon.lr;

import static com.leon.util.Utils.fill_first_set;
import static com.leon.util.Utils.fill_follow_set;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.leon.FileUtils;
import com.leon.grammar.Assoc;
import com.leon.grammar.Associativity;
import com.leon.grammar.Grammar;
import com.leon.grammar.Prec;
import com.leon.grammar.Production;
import com.leon.grammar.Terminal;
import com.leon.ll.LL1;
import com.leon.simple.Token;
import com.leon.tree.cst.CST;
import com.leon.util.ISymbol;
import com.leon.util.IToken;

/**
 * @author : Leon
 * @since : 2013-9-3
 * @see :
 */

public class LR1Test {
    
    @Test
    public void do_1_grammar() throws IOException {
        System.out.println("do_1_grammar");
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("S", new String[] { "E", "EOF" }, "new S($0)"));
        list.add(new Production("E", new String[] { "E", "PLUS", "T" }, "new E($0,$2)"));
        list.add(new Production("E", new String[] { "T" }, "new E($0)"));
        list.add(new Production("T", new String[] { "T", "TIMES", "P" }));
        list.add(new Production("T", new String[] { "P" }, "new T($0)"));
        list.add(new Production("P", new String[] { "NUM" }, "new P(\"$0\")"));
        list.add(new Production("P", new String[] { "LPAREN", "E", "RPAREN" }, "new P($1)"));
        List<Terminal> t_list = new ArrayList<Terminal>();
        t_list.add(new Terminal("EOF", 20, 20));
        t_list.add(new Terminal("PLUS", 3, 3));
        t_list.add(new Terminal("TIMES", 5, 5));
        t_list.add(new Terminal("NUM", 8, 8));
        t_list.add(new Terminal("LPAREN", 9, 9));
        t_list.add(new Terminal("RPAREN", 7, 7));
        Grammar g = new Grammar(list, "S", t_list);
        IToken t = new Token(new StringReader(")3+(4+"));
        LR1 lr = new LR1(g);
        List<ISymbol> ss = new ArrayList<ISymbol>();
        while (t.has_next()) {
            ss.add(t.next_token());
        }
        Assert.assertEquals(FileUtils.readFile("do_1_grammar.txt", this.getClass()), lr.lr1_driver(ss));
    }
    
    @Test
    public void do_2_grammar() throws IOException {
        System.out.println("do_2_grammar");
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("S", new String[] { "E", "EOF" }));
        list.add(new Production("E", new String[] { "ID" }));
        list.add(new Production("E", new String[] {}));
        List<Terminal> t_list = new ArrayList<Terminal>();
        t_list.add(new Terminal("EOF", 20, 20));
        t_list.add(new Terminal("ID", 9, 9));
        Grammar g = new Grammar(list, "S", t_list);
        IToken t = new Token(new StringReader(""));
        LR1 lr = new LR1(g);
        List<ISymbol> ss = new ArrayList<ISymbol>();
        while (t.has_next()) {
            ss.add(t.next_token());
        }
        Assert.assertEquals(FileUtils.readFile("do_2_grammar.txt", this.getClass()), lr.lr1_driver(ss));
    }
    
    @Test
    public void do_3_grammar() throws IOException {
        System.out.println("do_3_grammar");
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
        List<Terminal> t_list = new ArrayList<Terminal>();
        t_list.add(new Terminal("EOF", 20, 20));
        t_list.add(new Terminal("BEGIN", 10, 10));
        t_list.add(new Terminal("END", 11, 11));
        t_list.add(new Terminal("ID", 9, 9));
        t_list.add(new Terminal("ASSIGN", 14, 14));
        t_list.add(new Terminal("SEMI", 1, 1));
        t_list.add(new Terminal("READ", 12, 12));
        t_list.add(new Terminal("LPAREN", 9, 9));
        t_list.add(new Terminal("RPAREN", 7, 7));
        t_list.add(new Terminal("WRITE", 13, 13));
        t_list.add(new Terminal("COMMA", 2, 2));
        t_list.add(new Terminal("NUM", 8, 8));
        t_list.add(new Terminal("PLUS", 3, 3));
        t_list.add(new Terminal("TIMES", 5, 5));
        Grammar g = new Grammar(list, start_symbol, t_list);
        Set<String>[] first_set = fill_first_set(g);
        Set<String>[] follow_set = fill_follow_set(g, first_set);
        int[][] m = new int[g.terminals.length][g.nonterminals.length];
        for (int i = 0; i < g.productions.size(); i++) {
            c.predict(g.productions.get(i), first_set, follow_set, g, i + 1, m);
        }
        
        for (int i = 0; i < g.nonterminals.length; i++) {
            c.make_parsing_proc(g.nonterminals[i], m, g);
        }
        IToken t = new Token(new StringReader("begin a:=b*5+c;end"));
        CST cst = new CST(t, g);
        System.out.println(cst.toString());
        Assert.assertEquals(FileUtils.readFile("do_3_grammar.txt", this.getClass()), cst.toString());
    }
    
    @Test
    public void do_4_grammar() throws IOException {
        System.out.println("do_4_grammar");
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("stmt", new String[] { "if", "expr", "then", "stmt_list", "end_if" }));
        list.add(new Production("stmt",
                new String[] { "if", "expr", "then", "stmt_list", "else", "stmt_list", "end_if" }));
        Grammar g = new Grammar(list, "stmt", null);
        Grammar g1 = l.factor(g);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < g1.productions.size(); i++) {
            sb.append(g1.productions.get(i) + "\n");
        }
        Assert.assertEquals(FileUtils.readFile("do_4_grammar.txt", this.getClass()), sb.toString());
    }
    
    @Test
    public void do_5_grammar() {
        System.out.println("do_5_grammar");
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("A", new String[] { "a", "b", "B" }));
        list.add(new Production("A", new String[] { "a", "D" }));
        list.add(new Production("A", new String[] { "a", "b", "c", "C" }));
        list.add(new Production("E", new String[] { "a", "b" }));
        Grammar g = new Grammar(list, "A", null);
        Grammar g1 = l.factor(g);
        StringBuilder sb = new StringBuilder();
        sb.append("=========\n");
        for (int i = 0; i < g1.productions.size(); i++) {
            sb.append(g1.productions.get(i) + "\n");
        }
        sb.append("=========\n");
        Grammar g2 = l.factor(g1);
        for (int i = 0; i < g2.productions.size(); i++) {
            sb.append(g2.productions.get(i) + "\n");
        }
        Assert.assertEquals(FileUtils.readFile("do_5_grammar.txt", this.getClass()), sb.toString());
    }
    
    @Test
    public void do_6_grammar() {
        System.out.println("do_6_grammar");
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("A", new String[] { "a", "D" }));
        list.add(new Production("E", new String[] { "a", "b" }));
        list.add(new Production("A", new String[] { "a", "b", "C" }));
        Grammar g = new Grammar(list, "A", null);
        Grammar g1 = l.factor(g);
        StringBuilder sb = new StringBuilder();
        sb.append("===============\n");
        for (int i = 0; i < g1.productions.size(); i++) {
            sb.append(g1.productions.get(i) + "\n");
        }
        Assert.assertEquals(FileUtils.readFile("do_6_grammar.txt", this.getClass()), sb.toString());
    }
    
    @Test
    public void do_7_grammar() {
        System.out.println("do_7_grammar");
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("A", new String[] { "a" }));
        list.add(new Production("E", new String[] { "a", "b" }));
        list.add(new Production("A", new String[] { "a", "b", "C" }));
        Grammar g = new Grammar(list, "A", null);
        Grammar g1 = l.factor(g);
        StringBuilder sb = new StringBuilder();
        sb.append("===============\n");
        for (int i = 0; i < g1.productions.size(); i++) {
            sb.append(g1.productions.get(i) + "\n");
        }
        Assert.assertEquals(FileUtils.readFile("do_7_grammar.txt", this.getClass()), sb.toString());
    }
    
    @Test
    public void do_8_grammar() {
        System.out.println("do_8_grammar");
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("A", new String[] { "a", "B" }));
        list.add(new Production("A", new String[] { "B", "b" }));
        list.add(new Production("B", new String[] { "A", "c" }));
        list.add(new Production("B", new String[] { "d" }));
        Grammar g = new Grammar(list, "A", null);
        Grammar g1 = l.remove_left_recursion(g);
        StringBuilder sb = new StringBuilder();
        sb.append("===============\n");
        for (int i = 0; i < g1.productions.size(); i++) {
            sb.append(g1.productions.get(i) + "\n");
        }
        Assert.assertEquals(FileUtils.readFile("do_8_grammar.txt", this.getClass()), sb.toString());
    }
    
    @Test
    public void do_9_grammar() {
        System.out.println("do_9_grammar");
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("E", new String[] { "E", "+", "T" }));
        list.add(new Production("E", new String[] { "T" }));
        list.add(new Production("T", new String[] { "T", "*", "P" }));
        list.add(new Production("T", new String[] { "P" }));
        list.add(new Production("P", new String[] { "ID" }));
        Grammar g = new Grammar(list, "E", null);
        Grammar g1 = l.remove_left_recursion(g);
        StringBuilder sb = new StringBuilder();
        sb.append("===============\n");
        for (int i = 0; i < g1.productions.size(); i++) {
            sb.append(g1.productions.get(i) + "\n");
        }
        Assert.assertEquals(FileUtils.readFile("do_9_grammar.txt", this.getClass()), sb.toString());
    }
    
    @Test
    public void do_10_grammar() {
        System.out.println("do_10_grammar");
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
        Grammar g = new Grammar(list, "S", null);
        Grammar g1 = l.remove_left_recursion(g);
        StringBuilder sb = new StringBuilder();
        sb.append("===============\n");
        for (int i = 0; i < g1.productions.size(); i++) {
            sb.append(g1.productions.get(i) + "\n");
        }
        Assert.assertEquals(FileUtils.readFile("do_10_grammar.txt", this.getClass()), sb.toString());
    }
    
    @Test
    public void do_11_grammar() {
        System.out.println("do_11_grammar");
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("B", new String[] { "C", "b" }));
        list.add(new Production("A", new String[] { "B", "a" }));
        list.add(new Production("C", new String[] { "A", "c" }));
        list.add(new Production("C", new String[] {}));
        Grammar g = new Grammar(list, "A", null);
        Grammar g1 = l.remove_left_recursion(g);
        StringBuilder sb = new StringBuilder();
        sb.append("===============\n");
        for (int i = 0; i < g1.productions.size(); i++) {
            sb.append(g1.productions.get(i) + "\n");
        }
        Assert.assertEquals(FileUtils.readFile("do_11_grammar.txt", this.getClass()), sb.toString());
    }
    
    @Test
    public void do_12_grammar() {
        System.out.println("do_12_grammar");
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("A", new String[] { "B", "a" }));
        list.add(new Production("B", new String[] { "C", "b" }));
        list.add(new Production("C", new String[] { "A", "c" }));
        list.add(new Production("C", new String[] {}));
        Grammar g = new Grammar(list, "A", null);
        Grammar g1 = l.remove_left_recursion(g);
        StringBuilder sb = new StringBuilder();
        sb.append("===============\n");
        for (int i = 0; i < g1.productions.size(); i++) {
            sb.append(g1.productions.get(i) + "\n");
        }
        Assert.assertEquals(FileUtils.readFile("do_12_grammar.txt", this.getClass()), sb.toString());
    }
    
    @Test
    public void do_13_grammar() {
        System.out.println("do_13_grammar");
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("C", new String[] { "A", "c" }));
        list.add(new Production("A", new String[] { "B", "a" }));
        list.add(new Production("B", new String[] { "C", "b" }));
        list.add(new Production("B", new String[] {}));
        Grammar g = new Grammar(list, "A", null);
        Grammar g1 = l.remove_left_recursion(g);
        StringBuilder sb = new StringBuilder();
        sb.append("===============\n");
        for (int i = 0; i < g1.productions.size(); i++) {
            sb.append(g1.productions.get(i) + "\n");
        }
        Assert.assertEquals(FileUtils.readFile("do_13_grammar.txt", this.getClass()), sb.toString());
    }
    
    @Test
    public void do_14_grammar() {
        System.out.println("do_14_grammar");
        LL1 l = new LL1();
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("B", new String[] { "C", "b" }));
        list.add(new Production("C", new String[] { "B", "c" }));
        list.add(new Production("C", new String[] { "A" }));
        list.add(new Production("A", new String[] { "C", "a" }));
        list.add(new Production("A", new String[] {}));
        Grammar g = new Grammar(list, "A", null);
        Grammar g1 = l.remove_left_recursion(g);
        StringBuilder sb = new StringBuilder();
        sb.append("===============\n");
        for (int i = 0; i < g1.productions.size(); i++) {
            sb.append(g1.productions.get(i) + "\n");
        }
        Assert.assertEquals(FileUtils.readFile("do_14_grammar.txt", this.getClass()), sb.toString());
    }
    
    @Test
    public void do_15_grammar() throws IOException {
        System.out.println("do_15_grammar");
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("S", new String[] { "E", "EOF" }));
        list.add(new Production("E", new String[] { "T", "E_tail" }));
        list.add(new Production("E_tail", new String[] { "PLUS", "T", "E_tail" }));
        list.add(new Production("E_tail", new String[] { "MINUS", "T", "E_tail" }));
        list.add(new Production("E_tail", new String[] {}));
        list.add(new Production("T", new String[] { "LPAREN", "E", "RPAREN" }));
        list.add(new Production("T", new String[] { "ID" }));
        list.add(new Production("T", new String[] { "NUM" }));
        Grammar g = new Grammar(list, "S", null);
        IToken t = new Token(new StringReader("a+(a-4)"));
        CST cst = new CST(t, g);
        Assert.assertEquals(FileUtils.readFile("do_15_grammar.txt", this.getClass()), cst.toString());
    }
    
    @Test
    public void do_16_grammar() throws IOException {
        System.out.println("do_16_grammar");
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("K", new String[] { "S", "EOF" }));
        list.add(new Production("S", new String[] { "V", "ASSIGN", "E" }));
        list.add(new Production("S", new String[] { "E" }));
        list.add(new Production("E", new String[] { "V" }));
        list.add(new Production("V", new String[] { "ID" }));
        list.add(new Production("V", new String[] { "TIMES", "E" }));
        Grammar g = new Grammar(list, "K", null);
        IToken t = new Token(new StringReader("x:=*x"));
        LR1 lr = new LR1(g);
        List<ISymbol> ss = new ArrayList<ISymbol>();
        while (t.has_next()) {
            ss.add(t.next_token());
        }
        Assert.assertEquals(FileUtils.readFile("do_16_grammar.txt", this.getClass()), lr.lr1_driver(ss));
    }
    
    @Test
    public void do_17_grammar() throws IOException {
        System.out.println("do_17_grammar");
        List<Assoc> assoc_list = new ArrayList<Assoc>();
        assoc_list.add(new Assoc(1, Associativity.LEFT).add_symbol("PLUS").add_symbol("MINUS"));
        assoc_list.add(new Assoc(2, Associativity.LEFT).add_symbol("TIMES").add_symbol("DIVIDE"));
        assoc_list.add(new Assoc(3, Associativity.RIGHT).add_symbol("UMINUS"));
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("S", new String[] { "E", "EOF" }));
        list.add(new Production("E", new String[] { "E", "PLUS", "E" }));
        list.add(new Production("E", new String[] { "E", "MINUS", "E" }));
        list.add(new Production("E", new String[] { "E", "TIMES", "E" }));
        list.add(new Production("E", new String[] { "E", "DIVIDE", "E" }));
        list.add(new Production("E", new String[] { "ID" }));
        list.add(new Production("E", new String[] { "NUM" }));
        list.add(new Production("E", new String[] { "MINUS", "NUM" }, new Prec("UMINUS")));
        Grammar g = new Grammar(list, "S", null, assoc_list);
        IToken t = new Token(new StringReader("3-5*-4/3"));
        LR1 lr = new LR1(g);
        List<ISymbol> ss = new ArrayList<ISymbol>();
        while (t.has_next()) {
            ss.add(t.next_token());
        }
        Assert.assertEquals(FileUtils.readFile("do_17_grammar.txt", this.getClass()), lr.lr1_driver(ss));
    }
}
