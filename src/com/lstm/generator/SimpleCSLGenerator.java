/*
 *	Author:      Gilbert Maystre
 *	Date:        Nov 16, 2015
 */

package com.lstm.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleCSLGenerator extends Generator {
    
    private final List<Symbol> listRepresentation;
    
    /**
     * Implements the simple CSL a^n b^n c^n
     * Since there is a delimiter at the end and the begining of the string, the actual length of the generated sequence is 3n+2
     */
    public SimpleCSLGenerator(Symbol delimiter, Symbol a, Symbol b, Symbol c, int n){
        if(n < 0)
            throw new IllegalArgumentException("n has to be >= 0");
                
        listRepresentation = new ArrayList<>();
        for(int i = 0; i < 3*n+2; i++){
            if(i == 0 || i == 3*n+1)
                listRepresentation.add(delimiter);
            else if(0 < i && i <= n)
                listRepresentation.add(a);
            else if(n < i && i <= 2*n)
                listRepresentation.add(b);
            else if(2*n < i && i <= 3*n)
                listRepresentation.add(c);
        }

    }
    
    @Override
    public List<Symbol> getListRepresentation() {
        return Collections.unmodifiableList(listRepresentation);
    }

}
