package com.lstm.layers;

import com.lstm.node.InputNode;

import java.util.ArrayList;

/**
 * Created by Wang on 11/15/2015.
 */
public class OutputLayer {

    public void forwardPass(Double[][] weights, Double[] biases, Double[][] outputValues, int numOutputs) {
        //for each output node
        for (int row = weights.length - numOutputs; row < weights.length ; row++) {
            double outputNodeInput = squashingFunction(weightedInputSummation(weights, biases, outputValues, weights[row].length - numOutputs));

            for (int col = 0; col < weights[row].length; col++) {
                outputValues[row][col] = outputNodeInput;
            }
        }
    }

    public void backwardPass() {

    }

    // TODO clean up implementation so that not copy/paste "weightedInputSummation" and "squashingFunction" from MemoryBlock
    private double weightedInputSummation(Double[][] weights, Double[] biases, Double[][] outputValues, int column)
    {
        double cellInput = 0.0;

        for (int row = 0; row < weights.length; row++) {
            cellInput += weights[row][column] * outputValues[row][column];
        }

        cellInput += biases[column];

        return cellInput;
    }

    private double squashingFunction(double input)
    {
        return 1 / (1 + Math.exp(-input));
    }
}
