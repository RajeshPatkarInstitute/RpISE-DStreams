package rpise_dstream;

import java.io.PrintWriter;

public class MessageDispatcher extends Thread{
    StreamingRoom room;
    
    public void setRoom(StreamingRoom room )
    {
        this.room=room;
    }
    @Override
    public void run()
    {
        while(true)
        {
           try{
            String str = this.room.q.dequeue();
            for (PrintWriter o : this.room.nosList )
            {
                o.println(str);
                System.out.println("Broadcasting:"+this.room.getRoomName()+str);
            }
           } catch ( Exception e) {}
        }
    }
}
