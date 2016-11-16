/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
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
    private Task left;
    private Task right;
    private Task bottom;

    public KochManager(JSF31KochFractalFX object) {
        application = object;
        edges = new ArrayList<>();
        this.koch = new KochFractal();
    }

    public void count() {
        count++;
        if (count >= 3) {
            drawEdges();
        }
    }
    
    
    public void cancelTasks(){
        if(left != null){
            left.cancel();
        }
        if(right != null){
            right.cancel();
        }
        if(bottom != null){
            bottom.cancel();
        }
    }
    public void changeLevel(int level) throws InterruptedException, ExecutionException {
        cancelTasks();
        edges.clear();
        application.clearKochPanel();
        koch.setLevel(level);
        count = 0;
        this.ts = new TimeStamp();
        application.progressBarBottom.progressProperty().unbind();
        application.progressBarLeft.progressProperty().unbind();
        application.progressBarRight.progressProperty().unbind();
        application.labelProgressBottomEdge.textProperty().unbind();
        application.labelProgressLeftEdge.textProperty().unbind();
        application.labelProgressRightEdge.textProperty().unbind();
        ts.setBegin("Calculating start");
        pool = Executors.newFixedThreadPool(3);
        left = new GenerateLeft(this, new KochFractal(), level,application);
        right = new GenerateRight(this, new KochFractal(), level,application);
        bottom = new GenerateBottom(this, new KochFractal(), level,application);
        left.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                count();
            }

        });
        right.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                count();
            }

        });
        bottom.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                count();
            }

        });
        application.progressBarBottom.progressProperty().bind(bottom.progressProperty());
        application.progressBarLeft.progressProperty().bind(left.progressProperty());
        application.progressBarRight.progressProperty().bind(right.progressProperty());
        application.labelProgressBottomEdge.textProperty().bind(bottom.messageProperty());
        application.labelProgressLeftEdge.textProperty().bind(left.messageProperty());
        application.labelProgressRightEdge.textProperty().bind(right.messageProperty());
        pool.submit(left);
        pool.submit(right);
        pool.submit(bottom);
        ts.setEnd("Calculating end");
    }

    public void drawEdges() {
        edges.clear();
        application.setTextCalc(ts.toString());
        application.setTextNrEdges(String.valueOf(koch.getNrOfEdges()));
        application.clearKochPanel();
        updateEdges();
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

    private void updateEdges() {
        try {
            edges.addAll((Collection<? extends Edge>) left.getValue());
            edges.addAll((Collection<? extends Edge>) right.getValue());
            edges.addAll((Collection<? extends Edge>) bottom.getValue());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
