
package com.leon.simple.calc;

import com.leon.util.ISymbol;

/**
 * @author : Leon
 * @since : 2013-9-12
 * @see :
 */

public class P implements Value {
    
    public ISymbol symbol;
    public E       e;
    
    public P(ISymbol symbol) {
        this.symbol = symbol;
    }
    
    public P(E e) {
        this.e = e;
    }
    
    @Override
    public int value() {
        if (symbol == null) {
            return e.value();
        }
        else {
            return ((Integer) symbol.get_value()).intValue();
        }
    }
}
