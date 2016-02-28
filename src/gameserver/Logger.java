/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author philipp
 */
public class Logger {
    
    BufferedWriter bw;
    
    public Logger(Gamebase server) throws IOException
    {
        File f = new File("Logs/"+server.getServerName());
        if(!f.exists())
            f.createNewFile();
        bw = new BufferedWriter(new FileWriter(f));
    }
    
    public void log(String s) throws IOException
    {
        bw.write(s);
        bw.flush();
    }
    
}
