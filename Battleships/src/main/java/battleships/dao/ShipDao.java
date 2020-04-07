package battleships.dao;

import battleships.domain.Ship;
import java.sql.*;

public interface ShipDao {
    
    boolean create(Ship ship) throws SQLException;
    
    boolean addCoordinates(Ship ship, int x, int y) throws SQLException;
    
    int findByCoordinates(int x, int y) throws SQLException;
    
    void update(Ship ship, Connection conn) throws SQLException;
    
    void deleteShip(Ship ship, Connection conn) throws SQLException;
}
