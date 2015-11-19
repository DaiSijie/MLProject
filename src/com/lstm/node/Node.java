package com.lstm.node;

import java.util.ArrayList;

/**
 * Created by Wang on 11/15/2015.
 */
public abstract class Node {
    public abstract void input(ArrayList<Double> inputWeights, double bias);
    public abstract void activation(double value);
    public abstract double output();
}
