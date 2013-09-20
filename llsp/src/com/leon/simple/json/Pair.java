
package com.leon.simple.json;

import com.leon.util.ISymbol;

/**
 * @author : Leon
 * @since : 2013-9-20
 * @see :
 */

public class Pair {
    
    private ISymbol string;
    private Value   value;
    
    public Pair(ISymbol string, Value value) {
        this.string = string;
        this.value = value;
    }
}
