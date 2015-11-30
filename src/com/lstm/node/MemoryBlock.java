package com.lstm.node;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Wang on 11/15/2015.
 */
public class MemoryBlock {
    private Gate inputGate;
    private Gate forgetGate;
    private Gate outputGate;

    private double cellState;
    private double[] peepholes;

    public MemoryBlock(int numCells)
    {
        cellState = 0.0;
        peepholes = new double[3];
    }

    // TODO Peephole not implemented yet

    public void ForwardPass(Double[][] weights, Double[][] biases, Double[][] outputValues, int memBlockColIndex, int memBlockRowIndex)
    {
        double inputGate = weightedInputSummation(weights, biases, outputValues, memBlockColIndex + 1, true);
        double cellInput = inputGate * weightedInputSummation(weights, biases, outputValues, memBlockColIndex, true);

        double forgetGate = weightedInputSummation(weights, biases, outputValues, memBlockColIndex + 2, true);
        cellState = (cellState * forgetGate) + cellInput;

        double outputGate = weightedInputSummation(weights, biases, outputValues, memBlockColIndex + 3, true);
        double cellOutput = outputGate * cellState;


        // Store into outputValues
        for (int col = 0; col < outputValues[memBlockRowIndex].length; col++) {
            outputValues[memBlockRowIndex][col] = cellOutput;
        }

        for (int col = 0; col < outputValues[memBlockRowIndex + 1].length; col++) {
            outputValues[memBlockRowIndex + 1][col] = inputGate;
        }

        for (int col = 0; col < outputValues[memBlockRowIndex + 2].length; col++) {
            outputValues[memBlockRowIndex + 2][col] = forgetGate;
        }

        for (int col = 0; col < outputValues[memBlockRowIndex + 3].length; col++) {
            outputValues[memBlockRowIndex + 3][col] = outputGate;
        }
    }

    private double weightedInputSummation(Double[][] weights, Double[][] biases, Double[][] outputValues, int column, boolean applySquash)
    {
        double cellInput = 0.0;

        for (int row = 0; row < weights.length; row++) {
            cellInput += weights[row][column] * outputValues[row][column];
        }

        if (applySquash)
            return squashingFunction(cellInput);
        else
            return cellInput;
    }

    private double squashingFunction(double input)
    {
        return 1 / (1 + Math.exp(-input));
    }
}
