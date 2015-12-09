package com.lstm.network;

import java.util.ArrayList;

import com.lstm.datastructures.ForwardPassCache;
import com.lstm.datastructures.DerivativeCache;
import com.lstm.network.layers.HiddenLayer;
import com.lstm.network.layers.InputLayer;
import com.lstm.network.layers.OutputLayer;
import com.lstm.training.BackwardPass;
import com.lstm.training.DerivativeComputation;
import com.lstm.training.ForwardPass;

/**
 * Created by Wang on 11/15/2015.
 */
public class Network {
    
    private final int numInput;
    private final int numHidden;
    private final int numMemBlock;
    private final int numOutput;
    private final double alpha;
    
    private final DerivativeCache derivativeCache;
    private final ForwardPassCache forwardCache;

    private final ForwardPass forwardPass;
    private final DerivativeComputation derivativeComputation;
    private final BackwardPass backwardPass;
    
    InputLayer inputLayer;
    HiddenLayer hiddenLayer;
    OutputLayer outputLayer;


    public Network(int numInput, int numMemBlock, double learningRate, Generator gen) {
        this.numInput = numInput;
        this.numHidden = numMemBlock;
        this.numMemBlock = numMemBlock;
        this.alpha = learningRate;
        
        this.derivativeCache = new DerivativeCache();        
        //the number of cells is always 1, and the number of input is equal to the number of input
        this.forwardCache = new ForwardPassCache(numInput, numMemBlock, 1, numInput);

        this.forwardPass = new ForwardPass();
        this.derivativeComputation = new DerivativeComputation(derivativeCache, forwardCache, numMemBlock);
        this.backwardPass = new BackwardPass(derivativeCache, forwardCache);

        //init everything
        forwardPass.init();
        derivativeComputation.init();
        backwardPass.init();
        
        train()
    }


    
    public void train(ArrayList<Double> example) {
        inputLayer.forwardPass(derivativeCache, example);
        hiddenLayer.forwardPass(derivativeCache);
        outputLayer.forwardPass(derivativeCache);

        outputLayer.backwardPass();
        hiddenLayer.backwardPass();
        inputLayer.backwardPass();
    }

    public void classify() {

    }

    private double g(double z) {
        return 1 / (1 + Math.exp(-z));
    }

    // Todo check that formula is correct
    private double dg(double z) {
        return -Math.exp(-z) / ((1 - Math.exp(-z)) * (1 - Math.exp(-z)));
    }

    
    private void backwardpass(double[] targetY, double[] actualY){
        double[] deltas = new double[numOutput];
        
        for(int i = 0; i < numOutput; i++){
            double netk = 1; //fill
            deltas[i] = dg(netk) * (targetY[i] - actualY[i]);
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



