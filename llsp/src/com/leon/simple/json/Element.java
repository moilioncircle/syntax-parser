
package com.leon.simple.json;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Leon
 * @since : 2013-9-20
 * @see :
 */

public class Element implements JsonFormat {
    
    public List<Value> element_list;
    
    public Element(Value value) {
        if (element_list == null) {
            element_list = new ArrayList<Value>();
        }
        element_list.add(value);
    }
    
    public Element(Value value, Element element) {
        if (element_list == null) {
            element_list = new ArrayList<Value>();
        }
        element_list.add(value);
        element_list.addAll(element.element_list);
    }
    
    @Override
    public List<String> format() {
        List<String> list = new ArrayList<String>();
        for (Value value : element_list) {
            List<String> value_list = value.format();
            if (value_list.size() == 1) {
                list.add(value_list.get(0) + ",");
            }
            else {
                list.addAll(value_list);
                list.remove(list.size() - 1);
                list.add(value_list.get(value_list.size() - 1) + ",");
            }
        }
        return list;
    }
}
