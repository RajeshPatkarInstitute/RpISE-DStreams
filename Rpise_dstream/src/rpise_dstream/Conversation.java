package rpise_dstream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conversation extends Thread {

    Socket soc;
    int index;
    StreamingRoom room;

    Conversation(Socket soc, int i, StreamingRoom room) {
        this.soc = soc;
        this.index = i;
        this.room = room;
    }

    @Override
    public void run() {
        try {
            BufferedReader nis = new BufferedReader(
                    new InputStreamReader(
                            soc.getInputStream()));

            PrintWriter nos = new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(
                            soc.getOutputStream())), true
            );
            System.out.println("Entered Conversation Thread:" + this.index + "of Room:" + this.room);
            this.room.nosList.add(nos);
            String str = nis.readLine();

            while (!str.equalsIgnoreCase("End")) {
                this.room.q.enqueue(str);
                System.out.println("Conv>" + this.index + " User Said:" + str);
                str = nis.readLine();
            }
            nos.println("End");
            this.room.nosList.remove(nos);
            /*
            while (true) {
                String str;
                str = nis.readLine();
                if (str.equals("End")) {
                    return;
                } else {
                    this.room.q.enqueue(str);
                    System.out.println("Conv>" + this.index + " User Said:" + str);
                     str = nis.readLine();
                } 
            }
            
             */
        } catch (Exception ex) {
            Logger.getLogger(Conversation.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
