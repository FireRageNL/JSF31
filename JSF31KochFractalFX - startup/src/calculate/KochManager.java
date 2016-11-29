/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private String PATH = "..\\KochConsole\\edges.tmp";
    private String PATHTXT = "..\\KochConsole\\edges.txt";
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
    
    public void readFileMap

    public void loadTxtNonBuffer() {
        application.clearKochPanel();
        edges = new ArrayList();
        TimeStamp t = new TimeStamp();
        t.setBegin("Start measure");
        try {
            Scanner input;
            System.out.print(PATHTXT);
            File file = new File(PATHTXT);

            input = new Scanner(file);

            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] data = line.split(";");

                Edge e = new Edge(Double.parseDouble(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]), javafx.scene.paint.Color.BLUE);
                edges.add(e);

            }
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        t.setEnd("End Measuring");
        System.out.println(t.toString());
        for (Edge e : edges) {
            application.drawEdge(e);
        }
    }

    public void loadTxtWithBuffer() {
        InputStream file = null;
        TimeStamp t = new TimeStamp();
        try {
            t.setBegin("Start measure");
            FileReader fread = new FileReader(PATHTXT);
            BufferedReader in = new BufferedReader(fread);
            Scanner input = new Scanner(in);

            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] data = line.split(";");

                Edge e = new Edge(Double.parseDouble(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]), javafx.scene.paint.Color.BLUE);
                edges.add(e);

            }
            input.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(KochManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        t.setEnd("End Measuring");
        System.out.println(t.toString());
        for (Edge e : edges) {
            application.drawEdge(e);
        }
    }

    public void loadObjectBufferedFractal() {
        InputStream file = null;
        try {
            TimeStamp t = new TimeStamp();
            t.setBegin("Start measure");
            file = new FileInputStream(PATH);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(buffer);
            edges = (ArrayList<Edge>) ois.readObject();
            t.setEnd("End Measuring");
            System.out.println(t.toString());
            for (Edge e : edges) {
                application.drawEdge(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(KochManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void loadObjectNonBufferedFractal() {
        FileInputStream fin = null;
        try {
            application.clearKochPanel();
            TimeStamp t = new TimeStamp();
            t.setBegin("Start measure");
            edges = new ArrayList<>();
            //LoadEdges from file
            fin = new FileInputStream(PATH);
            ObjectInputStream ois = new ObjectInputStream(fin);
            edges = (ArrayList<Edge>) ois.readObject();
            t.setEnd("End Measuring");
            System.out.println(t.toString());
            for (Edge e : edges) {
                application.drawEdge(e);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KochManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KochManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(KochManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fin.close();
            } catch (IOException ex) {
                Logger.getLogger(KochManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void count() {
        count++;
        if (count >= 3) {
            drawEdges();
        }
    }

    public void cancelTasks() {
        if (left != null) {
            left.cancel();
        }
        if (right != null) {
            right.cancel();
        }
        if (bottom != null) {
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
        left = new GenerateLeft(this, new KochFractal(), level, application);
        right = new GenerateRight(this, new KochFractal(), level, application);
        bottom = new GenerateBottom(this, new KochFractal(), level, application);
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
