/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.fambagge.smitefixer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rolf
 */
public class Main {
    //public static Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 3128));
    //urlS = urlS.replace("", "69.16.175.10");

    public static void main(String[] args) {
        String os = System.getProperty("os.name").toLowerCase();
        
        System.out.println(""+os);
        
        if(os.contains("windows")) {
            //Check hosts file
            File hosts = new File("C:/Windows/System32/drivers/etc/hosts");
            
            if(hosts.exists()) {
                try {
                    List<String> hostsContent = Files.readAllLines(hosts.toPath());
                    
                    boolean foundHostLine = false;
                    
                    for(String line : hostsContent) {
                        if(line.contains(Cache.HOST) && line.contains("127.0.0.1") && !line.trim().startsWith("#")) {
                            System.out.println("Found hosts line: "+line);
                            foundHostLine = true;
                        }
                    }
                    
                    if(!foundHostLine) {
                        try {
                            System.out.println("Missing host line in hosts. Trying to fix...");
                            PrintWriter writer = new PrintWriter(new FileOutputStream(hosts, true));

                            String hostsLine = "127.0.0.1 "+Cache.HOST+" #Added by SmiteFixer";
                            
                            writer.println();
                            writer.println(hostsLine);
                            writer.flush();
                            writer.close();
                            
                            System.out.println("Done adding hosts line: "+hostsLine);
                        } catch(Exception e) {
                            System.out.println("Error writing to "+hosts.getAbsolutePath()+", try running as administrator");
                            System.exit(0);
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Could not find hosts file...");
            }
        }
        
        Proxy proxy = new Proxy();
        
        proxy.start();
    }
}
