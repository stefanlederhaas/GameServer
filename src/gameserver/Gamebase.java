/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver;

import java.util.LinkedList;

/**
 *
 * @author Stefan
 */
public abstract class Gamebase {
    
    
    public abstract String listen();
    
    public abstract void answer(String answer);
    
    public abstract boolean addUser(User u);
    
    
    
}
