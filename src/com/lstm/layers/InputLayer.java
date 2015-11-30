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

    /* Loop through each element of the example and save the corresponding element into the table mapping */
    public void forwardPass(ArrayList<Double> example, Double[][] outputValues) {
        int row = 0;

        // each element has its own corresponding input node
        for (double element : example)
        {
            for (int column = 0; column < outputValues[row].length; column++) {
                outputValues[row][column] = element;
            }
            row++;
        }
    }

    public void backwardPass() {

    }



}
