package com.lstm.layers;

import com.lstm.node.InputNode;
import com.lstm.node.Node;

import java.util.ArrayList;

/**
 * Created by Wang on 11/15/2015.
 */
public class InputLayer extends Layer {

    public ArrayList<InputNode> inputNodes;

    public InputLayer(int numRows, int numColumns) {
        super(numRows, numColumns);
    }

    public void forwardPass(ArrayList<Double> input) {
        inputNodes = new ArrayList<>(input.size());

        for (double value : input)
        {
            inputNodes.add(new InputNode(value));
        }
    }

    public void backwardPass() {

    }



}
