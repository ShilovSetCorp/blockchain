package blockchain;

import java.io.InputStream;
import java.nio.channels.Channels;
import java.util.Scanner;

/**
 * Created by Пользователь on 19.11.2018.
 */
public class User extends Thread {
    private String name;
    private Message message;
    Scanner in = new Scanner(System.in);

    public User(String name){
        this.name = name;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            newMaessage();
            try {
                Main.blockchain.addMessagesToTheBlockchain(getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void newMaessage(){
        if(!this.isInterrupted()) {
            synchronized (User.class) {
                System.out.print(this.name + ": ");
                String data = in.nextLine();
                data = this.name + ": " + data;
                try {
                    this.message = new Message(data,Main.blockchain.setMsgID());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Message getMessage(){
        return this.message;
    }

}
