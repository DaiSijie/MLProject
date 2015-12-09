/*
 *	Author:      Gilbert Maystre
 *	Date:        Dec 9, 2015
 */

package com.lstm.training;

import com.lstm.datastructures.DerivativeCache;
import com.lstm.datastructures.ForwardPassCache;

import static com.lstm.network.Functions.*;

public class DerivativeComputation {

    private final DerivativeCache derivativeCache;
    private final ForwardPassCache forwardCache;
    
    private final int numMemBlock;
    
    public DerivativeComputation(DerivativeCache derivativeCache, ForwardPassCache forwardPassCache, int numMemBlock){
        this.derivativeCache = derivativeCache;
        this.forwardCache = forwardPassCache;
        
        this.numMemBlock = numMemBlock;
    }
    
    public void init(){
        for (int j = 0; j < numMemBlock; j++) {
            derivativeCache.storeCellDerivative(j, 0);
            derivativeCache.storeInputGateDerivativeA(j, 0);
            derivativeCache.storeForgetGateDerivativeA(j, 0);

            int numPeephole = 3;
            for (int vprime = 0; vprime < numPeephole; vprime++) {
                derivativeCache.storeInputGateDerivativeB(j, vprime, 0);
                derivativeCache.storeForgetGateDerivativeB(j, vprime, 0);
            }
        }
    }
    
    public void doRound(){
        for (int j = 0; j < numMemBlock; j++) {
            updateCellDerivatives(j);
            updateInputGatesDerivatives(j);
            updateForgetGatesDerivatives(j);
        }
    }
    
    private void updateCellDerivatives(int j) {
        double oldValue = derivativeCache.getCellDerivative(j);

        //vars are in the same order as they appear in the formula
        double a = 1; //to fetch from array?
        double b = 1; //to fetch from array?
        double c = 1; //to fetch from array?
        double d = 1; //to fetch from array?

        double newValue = oldValue * a + dg(b) * c * d;

        derivativeCache.storeCellDerivative(j, newValue);
    }

    private void updateInputGatesDerivatives(int j) {
        //first step: update the general
        double oldValue = derivativeCache.getInputGateDerivativeA(j);

        //vars are in the same order as they appear in the formula
        double a = 1; //to fetch from array?
        double b = 1; //to fetch from array?
        double c = 1; //to fetch from array?
        double d = 1; //to fetch from array?

        double newValue = oldValue * a + g(b) * dg(c) * d;

        derivativeCache.storeInputGateDerivativeA(j, newValue);

        //second step: loop over peephole and update the specifics
        int numPeephole = 3;
        for (int vprime = 0; vprime < numPeephole; vprime++) {
            oldValue = derivativeCache.getInputGateDerivativeB(j, vprime);

            //vars are in the same order as they appear in the formula
            a = 1; //to fetch from array?
            b = 1; //to fetch from array?
            c = 1; //to fetch from array?
            d = 1; //to fetch from array?

            newValue = oldValue * a + g(b) * dg(c) * d;

            derivativeCache.storeInputGateDerivativeB(j, vprime, newValue);
        }
    }

    private void updateForgetGatesDerivatives(int j) {
        //first step: update the general
        double oldValue = derivativeCache.getForgetGateDerivativeA(j);

        //vars are in the same order as they appear in the formula
        double a = 1; //to fetch from array?
        double b = 1; //to fetch from array?
        double c = 1; //to fetch from array?
        double d = 1; //to fetch from array?

        double newValue = oldValue * a + b * dg(c) * d;

        derivativeCache.storeForgetGateDerivativeA(j, newValue);

        //second step: loop over peephole and update the specifics
        int numPeephole = 3;
        for (int vprime = 0; vprime < numPeephole; vprime++) {
            oldValue = derivativeCache.getForgetGateDerivativeB(j, vprime);

            //vars are in the same order as they appear in the formula
            a = 1; //to fetch from array?
            b = 1; //to fetch from array?
            c = 1; //to fetch from array?
            d = 1; //to fetch from array?

            newValue = oldValue * a + b * dg(c) * d;

            derivativeCache.storeForgetGateDerivativeB(j, vprime, newValue);
        }
    }
    
}
