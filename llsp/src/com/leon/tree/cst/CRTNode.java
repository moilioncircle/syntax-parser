package com.leon.tree.cst;

import java.util.ArrayList;
import java.util.List;


/** @author : Leon
 * @since   : 2013-8-12
 * @see    : concrete syntax tree node
 */

public abstract class CRTNode {
    public String name;
    public NodeType type;
    public List<CRTNode> childs = new ArrayList<CRTNode>();
    @Override
    public String toString() {
        return this.name;
    }
}


