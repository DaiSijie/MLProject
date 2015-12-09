package com.lstm.network;

public final class Functions {

    private Functions(){
        
    }
    
    public static double f(double z){
        return 1 / (1 + Math.exp(-z));
    }
    
    public static double g(double z){
        return 0;
    }
    
    public static double df(double z){
        return 0;
    }
    
    public static double dg(double z){
        return 0;
    }
    
}
