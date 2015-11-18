/*
 *	Author:      Gilbert Maystre
 *	Date:        Nov 16, 2015
 */

package com.lstm.generator;

import java.util.Iterator;
import java.util.List;

public abstract class Generator implements Iterable<Symbol>{
    
    public Iterator<Symbol> iterator(){
        return getListRepresentation().iterator();
    }
    
    public abstract List<Symbol> getListRepresentation();
    
    
    public Symbol getSymbolAt(int i){
        return getListRepresentation().get(i);
    }
}
