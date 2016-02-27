/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver.TicTacToe;

import gameserver.Gamebase;
import gameserver.User;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Stefan
 */
public class Tictactoe extends Gamebase implements Runnable{
        
        private User u1;
        private User u2;
        
    class Gamelogic{
        
        private byte[][] field = new byte[3][3];
        private User u1;
        private User u2;
        
        public Gamelogic(User u1,User u2)
        {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    field[i][j] = 0;       
                } 
            }
            
         this.u1 = u1;
         this.u2 = u2;
        }
        
        public boolean isSetPossible(int row,int col)
        {   
            return field[row][col]==0;
        }
        
        public User hasWon()
        {
            for (int i = 0; i < 3; i++) {
                int summ = (field[i][0]+field[i][1]+field[i][2]);
                
                if(summ==3)
                {
                    return u1;
                }
                else if(summ==-3)
                {
                    return u2;
                }
            }
            
            for (int i = 0; i < 3; i++) {
                int summ = (field[0][i]+field[1][i]+field[2][i]);
                
                if(summ==3)
                {
                    return u1;
                }
                else if(summ==-3)
                {
                    return u2;
                }
            }
            
            if(field[0][0]+field[1][1]+field[2][2] == 3)
            {
                return u1;
            }
            else if(field[0][0]+field[1][1]+field[2][2] == -3)
            {
                return u2;
            }
            
            if(field[0][2]+field[1][1]+field[2][0] == 3)
            {
                return u1;
            }
            else if(field[0][0]+field[1][1]+field[2][2] == -3)
            {
                return u2;
            }

              return null;      
            
        }
        
        private boolean isAllSet()
        {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(field[i][j] == 0)
                        return false;    
                } 
            }
            
            return true;
        }
    }
    
    
    private ServerSocket socket;
    private boolean isGameRunning;
    private Socket cs1,cs2;
    
    public Tictactoe(int port,InetAddress bindingAddress) throws IOException
    {
        socket = new ServerSocket(port,2,bindingAddress);
        isGameRunning = true;
    }
    
    

    @Override
    public String listen() {
     
      return null;
    }

    @Override
    public void answer(String answer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addUser(User u) {
        if(u1==null)
        {
            u1 = u;
            return true;
        }
        else if(u2==null)
        {
            u2 = u;
            return true;
        }
        
        return false;
        
    }

    @Override
    public void run() {
        do
        {
           String str = listen();  
            
            
            
        }while(isGameRunning);
        
    }
    
}
