/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suchprojectveryread;

import java.util.Properties;
import java.io.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roy
 */
public class SuchProjectVeryRead {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        try {
            Properties prop = new Properties();
            try (InputStream in = new FileInputStream("SuchPropertiesVeryFile.properties")) {
                prop.load(in);
                Enumeration<?> e = prop.propertyNames();
                while(e.hasMoreElements()){
                    String key = (String) e.nextElement();
                    String value = prop.getProperty(key);
                    System.out.println(key+" : "+ value);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(SuchProjectVeryRead.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
