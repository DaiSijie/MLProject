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
        cells = new Cell[numCells];     // cells.Size default should be 1
    }


    @Override
    public void input(double[] inputVector) {
        double sum = 0;
        for (double element : inputVector)
        {
            sum += element;
        }
    }

    @Override
    public void activation() {

    }

    @Override
    public double output() {
        return 0;
    }
}
