
package battleships.domain;

import battleships.dao.SQLShipDao;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShipServiceTest {
    
    ShipService ser;
    Ship ship;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws SQLException {
        SQLShipDao shipDao = new SQLShipDao("test.db");
        ser = new ShipService(shipDao);
        ship = new Ship(ShipType.BATTLESHIP, 1);
    }
    
    @After
    public void tearDown() throws SQLException {
        ser.clear();
    }

    @Test
    public void isShipReturnsPositive(){
        
    }
    
    @Test
    public void isShipReturnsNegative() throws SQLException {
        assertEquals(-1, ser.isShip(1, 1, 1));
    }
    
    @Test
    public void generateShipsWorks() throws SQLException {
        ser.generateShips();
        
        assertFalse(ser.isEmpty(1));
        assertFalse(ser.isEmpty(2));
    }
    
//    @Test
//    public void isSinkReturnsType() throws SQLException {
//        assertEquals("carrier", ser.isSink(1));
//    }

}
