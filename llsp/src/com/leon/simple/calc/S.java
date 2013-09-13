
package com.leon.simple.calc;

/**
 * @author : Leon
 * @since : 2013-9-12
 * @see :
 */

public class S implements Value {
    
    public E e;
    
    public S(E e) {
        this.e = e;
    }
    
    @Override
    public int value() {
        return e.value();
    }
}
