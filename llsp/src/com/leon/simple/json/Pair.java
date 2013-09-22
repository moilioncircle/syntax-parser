
package com.leon.simple.json;

import java.util.ArrayList;
import java.util.List;

import com.leon.util.ISymbol;

/**
 * @author : Leon
 * @since : 2013-9-20
 * @see :
 */

public class Pair implements JsonFormat {
    
    private ISymbol string;
    private Value   value;
    
    public Pair(ISymbol string, Value value) {
        this.string = string;
        this.value = value;
    }
    
    @Override
    public List<String> format() {
        List<String> list = new ArrayList<String>();
        String key_colon = "\"" + (String) string.get_value() + "\" : ";
        int size = value.format().size();
        if (size == 1) {
            list.add(key_colon + value.format().get(0) + ",");
        }
        else {
            list.add(key_colon);
            list.addAll(value.format());
            list.remove(list.size() - 1);
            list.add(value.format().get(size - 1) + ",");
        }
        
        return list;
    }
}
