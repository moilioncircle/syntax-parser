
package com.leon.util;

/**
 * @author : Leon
 * @since : 2013-8-28
 * @see :
 */

public interface ISymbol<T> {
    
    public int get_line();
    
    public int get_column();
    
    public T get_type();
    
    public Object get_value();
    
    public int get_insert_cost();
    
    public int get_delete_cost();
    
    public ISymbol<T> new_object(String type_name);
}
