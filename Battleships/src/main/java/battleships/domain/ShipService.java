
package battleships.domain;

import battleships.dao.SQLShipDao;

import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Random;

/**
 * Luokka tarjoaa työkalut hallinnoida tietokannan laivoja.
 */
public class ShipService {
    
    private SQLShipDao shipDao;
    
    private Random r;
    
    private Ship carrier1;
    private Ship battleship1;
    private Ship cruiser1;
    private Ship rowboat1;
    private Ship submarine1;
    private Ship destroyer1;
    
    private Ship carrier2;
    private Ship battleship2;
    private Ship cruiser2;
    private Ship rowboat2;
    private Ship submarine2;
    private Ship destroyer2;
    
    public ShipService(SQLShipDao shipDao) throws SQLException {
        this.shipDao = shipDao;
        
        r = new Random();
        
        // Player 1
        carrier1 = new Ship(ShipType.CARRIER, 1);
        battleship1 = new Ship(ShipType.BATTLESHIP, 1);
        cruiser1 = new Ship(ShipType.CRUISER, 1);
        rowboat1 = new Ship(ShipType.ROWBOAT, 1);
        submarine1 = new Ship(ShipType.SUBMARINE, 1);
        destroyer1 = new Ship(ShipType.DESTROYER, 1);
        
        // Player 2
        carrier2 = new Ship(ShipType.CARRIER, 2);
        battleship2 = new Ship(ShipType.BATTLESHIP, 2);
        cruiser2 = new Ship(ShipType.CRUISER, 2);
        rowboat2 = new Ship(ShipType.ROWBOAT, 2);
        submarine2 = new Ship(ShipType.SUBMARINE, 2);
        destroyer2 = new Ship(ShipType.DESTROYER, 2);
        
    }
    
    /**
     * Metodi kertoo, onko parametrina annetuissa koordinaateissa 
     * parametrina annetun pelaajan laivaa.
     * 
     * @param x
     * @param y
     * @param player
     * 
     * @see battleships.dao.SQLShipDao#findByCoordinates(int, int, int)
     * 
     * @return Laivan indeksin, mikäli ruudussa on laiva, 
     * tai -1, mikäli kyseisessä ruudussa ei ole laivaa
     * 
     * @throws SQLException 
     */
    public int isShip(int x, int y, int player) throws SQLException {
        
        return shipDao.findByCoordinates(x, y, player);
    }
    
    /**
     * Metodi lisää tietokantaan koordinaatit, johon pelaaja ampui, ja jossa ei 
     * ole laivaa.
     * 
     * @param x
     * @param y
     * @param player
     * 
     * @see battleships.dao.SQLShipDao#addMissed(int, int, int) 
     * 
     * @throws SQLException 
     */
    public void addMissed(int x, int y, int player) throws SQLException {
        
        shipDao.addMissed(x, y, player);
    }
    
    /**
     * Metodi tyhjentää tietokannan turhista tiedoista.
     * 
     * @see battleships.dao.SQLShipDao#clearTables() 
     * 
     * @throws SQLException 
     */
    public void clear() throws SQLException {
        
        shipDao.clearTables();
    }
    
    /**
     * Metodi generoi pelissä tarvittaville laivoille koordinaatit.
     * 
     * @see battleships.domain.ShipService#generateCoordinates(battleships.domain.Ship) 
     * 
     * @throws SQLException 
     */
    public void generateShips() throws SQLException {
        
        addShipsToDatabase();
        
        generateCoordinates(carrier1);
        generateCoordinates(battleship1);
        generateCoordinates(cruiser1);
        generateCoordinates(rowboat1);
        generateCoordinates(submarine1);
        generateCoordinates(destroyer1);
        
        generateCoordinates(carrier2);
        generateCoordinates(battleship2);
        generateCoordinates(cruiser2);
        generateCoordinates(rowboat2);
        generateCoordinates(submarine2);
        generateCoordinates(destroyer2);
        
    }
    
    /**
     * Metodi upottaa parametreina annetuista koordinaateista osan laivasta, jonka id 
     * on annettu parametrina.
     * 
     * @param x
     * @param y
     * @param id
     * 
     * @see battleships.dao.SQLShipDao#sinkPart(int, int, int) 
     * 
     * @throws SQLException 
     */
    public void sink(int x, int y, int id) throws SQLException {
        
        shipDao.sinkPart(x, y, id);
    }
    
    /**
     * Metodi kertoo, onko parametrina annettu laiva upotettu.
     * 
     * @param id
     * 
     * @see battleships.dao.SQLShipDao#isSunk(int) 
     * @see battleships.dao.SQLShipDao#getShip(int) 
     * @see battleships.dao.SQLShipDao#sinkShip(int) 
     * 
     * @return laivan tyyppi, tai null, mikäli laiva ei ole uponnut kokonaan
     * 
     * @throws SQLException 
     */
    public String isSunk(int id) throws SQLException {
        
        if (shipDao.isSunk(id)) {
            
            String ship = shipDao.getShip(id);
            
            shipDao.sinkShip(id);
            
            return ship;
            
        } else {
            
            return null;
        }
        
    }
    
    /**
     * Metodi kertoo, onko parametrina annetun pelaajan kaikki laivat upotettu.
     * 
     * @param player
     * @see battleships.dao.SQLShipDao#isEmpty(int) 
     * 
     * @return true, jos pelaajan kaikki laivat on upotettu, 
     * tai false, mikäli pelaajalla on vielä laivoja
     * 
     * @throws SQLException 
     */
    public boolean isEmpty(int player) throws SQLException {
        
        return shipDao.isEmpty(player);
    }
    
    /**
     * Metodi hakee tietokannasta ohi menneet ja osuneet osumat ja palauttaa ne kaksiulotteisessa taulukossa,
     * joka vastaa pelilaudan koordinaatteja.
     * 
     * @param player
     * 
     * @see battleships.dao.SQLShipDao#getMissed(int, java.lang.Integer[][]) 
     * @see battleships.dao.SQLShipDao#getSinkCoordinates(int, java.lang.Integer[][]) 
     * 
     * @return kaksiulotteinen taulukko, jossa ohiosumat on merkittu numerolla 1,
     * ja osumat numerolla 2.
     * 
     * @throws SQLException 
     */
    public Integer[][] getHits(int player) throws SQLException {
        
        Integer[][] hits = new Integer[11][11];
        
        for (int i = 0; i <= 10; i++) {
            for (int j = 0; j <= 10; j++) {
                hits[i][j] = 0;
            }
        }
        
        hits = shipDao.getMissed(player, hits);
        hits = shipDao.getSinkCoordinates(player, hits);
        
        return hits;
    }
    
    /**
     * Metodi hakee tietokannasta parametrina annetun pelaajan upottamien laivojen tyypit.
     * 
     * @param player
     * 
     * @return Lista upotettujen laivojen tyypeistä
     * 
     * @throws SQLException 
     */
    public ArrayList<String> getSunk(int player) throws SQLException {
        
        return shipDao.getSunkShips(player);
    }
    
    private boolean isValidate(int x, int y, double direction, int length, int player) throws SQLException {
        
        if (direction < 0.5) {
                
            for (int i = x; i < x + length; i++) {
                    
                if (isShip(i, y + 1, player) > 0 || isShip(i + 1, y, player) > 0 || isShip(i - 1, y, player) > 0 || isShip(i, y - 1, player) > 0 || x + length - 1 > 10) {
                        
                    return false;
                }
            }
                
            return true;
                
        } else {
                
            for (int i = y; i < y + length; i++) {
                    
                if (isShip(x, i + 1, player) > 0 || isShip(x + 1, i, player) > 0 || isShip(x - 1, i, player) > 0 || isShip(x, i - 1, player) > 0 || y + length - 1 > 10) {
                        
                    return false;
                }
            }
                
            return true;
        }
    }
    
    private void generateCoordinates(Ship ship) throws SQLException {
        
        int x, y;
        double direction = r.nextDouble();
         
        while (true) {
            
            x = r.nextInt(10) + 1;
            y = r.nextInt(10) + 1;
            
            if (isValidate(x, y, direction, ship.length(), ship.getOwner())) {
                break;
            }
        }
            
        if (direction < 0.5) {
            
            for (int i = x; i < x + ship.length(); i++) {
                shipDao.addCoordinates(ship, i, y);
            }
            
        } else {
            
            for (int i = y; i < y + ship.length(); i++) {
                shipDao.addCoordinates(ship, x, i);
            }
                
        }
        
    }
    
    private void addShipsToDatabase() throws SQLException {
        
        shipDao.create(carrier1);
        shipDao.create(battleship1);
        shipDao.create(cruiser1);
        shipDao.create(rowboat1);
        shipDao.create(submarine1);
        shipDao.create(destroyer1);
        
        shipDao.create(carrier2);
        shipDao.create(battleship2);
        shipDao.create(cruiser2);
        shipDao.create(rowboat2);
        shipDao.create(submarine2);
        shipDao.create(destroyer2);
        
    }
    
}
