
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
            int i = 0;
            for (String str : value.format()) {
                if (value.format().size() == 1) {
                    list.add(str + ",");
                }
                else {
                    if (i != value.format().size() - 1) {
                        list.add(str);
                    }
                    else {
                        list.add(str + ",");
                    }
                }
                i++;
            }
        }
        return list;
    }
}
