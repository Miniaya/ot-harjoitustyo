package battleships.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShipTest {
    
    Ship ship;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ship = new Ship();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void setTypeWorks(){
        ship.setShipType(ShipType.CARRIER);
        assertEquals(ShipType.CARRIER, ship.getShipType());
    }
    
    @Test
    public void constructorWorks(){
        ship = new Ship(ShipType.DESTROYER, 1);
        assertEquals(ShipType.DESTROYER, ship.getShipType());
    }
    
    @Test
    public void getSizeWorks(){
        ship.setShipType(ShipType.CRUISER);
        int size = ship.getShipType().getSize();
        assertEquals(3, size);
    }
    
    @Test
    public void setOwnerWorks(){
        ship.setOwner(1);
        assertEquals(1, ship.getOwner());
    }
    
    @Test
    public void lengthIsCorrect(){
        ship.setShipType(ShipType.CARRIER);
        assertEquals(5, ship.length());
    }

}
