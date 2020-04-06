
package battleships.dao;

import battleships.domain.ShipType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import battleships.domain.Ship;
import java.sql.SQLException;

public class SQLShipDaoTest {
    
    Ship ship;
    SQLShipDao d;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ship = new Ship(ShipType.BATTLESHIP, 1);
        d = new SQLShipDao();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void addShipToDatabaseWorks() throws SQLException{
        boolean add = d.create(ship);
        assertTrue(add);
    }
    
    @Test
    public void addCoordinatesWorks() throws SQLException{
        boolean add = d.addCoordinates(ship, 2, 2);
        assertTrue(add);
    }
    
    @Test
    public void findByCoordinatesWorks() throws SQLException{
        boolean find = d.findByCoordinates(2, 2);
        assertTrue(find);
    }

}
