
package com.leon.tree.cst;

import java.util.ArrayList;
import java.util.List;

import com.leon.grammar.Grammar;
import com.leon.grammar.Production;
import com.leon.ll.LL1;
import com.leon.util.Stack;
import com.leon.util.Token;

/**
 * @author : Leon
 * @since : 2013-8-12
 * @see : concrete syntax tree
 */

public class CST {
    public CSTNode root;
    
    public CST(Token token,Grammar g) {
        LL1 ll1= new LL1(token);
        int[][] m = ll1.predict_table(g);
        this.root = ll1.ll1_driver(g, m);
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
    
    public static void main(String[] args) {
        List<Production> list = new ArrayList<Production>();
        String[] terminals = new String[]{"ID","INTLIT",":=",",",";","+","*","(",")","begin","end","read","write","$"};
        String start_symbol = "system_goal";
        list.add(new Production("program",new String[]{"begin","statement_list","end"}));
        list.add(new Production("statement_list",new String[]{"statement","statement_tail"}));
        list.add(new Production("statement_tail",new String[]{"statement","statement_tail"}));
        list.add(new Production("statement_tail",new String[]{}));
        list.add(new Production("statement",new String[]{"ID",":=","expression",";"}));
        list.add(new Production("statement",new String[]{"read","(","id_list",")",";"}));
        list.add(new Production("statement",new String[]{"write","(","expr_list",")",";"}));
        list.add(new Production("id_list",new String[]{"ID","id_tail"}));
        list.add(new Production("id_tail",new String[]{",","ID","id_tail"}));
        list.add(new Production("id_tail",new String[]{}));
        list.add(new Production("expr_list",new String[]{"expression","expr_tail"}));
        list.add(new Production("expr_tail",new String[]{",","expression","expr_tail"}));
        list.add(new Production("expr_tail",new String[]{}));
        list.add(new Production("expression",new String[]{"primary","primary_tail"}));
        list.add(new Production("primary_tail",new String[]{"add_op","primary","primary_tail"}));
        list.add(new Production("primary_tail",new String[]{}));
        list.add(new Production("primary",new String[]{"(","expression",")"}));
        list.add(new Production("primary",new String[]{"ID"}));
        list.add(new Production("primary",new String[]{"INTLIT"}));
        list.add(new Production("add_op",new String[]{"+"}));
        list.add(new Production("add_op",new String[]{"*"}));
        list.add(new Production("system_goal",new String[]{"program","$"}));
        Grammar g = new Grammar(start_symbol,list,terminals);
        CST cpt = new CST(new Token("begin ID := ID * INTLIT + ID ; end $".split(" ")), g);
        System.out.println(cpt);
    }
}
