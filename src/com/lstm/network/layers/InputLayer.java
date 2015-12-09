package com.lstm.network.layers;

import java.util.ArrayList;

import com.lstm.datastructures.DerivativeCache;

/**
 * Created by Wang on 11/15/2015.
 */
public class InputLayer extends Layer {

    public InputLayer() {

    }

    /* Loop through each element of the example and save the corresponding element into the table mapping */
    public void forwardPass(DerivativeCache pragmaticCache, ArrayList<Double> example) {
        int inputIndex = 0;

        for (double element : example)
        {
            pragmaticCache.storeInputNodeOutput(inputIndex, element);
            inputIndex++;
        }
    }

    public void backwardPass() {

    }



}
