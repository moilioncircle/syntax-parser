
package com.leon.cc;

import com.leon.util.ISymbol;

/**
 * @author : Leon
 * @since : 2013-8-30
 * @see :
 */

public class CCSymbol implements ISymbol {
    
    public int    yyline;
    public int    yycolumn;
    public String type;
    public Object value;
    
    public CCSymbol(String type, int yyline, int yycolumn, Object value) {
        this(type, yyline, yycolumn);
        this.value = value;
    }
    
    public CCSymbol(String type, int yyline, int yycolumn) {
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
    public String get_type_name() {
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
}
