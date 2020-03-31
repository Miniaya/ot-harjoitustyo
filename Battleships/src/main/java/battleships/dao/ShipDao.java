
package battleships.dao;

import battleships.domain.Ship;

public interface ShipDao {
    
    void create(Ship ship) throws Exception;
    
    boolean findByCoordinates(int x, int y);
    
    void update(Ship ship);
}
