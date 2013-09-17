
package com.leon.simple.calc;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.leon.cc.CCToken;
import com.leon.cc.Syntax;
import com.leon.generator.SyntaxCodeGenerator;
import com.leon.simple.Token;
import com.leon.util.ISymbol;
import com.leon.util.IToken;

/**
 * @author : Leon
 * @since : 2013-9-13
 * @see :
 */

public class Main {
    
    public static void main(String[] args) throws Exception {
        SyntaxCodeGenerator scg = new SyntaxCodeGenerator();
        S o = (S) scg.generate(new File("resourses/calc.g"), new File("resourses/example_calc.ca"), Token.class);
        System.out.println(o.value());
        
        Syntax syntax = (Syntax) scg.generate(new File("resourses/syntax.g"), new File("resourses/syntax.g"),
                CCToken.class);
        System.out.println(syntax.classname);
    }
    
    public static List<ISymbol> getSymbolList(String str) throws IOException {
        IToken t = new Token(new StringReader(str));
        List<ISymbol> list = new ArrayList<ISymbol>();
        while (t.has_next()) {
            list.add(t.next_token());
        }
        return list;
    }
}
