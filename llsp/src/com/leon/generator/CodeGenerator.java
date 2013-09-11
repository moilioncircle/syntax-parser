
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
    
    public List<ISymbol> t;
    public Grammar       g          = new Grammar();
    private int          precedence = 1;
    
    //Declaration
    public Assoc generate_assoc(Associativity assoc_type, List<ISymbol> tokens) {
        Assoc assoc = new Assoc(precedence++, assoc_type);
        for (ISymbol symbol : tokens) {
            assoc.add_symbol((String) symbol.get_value());
        }
        g.assoc_list.add(assoc);
        return assoc;
    }
    
    public Terminal generate_terminal(ISymbol token, ISymbol insert_cost, ISymbol delete_cost) {
        Terminal t = new Terminal((String) token.get_value(), (Integer) insert_cost.get_value(),
                (Integer) delete_cost.get_value());
        g.terminals_list.add(t);
        return t;
    }
    
    public ISymbol generate_start_symbol(ISymbol symbol) {
        g.start_symbol = (String) symbol.get_value();
        return (ISymbol) symbol;
    }
    
    public ISymbol generate_header(ISymbol action) {
        return action;
    }
    
    //Precedence
    public Associativity generate_associativity(ISymbol symbol) {
        return Associativity.valueOf(symbol.get_type_name());
    }
    
    //Tokens
    public List<ISymbol> generate_tokens(ISymbol token) {
        List<ISymbol> tokens = new ArrayList<ISymbol>();
        tokens.add(token);
        return tokens;
    }
    
    public List<ISymbol> generate_tokens(List<ISymbol> tokens, ISymbol token) {
        tokens.add(token);
        return tokens;
    }
    
    //Token
    public ISymbol generate_token(ISymbol token) {
        return token;
    }
    
    //Productions
    public List<ProductionSet> generate_productions(ISymbol token, List<ProductionRightHand> rules) {
        ProductionSet set = new ProductionSet((String) token.get_value());
        for (ProductionRightHand p : rules) {
            set.rhs_set.add(p);
        }
        g.production_set.add(set);
        return g.production_set;
    }
    
    //Rules
    public List<ProductionRightHand> generate_rules(List<ProductionRightHand> rules, ProductionRightHand right) {
        rules.add(right);
        return rules;
    }
    
    public List<ProductionRightHand> generate_rules(ProductionRightHand right) {
        List<ProductionRightHand> rules = new ArrayList<ProductionRightHand>();
        rules.add(right);
        return rules;
    }
    
    //GrammarRule
    public ProductionRightHand generate_grammarRule(List<ISymbol> rule, Prec prec, ISymbol action) {
        if (rule == null) {
            rule = new ArrayList<ISymbol>();
        }
        String[] rhs = new String[rule.size()];
        for (int i = 0; i < rule.size(); i++) {
            rhs[i] = (String) rule.get(i).get_value();
        }
        ProductionRightHand right = new ProductionRightHand();
        right.rhs = rhs;
        right.prec = prec == null ? null : prec;
        right.semantic_action = action == null ? "" : (String) action.get_value();
        System.out.println(right);
        return right;
    }
    
    //Rule
    public List<ISymbol> generate_rule(List<ISymbol> rule, ISymbol token) {
        rule.add(token);
        return rule;
    }
    
    public List<ISymbol> generate_rule(ISymbol token) {
        List<ISymbol> rule = new ArrayList<ISymbol>();
        rule.add(token);
        return rule;
    }
    
    //Prec
    public Prec generate_prec(ISymbol token) {
        Prec prec = new Prec((String) token.get_value());
        return prec;
    }
    
    //Usercode
    public ISymbol generate_footer(ISymbol action) {
        return action;
    }
    
    public void generate() {
        generate_header(t.get(0));

        generate_token(t.get(3));
        generate_terminal(generate_token(t.get(3)),t.get(4),t.get(5));



        generate_token(t.get(9));
        generate_start_symbol(generate_token(t.get(9)));



        generate_associativity(t.get(11));
        generate_token(t.get(12));
        generate_tokens(generate_token(t.get(12)));
        generate_token(t.get(14));
        generate_tokens(generate_tokens(generate_token(t.get(12))),generate_token(t.get(14)));
        generate_assoc(generate_associativity(t.get(11)),generate_tokens(generate_tokens(generate_token(t.get(12))),generate_token(t.get(14))));



        generate_associativity(t.get(16));
        generate_token(t.get(17));
        generate_tokens(generate_token(t.get(17)));
        generate_token(t.get(19));
        generate_tokens(generate_tokens(generate_token(t.get(17))),generate_token(t.get(19)));
        generate_assoc(generate_associativity(t.get(16)),generate_tokens(generate_tokens(generate_token(t.get(17))),generate_token(t.get(19))));



        generate_associativity(t.get(21));
        generate_token(t.get(22));
        generate_tokens(generate_token(t.get(22)));
        generate_assoc(generate_associativity(t.get(21)),generate_tokens(generate_token(t.get(22))));




        generate_rule(t.get(27));
        generate_rule(generate_rule(t.get(27)),t.get(28));
        generate_grammarRule(generate_rule(generate_rule(t.get(27)),t.get(28)),null,t.get(29));
        generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(27)),t.get(28)),null,t.get(29)));
        generate_grammarRule(null,null,t.get(31));
        generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(27)),t.get(28)),null,t.get(29))),generate_grammarRule(null,null,t.get(31)));
        generate_productions(t.get(25),generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(27)),t.get(28)),null,t.get(29))),generate_grammarRule(null,null,t.get(31))));
        generate_rule(t.get(35));
        generate_rule(generate_rule(t.get(35)),t.get(36));
        generate_prec(t.get(38));
        generate_grammarRule(generate_rule(generate_rule(t.get(35)),t.get(36)),generate_prec(t.get(38)),t.get(39));
        generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(35)),t.get(36)),generate_prec(t.get(38)),t.get(39)));
        generate_rule(t.get(41));
        generate_rule(generate_rule(t.get(41)),t.get(42));
        generate_grammarRule(generate_rule(generate_rule(t.get(41)),t.get(42)),null,t.get(43));
        generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(35)),t.get(36)),generate_prec(t.get(38)),t.get(39))),generate_grammarRule(generate_rule(generate_rule(t.get(41)),t.get(42)),null,t.get(43)));
        generate_grammarRule(null,null,t.get(45));
        generate_rules(generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(35)),t.get(36)),generate_prec(t.get(38)),t.get(39))),generate_grammarRule(generate_rule(generate_rule(t.get(41)),t.get(42)),null,t.get(43))),generate_grammarRule(null,null,t.get(45)));
        generate_grammarRule(null,null,null);
        generate_rules(generate_rules(generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(35)),t.get(36)),generate_prec(t.get(38)),t.get(39))),generate_grammarRule(generate_rule(generate_rule(t.get(41)),t.get(42)),null,t.get(43))),generate_grammarRule(null,null,t.get(45))),generate_grammarRule(null,null,null));
        generate_productions(t.get(33),generate_rules(generate_rules(generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(35)),t.get(36)),generate_prec(t.get(38)),t.get(39))),generate_grammarRule(generate_rule(generate_rule(t.get(41)),t.get(42)),null,t.get(43))),generate_grammarRule(null,null,t.get(45))),generate_grammarRule(null,null,null)));

        generate_footer(t.get(49));
    }
}
