package com.lstm.network;

import java.util.ArrayList;

import com.lstm.layers.HiddenLayer;
import com.lstm.layers.InputLayer;
import com.lstm.layers.OutputLayer;
import com.lstm.node.MemoryBlock;
import com.lstm.node.Node;

/**
 * Created by Wang on 11/15/2015.
 */
public class Network {
    private Double[][] weights;         // for storing the weights between units
    private Double[][] biases;          // for storing the biases for the units in the hidden and output layers
    private Double[][] outputValues;    // for passing the values between the units/layers

    /*
        Weights/OutputValues (10 x 7):
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

    InputLayer inputLayer;
    HiddenLayer hiddenLayer;
    OutputLayer outputLayer;

    public Network(int numInput, int numMemoryBlock, int numOutput)
    {
        weights = new Double[numInput + (numMemoryBlock * 4) + numOutput][(numMemoryBlock * 4) + numOutput];
        biases = new Double[1][(numMemoryBlock * 4) + numOutput];
        outputValues = new Double[numInput + (numMemoryBlock * 4) + numOutput][(numMemoryBlock * 4) + numOutput];

        inputLayer = new InputLayer();
        hiddenLayer = new HiddenLayer(numMemoryBlock);
        outputLayer = new OutputLayer();
    }

    public void train(ArrayList<Double> example){
        inputLayer.forwardPass(example, outputValues);
        hiddenLayer.forwardPass(weights, biases, outputValues);
        outputLayer.forwardPass(weights, biases, outputValues);
    }

    public void classify(){

    }



}
