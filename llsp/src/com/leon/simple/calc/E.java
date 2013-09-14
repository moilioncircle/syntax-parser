
package com.leon.simple.calc;

import com.leon.util.ISymbol;

/**
 * @author : Leon
 * @since : 2013-9-12
 * @see :
 */

public class E implements Value {
    
    private ISymbol num;
    private ISymbol uminus;
    private E       e1;
    private ISymbol op;
    private E       e2;
    
    public E(E e1, ISymbol op, E e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }
    
    public E(E e1) {
        this.e1 = e1;
    }
    
    public E(ISymbol num) {
        this.num = num;
    }
    
    public E(ISymbol uminus, ISymbol num) {
        this.uminus = uminus;
        this.num = num;
    }
    
    @Override
    public int value() {
        if (e1 != null && e2 != null && op != null) {
            if (op.get_type_name().equals("PLUS")) {
                return e1.value() + e2.value();
            }
            else if (op.get_type_name().equals("MINUS")) {
                return e1.value() - e2.value();
            }
            else if (op.get_type_name().equals("TIMES")) {
                return e1.value() * e2.value();
            }
            else if (op.get_type_name().equals("DIVIDE")) {
                return e1.value() / e2.value();
            }
        }
        else if (e1 != null && e2 == null) {
            return e1.value();
        }
        else if (uminus != null && num != null) {
            return -((Integer) num.get_value()).intValue();
        }
        else if (uminus == null && num != null) {
            return ((Integer) num.get_value()).intValue();
        }
        throw new UnsupportedOperationException("sss");
    }
}
