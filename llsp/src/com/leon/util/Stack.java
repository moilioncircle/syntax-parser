
package com.leon.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Leon
 * @since : 2013-8-12
 * @see :
 */

public class Stack<T> {
    
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
    
    public static void main(String[] args) {
        Stack<String> s = new Stack<String>();
        s.push("a");
        s.push("b");
        s.push("c");
        s.push("d");
        s.push("e");
        System.out.println(s.toString());
        s.pop();
        s.pop();
        s.pop();
        s.pop();
        System.out.println(s.toString());
        s.pop();
        s.pop();
    }
}
