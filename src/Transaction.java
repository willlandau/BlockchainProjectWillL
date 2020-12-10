import java.time.LocalDateTime;

public class Transaction{
    Artefact artefact;
    LocalDateTime time;
    Stakeholder seller;
    Stakeholder buyer;
    Stakeholder auctionHouse;
    Double price;

    public Transaction(Artefact artefact, Stakeholder seller, Stakeholder buyer,
                       Stakeholder auctionHouse, Double price) {
        this.artefact = artefact;
        this.time = LocalDateTime.now();
        this.seller = seller;
        this.buyer = buyer;
        this.auctionHouse = auctionHouse;
        this.price = price;
    }

    public Artefact getArtefact() {
        return artefact;
    }

    public void setArtefact(Artefact artefact) {
        this.artefact = artefact;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Stakeholder getSeller() {
        return seller;
    }

    public void setSeller(Stakeholder seller) {
        this.seller = seller;
    }

    public Stakeholder getBuyer() {
        return buyer;
    }

    public void setBuyer(Stakeholder buyer) {
        this.buyer = buyer;
    }

    public Stakeholder getAuctionHouse() {
        return auctionHouse;
    }

    public void setAuctionHouse(Stakeholder auctionHouse) {
        this.auctionHouse = auctionHouse;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString(){
        String hi = "Artefact: " + this.artefact + "Buyer: " + this.buyer + "Seller:" + this.seller + "Price: " + this.price;
        return hi;
    }

    //Specific toString for calculating the has without stakeholder balances because they change throughout program
    public String toStringForHash(){
        String hi = "Artefact: " + this.artefact.toStringforHash() + "Buyer: " + this.buyer.toStringForHash() + "Seller:" + this.seller.toStringForHash() + "Price: " + this.price;
        return hi;
    }
}
