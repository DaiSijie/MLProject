/*
 *	Author:      Gilbert Maystre
 *	Date:        Nov 16, 2015
 */

package com.lstm.generator;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class Generator implements Iterable<Symbol>{
    
    public abstract List<Symbol> getListRepresentation();
    
    public Iterator<Symbol> iterator(){
        return getListRepresentation().iterator();
    }
    
    public Symbol getSymbolAt(int i){
        return getListRepresentation().get(i);
    }
    
    public abstract Set<Symbol> getAlphabet();
    
}
