
package com.leon.generator;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.leon.cc.CCGrammar;
import com.leon.cc.CCToken;
import com.leon.grammar.ProductionSet;
import com.leon.grammar.Terminal;
import com.leon.util.ISymbol;
import com.leon.util.IToken;

/**
 * @author : Leon
 * @since : 2013-9-11
 * @see :
 */

public class Main {
    
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(CCGrammar.class.getResourceAsStream("test.g"), "UTF8");
        IToken t = new CCToken(reader);
        List<ISymbol> list = new ArrayList<ISymbol>();
        while (t.has_next()) {
            list.add(t.next_token());
        }
        CodeGenerator c = new CodeGenerator();
        c.t = list;
        c.generate();
        
        System.out.println(c.g.start_symbol);
        for (Terminal terminal : c.g.terminals_list){
            System.out.println(terminal);
        }
        System.out.println(c.g.assoc_list);
        for (ProductionSet ps : c.g.production_set) {
            System.out.println(ps);
        }
    }
    
}
