package com.lstm.layers;

import com.lstm.node.InputNode;
import com.lstm.node.Node;

import java.util.ArrayList;

/**
 * Created by Wang on 11/15/2015.
 */
public class InputLayer extends Layer {

    public InputLayer() {

    }

    public void forwardPass(ArrayList<Double> example, Double[][] outputValues) {
        int i = 0;

        for (double element : example)
        {
            for (int j = 0; j < outputValues[i].length; j++) {
                outputValues[i][j] = element;
            }
            i++;
        }
    }

    public void backwardPass() {

    }



}
