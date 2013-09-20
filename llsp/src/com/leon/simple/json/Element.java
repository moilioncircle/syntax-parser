
package com.leon.simple.json;

/**
 * @author : Leon
 * @since : 2013-9-20
 * @see :
 */

public class Element {
    
    private Element element;
    private Value   value;
    
    public Element(Value value) {
        this.value = value;
    }
    
    public Element(Value value, Element element) {
        this.value = value;
        this.element = element;
    }
}
