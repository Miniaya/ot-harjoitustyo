
package battleships.domain;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.layout.GridPane;
import javafx.scene.robot.Robot;

public class Opponent {
    
    ArrayList<Integer>[] shots;
    Random r;
    
    
    public Opponent() {
        shots = new ArrayList[11];
        r = new Random();
        
        for (int i = 1; i <= 10; i++) {
            shots[i] = new ArrayList<>();
        }
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
        
        int[] shot = {x, y};
        
        return shot;
    }
}
