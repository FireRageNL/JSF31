/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.System.gc;
import timeutil.TimeStamp;

/**
 *
 * @author roy
 */
public class JSF31_w03_os_Runtime {

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) throws IOException, InterruptedException {
//        TimeStamp entireProcess = new TimeStamp();
//        TimeStamp processTime = new TimeStamp();
//        entireProcess.setBegin("begin process");
//        //Opdracht 3
//        Runtime nyoom = Runtime.getRuntime();
//        System.out.println(nyoom.availableProcessors());
//        System.out.println(nyoom.totalMemory() + "B");
//        System.out.println(nyoom.maxMemory() + "B");
//        System.out.println(nyoom.freeMemory() + "B");
//        int usedMem = (int) (nyoom.totalMemory() - nyoom.freeMemory());
//        System.out.println(usedMem +"B");
//        
//        //Opdracht 4
//        String s;
//        for(int i=0; i <100000;i++){
//            s= "Hello"+i;
//        }
//        usedMem = (int) (nyoom.totalMemory() - nyoom.freeMemory());
//        System.out.println(usedMem +"B");
//        gc();
//        usedMem = (int) (nyoom.totalMemory() - nyoom.freeMemory());
//        System.out.println(usedMem +"B");
//        
//        //Opdracht 5
//        processTime.setBegin("Processbuilder started");
//        ProcessBuilder bob = new ProcessBuilder("gnome-calculator");
//        Process proc = bob.start();
//        processTime.setEnd("processbuilder builded");
//        Thread.sleep(3500);
//        proc.destroy();
//        processTime.setBegin("exec started");
//        proc = nyoom.exec("gnome-calculator");
//        processTime.setEnd("exec builded");
//        Thread.sleep(3499);
//        proc.destroy();
//        //Opdracht 6
//        proc = nyoom.exec("ls");
//        InputStream is = proc.getInputStream();
//        InputStreamReader isr = new InputStreamReader(is);
//        try (BufferedReader br = new BufferedReader(isr)) {
//            String line;
//            while((line = br.readLine()) != null){
//                System.out.println(line);
//            }
//        }
//        entireProcess.setEnd("end of process");        
//        System.out.println(processTime.toString());
//        System.out.println(entireProcess.toString());
//    }
    ////////////////////
    //Vervolg opdracht//
    ////////////////////
    public static void main(String[] args) throws IOException, InterruptedException {
        TimeStamp entireProcess = new TimeStamp();
        entireProcess.setBegin("begin process");
        if (args.length % 2 == 0) {
            for (int i = 0; i < args.length; i += 2) {
                
                SuchProcessVeryRun spvr = new SuchProcessVeryRun(args[i],args[i+1]);
                Thread thread = new Thread(spvr);
                thread.start();
                
                
                
                
                

//                String[] command = new String[2];
//                command[0] = args[i];
//                ProcessBuilder bob;
//                if (args[i + 1].equals("")) {
//                    bob = new ProcessBuilder(args[i]);
//                } else {
//                    command[1] = args[i + 1];
//                    bob = new ProcessBuilder(command);
//                }
//                Process proc = bob.start();
//                InputStream is = proc.getInputStream();
//                if (is.read() != -1) {
//                    InputStreamReader isr = new InputStreamReader(is);
//                    try (BufferedReader br = new BufferedReader(isr)) {
//                        String line;
//                        while ((line = br.readLine()) != null) {
//                            System.out.println(line);
//                        }
//                        br.close();
//                    }
//                }
//                Thread.sleep(1000);
//                proc.waitFor();
            }
        }
        entireProcess.setEnd("end of process");
        System.out.println(entireProcess.toString());
    }
}
