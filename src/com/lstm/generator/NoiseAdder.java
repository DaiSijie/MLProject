package com.lstm.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class NoiseAdder extends Generator {

    public static final double easy = 0.01;
    public static final double hard = 0.5;
    
    private static final Random random = new Random(12081995);
    
    private final List<Symbol> listRepresentation;
    
    private final Generator clean;
    
    public NoiseAdder(Generator clean, double flipOdd){
        this.clean = clean;
        this.listRepresentation = new ArrayList<>();
                
        for(Symbol s : clean){
            if(!clean.getAlphabet().contains(s) || random.nextDouble() >= flipOdd){
                listRepresentation.add(s);
            }
            else{
                Symbol toPut = null;
                do{
             
                    int r = random.nextInt(clean.getAlphabet().size());
                    int index = 0;
                    for(Symbol k: clean.getAlphabet()){
                        if(index == r)
                            toPut = k;
                        index++;
                    }
                }
                while(toPut == s);
                
                listRepresentation.add(toPut);
            }
        }
    }
    
    
    @Override
    public List<Symbol> getListRepresentation() {
        return listRepresentation;
    }


    @Override
    public Set<Symbol> getAlphabet() {
        return clean.getAlphabet(); 
    }

}
