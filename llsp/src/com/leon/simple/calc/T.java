
package com.leon.simple.calc;

/**
 * @author : Leon
 * @since : 2013-9-17
 * @see :
 */

public class T implements Value {
    
    private S s;
    
    public T(S s) {
        this.s = s;
    }
    
    @Override
    public int value() {
        return s.value();
    }
    
}
