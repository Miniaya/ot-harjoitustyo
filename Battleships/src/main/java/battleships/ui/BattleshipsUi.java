package battleships.ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.util.HashMap;
import java.util.ArrayList;

public class BattleshipsUi extends Application {
    
    private HashMap<Integer, ArrayList<Integer>> coordinates;
    
    @Override
    public void init(){
        coordinates = new HashMap<>();
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(1);
        arr.add(2);
        arr.add(4);
        arr.add(7);
        arr.add(8);
        arr.add(9);
        arr.add(10);
        
        coordinates.put(1, arr);
        
        arr = new ArrayList<>();
        arr.add(4);
        
        coordinates.put(2, arr);
        
        arr = new ArrayList<>();
        arr.add(4);
        arr.add(6);
        arr.add(7);
        arr.add(8);
        
        coordinates.put(3, arr);
    }
    
    @Override
    public void start(Stage primaryStage) {       
        // Creating main menu
        BorderPane mainPane = new BorderPane();
        VBox menuPane = new VBox();
        menuPane.setSpacing(10);
        
        Button start = new Button("Start game");
        Button quit = new Button("Quit game");
        
        menuPane.getChildren().add(start);
        menuPane.getChildren().add(quit);
        menuPane.setAlignment(Pos.CENTER);
        
        mainPane.setCenter(menuPane);
        mainPane.setPrefSize(400, 400);
        
        Scene mainMenuScene = new Scene(mainPane);
        
        // Creating the game
        GridPane gamePane = new GridPane();
        
        for(int i = 0 ; i < 10 ; i++){
            for(int j = 0 ; j < 10 ; j++){
                Button button = new Button(" ");
                button.setFont(Font.font("Monospaced", 30));
                gamePane.add(button, i + 3, j + 3);
                
                int x = i + 1;
                int y = j + 1;
                
                button.setOnAction((event) -> {
                    if(isShip(x, y)){
                        markAsHit(x, y);
                        button.setText("O");
                    } else {
                        button.setText("X");
                    }
                });
            }
        }
        
        for(int i = 65 ; i < 75 ; i++){
            Label label = new Label(String.valueOf((char) i));
            label.setTextAlignment(TextAlignment.CENTER);
            gamePane.add(label, i -62, 2);
        }
        
        for(int i = 1 ; i <= 10 ; i++){
            gamePane.add(new Label(String.valueOf(i)), 2, i + 2);
        }
        
        gamePane.setPrefSize(650, 650);
        gamePane.setAlignment(Pos.CENTER);
        
        Scene gameScene = new Scene(gamePane);
        
        // Button actions
        start.setOnAction((event) -> {
            primaryStage.setScene(gameScene);
        });
        
        primaryStage.setTitle("BattleShips");
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }
    
    @Override
    public void stop(){
        
    }
    
    public static void main(String[] args){
        launch(BattleshipsUi.class);
    }
    
    private boolean isShip(int x, int y){
        return coordinates.get(y).contains(x);
    }
    
    private void markAsHit(int x, int y){
        coordinates.get(y).remove(x);
    }
}
