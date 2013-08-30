
package com.leon.cc;

import com.leon.util.ISymbol;

/**
 * @author : Leon
 * @since : 2013-8-30
 * @see :
 */

public class CCSymbol implements ISymbol<CCType> {
    
    public int    yyline;
    public int    yycolumn;
    public CCType type;
    public Object value;
    
    public CCSymbol(CCType type, int yyline, int yycolumn, Object value) {
        this(type, yyline, yycolumn);
        this.value = value;
    }
    
    public CCSymbol(CCType type, int yyline, int yycolumn) {
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
    public CCType get_type() {
        return this.type;
    }
    
    @Override
    public Object get_value() {
        return this.value;
    }
    
    @Override
    public int get_line() {
        return this.yyline+1;
    }
    
    @Override
    public int get_column() {
        return this.yycolumn+1;
    }
}
