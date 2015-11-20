package com.lstm.node;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Wang on 11/15/2015.
 */
public class MemoryBlock extends Node {
    private Gate inputGate;
    private Gate forgetGate;
    private Gate outputGate;

    private Cell[] cells;
    private double[] peepholes;

    private ArrayList<Double> inputs;

    public MemoryBlock(int numCells)
    {
        cells = new Cell[numCells];     // cells.length should be 1
        peepholes = new double[3];
        inputs = new ArrayList<>(0);
    }

    public void AddToInputs(double value)
    {
        inputs.add(value);
    }

    private void calculateinputGateActivation()
    {


        double cec = 0;

        for (Cell cell : cells)
        {
            cec += cell.CellState;
        }


    }

    @Override
    public void input(ArrayList<Double> inputWeights, double bias) {
        double sumWeights = 0;
        for (double weight : inputWeights)
        {
            sumWeights += weight;
        }
        sumWeights += bias;


    }

    @Override
    public void activation(double value) {

    }

    @Override
    public double output() {
        return 0;
    }
}
