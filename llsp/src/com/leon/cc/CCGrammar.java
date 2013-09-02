
package com.leon.cc;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.leon.grammar.Grammar;
import com.leon.grammar.ProductionSet;
import com.leon.lr.LR1;
import com.leon.util.IToken;

/**
 * @author : Leon
 * @since : 2013-8-30
 * @see :
 */

public class CCGrammar {
    
    public Grammar getGrammar() {
        List<ProductionSet> list = new ArrayList<ProductionSet>();
        list.add(new ProductionSet("program").add_rhs("Descriptor", "EOF"));
        list.add(new ProductionSet("Descriptor").add_rhs("Declarations", "SectionMarker", "Productions", "Usercode")
                                                .add_rhs("SectionMarker", "Productions", "Usercode"));
        list.add(new ProductionSet("Usercode").add_rhs("SectionMarker", "ACTION").add_rhs(
                new String[] { "SectionMarker" }));
        list.add(new ProductionSet("SectionMarker").add_rhs("MARK"));
        list.add(new ProductionSet("Declarations").add_rhs("Declarations", "Declaration").add_rhs("Declaration"));
        list.add(new ProductionSet("Declaration").add_rhs("SEMI")
                                                 .add_rhs("Precedence", "Tokens")
                                                 .add_rhs("NAME", "COLON", "Token")
                                                 .add_rhs("ACTION"));
        list.add(new ProductionSet("Precedence").add_rhs("LEFT").add_rhs("RIGHT"));
        list.add(new ProductionSet("Tokens").add_rhs("Tokens", "COMMA", "Token").add_rhs("Token"));
        list.add(new ProductionSet("Token").add_rhs("TOKEN"));
        list.add(new ProductionSet("Productions").add_rhs("Productions", "TOKEN", "COLON", "Rules", "SEMI").add_rhs(
                "TOKEN", "COLON", "Rules", "SEMI"));
        list.add(new ProductionSet("Rules").add_rhs("Rules", "OR", "GrammarRule").add_rhs("GrammarRule"));
        list.add(new ProductionSet("GrammarRule").add_rhs("Rule", "ACTION").add_rhs(new String[] { "ACTION" }));
        list.add(new ProductionSet("Rule").add_rhs("Rule", "TOKEN").add_rhs("TOKEN"));
        Grammar g = new Grammar("program", list);
        return g;
    }
    
    public static void main(String[] args) throws IOException {
        Grammar g = new CCGrammar().getGrammar();
        InputStreamReader reader = new InputStreamReader(CCGrammar.class.getResourceAsStream("test.g"), "UTF8");
        IToken<CCType> t = new CCToken(reader);
        LR1 lr1 = new LR1();
        lr1.lr1_driver(g, t);
    }
}
