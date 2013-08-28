
package com.leon.tree.cst;

/**
 * @author : Leon
 * @since : 2013-8-13
 * @see :
 */

public class InternalNode extends CSTNode {
    
    public InternalNode(String name) {
        this.name = name;
        this.type = NodeType.INTERNAL;
    }
}
