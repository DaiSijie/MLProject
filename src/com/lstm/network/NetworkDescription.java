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
    
    public NetworkDescription(int numInput, int numMemBlock){        
        this.numInput = numInput;
        this.numOutput = numInput;
        this.numMemBlock = numMemBlock;
        this.numSource = numInput + numMemBlock;
        this.numReceiver = numInput + 4 * numMemBlock;
        
    }
    
    
    
    
}
