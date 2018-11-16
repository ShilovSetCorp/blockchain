package blockchain;

import java.io.Serializable;
import java.time.LocalTime;

import java.security.MessageDigest;
import java.util.Date;
import java.util.Random;



class Block implements Serializable{

    private String prevHash;
    private String hash;
    private int id;
    private long timeStamp;
    private int magicNumber;
    private int timeSpent;

    public Block(Block prevblock, int difficulty){
        LocalTime start = LocalTime.now();
        if(prevblock == null) {
            this.prevHash = "0";
            this.id = 1;
        }
        else {
            this.prevHash = prevblock.getHash();
            this.id = prevblock.getId() + 1;
        }
        String zeros = "";
        for (int i = 0; i < difficulty; i++){
            zeros += "0";
        }

        do {
            setMagicNumber();
            this.hash = applySha256(this.prevHash + this.magicNumber);
        }while(!this.hash.startsWith(zeros));
        LocalTime finish = LocalTime.now();
        this.timeSpent = finish.toSecondOfDay() - start.toSecondOfDay();
        this.timeStamp = new Date().getTime();
    }

    public synchronized void printOutResults(){
        System.out.println("Id: " + this.getId());
        System.out.println("Timestamp: " + this.getTimeStamp());
        System.out.println("Magic number: " + this.getMagicNumber());
        System.out.println("Hash of the previous block:");
        System.out.println(this.getPrevHash());
        System.out.println("Hash of the block:");
        System.out.println(this.getHash());
        System.out.println("Block was generating for " + this.getTimeSpent() + " seconds");
    }


    private synchronized void setMagicNumber() {
        this.magicNumber = new Random().nextInt(Integer.MAX_VALUE);
    }

    public synchronized int getId() {
        return id;
    }

    public synchronized String getHash() {
        return hash;
    }

    public synchronized String getPrevHash() {
        return prevHash;
    }

    public synchronized long getTimeStamp(){
        return timeStamp;
    }

    public synchronized int getTimeSpent() {
        return timeSpent;
    }

    public synchronized String applySha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte i : hash) {
                String hex = Integer.toHexString(0xff & i);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized int getMagicNumber(){
        return this.magicNumber;
    }
}
