
package com.leon.simple.json;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import com.leon.generator.Compile;

/**
 * @author : Leon
 * @since : 2013-9-18
 * @see :
 */

public class JsonTest {
    
    Logger logger = Logger.getLogger(JsonTest.class.getName());
    
    @Test
    public void testCalc() throws Exception {
        Compile scg = new Compile();
        Json o = (Json) scg.ast(new File("test/com/leon/simple/json/json.g"), new File(
                "test/com/leon/simple/json/example_json.js"), JsonToken.class);
        logger.log(Level.INFO, "begin:" + new Timestamp(System.currentTimeMillis()));
        List<String> list = o.format();
        logger.log(Level.INFO, "end" + new Timestamp(System.currentTimeMillis()));
        for (String line : list) {
            System.out.println(line);
        }
    }
}
