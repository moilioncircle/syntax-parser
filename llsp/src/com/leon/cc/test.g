%name : EOF;
%start : program;
%left PLUS,MINUS;
%left TIMES,DIVIDE;
%right UMINUS;
#
    public String a = "#";
    public void setsth{
    
    } 
#
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