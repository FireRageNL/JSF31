/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runtime;

import java.io.IOException;

/**
 *
 * @author roy
 */
public class Run2 {
    public static void main(String[] args) throws IOException{
                        if(args.length % 2 == 0){
        for(int i = 0;i<args.length; i+=2){
        String[] command = {
            args[i],
            args[i+1]
        };
        ProcessBuilder bob = new ProcessBuilder(command);
        Process proc = bob.start();
        }
    }
    
}

}
