package com.leon.ll;

import java.util.List;

import com.leon.grammar.Production;



/** @author : Leon
 * @since   : 2013-8-14
 * @see    : 
 */

public class CommonPerfixStruct {
    
    public int              index;
    public List<Production> list;
    
    public CommonPerfixStruct(int index, List<Production> list) {
        this.list = list;
        this.index = index;
        if (this.list.size() > 0) {
            lhs = this.list.get(0).lhs;
            perfix = new String[this.index];
            for (int i = 0; i < perfix.length; i++) {
                perfix[i] = this.list.get(0).rhs[i];
            }
        }
    }
    
    public String   lhs;
    public String[] perfix;
}

