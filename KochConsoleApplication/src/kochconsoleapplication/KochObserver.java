/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kochconsoleapplication;

import calculate.Edge;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author daan
 */
public class KochObserver implements Observer{

    @Override
    public void update(Observable o, Object arg) {
        Edge e = (Edge)arg;
        System.out.println(e.X1 + "," + e.Y1+"-"+e.X2+","+e.Y2);
    }
    
    
}
