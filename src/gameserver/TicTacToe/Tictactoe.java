/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver.TicTacToe;

import gameserver.MainServer.Gamebase;
import gameserver.Util.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;

/**
 *
 * @author Stefan
 */
public class Tictactoe implements Runnable, Gamebase {

    private final LinkedList<TicTacToePlayer> player = new LinkedList<>();
    private final int port;
    private final Logger logger;
    private ServerSocket socket;
    private Socket client;

    public Tictactoe(int port) throws IOException {
        this.port = port;
        logger = new Logger(this);

    }

    int playerCount = 0;
    boolean running = true;
    TicTacToePlayer p1;
    TicTacToePlayer p2;

    @Override
    public void run() {

        boolean inter = false;
        int i = 1;
        try {
            Gamelogic gamelogic = new Gamelogic();
            socket = new ServerSocket(port);

            //Timeout for accept. if timout is reached a SocketTimeoutException will be thrown and therefore
            //the loop will continue as the condition says and it's possible to interrupt the server at runtime
            socket.setSoTimeout(500);
            while (playerCount != 2 && !inter) {

                try {
                    logger.log("Waiting for players");
                    client = socket.accept();
                    TicTacToePlayer p = new TicTacToePlayer(client);
                    player.add(p);
                    playerCount++;
                } catch (SocketTimeoutException sex) {
                    //if an interrupt occurs make the server stop by setting the boolean inter to true
                    if (Thread.interrupted()) {
                        inter = true;
                    }
                }
            }

            //if an interrupt occured exit this method
            if (inter) {
                return;
            }

            if (running) {
                //Start Game
                player.get(0).sendMessage("You are X");
                player.get(0).setNumber(1);
                player.get(1).sendMessage("You are O");
                player.get(1).setNumber(-1);

                p1 = player.get(0);
                p2 = player.get(1);
            }
            logger.log("Players successfully connected");

            while (running && !Thread.interrupted()) {

                int r = 0, c = 0;
                String l = "";
                if (i > 0) {
                    p1.makeTurn();
                    l = p1.listen();

                    switch (l) {
                        case "kA":
                            i *= -1;
                            break;
                        case "q":
                            running = false;
                            break;
                        case "ERR":

                            running = false;
                            p2.sendMessage("ERROR DURING GAME");
                            break;
                        default: {

                            String[] s = l.split("{");
                            s = s[1].split("}");
                            s = s[0].split(",");
                            r = Integer.parseInt(s[0]);
                            c = Integer.parseInt(s[1]);

                            gamelogic.set(r, c, p1.getNumber());
                        }
                    }

                } else {
                    p2.makeTurn();
                    l = p2.listen();
                    switch (l) {
                        case "kA":
                            i *= -1;
                            break;
                        case "q":
                            running = false;
                            break;
                        case "ERR":
                            running = false;
                            p1.sendMessage("ERROR DURING GAME");
                            break;
                        default: {

                            String[] s = l.split("{");
                            s = s[1].split("}");
                            s = s[0].split(",");
                            r = Integer.parseInt(s[0]);
                            c = Integer.parseInt(s[1]);

                            gamelogic.set(r, c, p2.getNumber());
                        }
                    }
                }

                boolean p1w = gamelogic.hasWon(p1.getNumber());
                boolean p2w = gamelogic.hasWon(p2.getNumber());
                if (p1w) {
                    p1.hasWon();
                    p2.hasLost();
                    running = false;
                }
                if (p2w) {
                    p2.hasWon();
                    p1.hasLost();
                    running = false;
                }
                if (!p1w && !p2w && gamelogic.isAllSet()) {
                    p1.hasDrawn();
                    p2.hasDrawn();
                    running = false;
                }

                if (running) {

                    p1.keepAlive();
                    p2.keepAlive();
                }

                logger.log("running = " + running);
                i *= -1;

            }

        } catch (IOException ex) {
            try {
                logger.log(ex.toString());
            } catch (IOException ex1) {
                System.out.println(ex1.toString());
            }

        } finally {
            try {
                logger.close();
            } catch (IOException ex) {

            }
        }

    }

    @Override
    public String getServerName() {
        return "TicTacToe_at_" + port;
    }

    class Gamelogic {

        private byte[][] field = new byte[3][3];

        public Gamelogic() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    field[i][j] = 0;
                }
            }

        }

        public void set(int r, int c, int p) {
            field[r][c] = (byte) p;
        }

        public boolean isSetPossible(int row, int col) {
            return field[row][col] == 0;
        }

        public boolean hasWon(int player) {
            for (int i = 0; i < 3; i++) {
                int summ = (field[i][0] + field[i][1] + field[i][2]);

                if ((summ / 3) == player) {
                    return true;
                }
            }

            for (int i = 0; i < 3; i++) {
                int summ = (field[0][i] + field[1][i] + field[2][i]);

                if ((summ / 3) == player) {
                    return true;
                }
            }

            if ((field[0][0] + field[1][1] + field[2][2]) / 3 == player) {
                return true;
            }

            if ((field[0][2] + field[1][1] + field[2][0]) / 3 == player) {
                return true;
            }

            return false;

        }

        private boolean isAllSet() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (field[i][j] == 0) {
                        return false;
                    }
                }
            }

            return true;
        }
    }

    class TicTacToePlayer implements CommandsToPlayer {

        protected Socket socket;
        private PrintWriter pw;
        private int number;
        private BufferedReader br;

        public TicTacToePlayer(Socket socket) throws IOException {
            this.socket = socket;
            logger.log("HostAddr=" + socket.getInetAddress().getHostAddress());
            pw = new PrintWriter(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw.flush();
        }

        public String listen() throws IOException {
            String line = br.readLine();

            if (line.contains("keepAlive")) {
                return "kA";
            }
            if (line.contains("quit")) {
                return "q";
            }
            if (line.contains("set")) {
                return line;
            }
            return "ERR";
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }

        @Override
        public void keepAlive() {
            pw.println("keepAlive{}");
            pw.flush();

        }

        @Override
        public void hasWon() {
            pw.println("hasWon{}");
            pw.flush();
        }

        @Override
        public void hasDrawn() {
            pw.println("hasDrawn{}");
            pw.flush();
        }

        @Override
        public void playerSet(int r, int c) {
            pw.printf("playerSet{%d,%d}\n", r, c);
            pw.flush();
        }

        @Override
        public void makeTurn() {
            pw.println("makeTurn{}");
            pw.flush();
        }

        @Override
        public void sendMessage(String msg) {
            pw.println("message{" + msg + "}");
            pw.flush();
        }

        @Override
        public void hasLost() {
            pw.println("hasLost{}");
            pw.flush();
        }

    }

}
