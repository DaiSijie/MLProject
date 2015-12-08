package com.lstm.network.layers;

import com.lstm.network.PragmaticCache;

import java.util.ArrayList;

/**
 * Created by Wang on 11/15/2015.
 */
public class InputLayer extends Layer {

    public InputLayer() {

    }

    /* Loop through each element of the example and save the corresponding element into the table mapping */
    public void forwardPass(PragmaticCache pragmaticCache, ArrayList<Double> example) {
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
