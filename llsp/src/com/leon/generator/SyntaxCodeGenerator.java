package com.leon.generator;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import com.leon.cc.CCToken;
import com.leon.cc.Syntax;
import com.leon.dynamic.CharSequenceJavaFileObject;
import com.leon.dynamic.ClassFileManager;
import com.leon.lr.LR1;
import com.leon.util.ISymbol;
import com.leon.util.IToken;
import com.leon.util.Utils;


/** @author : Leon
 * @since   : 2013-9-12
 * @see    : 
 */
public class SyntaxCodeGenerator extends CodeGenerator {
    public SyntaxCodeGenerator() {
    }

    public Object generate(File file,File parse_target,Class<? extends IToken> clazz) throws Exception {
        List<ISymbol> t = Utils.getSymbolList(new File("resourses/syntax.g"),CCToken.class);
        Syntax s = generate_program(generate_descriptor(t.get(0),generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_declarations(generate_terminal(generate_token(t.get(3)),t.get(4),t.get(5))),generate_terminal(generate_token(t.get(9)),t.get(10),t.get(11))),generate_terminal(generate_token(t.get(15)),t.get(16),t.get(17))),generate_terminal(generate_token(t.get(21)),t.get(22),t.get(23))),generate_terminal(generate_token(t.get(27)),t.get(28),t.get(29))),generate_terminal(generate_token(t.get(33)),t.get(34),t.get(35))),generate_terminal(generate_token(t.get(39)),t.get(40),t.get(41))),generate_terminal(generate_token(t.get(45)),t.get(46),t.get(47))),generate_terminal(generate_token(t.get(51)),t.get(52),t.get(53))),generate_terminal(generate_token(t.get(57)),t.get(58),t.get(59))),generate_terminal(generate_token(t.get(63)),t.get(64),t.get(65))),generate_terminal(generate_token(t.get(69)),t.get(70),t.get(71))),generate_terminal(generate_token(t.get(75)),t.get(76),t.get(77))),generate_terminal(generate_token(t.get(81)),t.get(82),t.get(83))),generate_terminal(generate_token(t.get(87)),t.get(88),t.get(89))),generate_terminal(generate_token(t.get(93)),t.get(94),t.get(95))),generate_terminal(generate_token(t.get(99)),t.get(100),t.get(101))),generate_start_symbol(t.get(103),generate_token(t.get(105)))),generate_classname(t.get(107),generate_token(t.get(108)))),generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(generate_productions(t.get(111),generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(113)),t.get(114)),null,t.get(115)))),t.get(117),generate_rules(generate_grammarRule(generate_rule(generate_rule(generate_rule(generate_rule(generate_rule(t.get(119)),t.get(120)),t.get(121)),t.get(122)),t.get(123)),null,t.get(124)))),t.get(126),generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(128)),t.get(129)),null,t.get(130))),generate_grammarRule(generate_rule(t.get(132)),null,t.get(133)))),t.get(135),generate_rules(generate_grammarRule(generate_rule(t.get(137)),null,t.get(138)))),t.get(140),generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(142)),t.get(143)),null,t.get(144))),generate_grammarRule(generate_rule(t.get(146)),null,t.get(147)))),t.get(149),generate_rules(generate_rules(generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(generate_rule(t.get(151)),t.get(152)),t.get(153)),null,t.get(154))),generate_grammarRule(generate_rule(generate_rule(generate_rule(generate_rule(generate_rule(generate_rule(t.get(156)),t.get(157)),t.get(158)),t.get(159)),t.get(160)),t.get(161)),null,t.get(162))),generate_grammarRule(generate_rule(generate_rule(generate_rule(generate_rule(t.get(164)),t.get(165)),t.get(166)),t.get(167)),null,t.get(168))),generate_grammarRule(generate_rule(generate_rule(generate_rule(t.get(170)),t.get(171)),t.get(172)),null,t.get(173)))),t.get(175),generate_rules(generate_rules(generate_rules(generate_rules(generate_grammarRule(generate_rule(t.get(177)),null,t.get(178))),generate_grammarRule(generate_rule(t.get(180)),null,t.get(181))),generate_grammarRule(generate_rule(t.get(183)),null,t.get(184))),generate_grammarRule(generate_rule(t.get(186)),null,t.get(187)))),t.get(189),generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(generate_rule(t.get(191)),t.get(192)),t.get(193)),null,t.get(194))),generate_grammarRule(generate_rule(t.get(196)),null,t.get(197)))),t.get(199),generate_rules(generate_grammarRule(generate_rule(t.get(201)),null,t.get(202)))),t.get(204),generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(generate_rule(generate_rule(generate_rule(t.get(206)),t.get(207)),t.get(208)),t.get(209)),t.get(210)),null,t.get(211))),generate_grammarRule(generate_rule(generate_rule(generate_rule(generate_rule(t.get(213)),t.get(214)),t.get(215)),t.get(216)),null,t.get(217)))),t.get(219),generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(generate_rule(t.get(221)),t.get(222)),t.get(223)),null,t.get(224))),generate_grammarRule(generate_rule(t.get(226)),null,t.get(227)))),t.get(229),generate_rules(generate_rules(generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(generate_rule(t.get(231)),t.get(232)),t.get(233)),null,t.get(234))),generate_grammarRule(generate_rule(generate_rule(t.get(236)),t.get(237)),null,t.get(238))),generate_grammarRule(generate_rule(t.get(240)),null,t.get(241))),generate_grammarRule(null,null,t.get(243)))),t.get(245),generate_rules(generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(247)),t.get(248)),null,t.get(249))),generate_grammarRule(generate_rule(t.get(251)),null,t.get(252)))),t.get(254),generate_rules(generate_grammarRule(generate_rule(generate_rule(t.get(256)),t.get(257)),null,t.get(258)))),generate_footer(t.get(261))));
        LR1 lr1 = new LR1(s.g);
        List<ISymbol> list = Utils.getSymbolList(file,CCToken.class);
        lr1.lr1_driver(list);
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("\nimport java.util.List;");
        sb.append("\nimport java.io.File;");
        sb.append("\n");
        sb.append("\nimport com.leon.util.IToken;");
        sb.append("\nimport com.leon.util.ISymbol;");
        sb.append("\nimport com.leon.cc.Syntax;");
        sb.append("\nimport com.leon.cc.CCToken;");
        sb.append("\nimport com.leon.generator.CodeGenerator;");
        sb.append("\nimport com.leon.lr.LR1;");
        sb.append("\nimport com.leon.util.Utils;");
        sb.append("\n/** @author : Leon");
        sb.append("\n * @since   : "+new Date(System.currentTimeMillis()));
        sb.append("\n * @see    : ");
        sb.append("\n */");
        sb.append("\npublic class IR extends CodeGenerator{");
        sb.append("\n");
        sb.append("\n\tpublic Object generate(File file,File parse_target,Class<? extends IToken> clazz) throws Exception {");
        sb.append("\n\t\tList<ISymbol> t = Utils.getSymbolList(file,CCToken.class);");
        sb.append("\n\t\tSyntax s = " + lr1.semantic.top() + ";");
        sb.append("\n\t\tLR1 lr1 = new LR1(s.g);");
        sb.append("\n\t\tList<ISymbol> list = Utils.getSymbolList(parse_target,clazz);");
        sb.append("\n\t\tlr1.lr1_driver(list);");
        sb.append("\n\t\tStringBuilder sb = new StringBuilder();");
        sb.append("\n\t\tsb.append(\"\\n\"+s.header);");
        sb.append("\n\t\tsb.append(\"\\n    public Object generate() throws Exception {\");");
        sb.append("\n\t\tsb.append(\"\\n        List<com.leon.util.ISymbol> t = com.leon.util.Utils.getSymbolList(new java.io.File(\\\""+parse_target.getPath().replaceAll("\\\\", "/")+"\\\"),"+clazz.getName()+".class);\");");
        sb.append("\n\t\tsb.append(\"\\n        return \"+lr1.semantic.top()+\";\");");
        sb.append("\n\t\tsb.append(\"\\n    }\");");
        sb.append("\n\t\tsb.append(\"\\n\"+s.footer);");
        sb.append("\n\t\treturn dynamic_compile(s.classname, sb.toString());");
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
        return instance.getClass().getMethod("generate", File.class,File.class,Class.class).invoke(instance, file ,parse_target,clazz);
    }

}
