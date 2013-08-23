
package com.leon.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Leon
 * @since : 2013-8-21
 * @see :
 */

public class Queue<T> {
    
    private List<T> list = new ArrayList<T>();
    
    public T peek() {
        if (!is_empty()) {
            return list.get(0);
        }
        return null;
    }
    
    public T poll() {
        if (!is_empty()) {
            T t = list.get(0);
            list.remove(0);
            return t;
        }
        return null;
    }
    
    public void put(T t) {
        list.add(t);
    }
    
    public boolean is_empty() {
        return list.isEmpty();
    }
}
