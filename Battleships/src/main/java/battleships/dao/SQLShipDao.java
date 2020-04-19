package battleships.dao;

import battleships.domain.Ship;
import java.sql.*;

public class SQLShipDao implements ShipDao {
    
    @Override
    public boolean create(Ship ship) throws SQLException {
        
        Connection conn = DriverManager.getConnection("jdbc:sqlite:ships.db");
        
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Ships (type_id, owner) VALUES ((SELECT id FROM Shiptypes WHERE type = ?), ?)");
        
            stmt.setString(1, ship.getShipType().toString().toLowerCase());
            stmt.setInt(2, ship.getOwner());
        
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
            
            return true;
            
        } catch (SQLException e) {
            
            conn.close();
            
            return false;
        }
        
    }
    
    @Override
    public boolean addCoordinates(Ship ship, int x, int y) throws SQLException {
        
        Connection conn = DriverManager.getConnection("jdbc:sqlite:ships.db");
        
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Positioning (ship_id, x, y) VALUES ((SELECT id FROM Ships WHERE type_id = (SELECT id FROM Shiptypes WHERE type = ?) AND owner = ?), ?, ?)");
            
            stmt.setString(1, ship.getShipType().toString().toLowerCase());
            stmt.setInt(2, ship.getOwner());
            stmt.setInt(3, x);
            stmt.setInt(4, y);
            
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
            
            return true;
            
        } catch (SQLException e) {
            
            conn.close();
            
            return false;
        }
    }
    
    @Override
    public int findByCoordinates(int x, int y, int player) throws SQLException {
        
        Connection conn = DriverManager.getConnection("jdbc:sqlite:ships.db");
        
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT P.ship_id FROM Positioning P LEFT JOIN Ships ON P.ship_id = Ships.id WHERE P.x = ? AND P.y = ? AND Ships.owner = ?");
            stmt.setInt(1, x);
            stmt.setInt(2, y);
            stmt.setInt(3, player);
            
            ResultSet r = stmt.executeQuery();
            
            if (r.next()) {
                int ans = r.getInt(1);
               
                stmt.close();
                r.close();
                conn.close();
            
                return ans;
                
            } else {
                
                stmt.close();
                r.close();
                conn.close();
                
                return -1;
            }
            
        } catch (SQLException e) {
            
            conn.close();
            return -1;
        }
        
    }
    
    @Override
    public void update(Ship ship, Connection conn) throws SQLException {
        
    }
    
    @Override
    public void deleteShip(int id, Connection conn) throws SQLException {
        
        try {
            
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Ships WHERE id = ?");
            stmt.setInt(1, id);
            
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
            
        } catch (SQLException e) {
            
            System.out.println(e);
            conn.close();
        }
    }
    
    public boolean sinkPart(int x, int y, Connection conn, int id) throws SQLException {
        
        try {
            
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Positioning WHERE x = ? AND y = ? AND ship_id = ?");
            stmt.setInt(1, x);
            stmt.setInt(2, y);
            stmt.setInt(3, id);
            
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
            
            return true;
            
        } catch (SQLException e) {
            
            conn.close();
            
            return false;
        }
    }
    
    public boolean isSunk(int id, Connection conn) throws SQLException {
        
        try {
            
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Positioning P LEFT JOIN Ships ON P.ship_id = Ships.id WHERE P.ship_id = ?");
            stmt.setInt(1, id);
            
            ResultSet r = stmt.executeQuery();
            
            if (r.next()) {
                
                stmt.close();
                r.close();
                conn.close();
                
                return false;
                
            } else {
                
                stmt.close();
                r.close();
                conn.close();
                
                return true;
            }
            
        } catch (SQLException e) {
            
            System.out.println(e);
            conn.close();
            
            return false;
        }
    }
    
    public String getShip(int id, Connection conn) throws SQLException {
        
        try {
            
            PreparedStatement stmt = conn.prepareStatement("SELECT type FROM ShipTypes LEFT JOIN Ships ON Ships.type_id = ShipTypes.id WHERE Ships.id = ?");
            stmt.setInt(1, id);
            
            ResultSet r = stmt.executeQuery();
            
            if (r.next()) {
                String ans = r.getString(1);
                
                stmt.close();
                r.close();
                conn.close();
                
                return ans;
                
            } else {
                
                stmt.close();
                r.close();
                conn.close();
                
                return null;
            }
        } catch (SQLException e) {
            
            conn.close();
            
            return null;
        }
    }
    
    public boolean clearTables(Connection conn) throws SQLException {
        
        try {
            
            PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM Ships");
            PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM Positioning");
            
            stmt1.executeUpdate();
            stmt2.executeUpdate();
            
            stmt1.close();
            stmt2.close();
            conn.close();
            
            return true;
            
        } catch (SQLException e) {
            
            conn.close();
            return false;
        }
    }
    
    public boolean isEmpty(int player, Connection conn) throws SQLException {
        
        try {
            
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Ships WHERE owner = ?");
            stmt.setInt(1, player);
            
            ResultSet r = stmt.executeQuery();
            
            if (!r.next()) {
                
                r.close();
                stmt.close();
                conn.close();
                
                return true;
                
            } else {
                
                r.close();
                stmt.close();
                conn.close();
                
                return false;
            }
        } catch (SQLException e) {
            
            System.out.println(e);
            conn.close();
            
            return false;
        }
    }
    
}
