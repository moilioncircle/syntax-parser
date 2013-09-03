
package com.leon.grammar;

import java.util.ArrayList;
import java.util.List;
import static com.leon.util.Utils.is_nonterminal;

;

/**
 * @author : Leon
 * @since : 2013-8-12
 * @see :
 */

public class Grammar {
    
    public String              start_symbol;
    public List<ProductionSet> production_set = new ArrayList<ProductionSet>();
    public List<Production>    productions    = new ArrayList<Production>();
    public List<Assoc>         assoc_list     = new ArrayList<Assoc>();
    
    public Grammar(String start_symbol, List<ProductionSet> production_set) {
        this(start_symbol);
        this.production_set = production_set;
        for (int i = 0; i < production_set.size(); i++) {
            productions.addAll(production_set.get(i).get_productions());
        }
        set_nonterminals();
        set_terminals();
        set_vocabulary();
        set_start_production();
        set_eof();
    }
    
    public Grammar(String start_symbol, List<ProductionSet> production_set, List<Assoc> assoc_list) {
        this(start_symbol, production_set);
        this.assoc_list = assoc_list;
    }
    
    public Grammar(List<Production> productions, String start_symbol) {
        this(start_symbol);
        this.productions = productions;
        for (int i = 0; i < productions.size(); i++) {
            Production production = productions.get(i);
            ProductionSet p_set = contain_lhs(production.lhs);
            if (p_set == null) {
                p_set = new ProductionSet(production.lhs);
                p_set.or(production.rhs);
                this.production_set.add(p_set);
            }
            else {
                p_set.or(production.rhs);
            }
        }
        set_nonterminals();
        set_terminals();
        set_vocabulary();
        set_start_production();
        set_eof();
    }
    
    public Grammar(List<Production> productions, String start_symbol, List<Assoc> assoc_list) {
        this(productions, start_symbol);
        this.assoc_list = assoc_list;
    }
    
    private Grammar(String start_symbol) {
        this.start_symbol = start_symbol;
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
    
    private void set_terminals() {
        List<String> terminals_list = new ArrayList<String>();
        for (int i = 0; i < this.productions.size(); i++) {
            Production p = this.productions.get(i);
            for (int j = 0; j < p.rhs.length; j++) {
                if (!is_nonterminal(p.rhs[j], this.nonterminals)) {
                    if (!terminals_list.contains(p.rhs[j])) {
                        terminals_list.add(p.rhs[j]);
                    }
                }
            }
        }
        String[] terminals = new String[terminals_list.size()];
        this.terminals = terminals_list.toArray(terminals);
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
    
    private void set_eof() {
        String[] rhs = this.start_production.rhs;
        this.eof = rhs[rhs.length - 1];
    }
    
    private ProductionSet contain_lhs(String lhs) {
        for (int i = 0; i < production_set.size(); i++) {
            if (production_set.get(i).lhs.equals(lhs)) {
                return production_set.get(i);
            }
        }
        return null;
    }
    
    public String[]   vocabulary;
    public Production start_production;
    public String     eof;
    public String[]   nonterminals;
    public String[]   terminals;
}
