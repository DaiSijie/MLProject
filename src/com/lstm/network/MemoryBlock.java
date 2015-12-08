package com.lstm.network;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Wang on 11/15/2015.
 */
public class MemoryBlock {

    private double cellState;
    private double[] peepholeWeights;

    public MemoryBlock(int numCells)
    {
        cellState = 0.0;
        peepholeWeights = new double[3]; /* [0] inputGate [1] forgetGate [2] outputGate */
    }

    // TODO Peephole initialization not implemented yet

    public void ForwardPass(PragmaticCache pragmaticCache, int block_j)
    {
        double inputGate = squashingFunction(pragmaticCache.getInputGateInput(block_j) + getPeepHole(0));
        double cellInput = inputGate * squashingFunction(pragmaticCache.getMemoryBlockInput(block_j));

        double forgetGate = squashingFunction(pragmaticCache.getForgetGateInput(block_j) + getPeepHole(1));
        cellState = (cellState * forgetGate) + cellInput;

        double outputGate = squashingFunction(pragmaticCache.getOutputGateInput(block_j) + getPeepHole(2));
        double cellOutput = outputGate * cellState;


        // Store into outputValues
        pragmaticCache.storeMemoryBlockOutput(block_j, cellOutput);
        pragmaticCache.storeInputGateOutput(block_j, inputGate);
        pragmaticCache.storeForgetGateOutput(block_j, forgetGate);
        pragmaticCache.storeOutputGateOutput(block_j, outputGate);
    }

    private double getPeepHole(int peepholeIndex)
    {
        return cellState * peepholeWeights[peepholeIndex];
    }

    // Todo need to correct squashing function
    private double squashingFunction(double input)
    {
        return 1 / (1 + Math.exp(-input));
    }

}
