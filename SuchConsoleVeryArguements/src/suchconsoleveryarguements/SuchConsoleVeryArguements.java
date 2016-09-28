/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suchconsoleveryarguements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author roy
 */
public class SuchConsoleVeryArguements {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        if(args.length % 2 == 0){
        Properties prop = new Properties();
        for(int i = 0;i<args.length; i+=2){
            
            prop.setProperty(args[i], args[i+1]);
            
        }
        File file = new File("SuchPropertiesVeryFile.properties");
        try (FileOutputStream fileout = new FileOutputStream(file)) {
            prop.store(fileout,"SuchEnvironment");
        }
        }
        else{
            System.out.println("Such uneven, very undividable, much error");
            System.out.println("─────────▄──────────────▄");
            System.out.println("────────▌▒█───────────▄▀▒▌");
            System.out.println("────────▌▒▒▀▄───────▄▀▒▒▒▐");
            System.out.println("───────▐▄▀▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐");
            System.out.println("─────▄▄▀▒▒▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐");
            System.out.println("───▄▀▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▀██▀▒▌");
            System.out.println("──▐▒▒▒▄▄▄▒▒▒▒▒▒▒▒▒▒▒▒▒▀▄▒▒▌");
            System.out.println("──▌▒▒▐▄█▀▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐");
            System.out.println("─▐▒▒▒▒▒▒▒▒▒▒▒▌██▀▒▒▒▒▒▒▒▒▀▄▌");
            System.out.println("─▌▒▀▄██▄▒▒▒▒▒▒▒▒▒▒▒░░░░▒▒▒▒▌");
            System.out.println("─▌▀▐▄█▄█▌▄▒▀▒▒▒▒▒▒░░░░░░▒▒▒▐");
            System.out.println("▐▒▀▐▀▐▀▒▒▄▄▒▄▒▒▒▒▒░░░░░░▒▒▒▒▌");
            System.out.println("▐▒▒▒▀▀▄▄▒▒▒▄▒▒▒▒▒▒░░░░░░▒▒▒▐");
            System.out.println("─▌▒▒▒▒▒▒▀▀▀▒▒▒▒▒▒▒▒░░░░▒▒▒▒▌");
            System.out.println("─▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▐");
            System.out.println("──▀▄▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▄▒▒▒▒▌");
            System.out.println("────▀▄▒▒▒▒▒▒▒▒▒▒▄▄▄▀▒▒▒▒▄▀");
            System.out.println("───▐▀▒▀▄▄▄▄▄▄▀▀▀▒▒▒▒▒▄▄▀");
            System.out.println("──▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▀▀");
            System.out.println("woof");
            
            
            
            
            
        }
    }
    
}