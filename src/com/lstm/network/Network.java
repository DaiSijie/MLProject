package com.lstm.network;

import java.util.ArrayList;

import com.lstm.node.MemoryBlock;
import com.lstm.node.Node;

/**
 * Created by Wang on 11/15/2015.
 */
public class Network {

    private ArrayList<Node> inputLayer;
    private ArrayList<MemoryBlock> hiddenLayer;
    private ArrayList<Node> outputLayer;

    public Network(int numInput, int numHidden, int numOutput)
    {
        inputLayer = new ArrayList<>(numInput);     // = 1
        hiddenLayer = new ArrayList<>(numHidden);   // = 1
        outputLayer = new ArrayList<>(numOutput);   // = 1
    }




}
