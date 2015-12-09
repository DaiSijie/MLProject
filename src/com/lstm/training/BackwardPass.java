/*
 *	Author:      Gilbert Maystre
 *	Date:        Dec 9, 2015
 */

package com.lstm.training;

import com.lstm.datastructures.DerivativeCache;
import com.lstm.datastructures.ForwardPassCache;

public class BackwardPass {

    private final DerivativeCache derivativeCache;
    private final ForwardPassCache forwardCache;
    
    public BackwardPass(DerivativeCache derivativeCache, ForwardPassCache forwardPassCache){
        this.derivativeCache = derivativeCache;
        this.forwardCache = forwardPassCache;
    }
        
    public void doRound(double[] targetY){
        
    }
    
    public void init(){
        
    }
    
    private void backwardpass(double[] targetY){
        double[] deltas = new double[numOutput];
        
        for(int i = 0; i < numOutput; i++){
            double netk = 1; //fill
            deltas[i] = dg(netk) * (targetY[i] - forwardCache.getOutputNodeOutput(i));
        }  
    }
    
    private double[] computeDeltasOut(int j, double[] deltas){
        if(deltas.length != numOutput)
            throw new IllegalArgumentException("bad size for deltas");
        
        double[] toReturn = new double[numMemBlock];
        
        for(int jj = 0; j < toReturn.length; j++){
            double sum = 0;
        }
        
        
        
        return null;
    }

}
