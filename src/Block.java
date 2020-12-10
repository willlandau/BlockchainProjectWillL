import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import static com.sun.xml.internal.stream.writers.XMLStreamWriterImpl.UTF_8;

public class Block {
    String previousHash;
    Transaction data;
    LocalDateTime timeStamp;
    int nonce;
    String hash;

    //constructor
    public Block(Transaction data, String previousHash, LocalDateTime timestamp){
        this.previousHash= previousHash;
        this.data = data;
        this.timeStamp = timestamp;
        this.nonce = ((int) (Math.random()*100));
        this.hash = null;

    }

    public String calculateBlockHash() {
        String dataToHash = previousHash + timeStamp.toString() + Integer.toString(nonce) + data.toStringForHash();
        MessageDigest digest = null;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes(UTF_8));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            System.out.println("The encoding is not supported");
        }
        StringBuffer buffer = new StringBuffer();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }


    public boolean treatySC (ArrayList<Block> blockChain){

        if(blockChain.size()>2) {
            ArrayList<Transaction> check = new ArrayList<Transaction>();
            check = retrieveProvenance(this.getData().artefact.getArtid(), blockChain);// uses retriveProvenance to fill the arraylist with all instances of that artefact
            int counter = 0; //counting how many after 2001
            for (int i = 0; i < check.size(); i++) {
                boolean is = check.get(i).getTime().isAfter(LocalDateTime.parse("2000-12-31T12:59:59"));//checks if the time is after 2001
                if (is == true) {
                    counter++;
                }
            }
            //checking if there has been enough transactions after 2001
            if (counter < 2) {
                System.out.println("Does not have enough transactions in the blockchain");
                return false;
            }
        }
            //checking if the buyer has the money
            if(this.getData().getBuyer().balance<this.getData().getPrice()){
                System.out.println("Buyer does not have enough money in balance");
                return false;
            }

        //executing the money transfers
        this.getData().getBuyer().setBalance(this.getData().getBuyer().getBalance()-this.getData().getPrice());
        this.getData().getAuctionHouse().setBalance(this.getData().getAuctionHouse().getBalance()
                +(this.getData().getPrice()*.1));
        this.getData().getArtefact().getCountryOfOrigin().setBalance
                (this.getData().getArtefact().getCountryOfOrigin().getBalance() + (this.getData().getPrice()*.2));
        this.getData().getSeller().setBalance(this.getData().getSeller().getBalance()+(this.getData().getPrice()*.7));
        this.data.getArtefact().setOwner(this.data.getBuyer());

        return true;
    }

    public ArrayList<Transaction> retrieveProvenance(String id, ArrayList<Block> blockChain){
        ArrayList<Transaction> list = new ArrayList<Transaction>();
        for (int i = 0; i < blockChain.size(); i++) {
            if (blockChain.get(i).getData().getArtefact().getArtid() == id){
                list.add(blockChain.get(i).getData());
            }
        }
        return list;
    }

    public ArrayList<Transaction> retrieveProvenance(String id, ArrayList<Block> blockChain, String thetimestamp){
        ArrayList<Transaction> list = new ArrayList<Transaction>();
        for (int i = 0; i < blockChain.size(); i++) {
            if (blockChain.get(i).getData().getArtefact().getArtid() == id && blockChain.get(i).getTimestamp().isAfter(LocalDateTime.parse(thetimestamp))) {
                list.add(blockChain.get(i).getData());
            }
            }
        return list;
    }

    public boolean mineBlock(int prefix, ArrayList<Block> blockChain){

        String prefixString = new String(new char[prefix]).replace('\0', '0');


        boolean begin = this.treatySC(blockChain);
        if (!begin){//if the transaction didn't go through abort the mining
            System.out.println("Could not complete transaction");
            return false;
        }
        boolean jackpot = false;
        while(!jackpot){
            this.nonce++;//add to the nonce
            String xhash = this.calculateBlockHash(); //finds the new hash with the new nonce
            String challenger = xhash.substring(0,prefix); //isolates the first four characters of the new hash
            if(challenger.equals(prefixString)){ //checks if the new nonce is what we are looking for
                jackpot=true;
                this.hash=xhash; //if it is correct, set the new hash as the one for all time
            }
        }
        return true;
    }



    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public Transaction getData() {
        return data;
    }

    public void setData(Transaction data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timeStamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timeStamp = timestamp;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
