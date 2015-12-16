/*
 *	Author:      Gilbert Maystre
 *	Date:        Dec 13, 2015
 */

package com.lstm.training;

import com.lstm.datastructures.ForwardPassCache2;
import com.lstm.network.NetworkDescription;

import static com.lstm.network.Functions.*;

import java.util.Random;

public class ForwardPass2 {

    private final int numInput;
    private final int numSource;
    private final int numMem;
    
    private final ForwardPassCache2 cache;
    
    public ForwardPass2(NetworkDescription desc, ForwardPassCache2 cache){
        this.cache = cache;
        
        this.numInput = desc.numInput;
        this.numSource = desc.numSource;
        this.numMem = desc.numMemBlock;
        
    }
    
    private final static Random rand = new Random(1995);
    
    private double next(){
        return rand.nextDouble() * 0.2 - 0.1;
    }
    
    public void init(){
        //weights
        for(int j = 0; j < numMem; j++){
            for(int m = 0; m < numSource; m++){
                cache.storeWeightF(j, m, next());
                cache.storeWeightIn(j, m, next());
                cache.storeWeightOut(j, m, next());
                cache.storeWeightCell(j, m, next());
            }
            
            cache.storeWeightFToCell(j, next());
            cache.storeWeightInToCell(j, next());
            cache.storeWeightOutToCell(j, next());

            cache.storeBiasF(j, +2.0);
            cache.storeBiasOut(j, -2.0);
            cache.storeBiasIn(j, -1.0);
            cache.storeBiasCell(j, next());

            cache.storeCellState(true, j, 0);
            cache.storeCellState(false, j, 0);
            
            cache.storeYCell(true, j, 0);
            cache.storeYCell(false, j, 0);
        }
        
        for(int k = 0; k < numInput; k++){
            for(int m = 0; m < numSource; m++){
                cache.storeWeightBaked(k, m, next());
            }
            cache.storeBakedBias(k, next());
        }
    }
    
    public void doRound(double[] newInput){
        cache.storeInput(newInput);
    
        //roll over
        for(int j = 0; j < numMem; j++){
            cache.storeYCell(true, j, cache.getYCell(false, j));
            cache.storeCellState(true, j, cache.getCellState(false, j));
        }
        
        for(int j = 0; j < numMem; j++){
            step1A(j);
            step1B(j);
            step1C(j);
            step2A(j);
            step2B(j);
        }
        step3();
    }
    
    private void step1A(int j){
        double sum = 0;
        for(int m = 0; m < numSource; m++){
            sum += cache.getWeightIn(j, m) * cache.smartGetYM(true, m);
        }
        
        //the bias
        sum += cache.getBiasIn(j);
                
        //the infamous peephole
        sum += cache.getWeightInToCell(j) * cache.getCellState(true, j);
        
        cache.storeNetIn(j, sum);
        cache.storeYIn(j, f(sum));
    }
    
    private void step1B(int j){
        double sum = 0;
        for(int m = 0; m < numSource; m++){
            sum += cache.getWeightF(j, m) * cache.smartGetYM(true, m);
        }
        
        //the bias
        sum += cache.getBiasF(j);
        
        //the infamous peephole
        sum += cache.getWeightFToCell(j) * cache.getCellState(true, j);
        
        cache.storeNetF(j, sum);
        cache.storeYF(j, f(sum));
    }
    
    private void step1C(int j){
        double sum = 0;
        for(int m = 0; m < numSource; m++){
            sum += cache.getWeightCell(j, m) * cache.smartGetYM(true, m);
        }
        
        //bias
        sum += cache.getBiasCell(j);
        
        cache.storeNetCell(j, sum);
        
        double cellState = (cache.getYF(j) * cache.getCellState(true, j)) + (cache.getYIn(j) * g(sum));
        cache.storeCellState(false, j, cellState);
    }
    
    private void step2A(int j){
        double sum = 0;
        
        for(int m = 0; m < numSource; m++){
            sum += cache.getWeightOut(j, m) * cache.smartGetYM(true, m);
        }
        
        //bias
        sum += cache.getBiasOut(j);
        
        //the infamous peephole
        sum += cache.getWeightOutToCell(j) * cache.getCellState(false, j);
        
        cache.storeNetOut(j, sum);
        cache.storeYOut(j, f(sum));
        

    }
    
    private void step2B(int j){
        double yCell = cache.getYOut(j) * cache.getCellState(false, j);
        
        cache.storeYCell(false, j, yCell);
    }
    
    private void step3(){
        for(int k = 0; k < numInput; k++){
            double sum = 0;
            for(int m = 0; m < numSource; m++){
                sum += cache.getWeightBaked(k, m) * cache.smartGetYM(false, m);
            }
            
            //bias
            sum += cache.getBakedBias(k);
            
            cache.storeNetBaked(k, sum);
            cache.storeYBaked(k, f(sum));
        }
    }

}
