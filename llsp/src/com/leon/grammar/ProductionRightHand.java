
package com.leon.grammar;

import java.util.Arrays;

/**
 * @author : Leon
 * @since : 2013-9-11
 * @see :
 */

public class ProductionRightHand {
    
    public String[] rhs;
    public Prec     prec;
    public String   semantic_action = "";
    
    @Override
    public String toString() {
        return "ProductionRightHand [rhs=" + Arrays.toString(rhs) + ", prec=" + prec + ", semantic_action="
               + semantic_action + "]";
    }
    
}
