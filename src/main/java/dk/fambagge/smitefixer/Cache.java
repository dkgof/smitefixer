/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.fambagge.smitefixer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rolf
 */
public class Cache {
    
    public static final String HOST = "cds.q6u4m8x5.hwcdn.net";
    public static final String IP = "69.16.175.10";
    
    public static File getFile(String request) {
        String filename = request.substring(request.lastIndexOf("/")+1);
        File result = new File("./cache/"+filename);
        
        System.out.println("Cache: "+filename+" - "+result.exists());

        int count = 0;
        
        while(!result.exists()) {
            download(request, result);
            
            count++;
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cache.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(count == 5) {
                break;
            }
        }
        
        if(!result.exists()) {
            System.out.println("Download failed: "+request);
        }
        
        return result;
    }
    
    public static void download(String request, File outputFile) {
        System.out.println("Downloading: "+request);
        
        try {
            Socket sock = new Socket(IP, 80);
            
            InputStream in = sock.getInputStream();
            
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
            
            writer.write("GET "+request+" HTTP/1.0\r\n");
            writer.write("Host: "+HOST+"\r\n");
            writer.write("Connection: close\r\n");
            writer.write("\r\n");
            writer.flush();
            
            outputFile.getParentFile().mkdirs();
            
            int b = in.read();
            
            StringBuilder sb = new StringBuilder();
            
            int length = 0;
            
            while(b != -1) {
                
                if(((char)b) == '\r') {
                    
                } else if(((char)b) == '\n') {
                    String line = sb.toString();
                    
                    if(line.startsWith("Content-Length:")) {
                        length = Integer.parseInt(line.substring(16));
                        
                        System.out.println(""+length);
                    }
                    
                    if(line.equals("")) {
                        //Data begins
                        break;
                    }
                    
                    sb = new StringBuilder();
                } else {
                    sb.append((char) b);
                }
                
                b = in.read();
            }

            if(length > 0) {
                FileOutputStream out = new FileOutputStream(outputFile, false);

                in.transferTo(out);

                out.close();
                
                System.out.println("Download done: "+request);
            } else {
                System.out.println("Download failed: "+request);
            }
            
            sock.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Cache.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
