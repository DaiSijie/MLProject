package com.lstm.network;

import java.util.ArrayList;

import com.lstm.layers.HiddenLayer;
import com.lstm.layers.InputLayer;
import com.lstm.node.MemoryBlock;
import com.lstm.node.Node;

/**
 * Created by Wang on 11/15/2015.
 */
public class Network {

    int numInput;
    InputLayer inputLayer;
    HiddenLayer hiddenLayer;

    //private ArrayList<Node> inputLayer;
    //private ArrayList<MemoryBlock> hiddenLayer;
    //private ArrayList<Node> outputLayer;

    public Network(int numInput, int numHidden, int numOutput)
    {
        //inputLayer = new ArrayList<>(numInput);     // = 1
        //hiddenLayer = new ArrayList<>(numHidden);   // = 1
        //outputLayer = new ArrayList<>(numOutput);   // = 1
        inputLayer = new InputLayer(numInput, (numHidden * 4) + numOutput);
        hiddenLayer = new HiddenLayer(numHidden);
    }

    public void train(ArrayList<Double> example){
        inputLayer.forwardPass(example);
        hiddenLayer.forwardPass(inputLayer.inputNodes);
    }

    public void classify(){

    }



}
