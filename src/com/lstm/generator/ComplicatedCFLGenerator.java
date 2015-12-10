package com.lstm.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ComplicatedCFLGenerator implements Generator {

    
    
    /**
     * Implements the complicated CFL a^n b^m B^m A^n
     * Since there is a delimiter at the end and the begining of the string, the actual length of the generated sequence is 2(m+n+1)
     */
    public ComplicatedCFLGenerator(int limitn, int limitm, int sumLimit){
        //TO implement
        
        
        
//        for(int i = 0; i < 2*n+2*m+2; i++){
//            if(i == 0)
//                listRepresentation.add(start);
//            else if(i == 2*n+2*m+1)
//                listRepresentation.add(end);
//            else if(0 < i && i <= n)
//                listRepresentation.add(a);
//            else if(n < i && i <= n+m)
//                listRepresentation.add(b);
//            else if(n+m < i && i <= n+2*m)
//                listRepresentation.add(B);
//            else if(n+2*m < i && i <= 2*n+2*m)
//                listRepresentation.add(A);
//        }
//        
//        alphabet = new HashSet<>();
//        alphabet.add(a);
//        alphabet.add(b);
//        alphabet.add(B);
//        alphabet.add(A);
    }

    @Override
    public boolean hasNext() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public char getNext() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double[] nextAsVector() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void restart() {
        // TODO Auto-generated method stub
        
    }
    
    
    
    
    

}
