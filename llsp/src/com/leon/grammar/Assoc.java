
package com.leon.grammar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Leon
 * @since : 2013-9-1
 * @see :
 */

public class Assoc {
    
    public int           precedence  = 0;
    public Associativity association = Associativity.NONASSOC;
    public List<String>  symbol_list = new ArrayList<String>();
    
    public Assoc add_symbol(String lexer) {
        symbol_list.add(lexer);
        return this;
    }
    
    public Assoc(int precedence, Associativity association) {
        this.precedence = precedence;
        this.association = association;
    }
    
    public Assoc() {
        
    }
    
    @Override
    public String toString() {
        return "Assoc [precedence=" + precedence + ", association=" + association + ", symbol_list=" + symbol_list
               + "]";
    }
    
}
