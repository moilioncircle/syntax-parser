
package com.leon.tree.cst;

import java.io.IOException;

import com.leon.grammar.Grammar;
import com.leon.ll.LL1;
import com.leon.util.IToken;
import com.leon.util.Stack;

/**
 * @author : Leon
 * @since : 2013-8-12
 * @see : concrete syntax tree
 */

public class CST {
    
    public CSTNode root;
    
    public CST(IToken token, Grammar g) throws IOException {
        LL1 ll1 = new LL1();
        int[][] m = ll1.predict_table(g);
        this.root = ll1.ll1_driver(g, m, token);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph g {\n");
        sb.append("\tnode[shape = record, width = .1, height = .1];\n");
        Stack<CSTNode> stack = new Stack<CSTNode>();
        int k = 0;
        Stack<Integer> k_stack = new Stack<Integer>();
        stack.push(root);
        k_stack.push(k);
        sb.append("\tnode" + k + "[label = \"{<n> " + root.name + " }\", color = lightgray, style = filled];\n");
        while (!stack.is_empty()) {
            CSTNode parent = stack.pop();
            String parentNode = "node" + k_stack.pop();
            for (int i = 0; i < parent.childs.size(); i++) {
                String childNode = "node" + (++k);
                if (parent.childs.get(i).type == NodeType.LEAF) {
                    sb.append("\t" + childNode + "[label = \"{<n> " + parent.childs.get(i).name
                              + " }\", color = lightblue, style = filled];\n");
                }
                else {
                    sb.append("\t" + childNode + "[label = \"{<n> " + parent.childs.get(i).name
                              + " }\", color = lightgray, style = filled];\n");
                }
                sb.append("\t" + parentNode + ":n->" + childNode + ":n;\n");
                stack.push(parent.childs.get(i));
                k_stack.push(k);
            }
        }
        sb.append("}\n");
        return sb.toString();
    }
}
