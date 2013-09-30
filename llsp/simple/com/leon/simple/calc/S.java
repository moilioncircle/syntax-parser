
package com.leon.simple.calc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Leon
 * @since : 2013-9-12
 * @see :
 */

public class S implements Value {
    
    public List<E> list;
    
    public S(E e) {
        this.list = new ArrayList<E>();
        this.list.add(e);
    }
    
    public S(S s, E e) {
        this.list = new ArrayList<E>(s.list);
        this.list.add(e);
    }
    
    @Override
    public int value() {
        int total = 0;
        for (E e : this.list) {
            System.out.println(e.value());
            total += e.value();
        }
        return total;
    }
}
