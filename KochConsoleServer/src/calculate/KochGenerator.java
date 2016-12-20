/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import kochconsoleserver.KochConsoleServer;
import kochconsoleserver.KochFractal;

/**
 *
 * @author daan_
 */
public class KochGenerator implements Observer{

    
    private List<Edge> edges;
    private int type;
    KochConsoleServer kcs;
    ObjectOutputStream out;
    ObjectInputStream in;
    
    public KochGenerator(int type, KochConsoleServer kcs){
        edges = new ArrayList();
        this.type = type;
        this.kcs = kcs;
    }
    
    
    public void generateFractal(int level) {
        edges = new ArrayList();
        System.out.println("Generating level: "+level);
        if (level > 12) {
            level = 12;
            System.out.println("Levels larger than 12 are not supported! Level is set as 12!");
        }
        KochFractal kf = new KochFractal();
        kf.addObserver(this);
        kf.setLevel(level);
        System.out.println("Generating " + kf.getNrOfEdges() + " Edges...");
        kf.generateBottomEdge();
        System.out.println("33% complete!");
        kf.generateLeftEdge();
        System.out.println("66% complete!");
        kf.generateRightEdge();
        System.out.println("100% Complete");
    }
   
    
    public void GenerateAndSend(int level, ObjectInputStream in, ObjectOutputStream out){
        this.out = out;
        this.in = in;
        edges = new ArrayList();
        System.out.println("Generating level: "+level);
        if (level > 12) {
            level = 12;
            System.out.println("Levels larger than 12 are not supported! Level is set as 12!");
        }
        KochFractal kf = new KochFractal();
        kf.addObserver(this);
        kf.setLevel(level);
        System.out.println("Generating " + kf.getNrOfEdges() + " Edges...");
        kf.generateBottomEdge();
        System.out.println("33% complete!");
        kf.generateLeftEdge();
        System.out.println("66% complete!");
        kf.generateRightEdge();
        System.out.println("100% Complete");
        try {
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(KochGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Edge> getEdges(){
        return edges;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        edges.add((Edge)arg);  
        if(type == 2){
            try {
                kcs.sendEdge((Edge)arg, out, in);
            } catch (IOException ex) {
                Logger.getLogger(KochGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
