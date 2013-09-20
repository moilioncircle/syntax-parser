package com.leon.simple.calc;

import com.leon.cc.CCSymbol;
import com.leon.util.ISymbol;
import com.leon.util.IToken;
%%
%public
%class Token
%unicode
%implements IToken
%function next_token
%type ISymbol
%{
    private ISymbol symbol(String type) {
        return new CCSymbol(type, yyline, yycolumn);
    }
    
    private ISymbol symbol(String type, Object value) {
        return new CCSymbol(type, yyline, yycolumn, value);
    }
    
    @Override
    public boolean has_next() {
        return !zzAtEOF;
    }
%}
%eofval{ 
    return symbol("EOF");
%eofval} 
%eofclose 
%line
%column
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator}|[ \t\f]
NUM = 0|[1-9][0-9]*
ID = [A-Za-z_][A-Za-z0-9_]*
%%
<YYINITIAL> {
    "+"                { return symbol("PLUS"); }
    "-"                { return symbol("MINUS"); }
    "*"                { return symbol("TIMES"); }
    "/"                { return symbol("DIVIDE"); }
    "("                { return symbol("LPAREN"); }
    ")"                { return symbol("RPAREN"); }
    "begin"            { return symbol("BEGIN"); }
    "end"              { return symbol("END"); }
    ":="               { return symbol("ASSIGN"); }
    ","                { return symbol("COMMA"); }
    ";"                { return symbol("SEMI"); }
    "read"             { return symbol("READ"); }
    "write"            { return symbol("WRITE"); }
    {NUM}              { return symbol("NUM",new Integer(yytext())); }
    {ID}               { return symbol("ID",yytext()); }
    {WhiteSpace}       {/* SKIP */}
}