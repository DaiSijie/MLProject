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

public class ComplicatedCFLGenerator extends Generator {

    private final List<Symbol> listRepresentation;
    private final Set<Symbol> alphabet;
    
    /**
     * Implements the complicated CFL a^n b^m B^m A^n
     * Since there is a delimiter at the end and the begining of the string, the actual length of the generated sequence is 2(m+n+1)
     */
    public ComplicatedCFLGenerator(Symbol delimiter, Symbol a, Symbol b, Symbol B, Symbol A,  int n, int m){
        listRepresentation = new ArrayList<>();
        
        for(int i = 0; i < 2*n+2*m+2; i++){
            if(i == 0 || i == 2*n+2*m+1)
                listRepresentation.add(delimiter);
            else if(0 < i && i <= n)
                listRepresentation.add(a);
            else if(n < i && i <= n+m)
                listRepresentation.add(b);
            else if(n+m < i && i <= n+2*m)
                listRepresentation.add(B);
            else if(n+2*m < i && i <= 2*n+2*m)
                listRepresentation.add(A);
        }
        
        alphabet = new HashSet<>();
        alphabet.add(a);
        alphabet.add(b);
        alphabet.add(B);
        alphabet.add(A);
    }
    
    
    
    @Override
    public List<Symbol> getListRepresentation() {
        return listRepresentation;
    }



    @Override
    public Set<Symbol> getAlphabet() {
        return Collections.unmodifiableSet(alphabet);
    }
    
    

}
