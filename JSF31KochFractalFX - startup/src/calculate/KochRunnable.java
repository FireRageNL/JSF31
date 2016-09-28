/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author daan
 */
public class KochRunnable implements Runnable, Observer{
    private KochFractal koch;
    private int side;
    
    public KochRunnable(int side){
        this.side = side;
        koch = new KochFractal();
        koch.addObserver(this);
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
    
    public synchronized void generateLeftThread(){
        koch.generateLeftEdge();
    }
    
    public synchronized void generateRightThread(){
        koch.generateRightEdge();
    }
    
    public synchronized void generateBottomThread(){
        koch.generateBottomEdge();
    }

    @Override
    public void update(Observable o, Object arg) {
        
    }
}
