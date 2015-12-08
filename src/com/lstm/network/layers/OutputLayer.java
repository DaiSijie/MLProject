package com.lstm.network.layers;

import com.lstm.network.PragmaticCache;

import java.util.ArrayList;

/**
 * Created by Wang on 11/15/2015.
 */
public class OutputLayer {

    public void forwardPass(PragmaticCache pragmaticCaches) {
        for (int i = 0; i < pragmaticCaches.getNumOutput(); i++) {
            double outputNodeInput = squashingFunction(pragmaticCaches.getOutputNodeInput(i));
            pragmaticCaches.storeOutputNodeOutput(i, outputNodeInput);
        }
    }

    public void backwardPass(/*ArrayList<Double> target*/) {

    }

    private double squashingFunction(double input)
    {
        return 1 / (1 + Math.exp(-input));
    }
}
