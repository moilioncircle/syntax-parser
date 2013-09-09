
package com.leon.util;

import java.io.IOException;

/**
 * @author : Leon
 * @since : 2013-8-28
 * @see :
 */

public interface IToken {
    
    public ISymbol next_token() throws IOException;
    
    public boolean has_next();
}
