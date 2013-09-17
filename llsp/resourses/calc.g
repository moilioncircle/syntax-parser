#

import java.util.List;

import com.leon.generator.BaseCodeGenerator;
import com.leon.simple.calc.S;
import com.leon.simple.calc.E;
import com.leon.util.*;

/**
 * @author : Leon
 * @since : 2013-9-12
 * @see :
 */

public class CalcGenerator implements BaseCodeGenerator{
#
%name : EOF 20 20;
%name : PLUS 3 3;
%name : MINUS 4 4;
%name : TIMES 5 5;
%name : DIVIDE 6 6;
%name : NUM 8 8;
%name : LPAREN 9 9;
%name : RPAREN 7 7;
%left PLUS,MINUS;
%left TIMES,DIVIDE;
%right UMINUS;
%start : S;
%class CalcGenerator;
%%
S               : E EOF #new S($0)#
                ;
E               : E PLUS E #new E($0,$1,$2)#
                | E MINUS E #new E($0,$1,$2)#
                | E TIMES E #new E($0,$1,$2)#
                | E DIVIDE E #new E($0,$1,$2)#
                | LPAREN E RPAREN #new E($1)#
                | NUM #new E($0)#
                | MINUS NUM %prec UMINUS #new E($0,$1)#
                ;
%%
#
}
#