#
    public String a = "#";
    public void setsth{
    
    } 
#
%name : EOF 1 2;
%start : program;
%left START,LEFT;
%left PREC,RIGHT;
%right COLON;
%%
program : Descriptor EOF #public String b="#"#
        | #public void setsth(){}#
        ;
program : Descriptor EOF %prec UMINUS ##
        | Descriptor EOF ##
        | ##
        |
        ;
%%
#
#