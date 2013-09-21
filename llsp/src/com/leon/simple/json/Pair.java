
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
        String key_colon = new StringBuilder().append("\"")
                                              .append((String) string.get_value())
                                              .append("\" : ")
                                              .toString();
        int i = 0;
        for (String str : value.format()) {
            if (value.format().size() == 1) {
                list.add(new StringBuilder().append(key_colon).append(str).append(",").toString());
            }
            else {
                if (i == 0) {
                    list.add(new StringBuilder().append(key_colon).append(str).toString());
                }
                else if (i != value.format().size() - 1) {
                    list.add(new StringBuilder().append(FormatUtil.fillBlank(key_colon)).append(str).toString());
                }
                else {
                    list.add(new StringBuilder().append(FormatUtil.fillBlank(key_colon))
                                                .append(str)
                                                .append(",")
                                                .toString());
                }
            }
            
            i++;
        }
        return list;
    }
}
