
package com.leon.simple.calc;

/**
 * @author : Leon
 * @since : 2013-9-12
 * @see :
 */

public class E implements Value {
    
    public E e;
    public T t;
    
    public E(E e, T t) {
        this.e = e;
        this.t = t;
    }
    
    public E(T t) {
        this.t = t;
    }
    
    @Override
    public int value() {
        if (e == null) {
            return t.value();
        }
        return e.value() + t.value();
    }
}
