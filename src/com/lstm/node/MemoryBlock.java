package com.lstm.node;

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

    public void ForwardPass(Double[][] weights, Double[] biases, Double[][] outputValues, int memBlockColIndex, int memBlockRowIndex)
    {
        double inputGate = squashingFunction(weightedInputSummation(weights, biases, outputValues, memBlockColIndex + 1) + getPeepHole(0));
        double cellInput = inputGate * squashingFunction(weightedInputSummation(weights, biases, outputValues, memBlockColIndex));

        double forgetGate = squashingFunction(weightedInputSummation(weights, biases, outputValues, memBlockColIndex + 2) + getPeepHole(1));
        cellState = (cellState * forgetGate) + cellInput;

        double outputGate = squashingFunction(weightedInputSummation(weights, biases, outputValues, memBlockColIndex + 3) + getPeepHole(2));
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

    private double weightedInputSummation(Double[][] weights, Double[] biases, Double[][] outputValues, int column)
    {
        double cellInput = 0.0;

        for (int row = 0; row < weights.length; row++) {
            cellInput += weights[row][column] * outputValues[row][column];
        }

        cellInput += biases[column];

        return cellInput;
    }

    private double getPeepHole(int peepholeIndex)
    {
        return cellState * peepholeWeights[peepholeIndex];
    }

    private double squashingFunction(double input)
    {
        return 1 / (1 + Math.exp(-input));
    }

    private double new1(double old1, double a, double b, double c, double d){
        return old1 * a + dg(b)*c*d;
    }

    private double new2(double old2, double a, double b, double c, double d){
        return old2 * a + g(b)*dg(c)*d;
    }

    private double new3(double old3, double a, double b, double c, double d){
        return old3 * a + g(b)*dg(c)*d;
    }

    private double new4(double old4, double a, double b, double c, double d){
        return old4 * a + b*dg(c)*d;
    }

    private double new5(double old5, double a, double b, double c, double d){
        return old5 * a + b*dg(c)*d;
    }

    private double g(double z){
        return 1/(1-Math.exp(-z));
    }

    private double dg(double z){
        return -Math.exp(-z)/((1-Math.exp(-z))*(1-Math.exp(-z)));
    }
}
