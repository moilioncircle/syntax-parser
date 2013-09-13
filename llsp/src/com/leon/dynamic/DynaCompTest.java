
package com.leon.dynamic;

import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

/**
 * @author : Leon
 * @since : 2013-9-13
 * @see :
 */

public class DynaCompTest {
    
    public static void main(String[] args) throws Exception {
        // Full name of the class that will be compiled.
        // If class should be in some package,
        // fullName should contain it too
        // (ex. "testpackage.DynaClass")
        String fullName = "DynaClass";
        
        // Here we specify the source code of the class to be compiled
        StringBuilder src = new StringBuilder();
        src.append("import com.leon.generator.CodeGenerator;\n");
        src.append("public class DynaClass extends CodeGenerator{\n");
        src.append("    public String toString() {\n");
        src.append("        return \"Hello, I am \" + ");
        src.append("this.getClass().getSimpleName();\n");
        src.append("    }\n");
        src.append("}\n");
        
        System.out.println(src);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        JavaFileManager fileManager = new ClassFileManager(compiler.getStandardFileManager(null, null, null));
        List<JavaFileObject> jfiles = new ArrayList<JavaFileObject>();
        jfiles.add(new CharSequenceJavaFileObject(fullName, src));
        compiler.getTask(null, fileManager, null, null, null, jfiles).call();
        Object instance = fileManager.getClassLoader(null).loadClass(fullName).newInstance();
        System.out.println(instance);
    }
}
