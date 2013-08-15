package com.leon.tree.cpt;


/** @author : Leon
 * @since   : 2013-8-13
 * @see    : 
 */

public class LeafNode extends CPNode{
    public LeafNode(String name){
        this.name = name;
        this.type = NodeType.LEAF;
    }
}


