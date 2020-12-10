public class Stakeholder {
    int stakeid;
    String name;
    String address;
    Double balance;

    public Stakeholder(String name, int id, String address, Double balance){
        this.name = name;
        this.stakeid = id;
        this.address = address;
        this.balance = balance;
    }

    public int getId() {
        return stakeid;
    }

    public void setId(int id) {
        this.stakeid = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString(){
        String an = "Name: " + this.name + " id: " + this.stakeid + " address: " + this.address + "Balance: " + this.balance;
        return an;
    }

    //Specific toString for calculating the has without stakeholder balances because they change throughout program
    public String toStringForHash(){
        String an = "Name: " + this.name + " id: " + this.stakeid + " address: " + this.address;
        return an;
    }
}
