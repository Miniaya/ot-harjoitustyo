package battleships.ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import battleships.domain.ShipService;
import javafx.scene.input.KeyCombination;

public class BattleshipsUi extends Application {
    
    private ShipService service;
    private Label turn;
    private Stage primaryStage;
    
    @Override
    public void init() throws SQLException {
        
        service = new ShipService();
        
    }
    
    @Override
    public void start(Stage primaryStage) throws SQLException {   
        
        this.primaryStage = primaryStage;
        
        // Creating main menu
        BorderPane mainPane = new BorderPane();
        VBox menuPane = new VBox();
        menuPane.setSpacing(10);
        
        Button newGame = new Button("New game");
        newGame.setFont(Font.font("Monospaced", 30));
        
        Button resume = new Button("Resume game");
        resume.setFont(Font.font("Monospaced", 30));
        
        Button quit = new Button("Quit game");
        quit.setFont(Font.font("Monospaced", 30));
        
        menuPane.getChildren().add(newGame);
        
        if(service.isEmpty(1) && service.isEmpty(2)){
            menuPane.getChildren().add(resume);
        }
        
        menuPane.getChildren().add(quit);
        menuPane.setAlignment(Pos.CENTER);
        
        mainPane.setCenter(menuPane);
        
        Scene mainMenuScene = new Scene(mainPane);
        
        // Creating the game
        GridPane player1Pane = new GridPane();
        GridPane player2Pane = new GridPane();
        BorderPane mainGamePane = new BorderPane();
        VBox shipPane1 = new VBox();
        VBox shipPane2 = new VBox();
        VBox pane = new VBox();
        HBox gameBoardPane = new HBox();
        
        gameBoardPane.setSpacing(100);
        pane.setSpacing(50);
        
        Button back = new Button("Back to menu");
        back.setFont(Font.font("Monospaced", 30));
        
        turn = new Label();
        turn.setText("Whose turn: Player 1");
        turn.setFont(Font.font("Monospaced", 30));
        
        Label player1 = new Label("Player 1");
        player1.setFont(Font.font("Monospaced", 30));
        
        Label player2 = new Label("Player 2");
        player2.setFont(Font.font("Monospaced", 30));
        
        shipPane1.getChildren().add(player1);
        shipPane1.getChildren().add(new Label(""));
        
        shipPane2.getChildren().add(player2);
        shipPane2.getChildren().add(new Label(""));
        
        gameBoard(player1Pane, shipPane1, 1, 2);
        gameBoard(player2Pane, shipPane2, 2, 1);
        
        gameBoardPane.getChildren().add(shipPane1);
        gameBoardPane.getChildren().add(player1Pane);
        gameBoardPane.getChildren().add(player2Pane);
        gameBoardPane.getChildren().add(shipPane2);
        
        pane.getChildren().add(gameBoardPane);
        pane.getChildren().add(turn);
        
        if(turn.getText().endsWith("!")){
            pane.getChildren().add(newGame);
        }
        
        mainGamePane.setTop(back);
        mainGamePane.setCenter(pane);
        
        gameBoardPane.setAlignment(Pos.CENTER);
        
        pane.setAlignment(Pos.CENTER);
        
        Scene gameScene = new Scene(mainGamePane);
        
        // Button actions
        newGame.setOnAction((event) -> {
            
            try {
                service.generateShips();
            } catch (SQLException ex) {
                Logger.getLogger(BattleshipsUi.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            switchScene(gameScene);
        });
        
        resume.setOnAction((event) -> {
            switchScene(gameScene);
        });
        
        quit.setOnAction((event) -> {
            primaryStage.close();
        });
        
        back.setOnAction((event) -> {
            switchScene(mainMenuScene);
        });
        
        primaryStage.setTitle("BattleShips");
        switchScene(mainMenuScene);
        primaryStage.show();
    }
    
    @Override
    public void stop() throws SQLException {
        service.clear();
    }
    
    private void gameBoard(GridPane pane, VBox ships, int player, int opponent){
        
        for(int i = 1 ; i <= 10 ; i++){
            for(int j = 1 ; j <= 10 ; j++){
                Button button = new Button(" ");
                button.setFont(Font.font("Monospaced", 30));
                pane.add(button, i + 1, j + 1);
                
                if(turn.getText().endsWith(String.valueOf(player))){
                    pane.setGridLinesVisible(true);
                }
                
                int x = i;
                int y = j;
                
                button.setOnAction((event) -> {
                    try {
                        
                        int shot = service.isShip(x, y, player);
                        
                        if (turn.getText().endsWith(String.valueOf(player)) && shot > 0 && button.getText().equals(" ")) {
                            button.setText("O");
                            button.setTextFill(Color.RED);
                            service.sink(x, y, shot);
                            
                            String ship = service.isSink(shot);
                            
                            turn.setText("Whose turn: Player " + opponent);
                            
                            if (ship != null) {
                                
                                Label label = new Label(ship);
                                label.setFont(Font.font("Monospaced", 20));
                                
                                ships.getChildren().add(label);
                            }
                            
                        } else if (turn.getText().endsWith(String.valueOf(player)) && shot < 0 && button.getText().equals(" ")) {
                            
                            button.setText("X");
                            turn.setText("Whose turn: Player " + opponent);
                        }
                        
                        if (service.isEmpty(player)) {
                            turn.setText("Player " + player + " won!");
                        }
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(BattleshipsUi.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                });
                
                pane.setGridLinesVisible(false);
            }
        }
        
        for(int i = 65 ; i < 75 ; i++){
            Label label = new Label(String.valueOf((char) i));
            label.setTextAlignment(TextAlignment.CENTER);
            pane.add(label, i - 63, 0);
        }
        
        for(int i = 1 ; i <= 10 ; i++){
            pane.add(new Label(String.valueOf(i)), 0, i + 1);
        }
    }
    
    private void switchScene(Scene scene){
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    }
    
    public static void main(String[] args){
        launch(BattleshipsUi.class);
    }
    
}
