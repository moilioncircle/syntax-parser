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
    
    
    public Grammar(String start_symbol,String[] terminals,List<ProductionSet> production_set){
        this(start_symbol,terminals);
        this.production_set = production_set;
        for (int i = 0; i < production_set.size(); i++) {
            productions.addAll(production_set.get(i).get_productions());
        }
        set_nonterminals();
        set_vocabulary();
    }
    
    public Grammar(String start_symbol,List<Production> productions,String[] terminals){
        this(start_symbol,terminals);
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
        set_nonterminals();
        set_vocabulary();
    }

    private void set_vocabulary() {
        vocabulary = new String[nonterminals.length+this.terminals.length];
        int j=0;
        for (int i = 0; i < nonterminals.length; i++) {
            vocabulary[j++] = nonterminals[i];
        }
        for (int i = 0; i < this.terminals.length; i++) {
            vocabulary[j++] = this.terminals[i];
        }
    }

    private void set_nonterminals() {
        this.nonterminals = new String[this.production_set.size()];
        for (int i = 0; i < this.production_set.size(); i++) {
            this.nonterminals[i] = this.production_set.get(i).lhs;
        }
    }
    
    private Grammar(String start_symbol,String[] terminals){
        this.start_symbol = start_symbol;
        this.terminals = terminals;
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


