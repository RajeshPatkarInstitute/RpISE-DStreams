package rpise_dstream;

import java.io.*;
import java.net.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ManikaS
 */
public class StreamingRoom extends Thread{

    ServerSocket ss;
    public ArrayList<PrintWriter> nosList = new ArrayList<>();
    public MessageQueue<String> q = new MessageQueue<>();
    public static BufferedReader nis;
    public static PrintWriter nos;
    private  String roomName ;

    StreamingRoom(ServerSocket soc) throws IOException  {
        this.ss = soc;
       // init(soc);
    }

    StreamingRoom(Socket ss) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //public void init(ServerSocket soc) throws IOException 
    @Override
    public void run()
    {
    try
        {
            System.out.println(" Connection establishing .."); 
            MessageDispatcher md = new MessageDispatcher();
            System.out.println("Staring Message Dispathcer for room "+this.getRoomName());
            md.setRoom(this);
            md.setDaemon(true);
            md.start();

            for (int i = 0; i< 5; i++)
            {
                System.out.println("Waiting for Client Connection in ROOM "+this.roomName+ this.getRoomName()+" ....");
                 Socket ss = this.ss.accept();
                System.out.println("Client Connection established for room " + this.getRoomName()+i);
                Conversation c = new Conversation(ss,i,this);
                c.start();
            }
            System.out.println("{{ *** COMPLETED POOL: EXITING ROOM ** }}"+this.roomName);

        }
        catch (Exception ex) {
            Logger.getLogger(Conversation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getRoomName ()
    {
        return roomName;
    }
    public void setRoomName ( String roomName)
    {
        this.roomName =roomName;
    }
}

class MessageQueue<T> {
    ArrayList<T> al = new ArrayList<>();
    synchronized public void enqueue(T queueStream) {
        al.add(queueStream);
        notify();
    }

    synchronized public T dequeue ()
    {
     if ( al.isEmpty())   
     {
         try {
             wait();
         } catch (Exception ex) {
             System.out.println(" Dqueue stream... " + ex);
         }
     }
        return  al.remove(0);
    }
    
    synchronized public void print()
    {
        for (T i : al)
        {
            System.out.println(" -----> " + i);
        }
    }
}
