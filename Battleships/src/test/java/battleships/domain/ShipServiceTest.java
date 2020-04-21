
package battleships.domain;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShipServiceTest {
    
    ShipService ser;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws SQLException {
        ser = new ShipService();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void isShipReturnsPositive(){
        
    }
    
    @Test
    public void isShipReturnsNegative(){
        
    }
}
