
package com.leon.generator;

import java.util.ArrayList;
import java.util.List;

import com.leon.grammar.Assoc;
import com.leon.grammar.Associativity;
import com.leon.grammar.Grammar;
import com.leon.grammar.Prec;
import com.leon.grammar.ProductionRightHand;
import com.leon.grammar.ProductionSet;
import com.leon.grammar.Terminal;
import com.leon.util.ISymbol;

/**
 * @author : Leon
 * @since : 2013-9-11
 * @see :
 */

public class CodeGenerator {
    List<ISymbol> t;
    Grammar                   g          = new Grammar();
    int                       precedence = 1;
    List<ISymbol>             tokens     = new ArrayList<ISymbol>();            //by assoc;
    List<ISymbol>             rule       = new ArrayList<ISymbol>();            //by production's right hand;
    List<ProductionRightHand> rules      = new ArrayList<ProductionRightHand>();
    
    //Declaration
    public Assoc generate_assoc(Object assoc_type) {
        Assoc assoc = new Assoc(precedence++, (Associativity)assoc_type);
        for (ISymbol symbol : tokens) {
            assoc.add_symbol(symbol.get_type_name());
        }
        g.assoc_list.add(assoc);
        //reset tokens;
        tokens = new ArrayList<ISymbol>();
        return assoc;
    }
    
    public Terminal generate_terminal(Object token, Object insert_cost, Object delete_cost) {
        Terminal t = new Terminal(((ISymbol)token).get_type_name(), (Integer)((ISymbol)insert_cost).get_value(), (Integer)((ISymbol)delete_cost).get_value());
        g.terminals_list.add(t);
        return t;
    }
    
    public ISymbol generate_start_symbol(Object symbol) {
        g.start_symbol = ((ISymbol)symbol).get_type_name();
        return (ISymbol)symbol;
    }
    
    public ISymbol generate_action(Object action) {
        return (ISymbol)action;
    }
    
    //Precedence
    public Associativity generate_associativity(Object symbol) {
        return Associativity.valueOf(((ISymbol)symbol).get_type_name());
    }
    
    //Tokens
    public List<ISymbol> generate_tokens(Object token) {
        tokens.add((ISymbol)token);
        return tokens;
    }
    
    //Token
    public ISymbol generate_token(Object token) {
        return (ISymbol)token;
    }
    
    //Productions
    public List<ProductionSet> generate_productions(Object token) {
        ProductionSet set = new ProductionSet(((ISymbol)token).get_type_name());
        for (ProductionRightHand p : rules) {
            set.rhs_set.add(p);
        }
        g.production_set.add(set);
        //reset rules;
        rules = new ArrayList<ProductionRightHand>();
        return g.production_set;
    }
    
    //Rules
    public List<ProductionRightHand> generate_rules(Object right) {
        rules.add((ProductionRightHand)right);
        return rules;
    }
    
    //GrammarRule
    public ProductionRightHand generate_grammarRule(Object prec, Object action) {
        String[] rhs = new String[rule.size()];
        for (int i = 0; i < rule.size(); i++) {
            rhs[i] = rule.get(i).get_type_name();
        }
        ProductionRightHand right = new ProductionRightHand();
        right.rhs = rhs;
        right.prec = (Prec)prec;
        right.semantic_action = (String)((ISymbol)action).get_value();
        //reset rule;
        rule = new ArrayList<ISymbol>();
        return right;
    }
    
    //Rule
    public List<ISymbol> generate_rule(Object token) {
        rule.add((ISymbol)token);
        return rule;
    }
    
    //Prec
    public Prec generate_prec(Object token) {
        Prec prec = new Prec(((ISymbol)token).get_type_name());
        return prec;
    }
}
