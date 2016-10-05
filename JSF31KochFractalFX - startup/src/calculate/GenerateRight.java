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
public class GenerateRight implements Callable<List<Edge>>, Observer {

    private KochManager km;
    private KochFractal kf;
    private List<Edge> edges;

    public GenerateRight(KochManager manager, KochFractal fractal, int level) {
        km = manager;
        kf = fractal;
        kf.setLevel(level);
        kf.addObserver(this);
        edges = new ArrayList<Edge>();
    }

    @Override
    public List<Edge> call() throws InterruptedException, BrokenBarrierException {
        kf.generateRightEdge();
        System.out.println("R:Klaar met genereeten");
        System.out.println("R:+1");
        return edges;
    }

    @Override
    public void update(Observable o, Object arg) {
        edges.add((Edge) arg);
    }

}
