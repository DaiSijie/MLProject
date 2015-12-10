package com.lstm.generator;

public class SimpleCSLGenerator implements Generator {
    
    private final int limit;
    
    private int stringNo;
    private int indexNo;
    
    /**
     * Implements the simple CSL a^n b^n c^n for n = 1 (SabcT) to n = limit (Sa...ab...bc...cT)
     */
    public SimpleCSLGenerator(int limit){
        if(limit < 1)
            throw new IllegalArgumentException("limit has to be >= 1");
        
        this.limit = limit;
        
        this.stringNo = 1;
        this.indexNo = 0;        
    }

    @Override
    public boolean hasNext() {
        return stringNo <= limit;
    }

    @Override
    public char getNext() {
        if(!hasNext())
            throw new IllegalStateException("Stream empty");
        
        char toReturn = ' ';
        
        if(indexNo == 0){
            indexNo++;
            toReturn = 'S';
        }
        else if(indexNo == 3*stringNo + 1){
            indexNo = 0;
            stringNo++;
            toReturn = 'T';
        }
        else if(0 < indexNo && indexNo <= stringNo){
            indexNo++;
            toReturn = 'a';
        }
        else if(stringNo <= indexNo && indexNo <= 2*stringNo){
            indexNo++;
            toReturn = 'b';
        }
        else if(2*stringNo < indexNo && indexNo <= 3*stringNo){
            indexNo++;
            toReturn = 'c';
        }
        
        return toReturn;
    }

    @Override
    public double[] nextAsVector() {
        char next = getNext();
        
        double[] toReturn = new double[4];
        
        if(next == 'S' || next == 'T')
            toReturn[0] = 1;
        else if(next == 'a')
            toReturn[1] = 1;
        else if(next == 'b')
            toReturn[2] = 1;
        else if(next == 'c')
            toReturn[3] = 1;
        
        return toReturn;
    }

    @Override
    public void restart() {
        this.stringNo = 0;
        this.indexNo = 0;
    }
    
}
