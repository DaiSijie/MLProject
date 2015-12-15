package com.lstm.training;

import com.lstm.datastructures.BackwardPassCache;
import com.lstm.datastructures.DerivativeCache;
import com.lstm.datastructures.ForwardPassCache;
import com.lstm.datastructures.ForwardPassCache2;
import com.lstm.network.NetworkDescription;

import java.util.ArrayList;

import static com.lstm.network.Functions.*;

public class BackwardPass {

    private final DerivativeCache derivativeCache;
    private final ForwardPassCache forwardCache;
    private final BackwardPassCache backwardCache;

    private final int numInput;
    private final int numMemBlock;
    private final int numSourceUnit;
    private final double alpha;

    public BackwardPass(NetworkDescription description, DerivativeCache derivativeCache, ForwardPassCache2 forwardPassCache, BackwardPassCache backwardPassCache){
        this.derivativeCache = derivativeCache;
        this.forwardCache = forwardPassCache;
        this.backwardCache = backwardPassCache;

        this.numSourceUnit = numSourceUnit;
        this.numInput = numInput;
        this.numMemBlock = numMemBlock;
        this.alpha = alpha;
    }

    // target is the next character (c + 1) of the string sequence. Where the network in the forward pass received input of character c
    public void doRound(double[] targetExample){
        computeDeltas(targetExample);
        updateWeightsDeltas();
    }

    public void init(){

    }

    private void computeDeltas(double[] target){
        //Step 1: compute the deltas of the output units.
        for(int k = 0; k < numInput; k++){
            //compute the injection error
            double err = target[k] - forwardCache.getOutputNodeOutput(k);
            double netk = forwardCache.getOutputNodeInput(k);
            double deltak = dg(netk) * err;

            backwardCache.storeDelta(k, deltak);
        }

        //Step 2: compute the deltas for output gates and internal state error
        for(int j = 0; j < numMemBlock; j++){
            //Step 2.a: deltas for output gates
            double sum = 0;
            for(int k = 0; k < numInput; k++){
                double wkc = forwardCache.getMemoryBlockToOutputWeights(j,k);   // what is capital W?, assumed is like little w
                sum += backwardCache.getDelta(k) * wkc;
            }

            double netOutj = forwardCache.getOutputGateInput(j) + forwardCache.getOutputGatePeephole(j, 0);
            double scjv = forwardCache.getCellState(j, 0); //assume 1 cell state in the memory block

            double deltaOutj = df(netOutj) * scjv * sum;
            backwardCache.storeDeltaOut(j, deltaOutj);

            //Step 2.b: internal state error NB: v always 1
            double youtj = forwardCache.getOutputGateOutput(j, 0);
            double escjv = youtj * sum;
            backwardCache.storeInternalStateError(j, escjv);

        }
    }

    private void updateWeightsDeltas(){
        //compute general output units weights 
        for(int m = 0; m < numSourceUnit; m++){
            for(int k = 0; k < numInput; k++){
                double ym = forwardCache.get_yhat_inputToOutputNode(m, k);
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

        for(int m = 0; m < numSourceUnit; m++){
            double ym = forwardCache.get_yhat_inputToOutputGate(m, j);
            double woutm = alpha * deltaout * ym;

            backwardCache.storeOutputGate(j, m, woutm);
        }

        double scjv = forwardCache.getCellState(j,0); //fetch from array? - done
        double woutcjv = alpha * deltaout * scjv;

        backwardCache.storeOutputGateC(j, woutcjv);
    }

    private void updateInputGatesWeightDeltas(int j){
        double internalStateError = backwardCache.getInternalStateError(j);

        for(int m = 0; m < numSourceUnit; m++){
            double derivative = derivativeCache.getInputGateDerivativeA(j, m);
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

        for(int m = 0; m < numSourceUnit; m++){
            double derivative = derivativeCache.getForgetGateDerivativeA(j, m);
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
        
        for(int m = 0; m < numSourceUnit; m++){
            double derivative = derivativeCache.getCellDerivative(j, m);
            double wcjvm = alpha * internalStateError * derivative;
            
            backwardCache.storeCell(j, m, wcjvm);            
        }
    }
}
