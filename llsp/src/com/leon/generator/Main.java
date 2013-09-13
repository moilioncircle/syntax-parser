package com.leon.generator;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

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
        SyntaxCodeGenerator s = new SyntaxCodeGenerator();
        s.generate("calc.g", T.class);
        IR ir = new IR();
        IToken t = new Token(new StringReader("3+4*6"));
        List<ISymbol> ss = new ArrayList<ISymbol>();
        while (t.has_next()) {
            ss.add(t.next_token());
        }
        ir.generate(ss);
    }
}

