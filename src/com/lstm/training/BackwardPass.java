package com.lstm.training;

import com.lstm.datastructures.BackwardPassCache;
import com.lstm.datastructures.DerivativeCache;
import com.lstm.datastructures.ForwardPassCache;
import com.lstm.network.NetworkDescription;

import static com.lstm.network.Functions.*;

public class BackwardPass {

    private final DerivativeCache derivativeCache;
    private final ForwardPassCache forwardCache;
    private final BackwardPassCache backwardCache;

    private final int numInput;
    private final int numMemBlock;
    private final int numSourceUnit;

    private final double alpha;
    private final double momentum;

    public BackwardPass(NetworkDescription description, DerivativeCache dcache, ForwardPassCache fcache, BackwardPassCache bcache){
        this.derivativeCache = dcache;
        this.forwardCache = fcache;
        this.backwardCache = bcache;

        this.numInput = description.numInput;
        this.numMemBlock = description.numMemBlock;
        this.numSourceUnit = description.numSource;

        this.alpha = description.learningRate;
        this.momentum = description.momentum;
    }

    // target is the next character (c + 1) of the string sequence. Where the network in the forward pass received input of character c
    public void doRound(double[] targetExample){
        computeDeltas(targetExample);
        updateWeightsDeltas();
    }

    public void init(){
        //by default, let's init to the random weights.
        for(int k = 0; k < numInput; k++){
            for(int m = 0; m < numSourceUnit; m++){
                backwardCache.storeOutputUnit(k, m, forwardCache.getWeightBaked(k, m));
            }
        }
        
        for(int j = 0; j < numMemBlock; j++){
            for(int m = 0; m < numSourceUnit; m++){
                backwardCache.storeOutputGate(j, m, forwardCache.getWeightOut(j, m));
                backwardCache.storeInputGate(j, m, forwardCache.getWeightIn(j, m));
                backwardCache.storeForgetGate(j, m, forwardCache.getWeightF(j, m));
                backwardCache.storeCell(j, m, forwardCache.getWeightCell(j, m));
                
            }
            backwardCache.storeOutputGateC(j, forwardCache.getWeightOutToCell(j));
            backwardCache.storeInputGateC(j, 0, forwardCache.getWeightInToCell(j));
            backwardCache.storeForgetGateC(j, 0, forwardCache.getWeightFToCell(j));
        }
    }
    

    private void computeDeltas(double[] target){
        //Step 1: compute the deltas of the output units.
        for(int k = 0; k < numInput; k++){
            //compute the injection error
            double err = target[k] - forwardCache.getYBaked(k);
            double netk = forwardCache.getNetBaked(k);
            double deltak = dg(netk) * err;

            backwardCache.storeDelta(k, deltak);
        }

        //Step 2: compute the deltas for output gates and internal state error
        for(int j = 0; j < numMemBlock; j++){
            //Step 2.a: deltas for output gates
            double sum = 0;
            for(int k = 0; k < numInput; k++){
                double wkc = forwardCache.getWeightBaked(k, j + numInput);
                sum += backwardCache.getDelta(k) * wkc;
            }

            double scjv = forwardCache.getCellState(false, j); //assume 1 cell state in the memory block
            double netOutj = forwardCache.getNetOut(j);

            double deltaOutj = df(netOutj) * scjv * sum;
            backwardCache.storeDeltaOut(j, deltaOutj);

            //Step 2.b: internal state error
            double youtj = forwardCache.getYOut(j);
            double escjv = youtj * sum;
            backwardCache.storeInternalStateError(j, escjv);
        }
    }
    

    private void updateWeightsDeltas(){
        //compute general output units weights 
        for(int m = 0; m < numSourceUnit; m++){
            for(int k = 0; k < numInput; k++){
                double ym = forwardCache.smartGetYM(false, m);
                double deltak = backwardCache.getDelta(k);

                double wkm = alpha * ym * deltak;
                double oldwkm = backwardCache.getOutputUnit(k, m);

                //momentum alg.
                double newWeight = forwardCache.getWeightBaked(k, m) - alpha * wkm + momentum * oldwkm;

                forwardCache.storeWeightBaked(k, m, newWeight);

                //storing old one now.
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
            double ym = forwardCache.smartGetYM(true, m);
            double woutm = alpha * deltaout * ym;

            double oldwoutm = backwardCache.getOutputGate(j, m);
            double oldweight = forwardCache.getWeightIn(j, m);

            //momentum algorithm
            double neweight = oldweight - alpha * woutm + momentum * oldwoutm;

            forwardCache.storeWeightIn(j, m, neweight);

            //store for next iteration
            backwardCache.storeOutputGate(j, m, oldwoutm);
        }

        double scjv = forwardCache.getCellState(false, j);
        double woutcjv = alpha * deltaout * scjv;

        //momentum algorithm
        double oldwoutcjv = backwardCache.getOutputGateC(j);
        double oldweight = forwardCache.getWeightOutToCell(j);
        double newWeight = oldweight - alpha * woutcjv + momentum * oldwoutcjv;
        forwardCache.storeWeightOutToCell(j, newWeight);

        backwardCache.storeOutputGateC(j, woutcjv);
    }

    private void updateInputGatesWeightDeltas(int j){
        double internalStateError = backwardCache.getInternalStateError(j);

        for(int m = 0; m < numSourceUnit; m++){
            double derivative = derivativeCache.getInputGateDerivativeA(j, m);
            double deltawinm = alpha * derivative * internalStateError;

            //momentum
            double olddeltawinm = backwardCache.getInputGate(j, m);
            double oldweight = forwardCache.getWeightIn(j, m);
            double newWeight = oldweight - alpha * deltawinm + momentum * olddeltawinm;
            forwardCache.storeWeightIn(j, m, newWeight);

            backwardCache.storeInputGate(j, m, deltawinm);
        }

        //there is only one peephole going to input gate, yay!
        double derivative = derivativeCache.getInputGateDerivativeB(j, 0);
        double wincjvprime = alpha * internalStateError * derivative;

        //momentum
        double oldwinc = backwardCache.getInputGateC(j, 0);
        double oldweight = forwardCache.getWeightInToCell(j);
        double newweight = oldweight - alpha * wincjvprime + momentum * oldwinc;
        forwardCache.storeWeightInToCell(j, newweight);

        backwardCache.storeInputGateC(j, 0, wincjvprime);

    }

    private void updateForgetGatesWeightDeltas(int j){
        double internalStateError = backwardCache.getInternalStateError(j);

        for(int m = 0; m < numSourceUnit; m++){
            double derivative = derivativeCache.getForgetGateDerivativeA(j, m);
            double deltawinm = alpha * derivative * internalStateError;

            //momentum
            double olddelta = backwardCache.getForgetGate(j, m);
            double oldweight = forwardCache.getWeightF(j, m);
            double newweight = oldweight - alpha * deltawinm + momentum * olddelta;
            forwardCache.storeWeightF(j, m, newweight);
            
            backwardCache.storeForgetGate(j, m, deltawinm);
        }

        //there is only one peephole to forget gate, yay!
        double derivative = derivativeCache.getForgetGateDerivativeB(j, 0);
        double wincjvprime = alpha * internalStateError * derivative;
        
        //momentum
        double oldwinc = backwardCache.getforgetGateC(j, 0);
        double oldweight = forwardCache.getWeightFToCell(j);
        double newweight = oldweight - alpha * wincjvprime + momentum * oldwinc;
        forwardCache.storeWeightFToCell(j, newweight);
        
        backwardCache.storeForgetGateC(j, 0, wincjvprime);
        
    }

    private void updateCellsWeightDeltas(int j){
        double internalStateError = backwardCache.getInternalStateError(j);

        for(int m = 0; m < numSourceUnit; m++){
            double derivative = derivativeCache.getCellDerivative(j, m);
            double wcjvm = alpha * internalStateError * derivative;

            //momentum
            double oldwcjvm = backwardCache.getCell(j, m);
            double oldweight = forwardCache.getWeightCell(j, m);
            double newweight = oldweight - alpha * wcjvm + momentum * oldwcjvm;
            forwardCache.storeWeightCell(j, m, newweight);
            
            backwardCache.storeCell(j, m, wcjvm); 
        }
    }
}
