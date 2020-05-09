package battleships.dao;

import battleships.domain.Ship;
import java.sql.*;
import java.util.ArrayList;

/**
 * Luokka hallinnoi suoraan tietokantaan tallennettuja arvoja.
 */
public class SQLShipDao implements ShipDao {
    
    private String db;
    
    public SQLShipDao(String db) {
        
        this.db = db;
    }
    
    /**
     * Metodi luo yhteyden konstruktorissa määriteltyyn tietokantaan
     * 
     * @return Yhteys tietokantaan
     * 
     * @throws SQLException 
     */
    public Connection getConnection() throws SQLException {
        
        String url = "jdbc:sqlite:" + db;
        
        Connection conn = DriverManager.getConnection(url);
        
        return conn;
    }
    
    /**
     * Metodi sulkee yhteyden parametreina annettuun tietokantaan ja statementtiin.
     * 
     * @param conn
     * @param stmt
     * 
     * @throws SQLException 
     */
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
        
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Ships (type_id, owner, sink) VALUES ((SELECT id FROM Shiptypes WHERE type = ?), ?, 0)");
        
        stmt.setString(1, ship.getShipType().toString().toLowerCase());
        stmt.setInt(2, ship.getOwner());
        
        stmt.executeUpdate();
            
        closeConnection(conn, stmt);
            
        return true;
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
        
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Coordinates (ship_id, x, y, sink) VALUES ((SELECT id FROM Ships WHERE type_id = (SELECT id FROM Shiptypes WHERE type = ?) AND owner = ?), ?, ?, 0)");
            
        stmt.setString(1, ship.getShipType().toString().toLowerCase());
        stmt.setInt(2, ship.getOwner());
        stmt.setInt(3, x);
        stmt.setInt(4, y);
            
        stmt.executeUpdate();
            
        closeConnection(conn, stmt);
            
        return true;
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
            
        PreparedStatement stmt = conn.prepareStatement("SELECT C.ship_id FROM Coordinates C LEFT JOIN Ships ON C.ship_id = Ships.id WHERE C.x = ? AND C.y = ? AND Ships.owner = ?");
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
    public void sinkShip(int id) throws SQLException {
        
        Connection conn = getConnection();
            
        PreparedStatement stmt = conn.prepareStatement("UPDATE Ships SET sink = 1 WHERE id = ?");
        stmt.setInt(1, id);
            
        stmt.executeUpdate();
            
        closeConnection(conn, stmt);
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
    @Override
    public void sinkPart(int x, int y, int id) throws SQLException {
        
        Connection conn = getConnection();
            
        PreparedStatement stmt = conn.prepareStatement("UPDATE Coordinates SET sink = 1 WHERE x = ? AND y = ? AND ship_id = ?");
        stmt.setInt(1, x);
        stmt.setInt(2, y);
        stmt.setInt(3, id);
            
        stmt.executeUpdate();
            
        closeConnection(conn, stmt);
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
    @Override
    public boolean isSunk(int id) throws SQLException {
        
        Connection conn = getConnection();
            
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Coordinates C LEFT JOIN Ships ON C.ship_id = Ships.id WHERE C.ship_id = ? AND C.sink = 0");
        stmt.setInt(1, id);
            
        ResultSet r = stmt.executeQuery();
            
        boolean isSunk = true;
            
        if (r.next()) {
            isSunk = false;     
        } 
            
        r.close();
        closeConnection(conn, stmt);
            
        return isSunk;
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
    @Override
    public String getShip(int id) throws SQLException {
        
        Connection conn = getConnection();
            
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
    }
    
    /**
     * Metodi poistaa tietokantataulujen Ships ja Positioning sisällöt.
     * 
     * @throws SQLException 
     */
    public void clearTables() throws SQLException {
        
        Connection conn = getConnection();
            
        PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM Ships");
        PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM Coordinates");
        PreparedStatement stmt3 = conn.prepareStatement("DELETE FROM Missed");
            
        stmt1.executeUpdate();
        stmt2.executeUpdate();
        stmt3.executeUpdate();
            
        stmt1.close();
        stmt2.close();
        stmt3.close();
        conn.close();
            
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
    @Override
    public boolean isEmpty(int player) throws SQLException {
        
        Connection conn = getConnection();
            
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Ships WHERE owner = ? AND sink = 0");
        stmt.setInt(1, player);
            
        ResultSet r = stmt.executeQuery();
            
        boolean empty = true;
            
        if (r.next()) {            
            empty = false;
        }
            
        r.close();
        closeConnection(conn, stmt);
            
        return empty;
    }
    
    /**
     * Metodi lisää tietokantaan koordinaatit kohtaan, johon pelaaja ampui, mutta jossa ei ollut laivaa.
     * 
     * @param x
     * @param y
     * @param player
     * 
     * @throws SQLException 
     */
    @Override
    public void addMissed(int x, int y, int player) throws SQLException {
        
        Connection conn = getConnection();
        
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Missed (x, y, player) VALUES (?, ?, ?)");
        stmt.setInt(1, x);
        stmt.setInt(2, y);
        stmt.setInt(3, player);
        
        stmt.executeUpdate();
        
        conn.close();
    }
    
    /**
     * Metodi hakee tietokannasta pelaajan ohiosumat ja tallettaa ne parametrina annettuun kaksiulotteiseen taulukkoon.
     * 
     * @param player
     * @param hits
     * 
     * @return kaksiulotteinen taulukko, jossa ohiosumat on merkitty numerolla 1.
     * 
     * @throws SQLException 
     */
    @Override
    public Integer[][] getMissed(int player, Integer[][] hits) throws SQLException {
        
        Connection conn = getConnection();
        
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Missed WHERE player = ?");
        stmt.setInt(1, player);
        
        ResultSet r = stmt.executeQuery();
        
        while (r.next()) {
            hits[r.getInt(1)][r.getInt(2)] = 1;
        }
        
        r.close();
        closeConnection(conn, stmt);
        
        return hits;
    }
    
    /**
     * Metodi hakee tietokannasta koordinaatit laivan osista, jotka pelaaja on upottanut ja tallettaa ne parametrina annettuun kaksiulotteiseen taulukkoon.
     * 
     * @param player
     * @param hits
     * 
     * @return kaksiulotteinen taulukko, jossa osumat on merkitty numerolla 2.
     * 
     * @throws SQLException 
     */
    @Override
    public Integer[][] getSinkCoordinates(int player, Integer[][] hits) throws SQLException {
        
        Connection conn = getConnection();
        
        PreparedStatement stmt = conn.prepareStatement("SELECT C.x, C.y FROM Coordinates C LEFT JOIN Ships ON C.ship_id = Ships.id WHERE Ships.owner = ? AND C.sink = 1");
        stmt.setInt(1, player);
        
        ResultSet r = stmt.executeQuery();
        
        while (r.next()) {
            hits[r.getInt(1)][r.getInt(2)] = 2;
        }
        
        r.close();
        closeConnection(conn, stmt);
        
        return hits;
    }
    
    /**
     * Metodi hakee tietokannasta pelaajan upottamien laivojen tyypit ja palauttaa ne listassa.
     * 
     * @param player
     * 
     * @return Lista pelaajan upottamista laivoista
     * 
     * @throws SQLException 
     */
    @Override
    public ArrayList<String> getSunkShips(int player) throws SQLException {
        
        Connection conn = getConnection();
        
        PreparedStatement stmt = conn.prepareStatement("SELECT S.type FROM Shiptypes S LEFT JOIN Ships ON Ships.type_id = S.id WHERE Ships.owner = ? AND Ships.sink = 1");
        stmt.setInt(1, player);
        
        ResultSet r = stmt.executeQuery();
        ArrayList<String> ships = new ArrayList<>();
        
        while (r.next()) {
            ships.add(r.getString(1));
        }
        
        r.close();
        closeConnection(conn, stmt);
        
        return ships;
    }
}
