
package battleships.domain;

import battleships.dao.SQLShipDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Random;

public class ShipService {
    
    private SQLShipDao shipDao;
    private Connection connection;
    
    private Ship carrier1;
    private Ship battleship1;
    private Ship cruiser1;
    private Ship rowboat1;
    private Ship submarine1;
    private Ship destroyer1;
    
    private Ship carrier2;
    private Ship battleship2;
    private Ship cruiser2;
    private Ship rowboat2;
    private Ship submarine2;
    private Ship destroyer2;
    
    public ShipService() throws SQLException {
        shipDao = new SQLShipDao();
        
        // Player 1
        carrier1 = new Ship(ShipType.CARRIER, 1);
        battleship1 = new Ship(ShipType.BATTLESHIP, 1);
        cruiser1 = new Ship(ShipType.CRUISER, 1);
        rowboat1 = new Ship(ShipType.ROWBOAT, 1);
        submarine1 = new Ship(ShipType.SUBMARINE, 1);
        destroyer1 = new Ship(ShipType.DESTROYER, 1);
        
        // add ships to database
        shipDao.create(carrier1);
        shipDao.create(battleship1);
        shipDao.create(cruiser1);
        shipDao.create(rowboat1);
        shipDao.create(submarine1);
        shipDao.create(destroyer1);
        
        // Player 2
        carrier2 = new Ship(ShipType.CARRIER, 2);
        battleship2 = new Ship(ShipType.BATTLESHIP, 2);
        cruiser2 = new Ship(ShipType.CRUISER, 2);
        rowboat2 = new Ship(ShipType.ROWBOAT, 2);
        submarine2 = new Ship(ShipType.SUBMARINE, 2);
        destroyer2 = new Ship(ShipType.DESTROYER, 2);
        
        // add ships to database
        shipDao.create(carrier2);
        shipDao.create(battleship2);
        shipDao.create(cruiser2);
        shipDao.create(rowboat2);
        shipDao.create(submarine2);
        shipDao.create(destroyer2);
        
    }
    
    public int isShip(int x, int y, int player) throws SQLException {
        
        return shipDao.findByCoordinates(x, y, player);
    }
    
    public void generateCoordinates(Ship ship) throws SQLException {
        
        Random r = new Random();
        int x, y;
        double direction = r.nextDouble();
         
        while (true) {
            
            x = r.nextInt(10) + 1;
            y = r.nextInt(10) + 1;
            
            if (isValidate(x, y, direction, ship.length(), ship.getOwner())) {
                break;
            }
        }
            
        if ( direction < 0.5 ) {
            
            for (int i = x ; i < x + ship.length() ; i++) {
                shipDao.addCoordinates(ship, i, y);
            }
            
        } else {
            
            for (int i = y ; i < y + ship.length() ; i++) {
                shipDao.addCoordinates(ship, x, i);
            }
                
        }
        
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
