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

    public void generate(List<ISymbol> t){
        new E(new E(new T(new P(t.get(0)))),new T(new T(new P(t.get(2))),new P(t.get(4))));
    }

}