package com.lstm.network;

import java.util.ArrayList;

import com.lstm.network.layers.HiddenLayer;
import com.lstm.network.layers.InputLayer;
import com.lstm.network.layers.OutputLayer;

/**
 * Created by Wang on 11/15/2015.
 */
public class Network {
    private PragmaticCache pragmaticCache;

    private double alpha;               // learning rate

    InputLayer inputLayer;
    HiddenLayer hiddenLayer;
    OutputLayer outputLayer;

    public Network(int numIn, int numHidden, int numOut, double learningRate) {
        inputLayer = new InputLayer();
        hiddenLayer = new HiddenLayer(numHidden);
        outputLayer = new OutputLayer();

        new PragmaticCache(numIn, numHidden, numOut);

        alpha = learningRate;

        initializeWeights();
    }

    // TODO initialize weights matrix
    private void initializeWeights() {

    }

    public void train(ArrayList<Double> example) {
        inputLayer.forwardPass(pragmaticCache, example);
        hiddenLayer.forwardPass(pragmaticCache);
        outputLayer.forwardPass(pragmaticCache);

        outputLayer.backwardPass();
        hiddenLayer.backwardPass();
        inputLayer.backwardPass();
    }

    public void classify() {

    }

    /*public double[] returnPeepholeConnections(int block_j, int gate) {
        return hiddenLayer.getMemoryBlocks().get(block_j);
    }*/


    private double g(double z) {
        return 1 / (1 + Math.exp(-z));
    }

    // Todo check that formula is correct
    private double dg(double z) {
        return -Math.exp(-z) / ((1 - Math.exp(-z)) * (1 - Math.exp(-z)));
    }



    private void initDerivatives() {
        for (int j = 0; j < pragmaticCache.getNumMemBlock(); j++) {
            pragmaticCache.storeCellDerivative(j, 0);
            pragmaticCache.storeInputGateDerivativeA(j, 0);
            pragmaticCache.storeForgetGateDerivativeA(j, 0);

            int numPeephole = 3;
            for (int vrpime = 0; vrpime < numPeephole; vrpime++) {
                pragmaticCache.storeInputGateDerivativeB(j, vrpime, 0);
                pragmaticCache.storeForgetGateDerivativeB(j, vrpime, 0);
            }
        }
    }

    private void updateDerivatives() {
        for (int j = 0; j < pragmaticCache.getNumMemBlock(); j++) {
            updateCellDerivatives(j);
            updateInputGatesDerivatives(j);
            updateForgetGatesDerivatives(j);
        }
    }

    private void updateCellDerivatives(int j) {
        double oldValue = pragmaticCache.getCellDerivative(j);

        //vars are in the same order as they appear in the formula
        double a = 1; //to fetch from array?
        double b = 1; //to fetch from array?
        double c = 1; //to fetch from array?
        double d = 1; //to fetch from array?

        double newValue = oldValue * a + dg(b) * c * d;

        pragmaticCache.storeCellDerivative(j, newValue);
    }

    private void updateInputGatesDerivatives(int j) {
        //first step: update the general
        double oldValue = pragmaticCache.getInputGateDerivativeA(j);

        //vars are in the same order as they appear in the formula
        double a = 1; //to fetch from array?
        double b = 1; //to fetch from array?
        double c = 1; //to fetch from array?
        double d = 1; //to fetch from array?

        double newValue = oldValue * a + g(b) * dg(c) * d;

        pragmaticCache.storeInputGateDerivativeA(j, newValue);

        //second step: loop over peephole and update the specifics
        int numPeephole = 3;
        for (int vprime = 0; vprime < numPeephole; vprime++) {
            oldValue = pragmaticCache.getInputGateDerivativeB(j, vprime);

            //vars are in the same order as they appear in the formula
            a = 1; //to fetch from array?
            b = 1; //to fetch from array?
            c = 1; //to fetch from array?
            d = 1; //to fetch from array?

            newValue = oldValue * a + g(b) * dg(c) * d;

            pragmaticCache.storeInputGateDerivativeB(j, vprime, newValue);
        }
    }

    private void updateForgetGatesDerivatives(int j) {
        //first step: update the general
        double oldValue = pragmaticCache.getForgetGateDerivativeA(j);

        //vars are in the same order as they appear in the formula
        double a = 1; //to fetch from array?
        double b = 1; //to fetch from array?
        double c = 1; //to fetch from array?
        double d = 1; //to fetch from array?

        double newValue = oldValue * a + b * dg(c) * d;

        pragmaticCache.storeForgetGateDerivativeA(j, newValue);

        //second step: loop over peephole and update the specifics
        int numPeephole = 3;
        for (int vprime = 0; vprime < numPeephole; vprime++) {
            oldValue = pragmaticCache.getForgetGateDerivativeB(j, vprime);

            //vars are in the same order as they appear in the formula
            a = 1; //to fetch from array?
            b = 1; //to fetch from array?
            c = 1; //to fetch from array?
            d = 1; //to fetch from array?

            newValue = oldValue * a + b * dg(c) * d;

            pragmaticCache.storeForgetGateDerivativeB(j, vprime, newValue);
        }
    }
}



