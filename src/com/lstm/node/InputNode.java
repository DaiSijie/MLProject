package com.lstm.node;

import java.util.ArrayList;

/**
 * Created by Wang on 11/19/2015.
 */
public class InputNode {

    private double nodeValue;

    public InputNode(double value)
    {
        nodeValue = value;
    }

    public double Output() {
        return nodeValue;
    }
}
