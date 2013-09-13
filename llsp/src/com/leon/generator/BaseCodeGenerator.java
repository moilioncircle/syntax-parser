
package com.leon.generator;

import java.util.List;

import com.leon.util.ISymbol;

/**
 * @author : Leon
 * @since : 2013-9-13
 * @see :
 */

public interface BaseCodeGenerator {
    
    public Object generate(List<ISymbol> t) throws Exception;
}
