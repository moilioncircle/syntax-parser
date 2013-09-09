
package com.leon.util;

/**
 * @author : Leon
 * @since : 2013-8-28
 * @see :
 */

public interface ISymbol {
    
    public int get_line();
    
    public int get_column();
    
    public Object get_value();
    
    public String get_type_name();
}
