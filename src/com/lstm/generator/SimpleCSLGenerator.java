/*
 *	Author:      Gilbert Maystre
 *	Date:        Nov 16, 2015
 */

package com.lstm.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleCSLGenerator extends Generator {
    
    private final List<Symbol> listRepresentation;
    private final Set<Symbol> alphabet;
    
    /**
     * Implements the simple CSL a^n b^n c^n
     * Since there is a delimiter at the end and the begining of the string, the actual length of the generated sequence is 3n+2
     */
    public SimpleCSLGenerator(Symbol start, Symbol a, Symbol b, Symbol c, Symbol end, int n){
        if(n < 0)
            throw new IllegalArgumentException("n has to be >= 0");
                
        listRepresentation = new ArrayList<>();
        for(int i = 0; i < 3*n+2; i++){
            if(i == 0)
                listRepresentation.add(start);
            else if(i == 3*n+1)
                listRepresentation.add(end);
            else if(0 < i && i <= n)
                listRepresentation.add(a);
            else if(n < i && i <= 2*n)
                listRepresentation.add(b);
            else if(2*n < i && i <= 3*n)
                listRepresentation.add(c);
        }
        
        alphabet = new HashSet<>();
        alphabet.add(a);
        alphabet.add(b);
        alphabet.add(c);
    }
    
    @Override
    public List<Symbol> getListRepresentation() {
        return Collections.unmodifiableList(listRepresentation);
    }

    @Override
    public Set<Symbol> getAlphabet() {
        return Collections.unmodifiableSet(alphabet);
    }

}
