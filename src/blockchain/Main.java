package blockchain;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static Blockchain blockchain;
    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        String filePath = in.nextLine();//"D:\\hyperskillGit\\blockchain\\MyBlockchain";
        blockchain= new Blockchain(filePath);
        List<Thread> miners = new ArrayList<>();

        if(blockchain.isBlockchainValid()) {
            for (int i = 0; i < 8; i++) {
                miners.add(new Miner(i+1));
            }
            for (Thread miner: miners){
                miner.start();
            }
            for (Thread miner: miners){
                miner.join();
            }
            blockchain.writeBlockchainToFile(filePath);
        }
        else {
            System.out.println("Blockchain loaded from file is not valid.");
        }
    }
}