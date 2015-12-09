/*
 *	Author:      Gilbert Maystre
 *	Date:        Dec 9, 2015
 */

package com.lstm.training;

import com.lstm.datastructures.ForwardPassCache;

import static com.lstm.network.Functions.*;

public class ForwardPass {

    private final ForwardPassCache forwardCache;
    private final int numMemBlock;
    private final int numInput;
    
    public ForwardPass(ForwardPassCache forwardCache, int numMemBlock, int numInput){
        this.forwardCache = forwardCache;
        this.numMemBlock = numMemBlock;
        this.numInput = numInput;
    }
    
    public void doRound(double[] input){
        inputLayer(input);
        hiddenLayer();
        outputLayer();
    }
    
    public void init(){
        //TODO: fill initial values in cache.
    }
    
    private void inputLayer(double[] input){
        for(int i = 0; i < input.length; i++)
            forwardCache.storeInputNodeOutput(i, input[i]);
    }
    
    private void hiddenLayer(){
        // Assuming only 1 cell in each Memory Block.
        for (int block_j = 0; block_j < numMemBlock; block_j++) {
            double inputGate = f(forwardCache.getInputGateInput(block_j) + forwardCache.getInputGatePeephole(block_j,0));
            double cellInput = inputGate * f(forwardCache.getMemoryBlockInput(block_j));

            double forgetGate = f(forwardCache.getForgetGateInput(block_j) + forwardCache.getForgetGatePeephole(block_j,0));
            forwardCache.storeCellState(block_j, 0, (forwardCache.getCellState(block_j, 0) * forgetGate) + cellInput);

            double outputGate = f(forwardCache.getOutputGateInput(block_j) + forwardCache.getOutputGatePeephole(block_j, 0));
            double cellOutput = outputGate * forwardCache.getCellState(block_j, 0);

            // Store into outputValues
            forwardCache.storeMemoryBlockOutput(block_j, cellOutput);
            forwardCache.storeInputGateOutput(block_j, inputGate);
            forwardCache.storeForgetGateOutput(block_j, forgetGate);
            forwardCache.storeOutputGateOutput(block_j, outputGate);
        }
    }
    
    private void outputLayer(){
        for (int i = 0; i < numInput; i++) {
            double outputNodeInput = f(forwardCache.getOutputNodeInput(i));
            forwardCache.storeOutputNodeOutput(i, outputNodeInput);
        }
    }
}
