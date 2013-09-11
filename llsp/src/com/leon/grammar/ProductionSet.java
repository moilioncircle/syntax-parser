
package com.leon.grammar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Leon
 * @since : 2013-8-15
 * @see :
 */

public class ProductionSet {
    
    public String         lhs;
    public List<ProductionRightHand> rhs_set = new ArrayList<ProductionRightHand>();
    
    public ProductionSet(String lhs) {
        this.lhs = lhs;
    }
    
    public List<Production> get_productions() {
        List<Production> result = new ArrayList<Production>();
        for (int i = 0; i < rhs_set.size(); i++) {
            ProductionRightHand right = rhs_set.get(i);
            result.add(new Production(lhs, right.rhs,right.prec,right.semantic_action));
        }
        return result;
    }
    
    public ProductionSet or(Prec prec,String semantic_action,String... rhs) {
        ProductionRightHand right = new ProductionRightHand();
        right.prec = prec;
        right.semantic_action = semantic_action;
        right.rhs = rhs;
        rhs_set.add(right);
        return this;
    }
    
    public ProductionSet or(String... rhs) {
        or(null, "", rhs);
        return this;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (rhs_set.size() == 0) {
            return "";
        }
        sb.append(lhs + "->");
        for (int i = 0; i < rhs_set.size() - 1; i++) {
            if (rhs_set.get(i).rhs.length == 0) {
                sb.append("lambda ");
            }
            else {
                for (int j = 0; j < rhs_set.get(i).rhs.length; j++) {
                    sb.append(rhs_set.get(i).rhs[j] + " ");
                }
            }
            sb.append("| ");
        }
        if (rhs_set.get(rhs_set.size() - 1).rhs.length == 0) {
            sb.append("lambda ");
        }
        else {
            for (int j = 0; j < rhs_set.get(rhs_set.size() - 1).rhs.length; j++) {
                sb.append(rhs_set.get(rhs_set.size() - 1).rhs[j] + " ");
            }
        }
        return sb.toString();
    }
    
}
