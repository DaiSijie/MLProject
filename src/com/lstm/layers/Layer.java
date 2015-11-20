package com.lstm.layers;

import java.util.ArrayList;

import com.lstm.node.Node;

/**
 * Created by Wang on 11/15/2015.
 */
public abstract class Layer {
    private Double[][] weights;
    private Double[][] biases;

    /*
        Weights (10 x 7):
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

    public Layer(int numRows, int numColumns)
    {
        weights = new Double[numRows][numColumns];
        biases = new Double[1][numColumns];
    }

    public Double[][] getWeights() {
        return weights;
    }

    protected void setWeights(Double[][] weights) {
        this.weights = weights;
    }

    public Double[][] getBiases() {
        return biases;
    }

    protected void setBiases(Double[][] biases) {
        this.biases = biases;
    }

    //public abstract void forwardPass(ArrayList<Double> input);
    //public abstract void backwardPass();
}
