
package com.leon.simple.calc;

import java.io.File;

import com.leon.cc.CCToken;
import com.leon.cc.Syntax;
import com.leon.generator.Compile;
import com.leon.simple.Token;

/**
 * @author : Leon
 * @since : 2013-9-13
 * @see :
 */

public class Main {
    
    public static void main(String[] args) throws Exception {
        Compile scg = new Compile();
        T o = (T) scg.ast(new File("resourses/calc.g"), new File("resourses/example_calc.ca"), Token.class);
        System.out.println(o.value());
        
        Syntax s = (Syntax)scg.ast(new File("resourses/syntax.g"), new File("resourses/syntax.g"), CCToken.class);
        System.out.println(s.classname);
    }
}
