/*
 *	Author:      Gilbert Maystre
 *	Date:        Nov 16, 2015
 */

package com.lstm.generator;

public class Symbol {

    public static final Symbol START = new Symbol("S");
    public static final Symbol END = new Symbol("T");
    
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
