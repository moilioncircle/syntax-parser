
package com.leon.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Leon
 * @since : 2013-8-12
 * @see :
 */

public class Stack<T> implements Cloneable{
    
    private List<T> list = new ArrayList<T>();
    
    public void push(T t) {
        list.add(0, t);
    }
    
    public T pop() {
        if (!is_empty()) {
            T t = list.get(0);
            list.remove(0);
            return t;
        }
        return null;
    }
    
    public T top() {
        if (!is_empty()) {
            return list.get(0);
        }
        return null;
    }
    
    public boolean is_empty() {
        return list.isEmpty();
    }
    
    public String toString() {
        return list.toString();
    }
    
    public Stack<T> copy(){
        Stack<T> stack = new Stack<T>();
        stack.list = new ArrayList<T>(this.list);
        return stack;
    }

}
