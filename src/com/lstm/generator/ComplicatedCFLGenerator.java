/*
 *	Author:      Gilbert Maystre
 *	Date:        Nov 17, 2015
 */

package com.lstm.generator;

import java.util.ArrayList;
import java.util.List;

public class ComplicatedCFLGenerator extends Generator {

    private final List<Symbol> listRepresentation;
    
    public ComplicatedCFLGenerator(Symbol delimiter, Symbol a, Symbol A, Symbol B, Symbol b, int n, int m){
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
    }
    
    
    
    @Override
    public List<Symbol> getListRepresentation() {
        return listRepresentation;
    }
    
    

}
