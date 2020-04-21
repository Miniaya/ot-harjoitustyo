
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
    Connection conn;
    
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
        d = new SQLShipDao();
        
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
        
        conn = DriverManager.getConnection("jdbc:sqlite:ships.db");
        
        assertEquals(1, d.findByCoordinates(2, 2, 1, conn));
    }
    
    @Test
    public void findByCoordinatesReturnsNegative() throws SQLException {
        
        conn = DriverManager.getConnection("jdbc:sqlite:ships.db");
        
        assertEquals(-1, d.findByCoordinates(2, 2, 2, conn));
    }
    
    @Test
    public void getShipReturnsType() throws SQLException {
        
        conn = DriverManager.getConnection("jdbc:sqlite:ships.db");
        String type = d.getShip(1, conn);
        
        assertEquals("battleship", type);
    }
    
    @Test
    public void getShipReturnsNull() throws SQLException {
        
        conn = DriverManager.getConnection("jdbc:sqlite:ships.db");
        String type = d.getShip(3, conn);
        
        assertEquals(null, type);
    }
    
    @Test
    public void isSunkReturnsTrue() throws SQLException {
        
        conn = DriverManager.getConnection("jdbc:sqlite:ships.db");
        d.sinkPart(2, 2, conn, 1);
        
        conn = DriverManager.getConnection("jdbc:sqlite:ships.db");
        assertTrue(d.isSunk(1, conn));
    }
    
    @Test
    public void isSunkReturnsFalse() throws SQLException {
        
        conn = DriverManager.getConnection("jdbc:sqlite:ships.db");
        
        assertFalse(d.isSunk(2, conn));
    }
    
    @Test
    public void isEmptyReturnsFalse() throws SQLException {
        
        conn = DriverManager.getConnection("jdbc:sqlite:ships.db");
        assertFalse(d.isEmpty(2, conn));
    }
    
    @Test
    public void isEmptyReturnsTrue() throws SQLException {
        
         conn = DriverManager.getConnection("jdbc:sqlite:ships.db");
         assertTrue(d.isEmpty(3, conn));
    }
    
    @After
    public void tearDown() throws SQLException {
        
        conn = DriverManager.getConnection("jdbc:sqlite:ships.db");
        d.clearTables(conn);
    }

}
