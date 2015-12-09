package com.lstm.training;

import com.lstm.datastructures.BackwardPassCache;
import com.lstm.datastructures.DerivativeCache;
import com.lstm.datastructures.ForwardPassCache;

import static com.lstm.network.Functions.*;

public class BackwardPass {

    private final DerivativeCache derivativeCache;
    private final ForwardPassCache forwardCache;
    private final BackwardPassCache backwardCache;

    private final int numInput;
    private final int numMemBlock;
    private final double alpha;

    public BackwardPass(DerivativeCache derivativeCache, ForwardPassCache forwardPassCache, BackwardPassCache backwardPassCache, int numInput, int numMemBlock, double alpha){
        this.derivativeCache = derivativeCache;
        this.forwardCache = forwardPassCache;
        this.backwardCache = backwardPassCache;

        this.numInput = numInput;
        this.numMemBlock = numMemBlock;
        this.alpha = alpha;
    }

    public void doRound(){
        computeDeltas();
        updateWeightsDeltas();
    }

    public void init(){

    }

    private void computeDeltas(){
        //Step 1: compute the deltas of the output units.
        for(int k = 0; k < numInput; k++){
            //compute the injection error
            double err = forwardCache.getInputNodeOutput(k) - forwardCache.getOutputNodeOutput(k);
            double netk = 1; //fetch from array?
            double deltak = df(netk) * err;

            backwardCache.storeDelta(k, deltak);
        }

        //Step 2: compute the deltas for output gates and internal state error
        for(int j = 0; j < numMemBlock; j++){
            //Step 2.a: deltas for output gates
            double sum = 0;
            for(int k = 0; k < numInput; k++){
                double wkc = 1;//fetch from array?
                sum += backwardCache.getDelta(k) * wkc;
            }

            double netOutj = 1; //fetch from array?
            double scjv = 1; //fetch from array?

            double deltaOutj = df(netOutj) * scjv * sum;
            backwardCache.storeDeltaOut(j, deltaOutj);

            //Step 2.b: internal state error NB: v always 1
            double youtj = 1; //fetch from array?
            double escjv = youtj * sum;
            backwardCache.storeInternalStateError(j, escjv);

        }
    }

    private void updateWeightsDeltas(){
        //compute general output units weights 
        int what = 10; //fetch from array?
        for(int m = 0; m < what; m++){
            for(int k = 0; k < numInput; k++){
                double ym = 1; //fetch from array?
                double deltak = backwardCache.getDelta(k);

                double wkm = alpha * ym * deltak;
                backwardCache.storeOutputUnit(k, m, wkm);
            }
        }

        for(int j = 0; j < numMemBlock; j++){
            updateOutputGatesWeightsDeltas(j);
            updateInputGatesWeightDeltas(j);
            updateForgetGatesWeightDeltas(j);
            updateCellsWeightDeltas(j);
        }

    }

    private void updateOutputGatesWeightsDeltas(int j){
        double deltaout = backwardCache.getDeltaOut(j);

        int what = 10; //fetch from array?
        for(int m = 0; m < what; m++){
            double ym = 1; //fetch from array?
            double woutm = alpha * deltaout * ym;

            backwardCache.storeOutputGate(j, m, woutm);
        }

        double scjv = 1; //fetch from array?
        double woutcjv = alpha * deltaout * scjv;

        backwardCache.storeOutputGateC(j, woutcjv);
    }

    private void updateInputGatesWeightDeltas(int j){
        double internalStateError = backwardCache.getInternalStateError(j);

        int what = 10; //fetch from array?
        for(int m = 0; m < what; m++){
            double derivative = derivativeCache.getInputGateDerivativeA(m);
            double deltawinm = alpha * derivative * internalStateError;

            backwardCache.storeInputGate(j, m, deltawinm);
        }
        
        int numPeepholes = 3; //check
        for(int vprime = 0; vprime < numPeepholes; vprime++){
            double derivative = derivativeCache.getInputGateDerivativeB(j, vprime);
            double wincjvprime = alpha * internalStateError * derivative;
            
            backwardCache.storeInputGateC(j, vprime, wincjvprime);
        }
    }

    private void updateForgetGatesWeightDeltas(int j){
        double internalStateError = backwardCache.getInternalStateError(j);

        int what = 10; //fetch from array?
        for(int m = 0; m < what; m++){
            double derivative = derivativeCache.getForgetGateDerivativeA(m);
            double deltawinm = alpha * derivative * internalStateError;

            backwardCache.storeForgetGate(j, m, deltawinm);
        }
        
        int numPeepholes = 3; //check
        for(int vprime = 0; vprime < numPeepholes; vprime++){
            double derivative = derivativeCache.getForgetGateDerivativeB(j, vprime);
            double wincjvprime = alpha * internalStateError * derivative;
            
            backwardCache.storeForgetGateC(j, vprime, wincjvprime);
        }
    }

    private void updateCellsWeightDeltas(int j){
        double internalStateError = backwardCache.getInternalStateError(j);
        
        int what = 10; //fetch from array?
        for(int m = 0; m < what; m++){
            double derivative = derivativeCache.getCellDerivative(m);
            
            //TODO: understand what m indexes...
            
        }
    }






}
