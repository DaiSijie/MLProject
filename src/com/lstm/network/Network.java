package com.lstm.network;

import com.lstm.datastructures.ForwardPassCache;
import com.lstm.generator.Generator;
import com.lstm.datastructures.DerivativeCache;
import com.lstm.training.BackwardPass;
import com.lstm.training.DerivativeComputation;
import com.lstm.training.ForwardPass;

public class Network {
    
    private final int numInput;
  
    private final DerivativeCache derivativeCache;
    private final ForwardPassCache forwardCache;

    private final ForwardPass forwardPass;
    private final DerivativeComputation derivativeComputation;
    private final BackwardPass backwardPass;
    
    public Network(int numInput, int numMemBlock, double learningRate, Generator gen) {    
        this.numInput = numInput;
        
        this.derivativeCache = new DerivativeCache();        
        //the number of cells is always 1, and the number of input is equal to the number of input
        this.forwardCache = new ForwardPassCache(numInput, numMemBlock, 1, numInput);

        this.forwardPass = new ForwardPass(forwardCache, numMemBlock, numInput);
        this.derivativeComputation = new DerivativeComputation(derivativeCache, forwardCache, numMemBlock);
        this.backwardPass = new BackwardPass(derivativeCache, forwardCache);

        //init everything
        forwardPass.init();
        derivativeComputation.init();
        backwardPass.init();
        
        //and train
        train(gen);
    }
    
    private void train(Generator gen){
        //for all token in the generator, figure out the input from the generator and do a round
        double[] input = null;
        
        doRound(input); 
    }
    
    private void doRound(double[] input){
        forwardPass.doRound(input);
        derivativeComputation.doRound();
        backwardPass.doRound(input);
    }

    public boolean classify(String str) {
        for(char c : str.toCharArray()){
            //somehow transform the char into an array
            double[] input = null;
            
            forwardPass.doRound(input);
            for(int k = 0; k < numInput; k++){
                if(input[k] != forwardCache.getOutputNodeOutput(k))
                    return false;
            }
            
        }
        
        return true;
    }
}
