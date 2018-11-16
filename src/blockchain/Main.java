package blockchain;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        String filePath = "D:\\hyperskillGit\\blockchain\\MyBlockchain";
        Blockchain blockchain = new Blockchain(filePath);
        Thread[] miners = new Miner[20];

        if(blockchain.isBlockchainValid()) {
            for (int i = 0; i < miners.length; i++) {
                miners[i] = new Miner(blockchain, i+1);
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