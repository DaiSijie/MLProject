/*
 *	Author:      Gilbert Maystre
 *	Date:        Dec 13, 2015
 */

package com.lstm.network;

public class NetworkDescription {    
    
    public final int numInput;
    public final int numOutput;
    public final int numMemBlock;
    public final int numSource;
    public final int numReceiver;
    
    public final int numBias;
    public final int numWeights;
    
    public final int numPeephole;
    public final int numCellsPerBlock;
    
    public NetworkDescription(int numInput, int numMemBlock){        
        this.numInput = numInput;
        this.numOutput = numInput;
        this.numMemBlock = numMemBlock;
        this.numSource = numInput + numMemBlock;
        this.numReceiver = numInput + 4 * numMemBlock;
        
        this.numBias = numReceiver;
        this.numWeights = numReceiver * numSource;
        
        this.numPeephole = 3;
        this.numCellsPerBlock = 1;
        
    }

}
