package com.lstm.network.layers;

import java.util.ArrayList;

import com.lstm.datastructures.DerivativeCache;

/**
 * Created by Wang on 11/15/2015.
 */
public class OutputLayer {

    public void forwardPass(DerivativeCache pragmaticCaches) {
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
