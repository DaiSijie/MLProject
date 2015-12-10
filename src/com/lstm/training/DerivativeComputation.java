package com.lstm.training;

import com.lstm.datastructures.DerivativeCache;
import com.lstm.datastructures.ForwardPassCache;

import static com.lstm.network.Functions.*;

public class DerivativeComputation {

    private final DerivativeCache derivativeCache;
    private final ForwardPassCache forwardCache;
    
    private final int numMemBlock;
    private final int numSourceUnit;
    
    public DerivativeComputation(DerivativeCache derivativeCache, ForwardPassCache forwardPassCache, int numMemBlock, int numSourceUnit){
        this.derivativeCache = derivativeCache;
        this.forwardCache = forwardPassCache;
        
        this.numMemBlock = numMemBlock;
        this.numSourceUnit = numSourceUnit;
    }
    
    public void init(){
        for (int j = 0; j < numMemBlock; j++) {
            for(int m = 0; m < numSourceUnit; m++){
                derivativeCache.storeCellDerivative(j, m, 0);
                derivativeCache.storeInputGateDerivativeA(j, m, 0);
                derivativeCache.storeForgetGateDerivativeA(j, m, 0);
            }

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
        for(int m = 0; m < numSourceUnit; m++){
            double oldValue = derivativeCache.getCellDerivative(j, m);

            //vars are in the same order as they appear in the formula
            double a = 1; //to fetch from array?
            double b = 1; //to fetch from array?
            double c = 1; //to fetch from array?
            double d = 1; //to fetch from array?

            double newValue = oldValue * a + dg(b) * c * d;

            derivativeCache.storeCellDerivative(j, m, newValue);
        }
    }

    private void updateInputGatesDerivatives(int j) {
        //first step: for sending units
        for(int m = 0; m < numSourceUnit; m++){
            double oldValue = derivativeCache.getInputGateDerivativeA(j, m);

            //vars are in the same order as they appear in the formula
            double a = 1; //to fetch from array?
            double b = 1; //to fetch from array?
            double c = 1; //to fetch from array?
            double d = 1; //to fetch from array?

            double newValue = oldValue * a + g(b) * df(c) * d;

            derivativeCache.storeInputGateDerivativeA(j, m, newValue);
        }

        //second step: for peephole
        int numPeephole = 3;
        for (int vprime = 0; vprime < numPeephole; vprime++) {
            double oldValue = derivativeCache.getInputGateDerivativeB(j, vprime);

            //vars are in the same order as they appear in the formula
            double a = 1; //to fetch from array?
            double b = 1; //to fetch from array?
            double c = 1; //to fetch from array?
            double d = 1; //to fetch from array?

            double newValue = oldValue * a + g(b) * df(c) * d;

            derivativeCache.storeInputGateDerivativeB(j, vprime, newValue);
        }
    }

    private void updateForgetGatesDerivatives(int j) {
        //first step: for sending units
        for(int m = 0; m < numSourceUnit; m++){
            double oldValue = derivativeCache.getForgetGateDerivativeA(j, m);

            //vars are in the same order as they appear in the formula
            double a = 1; //to fetch from array?
            double b = 1; //to fetch from array?
            double c = 1; //to fetch from array?
            double d = 1; //to fetch from array?

            double newValue = oldValue * a + b * df(c) * d;

            derivativeCache.storeForgetGateDerivativeA(j, m, newValue);
        }


        //second step: loop over peephole and update the specifics
        int numPeephole = 3;
        for (int vprime = 0; vprime < numPeephole; vprime++) {
            double oldValue = derivativeCache.getForgetGateDerivativeB(j, vprime);

            //vars are in the same order as they appear in the formula
            double a = 1; //to fetch from array?
            double b = 1; //to fetch from array?
            double c = 1; //to fetch from array?
            double d = 1; //to fetch from array?

            double newValue = oldValue * a + b * df(c) * d;

            derivativeCache.storeForgetGateDerivativeB(j, vprime, newValue);
        }
    }
    
}
