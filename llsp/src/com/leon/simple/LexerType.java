
package com.leon.simple;

/**
 * @author : Leon
 * @since : 2013-8-28
 * @see :
 */

public enum LexerType {
    SEMI(1, 1), COMMA(2, 2), PLUS(3, 3), MINUS(4, 4), TIMES(5, 5), DIVIDE(6, 6), RPAREN(7, 7), NUM(8, 8), ID(9, 9),
    LPAREN(9, 9), BEGIN(10, 10), END(11, 11), READ(12, 12), WRITE(13, 13), ASSIGN(14, 14), EOF(20, 20);
    
    private int i_cost;
    private int d_cost;
    
    private LexerType(int i_cost, int d_cost) {
        this.i_cost = i_cost;
        this.d_cost = d_cost;
    }
    
    private LexerType() {
        
    }
    
    public int get_insert_cost() {
        return i_cost;
    }
    
    public int get_delete_cost() {
        return d_cost;
    }
}
