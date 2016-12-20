/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
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
    private Map<String, Integer> comProtocol;

    public KochManager(JSF31KochFractalFX object) throws IOException {
        application = object;
        edges = new ArrayList<>();
        this.koch = new KochFractal();

    }

    public void requestEdgesFull(final int level) throws IOException {
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
                    comProtocol = new HashMap();
                    comProtocol.put("level", level);
                    comProtocol.put("type", 1);
                    out.writeObject(comProtocol);

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

    public void getPerEdge(final int level) {
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
                    comProtocol = new HashMap();
                    comProtocol.put("level", level);
                    comProtocol.put("type", 2);
                    out.writeObject(comProtocol);
                    Edge e = (Edge) in.readObject();
                    while (e != null) {
                        edges.add(e);
                        application.requestDrawEdge(e);
                        e = (Edge) in.readObject();
                    }
                } catch (IOException ex) {

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(KochManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    public void changeLevel(int level) throws InterruptedException, ExecutionException {
        edges.clear();
        application.clearKochPanel();
        try {
            requestEdgesFull(level);

        } catch (IOException ex) {
            Logger.getLogger(KochManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void drawEdges() {
        //edges.clear();
//        application.setTextCalc(ts.toString());
        //       application.setTextNrEdges(String.valueOf(koch.getNrOfEdges()));
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
