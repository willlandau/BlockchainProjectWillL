import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main{

    public static void main(String[] args) {

        ArrayList<Block> blockchain = new ArrayList<>();
        int prefix = 4;   //we want our hash to start with four zeroes
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        boolean readytomine = false; //boolean to leave the menu phase
        Scanner Kb = new Scanner(System.in);
        System.out.println("Welcome to the Artefact Management Blockchain");
        //creating Arraylists for the currently made stakeholders, artefacts and transactions
        ArrayList<Stakeholder> stakeholders = new ArrayList<>();
        ArrayList<Artefact> artefacts = new ArrayList<>();
        ArrayList<Transaction> transactions = new ArrayList<>();
        /*stakeholders.add(new Stakeholder("will", 342, "fda", 10000.0));
        stakeholders.add(new Stakeholder("can" , 321, "fd" , 10000.0));
        stakeholders.add(new Stakeholder("USA", 453, "fdksl", 900000.0));
        stakeholders.add(new Stakeholder("House", 3442, "fdhsj", 8190.0));
        artefacts.add(new Artefact("book", "fdfsd" , stakeholders.get(2), stakeholders.get(1)));
        transactions.add(new Transaction(artefacts.get(0), stakeholders.get(0), stakeholders.get(1), stakeholders.get(3), 5.0));
        transactions.add(new Transaction(artefacts.get(0), stakeholders.get(1), stakeholders.get(0), stakeholders.get(3), 6.0));
        transactions.add(new Transaction(artefacts.get(0), stakeholders.get(0), stakeholders.get(1), stakeholders.get(3), 7.0));*/


        while (readytomine == false) {
            System.out.println("Menu:");
            System.out.println("1: Create Stakeholder");
            System.out.println("2: Create Artifact");
            System.out.println("3: Create Transaction");
            System.out.println("4: Ready to begin");
            System.out.println("Enter your choice (1,2,3,4):");
            int choice = Kb.nextInt();
            if (choice == 1) {
                //collecting data for a new stakeholder (buyer, seller, country of origin and sellling house)
                Scanner tests = new Scanner(System.in);
                System.out.println("Enter Name:");
                String name = tests.nextLine();
                System.out.println("Enter Address (one line):");
                String address = tests.nextLine();
                System.out.println("Enter Balance:");
                Double balance = Double.parseDouble(tests.nextLine());
                System.out.println("Enter ID");
                int ID = Integer.parseInt(tests.nextLine());
                stakeholders.add(new Stakeholder(name, ID, address, balance)); //creating new stakeholder and adding it to the array

            }
            if (choice == 2) {
                //creating a new artefact
                Scanner artS = new Scanner(System.in);
                System.out.println("Enter Name:");
                String name = artS.nextLine();
                System.out.println("Enter ID");
                String ID = artS.nextLine();
                System.out.println("Enter Name Of the Country Of Origin");
                Stakeholder count = findStakeholder(stakeholders, artS.nextLine());
                System.out.println("Enter Name Of Owner");
                Stakeholder owner = findStakeholder(stakeholders, artS.nextLine());
                artefacts.add(new Artefact(name, ID, count, owner)); //creating new artefact and adding it to the array
            }
            if (choice == 3) {
                //getting transaction data
                Scanner transS = new Scanner(System.in);
                System.out.println("Enter Item Being Sold:");
                Artefact item = findArtefact(artefacts, transS.nextLine());
                System.out.println("Enter Buyer:");
                Stakeholder buyer = findStakeholder(stakeholders, transS.nextLine());
                System.out.println("Enter Seller:");
                Stakeholder seller = findStakeholder(stakeholders, transS.nextLine());
                System.out.println("Enter Auction House:");
                Stakeholder house = findStakeholder(stakeholders, transS.nextLine());
                System.out.println("Enter Price");
                Double price = Double.parseDouble(transS.nextLine());
                transactions.add(new Transaction(item, seller, buyer, house, price)); //adding transaction to array
            }
            if (choice == 4) {
                readytomine = true;
            }
            if (choice < 1 || choice > 4) {
                System.out.println("Invalid Input, try again");
            }
        }
        //data1-data3 should be filled by the user

        Block genesisBlock = new Block(transactions.get(0), "TUC", LocalDateTime.now());
        boolean p =genesisBlock.mineBlock(prefix, blockchain);
        if (p) {
            if (genesisBlock.getHash().substring(0, prefix).equals(prefixString) && verify_Blockchain(blockchain)) {
                blockchain.add(genesisBlock);
            } else
                System.out.println("Malicious block, not added to the chain");
        }


        Block secondBlock = new Block(transactions.get(1), blockchain.get(blockchain.size() - 1).getHash(), LocalDateTime.now());

        boolean t = secondBlock.mineBlock(prefix, blockchain);
        if (t) {
            if (secondBlock.getHash().substring(0, prefix).equals(prefixString) && verify_Blockchain(blockchain)) {
                blockchain.add(secondBlock);
            } else
                System.out.println("Malicious block, not added to the chain");
        }


        Block newBlock = new Block(transactions.get(2),blockchain.get(blockchain.size() - 1).getHash(),
                LocalDateTime.now());
        boolean l = newBlock.mineBlock(prefix, blockchain);
        if (l) {
            if (newBlock.getHash().substring(0, prefix).equals(prefixString) && verify_Blockchain(blockchain))
                blockchain.add(newBlock);
            else {
                System.out.println("Malicious block, not added to the chain");
            }
        }
        System.out.println("stop");
    }

    /**
     * Finds a stakeholder by the name in an array.If it doesn't exist, then asks for another input.
     * @param stake the array of stakeholders
     * @param in the input string that should be the name
     * @return the stakeholder that the user is looking for
     */
    public static Stakeholder findStakeholder(ArrayList<Stakeholder> stake, String in){
        for (int i = 0; i < stake.size(); i++) {
            if(stake.get(i).getName().equals(in)){
                return stake.get(i);
            }
        }
        System.out.println("Could not find name, please enter again:");
        Scanner Kb = new Scanner(System.in);
        String newin= Kb.nextLine();
        Stakeholder an = findStakeholder(stake, newin);
        return an;
    }

    //same as findStakeholder but artefacts
    public static Artefact findArtefact(ArrayList<Artefact> art, String in){
        for (int i = 0; i < art.size(); i++) {
            if(art.get(i).getName().equals(in)){
                return art.get(i);
            }
        }
        System.out.println("Could not find name, please enter again:");
        Scanner Kb = new Scanner(System.in);
        String newin= Kb.nextLine();
        Artefact an = findArtefact(art, newin);

        return an;
    }


    public static boolean verify_Blockchain(ArrayList<Block> BC){
        if (BC.size()==0){
            return true;
        }
        for (int i = 0; i < BC.size(); i++) {
            //checks if current hash is what it should be
            String challenger = BC.get(i).calculateBlockHash();
            if(BC.get(i).getHash().equals(BC.get(i).calculateBlockHash()) == false ){
                return false;
            }

            if(i>0) {
                //checks if the previous hash is what it should be
                if (BC.get(i - 1).getHash().equals(BC.get(i).getPreviousHash()) == false) {
                    return false;
                }
            }
            //checks if the current block is mined
            if (BC.get(i).getHash() == null){
                return false;
            }
        }
        return true;
    }




}


