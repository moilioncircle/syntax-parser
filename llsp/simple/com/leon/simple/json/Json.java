
package com.leon.simple.json;

import java.util.List;

/**
 * @author : Leon
 * @since : 2013-9-20
 * @see :
 */

public class Json implements JsonFormat {
    
    private Root root;
    
    public Json(Root root) {
        this.root = root;
    }
    
    @Override
    public List<String> format() {
        return root.format();
    }
}
