/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver.TicTacToe;

import gameserver.User;
import java.io.PrintWriter;
import java.net.ServerSocket;

/**
 *
 * @author Stefan
 */
public class TTT_Commands {
    
    public static boolean playerSet(User u,int row,int col,PrintWriter pw)
    {
        try {
           String s = "";
           s = String.format("playerSet{%s,%d,%d}",u.getUsername(),row,col);
           pw.println(s); 
           return true;
        } catch (Exception e) {
            System.out.println("cmd playerSet(): Connection lost!");
            return false;
        }
    }
    
    public static boolean keepAlive(PrintWriter pw)
    {
        try {
           String s = "keepAlive{}";
           pw.println(s); 
           return true;
        } catch (Exception e) {
            System.out.println("cmd keepAlive(): Connection lost!");
            return false;
        }
    }
    
    public static boolean hasWon(User u,PrintWriter pw)
    {
        try {
           String s = "";
           s = String.format("hasWon{%s}",(u==null)?"NULL":u.getUsername());
           pw.println(s); 
           return true;
        } catch (Exception e) {
            System.out.println("cmd hasWon(): Connection lost!");
            return false;
        }
    }
    
    public static boolean makeTurn(User u,PrintWriter pw)
    {
        try {
           String s = "";
           s = String.format("makeTurn{%s}",u.getUsername());
           pw.println(s); 
           return true;
        } catch (Exception e) {
            System.out.println("cmd makeTurn(): Connection lost!");
            return false;
        }
    }
    
    public static boolean sendMessage(User u,PrintWriter pw, String s)
    {
        try {
           s = String.format("sendMessage{%s}",s);
           pw.println(s); 
           return true;
        } catch (Exception e) {
            System.out.println("cmd sendMessage(): Connection lost!");
            return false;
        }
    }
    
}
