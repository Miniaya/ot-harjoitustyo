package battleships.dao;

import battleships.domain.Ship;
import java.sql.*;
import java.util.ArrayList;

/**
 * Rajapinta, joka tarjoaa metodit laivojen ja koordinaattien käsittelyyn
 */
public interface ShipDao {
    
    boolean create(Ship ship) throws SQLException;
    
    boolean addCoordinates(Ship ship, int x, int y) throws SQLException;
    
    int findByCoordinates(int x, int y, int player) throws SQLException;
    
    void sinkShip(int id) throws SQLException;
    
    void sinkPart(int x, int y, int id) throws SQLException;
    
    boolean isSunk(int id) throws SQLException;
    
    String getShip(int id) throws SQLException;
    
    boolean isEmpty(int player) throws SQLException;
    
    void addMissed(int x, int y, int player) throws SQLException;
    
    Integer[][] getMissed(int player, Integer[][] hits) throws SQLException;
    
    Integer[][] getSinkCoordinates(int player, Integer[][] hits) throws SQLException;
    
    ArrayList<String> getSunkShips(int player) throws SQLException;
}
