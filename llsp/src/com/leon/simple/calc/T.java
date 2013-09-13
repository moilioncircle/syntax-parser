
package com.leon.simple.calc;

/**
 * @author : Leon
 * @since : 2013-9-12
 * @see :
 */

public class T implements Value {
    
    public T t;
    public P p;
    
    public T(T t, P p) {
        this.t = t;
        this.p = p;
    }
    
    public T(P p) {
        this.p = p;
    }
    
    @Override
    public int value() {
        if (t == null) {
            return p.value();
        }
        else {
            return t.value() * p.value();
        }
    }
}
