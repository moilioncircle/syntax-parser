
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
    public List<Terminal>      terminals_list = new ArrayList<Terminal>();
    
    public Grammar(String start_symbol, List<ProductionSet> production_set, List<Terminal> terminals_list) {
        this(start_symbol, terminals_list);
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
    
    public Grammar(String start_symbol, List<ProductionSet> production_set, List<Terminal> terminals_list,
            List<Assoc> assoc_list) {
        this(start_symbol, production_set, terminals_list);
        this.assoc_list = assoc_list;
    }
    
    public Grammar(List<Production> productions, String start_symbol, List<Terminal> terminals_list) {
        this(start_symbol, terminals_list);
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
    
    public Grammar(List<Production> productions, String start_symbol, List<Terminal> terminals_list,
            List<Assoc> assoc_list) {
        this(productions, start_symbol, terminals_list);
        this.assoc_list = assoc_list;
    }
    
    private Grammar(String start_symbol, List<Terminal> terminals_list) {
        this.start_symbol = start_symbol;
        this.terminals_list = terminals_list;
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
        if (terminals_list != null) {
            this.terminals = new String[terminals_list.size()];
            for (int i = 0; i < this.terminals_list.size(); i++) {
                this.terminals[i] = this.terminals_list.get(i).name;
            }
        }
        else {
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
            this.terminals_list = new ArrayList<Terminal>();
            for (int i = 0; i < terminals.length; i++) {
                this.terminals_list.add(new Terminal(terminals[i]));
            }
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
    
    public Terminal get_terminal_by_name(String name) {
        for (Terminal t : terminals_list) {
            if (t.name.equals(name)) {
                return t;
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
