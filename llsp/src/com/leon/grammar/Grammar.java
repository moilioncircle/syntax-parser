package com.leon.grammar;

import java.util.ArrayList;
import java.util.List;


/** @author : Leon
 * @since   : 2013-8-12
 * @see    : 
 */

public class Grammar {
    public String start_symbol;
    public String[] nonterminals;
    public String[] terminals;
    
    
    public Grammar(String start_symbol,String[] nonterminals,String[] terminals,List<ProductionSet> production_set){
        this(start_symbol,nonterminals,terminals);
        this.production_set = production_set;
        for (int i = 0; i < production_set.size(); i++) {
            productions.addAll(production_set.get(i).get_productions());
        }
    }
    
    public Grammar(String start_symbol,List<Production> productions,String[] nonterminals,String[] terminals){
        this(start_symbol,nonterminals,terminals);
        this.productions = productions;
        for (int i = 0; i < productions.size(); i++) {
            Production production = productions.get(i);
            ProductionSet p_set = contain_lhs(production.lhs);
            if(p_set == null){
                p_set = new ProductionSet(production.lhs);
                p_set.add_rhs(production.rhs);
                this.production_set.add(p_set);
            }else{
                p_set.add_rhs(production.rhs);
            }
        }
    }
    
    private Grammar(String start_symbol,String[] nonterminals,String[] terminals){
        this.start_symbol = start_symbol;
        this.nonterminals = nonterminals;
        this.terminals = terminals;
        vocabulary = new String[nonterminals.length+terminals.length];
        int j=0;
        for (int i = 0; i < nonterminals.length; i++) {
            vocabulary[j++] = nonterminals[i];
        }
        for (int i = 0; i < terminals.length; i++) {
            vocabulary[j++] = terminals[i];
        }
    }
    
    private ProductionSet contain_lhs(String lhs){
        for (int i = 0; i < production_set.size(); i++) {
            if(production_set.get(i).lhs.equals(lhs)){
                return production_set.get(i);
            }
        }
        return null;
    }

    public List<ProductionSet> production_set = new ArrayList<ProductionSet>();
    public List<Production> productions = new ArrayList<Production>();
    public String[] vocabulary;
    
}


