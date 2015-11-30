package com.lstm.layers;

import com.lstm.node.InputNode;
import com.lstm.node.MemoryBlock;

import java.util.ArrayList;

/**
 * Created by Wang on 11/15/2015.
 */
public class HiddenLayer extends Layer {

    private ArrayList<MemoryBlock> memoryBlocks;

    public HiddenLayer(int numMemoryBlocks)
    {
        memoryBlocks = new ArrayList<>(numMemoryBlocks);
    }

    public void forwardPass(Double[][] weights, Double[][] biases, Double[][] outputValues, int numInputs)
    {
        for (int i = 0; i < memoryBlocks.size(); i++) {
            memoryBlocks.get(i).ForwardPass(weights, biases, outputValues, i * 4, numInputs + (i * 4));
        }
    }



    public void backwardPass() {

    }
}
