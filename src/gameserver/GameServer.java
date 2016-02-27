/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver;

import gameserver.TicTacToe.Tictactoe;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.net.ssl.SSLSocket;


public class GameServer {

   
    public static void main(String[] args) {
        try {
            Tictactoe ttt = new Tictactoe(1400);
            Thread t = new Thread(ttt);
            t.start();
            
            Socket s1,s2;
            s1 = new Socket("localhost", 1400);
            s2 = new Socket("localhost",1400);
            
            BufferedReader br = new BufferedReader(new InputStreamReader(s1.getInputStream()));
            BufferedReader br2 = new BufferedReader(new InputStreamReader(s2.getInputStream()));
            
            
            
           while(t.isAlive())
           {
               
               System.out.println("P1: "+br.readLine());
               System.out.println("P2: "+br2.readLine());
           }
            
        } catch (IOException ex) {
            
        }
    }
    
}
