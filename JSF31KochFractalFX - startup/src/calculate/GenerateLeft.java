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
import java.util.concurrent.ExecutionException;
/**
 *
 * @author roy_v
 */
public class GenerateLeft implements Callable<List<Edge>>, Observer {

    private final KochManager km;
    private final KochFractal kf;
    private List<Edge> edges;
    private CyclicBarrier cb;
    public  GenerateLeft(KochManager manager, KochFractal fractal, int level, CyclicBarrier cb) {
        km = manager;
        this.kf = fractal;
        this.kf.setLevel(level);
        this.cb = cb;
        this.kf.addObserver(this);
        edges = new ArrayList<Edge>();
    }

    @Override
    public List<Edge> call() throws InterruptedException, BrokenBarrierException{
        kf.generateLeftEdge();
        System.out.println("L:Klaar met genereeten");
        cb.await();
        System.out.println("L:+1");
        return edges;
    }

    @Override
    public void update(Observable o, Object arg) {
        edges.add((Edge) arg);
    }

}
