
package com.leon.tree.cpt;

import com.leon.util.Stack;
import com.leon.util.Token;

/**
 * @author : Leon
 * @since : 2013-8-12
 * @see : concrete parse tree
 */

public class CPT {
    
    Token token;
    
    public CPT(String[] str) {
        this.token = new Token(str);
    }
    
    public CPNode program() {
        CPNode node = new InternalNode("program");
        String tok = current_token();
        switch (tok) {
            case "begin":
                node.childs.add(match("begin"));
                node.childs.add(statement_list());
                node.childs.add(match("end"));
                break;
            default:
                syntax_error(tok);
                break;
        }
        return node;
    }
    
    public CPNode statement_list() {
        CPNode node = new InternalNode("statement_list");
        String tok = current_token();
        switch (tok) {
            case "ID":
                node.childs.add(statement());
                node.childs.add(statement_tail());
                break;
            case "read":
                node.childs.add(statement());
                node.childs.add(statement_tail());
                break;
            case "write":
                node.childs.add(statement());
                node.childs.add(statement_tail());
                break;
            default:
                syntax_error(tok);
                break;
        }
        return node;
    }
    
    public CPNode statement() {
        CPNode node = new InternalNode("statement");
        String tok = current_token();
        switch (tok) {
            case "ID":
                node.childs.add(match("ID"));
                node.childs.add(match(":="));
                node.childs.add(expression());
                node.childs.add(match(";"));
                break;
            case "read":
                node.childs.add(match("read"));
                node.childs.add(match("("));
                node.childs.add(id_list());
                node.childs.add(match(")"));
                node.childs.add(match(";"));
                break;
            case "write":
                node.childs.add(match("write"));
                node.childs.add(match("("));
                node.childs.add(expr_list());
                node.childs.add(match(")"));
                node.childs.add(match(";"));
                break;
            default:
                syntax_error(tok);
                break;
        }
        return node;
    }
    
    public CPNode statement_tail() {
        CPNode node = new InternalNode("statement_tail");
        String tok = current_token();
        switch (tok) {
            case "ID":
                node.childs.add(statement());
                node.childs.add(statement_tail());
                break;
            case "end":
                /* null */
                break;
            case "read":
                node.childs.add(statement());
                node.childs.add(statement_tail());
                break;
            case "write":
                node.childs.add(statement());
                node.childs.add(statement_tail());
                break;
            default:
                syntax_error(tok);
                break;
        }
        return node;
    }
    
    public CPNode expression() {
        CPNode node = new InternalNode("expression");
        String tok = current_token();
        switch (tok) {
            case "ID":
                node.childs.add(primary());
                node.childs.add(primary_tail());
                break;
            case "INTLIT":
                node.childs.add(primary());
                node.childs.add(primary_tail());
                break;
            case "(":
                node.childs.add(primary());
                node.childs.add(primary_tail());
                break;
            default:
                syntax_error(tok);
                break;
        }
        return node;
    }
    
    public CPNode id_list() {
        CPNode node = new InternalNode("id_list");
        String tok = current_token();
        switch (tok) {
            case "ID":
                node.childs.add(match("ID"));
                node.childs.add(id_tail());
                break;
            default:
                syntax_error(tok);
                break;
        }
        return node;
    }
    
    public CPNode expr_list() {
        CPNode node = new InternalNode("expr_list");
        String tok = current_token();
        switch (tok) {
            case "ID":
                node.childs.add(expression());
                node.childs.add(expr_tail());
                break;
            case "INTLIT":
                node.childs.add(expression());
                node.childs.add(expr_tail());
                break;
            case "(":
                node.childs.add(expression());
                node.childs.add(expr_tail());
                break;
            default:
                syntax_error(tok);
                break;
        }
        return node;
    }
    
    public CPNode id_tail() {
        CPNode node = new InternalNode("id_tail");
        String tok = current_token();
        switch (tok) {
            case ",":
                node.childs.add(match(","));
                node.childs.add(match("ID"));
                node.childs.add(id_tail());
                break;
            case ")":
                /* null */
                break;
            default:
                syntax_error(tok);
                break;
        }
        return node;
    }
    
    public CPNode expr_tail() {
        CPNode node = new InternalNode("expr_tail");
        String tok = current_token();
        switch (tok) {
            case ",":
                node.childs.add(match(","));
                node.childs.add(expression());
                node.childs.add(expr_tail());
                break;
            case ")":
                /* null */
                break;
            default:
                syntax_error(tok);
                break;
        }
        return node;
    }
    
    public CPNode primary() {
        CPNode node = new InternalNode("primary");
        String tok = current_token();
        switch (tok) {
            case "ID":
                node.childs.add(match("ID"));
                break;
            case "INTLIT":
                node.childs.add(match("INTLIT"));
                break;
            case "(":
                node.childs.add(match("("));
                node.childs.add(expression());
                node.childs.add(match(")"));
                break;
            default:
                syntax_error(tok);
                break;
        }
        return node;
    }
    
    public CPNode primary_tail() {
        CPNode node = new InternalNode("primary_tail");
        String tok = current_token();
        switch (tok) {
            case ",":
                /* null */
                break;
            case ";":
                /* null */
                break;
            case "+":
                node.childs.add(add_op());
                node.childs.add(primary());
                node.childs.add(primary_tail());
                break;
            case "*":
                node.childs.add(add_op());
                node.childs.add(primary());
                node.childs.add(primary_tail());
                break;
            case ")":
                /* null */
                break;
            default:
                syntax_error(tok);
                break;
        }
        return node;
    }
    
    public CPNode add_op() {
        CPNode node = new InternalNode("add_op");
        String tok = current_token();
        switch (tok) {
            case "+":
                node.childs.add(match("+"));
                break;
            case "*":
                node.childs.add(match("*"));
                break;
            default:
                syntax_error(tok);
                break;
        }
        return node;
    }
    
    public CPNode system_goal() {
        CPNode node = new InternalNode("system_goal");
        String tok = current_token();
        switch (tok) {
            case "begin":
                node.childs.add(program());
                node.childs.add(match("$"));
                break;
            default:
                syntax_error(tok);
                break;
        }
        return node;
    }
    
    private CPNode match(String tok) {
        if (current_token().equals(tok)) {
            CPNode node = new LeafNode(tok);
            token.next_token();
            return node;
        }
        else {
            syntax_error(tok);
        }
        return null;
    }
    
    private void syntax_error(String tok) {
        System.out.println("syntax error:" + tok);
    }
    
    private String current_token() {
        return token.current_token();
    }
    
    public static void main(String[] args) {
        String[] str = "begin ID := ID * INTLIT + ID ; end $".split(" ");
        CPT ast = new CPT(str);
        System.out.println("digraph g {");
        System.out.println("\tnode[shape = record, width = .1, height = .1];");
        CPNode root = ast.system_goal();
        Stack<CPNode> stack = new Stack<CPNode>();
        int k = 0;
        Stack<Integer> k_stack = new Stack<Integer>();
        stack.push(root);
        k_stack.push(k);
        System.out.println("\tnode" + k + "[label = \"{<n> " + root.name + " }\", color = lightgray, style = filled];");
        while (!stack.is_empty()) {
            CPNode parent = stack.pop();
            String parentNode = "node" + k_stack.pop();
            for (int i = 0; i < parent.childs.size(); i++) {
                String childNode = "node" + (++k);
                if (parent.childs.get(i).type == NodeType.LEAF) {
                    System.out.println("\t" + childNode + "[label = \"{<n> " + parent.childs.get(i).name
                                       + " }\", color = lightblue, style = filled];");
                }
                else {
                    System.out.println("\t" + childNode + "[label = \"{<n> " + parent.childs.get(i).name
                                       + " }\", color = lightgray, style = filled];");
                }
                System.out.println("\t" + parentNode + ":n->" + childNode + ":n;");
                stack.push(parent.childs.get(i));
                k_stack.push(k);
            }
        }
        System.out.println("}");
    }
}
