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
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.net.Socket;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
    private Task left;
    private Task right;
    private Task bottom;

    public KochManager(JSF31KochFractalFX object) throws IOException {
        application = object;
        edges = new ArrayList<>();
        this.koch = new KochFractal();

    }

    public void requestEdges() throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    application.clearKochPanel();
                    edges = new ArrayList();
                    Socket s = new Socket("localhost", 8189);
                    OutputStream outStream = s.getOutputStream();
                    InputStream inStream = s.getInputStream();

                    // Let op: volgorde is van belang!
                    ObjectOutputStream out = new ObjectOutputStream(outStream);
                    ObjectInputStream in = new ObjectInputStream(inStream);                    
                    
                    int result = in.readInt();

                    for (int i = 0; i < result; i++) {
                        Edge e = (Edge) in.readObject();
                        //Kleur komt nog niet binnen :|
                        edges.add(e);
                        application.requestDrawEdge(e);
                    }
                    in.close();
                    out.flush();
                    s.close();
                        
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(KochManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(KochManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }

        }).start();

    }

    public void changeLevel(int level) throws InterruptedException, ExecutionException {

        edges.clear();
        application.clearKochPanel();
        koch.setLevel(level);
        count = 0;

    }

    public void drawEdges() {
        edges.clear();
//        application.setTextCalc(ts.toString());
        //       application.setTextNrEdges(String.valueOf(koch.getNrOfEdges()));
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

    }
}
