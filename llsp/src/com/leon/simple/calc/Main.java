
package com.leon.simple.calc;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
        List<ISymbol> parse_str = getSymbolList("2+3*6+(5*4+3*8+9)*-5");
        Object o = scg.generate(new File("resourses/calc.g"), parse_str, "CalcGenerator");
        S s = (S) o;
        System.out.println(s.value());
    }
    
    public static List<ISymbol> getSymbolList(String str) throws UnsupportedEncodingException, IOException {
        IToken t = new Token(new StringReader(str));
        List<ISymbol> list = new ArrayList<ISymbol>();
        while (t.has_next()) {
            list.add(t.next_token());
        }
        return list;
    }
}
