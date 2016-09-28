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
    private int count = 0;

    public KochManager(JSF31KochFractalFX object) {
        application = object;
        edges = new ArrayList<Edge>();
    }

    public void changeLevel(int level) {
        edges.clear();
        koch.setLevel(level);
        TimeStamp ts = new TimeStamp();
        ts.setBegin("Drawing start");
<<<<<<< HEAD

        Thread tLeft = new Thread(new KochRunnable(koch, 0, this));
        Thread tRight = new Thread(new KochRunnable(koch, 1, this));
        Thread tBottom = new Thread(new KochRunnable(koch, 2, this));
        tLeft.start();
        tRight.start();
        tBottom.start();
        drawEdges();
        gc();
=======
        
        koch.generateBottomEdge();
        koch.generateLeftEdge();
        koch.generateRightEdge();
        
>>>>>>> parent of b2cc166... such speed very fast
        ts.setEnd("Drawing end");
        application.setTextCalc(ts.toString());
        application.setTextNrEdges(String.valueOf(koch.getNrOfEdges()));
    }

    public void drawEdges() {

        application.clearKochPanel();

        for (Edge e : edges) {
            application.drawEdge(e);
        }

    }

    @Override
    public void update(Observable o, Object arg) {
<<<<<<< HEAD
        koch = (KochFractal) o;
        edges.add((Edge) arg);
        //drawEdges();
=======
        koch = (KochFractal)o;
        edges.add((Edge)arg);
        drawEdges();

>>>>>>> parent of b2cc166... such speed very fast
    }

    public void addEdges(ArrayList edges) {
        this.edges.addAll(edges);
    }

    void IncreaseCount() {
        count++;
    }

    int getCount() {
        return count;
    }
}
