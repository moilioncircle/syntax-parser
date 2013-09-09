package com.leon.cc;

import com.leon.cc.CCSymbol;
import com.leon.util.ISymbol;
import com.leon.util.IToken;
%%
%public
%class CCToken
%unicode
%implements IToken
%function next_token
%type ISymbol
%{
    StringBuilder action = new StringBuilder();
    StringBuilder string = new StringBuilder();
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
TOKEN = [A-Za-z_][A-Za-z0-9_]*
StringCharacter = [^\r\n\"\\]
SingleCharacter = [^\r\n\'\\]
OctDigit = [0-7]
%state ACTION,STRING,CHARLITERAL
%%
<YYINITIAL> {
    "%%"                            { return symbol("MARK",yytext()); }
    ";"                             { return symbol("SEMI",yytext()); }
    "%name"                         { return symbol("NAME",yytext()); }
    ":"                             { return symbol("COLON",yytext()); }
    "%left"                         { return symbol("LEFT",yytext()); }
    "%right"                        { return symbol("RIGHT",yytext()); }
    "%nonassoc"                     { return symbol("NONASSOC",yytext()); }
    "%binary"                       { return symbol("BINARY",yytext()); }
    "%prec"                         { return symbol("PREC",yytext()); }
    "%start"                        { return symbol("START",yytext()); }
    ","                             { return symbol("COMMA",yytext()); }
    "="                             { return symbol("ASSIGN",yytext()); }
    "["                             { return symbol("LBRACKET",yytext()); }
    "]"                             { return symbol("RBRACKET",yytext()); }
    "|"                             { return symbol("OR",yytext()); }
    {NUM}                           { return symbol("NUM",new Integer(yytext())); }
    {TOKEN}                         { return symbol("TOKEN",yytext()); }
    {WhiteSpace}                    {/* SKIP */}
    "#"                             { action.setLength(0);yybegin(ACTION);}
    /* error cases */
    \\.                             { throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); }
}
<ACTION> {
    "#"                             { yybegin(YYINITIAL);return symbol("ACTION",action.toString());}
    [^\n\r\"\'\t\\\#]*              { action.append(yytext());}
    \"                              { string.setLength(0); yybegin(STRING);string.append("\""); }
    \'                              { yybegin(CHARLITERAL); }
    \\t                             { action.append('\t'); }
    \\n                             { action.append('\n'); }
    \\r                             { action.append('\r'); }
    \\\"                            { action.append('\"'); }
    \\                              { action.append('\\'); }
    {WhiteSpace}                    { action.append(yytext());}
    /* error cases */
    \\.                             { throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); }
}

<STRING> {
    \"                              { yybegin(ACTION); string.append("\""); action.append(string.toString()); }
  
    {StringCharacter}+              { string.append( yytext() ); }
  
    /* escape sequences */
    "\\b"                           { string.append('\b'); }
    "\\t"                           { string.append('\t'); }
    "\\n"                           { string.append('\n'); }
    "\\f"                           { string.append('\f'); }
    "\\r"                           { string.append('\r'); }
    "\\\""                          { string.append('\"'); }
    "\\'"                           { string.append('\''); }
    "\\\\"                          { string.append('\\'); }
    \\[0-3]?{OctDigit}?{OctDigit}   { char val = (char) Integer.parseInt(yytext().substring(1),8);
                                           string.append( val ); }
  
    /* error cases */
    \\.                             { throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); }
    {LineTerminator}                { throw new RuntimeException("Unterminated string at end of line"); }
}

<CHARLITERAL> {
    {SingleCharacter}\'             { yybegin(ACTION); action.append("'"+new Character(yytext().charAt(0))+"'"); }
  
    /* escape sequences */
    "\\b"\'                         { yybegin(ACTION); action.append("'"+new Character('\b')+"'");}
    "\\t"\'                         { yybegin(ACTION); action.append("'"+new Character('\t')+"'");}
    "\\n"\'                         { yybegin(ACTION); action.append("'"+new Character('\n')+"'");}
    "\\f"\'                         { yybegin(ACTION); action.append("'"+new Character('\f')+"'");}
    "\\r"\'                         { yybegin(ACTION); action.append("'"+new Character('\r')+"'");}
    "\\\""\'                        { yybegin(ACTION); action.append("'"+new Character('\"')+"'");}
    "\\'"\'                         { yybegin(ACTION); action.append("'"+new Character('\'')+"'");}
    "\\\\"\'                        { yybegin(ACTION); action.append("'"+new Character('\\')+"'");}
    \\[0-3]?{OctDigit}?{OctDigit}\' { yybegin(ACTION); int val = Integer.parseInt(yytext().substring(1,yylength()-1),8);
                                        action.append("'"+new Character((char)val)+"'"); }
  
    /* error cases */
    \\.                             { throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); }
    {LineTerminator}                { throw new RuntimeException("Unterminated character literal at end of line"); }
}
