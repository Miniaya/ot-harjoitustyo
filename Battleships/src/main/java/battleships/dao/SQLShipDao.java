package battleships.dao;

import battleships.domain.Ship;
import java.sql.*;

public class SQLShipDao implements ShipDao {
    
    @Override
    public boolean create(Ship ship) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:ships.db");
        
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Ships (type_id, owner) VALUES ((SELECT id FROM Shiptypes WHERE type = ?), ?)");
        
            stmt.setString(1, ship.getShipType().toString().toLowerCase());
            stmt.setInt(2, ship.getOwner());
        
            stmt.executeUpdate();
            
            stmt.close();
            connection.close();
            
            return true;
            
        } catch (SQLException e) {
            
            connection.close();
            
            return false;
        }
        
    }
    
    @Override
    public boolean addCoordinates(Ship ship, int x, int y) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:ships.db");
        
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Positioning (ship_id, x, y) VALUES ((SELECT id FROM Ships WHERE type_id = (SELECT id FROM Shiptypes WHERE type = ?) AND owner = ?), ?, ?)");
            
            stmt.setString(1, ship.getShipType().toString().toLowerCase());
            stmt.setInt(2, ship.getOwner());
            stmt.setInt(3, x);
            stmt.setInt(4, y);
            
            stmt.executeUpdate();
            
            stmt.close();
            connection.close();
            
            return true;
            
        } catch (SQLException e) {
            
            connection.close();
            
            return false;
        }
    }
    
    @Override
    public boolean findByCoordinates(int x, int y) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:ships.db");
        
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Positioning WHERE x = ? AND y = ?");
            stmt.setInt(1, x);
            stmt.setInt(2, y);
            
            ResultSet r = stmt.executeQuery();
            
            if (r.next()) {
                
                stmt.close();
                r.close();
                connection.close();
                return true;
                
            } else { 
                
                stmt.close();
                r.close();
                connection.close();
                return false;
            }
            
        } catch (SQLException e) {
            
            connection.close();
            return false;
        }
        
    }
    
    @Override
    public void update(Ship ship) throws SQLException {
        
    }
    
    @Override
    public void deleteShip(Ship ship) throws SQLException {
        
    }
    
    public boolean sinkPart(Ship ship, int x, int y) throws SQLException {
        
    }
    
    public boolean isSunk(Ship ship) {
        
    }
    
    public boolean clearTables() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:ships.db");
        
        try {
            
            PreparedStatement stmt1 = connection.prepareStatement("DELETE FROM Ships");
            PreparedStatement stmt2 = connection.prepareStatement("DELETE FROM Positioning");
            
            stmt1.executeUpdate();
            stmt2.executeUpdate();
            
            connection.close();
            
            return true;
            
        } catch (SQLException e) {
            
            System.out.println(e);
            connection.close();
            return false;
        }
    }
}
