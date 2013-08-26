package com.leon.tree.cst;


/** @author : Leon
 * @since   : 2013-8-13
 * @see    : 
 */

public class LeafNode extends CRTNode{
    public LeafNode(String name){
        this.name = name;
        this.type = NodeType.LEAF;
    }
}


