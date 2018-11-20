package blockchain;

import java.io.Serializable;
import java.time.LocalTime;

import java.security.MessageDigest;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;



class Block implements Serializable{

    private List<String> messagesStack = new LinkedList<String>();
    private String prevHash;
    private String hash;
    private int id;
    private long timeStamp;
    private int magicNumber;

    public Block(Block prevBlock, int difficulty){

        if(prevBlock == null) {
            this.prevHash = "0";
            this.id = 1;
        }
        else {
            this.prevHash = prevBlock.getHash();
            this.id = prevBlock.getId() + 1;
        }
        String zeros = "";
        for (int i = 0; i < difficulty; i++){
            zeros += "0";
        }

        do {
            setMagicNumber();
            this.hash = applySha256(this.prevHash + this.magicNumber);
        }while(!this.hash.startsWith(zeros));

        this.timeStamp = new Date().getTime();
    }

    public void printOutResults() {
        System.out.println("Id: " + this.getId());
        System.out.println("Timestamp: " + this.getTimeStamp());
        System.out.println("Magic number: " + this.getMagicNumber());
        System.out.println("Hash of the previous block:");
        System.out.println(this.getPrevHash());
        System.out.println("Hash of the block:");
        System.out.println(this.getHash());
        if (this.id == 1 || messagesStack.isEmpty()){
            System.out.println("Block data: no messages");
        }else{
            System.out.println("Block data: ");
            for(String s: messagesStack) {
                System.out.println(s);
            }
        }
    }


    private void setMagicNumber() {
        this.magicNumber = new Random().nextInt(Integer.MAX_VALUE);
    }

    public int getId() {
        return id;
    }

    public String getHash() {
        return hash;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public long getTimeStamp(){
        return timeStamp;
    }


    public String applySha256(String input){
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

    public int getMagicNumber(){
        return this.magicNumber;
    }

    public void writeMessagesStack(LinkedList<String> messagesStack){
        this.messagesStack.addAll(messagesStack);
    }

}
