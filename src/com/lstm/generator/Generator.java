/*
 *	Author:      Gilbert Maystre
 *	Date:        Dec 10, 2015
 */

package com.lstm.generator;

public interface Generator {

    public boolean hasNext();
    
    public char getNext();
    
    public double[] nextAsVector();
    
    public void restart();   
}
