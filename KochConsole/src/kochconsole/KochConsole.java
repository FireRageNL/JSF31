/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kochconsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.io.*;

/**
 *
 * @author roy_v
 */
public class KochConsole implements Observer {

    List<Edge> ret = new ArrayList<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        KochConsole app = new KochConsole();
        app.doTheStuff();
    }

    public void doTheStuff() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the level to be generated: ");
        int level = scanner.nextInt();
        if(level > 12){
            level = 12;
            System.out.println("Levels larger than 12 are not supported! Level is set as 12!");
        }
        KochFractal kf = new KochFractal();
        kf.addObserver(this);
        kf.setLevel(level);
        System.out.println("Generating "+ kf.getNrOfEdges()+ " Edges...");
        kf.generateBottomEdge();
        System.out.println("33% complete!");
        kf.generateLeftEdge();
        System.out.println("66% complete!");
        kf.generateRightEdge();
        System.out.println("100% Coplete, now writing file!");
        try {
            FileOutputStream out = new FileOutputStream("edges.tmp");
            ObjectOutputStream ops = new ObjectOutputStream(out);
            ops.writeObject(ret);
            ops.close();
            System.out.println("File written!");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        ret.add((Edge) arg);
    }

}
