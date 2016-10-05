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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

/**
 *
 * @author daan
 */
public class KochManager implements Observer {

    private JSF31KochFractalFX application;
    private KochFractal koch;
    private ArrayList<Edge> edges;
    private int count;
    public TimeStamp ts;
    public TimeStamp ts2;
    private ExecutorService pool;

    private Future<List<Edge>> fut1;
    private Future<List<Edge>> fut2;
    private Future<List<Edge>> fut3;

    public KochManager(JSF31KochFractalFX object) {
        application = object;
        edges = new ArrayList<>();
        this.koch = new KochFractal();
    }

    public void count() throws InterruptedException, ExecutionException {
        count++;
        if (count >= 3) {
            System.out.println("OBSOLETE");
        }
    }

    public void changeLevel(int level) throws InterruptedException, ExecutionException {
        edges.clear();
        koch.setLevel(level);
        count = 0;
        this.ts = new TimeStamp();
        ts.setBegin("Calculating start");
        pool = Executors.newFixedThreadPool(3);
        fut3 = pool.submit(new GenerateBottom(this, new KochFractal(), level));
        fut1 = pool.submit(new GenerateRight(this, new KochFractal(), level));
        fut2 = pool.submit(new GenerateLeft(this, new KochFractal(), level));
        edges.addAll(fut1.get());
        edges.addAll(fut2.get());
        edges.addAll(fut3.get());
        ts.setEnd("Calculating end");
        application.requestDrawEdges();
        pool.shutdown();
    }

    public void drawEdges() {
        application.setTextCalc(ts.toString());
        application.setTextNrEdges(String.valueOf(koch.getNrOfEdges()));
        application.clearKochPanel();
        ts2 = new TimeStamp();
        ts2.setBegin("Drawing Start");

        for (Edge e : edges) {
            application.drawEdge(e);
        }
        ts2.setEnd("Drawing End");
        application.setTextDraw(ts2.toString());
    }

    @Override
    public void update(Observable o, Object arg) {
        edges.add((Edge) arg);
    }
}
