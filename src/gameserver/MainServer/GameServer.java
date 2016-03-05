/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver.MainServer;

import gameserver.TicTacToe.Tictactoe;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class GameServer {

    private int nextFreePort = 5000;
    private boolean running = true;
    private HashMap<Integer, Thread> games = new HashMap<>();

    public void clear() throws IOException {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public boolean isRunning() {
        return running;
    }

    public void createGame() throws IOException {
        byte b = 0;
        int i = 0;

        clear();
        System.out.println("Which would you like to start?");
        System.out.println("1) TicTacToe");
        System.out.println("2) 4 Wins");
        System.out.println("3) Black Jack");
        System.out.println("4) Texas Hold'em");
        System.out.println("5) sixty-six");
        System.out.print("Your choice: ");
        Scanner scan = new Scanner(System.in);
        while (!scan.hasNextByte()) {
        }
        b = scan.nextByte();
        do {
            if (i != 0) {
                nextFreePort++;
            }
            i++;
        } while (games.containsKey(new Integer(nextFreePort)));

        switch (b) {
            case 1:
                Thread t = new Thread(new Tictactoe(nextFreePort));
                t.start();
                games.put(new Integer(nextFreePort), t);
                System.out.println("TicTacToe started at: " + nextFreePort);
                break;
            default:

        }
        nextFreePort++;

    }

    public void manageGame() {
        System.out.println("MG");
        if(games!=null)
        {
            for(int i = 0;i<games.size();i++)
            {
                System.out.println(games.get(5001).getId()+": "+games.get(5001).getName());
            }
        }
        else
        {
            System.out.println("There are currently no Games running!");
        }
        
    }

    public void stopServer_S() {
        System.out.println("SS");
    }

    public void killAll() {
        running = false;
    }

    public void exit() {
        for (Thread t : games.values()) {
            t.interrupt();
        }
        running = false;
    }

    public byte printMainMenu() {
        System.out.println("What would you wish to do?");
        System.out.println("1) Create new game");
        System.out.println("2) Manage Running game");
        System.out.println("3) Stop server(s)");
        System.out.println("4) Kill'em all");
        System.out.println("5) Safe exit");
        System.out.print("Your choice: ");
        Scanner scan = new Scanner(System.in);
        while (!scan.hasNextByte()) {
        }
        return scan.nextByte();

    }

    public static void main(String[] args) {
        GameServer server = new GameServer();
        do {
            try {

                server.clear();
                byte b = server.printMainMenu();
                switch (b) {
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

        } while (server.isRunning());
    }

}
