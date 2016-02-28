/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver.Util;

import gameserver.MainServer.Gamebase;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author philipp
 */
public class Logger {
    
    private BufferedWriter bw;
    private final String path = System.getProperty("user.dir");
    
    public Logger(Gamebase server) throws IOException
    {
        
        File f = new File(path+File.separator+server.getServerName());
    
        if(!f.exists())
            f.createNewFile();
        bw = new BufferedWriter(new FileWriter(f, true));
    }
    
    public void close() throws IOException
    {
        bw.close();
    }
    
    public void log(String s) throws IOException
    {
        bw.write(s);
        bw.newLine();
        bw.flush();
    }
    
}
