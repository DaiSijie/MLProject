package com.lstm.network.layers;

import com.lstm.datastructures.DerivativeCache;

/**
 * Created by Wang on 11/15/2015.
 */
public class HiddenLayer extends Layer {

    public HiddenLayer()
    {

    }

    public void forwardPass(DerivativeCache pragmaticCache)
    {
        // Assuming only 1 cell in each Memory Block.
        for (int block_j = 0; block_j < pragmaticCache.getNumMemBlock(); block_j++) {
            double inputGate = squashingFunction(pragmaticCache.getInputGateInput(block_j) + pragmaticCache.getInputGatePeephole(block_j,0));
            double cellInput = inputGate * squashingFunction(pragmaticCache.getMemoryBlockInput(block_j));

            double forgetGate = squashingFunction(pragmaticCache.getForgetGateInput(block_j) + pragmaticCache.getForgetGatePeephole(block_j,0));
            pragmaticCache.storeCellState(block_j, 0, (pragmaticCache.getCellState(block_j, 0) * forgetGate) + cellInput);

            double outputGate = squashingFunction(pragmaticCache.getOutputGateInput(block_j) + pragmaticCache.getOutputGatePeephole(block_j, 0));
            double cellOutput = outputGate * pragmaticCache.getCellState(block_j, 0);


            // Store into outputValues
            pragmaticCache.storeMemoryBlockOutput(block_j, cellOutput);
            pragmaticCache.storeInputGateOutput(block_j, inputGate);
            pragmaticCache.storeForgetGateOutput(block_j, forgetGate);
            pragmaticCache.storeOutputGateOutput(block_j, outputGate);
        }
    }

    // Todo need to correct squashing function
    private double squashingFunction(double input)
    {
        return 1 / (1 + Math.exp(-input));
    }

    public void backwardPass() {

    }
}
