
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
        List<String> value_list = value.format();
        if (value_list.size() == 1) {
            list.add(key_colon + value_list.get(0) + ",");
        }
        else {
            list.add(key_colon + value_list.get(0));
            for (int i = 1; i < value_list.size() - 1; i++) {
                list.add(value_list.get(i));
            }
            list.add(value_list.get(value_list.size() - 1) + ",");
        }
        
        return list;
    }
}
