
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
    public List<String[]> rhs_set = new ArrayList<String[]>();
    
    public ProductionSet(String lhs){
        this.lhs = lhs;
    }
    
    public List<Production> get_productions(){
        List<Production> result = new ArrayList<Production>();
        for (int i = 0; i < rhs_set.size(); i++) {
            result.add(new Production(lhs, rhs_set.get(i)));
        }
        return result;
    }
    
    public void add_rhs(String[] rhs){
        rhs_set.add(rhs);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(rhs_set.size() == 0){
            return "";
        }
        sb.append(lhs+"->");
        for (int i = 0; i < rhs_set.size()-1; i++) {
            if(rhs_set.get(i).length == 0){
                sb.append("lambda ");
            }
            else{
                for (int j = 0; j < rhs_set.get(i).length; j++) {
                    sb.append(rhs_set.get(i)[j]+" ");
                }
            }
            sb.append("| ");
        }
        if(rhs_set.get(rhs_set.size()-1).length == 0){
            sb.append("lambda ");
        }
        else{
            for (int j = 0; j < rhs_set.get(rhs_set.size()-1).length; j++) {
                sb.append(rhs_set.get(rhs_set.size()-1)[j]+" ");
            }
        }
        return sb.toString();
    }
    
    

}
