
package com.leon.generator;

import java.util.List;

import com.leon.simple.calc.T;
import com.leon.util.ISymbol;
import com.leon.util.Utils;

/**
 * @author : Leon
 * @since : 2013-9-13
 * @see :
 */

public class Main {
    
    public static void main(String[] args) throws Exception {
        SyntaxCodeGenerator scg = new SyntaxCodeGenerator();
        List<ISymbol> parse_str = Utils.getSymbolList("2+3*6+(5*4+3*8+9)");
        scg.generate("calc.g", T.class,parse_str);
    }
}