package com.leon.cc;

import com.leon.cc.CCType;
import com.leon.cc.CCSymbol;
import com.leon.util.ISymbol;
import com.leon.util.IToken;
%%
%public
%class CCToken
%unicode
%implements IToken<CCType>
%function next_token
%type ISymbol<CCType>
%{
    StringBuilder action = new StringBuilder();
    StringBuilder usercode = new StringBuilder();
    private ISymbol<CCType> symbol(CCType type) {
        return new CCSymbol(type, yyline, yycolumn);
    }
    
    private ISymbol<CCType> symbol(CCType type, Object value) {
        return new CCSymbol(type, yyline, yycolumn, value);
    }
%}
%eofval{ 
    return symbol(CCType.EOF);
%eofval} 
%eofclose 
%line
%column
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator}|[ \t\f]
NUM = 0|[1-9][0-9]*
TOKEN = [A-Za-z_][A-Za-z0-9_]*
%state ACTION,USERCODE
%%
<YYINITIAL> {
    "%%"                         { return symbol(CCType.MARK); }
    ";"                          { return symbol(CCType.SEMI); }
    "%name"                      { return symbol(CCType.NAME); }
    ":"                          { return symbol(CCType.COLON); }
    "%left"                      { return symbol(CCType.LEFT); }
    "%right"                     { return symbol(CCType.RIGHT); }
    ","                          { return symbol(CCType.COMMA); }
    "["                          { return symbol(CCType.LBRACKET); }
    "]"                          { return symbol(CCType.RBRACKET); }
    "|"                          { return symbol(CCType.OR); }
    {NUM}                        { return symbol(CCType.NUM,new Integer(yytext())); }
    {TOKEN}                      { return symbol(CCType.TOKEN,yytext()); }
    {WhiteSpace}                 {/* SKIP */}
    "%{"                         { action.setLength(0);yybegin(ACTION);System.out.println("%{");}
    "%usercode{"                 { usercode.setLength(0);yybegin(USERCODE);System.out.println("%usercode{");}
}
<ACTION> {
    "%}"                         { yybegin(YYINITIAL);System.out.println("%}");return symbol(CCType.ACTION,action.toString());}
    (?!(%\}))+                    { action.append( yytext() );}
    \\t                          { action.append('\t'); }
    \\n                          { action.append('\n'); }
    \\r                          { action.append('\r'); }
    \\\"                         { action.append('\"'); }
    \\                           { action.append('\\'); }
}
<USERCODE> {
    "%usercode}"                 { yybegin(YYINITIAL);System.out.println("%usercode}");return symbol(CCType.USERCODE,usercode.toString());}
    (?!%usercode\})+            { usercode.append( yytext() );}
    \\t                          { usercode.append('\t'); }
    \\n                          { usercode.append('\n'); }
    \\r                          { usercode.append('\r'); }
    \\\"                         { usercode.append('\"'); }
    \\                           { usercode.append('\\'); }
}