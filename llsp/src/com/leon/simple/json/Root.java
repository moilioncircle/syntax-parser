
package com.leon.simple.json;

/**
 * @author : Leon
 * @since : 2013-9-20
 * @see :
 */

public class Root {
    
    private JsonObject object;
    private JsonArray  array;
    
    public Root(JsonObject object) {
        this.object = object;
    }
    
    public Root(JsonArray array) {
        this.array = array;
    }
}
