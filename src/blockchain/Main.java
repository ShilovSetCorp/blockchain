package blockchain;

public class Main {
    public static void main(String[] args) {
        Block block = null;
        for (int i = 0; i < 3; i++){
            block = new Block(block);
            System.out.println("Block:");
            System.out.println("Id: " + block.getId());
            System.out.println("Timestamp: " + block.getTimeStamp());
            System.out.println("Hash of the previous block:");
            System.out.println(block.getPrevHash());
            System.out.println("Hash of the block:");
            System.out.println(block.getHash());
            System.out.println("");
        }
    }
}