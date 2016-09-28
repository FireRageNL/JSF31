/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;

/**
 *
 * @author daan
 */
public class KochRunnable implements Runnable{
    private KochFractal koch;
    private int side;
    
    public KochRunnable(KochFractal koch, int side){
        this.koch = koch;
        this.side = side;
    }
    
    @Override
    public void run() {
        if(side == 0){
            generateLeftThread();
        }
        else if(side == 1){
            generateRightThread();
        }
        else{
            generateBottomThread();
        }
    }
    
    public void generateLeftThread(){
        koch.generateLeftEdge();
    }
    
    public void generateRightThread(){
        koch.generateRightEdge();
    }
    
    public void generateBottomThread(){
        koch.generateBottomEdge();
    }
}
