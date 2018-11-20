package blockchain;

import java.io.InputStream;
import java.nio.channels.Channels;
import java.util.Scanner;

/**
 * Created by Пользователь on 19.11.2018.
 */
public class User extends Thread {
    private String name;
    private String message;
    Scanner in = new Scanner(System.in);

    public User(String name){
        this.name = name;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            newMaessage();
            Main.blockchain.addMessagesToTheBlockchain(getMessage());
        }
    }

    private synchronized void newMaessage(){
        if(!this.isInterrupted()) {

            String s = in.nextLine();
            this.message = this.name + ": " + s;
        }
    }

    public String getMessage(){
        return this.message;
    }

}
