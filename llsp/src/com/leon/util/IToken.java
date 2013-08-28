
package com.leon.util;

import java.io.IOException;

/**
 * @author : Leon
 * @since : 2013-8-28
 * @see :
 */

public interface IToken<T> {
    
    public ISymbol<T> next_token() throws IOException;
}
