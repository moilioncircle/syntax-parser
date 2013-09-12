
package com.leon.generator;

import java.io.IOException;
import java.util.List;

import com.leon.cc.Syntax;
import com.leon.lr.LR1;
import com.leon.util.ISymbol;


/**
 * @author : Leon
 * @since : 2013-9-12
 * @see :
 */

public class Generator {
    private IR ir;
    public Generator(IR ir){
        this.ir = ir;
    }
    public void generate(List<ISymbol> list) throws IOException{
        Syntax s = ir.s;
        System.out.println(s.header);
        LR1 lr1 = new LR1(s.g);
        lr1.lr1_driver(list);
        System.out.println("\tpublic void generate(List<ISymbol> t){");
        System.out.println("\t\t"+lr1.semantic.top()+";");
        System.out.println("\t}");
        System.out.println(s.footer);
    }
}
