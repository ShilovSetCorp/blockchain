package blockchain;

import java.util.LinkedList;

/**
 * Created by Пользователь on 16.11.2018.
 */
public class Miner extends Thread{
    private int minerNumber;

    public Miner(int minerNumber){
        this.minerNumber = minerNumber;
    }


    @Override
    public void run(){

        synchronized (Block.class) {
            boolean blockIsValid = Main.blockchain.addBlock(new Block(Main.blockchain.getBlockchain().peekLast(), Main.blockchain.getDifficulty()));
            if (blockIsValid) {
                System.out.println("");
                System.out.println("Block:");
                System.out.println("Created by miner # " + this.minerNumber);
                Main.blockchain.getBlockchain().peekLast().printOutResults();
                Main.blockchain.setDifficulty();
            } else {
                System.out.println("Generated block is not valid, so no block was added to the blockchain");
            }
        }

    }


}
