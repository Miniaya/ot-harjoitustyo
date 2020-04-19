
package battleships.domain;

import battleships.dao.SQLShipDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ShipService {
    
    private SQLShipDao shipDao;
    private Connection connection;
    
    private Ship carrier;
    private Ship battleship;
    private Ship cruiser1;
    private Ship cruiser2;
    private Ship submarine;
    private Ship destroyer;
    
    public ShipService() throws SQLException {
        shipDao = new SQLShipDao();
        
        carrier = new Ship(ShipType.CARRIER, 1);
        battleship = new Ship(ShipType.BATTLESHIP, 1);
        cruiser1 = new Ship(ShipType.CRUISER, 1);
        cruiser2 = new Ship(ShipType.CRUISER, 1);
        submarine = new Ship(ShipType.SUBMARINE, 1);
        destroyer = new Ship(ShipType.DESTROYER, 1);
        
        // add ships to database
        shipDao.create(carrier);
        shipDao.create(battleship);
        shipDao.create(cruiser1);
        shipDao.create(cruiser2);
        shipDao.create(submarine);
        shipDao.create(destroyer);
    }
    
    public int isShip(int x, int y) throws SQLException {
        
        int shot = shipDao.findByCoordinates(x, y);
        
        if (shot > 0) {
            
            connection = DriverManager.getConnection("jdbc:sqlite:ships.db");
            shipDao.sinkPart(x, y, connection);
            
        }
        
        return shot;
    }
    
    public void generateShips() throws SQLException {
        
        // carrier placement
        
        for (int i = 6; i <= 10; i++) {
            shipDao.addCoordinates(carrier, i, 10);
        }
        
        // battleship placement
        
        for (int i = 3; i <= 6; i++) {
            shipDao.addCoordinates(battleship, 1, i);
        }
        
        // cruiser1 placement
        
        for (int i = 1; i <= 3; i++) {
            shipDao.addCoordinates(cruiser1, 4, i);
        }
        
        // cuiser2 & submarine placement
        
        for (int i = 6; i <= 8; i++) {
            shipDao.addCoordinates(cruiser2, i, 3);
            shipDao.addCoordinates(submarine, 8, i);
        }
        
        // destroyer placement
        shipDao.addCoordinates(destroyer, 1, 8);
        shipDao.addCoordinates(destroyer, 2, 8);
        
    }
    
    public void clear() throws SQLException {
        
        connection = DriverManager.getConnection("jdbc:sqlite:ships.db");
        shipDao.clearTables(connection);
    }
    
    public String isSink(int id) throws SQLException {
        
        connection = DriverManager.getConnection("jdbc:sqlite:ships.db");
        
        if (shipDao.isSunk(id, connection)) {
            
            connection = DriverManager.getConnection("jdbc:sqlite:ships.db");
            String ship = shipDao.getShip(id, connection);
            
            connection = DriverManager.getConnection("jdbc:sqlite:ships.db");
            shipDao.deleteShip(id, connection);
            
            return ship;
            
        } else {
            
            return null;
        }
        
    }
    
    public void generateShips() throws SQLException {
        
        generateCoordinates(carrier1);
        generateCoordinates(battleship1);
        generateCoordinates(cruiser1);
        generateCoordinates(rowboat1);
        generateCoordinates(submarine1);
        generateCoordinates(destroyer1);
        
        generateCoordinates(carrier2);
        generateCoordinates(battleship2);
        generateCoordinates(cruiser2);
        generateCoordinates(rowboat2);
        generateCoordinates(submarine2);
        generateCoordinates(destroyer2);
        
    }
    
    private boolean isValidate(int x, int y, double direction, int length, int player) throws SQLException {
        
        if (direction < 0.5) {
            
            if ( x + length - 1 > 10) {
                
                return false;
                
            } else {
                
                for(int i = x ; i < x + length ; i++){
                    
                    if(isShip(i, y + 1, player) > 0 || isShip(i + 1, y, player) > 0 || isShip(i - 1, y, player) > 0 || isShip(i, y - 1, player) > 0){
                        
                    return false;
                    }
                }
                
                return true;
            }
                
        } else {
                      
            if ( y + length - 1 > 10) {
                
                return false;
                
            } else {
                
                for(int i = y ; i < y + length ; i++){
                    
                    if(isShip(x, i + 1, player) > 0 || isShip(x + 1, i, player) > 0 || isShip(x - 1, i, player) > 0 || isShip(x, i - 1, player) > 0){
                        
                        return false;
                    }
                }
                
                return true;
            }
        }
    }
    
    public void sink(int x, int y, int id) throws SQLException {
        
        connection = DriverManager.getConnection("jdbc:sqlite:ships.db");
        shipDao.sinkPart(x, y, connection, id);
    }
    
    public boolean isEmpty(int player) throws SQLException {
        
        connection = DriverManager.getConnection("jdbc:sqlite:ships.db");
        
        return shipDao.isEmpty(player, connection);
    }
    
}
