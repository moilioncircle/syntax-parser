
package com.leon.simple.json;

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

public class JsonTest {
    
    @Test
    public void testCalc() throws Exception {
        Compile scg = new Compile();
        Json o = (Json) scg.ast(new File("resourses/json.g"), new File("resourses/example_json.js"), JsonToken.class);
    }
}
