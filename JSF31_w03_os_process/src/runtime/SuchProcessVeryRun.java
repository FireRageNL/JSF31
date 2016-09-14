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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roy
 */
public class SuchProcessVeryRun implements Runnable {

    private String command;
    private String argument;

    public SuchProcessVeryRun(String command, String argument) {
        this.command = command;
        this.argument = argument;
    }

    @Override
    public void run() {
        try {
            ProcessBuilder bob;
            if (argument.equals("")) {
                bob = new ProcessBuilder(command);
            } else {
                
                bob = new ProcessBuilder(command,argument);
            }
            Process proc = bob.start();
            InputStream is = proc.getInputStream();
            if (is.read() != -1) {
                InputStreamReader isr = new InputStreamReader(is);
                try (BufferedReader br = new BufferedReader(isr)) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(SuchProcessVeryRun.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Thread.sleep(1000);
            proc.waitFor();
            System.out.println(command + " " + argument+" Terminated" );
        } catch (IOException ex) {
            Logger.getLogger(SuchProcessVeryRun.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(SuchProcessVeryRun.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
