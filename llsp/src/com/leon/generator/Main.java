package com.leon.generator;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.leon.cc.CCGrammar;
import com.leon.lr.LR1;
import com.leon.simple.Token;
import com.leon.simple.calc.T;
import com.leon.util.ISymbol;
import com.leon.util.IToken;


/** @author : Leon
 * @since   : 2013-9-12
 * @see    : 
 */

public class Main {
    public static void main(String[] args) throws IOException {
        SyntaxCodeGenerator s = new SyntaxCodeGenerator("syntax.g", CCGrammar.class);
        s.generate_file("calc.g", T.class);
        IR ir = new IR("calc.g", T.class);
        Generator g = new Generator(ir);
        
        IToken t = new Token(new StringReader("3+4*6"));
        List<ISymbol> ss = new ArrayList<ISymbol>();
        while (t.has_next()) {
            ss.add(t.next_token());
        }
        
        g.generate(ss);

    }
}


