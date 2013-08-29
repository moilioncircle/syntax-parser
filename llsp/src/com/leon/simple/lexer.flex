package com.leon.simple;

import com.leon.simple.LexerType;
import com.leon.simple.Symbol;
import com.leon.util.ISymbol;
import com.leon.util.IToken;
%%
%public
%class Token
%unicode
%implements IToken<LexerType>
%function next_token
%type ISymbol<LexerType>
%{
    private ISymbol<LexerType> symbol(LexerType type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    private ISymbol<LexerType> symbol(LexerType type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}
%eofval{ 
    return symbol(LexerType.EOF);
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
    "+"                { return symbol(LexerType.PLUS); }
    "-"                { return symbol(LexerType.MINUS); }
    "*"                { return symbol(LexerType.TIMES); }
    "/"                { return symbol(LexerType.DIVIDE); }
    "("                { return symbol(LexerType.LPAREN); }
    ")"                { return symbol(LexerType.RPAREN); }
    "begin"                { return symbol(LexerType.BEGIN); }
    "end"                { return symbol(LexerType.END); }
    ":="                { return symbol(LexerType.ASSIGN); }
    ","                { return symbol(LexerType.COMMA); }
    ";"                { return symbol(LexerType.SEMI); }
    "read"                { return symbol(LexerType.READ); }
    "write"                { return symbol(LexerType.WRITE); }
    {NUM}              { return symbol(LexerType.NUM,new Integer(yytext())); }
    {ID}               { return symbol(LexerType.ID,yytext()); }
    {WhiteSpace}       {/* SKIP */}
}