
package com.leon.cc;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.leon.grammar.Grammar;
import com.leon.grammar.Production;
import com.leon.lr.LR1;
import com.leon.util.ISymbol;
import com.leon.util.IToken;

/**
 * @author : Leon
 * @since : 2013-8-30
 * @see :
 */

public class CCGrammar {
    
    public Grammar getGrammar() {
        List<Production> list = new ArrayList<Production>();
        list.add(new Production("program", new String[] { "Descriptor", "EOF" }));
        list.add(new Production("Descriptor", new String[] { "ACTION", "Declarations", "SectionMarker", "Productions",
                "Usercode" }, "generate_descriptor($0,$1,$3,$4)"));
        list.add(new Production("Usercode", new String[] { "SectionMarker", "ACTION" }, "generate_footer($1)"));
        list.add(new Production("Usercode", new String[] { "SectionMarker" }, "generate_footer(null)"));
        list.add(new Production("SectionMarker", new String[] { "MARK" }));
        list.add(new Production("Declarations", new String[] { "Declarations", "Declaration" },
                "generate_declarations($0,$1)"));
        list.add(new Production("Declarations", new String[] { "Declaration" }, "generate_declarations($0)"));
        list.add(new Production("Declaration", new String[] { "Precedence", "Tokens", "SEMI" }, "generate_assoc($0,$1)"));
        list.add(new Production("Declaration", new String[] { "NAME", "COLON", "Token", "NUM", "NUM", "SEMI" },
                "generate_terminal($2,$3,$4)"));
        list.add(new Production("Declaration", new String[] { "START", "COLON", "Token", "SEMI" },
                "generate_start_symbol($2)"));
        list.add(new Production("Precedence", new String[] { "LEFT" }, "generate_associativity($0)"));
        list.add(new Production("Precedence", new String[] { "RIGHT" }, "generate_associativity($0)"));
        list.add(new Production("Precedence", new String[] { "NONASSOC" }, "generate_associativity($0)"));
        list.add(new Production("Precedence", new String[] { "BINARY" }, "generate_associativity($0)"));
        list.add(new Production("Tokens", new String[] { "Tokens", "COMMA", "Token" }, "generate_tokens($0,$2)"));
        list.add(new Production("Tokens", new String[] { "Token" }, "generate_tokens($0)"));
        list.add(new Production("Token", new String[] { "TOKEN" }, "generate_token($0)"));
        list.add(new Production("Productions", new String[] { "Productions", "TOKEN", "COLON", "Rules", "SEMI" },
                "generate_productions($0,$1,$3)"));
        list.add(new Production("Productions", new String[] { "TOKEN", "COLON", "Rules", "SEMI" },
                "generate_productions($0,$2)"));
        list.add(new Production("Rules", new String[] { "Rules", "OR", "GrammarRule" }, "generate_rules($0,$2)"));
        list.add(new Production("Rules", new String[] { "GrammarRule" }, "generate_rules($0)"));
        list.add(new Production("GrammarRule", new String[] { "Rule", "Prec", "ACTION" },
                "generate_grammarRule($0,$1,$2)"));
        list.add(new Production("GrammarRule", new String[] { "Rule", "ACTION" }, "generate_grammarRule($0,null,$1)"));
        list.add(new Production("GrammarRule", new String[] { "ACTION" }, "generate_grammarRule(null,null,$0)"));
        list.add(new Production("GrammarRule", new String[] {}, "generate_grammarRule(null,null,null)"));
        list.add(new Production("Rule", new String[] { "Rule", "TOKEN" }, "generate_rule($0,$1)"));
        list.add(new Production("Rule", new String[] { "TOKEN" }, "generate_rule($0)"));
        list.add(new Production("Prec", new String[] { "PREC", "TOKEN" }, "generate_prec($1)"));
        Grammar g = new Grammar(list, "program", null);
        return g;
    }
    
    public static void main(String[] args) throws IOException {
        Grammar g = new CCGrammar().getGrammar();
        InputStreamReader reader = new InputStreamReader(CCGrammar.class.getResourceAsStream("test.g"), "UTF8");
        IToken t = new CCToken(reader);
        List<ISymbol> list = new ArrayList<ISymbol>();
        while (t.has_next()) {
            list.add(t.next_token());
        }
        LR1 lr1 = new LR1(g);
        lr1.lr1_driver(list);
        System.out.println("package com.leon.generator;");
        System.out.println("import java.util.List;");
        System.out.println("import java.io.IOException;");
        System.out.println("import com.leon.util.ISymbol;");
        System.out.println("import com.leon.cc.Syntax;");
        System.out.println("public class CCCodeGenerator extends CodeGenerator{");
        System.out.println("\tpublic CCCodeGenerator(List<ISymbol> t) {");
        System.out.println("\t\tsuper(t);");
        System.out.println("\t}");
        System.out.println("\tpublic void generate() throws IOException {");
        System.out.println("\t\tSyntax s = " + lr1.semantic.top() + ";");
        System.out.println("\t}");
        System.out.println("}");
    }
}
