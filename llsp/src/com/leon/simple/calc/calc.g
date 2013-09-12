#
package com.leon.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.leon.grammar.Assoc;
import com.leon.grammar.Associativity;
import com.leon.grammar.Grammar;
import com.leon.grammar.Prec;
import com.leon.grammar.ProductionRightHand;
import com.leon.grammar.ProductionSet;
import com.leon.grammar.Terminal;
import com.leon.lr.LR1;
import com.leon.util.ISymbol;

/**
 * @author : Leon
 * @since : 2013-9-11
 * @see :
 */

public class CaleGenerator implements BaseCodeGenerator{

    public CaleGenerator(){
    }
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