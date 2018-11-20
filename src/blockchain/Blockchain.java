package blockchain;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Пользователь on 16.11.2018.
 */
class Blockchain {
    private LinkedList<String> messages = new LinkedList<String>();
    private volatile LinkedList<Block> blockchain = null;
    private boolean newFile = false;
    private int timeSpent;
    private int difficulty = 0;


    public Blockchain(String filePath) {
        File file = new File(filePath);//"D:\\hyperskillGit\\blockchain\\MyBlockchain");
        readBlockchainFromFile(file);

    }

    public void readBlockchainFromFile(File file) {
        try {
            newFile = file.createNewFile();
            if (newFile) {
                System.out.println("The new blockchain file was successfully created.");
                this.blockchain = new LinkedList<Block>();
                this.difficulty = 0;
            } else {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                this.blockchain = (LinkedList<Block>) ois.readObject();
                System.out.println("The Blockchain was read from the file");
                ois.close();
                fis.close();
                this.difficulty = getDifficultyFromFile(this.blockchain.peekLast().getHash());
            }
        } catch (IOException e) {
            System.out.println("Cannot create the file: " + file.getPath());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeBlockchainToFile(String filePath) {
        File file = new File(filePath);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(blockchain);
            oos.close();
            fos.close();
            System.out.println("The Blockchain was successfully written to the file");
            System.out.println("");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Cannot write to the file");
        }
    }

    public void setDifficulty() {
        Block b = this.blockchain.peekLast();
        if (b == null) {
            this.difficulty = 0;
        } else {
            if (getTimeSpent() < 10) {
                this.difficulty += 1;
                System.out.println("N was increased to " + this.difficulty);
            } else if (getTimeSpent() >= 60) {
                this.difficulty -= 1;
                System.out.println("N was decreased to " + this.difficulty);
            } else {
                System.out.println("N stays the same");
            }
        }
    }

    public synchronized int getDifficulty() {
        return this.difficulty;
    }

    public synchronized LinkedList<Block> getBlockchain() {
        return this.blockchain;
    }

    public synchronized boolean addBlock(Block block) {
        Block blockPrev = this.blockchain.peekLast();
        if (block != null) {
            if (blockPrev == null && block.getPrevHash().equals("0")) { //first block
                this.blockchain.add(block);
                return true;
            } else if (blockPrev.getHash().equals(block.getPrevHash())) { //block is valid
                this.blockchain.add(block);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public synchronized boolean isBlockchainValid() {
        int cnt = 0;
        if (!this.newFile) {
            for (int i = 0; i < this.blockchain.size() - 1; i++) {
                if (!this.blockchain.get(i).getHash().equals(this.blockchain.get(i + 1).getPrevHash())) {
                    cnt++;
                }
            }
        }
        return cnt == 0;
    }

    private int getDifficultyFromFile(String hash){
        int i = 0;
        while (hash.charAt(i) == '0'){
            i++;
        }
        return i;
    }

    public synchronized void addMessagesToTheBlockchain(String msg){
        this.messages.add(msg);
    }

    public synchronized void addMessagesToTheBlock(){
        this.blockchain.peekLast().writeMessagesStack(this.messages);
        this.messages.clear();
    }

    public void setTimeSpent(int timeSpent){
        this.timeSpent = timeSpent;
    }
    public int getTimeSpent() {
        return timeSpent;
    }



}
