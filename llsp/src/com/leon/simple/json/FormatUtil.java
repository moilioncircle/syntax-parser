
package com.leon.simple.json;

/**
 * @author : Leon
 * @since : 2013-9-21
 * @see :
 */

public class FormatUtil {
    
    public static String fillBlank(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
}
