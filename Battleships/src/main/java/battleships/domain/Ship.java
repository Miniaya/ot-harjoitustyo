package battleships.domain;

public class Ship {
    
    private ShipType type;
    
    public Ship(ShipType type){
        this.type = type;
    }
    
    public Ship(){
        
    }
    
    public ShipType getShipType(){
        return type;
    }
    
    public void setShipType(ShipType type){
        this.type = type;
    }
    
}
