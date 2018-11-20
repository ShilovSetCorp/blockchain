package blockchain;


import java.io.UnsupportedEncodingException;
import java.time.LocalTime;

/**
 * Created by Пользователь on 16.11.2018.
 */
public class Miner extends Thread {
    private int minerNumber;


    public Miner(int minerNumber) {
        this.minerNumber = minerNumber;
    }


    @Override
    public void run() {
        LocalTime start = LocalTime.now();
        while (!Main.blockchain.addBlock(new Block(Main.blockchain.getBlockchain().peekLast(), Main.blockchain.getDifficulty()))) { }
        Main.blockchain.addMessagesToTheBlock();
        LocalTime finish = LocalTime.now();
        Main.blockchain.setTimeSpent(finish.toSecondOfDay() - start.toSecondOfDay());
        System.out.println("");
        System.out.println("Block:");
        System.out.println("Created by miner # " + this.minerNumber);
        try {
            Main.blockchain.getBlockchain().peekLast().printOutResults();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("Block was generating for " + Main.blockchain.getTimeSpent() + " seconds");
        Main.blockchain.setDifficulty();
    }


}


