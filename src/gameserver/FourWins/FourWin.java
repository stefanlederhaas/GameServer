/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver.FourWins;

import gameserver.Gamebase;
import gameserver.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 *
 * @author philipp
 */
public class FourWin implements Gamebase, Runnable {

    private ServerSocket server;
    private int port;
    private Logger logger;
    private LinkedList<FourWinPlayer> player = new LinkedList<>();

    public FourWin(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        int playerCount = 0;
        boolean running = true;
        int i = 1;
    }

    @Override
    public String getServerName() {
        return "FourWin_at_" + port;
    }

    class GameLogic {

        private byte[][] field = new byte[6][7];

        public GameLogic() {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    field[i][j] = 0;
                }
            }
        }

        public void set(int r, int c, int p) {
            field[r][c] = (byte) p;
        }

        public boolean hasWon(int player) {
            return false;
        }

        private boolean isAllSet() {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    if (field[i][j] == 0) {
                        return false;
                    }
                }
            }
            return true;
        }

    }

    class FourWinPlayer implements CommandsToPlayer {

        protected Socket socket;
        private PrintWriter pw;
        private int number;
        private BufferedReader br;

        public FourWinPlayer(Socket socket) throws IOException {
            this.socket = socket;
            this.pw = new PrintWriter(socket.getOutputStream());
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        
        public String listen()
        {
            return "";
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        @Override
        public void keepAlive() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void hasWon() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void hasLost() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void hasDrawn() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void playerSet(int r, int c) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void makeTurn() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void sendMessage(String msg) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        
        
    }

}
