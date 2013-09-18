
package com.leon.generator;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

/**
 * @author : Leon
 * @since : 2013-9-12
 * @see :
 */
public class Compile extends CodeGenerator {
    
    public static final Logger logger = Logger.getLogger(Compile.class.getName());
    
    public Compile() {
    }
    
    public Object ast(File file, File parse_target, Class<? extends IToken> clazz) throws Exception {
        SyntaxCodeGenerator scg = new SyntaxCodeGenerator();
        Syntax s = (Syntax) scg.generate();
        LR1 lr1 = new LR1(s.g);
        List<ISymbol> list = Utils.getSymbolList(file, CCToken.class);
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
        sb.append("\n * @since   : " + new Date(System.currentTimeMillis()));
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
        sb.append("\n\t\tsb.append(\"\\n        List<com.leon.util.ISymbol> t = com.leon.util.Utils.getSymbolList(new java.io.File(\\\""
                  + parse_target.getPath().replaceAll("\\\\", "/") + "\\\")," + clazz.getName() + ".class);\");");
        sb.append("\n\t\tsb.append(\"\\n        return \"+lr1.semantic.top()+\";\");");
        sb.append("\n\t\tsb.append(\"\\n    }\");");
        sb.append("\n\t\tsb.append(\"\\n\"+s.footer);");
        sb.append("\n\t\treturn dynamic_compile(s.classname, sb.toString());");
        sb.append("\n\t}");
        sb.append("\n");
        sb.append("\n}");
        logger.log(Level.INFO, sb.toString());
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        JavaFileManager fileManager = new ClassFileManager(compiler.getStandardFileManager(null, null, null));
        List<JavaFileObject> jfiles = new ArrayList<JavaFileObject>();
        jfiles.add(new CharSequenceJavaFileObject("IR", sb.toString()));
        compiler.getTask(null, fileManager, null, null, null, jfiles).call();
        Object instance = fileManager.getClassLoader(null).loadClass("IR").newInstance();
        return instance.getClass()
                       .getMethod("generate", File.class, File.class, Class.class)
                       .invoke(instance, file, parse_target, clazz);
    }
    
}
