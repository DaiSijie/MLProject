package com.lstm.network.layers;

import com.lstm.network.MemoryBlock;
import com.lstm.network.PragmaticCache;

import java.util.ArrayList;

/**
 * Created by Wang on 11/15/2015.
 */
public class HiddenLayer extends Layer {

    private ArrayList<MemoryBlock> memoryBlocks;

    public HiddenLayer(int numMemoryBlocks)
    {
        memoryBlocks = new ArrayList<>(numMemoryBlocks);
    }

    public void forwardPass(PragmaticCache pragmaticCache)
    {
        for (int i = 0; i < getMemoryBlocks().size(); i++) {
            getMemoryBlocks().get(i).ForwardPass(pragmaticCache, i);
        }
    }

    public void backwardPass() {

    }

    public ArrayList<MemoryBlock> getMemoryBlocks() {
        return memoryBlocks;
    }
}
