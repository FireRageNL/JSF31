/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author roy_v
 */
public class GenerateRight implements Runnable, Observer {

    private KochManager km;
    private KochFractal kf;

    public GenerateRight(KochManager manager, KochFractal fractal, int level) {
        km = manager;
        kf = fractal;
        kf.setLevel(level);
        kf.addObserver(this);
    }

    @Override
    public void run() {
        kf.generateRightEdge();
        km.count();
    }

    @Override
    public void update(Observable o, Object arg) {
        km.updateEdges((Edge) arg);
    }

}
