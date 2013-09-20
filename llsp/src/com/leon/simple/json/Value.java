
package com.leon.simple.json;

import com.leon.util.ISymbol;

/**
 * @author : Leon
 * @since : 2013-9-20
 * @see :
 */

public class Value {
    
    private ISymbol    value;
    private ValueType  type;
    private JsonObject object_value;
    private JsonArray  array_value;
    
    public Value(ISymbol value, ValueType type) {
        this.value = value;
        this.type = type;
    }
    
    public Value(JsonObject object_value, ValueType type) {
        this.object_value = object_value;
        this.type = type;
    }
    
    public Value(JsonArray array_value, ValueType type) {
        this.array_value = array_value;
        this.type = type;
    }
    
}
