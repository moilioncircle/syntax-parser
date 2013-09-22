
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
        int i = 0;
        for (String str : value.format()) {
            if (value.format().size() == 1) {
                list.add(key_colon + str + ",");
            }
            else {
                if (i == 0) {
                    list.add(key_colon + str);
                }
                else if (i != value.format().size() - 1) {
                    list.add(FormatUtil.fillBlank(key_colon) + str);
                }
                else {
                    list.add(FormatUtil.fillBlank(key_colon) + str + ",");
                }
            }
            i++;
        }
        return list;
    }
}
