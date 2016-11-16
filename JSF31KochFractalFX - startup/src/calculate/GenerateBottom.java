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
import javafx.concurrent.Task;
import jsf31kochfractalfx.JSF31KochFractalFX;

/**
 *
 * @author roy_v
 */
public class GenerateBottom extends Task<List<Edge>> implements Observer {

    private final KochManager km;
    private final KochFractal kf;
    private List<Edge> edges;
    private final JSF31KochFractalFX app;

    public GenerateBottom(KochManager manager, KochFractal fractal, int level, JSF31KochFractalFX application) {
        km = manager;
        this.kf = fractal;
        this.kf.setLevel(level);
        this.kf.addObserver(this);
        edges = new ArrayList<Edge>();
        updateProgress(0, kf.getNrOfEdges() / 3);
        updateMessage("0");
        app = application;
    }

    @Override
    public List<Edge> call() {
        kf.generateBottomEdge();
        return edges;
    }

    @Override
    public void update(Observable o, Object arg) {
        edges.add((Edge) arg);
        updateProgress(edges.size(), kf.getNrOfEdges() / 3);
        updateMessage(Integer.toString(edges.size()));
    }

}
