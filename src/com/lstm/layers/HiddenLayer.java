package com.lstm.layers;

import com.lstm.node.InputNode;
import com.lstm.node.MemoryBlock;

import java.util.ArrayList;

/**
 * Created by Wang on 11/15/2015.
 */
public class HiddenLayer extends Layer {

    private ArrayList<MemoryBlock> memoryBlocks;
    private double inputNodesSum;

    public HiddenLayer(int numMemoryBlocks)
    {
        memoryBlocks = new ArrayList<>(numMemoryBlocks);
    }

    private void sumInputNodes(ArrayList<InputNode> inputNodes)
    {
        for (InputNode node : inputNodes) {
            inputNodesSum += node.Output();
        }
    }

    public void forwardPass(Double[][] weights, Double[][] biases, Double[][] outputValues) {

        //sumInputNodes(inputNodes);

        // Add inputNodesSum to each MemoryBlock input
        for (MemoryBlock memoryBlock : memoryBlocks)
        {
            memoryBlock.AddToInputs(inputNodesSum);
        }


    }

    private double computeCellInput(Double[][] weights, Double[][] biases, Double[][] outputValues, int column)
    {
        double cellInput = 0.0;

        for (int row = 0; row < weights.length; row++) {
            cellInput += weights[row][column] * outputValues[row][column];
        }

        return cellInput;
    }

    public void backwardPass() {

    }
}
