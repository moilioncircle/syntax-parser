
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
    
    Grammar                   g          = new Grammar();
    int                       precedence = 1;
    List<ISymbol>             tokens     = new ArrayList<ISymbol>();             //by assoc;
    List<ISymbol>             rule       = new ArrayList<ISymbol>();             //by production's right hand;
    List<ProductionRightHand> rules      = new ArrayList<ProductionRightHand>();
    
    //Declaration
    public Assoc generate_assoc(Associativity assoc_type) {
        Assoc assoc = new Assoc(precedence++, assoc_type);
        for (ISymbol symbol : tokens) {
            assoc.add_symbol(symbol.get_type_name());
        }
        g.assoc_list.add(assoc);
        //reset tokens;
        tokens = new ArrayList<ISymbol>();
        return assoc;
    }
    
    public Terminal generate_terminal(ISymbol token, int insert_cost, int delete_cost) {
        Terminal t = new Terminal(token.get_type_name(), insert_cost, delete_cost);
        g.terminals_list.add(t);
        return t;
    }
    
    public ISymbol generate_start_symbol(ISymbol symbol) {
        g.start_symbol = symbol.get_type_name();
        return symbol;
    }
    
    public ISymbol generate_action(ISymbol action) {
        return action;
    }
    
    //Precedence
    public Associativity generate_associativity(ISymbol symbol) {
        return Associativity.valueOf(symbol.get_type_name());
    }
    
    //Tokens
    public List<ISymbol> generate_tokens(ISymbol token) {
        tokens.add(token);
        return tokens;
    }
    
    //Token
    public ISymbol generate_token(ISymbol token) {
        return token;
    }
    
    //Productions
    public List<ProductionSet> generate_productions(ISymbol token) {
        ProductionSet set = new ProductionSet(token.get_type_name());
        for (ProductionRightHand p : rules) {
            set.rhs_set.add(p);
        }
        g.production_set.add(set);
        //reset rules;
        rules = new ArrayList<ProductionRightHand>();
        return g.production_set;
    }
    
    //Rules
    public List<ProductionRightHand> generate_rules(ProductionRightHand right) {
        rules.add(right);
        return rules;
    }
    
    //GrammarRule
    public ProductionRightHand generate_grammarRule(Prec prec, ISymbol action) {
        String[] rhs = new String[rule.size()];
        for (int i = 0; i < rule.size(); i++) {
            rhs[i] = rule.get(i).get_type_name();
        }
        ProductionRightHand right = new ProductionRightHand();
        right.rhs = rhs;
        right.prec = prec;
        right.semantic_action = (String) action.get_value();
        //reset rule;
        rule = new ArrayList<ISymbol>();
        return right;
    }
    
    //Rule
    public List<ISymbol> generate_rule(ISymbol token) {
        rule.add(token);
        return rule;
    }
    
    //Prec
    public Prec generate_prec(ISymbol token) {
        Prec prec = new Prec(token.get_type_name());
        return prec;
    }
}
