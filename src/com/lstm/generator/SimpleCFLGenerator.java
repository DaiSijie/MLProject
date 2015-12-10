package com.lstm.generator;

public class SimpleCFLGenerator implements Generator{
    
    private int stringNo = 0;
    private int indexNo = 0;
    
    private final int limit;
    
    /**
     * Implements the simple CFL a^n b^n for String n = 1 (SabT) to n = limit (Sa...ab...bT)
     * Since there is a delimiter at the end and the begining of the string, the actual length of the generated sequence is 2n+2
     */
    public SimpleCFLGenerator(int limit){
        if(limit < 1)
            throw new IllegalArgumentException("limit has to be >= 1");
        
        this.stringNo = 1;
        this.indexNo = 0;
        
        this.limit = limit;
    }
    
    public boolean hasNext(){
        return stringNo <= limit;
    }
    
    public char getNext(){
        if(!hasNext())
            throw new IllegalStateException("stream is finished");
        
        char toReturn = ' ';
            
        if(indexNo == 0){
            indexNo++;
            toReturn = 'S';
        }
        else if(indexNo == 2 * stringNo + 1){
            indexNo = 0;
            stringNo++; 
            toReturn = 'T';
        }
        else if(0 < indexNo && indexNo <= stringNo){
            indexNo++;
            toReturn = 'a';
        }
        else if(stringNo < indexNo && indexNo <= 2*stringNo){
            indexNo++;
            toReturn = 'b';
        }
        
        return toReturn;
    }
    
    public double[] nextAsVector(){
        char next = getNext();
        
        double[] toReturn = new double[3];
        if(next == 'S' || next == 'T')
            toReturn[0] = 1;
        else if(next == 'a')
            toReturn[1] = 1;
        else if(next == 'b')
            toReturn[2] = 1;
        
        return toReturn;  
    }
    
    public void restart(){
        this.stringNo = 0;
        this.indexNo = 0;
    }
}
