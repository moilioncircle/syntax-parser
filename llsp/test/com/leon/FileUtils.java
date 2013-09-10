
package com.leon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author : Leon
 * @since : 2013-9-10
 * @see :
 */

public class FileUtils {
    
    public static String readFile(String fileName,Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(clazz.getResourceAsStream(fileName)));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                br.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
