package com.lstm.datastructures;

import java.util.HashMap;

/**
 * Created by Wang on 12/6/2015.
 */
public class DerivativeCache {
    private final HashMap<Integer, Double> cellDerivative;

    private final HashMap<Integer, Double> inputGateDerivativeA;
    private final HashMap<Integer, HashMap<Integer, Double>> inputGateDerivativeB;

    private final HashMap<Integer, Double> forgetGateDerivativeA;
    private final HashMap<Integer, HashMap<Integer, Double>> forgetGateDerivativeB;

    public DerivativeCache() {
        this.cellDerivative = new HashMap<>();

        this.inputGateDerivativeA = new HashMap<>();
        this.inputGateDerivativeB = new HashMap<>();

        this.forgetGateDerivativeA = new HashMap<>();
        this.forgetGateDerivativeB = new HashMap<>();
    }

    public double getCellDerivative(int j) {
        return cellDerivative.get(j);
    }

    public double getInputGateDerivativeA(int j) {
        return inputGateDerivativeA.get(j);
    }

    public double getInputGateDerivativeB(int j, int vprime) {
        return inputGateDerivativeB.get(j).get(vprime);
    }

    public double getForgetGateDerivativeA(int j) {
        return forgetGateDerivativeA.get(j);
    }

    public double getForgetGateDerivativeB(int j, int vprime) {
        return forgetGateDerivativeB.get(j).get(vprime);
    }

    public void storeCellDerivative(int j, double value) {
        cellDerivative.put(j, value);
    }

    public void storeInputGateDerivativeA(int j, double value) {
        inputGateDerivativeA.put(j, value);
    }

    public void storeInputGateDerivativeB(int j, int vprime, double value) {
        if (!inputGateDerivativeB.containsKey(j))
            inputGateDerivativeB.put(j, new HashMap<Integer, Double>());

        inputGateDerivativeB.get(j).put(vprime, value);
    }

    public void storeForgetGateDerivativeA(int j, double value) {
        forgetGateDerivativeA.put(j, value);
    }

    public void storeForgetGateDerivativeB(int j, int vprime, double value) {
        if (!forgetGateDerivativeB.containsKey(j))
            forgetGateDerivativeB.put(j, new HashMap<Integer, Double>());

        forgetGateDerivativeB.get(j).put(vprime, value);
    }

}
