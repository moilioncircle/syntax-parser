package com.leon.tree.cpt;

import java.util.ArrayList;
import java.util.List;


/** @author : Leon
 * @since   : 2013-8-12
 * @see    : concrete parse tree node
 */

public abstract class CPNode {
    public String name;
    public NodeType type;
    public List<CPNode> childs = new ArrayList<CPNode>();
}
enum NodeType{
    LEAF,INTERNAL;
}

