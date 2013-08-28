
package com.leon.tree.cst;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.leon.grammar.Grammar;
import com.leon.grammar.Production;
import com.leon.ll.LL1;
import com.leon.simple.LexerType;
import com.leon.simple.Token;
import com.leon.util.IToken;
import com.leon.util.Stack;

/**
 * @author : Leon
 * @since : 2013-8-12
 * @see : concrete syntax tree
 */

public class CST {
    public CSTNode root;
    
    public CST(IToken<?> token,Grammar g) throws IOException {
        LL1 ll1= new LL1();
        int[][] m = ll1.predict_table(g);
        this.root = ll1.ll1_driver(g, m,token);
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\ndigraph g {");
        sb.append("\n\tnode[shape = record, width = .1, height = .1];");
        Stack<CSTNode> stack = new Stack<CSTNode>();
        int k = 0;
        Stack<Integer> k_stack = new Stack<Integer>();
        stack.push(root);
        k_stack.push(k);
        sb.append("\n\tnode" + k + "[label = \"{<n> " + root.name + " }\", color = lightgray, style = filled];");
        while (!stack.is_empty()) {
            CSTNode parent = stack.pop();
            String parentNode = "node" + k_stack.pop();
            for (int i = 0; i < parent.childs.size(); i++) {
                String childNode = "node" + (++k);
                if (parent.childs.get(i).type == NodeType.LEAF) {
                    sb.append("\n\t" + childNode + "[label = \"{<n> " + parent.childs.get(i).name
                                       + " }\", color = lightblue, style = filled];");
                }
                else {
                    sb.append("\n\t" + childNode + "[label = \"{<n> " + parent.childs.get(i).name
                                       + " }\", color = lightgray, style = filled];");
                }
                sb.append("\n\t" + parentNode + ":n->" + childNode + ":n;");
                stack.push(parent.childs.get(i));
                k_stack.push(k);
            }
        }
        sb.append("\n}");
        return sb.toString();
    }
    
    public static void main(String[] args) throws IOException {
        List<Production> list = new ArrayList<Production>();
        String[] terminals = new String[]{"ID","NUM","ASSIGN","COMMA","SEMI","PLUS","TIMES","LPAREN","RPAREN","BEGIN","END","READ","WRITE","EOG"};
        String start_symbol = "system_goal";
        list.add(new Production("program",new String[]{"BEGIN","statement_list","END"}));
        list.add(new Production("statement_list",new String[]{"statement","statement_tail"}));
        list.add(new Production("statement_tail",new String[]{"statement","statement_tail"}));
        list.add(new Production("statement_tail",new String[]{}));
        list.add(new Production("statement",new String[]{"ID","ASSIGN","expression","SEMI"}));
        list.add(new Production("statement",new String[]{"READ","LPAREN","id_list","RPAREN","SEMI"}));
        list.add(new Production("statement",new String[]{"WRITE","LPAREN","expr_list","RPAREN","SEMI"}));
        list.add(new Production("id_list",new String[]{"ID","id_tail"}));
        list.add(new Production("id_tail",new String[]{"COMMA","ID","id_tail"}));
        list.add(new Production("id_tail",new String[]{}));
        list.add(new Production("expr_list",new String[]{"expression","expr_tail"}));
        list.add(new Production("expr_tail",new String[]{"COMMA","expression","expr_tail"}));
        list.add(new Production("expr_tail",new String[]{}));
        list.add(new Production("expression",new String[]{"primary","primary_tail"}));
        list.add(new Production("primary_tail",new String[]{"add_op","primary","primary_tail"}));
        list.add(new Production("primary_tail",new String[]{}));
        list.add(new Production("primary",new String[]{"LPAREN","expression","RPAREN"}));
        list.add(new Production("primary",new String[]{"ID"}));
        list.add(new Production("primary",new String[]{"NUM"}));
        list.add(new Production("add_op",new String[]{"PLUS"}));
        list.add(new Production("add_op",new String[]{"TIMES"}));
        list.add(new Production("system_goal",new String[]{"program","EOG"}));
        Grammar g = new Grammar(start_symbol,list,terminals);
        IToken<LexerType> t = new Token(new StringReader("begin a:=b*5+c;end$"));
        CST c = new CST(t, g);
        System.out.println(c.toString());
    }
}
