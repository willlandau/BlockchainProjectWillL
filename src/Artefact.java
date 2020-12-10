public class Artefact {
    String artid;
    String name;
    Stakeholder countryOfOrigin;
    Stakeholder owner;

    //constructor
    public Artefact(String name, String id, Stakeholder origin, Stakeholder owner){
        this.name = name;
        this.artid = id;
        this.countryOfOrigin = origin;
        this.owner = owner;
    }

    //getters and setters
    public String getArtid() {
        return artid;
    }

    public void setArtid(String artid) {
        this.artid = artid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stakeholder getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(Stakeholder countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public Stakeholder getOwner() {
        return owner;
    }

    public void setOwner(Stakeholder owner) {
        this.owner = owner;
    }

    @Override
    public String toString(){
        String an = "Name: " + this.name + " id: " + this.artid + " Owner: " + this.owner.getName() +
                " Country of Origin; " + this.countryOfOrigin;
        return an;
    }

    public String toStringforHash(){
        String an = "Name: " + this.name + " id: " + this.artid;
        return an;
    }
}
