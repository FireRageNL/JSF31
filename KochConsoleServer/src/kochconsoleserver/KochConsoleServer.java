/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kochconsoleserver;

import calculate.Edge;
import calculate.KochGenerator;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daan_
 */
public class KochConsoleServer {

    private List<Edge> edges = new ArrayList();
    private static ServerSocket server;

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // TODO code application logic here
        KochConsoleServer kcs = new KochConsoleServer();
        server = new ServerSocket(8189);
        System.out.println("Server started... waiting for requests...");
        while (true) {
            kcs.messageReceived();
        }

    }

    public void messageReceived() throws ClassNotFoundException {
        InputStream inStream = null;
        try {
            final Socket incoming = server.accept(); //wait for reply
            System.out.println("Connected to " + incoming.getInetAddress());
            OutputStream outStream = incoming.getOutputStream();
            inStream = incoming.getInputStream();
            final ObjectInputStream in = new ObjectInputStream(inStream);
            ObjectOutputStream out = new ObjectOutputStream(outStream);
            final Map<String, Integer> message = (Map<String, Integer>) in.readObject();
            switch (message.get("type")) {
                case 1:
                    System.out.println("Full generate requested..");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            sendEdgesFull(message.get("level"), incoming, out, in);
                        }
                    }).start();
                    break;
                case 2:
                    System.out.println("Per edge requested..");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            sendPerEdge(message.get("level"), in, out);
                        }
                    }).start();
                    break;
                case 3:
                    break;
                default:
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(KochConsoleServer.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public void sendEdgesFull(int level, Socket incoming, ObjectOutputStream out, ObjectInputStream in) {
        try {
            KochGenerator kg = new KochGenerator(1, this);
            kg.generateFractal(level);
            edges = kg.getEdges();
            out.writeInt(edges.size());
            System.out.println("sending edges \n\n\n");
            for (Edge e : edges) {
                out.writeObject(e);
            }
            out.flush();
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(KochConsoleServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sendEdge(final Edge e, ObjectOutputStream out, ObjectInputStream in) throws IOException {
        out.writeObject(e);
        out.flush();
    }

    private void sendPerEdge(int level, ObjectInputStream in, ObjectOutputStream out) {
        KochGenerator kg = new KochGenerator(2, this);
        kg.GenerateAndSend(level, in, out);
    }

}
