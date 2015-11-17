package com.lstm.node;

/**
 * Created by Wang on 11/15/2015.
 */
public class Gate extends Node {

    @Override
    public void input(double[] input) {

    }

    @Override
    public void activation() {
        //logistic sigmoid, range [0,1]
    }

    @Override
    public double output() {
        return 0;
    }
}
