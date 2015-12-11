package com.lstm.datastructures;

import java.util.HashMap;

public class ForwardPassCache {
    
    private final int numInput;
    private final int numMemBlock;
    private final int numCells;
    private final int numOutput;
    
    private final Double[][][] weights;       // for storing the weights between units
    private final Double[] biases;            // for storing the biases for the units in the hidden and output layers
    private final double[] input;
    private final Double[] output;
    
    private final HashMap<Integer, HashMap<Integer, Double[][]>> memoryBlocks;
    
    public ForwardPassCache(int numInput, int numMemBlock, int numCellsInMemBlock, int numOutput) {
        this.numInput = numInput;
        this.numMemBlock = numMemBlock;
        this.numCells = numCellsInMemBlock;
        this.numOutput = numOutput;

        this.weights = new Double[2][numInput + numMemBlock][(numMemBlock * 4) + numOutput];
        this.biases = new Double[(numMemBlock * 4) + numOutput];
        this.memoryBlocks = new HashMap<>();

        this.input = new double[numInput];      //not needed
        this.output = new Double[numOutput];
    }

    /*
        Weights (4 x 7): [0] is output values [1] is weight

        --> left hand side is the "from", top is the "to"
        ----> example: [0,0] is from input1 to memoryBlock

        the YES/NO shows if there is a "unit-to-unit" connection. Only the cells with YES should have a weight

                        memoryBlock1    inGate1      forgetGate1     outGate1        memoryBlock2    inGate2...      output1     output2     output3
        input1          YES             YES          YES             YES             ...             ...             YES         YES         YES
        input2          YES             YES          YES             YES             ...             ...             YES         YES         YES
        input3          YES             YES          YES             YES             ...             ...             YES         YES         YES
        memoryBlock1    YES             YES          YES             YES             ...             ...             YES         YES         YES
        memoryBlock2    ...             ...          ...             ...             ...             ...             ...         ...         ...

    */

    /*
        Biases (1 x 7):
                        memoryBlock     inGate      forgetGate      outGate     output1     output2     output3
        value           -               -           -               -           -           -           -
    */

    //bias weights to input gate, forget gate and output gate are initialized with 1.0, 2.0 and 2.0. All other
    //weights are initialized randomly in the range [ 0.1, 0.1].

    /*
        MemoryBlocks (memoryBlock_j x cells_v x [Value or Weight][4]):
                       cellState       inGatePeephole      forgetGatePeephole      outGatePeephole
        value          -               -                   -                       -
        weight         null            -                   -                       -
    */

    // TODO Peephole initialization not implemented yet

    /* Column Summation for Weighted Input */
    private double weightedInputSummation(int column){
        double cellInput = 0.0;

        for (int row = 0; row < weights.length; row++) {
            cellInput += weights[0][row][column] * weights[1][row][column];
        }

        cellInput += biases[column];

        return cellInput;
    }

    public double getMemoryBlockInput(int block_j){
        int index = block_j * 4;
        return weightedInputSummation(index);
    }

    public double getInputGateInput(int block_j){
        int index = (block_j * 4) + 1;
        return weightedInputSummation(index);
    }

    public double getForgetGateInput(int block_j){
        int index = (block_j * 4) + 2;
        return weightedInputSummation(index);
    }

    public double getOutputGateInput(int block_j){
        int index = (block_j * 4) + 3;
        return weightedInputSummation(index);
    }

    public double getOutputNodeInput(int output_k){
        int index = (numMemBlock + 1) * 4 + output_k;
        return weightedInputSummation(index);
    }
    /* Column Summation for Weighted Input */

    /* Start Input Layer */
    public double getInputNodeOutput(int input_i){
        int index = input_i;
        return weights[0][index][0];
    }

    public void storeInputNodeOutput(int input_i, double value){
        int index = input_i;
        for (int col = 0; col < weights[0][index].length; col++){
            weights[0][index][col] = value;
        }
    }
    /* End Input Layer */

    /* Start Hidden Layer */
    public double getMemoryBlockOutput(int block_j){
        int index = numInput + block_j;
        return weights[0][index][0];
    }

    public void storeMemoryBlockOutput(int block_j, double value){
        int index = numInput + block_j;

        for (int col = 0; col < weights[0][index].length; col++) {
            weights[0][index][col] = value;
        }
    }
    /* End Hidden Layer */

    /* Start Output Layer */
    public double getOutputNodeOutput(int k){
        return output[k];
    }

    public void storeOutputNodeOutput(int k, double value){
        output[k] = value;
    }
    /* End Output Layer */

    /* Start Peephole and Cell State */
    public double getInputGateOutput(int j, int vprime){
        return memoryBlocks.get(j).get(vprime)[1][1];
    }

    public double getForgetGateOutput(int j, int vprime){
        return memoryBlocks.get(j).get(vprime)[1][2];
    }

    public double getOutputGateOutput(int j, int vprime){
        return memoryBlocks.get(j).get(vprime)[1][2];
    }

    public void storeInputGateOutput(int j, int vprime, double value){
        if (!memoryBlocks.containsKey(j)){
            memoryBlocks.put(j, new HashMap<Integer, Double[][]>());
            memoryBlocks.get(j).put(vprime, new Double[2][4]);
        }

        memoryBlocks.get(j).get(vprime)[1][1] = value;
    }

    public void storeForgetGateOutput(int j, int vprime, double value) {
        if (!memoryBlocks.containsKey(j)){
            memoryBlocks.put(j, new HashMap<Integer, Double[][]>());
            memoryBlocks.get(j).put(vprime, new Double[2][4]);
        }

        memoryBlocks.get(j).get(vprime)[1][2] = value;
    }

    public void storeOutputGateOutput(int j, int vprime, double value) {
        if (!memoryBlocks.containsKey(j)){
            memoryBlocks.put(j, new HashMap<Integer, Double[][]>());
            memoryBlocks.get(j).put(vprime, new Double[2][4]);
        }

        memoryBlocks.get(j).get(vprime)[1][3] = value;
    }

    public double getCellState(int j, int vprime){
        return memoryBlocks.get(j).get(vprime)[0][0];
    }

    public double getInputGatePeephole(int j, int vprime){
        return memoryBlocks.get(j).get(vprime)[0][1];
    }

    public double getForgetGatePeephole(int j, int vprime){
        return memoryBlocks.get(j).get(vprime)[0][2];
    }

    public double getOutputGatePeephole(int j, int vprime){
        return memoryBlocks.get(j).get(vprime)[0][3];
    }

    public void storeCellState(int j, int vprime, double value){
        if (!memoryBlocks.containsKey(j)){
            memoryBlocks.put(j, new HashMap<Integer, Double[][]>());
            memoryBlocks.get(j).put(vprime, new Double[2][4]);
        }
        memoryBlocks.get(j).get(vprime)[0][0] = value;
    }

    public void storeInputGatePeephole(int j, int vprime, double value){
        if (!memoryBlocks.containsKey(j)){
            memoryBlocks.put(j, new HashMap<Integer, Double[][]>());
            memoryBlocks.get(j).put(vprime, new Double[2][4]);
        }

        memoryBlocks.get(j).get(vprime)[0][1] = value;
    }

    public void storeForgetGatePeephole(int j, int vprime, double value){
        if (!memoryBlocks.containsKey(j)){
            memoryBlocks.put(j, new HashMap<Integer, Double[][]>());
            memoryBlocks.get(j).put(vprime, new Double[2][4]);
        }

        memoryBlocks.get(j).get(vprime)[0][2] = value;
    }

    public void storeOutputGatePeephole(int j, int vprime, double value){
        if (!memoryBlocks.containsKey(j)){
            memoryBlocks.put(j, new HashMap<Integer, Double[][]>());
            memoryBlocks.get(j).put(vprime, new Double[2][4]);
        }
        memoryBlocks.get(j).get(vprime)[0][3] = value;
    }
    /* End Peephole and Cell State */

    /* Add functions to update the weights for the backward pass */
    public double getMemoryBlockToOutputWeights(int from, int to)
    {
        return weights[1][numInput + from][(numMemBlock * 4) + to];
    }

    /*Misc. get outputs. TODO: Eliminate need of knowledge of the weights table. */
    public double get_yhat_inputToOutputNode(int from, int to)  //to is k
    {
        return weights[0][from][(numMemBlock * 4) + to];
    }

    public double get_yhat_inputToOutputGate(int from, int j)
    {
        return weights[0][from][(j * 4) + 3];
    }
    
    public double getYHat(int m){
        if(m < numInput)
            return getExample(m);
        else
            return getOutputGateOutput(m - input.length, 0);
    }
    
    public void setExample(double[] example){
        for(int k = 0; k < example.length; k++){
            this.input[k] = example[k];
        }
    }
    
    public double getExample(int k){
        return input[k];
    }

}
