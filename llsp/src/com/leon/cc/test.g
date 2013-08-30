%name : [EOF];
%name : [MARK];
%name : [SEMI];
%name : [NAME];
%name : [COLON];
%name : [LEFT];
%name : [RIGHT];
%name : [COMMA];
%name : [LBRACKET];
%name : [TOKEN];
%name : [RBRACKET];
%name : [OR];
%left [LBRACKET],[COLON];
%usercode{
    public String a = "bbbb";
    public void setsth{
    
    }
%usercode}
%%
program : Descriptor EOF;
Descriptor : Declarations SectionMarker Productions SectionMarker | SectionMarker Productions SectionMarker;
SectionMarker : MARK;
Declarations : Declarations Declaration | Declaration;
Declaration : SEMI | Precedence Tokens | NAME COLON Token;
Precedence : LEFT | RIGHT;
Tokens : Tokens COMMA Token | Token;
Token : LBRACKET TOKEN RBRACKET;
Productions : Productions TOKEN COLON Rules SEMI | TOKEN COLON Rules SEMI;
Rules : Rules OR GrammarRule | GrammarRule{};
GrammarRule : Rule | ;
Rule : Rule TOKEN | TOKEN;


