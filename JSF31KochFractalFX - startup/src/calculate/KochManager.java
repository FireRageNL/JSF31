/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import static java.lang.System.gc;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

/**
 *
 * @author daan
 */
public class KochManager implements Observer{
    
    private JSF31KochFractalFX application;
    private KochFractal koch;
    private ArrayList<Edge> edges;
    
    public KochManager(JSF31KochFractalFX object){
        application = object;
        edges = new ArrayList<Edge>();
    }
    
    public void changeLevel(int level){
        edges.clear();
        koch.setLevel(level);
        TimeStamp ts = new TimeStamp();
        ts.setBegin("Drawing start");
        
        KochRunnable left = new KochRunnable(koch, 0);
        KochRunnable right = new KochRunnable(koch, 1);
        KochRunnable bottom = new KochRunnable(koch, 2);
        
        Thread tLeft = new Thread(left);
        tLeft.run();
        
        Thread tRight = new Thread(right);
        tRight.run();
        
        Thread tBottom = new Thread(bottom);
        tBottom.run();
        
        drawEdges();
        gc();
        ts.setEnd("Drawing end");
       application.setTextCalc(ts.toString());
       application.setTextNrEdges(String.valueOf(koch.getNrOfEdges()));
    }
    
    public void drawEdges(){
        
        application.clearKochPanel();
        
       for(Edge e : edges){
           application.drawEdge(e);
       }
       
    }

    @Override
    public void update(Observable o, Object arg) {
        koch = (KochFractal)o;
        edges.add((Edge)arg);
        //drawEdges();
    }
}
