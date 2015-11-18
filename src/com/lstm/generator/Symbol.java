/*
 *	Author:      Gilbert Maystre
 *	Date:        Nov 16, 2015
 */

package com.lstm.generator;

public class Symbol {

    private final String name;
    
    public Symbol(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
    public boolean equals(Object other){
        if(!(other instanceof Symbol))
            return false;
        
        return name.equals(((Symbol)other).name);
    }
    
    public int hashCode(){
        return name.hashCode();
    }
    
}
