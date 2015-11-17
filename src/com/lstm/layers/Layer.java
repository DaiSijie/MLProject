package com.lstm.layers;

import java.util.ArrayList;

import com.lstm.node.Node;

/**
 * Created by Wang on 11/15/2015.
 */
public abstract class Layer {
    protected ArrayList<Double> input;
    protected ArrayList<Node> nodes;
    protected double output;

    public abstract void forwardPass();
    public abstract void backwardPass();
}
