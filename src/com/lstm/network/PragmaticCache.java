package com.lstm.network;

import java.util.HashMap;

/**
 * Created by Wang on 12/6/2015.
 */
public class PragmaticCache {
    private int numInput;
    private int numMemBlock;
    private int numCells;
    private int numOutput;

    private final HashMap<Integer, Double> cellDerivative;

    private final HashMap<Integer, Double> inputGateDerivativeA;
    private final HashMap<Integer, HashMap<Integer, Double>> inputGateDerivativeB;

    private final HashMap<Integer, Double> forgetGateDerivativeA;
    private final HashMap<Integer, HashMap<Integer, Double>> forgetGateDerivativeB;

    public PragmaticCache(int numInput, int numMemBlock, int numCellsInMemBlock, int numOutput) {
        this.cellDerivative = new HashMap<>();

        this.inputGateDerivativeA = new HashMap<>();
        this.inputGateDerivativeB = new HashMap<>();

        this.forgetGateDerivativeA = new HashMap<>();
        this.forgetGateDerivativeB = new HashMap<>();

        this.numInput = numInput;
        this.numMemBlock = numMemBlock;
        this.numCells = numCellsInMemBlock;
        this.numOutput = numOutput;

        this.biases = new Double[(numMemBlock * 4) + numOutput];
        this.weights = new Double[numInput + (numMemBlock * 4) + numOutput][(numMemBlock * 4) + numOutput];
        this.outputValues = new Double[numInput + (numMemBlock * 4) + numOutput][(numMemBlock * 4) + numOutput];

        this.memoryBlocks = new HashMap<>();
    }

    public int getNumInput() {
        return numInput;
    }

    public int getNumMemBlock() {
        return numMemBlock;
    }

    public int getNumCells() {
        return numCells;
    }

    public int getNumOutput() {
        return numOutput;
    }

    public double getCellDerivative(int j) {
        return cellDerivative.get(j);
    }

    public double getInputGateDerivativeA(int j) {
        return inputGateDerivativeA.get(j);
    }

    public double getInputGateDerivativeB(int j, int vprime) {
        return inputGateDerivativeB.get(j).get(vprime);
    }

    public double getForgetGateDerivativeA(int j) {
        return forgetGateDerivativeA.get(j);
    }

    public double getForgetGateDerivativeB(int j, int vprime) {
        return forgetGateDerivativeB.get(j).get(vprime);
    }

    public void storeCellDerivative(int j, double value) {
        cellDerivative.put(j, value);
    }

    public void storeInputGateDerivativeA(int j, double value) {
        inputGateDerivativeA.put(j, value);
    }



    public void storeInputGateDerivativeB(int j, int vprime, double value) {
        if (!inputGateDerivativeB.containsKey(j))
            inputGateDerivativeB.put(j, new HashMap<Integer, Double>());

        inputGateDerivativeB.get(j).put(vprime, value);
    }

    public void storeForgetGateDerivativeA(int j, double value) {
        forgetGateDerivativeA.put(j, value);
    }

    public void storeForgetGateDerivativeB(int j, int vprime, double value) {
        if (!forgetGateDerivativeB.containsKey(j))
            forgetGateDerivativeB.put(j, new HashMap<Integer, Double>());

        forgetGateDerivativeB.get(j).put(vprime, value);
    }


    private Double[][] weights;         // for storing the weights between units
    private Double[] biases;            // for storing the biases for the units in the hidden and output layers
    private Double[][] outputValues;    // for passing the values between the units/layers

    private final HashMap<Integer, HashMap<Integer, Double[]>> memoryBlocks;

    /*
        Weights/OutputValues (10 x 7):

        -----> assumption in this illustration is that this network has only 1 memory block

        --> left hand side is the "from"
        ---> top is the "to"
        ----> example: [0,0] is from input1 to memoryBlock

                        memoryBlock     inGate      forgetGate      outGate     output1     output2     output3
        input1          -               -           -               -           -           -           -
        input2          -               -           -               -           -           -           -
        input3          -               -           -               -           -           -           -
        memoryBlock     -               -           -               -           -           -           -
        inGate          -               -           -               -           -           -           -
        forgetGate      -               -           -               -           -           -           -
        outGate         -               -           -               -           -           -           -
        output1         -               -           -               -           -           -           -
        output2         -               -           -               -           -           -           -
        output3         -               -           -               -           -           -           -
    */

    /*
        Biases (1 x 7):
                        memoryBlock     inGate      forgetGate      outGate     output1     output2     output3
        value           -               -           -               -           -           -           -
    */

    /*
        MemoryBlocks (memoryBlock_j x cells_v x 4):
                       cellState       inGatePeephole      forgetGatePeephole      outGatePeephole
        cell           -               -                   -                       -
    */

    // TODO Clean up code below so is condensed. Keeping this way for now for debugging purposes.

    // TODO Peephole initialization not implemented yet

    private double weightedInputSummation(int column)
    {
        double cellInput = 0.0;

        for (int row = 0; row < weights.length; row++) {
            cellInput += weights[row][column] * outputValues[row][column];
        }

        cellInput += biases[column];

        return cellInput;
    }

    /* Start Input Layer */
    public double getInputNodeOutput(int input_i)
    {
        int index = input_i;
        return outputValues[index][0];
    }

    public void storeInputNodeOutput(int input_i, double value)
    {
        int index = input_i;
        for (int col = 0; col < outputValues[index].length; col++) {
            outputValues[index][col] = value;
        }
    }
    /* End Input Layer */

    /* Start Hidden Layer */
    public double getMemoryBlockInput(int block_j) {
        int index = block_j * 4;
        return weightedInputSummation(index);
    }

    public double getInputGateInput(int block_j) {
        int index = (block_j * 4) + 1;
        return weightedInputSummation(index);
    }

    public double getForgetGateInput(int block_j) {
        int index = (block_j * 4) + 2;
        return weightedInputSummation(index);
    }

    public double getOutputGateInput(int block_j) {
        int index = (block_j * 4) + 3;
        return weightedInputSummation(index);
    }

    public double getMemoryBlockOutput(int block_j)
    {
        int index = getNumInput() + block_j * 4;
        return outputValues[index][index - getNumInput()];
    }

    public double getInputGateOutput(int block_j)
    {
        int index = getNumInput() + block_j * 4;
        index += 1;
        return outputValues[index][index - getNumInput()];
    }

    public double getForgetGateOutput(int block_j)
    {
        int index = getNumInput() + block_j * 4;
        index += 2;
        return outputValues[index][index - getNumInput()];
    }

    public double getOutputGateOutput(int block_j)
    {
        int index = getNumInput() + block_j * 4;
        index += 3;
        return outputValues[index][index - getNumInput()];
    }

    public void storeMemoryBlockOutput(int block_j, double value) {
        int index = getNumInput() + block_j * 4;

        for (int col = 0; col < outputValues[index].length; col++) {
            outputValues[index][col] = value;
        }
    }

    public void storeInputGateOutput(int block_j, double value)
    {
        int index = getNumInput() + block_j * 4;
        index += 1;
        for (int col = 0; col < outputValues[index].length; col++) {
            outputValues[index][col] = value;
        }
    }

    public void storeForgetGateOutput(int block_j, double value) {
        int index = getNumInput() + block_j * 4;
        index += 2;
        for (int col = 0; col < outputValues[index].length; col++) {
            outputValues[index][col] = value;
        }
    }

    public void storeOutputGateOutput(int block_j, double value) {
        int index = getNumInput() + block_j * 4;
        index += 3;
        for (int col = 0; col < outputValues[index].length; col++) {
            outputValues[index][col] = value;
        }
    }
    /* End Hidden Layer */

    /* Start Output Layer */
    public double getOutputNodeInput(int output_k)
    {
        int index = (getNumMemBlock() + 1) * 4 + output_k;
        return weightedInputSummation(index);
    }

    public double getOutputNodeOutput(int output_k)
    {
        int index = getNumInput() + ((getNumMemBlock() + 1) * 4) + output_k;
        return outputValues[index][index - getNumInput()];
    }

    public void storeOutputNodeOutput(int output_k, double value)
    {
        int index = getNumInput() + ((getNumMemBlock() + 1) * 4) + output_k;
        for (int col = 0; col < outputValues[index].length; col++) {
            outputValues[index][col] = value;
        }
    }
    /* End Output Layer */

    public double getCellState(int j, int vprime)
    {
        return memoryBlocks.get(j).get(vprime)[0];
    }

    public double getInputGatePeephole(int j, int vprime)
    {
        return memoryBlocks.get(j).get(vprime)[1];
    }

    public double getForgetGatePeephole(int j, int vprime)
    {
        return memoryBlocks.get(j).get(vprime)[2];
    }

    public double getOutputGatePeephole(int j, int vprime)
    {
        return memoryBlocks.get(j).get(vprime)[3];
    }

    public void storeCellState(int j, int vprime, double value)
    {
        if (!memoryBlocks.containsKey(j))
        {
            memoryBlocks.put(j, new HashMap<Integer, Double[]>());
            memoryBlocks.get(j).put(vprime, new Double[4]);
        }

        memoryBlocks.get(j).get(vprime)[0] = value;
    }

    public void storeInputGatePeephole(int j, int vprime, double value)
    {
        if (!memoryBlocks.containsKey(j))
        {
            memoryBlocks.put(j, new HashMap<Integer, Double[]>());
            memoryBlocks.get(j).put(vprime, new Double[4]);
        }

        memoryBlocks.get(j).get(vprime)[1] = value;
    }

    public void storeForgetGatePeephole(int j, int vprime, double value)
    {
        if (!memoryBlocks.containsKey(j))
        {
            memoryBlocks.put(j, new HashMap<Integer, Double[]>());
            memoryBlocks.get(j).put(vprime, new Double[4]);
        }

        memoryBlocks.get(j).get(vprime)[2] = value;
    }

    public void storeOutputGatePeephole(int j, int vprime, double value)
    {
        if (!memoryBlocks.containsKey(j))
        {
            memoryBlocks.put(j, new HashMap<Integer, Double[]>());
            memoryBlocks.get(j).put(vprime, new Double[4]);
        }

        memoryBlocks.get(j).get(vprime)[3] = value;
    }


}
