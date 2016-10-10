/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

/**
 *
 * @author roy_v
 */
public class GenerateBottom implements Callable<List<Edge>>, Observer {

    private final KochManager km;
    private final KochFractal kf;
    private List<Edge> edges;
    private CyclicBarrier cb;

    public GenerateBottom(KochManager manager, KochFractal fractal, int level, CyclicBarrier cb) {
        km = manager;
        this.kf = fractal;
        this.kf.setLevel(level);
        this.kf.addObserver(this);
        this.cb = cb;
        edges = new ArrayList<Edge>();
    }

    @Override
    public List<Edge> call() throws BrokenBarrierException, InterruptedException {
        kf.generateBottomEdge();
        if(km.getCyclicBarrier().await() == 0){
            return edges;
        }
        return edges;
    }

    @Override
    public void update(Observable o, Object arg) {
        edges.add((Edge) arg);
    }

}
