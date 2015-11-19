package com.lstm.node;

/**
 * Created by Wang on 11/15/2015.
 */
public abstract class Node {
    public abstract void input(double[] inputVector);
    public abstract void activation();
    public abstract double output();
}
