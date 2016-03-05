/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver.TicTacToe;

/**
 *
 * @author Stefan
 */
public interface CommandsToPlayer {
    
    public void hasWon();
    public void hasLost();
    public void hasDrawn();
    public void playerSet(int r, int c);
    public void makeTurn();
    public void sendMessage(String msg);
    
}
