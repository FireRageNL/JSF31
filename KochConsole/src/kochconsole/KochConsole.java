/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kochconsole;

import calculate.Edge;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.nio.ch.DirectBuffer;
import timeutil.TimeStamp;

/**
 *
 * @author roy_v
 */
public class KochConsole implements Observer {

    List<Edge> ret = new ArrayList<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        KochConsole app = new KochConsole();
        //app.doTheStuffWithOutputStreams();
        //app.doTheStuffWithNonBufferWriters();
        //app.doTheStuffWithBufferedWriters();
        app.doTheStuffWithMemoryMappedfile();
    }

    public void doTheStuffWithMemoryMappedfile() throws IOException {
        if(Files.exists(Paths.get("edge.ram"))){
            Files.delete(Paths.get("edge.ram"));
        }
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the level to be generated: ");
        int level = scanner.nextInt();
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
        System.out.println("100% Complete, now writing file!");
        TimeStamp t = new TimeStamp();
        t.setBegin("Begin write with memory mapped file");
        int numberOfBytes = kf.getNrOfEdges() * 4 * 8;
        try {
            RandomAccessFile raf = new RandomAccessFile("edge.ram", "rw");
            MappedByteBuffer mbf = raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, numberOfBytes+8);
            FileLock instantLock = raf.getChannel().lock(0,4,false);
            mbf.putInt(kf.getNrOfEdges());
            instantLock.release();
            int numberOfEdges = 0;
            for (Edge e : ret) {
                FileLock intLock = raf.getChannel().lock(4,4,false);
                FileLock lock = raf.getChannel().lock((numberOfEdges * 32) + 8, 32, false);
                mbf.position(4);
                mbf.putInt(numberOfEdges+1);
                mbf.position((numberOfEdges*32)+8);
                mbf.putDouble(e.X1);
                mbf.putDouble(e.Y1);
                mbf.putDouble(e.X2);
                mbf.putDouble(e.Y2);
                System.out.println("Edge written (" + (numberOfEdges+1) + "/"+kf.getNrOfEdges()+")");
                numberOfEdges++;
                lock.release();
                intLock.release();
            }
            unmap(mbf);
            
            
            
            
            
            
//            Path p = Paths.get("edge.ram");
//            Path p2 = Paths.get("edger.ram");
//            Path p3 = Paths.get("edges.ram");
//            if (Files.exists(p3)) {
//                Files.delete(p3);
//
//            }
//            if (Files.exists(p2)) {
//                Files.delete(p2);
//
//            }
//            Files.copy(p, p.resolveSibling("edger.ram"));
//            Files.move(p2, p2.resolveSibling("edges.ram"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KochConsole.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KochConsole.class.getName()).log(Level.SEVERE, null, ex);
        }
        t.setEnd("End of writing memory mapped file");
        System.out.println(t.toString());

    }

    public void doTheStuffWithBufferedWriters() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the level to be generated: ");
        int level = scanner.nextInt();
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
        System.out.println("100% Complete, now writing file!");
        TimeStamp t = new TimeStamp();
        t.setBegin("Begin write with buffer to txt");
        try {
            FileWriter fw = new FileWriter("edges.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (Edge e : ret) {
                bw.write(String.valueOf(e.X1) + ";" + String.valueOf(e.Y1) + ";" + String.valueOf(e.X2) + ";" + String.valueOf(e.Y2) + ";" + e.color.toString());
            }
        } catch (IOException ex) {
            Logger.getLogger(KochConsole.class.getName()).log(Level.SEVERE, null, ex);
        }

        t.setEnd("File Written");
        System.out.println(t.toString());
    }

    public void doTheStuffWithNonBufferWriters() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the level to be generated: ");
        int level = scanner.nextInt();
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
        System.out.println("100% Complete, now writing file!");
        TimeStamp t = new TimeStamp();
        t.setBegin("Begin write without buffer to txt");
        //Write to file
        try (PrintWriter out = new PrintWriter("edges.txt")) {
            for (Edge e : ret) {
                out.println(String.valueOf(e.X1) + ";" + String.valueOf(e.Y1) + ";" + String.valueOf(e.X2) + ";" + String.valueOf(e.Y2) + ";" + e.color.toString());
            }
            out.println();
        }
        t.setEnd("File Written");
        System.out.println(t.toString());

    }

    public void doTheStuffWithOutputStreams() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the level to be generated: ");
        int level = scanner.nextInt();
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
        System.out.println("100% Complete, now writing file!");
        try {
            TimeStamp t = new TimeStamp();
            t.setBegin("Begin writing to binary");
            FileOutputStream out = new FileOutputStream("edges.tmp");
            BufferedOutputStream bout = new BufferedOutputStream(out); //comment deze
            ObjectOutputStream ops = new ObjectOutputStream(bout); //comment deze
            //ObjectOutputStream ops = new ObjectOutputStream(out);
            ops.writeObject(ret);
            ops.close();
            t.setEnd("File Written");

            System.out.println(t.toString());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        ret.add((Edge) arg);
    }

    public static void unmap(MappedByteBuffer buffer) {
        sun.misc.Cleaner cleaner = ((DirectBuffer) buffer).cleaner();
        cleaner.clean();
    }

}
