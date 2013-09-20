package com.leon.simple.json;

import com.leon.cc.CCSymbol;
import com.leon.util.ISymbol;
import com.leon.util.IToken;
%%
%public
%class JsonToken
%unicode
%implements IToken
%function next_token
%type ISymbol
%{
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
WhiteSpace = {LineTerminator} | [ \t\f]
OctDigit          = [0-7]
/* floating point literals */        
Number = ({FLit1}|{FLit2}|{FLit3}) {Exponent}?

FLit1    = [0-9]+ \. [0-9]* 
FLit2    = \. [0-9]+ 
FLit3    = [0-9]+ 
Exponent = [eE] [+-]? [0-9]+

/* string */
StringCharacter = [^\r\n\"\\]

%state STRING

%%

<YYINITIAL> {

  /* keywords */
  "{"                            { return symbol("OBJECTBEGIN",yytext()); }
  "}"                            { return symbol("OBJECTEND",yytext()); }
  ","                            { return symbol("COMMA",yytext()); }
  ":"                            { return symbol("COLON",yytext()); }
  "["                            { return symbol("ARRAYBEGIN",yytext()); }
  "]"                            { return symbol("ARRAYEND",yytext()); }
  "true"                         { return symbol("TRUE",yytext()); }
  "false"                        { return symbol("FALSE",yytext()); }
  "null"                         { return symbol("NULL",yytext()); }

  /* string literal */
  \"                             { yybegin(STRING); string.setLength(0); }

  /* numeric literals */
  {Number}                       { return symbol("NUMBER", new Double(yytext())); }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }
}

<STRING> {
  \"                             { yybegin(YYINITIAL); return symbol("STRING", string.toString()); }

  {StringCharacter}+             { string.append( yytext() ); }

  /* escape sequences */
  "\\b"                          { string.append( '\b' ); }
  "\\t"                          { string.append( '\t' ); }
  "\\n"                          { string.append( '\n' ); }
  "\\f"                          { string.append( '\f' ); }
  "\\r"                          { string.append( '\r' ); }
  "\\\""                         { string.append( '\"' ); }
  "\\'"                          { string.append( '\'' ); }
  "\\\\"                         { string.append( '\\' ); }
  \\[0-3]?{OctDigit}?{OctDigit}  { char val = (char) Integer.parseInt(yytext().substring(1),8);
                        				   string.append( val ); }

  /* error cases */
  \\.                            { throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); }
  {LineTerminator}               { throw new RuntimeException("Unterminated string at end of line"); }
}

/* error fallback */
.|\n                             { throw new RuntimeException("Illegal character \""+yytext()+
                                                              "\" at line "+yyline+", column "+yycolumn); }