
package com.leon.simple.json;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Leon
 * @since : 2013-9-20
 * @see :
 */

public class JsonArray implements JsonFormat {
    
    private Element element;
    
    public JsonArray() {
        
    }
    
    public JsonArray(Element element) {
        this.element = element;
    }
    
    @Override
    public List<String> format() {
        List<String> list = new ArrayList<String>();
        list.add("[");
        if (element != null) {
            for (int i = 0; i < element.format().size(); i++) {
                String str = element.format().get(i);
                if (i != element.format().size() - 1) {
                    list.add(new StringBuilder().append("    ").append(str).toString());
                }
                else {
                    StringBuilder sb = new StringBuilder().append("    ").append(str);
                    sb.deleteCharAt(sb.length() - 1);
                    list.add(sb.toString());
                }
            }
        }
        list.add("]");
        return list;
    }
}
