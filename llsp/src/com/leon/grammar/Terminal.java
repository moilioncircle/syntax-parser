
package com.leon.grammar;

/**
 * @author : Leon
 * @since : 2013-9-9
 * @see :
 */

public class Terminal {
    
    public int    insert_cost;
    public int    delete_cost;
    public String name;
    
    public Terminal(String name, int insert_cost, int delete_cost) {
        this.name = name;
        this.insert_cost = insert_cost;
        this.delete_cost = delete_cost;
    }
    
    public Terminal(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "Terminal [insert_cost=" + insert_cost + ", delete_cost=" + delete_cost + ", name=" + name + "]";
    }
}
