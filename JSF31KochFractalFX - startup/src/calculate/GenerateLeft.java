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
public class GenerateLeft implements Runnable, Observer {

    private final KochManager km;
    private final KochFractal kf;

    public GenerateLeft(KochManager manager, KochFractal fractal, int level) {
        km = manager;
        this.kf = fractal;
        this.kf.setLevel(level);
        this.kf.addObserver(this);
    }

    @Override
    public void run() {
        kf.generateLeftEdge();
        km.count();
    }

    @Override
    public void update(Observable o, Object arg) {
        km.updateEdges((Edge) arg);
    }

}
