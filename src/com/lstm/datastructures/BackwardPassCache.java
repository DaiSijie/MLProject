/*
 *	Author:      Gilbert Maystre
 *	Date:        Dec 9, 2015
 */

package com.lstm.datastructures;

import java.util.HashMap;
import java.util.Map;

public class BackwardPassCache {
    
    //deltas
    private final Map<Integer, Double> delta;
    private final Map<Integer, Double> deltaOut;
    private final Map<Integer, Double> internalStateError;
    
    //weights
    private final Map<Integer, HashMap<Integer, Double >> outputUnit;
    
    private final Map<Integer, HashMap<Integer, Double>> outputGate;
    private final Map<Integer, Double> outputGateC;
    
    private final Map<Integer, HashMap<Integer, Double>> inputGate;
    private final Map<Integer, HashMap<Integer, Double>> inputGateC;
    
    private final Map<Integer, HashMap<Integer, Double>> forgetGate;
    private final Map<Integer, HashMap<Integer, Double>> forgetGateC;
    
    private final Map<Integer, HashMap<Integer, Double>> cell;
    
    public BackwardPassCache(){
        this.delta = new HashMap<>();
        this.deltaOut = new HashMap<>();
        this.internalStateError = new HashMap<>();
        
        this.outputUnit = new HashMap<>();
        this.outputGate = new HashMap<>();
        this.outputGateC = new HashMap<>();
        this.inputGate = new HashMap<>();
        this.inputGateC = new HashMap<>();
        this.forgetGate = new HashMap<>();
        this.forgetGateC = new HashMap<>();
        this.cell = new HashMap<>();
    }
    
    public void storeCell(int j, int m, double value){
        if(!cell.containsKey(j))
            cell.put(j, new HashMap<>());
        
        cell.get(j).put(m, value);
    }
    
    public double getCell(int j, int m){
        return cell.get(j).get(m);
    }
    
    public void storeForgetGate(int j, int m, double value){
        if(!forgetGate.containsKey(j))
            forgetGate.put(j, new HashMap<>());
        
        forgetGate.get(j).put(m, value);
    }
    
    public double getForgetGate(int j, int m){
        return forgetGate.get(j).get(m);
    }
    
    public void storeForgetGateC(int j, int vprime, double value){
        if(!forgetGateC.containsKey(j))
            forgetGateC.put(j, new HashMap<>());
        
       forgetGateC.get(j).put(vprime, value);
    }
    
    public double getforgetGateC(int j, int vprime){
        return forgetGateC.get(j).get(vprime);
    }
    
    public void storeInputGate(int j, int m, double value){
        if(!inputGate.containsKey(j))
            inputGate.put(j, new HashMap<>());
        
        inputGate.get(j).put(m, value);
    }
    
    public double getInputGate(int j, int m){
        return inputGate.get(j).get(m);
    }
    
    public void storeInputGateC(int j, int vprime, double value){
        if(!inputGateC.containsKey(j))
            inputGateC.put(j, new HashMap<>());
        
       inputGateC.get(j).put(vprime, value);
    }
    
    public double getInputGateC(int j, int vprime){
        return inputGateC.get(j).get(vprime);
    }
    
    
    public void storeOutputGateC(int j,double value){
        outputGateC.put(j, value);
    }
    
    public double getOutputGateC(int j){
        return outputGateC.get(j);
    }
    
    
    public void storeOutputGate(int j, int m, double value){
        if(!outputGate.containsKey(j))
            outputGate.put(j, new HashMap<>());
        
        outputGate.get(j).put(m, value);
    }
    
    public double getOutputGate(int j, int m){
        return outputGate.get(j).get(m);
    }
    
    
    public void storeOutputUnit(int k, int m, double value){
        if(!outputUnit.containsKey(k))
            outputUnit.put(k, new HashMap<>());
        
        outputUnit.get(k).put(m, value);
    }
    
    public double getOutputUnit(int k, int m){
        return outputUnit.get(k).get(m);
    }
    
    

    public void storeDelta(int k, double value){
        delta.put(k, value);
    }
    
    public double getDelta(int k){
        return delta.get(k);
    }
    
    public void storeDeltaOut(int j, double value){
        deltaOut.put(j, value);
    }
    
    public double getDeltaOut(int j){
        return deltaOut.get(j);
    }
    
    public void storeInternalStateError(int j, double value){
        internalStateError.put(j, value);
    }
    
    public double getInternalStateError(int j){
        return internalStateError.get(j);
    }
}
