#
package com.leon.generator;

import java.util.List;

import com.leon.simple.calc.E;
import com.leon.simple.calc.P;
import com.leon.simple.calc.T;
import com.leon.util.*;

/**
 * @author : Leon
 * @since : 2013-9-12
 * @see :
 */

public class CalcGenerator {
#
%name : EOF 20 20;
%name : PLUS 3 3;
%name : TIMES 5 5;
%name : NUM 8 8;
%name : LPAREN 9 9;
%name : RPAREN 7 7;
%start : S;
%%
S               : E EOF #new S($0)#
                ;
E               : E PLUS T #new E($0,$2)#
                | T #new E($0)#
                ;
T               : T TIMES P #new T($0,$2)#
                | P #new T($0)#
                ;
P               : NUM #new P($0)#
                | LPAREN E RPAREN #new P($1)#
                ;
%%
#
}
#