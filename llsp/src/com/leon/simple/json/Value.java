
package com.leon.simple.json;

import java.util.ArrayList;
import java.util.List;

import com.leon.util.ISymbol;

/**
 * @author : Leon
 * @since : 2013-9-20
 * @see :
 */

public class Value implements JsonFormat {
    
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
    
    @Override
    public List<String> format() {
        List<String> list = new ArrayList<String>();
        if (type == ValueType.STRING) {
            list.add("\"" + (String) value.get_value() + "\"");
        }
        else if (type == ValueType.NUMBER) {
            list.add((Double) value.get_value() + "");
        }
        else if (type == ValueType.TRUE || type == ValueType.FALSE || type == ValueType.NULL) {
            list.add((String) value.get_value());
        }
        else if (type == ValueType.OBJECT) {
            list.addAll(object_value.format());
        }
        else if (type == ValueType.ARRAY) {
            list.addAll(array_value.format());
        }
        return list;
    }
    
}
