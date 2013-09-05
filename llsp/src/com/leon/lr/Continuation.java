package com.leon.lr;

import com.leon.grammar.Production;


/** @author : Leon
 * @since   : 2013-9-5
 * @see    : 
 */

public class Continuation {
    public ContinuationType type;
    public String symbol;
    public Production p;
    @Override
    public String toString() {
        return "Continuation [type=" + type + ", symbol=" + symbol + ", p=" + p + "]";
    }
}


