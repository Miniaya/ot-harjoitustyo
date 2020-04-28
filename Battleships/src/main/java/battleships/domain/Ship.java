package battleships.domain;

/**
 * Luokkaa käytetään luomaan pelissä tarvittavat laivat
 * ja se tarjoaa laivan käsittelyyn tarvittavat metodit.
 * 
 */

public class Ship {
    
    private ShipType type;
    private int owner;
    
    public Ship(ShipType type, int owner) {
        this.type = type;
        this.owner = owner;
    }
    
    public Ship(){
        
    }
    
    public ShipType getShipType() {
        return type;
    }
    
    public void setShipType(ShipType type) {
        this.type = type;
    }
    
    public int getOwner() {
        return owner;
    }
    
    public void setOwner(int owner) {
        this.owner = owner;
    }
    
    /**
     * @see battleships.domain.ShipType#size
     * 
     * @return laivatyypin pituus
     */
    public int length() {
        return type.getSize();
    }
    
}
