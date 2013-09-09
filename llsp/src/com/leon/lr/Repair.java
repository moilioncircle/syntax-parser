
package com.leon.lr;

import java.util.List;

import com.leon.util.ISymbol;

/**
 * @author : Leon
 * @since : 2013-9-6
 * @see :
 */

public class Repair {
    
    public int           delete_size;
    public List<ISymbol> insert;
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("delete_size:" + delete_size + "\n");
        if (insert != null) {
            for (ISymbol s : insert) {
                sb.append("insert:" + s.get_type_name() + "\n");
            }
        }
        return sb.toString();
        
    }
}
