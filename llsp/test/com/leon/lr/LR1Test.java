package com.leon.lr;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.leon.grammar.Grammar;
import com.leon.grammar.Production;
import com.leon.grammar.Terminal;
import com.leon.simple.Token;
import com.leon.util.ISymbol;
import com.leon.util.IToken;


/** @author : Leon
 * @since   : 2013-9-3
 * @see    : 
 */

public class LR1Test {
    @Test
    public void testgrammar1() throws IOException{
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
        System.out.println(lr.lr1_driver(ss));
    }
}
