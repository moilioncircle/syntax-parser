
package com.leon.simple.json;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Leon
 * @since : 2013-9-20
 * @see :
 */

public class JsonObject implements JsonFormat {
    
    private Member member;
    
    public JsonObject() {
        
    }
    
    public JsonObject(Member member) {
        this.member = member;
    }
    
    @Override
    public List<String> format() {
        List<String> list = new ArrayList<String>();
        if (member != null) {
            list.add("{");
            List<String> member_list = member.format();
            for (int i = 0; i < member_list.size(); i++) {
                String str = member_list.get(i);
                if (i != member_list.size() - 1) {
                    list.add(new StringBuilder().append("    ").append(str).toString());
                }
                else {
                    StringBuilder sb = new StringBuilder().append("    ").append(str);
                    sb.deleteCharAt(sb.length() - 1);
                    list.add(sb.toString());
                }
            }
            list.add("}");
        }
        else {
            list.add("{}");
        }
        return list;
    }
}
