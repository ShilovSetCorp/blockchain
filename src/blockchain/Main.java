package blockchain;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LinkedList<Block> blockchain = null;
        boolean isNewFile = false;
        boolean blockchainValid = false;
        int difficulty = setDifficulty();
        File file = new File("D:\\hyperskillGit\\blockchain\\MyBlockchain");
        try {
             isNewFile = file.createNewFile();

            if(isNewFile){
                System.out.println("The new blockchain file was successfully created.");
                blockchain = new LinkedList<Block>();
                blockchainValid = true;
            } else {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                blockchain = (LinkedList<Block>) ois.readObject();
                System.out.println("The Blockchain was read from the file");
                ois.close();
                blockchainValid = isBlockchainValid(blockchain);
            }
        }catch (IOException e){
            System.out.println("Cannot create the file: " + file.getPath());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(blockchainValid) {
            for (int i = 0; i < 10; i++) {
                if (isNewFile && i == 0) {
                    blockchain.add(new Block(null, difficulty));
                } else {
                    blockchain.add(new Block(blockchain.peekLast(), difficulty));
                }

                System.out.println("Block:");
                System.out.println("Id: " + blockchain.peekLast().getId());
                System.out.println("Timestamp: " + blockchain.peekLast().getTimeStamp());
                System.out.println("Magic number: " + blockchain.peekLast().getMagicNumber());
                System.out.println("Hash of the previous block:");
                System.out.println(blockchain.peekLast().getPrevHash());
                System.out.println("Hash of the block:");
                System.out.println(blockchain.peekLast().getHash());
                System.out.println("Block was generating for " + blockchain.peekLast().getTimeSpent() + " seconds");
                System.out.println("");
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(blockchain);
                    oos.close();
                    System.out.println("The Blockchain was successfully written to the file");
                    System.out.println("");
                } catch (FileNotFoundException e) {
                    System.out.println("File not found");
                } catch (IOException e) {
                    System.out.println("Cannot write to the file");
                }
            }
        }else{
            System.out.println("Blockchain loaded from file is not valid.");
        }
    }

    public static int setDifficulty(){
        Scanner in = new Scanner(System.in);
        System.out.println("Set difficulty (number of zeros in the beginning of hash): ");
        return in.nextInt();
    }

    public static boolean isBlockchainValid(LinkedList<Block> bc){
        int cnt = 0;
        for (int i = 0; i < bc.size() - 1; i++){
            if(!bc.get(i).getHash().equals(bc.get(i+1).getPrevHash())){
                cnt++;
            }
        }
        return cnt==0;
    }
}