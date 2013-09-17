#
package com.leon.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.leon.grammar.Assoc;
import com.leon.grammar.Associativity;
import com.leon.grammar.Grammar;
import com.leon.grammar.Prec;
import com.leon.grammar.ProductionRightHand;
import com.leon.grammar.ProductionSet;
import com.leon.grammar.Terminal;
import com.leon.lr.LR1;
import com.leon.util.ISymbol;

/**
 * @author : Leon
 * @since : 2013-9-11
 * @see :
 */

public class CodeGenerator implements BaseCodeGenerator{

    public List<ISymbol> t;
    private int          precedence = 1;
    public CodeGenerator(List<ISymbol> t){
        this.t = t;
    }
#
%name : EOF 20 20;
%name : ACTION 14 14;
%name : MARK 6 6;
%name : SEMI 1 1;
%name : NAME 1 2;
%name : COLON 3 3;
%name : NUM 5 5;
%name : START 13 13;
%name : CLASSNAME 15 15;
%name : LEFT 7 7;
%name : RIGHT 8 8;
%name : NONASSOC 9 9;
%name : BINARY 10 10;
%name : COMMA 2 2;
%name : TOKEN 4 4;
%name : OR 11 11;
%name : PREC 12 12;
%start : program;
%class CodeGenerator;
%%
program         : Descriptor EOF #generate_program($0)#
                ;
Descriptor      : ACTION Declarations SectionMarker Productions Usercode #generate_descriptor($0,$1,$3,$4)#
                ;
Usercode        : SectionMarker ACTION #generate_footer($1)#
                | SectionMarker #generate_footer(null)#
                ;
SectionMarker   : MARK ##
                ;
Declarations    : Declarations Declaration #generate_declarations($0,$1)#
                | Declaration #generate_declarations($0)#
                ;
Declaration     : Precedence Tokens SEMI #generate_assoc($0,$1)#
                | NAME COLON Token NUM NUM SEMI #generate_terminal($2,$3,$4)#
                | START COLON Token SEMI #generate_start_symbol($0,$2)#
                | CLASSNAME Token SEMI #generate_classname($0,$1)#
                ;
Precedence      : LEFT #generate_associativity($0)#
                | RIGHT #generate_associativity($0)#
                | NONASSOC #generate_associativity($0)#
                | BINARY #generate_associativity($0)#
                ;
Tokens          : Tokens COMMA Token #generate_tokens($0,$2)#
                | Token #generate_tokens($0)#
                ;
Token           : TOKEN #generate_token($0)#
                ;
Productions     : Productions TOKEN COLON Rules SEMI #generate_productions($0,$1,$3)#
                | TOKEN COLON Rules SEMI #generate_productions($0,$2)#
                ;
Rules           : Rules OR GrammarRule #generate_rules($0,$2)#
                | GrammarRule #generate_rules($0)#
                ;
GrammarRule     : Rule Prec ACTION #generate_grammarRule($0,$1,$2)#
                | Rule ACTION #generate_grammarRule($0,null,$1)#
                | ACTION #generate_grammarRule(null,null,$0)#
                | #generate_grammarRule(null,null,null)#
                ;
Rule            : Rule TOKEN #generate_rule($0,$1)#
                | TOKEN #generate_rule($0)#
                ;
Prec            : PREC TOKEN #generate_prec($1)#
                ;
%%
#
    //Declaration
    public Assoc generate_assoc(Associativity assoc_type, List<ISymbol> tokens) {
        Assoc assoc = new Assoc(precedence++, assoc_type);
        for (ISymbol symbol : tokens) {
            assoc.add_symbol((String) symbol.get_value());
        }
        return assoc;
    }
    
    public Terminal generate_terminal(ISymbol token, ISymbol insert_cost, ISymbol delete_cost) {
        Terminal t = new Terminal((String) token.get_value(), (Integer) insert_cost.get_value(),
                (Integer) delete_cost.get_value());
        return t;
    }
    
    public ISymbol generate_start_symbol(ISymbol symbol) {
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
        List<ProductionSet> production_set = new ArrayList<ProductionSet>();
        ProductionSet set = new ProductionSet((String) token.get_value());
        for (ProductionRightHand p : rules) {
            set.rhs_set.add(p);
        }
        production_set.add(set);
        return production_set;
    }
    
    public List<ProductionSet> generate_productions(List<ProductionSet> production_set,ISymbol token, List<ProductionRightHand> rules) {
        ProductionSet set = new ProductionSet((String) token.get_value());
        for (ProductionRightHand p : rules) {
            set.rhs_set.add(p);
        }
        production_set.add(set);
        return production_set;
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
    
    //Declarations
    public List<Object> generate_declarations(Object declaration){
        List<Object> declarations = new ArrayList<Object>();
        declarations.add(declaration);
        return declarations;
    }
    
    public List<Object> generate_declarations(List<Object> declarations,Object declaration){
        declarations.add(declaration);
        return declarations;
    }
    
    //Descriptor
    public Grammar generate_descriptor(ISymbol action,List<Object> declarations,List<ProductionSet> productions,ISymbol usercode) throws IOException{
        System.out.println((String)action.get_value());
        Grammar g = new Grammar();
        for(Object declaration:declarations){
            if(declaration instanceof Assoc){
                g.assoc_list.add((Assoc)declaration);
            }else if(declaration instanceof Terminal){
                g.terminals_list.add((Terminal)declaration);
            }else if(declaration instanceof ISymbol){
                g.start_symbol = (String)((ISymbol)declaration).get_value();
            }
        }
        g.production_set = productions;
        g.init_productions();
        g.init();
        LR1 lr1 = new LR1(g);
        lr1.lr1_driver(t);
        System.out.println("    public void generate() throws IOException{");
        System.out.println("        Grammar g = "+lr1.semantic.top()+";");
        System.out.println("    }");
        System.out.println((String)usercode.get_value());
        return g;
    }
}
#