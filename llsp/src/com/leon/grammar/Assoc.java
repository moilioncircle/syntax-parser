
package com.leon.grammar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Leon
 * @since : 2013-9-1
 * @see :
 */

public class Assoc {
    
    public int          priority;
    public AssocType    type;
    public List<String> lexer_list = new ArrayList<String>();
    
    public void add_lexer(String lexer) {
        lexer_list.add(lexer);
    }
    
    public Assoc(int priority, AssocType type, List<String> lexer_list) {
        this.priority = priority;
        this.type = type;
        this.lexer_list = lexer_list;
    }
}
