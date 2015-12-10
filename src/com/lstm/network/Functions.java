package com.lstm.network;

public final class Functions {

    private Functions(){} //private empty constructor for non-instanciation
    
    public static double f(double z){
        return 1 / (1 + Math.exp(-z));
    }
    
    public static double df(double z){
        double ez = Math.exp(z);
        return ez / ((ez + 1) * (ez + 1));
    }
    
    public static double g(double z){
        return z; //To verify, but written at the end of paragraph 3.2
    }
        
    public static double dg(double z){
        return 1;
    }
    
    public static double squashOutput(double z){
        return 4 * (f(z) - 0.5); //To verify, but written at the end of paragraph 3.2
    }
    
    public static double dsquashOutput(double z){
        return 4 * df(z);
    }
    
}
