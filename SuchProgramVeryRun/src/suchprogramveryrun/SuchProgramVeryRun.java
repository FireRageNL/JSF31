/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suchprogramveryrun;

import java.util.Map;
import java.util.Properties;
import java.io.*;

/**
 *
 * @author roy
 */
public class SuchProgramVeryRun {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        Map<String,String>env = System.getenv();
        env.keySet().stream().filter((envName) -> (envName.equals("TestEnviron"))).forEach((envName) -> {
            prop.setProperty(envName, env.get(envName));
            System.out.println(envName +' '+ env.get(envName));
        });
        File file = new File("SuchPropertiesVeryFile.properties");
        try (FileOutputStream fileout = new FileOutputStream(file)) {
            prop.store(fileout,"SuchEnvironment");
        }
    }
    
}
