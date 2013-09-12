package com.leon.generator;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.leon.cc.CCToken;
import com.leon.cc.Syntax;
import com.leon.lr.LR1;
import com.leon.util.ISymbol;
import com.leon.util.IToken;
import com.leon.util.Utils;


/** @author : Leon
 * @since   : 2013-9-12
 * @see    : 
 */
//generate CCGrammar;
public class SyntaxCodeGenerator extends CodeGenerator{
    private Syntax s;
    private List<ISymbol> t;
    //syntax.g,CCGrammar.class
    public SyntaxCodeGenerator(String fileName,Class<?> clazz) throws IOException {
        this.t = Utils.getSymbolList(fileName, clazz);
        this.s = generate();
    }

    public Syntax generate() throws IOException {
        Syntax s = generate_descriptor(t.get(0),generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_terminal(generate_token(t.get(3)),t.get(4),t.get(5))),generate_terminal(generate_token(t.get(9)),t.get(10),t.get(11))),generate_terminal(generate_token(t.get(15)),t.get(16),t.get(17))),generate_terminal(generate_token(t.get(21)),t.get(22),t.get(23))),generate_terminal(generate_token(t.get(27)),t.get(28),t.get(29))),generate_terminal(generate_token(t.get(33)),t.get(34),t.get(35))),generate_terminal(generate_token(t.get(39)),t.get(40),t.get(41))),generate_terminal(generate_token(t.get(45)),t.get(46),t.get(47))),generate_terminal(generate_token(t.get(51)),t.get(52),t.get(53))),generate_terminal(generate_token(t.get(57)),t.get(58),t.get(59))),generate_terminal(generate_token(t.get(63)),t.get(64),t.get(65))),generate_terminal(generate_token(t.get(69)),t.get(70),t.get(71))),generate_terminal(generate_token(t.get(75)),t.get(76),t.get(77))),generate_terminal(generate_token(t.get(81)),t.get(82),t.get(83))),generate_terminal(generate_token(t.get(87)),t.get(88),t.get(89))),generate_terminal(generate_token(t.get(93)),t.get(94),t.get(95))),generate_start_symbol(generate_token(t.get(99)))),generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(t.get(102),generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(104)),t.get(105)),null,t.get(106)))),t.get(108),generate_rules(generate_grammarRule(generate_rule(generate_rule(generate_rule(generate_rule(generate_rule(t.get(110)),t.get(111)),t.get(112)),t.get(113)),t.get(114)),null,t.get(115)))),t.get(117),generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(119)),t.get(120)),null,t.get(121))),generate_grammarRule(generate_rule(t.get(123)),null,t.get(124)))),t.get(126),generate_rules(generate_grammarRule(generate_rule(t.get(128)),null,t.get(129)))),t.get(131),generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(133)),t.get(134)),null,t.get(135))),generate_grammarRule(generate_rule(t.get(137)),null,t.get(138)))),t.get(140),generate_rules(generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(generate_rule(t.get(142)),t.get(143)),t.get(144)),null,t.get(145))),generate_grammarRule(generate_rule(generate_rule(generate_rule(generate_rule(generate_rule(generate_rule(t.get(147)),t.get(148)),t.get(149)),t.get(150)),t.get(151)),t.get(152)),null,t.get(153))),generate_grammarRule(generate_rule(generate_rule(generate_rule(generate_rule(t.get(155)),t.get(156)),t.get(157)),t.get(158)),null,t.get(159)))),t.get(161),generate_rules(generate_rules(generate_rules(generate_rules(generate_grammarRule(generate_rule(t.get(163)),null,t.get(164))),generate_grammarRule(generate_rule(t.get(166)),null,t.get(167))),generate_grammarRule(generate_rule(t.get(169)),null,t.get(170))),generate_grammarRule(generate_rule(t.get(172)),null,t.get(173)))),t.get(175),generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(generate_rule(generate_rule(t.get(177)),t.get(178)),t.get(179)),t.get(180)),null,t.get(181))),generate_grammarRule(generate_rule(t.get(183)),null,t.get(184)))),t.get(186),generate_rules(generate_grammarRule(generate_rule(t.get(188)),null,t.get(189)))),t.get(191),generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(generate_rule(generate_rule(generate_rule(t.get(193)),t.get(194)),t.get(195)),t.get(196)),t.get(197)),null,t.get(198))),generate_grammarRule(generate_rule(generate_rule(generate_rule(generate_rule(t.get(200)),t.get(201)),t.get(202)),t.get(203)),null,t.get(204)))),t.get(206),generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(generate_rule(t.get(208)),t.get(209)),t.get(210)),null,t.get(211))),generate_grammarRule(generate_rule(t.get(213)),null,t.get(214)))),t.get(216),generate_rules(generate_rules(generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(generate_rule(t.get(218)),t.get(219)),t.get(220)),null,t.get(221))),generate_grammarRule(generate_rule(generate_rule(t.get(223)),t.get(224)),null,t.get(225))),generate_grammarRule(generate_rule(t.get(227)),null,t.get(228))),generate_grammarRule(null,null,t.get(230)))),t.get(232),generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(234)),t.get(235)),null,t.get(236))),generate_grammarRule(generate_rule(t.get(238)),null,t.get(239)))),t.get(241),generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(243)),t.get(244)),null,t.get(245)))),generate_footer(t.get(248)));
        return s;
    }
    
    public void generate_file(String fileName,Class<?> clazz) throws IOException{
        InputStreamReader reader = new InputStreamReader(clazz.getResourceAsStream(fileName), "UTF8");
        IToken token = new CCToken(reader);
        List<ISymbol> list = new ArrayList<ISymbol>();
        while (token.has_next()) {
            list.add(token.next_token());
        }
        LR1 lr1 = new LR1(s.g);
        lr1.lr1_driver(list);
        //calc.g
        System.out.println("package com.leon.generator;");
        System.out.println("import java.io.IOException;");
        System.out.println("import java.util.List;");
        System.out.println("import com.leon.cc.Syntax;");
        System.out.println("import com.leon.util.ISymbol;");
        System.out.println("import com.leon.util.Utils;");
        System.out.println("public class IR extends CodeGenerator{");
        System.out.println("\tpublic Syntax s;");
        System.out.println("\tprivate List<ISymbol> t;");
        System.out.println("\tpublic IR(String fileName,Class<?> clazz) throws IOException {");
        System.out.println("\t\tthis.t = Utils.getSymbolList(fileName, clazz);");
        System.out.println("\t\tthis.s = generate();");
        System.out.println("\t}");
        System.out.println("\tpublic Syntax generate() throws IOException {");
        System.out.println("\t\tSyntax s = " + lr1.semantic.top() + ";");
        System.out.println("\t\treturn s;");
        System.out.println("\t}");
        System.out.println("}");
    }
    
}


