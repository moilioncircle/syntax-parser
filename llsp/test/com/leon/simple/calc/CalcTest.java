
package com.leon.simple.calc;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.leon.generator.Compile;
import com.leon.simple.calc.Token;

/**
 * @author : Leon
 * @since : 2013-9-18
 * @see :
 */

public class CalcTest {
    
    @Test
    public void testCalc() throws Exception {
        Compile scg = new Compile();
        T o = (T) scg.ast(new File("resourses/calc.g"), new File("resourses/example_calc.ca"), Token.class);
        Assert.assertEquals(7229, o.value());
    }
}
