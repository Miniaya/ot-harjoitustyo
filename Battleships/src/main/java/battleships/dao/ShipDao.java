package battleships.dao;

import battleships.domain.Ship;
import java.sql.*;

public interface ShipDao {
    
    boolean create(Ship ship) throws SQLException;
    
    boolean addCoordinates(Ship ship, int x, int y) throws SQLException;
    
    boolean findByCoordinates(int x, int y) throws SQLException;
    
    void update(Ship ship) throws SQLException;
    
    void deleteShip(Ship ship) throws SQLException;
}
