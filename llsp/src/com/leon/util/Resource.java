
package com.leon.util;

import java.io.InputStream;

/**
 * @author : Leon
 * @since : 2013-9-30
 * @see :
 */

public class Resource {
    
    public static InputStream getResourceAsStream(String name) {
        return Resource.class.getResourceAsStream(name);
    }
}
