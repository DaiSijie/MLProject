/*
 *	Author:      Gilbert Maystre
 *	Date:        Nov 17, 2015
 */

package com.lstm.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleCFLGenerator extends Generator{

    public final List<Symbol> listRepresentation;
    public final Set<Symbol> alphabet;
    
    /**
     * Implements the simple CFL a^n b^n
     * Since there is a delimiter at the end and the begining of the string, the actual length of the generated sequence is 2n+2
     */
    public SimpleCFLGenerator(Symbol start, Symbol a, Symbol b, Symbol end, int n){
        if(n < 0)
            throw new IllegalArgumentException("n has to be >= 0");
        
        this.alphabet = new HashSet<>();
        alphabet.add(a);
        alphabet.add(b);
        
        listRepresentation = new ArrayList<>();
        for(int i = 0; i < 2*n+2; i++){
            if(i == 0)
                listRepresentation.add(start);
            else if(i == 2*n+1)
                listRepresentation.add(end);
            else if(0 < i && i <= n)
                listRepresentation.add(a);
            else if(0 < n && i <= 2*n)
                listRepresentation.add(b);
        }
    }
    
    @Override
    public List<Symbol> getListRepresentation() {
        return this.listRepresentation;
    }

    @Override
    public Set<Symbol> getAlphabet() {
        return Collections.unmodifiableSet(alphabet);
    }

}
