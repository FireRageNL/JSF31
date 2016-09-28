/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kochconsoleapplication;

import calculate.KochFractal;

/**
 *
 * @author daan
 */
public class KochConsoleApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //Make observer
        KochObserver ko = new KochObserver();
        //Make kochfractal object
        KochFractal kf = new KochFractal();
        //add ko to kf
        kf.addObserver(ko);
        
        kf.setLevel(2);
        kf.generateBottomEdge();
        kf.generateLeftEdge();
        kf.generateRightEdge();
        

    }
    
}
