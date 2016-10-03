/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
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

    public KochManager(JSF31KochFractalFX object) {
        application = object;
        edges = new ArrayList<>();
        this.koch = new KochFractal();
    }

    public synchronized void count() {
        count++;
        if (count >= 3) {
            ts.setEnd("Calculating end");
            application.requestDrawEdges();
        }
    }

    public synchronized void changeLevel(int level) {
        edges.clear();
        koch.setLevel(level);
        count = 0;
        this.ts = new TimeStamp();
        ts.setBegin("Calculating start");

        Thread bottomThread = new Thread(new GenerateBottom(this, new KochFractal(), koch.getLevel()));
        bottomThread.start();
        Thread leftThread = new Thread(new GenerateLeft(this, new KochFractal(), koch.getLevel()));
        leftThread.start();
        Thread rightThread = new Thread(new GenerateRight(this, new KochFractal(), koch.getLevel()));
        rightThread.start();

    }

    public synchronized void updateEdges(Edge e) {
        edges.add(e);
    }

    public synchronized void drawEdges() {
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
    public synchronized void update(Observable o, Object arg) {
        edges.add((Edge) arg);
    }
}
