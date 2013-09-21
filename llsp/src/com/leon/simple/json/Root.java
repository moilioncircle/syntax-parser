
package com.leon.simple.json;

import java.util.List;

/**
 * @author : Leon
 * @since : 2013-9-20
 * @see :
 */

public class Root implements JsonFormat {
    
    private JsonObject object;
    private JsonArray  array;
    
    public Root(JsonObject object) {
        this.object = object;
    }
    
    public Root(JsonArray array) {
        this.array = array;
    }
    
    @Override
    public List<String> format() {
        if (object != null) {
            return object.format();
        }
        else {
            return array.format();
        }
    }
}
