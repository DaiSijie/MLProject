package com.lstm.datastructures;

import java.util.HashMap;

public class DerivativeCache {
    private final HashMap<Integer, HashMap<Integer, Double>> cellDerivative;

    private final HashMap<Integer, HashMap<Integer, Double>> inputGateDerivativeA;
    private final HashMap<Integer, HashMap<Integer, Double>> inputGateDerivativeB;

    private final HashMap<Integer, HashMap<Integer, Double>> forgetGateDerivativeA;
    private final HashMap<Integer, HashMap<Integer, Double>> forgetGateDerivativeB;

    public DerivativeCache() {
        this.cellDerivative = new HashMap<>();

        this.inputGateDerivativeA = new HashMap<>();
        this.inputGateDerivativeB = new HashMap<>();

        this.forgetGateDerivativeA = new HashMap<>();
        this.forgetGateDerivativeB = new HashMap<>();
    }

    public double getCellDerivative(int j, int m) {
        return cellDerivative.get(j).get(m);
    }

    public double getInputGateDerivativeA(int j, int m) {
        return inputGateDerivativeA.get(j).get(m);
    }

    public double getInputGateDerivativeB(int j, int vprime) {
        return inputGateDerivativeB.get(j).get(vprime);
    }

    public double getForgetGateDerivativeA(int j, int m) {
        return forgetGateDerivativeA.get(j).get(m);
    }

    public double getForgetGateDerivativeB(int j, int vprime) {
        return forgetGateDerivativeB.get(j).get(vprime);
    }

    public void storeCellDerivative(int j, int m, double value) {
        if(!cellDerivative.containsKey(j))
            cellDerivative.put(j, new HashMap<Integer, Double>());
        
        cellDerivative.get(j).put(m, value);
    }

    public void storeInputGateDerivativeA(int j, int m, double value) {
        if(!inputGateDerivativeA.containsKey(j))
            inputGateDerivativeA.put(j, new HashMap<>());
        
        inputGateDerivativeA.get(j).put(m, value);
    }

    public void storeInputGateDerivativeB(int j, int vprime, double value) {
        if (!inputGateDerivativeB.containsKey(j))
            inputGateDerivativeB.put(j, new HashMap<Integer, Double>());

        inputGateDerivativeB.get(j).put(vprime, value);
    }

    public void storeForgetGateDerivativeA(int j, int m, double value) {
        if(!forgetGateDerivativeA.containsKey(j))
            forgetGateDerivativeA.put(j, new HashMap<>());
        
        forgetGateDerivativeA.get(j).put(m, value);
    }

    public void storeForgetGateDerivativeB(int j, int vprime, double value) {
        if (!forgetGateDerivativeB.containsKey(j))
            forgetGateDerivativeB.put(j, new HashMap<Integer, Double>());

        forgetGateDerivativeB.get(j).put(vprime, value);
    }


}
