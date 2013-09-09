
package com.leon.cc;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.leon.grammar.Grammar;
import com.leon.grammar.ProductionSet;
import com.leon.lr.LR1;
import com.leon.util.ISymbol;
import com.leon.util.IToken;

/**
 * @author : Leon
 * @since : 2013-8-30
 * @see :
 */

public class CCGrammar {
    
    public Grammar getGrammar() {
        List<ProductionSet> list = new ArrayList<ProductionSet>();
        list.add(new ProductionSet("program").or("Descriptor", "EOF"));
        list.add(new ProductionSet("Descriptor").or("Declarations", "SectionMarker", "Productions", "Usercode").or(
                "SectionMarker", "Productions", "Usercode"));
        list.add(new ProductionSet("Usercode").or("SectionMarker", "ACTION").or(new String[] { "SectionMarker" }));
        list.add(new ProductionSet("SectionMarker").or("MARK"));
        list.add(new ProductionSet("Declarations").or("Declarations", "Declaration").or("Declaration"));
        list.add(new ProductionSet("Declaration").or("SEMI")
                                                 .or("Precedence", "Tokens")
                                                 .or("NAME", "COLON", "Token")
                                                 .or("START", "COLON", "Token")
                                                 .or("ACTION"));
        list.add(new ProductionSet("Precedence").or("LEFT").or("RIGHT").or("NONASSOC").or("BINARY"));
        list.add(new ProductionSet("Tokens").or("Tokens", "COMMA", "Token").or("Token"));
        list.add(new ProductionSet("Token").or("TOKEN"));
        list.add(new ProductionSet("Productions").or("Productions", "TOKEN", "COLON", "Rules", "SEMI").or("TOKEN",
                "COLON", "Rules", "SEMI"));
        list.add(new ProductionSet("Rules").or("Rules", "OR", "GrammarRule").or("GrammarRule"));
        list.add(new ProductionSet("GrammarRule").or("Rule", "Prec", "ACTION")
                                                 .or("Rule", "ACTION")
                                                 .or("ACTION")
                                                 .or());
        list.add(new ProductionSet("Rule").or("Rule", "TOKEN").or("TOKEN"));
        list.add(new ProductionSet("Prec").or("PREC", "TOKEN"));
        Grammar g = new Grammar("program", list);
        return g;
    }
    
    public static void main(String[] args) throws IOException {
        Grammar g = new CCGrammar().getGrammar();
        InputStreamReader reader = new InputStreamReader(CCGrammar.class.getResourceAsStream("test.g"), "UTF8");
        IToken<CCType> t = new CCToken(reader);
        List<ISymbol<?>> list = new ArrayList<ISymbol<?>>();
        while(t.has_next()){
            list.add(t.next_token());
        }
        LR1 lr1 = new LR1(g);
        long start = System.currentTimeMillis();
        lr1.lr1_driver(list);
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "");
    }
}
