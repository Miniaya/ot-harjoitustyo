
package battleships.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Opponent {
    
    ArrayList<Integer>[] shots;
    Random r;
    GridPane gameboard;
    boolean hit;
    int[] prev;
    ShipService service;
    
    public Opponent(GridPane gameboard) throws SQLException {
        shots = new ArrayList[11];
        r = new Random();
        this.gameboard = gameboard;
        service = new ShipService();
        
        for (int i = 1; i <= 10; i++) {
            shots[i] = new ArrayList<>();
        }
    }
    
    public Opponent() {
        shots = new ArrayList[11];
        r = new Random();
        
        for (int i = 1; i <= 10; i++) {
            shots[i] = new ArrayList<>();
        }
    }
    
    public void setGameboard(GridPane board) {
        this.gameboard = board;
    }
    
    public int[] shoot() {
        
        int x, y;
        
        while (true) {
            
            x = r.nextInt(10) + 1;
            y = r.nextInt(10) + 1;
            
            if (shots[x].get(y) == null) {
                break;
            }
        }
        
        shots[x].add(y);
        
//        if(isHit(x, y)){
//            hit = true;
//            int[] prev = {x, y};
//            this.prev = prev;
//        }

        int[] shot = {x, y};
        return shot;
    }
    
    public boolean isHit(int x, int y) throws SQLException {
        
        for (Node node : gameboard.getChildren()) {
            if (GridPane.getColumnIndex(node) == x + 1 && GridPane.getRowIndex(node) == y + 1) {
                Button btn = (Button) node;
                
                if(service.isShip(x, y, 2) > 0){
                    btn.setText("O");
                    return true;
                } else {
                    btn.setText("X");
                    return false;
                }
            }
        }
        
        return false;
    }
}