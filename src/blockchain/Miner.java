package blockchain;

import java.util.LinkedList;

/**
 * Created by Пользователь on 16.11.2018.
 */
public class Miner extends Thread{
    private Blockchain blockchain;
    private int minerNumber;

    public Miner(Blockchain blockchain,int minerNumber){
        this.blockchain = blockchain;
        this.minerNumber = minerNumber;
    }


    @Override
    public void run(){

        synchronized (Blockchain.class) {
            boolean blockIsValid = blockchain.addBlock(new Block(blockchain.getBlockchain().peekLast(), blockchain.getDifficulty()));

            if (blockIsValid) {
                System.out.println("");
                System.out.println("Block:");
                System.out.println("Created by miner # " + this.minerNumber);
                blockchain.getBlockchain().peekLast().printOutResults();
                blockchain.setDifficulty();
            } else {
                System.out.println("Generated block is not valid, so no block was added to the blockchain");
            }
        }

    }


}
