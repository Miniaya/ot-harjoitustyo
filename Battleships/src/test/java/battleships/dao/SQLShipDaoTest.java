
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

public class SQLShipDaoTest {
    
    Ship bl;
    Ship carr;
    SQLShipDao d;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
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
    
    @After
    public void tearDown() throws SQLException {
        
        d.clearTables();
    }

}
