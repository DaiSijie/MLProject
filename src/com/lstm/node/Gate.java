package com.lstm.node;

import java.util.ArrayList;

/**
 * Created by Wang on 11/15/2015.
 */
public class Gate extends Node {

    private double activationValue;

    @Override
    public void input(ArrayList<Double> inputWeights, double bias) {

    }

    public void inputCells()
    {

    }

    @Override
    public void activation(double value) {
        activationValue = 1 / (1 + Math.exp(-value));
    }



    @Override
    public double output() {
        return 0;
    }
}
