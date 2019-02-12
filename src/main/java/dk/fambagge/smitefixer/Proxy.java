/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.fambagge.smitefixer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rolf
 */
public class Proxy implements Runnable {
    
    private Thread ourThread;
    
    public Proxy() {
        
    }
    
    public void start() {
        ourThread = new Thread(this);
        ourThread.start();
    }

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(80);
            
            ExecutorService executor = Executors.newCachedThreadPool();
            
            System.out.println("Proxy running...");
            
            while(true) {
                Socket client = server.accept();
                
                executor.submit(new ClientHandler(client));
            }
        } catch (IOException ex) {
            Logger.getLogger(Proxy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private class ClientHandler implements Runnable {
        private final Socket client;
        
        private ClientHandler(Socket client) {
            this.client = client;
        }
        
        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                
                String line = reader.readLine();
                
                String request = "";
                
                while(line != null) {
                    if(line.startsWith("GET")) {
                        request = line.substring(4, line.indexOf("HTTP")-1);
                        
                        File result = Cache.getFile(request);
                        
                        FileInputStream in = new FileInputStream(result);
                        
                        PrintWriter writer = new PrintWriter(client.getOutputStream());
                        writer.write("HTTP/1.0 200 OK\r\n");
                        writer.write("Connection: close\r\n");
                        writer.write("Accept-Ranges: bytes\r\n");
                        writer.write("Cache-Control: max-age=74667\r\n");
                        writer.write("Content-Length: "+result.length()+"\r\n");
                        if(request.endsWith(".json")) {
                            writer.write("Content-Type: application/json\r\n");
                        } else if(request.endsWith(".jpg")) {
                            writer.write("Content-Type: image/jpeg\r\n");
                        }
                        writer.write("\r\n");
                        writer.flush();
                        
                        in.transferTo(client.getOutputStream());
                        client.getOutputStream().flush();
                        
                        in.close();
                    }

                    if(line.equals("")) {
                        break;
                    }
                    
                    line = reader.readLine();
                }
                
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(Proxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
