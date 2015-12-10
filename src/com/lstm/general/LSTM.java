package com.lstm.general;

import com.lstm.generator.ComplicatedCFLGenerator;
import com.lstm.generator.Generator;
import com.lstm.generator.NoiseAdder;
import com.lstm.generator.SimpleCFLGenerator;
import com.lstm.generator.SimpleCSLGenerator;

public class LSTM {

    public static void main(String[] args){
        
        //int experiment = new Integer(args[0]);
        
        /*
         * Parse arg of the program: experiment 1 (easy CFL), 2 (hard CFL) or 3 (CSL)
         * 
         * Setup the Network and the Generator
         * 
         * Train the network on the generator
         * 
         * Test the trained network on a larger dataset. Output results
         */
        
        System.out.println("Examples: \n");
        
        System.out.println("Simple CFL a^n b^n with limit n = 3");
        Generator g1 = new SimpleCFLGenerator(3);
        while(g1.hasNext()){
            System.out.print(g1.getNext());
        }
        
        System.out.println("\n");
        
        System.out.println("Simple CSL a^n b^n c^n with limit n = 3");
        Generator g2 = new SimpleCSLGenerator(3);
        while(g2.hasNext()){
            System.out.print(g2.getNext());
        }
        
        
        
//        System.out.println("Simple CFL a^n b^n");
//        Generator g1 = new SimpleCFLGenerator(START, new Symbol("a"), new Symbol("b"), END, 10);
//        for(Symbol s : g1)
//            System.out.print(s.getName());
//        
//        System.out.println("\n");
//        
//        System.out.println("Complicated CFL a^n b^m B^m A^n");
//        Generator g2 = new ComplicatedCFLGenerator(START, new Symbol("a"), new Symbol("b"), new Symbol("B"), new Symbol("A"), END, 2, 3);
//        for(Symbol s : g2)
//            System.out.print(s.getName());
//        
//        System.out.println("\n");
//        
//        System.out.println("Simple CSL a^n b^n c^n");
//        Generator g3 = new SimpleCSLGenerator(START, new Symbol("a"), new Symbol("b"), new Symbol("c"), END, 10);
//        for(Symbol s : g3)
//            System.out.print(s.getName());
//        
//        System.out.println("\n");
//        
//        System.out.println("Noise (symbol flipped with p = 0.2) on CFL a^n b^n");
//        Generator g4 = new NoiseAdder(new SimpleCFLGenerator(START, new Symbol("a"), new Symbol("b"), END, 10), 0.2);
//        for(Symbol s : g4)
//            System.out.print(s.getName());
//        
//        System.out.println("\n");
//        
//        System.out.println("Noise (symbol flipped with p = 0.5) on CFL a^n b^m B^m A^n");
//        Generator g5 = new NoiseAdder(new ComplicatedCFLGenerator(START, new Symbol("a"), new Symbol("b"), new Symbol("B"), new Symbol("A"), END, 2, 3), NoiseAdder.hard);
//        for(Symbol s : g5)
//            System.out.print(s.getName());
    }
    
}
