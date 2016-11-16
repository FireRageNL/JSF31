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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import jsf31kochfractalfx.JSF31KochFractalFX;

/**
 *
 * @author roy_v
 */
public class GenerateRight extends Task<List<Edge>> implements Observer {

    private final KochManager km;
    private final KochFractal kf;
    private List<Edge> edges;
    private final JSF31KochFractalFX app;
    private Edge e;

    public GenerateRight(KochManager manager, KochFractal fractal, int level, JSF31KochFractalFX application) {
        km = manager;
        kf = fractal;
        kf.setLevel(level);
        kf.addObserver(this);
        edges = new ArrayList<Edge>();
        app = application;
    }

    @Override
    public List<Edge> call() {
        kf.generateRightEdge();
        return edges;
    }

    @Override
    public void update(Observable o, Object arg) {
        
        Edge edge = (Edge) arg;
        edges.add(edge);
        e = new Edge(edge.X1,edge.Y1,edge.X2,edge.Y2,Color.WHITE);
        
        Platform.runLater(new Runnable(){
            @Override
            public void run(){
                app.drawEdge(e);
            }
        });
        updateProgress(edges.size(), kf.getNrOfEdges() / 3);
        updateMessage(Integer.toString(edges.size()));
        try {
            Thread.sleep(2);
        } catch (InterruptedException ex) {
            Logger.getLogger(GenerateRight.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
