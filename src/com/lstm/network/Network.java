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
    private Double[] biases;            // for storing the biases for the units in the hidden and output layers
    private Double[][] outputValues;    // for passing the values between the units/layers

    private double alpha;               // learning rate

    private int numInput;
    private int numMemBlock;
    private int numOutput;
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

    InputLayer inputLayer;
    HiddenLayer hiddenLayer;
    OutputLayer outputLayer;

    public Network(int numIn, int numHidden, int numOut, double learningRate)
    {
        numInput = numIn;
        numMemBlock = numHidden;
        numOutput = numOut;

        biases = new Double[(numMemBlock * 4) + numOutput];
        weights = new Double[numInput + (numMemBlock * 4) + numOutput][(numMemBlock * 4) + numOutput];
        outputValues = new Double[numInput + (numMemBlock * 4) + numOutput][(numMemBlock * 4) + numOutput];

        inputLayer = new InputLayer();
        hiddenLayer = new HiddenLayer(numMemBlock);
        outputLayer = new OutputLayer();

        alpha = learningRate;

        initializeWeights();
    }

    // TODO initialize weights matrix
    private void initializeWeights()
    {

    }

    public void train(ArrayList<Double> example){
        inputLayer.forwardPass(example, outputValues);
        hiddenLayer.forwardPass(weights, biases, outputValues, numInput);
        outputLayer.forwardPass(weights, biases, outputValues, numOutput);
    }

    public void classify(){

    }



}
