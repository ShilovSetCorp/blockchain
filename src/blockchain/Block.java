package blockchain;

import java.security.MessageDigest;
import java.time.LocalDateTime;


public class Block {
    private String prevHash;
    private String hash;
    private int id;
    private LocalDateTime timeStamp;

    public Block(Block prevblock){
        if(prevblock == null) {
            this.prevHash = "0";
            this.id = 1;
        }
        else {
            this.prevHash = prevblock.getHash();
            this.id = prevblock.getId() + 1;
        }
        this.hash = applySha256(this.prevHash);
        this.timeStamp = LocalDateTime.now();
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

    public LocalDateTime getTimeStamp(){
        return timeStamp;
    }

    public static String applySha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
