package battleships.domain;

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
    
}
