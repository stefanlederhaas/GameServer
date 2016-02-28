/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver;

import java.io.Console;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GameServer {

    private boolean running=true;
    private LinkedList<Thread> games = new LinkedList<>();
    
    public void clear() throws IOException
    {
        System.out.print("\033[H\033[2J"); 
       
    }

    public boolean isRunning() {
        return running;
    }
    
   
    public void createGame()
    {
        System.out.println("New Game");
    }
    
    public void manageGame()
    {
        System.out.println("MG");
    }
    
    public void stopServer_S()
    {
        System.out.println("SS");
    }
    
    public void killAll()
    {
        running = false;
    }
    
    public void exit()
    {
        running = false;
    }
    
    public byte printMainMenu()
    {
        System.out.println("What would you wish to do?");
        System.out.println("1) Create new game");
        System.out.println("2) Manage Running game");
        System.out.println("3) Stop server(s)");
        System.out.println("4) Kill'em all");
        System.out.println("5) Safe exit");
        System.out.print("Your choice: ");
        return new Scanner(System.in).nextByte();
        
    }
    
    public static void main(String[] args) {
        GameServer server = new GameServer();
        do{
            try {
                
                server.clear();
                byte b = server.printMainMenu();
                switch(b)
                {
                    case 1:
                        server.createGame();
                        break;
                    case 2:
                        server.manageGame();
                        break;
                    case 3:
                        server.stopServer_S();
                        break;
                    case 4:
                        server.killAll();
                        break;
                    case 5:
                        server.exit();
                        break;
                        
                }
            } catch (IOException ex) {
                System.out.println("Clear didn't wort exiting now");
            }
            
        }while(server.isRunning());
    }
    
}
