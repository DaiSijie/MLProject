package com.lstm.node;

/**
 * Created by Wang on 11/15/2015.
 */
public class MemoryBlock extends Node {

    private Gate inputGate;
    private Gate forgetGate;
    private Gate outputGate;

    private Cell[] cells;

    public MemoryBlock(int numCells)
    {
        cells = new Cell[numCells];     //default should be 1
    }


    @Override
    public void input(double[] input) {

    }

    @Override
    public void activation() {

    }

    @Override
    public double output() {
        return 0;
    }
}
