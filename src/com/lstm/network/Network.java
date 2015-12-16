package com.lstm.network;

import com.lstm.datastructures.ForwardPassCache;
import com.lstm.generator.Generator;
import com.lstm.datastructures.BackwardPassCache;
import com.lstm.datastructures.DerivativeCache;
import com.lstm.training.BackwardPass;
import com.lstm.training.DerivativeComputation;
import com.lstm.training.ForwardPass;

public class Network {
    
    private final ForwardPass forwardPass;
    private final DerivativeComputation derivativeComputation;
    private final BackwardPass backwardPass;
    
    private final int numOutput;
    
    public Network(NetworkDescription description, Generator gen) {
        //we build everything for the forward pass
        ForwardPassCache fwdcache = new ForwardPassCache(description);
        this.forwardPass = new ForwardPass(description, fwdcache);
        
        //we build everything for the derivative computation
        DerivativeCache dcache = new DerivativeCache();
        this.derivativeComputation = new DerivativeComputation(description, dcache, fwdcache);
        
        //we build everything for the backward pass
        BackwardPassCache bcache = new BackwardPassCache();
        this.backwardPass = new BackwardPass(description, dcache, fwdcache, bcache);
        
        this.numOutput = description.numOutput;
        
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
        double[] target = null;
        
        doRound(input, target);
    }
    
    private void doRound(double[] input, double[] target){
        forwardPass.doRound(input);
        derivativeComputation.doRound();
        backwardPass.doRound(target);
    }


    //Learning and testing alternate: after each epoch (= 1000 training sequences) we freeze the weights and run a test.
    public boolean classify(String str) {
        for(char c : str.toCharArray()){
            //somehow transform the char into an array
            double[] input = null;
            
            forwardPass.doRound(input);
            for(int k = 0; k < numOutput; k++){
//                if(input[k] != forwardCache.getOutputNodeOutput(k))
                    return false;
            }
            
        }
        
        return true;
    }
}
