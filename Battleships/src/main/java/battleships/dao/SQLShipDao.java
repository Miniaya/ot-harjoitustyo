package battleships.dao;

import battleships.domain.Ship;
import java.sql.*;

/**
 * Luokka hallinnoi suoraan tietokantaan tallennettuja arvoja.
 */
public class SQLShipDao implements ShipDao {
    
    private String db;
    
    public SQLShipDao(String db){
        
        this.db = db;
    }
    
    public Connection getConnection() throws SQLException {
        
        String url = "jdbc:sqlite:" + db;
        
        Connection conn = DriverManager.getConnection(url);
        
        return conn;
    }
    
    public void closeConnection(Connection conn, Statement stmt) throws SQLException {
        
        stmt.close();
        conn.close();
    }
    
    /**
     * Metodi lisää parametrina annetun laivan tietokantaan.
     * 
     * @param ship
     * 
     * @see battleships.dao.ShipDao#create(battleships.domain.Ship) 
     * 
     * @return true, mikäli lisääminen onnistuu, muuten false
     * 
     * @throws SQLException 
     */
    @Override
    public boolean create(Ship ship) throws SQLException {
        
        Connection conn = getConnection();
        
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Ships (type_id, owner) VALUES ((SELECT id FROM Shiptypes WHERE type = ?), ?)");
        
            stmt.setString(1, ship.getShipType().toString().toLowerCase());
            stmt.setInt(2, ship.getOwner());
        
            stmt.executeUpdate();
            
            closeConnection(conn, stmt);
            
            return true;
            
        } catch (SQLException e) {
            
            conn.close();
            
            return false;
        }
        
    }
    
    /**
     * Metodi lisää parametrina annetun laivan annettuihin koordinaatteihin.
     * 
     * @param ship
     * @param x
     * @param y
     * 
     * @see battleships.dao.ShipDao#addCoordinates(battleships.domain.Ship, int, int) 
     * 
     * @return true, mikäli lisääminen onnistuu, muuten false
     * 
     * @throws SQLException 
     */
    @Override
    public boolean addCoordinates(Ship ship, int x, int y) throws SQLException {
        
        Connection conn = getConnection();
        
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Positioning (ship_id, x, y) VALUES ((SELECT id FROM Ships WHERE type_id = (SELECT id FROM Shiptypes WHERE type = ?) AND owner = ?), ?, ?)");
            
            stmt.setString(1, ship.getShipType().toString().toLowerCase());
            stmt.setInt(2, ship.getOwner());
            stmt.setInt(3, x);
            stmt.setInt(4, y);
            
            stmt.executeUpdate();
            
            closeConnection(conn, stmt);
            
            return true;
            
        } catch (SQLException e) {
            
            conn.close();
            
            return false;
        }
    }
    
    /**
     * Metodi etsii parametrina annetuista koordinaateista annetun pelaajan laivaa
     * 
     * @param x
     * @param y
     * @param player
     * 
     * @see battleships.dao.ShipDao#findByCoordinates(int, int, int, java.sql.Connection) 
     * 
     * @return -1, mikäli koordinaateissa ei ole laivaa, muuten laivan id:n
     * 
     * @throws SQLException 
     */
    @Override
    public int findByCoordinates(int x, int y, int player) throws SQLException {
        
        Connection conn = getConnection();
        
        try {
            
            PreparedStatement stmt = conn.prepareStatement("SELECT P.ship_id FROM Positioning P LEFT JOIN Ships ON P.ship_id = Ships.id WHERE P.x = ? AND P.y = ? AND Ships.owner = ?");
            stmt.setInt(1, x);
            stmt.setInt(2, y);
            stmt.setInt(3, player);
            
            ResultSet r = stmt.executeQuery();
            
            int ans = -1;
            
            if (r.next()) {
                
                ans = r.getInt(1);
            }
            
            r.close();
            closeConnection(conn, stmt);
            
            return ans;
            
        } catch (SQLException e) {
            
            System.out.println(e);
            conn.close();
            
            return -1;
        }
        
    }
    
    /**
     * Metodi poistaa parametrina annetun laivan tietokannasta.
     * 
     * @param id
     * 
     * @see battleships.dao.ShipDao#deleteShip(int, java.sql.Connection) 
     * 
     * @throws SQLException 
     */
    @Override
    public void deleteShip(int id) throws SQLException {
        
        Connection conn = getConnection();
        
        try {
            
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Ships WHERE id = ?");
            stmt.setInt(1, id);
            
            stmt.executeUpdate();
            
            closeConnection(conn, stmt);
            
        } catch (SQLException e) {
            conn.close();
        }
    }
    
    /**
     * Metodi poistaa parametrina annetuista koordinaateista laivan, jonka id on annettu.
     * 
     * @param x
     * @param y
     * @param id
     * 
     * @throws SQLException 
     */
    public void sinkPart(int x, int y, int id) throws SQLException {
        
        Connection conn = getConnection();
        
        try {
            
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Positioning WHERE x = ? AND y = ? AND ship_id = ?");
            stmt.setInt(1, x);
            stmt.setInt(2, y);
            stmt.setInt(3, id);
            
            stmt.executeUpdate();
            
            closeConnection(conn, stmt);
            
        } catch (SQLException e) {
            
            conn.close();
            
        }
    }
    
    /**
     * Metodi kertoo, onko parametrina annettu laiva upotettu, eli onko laivalla
     * enää ainuttakaan koordinaattia tietokannassa.
     * 
     * @param id
     * 
     * @return true, mikäli laiva on upotettu, muuten false
     * 
     * @throws SQLException 
     */
    public boolean isSunk(int id) throws SQLException {
        
        Connection conn = getConnection();
        
        try {
            
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Positioning P LEFT JOIN Ships ON P.ship_id = Ships.id WHERE P.ship_id = ?");
            stmt.setInt(1, id);
            
            ResultSet r = stmt.executeQuery();
            
            boolean isSunk = true;
            
            if (r.next()) {
                
                isSunk = false;
                
            } 
            
            r.close();
            closeConnection(conn, stmt);
            
            return isSunk;
            
        } catch (SQLException e) {
            
            System.out.println(e);
            conn.close();
            
            return false;
        }
    }
    
    /**
     * Metodi kertoo, mikä laivatyyppi vastaa parametrina annettua laivan id:tä.
     * 
     * @param id
     * 
     * @return laivan tyyppi, mikäli laiva löytyy tietokannasta, muuten null
     * 
     * @throws SQLException 
     */
    public String getShip(int id) throws SQLException {
        
        Connection conn = getConnection();
        
        try {
            
            PreparedStatement stmt = conn.prepareStatement("SELECT type FROM ShipTypes LEFT JOIN Ships ON Ships.type_id = ShipTypes.id WHERE Ships.id = ?");
            stmt.setInt(1, id);
            
            ResultSet r = stmt.executeQuery();
            
            String type;
            
            if (r.next()) {
                
                type = r.getString(1);
                
            } else {
                
                type = null;
            }
            
            r.close();
            closeConnection(conn, stmt);
            
            return type;
            
        } catch (SQLException e) {
            
            System.out.println(e);
            conn.close();
            
            return null;
        }
    }
    
    /**
     * Metodi poistaa tietokantataulujen Ships ja Positioning sisällöt.
     * 
     * @return true, mikäli poisto onnistuu, muuten false
     * @throws SQLException 
     */
    public boolean clearTables() throws SQLException {
        
        Connection conn = getConnection();
        
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
    
    /**
     * Metodi kertoo, onko annetulla pelaajalla enää pinnalla olevia laivoja.
     * 
     * @param player
     * 
     * @return true, mikäli laivoja ei ole, muuten false
     * 
     * @throws SQLException 
     */
    public boolean isEmpty(int player) throws SQLException {
        
        Connection conn = getConnection();
        
        try {
            
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Ships WHERE owner = ?");
            stmt.setInt(1, player);
            
            ResultSet r = stmt.executeQuery();
            
            boolean empty = true;
            
            if (r.next()) {
                
                empty = false;
            }
            
            r.close();
            closeConnection(conn, stmt);
            
            return empty;
            
        } catch (SQLException e) {
            
            System.out.println(e);
            conn.close();
            
            return false;
        }
    }
}
