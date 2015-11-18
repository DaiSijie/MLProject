/*
 *	Author:      Gilbert Maystre
 *	Date:        Nov 16, 2015
 */

package com.lstm.general;

import com.lstm.generator.ComplicatedCFLGenerator;
import com.lstm.generator.Generator;
import com.lstm.generator.NoiseAdder;
import com.lstm.generator.SimpleCFLGenerator;
import com.lstm.generator.SimpleCSLGenerator;
import com.lstm.generator.Symbol;

public class LSTM {

    public static void main(String[] args){
        System.out.println("Examples: \n");
        
        System.out.println("Simple CFL a^n b^n");
        Generator g1 = new SimpleCFLGenerator(new Symbol("s"), new Symbol("a"), new Symbol("b"), 10);
        for(Symbol s : g1)
            System.out.print(s.getName());
        
        System.out.println("\n");
        
        System.out.println("Complicated CFL a^n b^m B^m A^n");
        Generator g2 = new ComplicatedCFLGenerator(new Symbol("s"), new Symbol("a"), new Symbol("b"), new Symbol("B"), new Symbol("A"), 2, 3);
        for(Symbol s : g2)
            System.out.print(s.getName());
        
        System.out.println("\n");
        
        System.out.println("Simple CSL a^n b^n c^n");
        Generator g3 = new SimpleCSLGenerator(new Symbol("s"), new Symbol("a"), new Symbol("b"), new Symbol("c"), 10);
        for(Symbol s : g3)
            System.out.print(s.getName());
        
        System.out.println("\n");
        
        System.out.println("Noise (symbol flipped with p = 0.2) on CFL a^n b^n");
        Generator g4 = new NoiseAdder(new SimpleCFLGenerator(new Symbol("s"), new Symbol("a"), new Symbol("b"), 10), 0.2);
        for(Symbol s : g4)
            System.out.print(s.getName());
        
        System.out.println("\n");
        
        System.out.println("Noise (symbol flipped with p = 0.5) on CFL a^n b^m B^m A^n");
        Generator g5 = new NoiseAdder(new ComplicatedCFLGenerator(new Symbol("s"), new Symbol("a"), new Symbol("b"), new Symbol("B"), new Symbol("A"), 2, 3), NoiseAdder.hard);
        for(Symbol s : g5)
            System.out.print(s.getName());
    }
    
}
