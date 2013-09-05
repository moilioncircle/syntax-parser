
package com.leon.simple;

import com.leon.util.ISymbol;

/**
 * @author : Leon
 * @since : 2013-8-27
 * @see :
 */

public class Symbol implements ISymbol<LexerType> {
    
    public int       yyline;
    public int       yycolumn;
    public LexerType type;
    public Object    value;
    
    public Symbol(LexerType type, int yyline, int yycolumn, Object value) {
        this(type, yyline, yycolumn);
        this.value = value;
    }
    
    public Symbol(LexerType type, int yyline, int yycolumn) {
        this.type = type;
        this.yyline = yyline;
        this.yycolumn = yycolumn;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("type:" + type.toString());
        if (value != null) {
            sb.append(",value:" + value.toString());
        }
        return sb.toString();
    }
    
    @Override
    public LexerType get_type() {
        return this.type;
    }
    
    @Override
    public Object get_value() {
        return this.value;
    }
    
    @Override
    public int get_line() {
        return this.yyline + 1;
    }
    
    @Override
    public int get_column() {
        return this.yycolumn + 1;
    }
    
    @Override
    public int get_insert_cost() {
        return type.get_insert_cost();
    }
    
    @Override
    public int get_delete_cost() {
        return type.get_delete_cost();
    }
    
    @Override
    public ISymbol<LexerType> new_object(String name) {
        return new Symbol(LexerType.valueOf(name), yyline, yycolumn);
    }
}
