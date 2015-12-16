/*
 *	Author:      Gilbert Maystre
 *	Date:        Dec 13, 2015
 */

package com.lstm.datastructures;

import java.util.Arrays;
import java.util.HashMap;

import com.lstm.network.NetworkDescription;

public class ForwardPassCache {

    private final int numInput;
    private final int numSource;
    
    //WEIGHTS
    private final HashMap<Integer, HashMap<Integer, Double>> weightF;
    private final HashMap<Integer, Double> weightFToCell;
    private final HashMap<Integer, Double> fBias;
    private final HashMap<Integer, HashMap<Integer, Double>> weightIn;
    private final HashMap<Integer, Double> weightInToCell;
    private final HashMap<Integer, Double> inBias;
    private final HashMap<Integer, HashMap<Integer, Double>> weightOut;
    private final HashMap<Integer, Double> weightOutToCell;
    private final HashMap<Integer, Double> outBias;
    private final HashMap<Integer, HashMap<Integer, Double>> weightCell;
    private final HashMap<Integer, Double> cellBias;
    private final HashMap<Integer, HashMap<Integer, Double>> weightBaked;
    private final HashMap<Integer, Double> bakedBias;
    
    //NETS
    private final HashMap<Integer, Double> netIn;
    private final HashMap<Integer, Double> netF;
    private final HashMap<Integer, Double> netOut;
    private final HashMap<Integer, Double> netCell;
    private final HashMap<Integer, Double> netBaked;
    
    //Y
    private final HashMap<Integer, Double> yF;
    private final HashMap<Integer, Double> yOut;
    private final HashMap<Integer, Double> yIn;
    private final HashMap<Boolean, HashMap<Integer, Double>> yCell;
    private final HashMap<Integer, Double> yBaked;
    
    //CELLS
    private final HashMap<Boolean, HashMap<Integer, Double>> cellState;
         
    //INPUT
    private double[] input;
    
    public void printWeights(){
        System.out.println("For weight out, first mem block");
        for(int m = 0; m < numSource; m++){
            System.out.print(this.getWeightOut(0, m) + ", ");
        }
        System.out.println("\n\n");
    }
    
    
    public ForwardPassCache(NetworkDescription description){
        this.numInput = description.numInput;
        this.numSource = description.numSource;
        
        //WEIGHTS
        this.weightF = new HashMap<>();
        this.weightFToCell = new HashMap<>();
        this.fBias = new HashMap<>();
        this.weightIn = new HashMap<>();
        this.weightInToCell = new HashMap<>();
        this.inBias = new HashMap<>();
        this.weightOut = new HashMap<>();
        this.weightOutToCell = new HashMap<>();
        this.outBias = new HashMap<>();
        this.weightCell = new HashMap<>();
        this.cellBias = new HashMap<>();
        this.weightBaked = new HashMap<>();
        this.bakedBias = new HashMap<>();
        
        //NETS
        this.netIn = new HashMap<>();
        this.netF = new HashMap<>();
        this.netOut = new HashMap<>();
        this.netCell = new HashMap<>();
        this.netBaked = new HashMap<>();
        
        //Y
        this.yF = new HashMap<>();
        this.yOut = new HashMap<>();
        this.yIn = new HashMap<>();
        this.yCell = new HashMap<>();
        this.yBaked = new HashMap<>();
        
        //CELLS
        this.cellState = new HashMap<>();
    }
    
    
    /*
     * BEGIN WEIGHTS
     */
    public double getWeightF(int j, int m){
        return weightF.get(j).get(m);
    }
    
    public void storeWeightF(int j, int m, double value){
        if(!weightF.containsKey(j))
            weightF.put(j, new HashMap<Integer, Double>());
        
        weightF.get(j).put(m, value);
    }
    
    public double getWeightFToCell(int j){
        return weightFToCell.get(j);
    }
    
    public void storeWeightFToCell(int j, double value){
        weightFToCell.put(j, value);
    }

    public double getBiasF(int j){
        return fBias.get(j);
    }
    
    public void storeBiasF(int j, double value){
       fBias.put(j, value) ;
    }
    

    public double getWeightIn(int j, int m){
        return weightIn.get(j).get(m);
    }
    
    public void storeWeightIn(int j, int m, double value){
        if(!weightIn.containsKey(j))
            weightIn.put(j, new HashMap<Integer, Double>());
        
        weightIn.get(j).put(m, value);
    }

    public double getWeightInToCell(int j){
        return weightInToCell.get(j);
    }
    
    public void storeWeightInToCell(int j, double value){
        weightInToCell.put(j, value);
    }

    public double getBiasIn(int j){
        return inBias.get(j);
    }
    
    public void storeBiasIn(int j, double value){
        inBias.put(j, value);
    }
    
        
    public double getWeightOut(int j, int m){
        return weightOut.get(j).get(m);
    }
    
    public void storeWeightOut(int j, int m, double value){
        if(!weightOut.containsKey(j))
            weightOut.put(j, new HashMap<Integer, Double>());
        
        weightOut.get(j).put(m, value);
    }
    
    public double getWeightOutToCell(int j){
        return weightOutToCell.get(j);
    }
    
    public void storeWeightOutToCell(int j, double value){
        weightOutToCell.put(j, value);
    }
    
    public double getBiasOut(int j){
        return outBias.get(j);
    }
    
    public void storeBiasOut(int j, double value){
        outBias.put(j, value);
    }
    
    
    
    public double getWeightCell(int j, int m){
        return weightCell.get(j).get(m);
    }
   
    public void storeWeightCell(int j, int m, double value){
        if(!weightCell.containsKey(j))
            weightCell.put(j, new HashMap<Integer, Double>());
        
        weightCell.get(j).put(m, value);
    }
    
    public double getBiasCell(int j){
        return cellBias.get(j);
    }
    
    public void storeBiasCell(int j, double value){
        cellBias.put(j, value);
    }
    
    
    public double getWeightBaked(int k, int m){
        return weightBaked.get(k).get(m);
    }
    
    public void storeWeightBaked(int k, int m, double value){
        if(!weightBaked.containsKey(k))
            weightBaked.put(k, new HashMap<Integer, Double>());
        
        weightBaked.get(k).put(m, value);
    }
   
    public double getBakedBias(int k){
        return bakedBias.get(k);
    }
    
    public void storeBakedBias(int k, double value){
        bakedBias.put(k, value);
    }
    
    /*
     * END WEIGHTS
     */
    
    
    /*
     * BEGIN NETS
     */
    
    public double getNetIn(int j){
        return netIn.get(j);
    }
    
    public void storeNetIn(int j, double value){
        netIn.put(j, value);
    }
    
    public double getNetF(int j){
        return netF.get(j);
    }
    
    public void storeNetF(int j, double value){
        netF.put(j, value);
    }
    
    public double getNetOut(int j){
        return netOut.get(j);
    }
    
    public void storeNetOut(int j, double value){
        netOut.put(j, value);
    }
    
    public double getNetCell(int j){
        return netCell.get(j);
    }
    
    public void storeNetCell(int j, double value){
        netCell.put(j, value);
    }
    
    public double getNetBaked(int k){
        return netBaked.get(k);
    }
    
    public void storeNetBaked(int k, double value){
        netBaked.put(k, value);
    }
    
    /*
     * END NETS
     */

    
    /*
     * BEGIN Y
     */
    
    public double getYF(int j){
        return yF.get(j);
    }
    
    public void storeYF(int j, double value){
        yF.put(j, value);
    }
    
    public double getYOut(int j){
        return yOut.get(j);
    }
    
    public void storeYOut(int j, double value){
        yOut.put(j, value);
    }
    
    public double getYIn(int j){
        return yIn.get(j);
    }
    
    public void storeYIn(int j, double value){
        yIn.put(j, value);
    }
    
    public double getYCell(boolean hat, int j){
        return yCell.get(hat).get(j);
    }
    
    public void storeYCell(boolean hat, int j, double value){
        if(!yCell.containsKey(hat))
            yCell.put(hat, new HashMap<Integer, Double>());
        
        yCell.get(hat).put(j, value);
    }
    
    public double getYBaked(int k){
        return yBaked.get(k);
    }
    
    public void storeYBaked(int k, double value){
        yBaked.put(k, value);
    }
    
    public double smartGetYM(boolean hat, int m){
        if(0 <= m && m < numInput){
            return input[m];
        }
        else if(m <= numInput && m < numSource){
            return getYCell(hat, m - numInput);
        }
        else{
            throw new IllegalArgumentException("Invalid m passed to smart y hat");
        }
    }
        
    /*
     * END Y
     */
    
    
    /*
     * BEGIN CELLS
     */
    
    public double getCellState(boolean hat, int j){
        return cellState.get(hat).get(j);
    }
    
    public void storeCellState(boolean hat, int j, double value){
        if(!cellState.containsKey(hat))
            cellState.put(hat, new HashMap<Integer, Double>());
        
        cellState.get(hat).put(j, value);
    }

    public double getCellPeephole(boolean hat, int j, int vprime){
        return getCellState(hat, j);
    }
    
    /*
     * END CELLS
     */

    /*
     * BEGIN INPUT
     */
    
    public void storeInput(double[] newInput){
        input = Arrays.copyOf(newInput, newInput.length);
    }
    
    public double[] getInput(){
        return Arrays.copyOf(input, input.length);
    }
    
    /*
     * END INPUT
     */

}
