package com.leon.generator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import com.leon.cc.CCGrammar;
import com.leon.cc.Syntax;
import com.leon.dynamic.CharSequenceJavaFileObject;
import com.leon.dynamic.ClassFileManager;
import com.leon.lr.LR1;
import com.leon.util.ISymbol;
import com.leon.util.Utils;


/** @author : Leon
 * @since   : 2013-9-12
 * @see    : 
 */
public class SyntaxCodeGenerator extends CodeGenerator {
    public SyntaxCodeGenerator() {
    }

    public Object generate(String fileName,Class<?> clazz,List<ISymbol> parse_str,String className) throws Exception {
        List<ISymbol> t = Utils.getSymbolList("syntax.g", CCGrammar.class);
        Syntax s = generate_program(generate_descriptor(t.get(0),generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_terminal(generate_token(t.get(3)),t.get(4),t.get(5))),generate_terminal(generate_token(t.get(9)),t.get(10),t.get(11))),generate_terminal(generate_token(t.get(15)),t.get(16),t.get(17))),generate_terminal(generate_token(t.get(21)),t.get(22),t.get(23))),generate_terminal(generate_token(t.get(27)),t.get(28),t.get(29))),generate_terminal(generate_token(t.get(33)),t.get(34),t.get(35))),generate_terminal(generate_token(t.get(39)),t.get(40),t.get(41))),generate_terminal(generate_token(t.get(45)),t.get(46),t.get(47))),generate_terminal(generate_token(t.get(51)),t.get(52),t.get(53))),generate_terminal(generate_token(t.get(57)),t.get(58),t.get(59))),generate_terminal(generate_token(t.get(63)),t.get(64),t.get(65))),generate_terminal(generate_token(t.get(69)),t.get(70),t.get(71))),generate_terminal(generate_token(t.get(75)),t.get(76),t.get(77))),generate_terminal(generate_token(t.get(81)),t.get(82),t.get(83))),generate_terminal(generate_token(t.get(87)),t.get(88),t.get(89))),generate_terminal(generate_token(t.get(93)),t.get(94),t.get(95))),generate_start_symbol(generate_token(t.get(99)))),generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(t.get(102),generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(104)),t.get(105)),null,t.get(106)))),t.get(108),generate_rules(generate_grammarRule(generate_rule(generate_rule(generate_rule(generate_rule(generate_rule(t.get(110)),t.get(111)),t.get(112)),t.get(113)),t.get(114)),null,t.get(115)))),t.get(117),generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(119)),t.get(120)),null,t.get(121))),generate_grammarRule(generate_rule(t.get(123)),null,t.get(124)))),t.get(126),generate_rules(generate_grammarRule(generate_rule(t.get(128)),null,t.get(129)))),t.get(131),generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(133)),t.get(134)),null,t.get(135))),generate_grammarRule(generate_rule(t.get(137)),null,t.get(138)))),t.get(140),generate_rules(generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(generate_rule(t.get(142)),t.get(143)),t.get(144)),null,t.get(145))),generate_grammarRule(generate_rule(generate_rule(generate_rule(generate_rule(generate_rule(generate_rule(t.get(147)),t.get(148)),t.get(149)),t.get(150)),t.get(151)),t.get(152)),null,t.get(153))),generate_grammarRule(generate_rule(generate_rule(generate_rule(generate_rule(t.get(155)),t.get(156)),t.get(157)),t.get(158)),null,t.get(159)))),t.get(161),generate_rules(generate_rules(generate_rules(generate_rules(generate_grammarRule(generate_rule(t.get(163)),null,t.get(164))),generate_grammarRule(generate_rule(t.get(166)),null,t.get(167))),generate_grammarRule(generate_rule(t.get(169)),null,t.get(170))),generate_grammarRule(generate_rule(t.get(172)),null,t.get(173)))),t.get(175),generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(generate_rule(generate_rule(t.get(177)),t.get(178)),t.get(179)),t.get(180)),null,t.get(181))),generate_grammarRule(generate_rule(t.get(183)),null,t.get(184)))),t.get(186),generate_rules(generate_grammarRule(generate_rule(t.get(188)),null,t.get(189)))),t.get(191),generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(generate_rule(generate_rule(generate_rule(t.get(193)),t.get(194)),t.get(195)),t.get(196)),t.get(197)),null,t.get(198))),generate_grammarRule(generate_rule(generate_rule(generate_rule(generate_rule(t.get(200)),t.get(201)),t.get(202)),t.get(203)),null,t.get(204)))),t.get(206),generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(generate_rule(t.get(208)),t.get(209)),t.get(210)),null,t.get(211))),generate_grammarRule(generate_rule(t.get(213)),null,t.get(214)))),t.get(216),generate_rules(generate_rules(generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(generate_rule(t.get(218)),t.get(219)),t.get(220)),null,t.get(221))),generate_grammarRule(generate_rule(generate_rule(t.get(223)),t.get(224)),null,t.get(225))),generate_grammarRule(generate_rule(t.get(227)),null,t.get(228))),generate_grammarRule(null,null,t.get(230)))),t.get(232),generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(234)),t.get(235)),null,t.get(236))),generate_grammarRule(generate_rule(t.get(238)),null,t.get(239)))),t.get(241),generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(243)),t.get(244)),null,t.get(245)))),generate_footer(t.get(248))));
        LR1 lr1 = new LR1(s.g);
        List<ISymbol> list = Utils.getSymbolList(fileName, clazz);
        lr1.lr1_driver(list);
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("\nimport java.io.IOException;");
        sb.append("\nimport java.util.List;");
        sb.append("\n");
        sb.append("\nimport com.leon.cc.Syntax;");
        sb.append("\nimport com.leon.generator.CodeGenerator;");
        sb.append("\nimport com.leon.lr.LR1;");
        sb.append("\nimport com.leon.util.ISymbol;");
        sb.append("\nimport com.leon.util.Utils;");
        sb.append("\n/** @author : Leon");
        sb.append("\n * @since   : "+new Date(System.currentTimeMillis()));
        sb.append("\n * @see    : ");
        sb.append("\n */");
        sb.append("\npublic class IR extends CodeGenerator{");
        sb.append("\n");
        sb.append("\n\tpublic IR() throws IOException {");
        sb.append("\n\t}");
        sb.append("\n");
        sb.append("\n\tpublic Object generate(List<ISymbol> list,String className) throws Exception {");
        sb.append("\n\t\tList<ISymbol> t = Utils.getSymbolList(\""+fileName+"\", "+clazz.getName()+".class);");
        sb.append("\n\t\tSyntax s = " + lr1.semantic.top() + ";");
        sb.append("\n\t\tLR1 lr1 = new LR1(s.g);");
        sb.append("\n\t\tlr1.lr1_driver(list);");
        sb.append("\n\t\tStringBuilder sb = new StringBuilder();");
        sb.append("\n\t\tsb.append(\"\\n\"+s.header);");
        sb.append("\n\t\tsb.append(\"\\n    public Object generate(List<ISymbol> t){\");");
        sb.append("\n\t\tsb.append(\"\\n        return \"+lr1.semantic.top()+\";\");");
        sb.append("\n\t\tsb.append(\"\\n    }\");");
        sb.append("\n\t\tsb.append(\"\\n\"+s.footer);");
        sb.append("\n\t\treturn dynamic_compile(list, className, sb.toString());");
        sb.append("\n\t}");
        sb.append("\n");
        sb.append("\n}");
        System.out.println(sb.toString());
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        JavaFileManager fileManager = new ClassFileManager(compiler.getStandardFileManager(null, null, null));
        List<JavaFileObject> jfiles = new ArrayList<JavaFileObject>();
        jfiles.add(new CharSequenceJavaFileObject("IR", sb.toString()));
        compiler.getTask(null, fileManager, null, null, null, jfiles).call();
        Object instance = fileManager.getClassLoader(null).loadClass("IR").newInstance();
        return instance.getClass().getMethod("generate", List.class,String.class).invoke(instance, parse_str,className);
    }

}
