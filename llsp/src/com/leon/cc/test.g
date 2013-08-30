#
    public String a = '#';
    public void setsth{
    
    } 
#
%name : EOF;
%left PLUS,MINUS;
#
    public String a = '#';
    public void setsth{
    
    } 
#
%%
program : Descriptor EOF #public String b="#"#
        | #public void setsth(){}#
        ;
program : Descriptor EOF ##
        | #public void setsth(){}#
        ;
%%
#
    public String a = '#';
    public void setsth{
    
    }
#