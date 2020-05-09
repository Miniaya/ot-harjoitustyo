
package battleships.domain;

import battleships.dao.SQLShipDao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShipServiceTest {
    
    ShipService ser;
    Ship ship;
    SQLShipDao shipDao;
    
    @Before
    public void setUp() throws SQLException {
        shipDao = new SQLShipDao("test.db");
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
    
    @Test
    public void isSinkReturnsType() throws SQLException {
        shipDao.create(ship);
        shipDao.addCoordinates(ship, 1, 1);
        
        ser.sink(1, 1, 1);
        
        assertEquals("battleship", ser.isSink(1));
    }
    
    @Test
    public void isSinkReturnsNull() throws SQLException {
        shipDao.create(ship);
        shipDao.addCoordinates(ship, 1, 1);
        
        assertEquals(null, ser.isSink(1));
    }
    
    @Test
    public void getHitsReturnsMissedAndHits() throws SQLException {
        ser.addMissed(2, 2, 1);
        shipDao.create(ship);
        shipDao.addCoordinates(ship, 1, 1);
        
        ser.sink(1, 1, 1);
        
        Integer hits[][] = ser.getHits(1);
        
        int missed = hits[2][2];
        int shot = hits[1][1];
        
        assertEquals(1, missed);
        assertEquals(2, shot);
    }
    
    @Test
    public void getSunkReturnsType() throws SQLException {
        shipDao.create(ship);
        shipDao.addCoordinates(ship, 1, 1);
        shipDao.sinkShip(1);
        
        ArrayList<String> ships = ser.getSunk(1);
        
        assertEquals("battleship", ships.get(0));
    }

}
