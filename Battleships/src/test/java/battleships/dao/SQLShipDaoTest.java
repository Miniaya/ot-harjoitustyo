
package battleships.dao;

import battleships.domain.ShipType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import battleships.domain.Ship;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLShipDaoTest {
    
    Ship bl;
    Ship carr;
    SQLShipDao d;
    
    @Before
    public void setUp() throws SQLException {
        bl = new Ship(ShipType.BATTLESHIP, 1);
        carr = new Ship(ShipType.CARRIER, 2);
        d = new SQLShipDao("test.db");
        
        d.create(bl);
        d.addCoordinates(bl, 2, 2);
        
        d.create(carr);
        d.addCoordinates(carr, 3, 5);
    }
    
    @Test
    public void addShipToDatabaseWorks() throws SQLException {
        assertTrue(d.create(new Ship(ShipType.ROWBOAT, 1)));
    }
    
    @Test
    public void addCoordinatesWorks() throws SQLException {
        assertTrue(d.addCoordinates(new Ship(ShipType.ROWBOAT, 1), 6, 6));
    }
    
    @Test
    public void findByCoordinatesReturnsCorrectId() throws SQLException {
        
        assertEquals(1, d.findByCoordinates(2, 2, 1));
    }
    
    @Test
    public void findByCoordinatesReturnsNegative() throws SQLException {
        
        assertEquals(-1, d.findByCoordinates(2, 2, 2));
    }
    
    @Test
    public void getShipReturnsType() throws SQLException {
       
        String type = d.getShip(1);
        
        assertEquals("battleship", type);
    }
    
    @Test
    public void getShipReturnsNull() throws SQLException {
        
        String type = d.getShip(3);
        
        assertEquals(null, type);
    }
    
    @Test
    public void isSunkReturnsTrue() throws SQLException {
        
        d.sinkPart(2, 2, 1);
        
        assertTrue(d.isSunk(1));
    }
    
    @Test
    public void isSunkReturnsFalse() throws SQLException {
        
        assertFalse(d.isSunk(2));
    }
    
    @Test
    public void isEmptyReturnsFalse() throws SQLException {
        
        assertFalse(d.isEmpty(2));
    }
    
    @Test
    public void isEmptyReturnsTrue() throws SQLException {
        
        assertTrue(d.isEmpty(3));
    }
    
    @Test
    public void addMissedWorks() throws SQLException {
        
        d.addMissed(1, 1, 1);
        Integer[][] arr = new Integer[2][2];
        arr = d.getMissed(1, arr);
        int ans = arr[1][1];
        
        assertEquals(1, ans);
    }
    
    @Test
    public void getSunkShipsReturnsType() throws SQLException {
        
        d.sinkShip(1);
        
        ArrayList<String> ships = d.getSunkShips(1);
        
        assertEquals("battleship", ships.get(0));
    }
    
    @Test
    public void getSunkReturnsEmptyList() throws SQLException {
        
        ArrayList<String> ships = d.getSunkShips(1);
        
        assertEquals(0, ships.size());
    }
    
    @Test
    public void getSinkCoordinatesWorks() throws SQLException {
        
        d.sinkPart(2, 2, 1);
        
        Integer[][] arr = new Integer[3][3];
        arr = d.getSinkCoordinates(1, arr);
        int ans = arr[2][2];
        
        assertEquals(2, ans);
    }
    
    @After
    public void tearDown() throws SQLException {
        
        d.clearTables();
    }

}
