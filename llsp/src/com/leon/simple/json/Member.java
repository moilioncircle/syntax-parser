
package com.leon.simple.json;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Leon
 * @since : 2013-9-20
 * @see :
 */

public class Member implements JsonFormat {
    
    public List<Pair> member_list = new ArrayList<Pair>();
    
    public Member(Pair pair) {
        member_list.add(pair);
    }
    
    public Member(Pair pair, Member member) {
        member_list.add(pair);
        member_list.addAll(member.member_list);
    }
    
    @Override
    public List<String> format() {
        List<String> list = new ArrayList<String>();
        for (Pair pair : member_list) {
            list.addAll(pair.format());
        }
        return list;
    }
}
