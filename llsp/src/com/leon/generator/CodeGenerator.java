
package com.leon.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import com.leon.cc.Syntax;
import com.leon.dynamic.CharSequenceJavaFileObject;
import com.leon.dynamic.ClassFileManager;
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

public abstract class CodeGenerator {
    
    private int precedence = 1;
    
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
    
    public List<ISymbol> generate_start_symbol(ISymbol type, ISymbol symbol) {
        List<ISymbol> list = new ArrayList<ISymbol>();
        list.add(type);
        list.add(symbol);
        return list;
    }
    
    public List<ISymbol> generate_classname(ISymbol type, ISymbol symbol) {
        List<ISymbol> list = new ArrayList<ISymbol>();
        list.add(type);
        list.add(symbol);
        return list;
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
    
    public List<ProductionSet> generate_productions(List<ProductionSet> production_set, ISymbol token,
                                                    List<ProductionRightHand> rules) {
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
    public List<Object> generate_declarations(Object declaration) {
        List<Object> declarations = new ArrayList<Object>();
        declarations.add(declaration);
        return declarations;
    }
    
    public List<Object> generate_declarations(List<Object> declarations, Object declaration) {
        declarations.add(declaration);
        return declarations;
    }
    
    //Descriptor
    @SuppressWarnings("unchecked")
    public Syntax generate_descriptor(ISymbol action, List<Object> declarations, List<ProductionSet> productions,
                                      ISymbol usercode) throws IOException {
        Syntax s = new Syntax();
        s.header = (String) action.get_value();
        Grammar g = new Grammar();
        for (Object declaration : declarations) {
            if (declaration instanceof Assoc) {
                g.assoc_list.add((Assoc) declaration);
            }
            else if (declaration instanceof Terminal) {
                g.terminals_list.add((Terminal) declaration);
            }
            else if (declaration instanceof List) {
                List<ISymbol> list = (List<ISymbol>) declaration;
                ISymbol type = list.get(0);
                ISymbol value = list.get(1);
                if (type.get_type_name().equals("START")) {
                    g.start_symbol = (String) value.get_value();
                }
                else if (type.get_type_name().equals("CLASSNAME")) {
                    s.classname = (String) value.get_value();
                }
            }
        }
        g.production_set = productions;
        g.init_productions();
        g.init();
        s.g = g;
        s.footer = (String) usercode.get_value();
        return s;
    }
    
    //program
    public Syntax generate_program(Syntax descriptor) {
        return descriptor;
    }
    
    public Object dynamic_compile(String className, String src) throws Exception {
        System.out.println(src);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        JavaFileManager fileManager = new ClassFileManager(compiler.getStandardFileManager(null, null, null));
        List<JavaFileObject> jfiles = new ArrayList<JavaFileObject>();
        jfiles.add(new CharSequenceJavaFileObject(className, src));
        compiler.getTask(null, fileManager, null, null, null, jfiles).call();
        Object instance = fileManager.getClassLoader(null).loadClass(className).newInstance();
        return instance.getClass().getMethod("generate").invoke(instance);
    }
}
