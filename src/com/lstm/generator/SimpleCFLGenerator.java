/*
 *	Author:      Gilbert Maystre
 *	Date:        Nov 17, 2015
 */

package com.lstm.generator;

import java.util.ArrayList;
import java.util.List;

public class SimpleCFLGenerator extends Generator{

    public final List<Symbol> listRepresentation;
    
    
    /**
     * Implements the simple CFL a^n b^n
     * Since there is a delimiter at the end and the begining of the string, the actual length of the generated sequence is 2n+2
     */
    public SimpleCFLGenerator(Symbol delimiter, Symbol a, Symbol b, int n){
        if(n < 0)
            throw new IllegalArgumentException("n has to be >= 0");
        
        listRepresentation = new ArrayList<>();
        for(int i = 0; i < 2*n+2; i++){
            if(i == 0 || i == 2*n+1)
                listRepresentation.add(delimiter);
            else if(0 < i && i <= n)
                listRepresentation.add(a);
            else if(0 < n && i <= 2*n)
                listRepresentation.add(delimiter);
        }
    }
    
    @Override
    public List<Symbol> getListRepresentation() {
        return this.listRepresentation;
    }

}
