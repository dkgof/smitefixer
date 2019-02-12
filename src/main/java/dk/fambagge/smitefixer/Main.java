/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.fambagge.smitefixer;

import java.io.File;

/**
 *
 * @author rolf
 */
public class Main {
    //public static Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 3128));
    //urlS = urlS.replace("", "69.16.175.10");

    public static void main(String[] args) {
        File baseDir = new File("D:\\Misc\\Documents\\My Games\\Smite\\BattleGame\\Downloaded");

        Proxy proxy = new Proxy();
        
        proxy.start();
    }
}
