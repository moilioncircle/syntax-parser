
package com.leon.grammar;

import java.util.ArrayList;
import java.util.List;
import static com.leon.util.Utils.is_nonterminal;

/**
 * @author : Leon
 * @since : 2013-8-12
 * @see :
 */

public class Grammar {
    
    public String   start_symbol;
    public String[] nonterminals;
    public String[] terminals;
    public String   eog;         //end of grammar;
                                  
    public Grammar(String start_symbol, String[] terminals, List<ProductionSet> production_set) {
        this(start_symbol, terminals);
        this.production_set = production_set;
        for (int i = 0; i < production_set.size(); i++) {
            productions.addAll(production_set.get(i).get_productions());
        }
        set_nonterminals();
        set_vocabulary();
        set_start_production();
        set_eog();
    }
    
    public Grammar(String start_symbol, List<Production> productions, String[] terminals) {
        this(start_symbol, terminals);
        this.productions = productions;
        for (int i = 0; i < productions.size(); i++) {
            Production production = productions.get(i);
            ProductionSet p_set = contain_lhs(production.lhs);
            if (p_set == null) {
                p_set = new ProductionSet(production.lhs);
                p_set.add_rhs(production.rhs);
                this.production_set.add(p_set);
            }
            else {
                p_set.add_rhs(production.rhs);
            }
        }
        set_nonterminals();
        set_vocabulary();
        set_start_production();
        set_eog();
    }
    
    private void set_vocabulary() {
        vocabulary = new String[nonterminals.length + this.terminals.length];
        int j = 0;
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
    
    private void set_start_production() {
        for (int i = 0; i < productions.size(); i++) {
            Production p = productions.get(i);
            if (p.lhs.equals(this.start_symbol)) {
                this.start_production = p;
                break;
            }
        }
    }
    
    private void set_eog() {
        String[] rhs = this.start_production.rhs;
        this.eog = rhs[rhs.length - 1];
        if (is_nonterminal(this.eog, this.nonterminals)) {
            throw new UnsupportedOperationException("eog is nonterminal:" + this.eog);
        }
    }
    
    private Grammar(String start_symbol, String[] terminals) {
        this.start_symbol = start_symbol;
        this.terminals = terminals;
    }
    
    private ProductionSet contain_lhs(String lhs) {
        for (int i = 0; i < production_set.size(); i++) {
            if (production_set.get(i).lhs.equals(lhs)) {
                return production_set.get(i);
            }
        }
        return null;
    }
    
    public List<ProductionSet> production_set = new ArrayList<ProductionSet>();
    public List<Production>    productions    = new ArrayList<Production>();
    public String[]            vocabulary;
    public Production          start_production;
    
}
