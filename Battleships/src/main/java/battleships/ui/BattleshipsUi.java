package battleships.ui;

import battleships.dao.SQLShipDao;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.KeyCombination;
import javafx.scene.Node;

import java.sql.SQLException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Properties;

import battleships.domain.ShipService;
import java.util.ArrayList;

public class BattleshipsUi extends Application {
    
    private ShipService service;
    private Label turn;
    private Stage primaryStage;
    private GridPane player1Pane;
    private GridPane player2Pane;
    private VBox shipPane1;
    private VBox shipPane2;
    private Label player1;
    private Label player2;
    
    @Override
    public void init() throws SQLException, FileNotFoundException, IOException {
        
        Properties properties = new Properties();
        
        properties.load(new FileInputStream("config.properties"));
        
        String shipDB = properties.getProperty("shipDB");
        
        SQLShipDao shipDao = new SQLShipDao(shipDB);
        
        service = new ShipService(shipDao);
    }
    
    @Override
    public void start(Stage primaryStage) throws SQLException {   
        
        this.primaryStage = primaryStage;
        
        // Creating main menu
        BorderPane mainPane = new BorderPane();
        VBox menuPane = new VBox();
        menuPane.setSpacing(10);
        
        Label battleships = new Label("BattleShips");
        battleships.setPadding(new Insets(20, 20, 20, 20));
        battleships.setFont(Font.font("Arial", 70));
        
        Button newGame = new Button("New game");
        newGame.setFont(Font.font("Arial", 30));
        newGame.setMaxSize(300.0, Double.MAX_VALUE);
        
        Button resume = new Button("Resume game");
        resume.setFont(Font.font("Arial", 30));
        resume.setMaxSize(300.0, Double.MAX_VALUE);
        
        Button quit = new Button("Quit game");
        quit.setFont(Font.font("Arial", 30));
        quit.setMaxSize(300.0, Double.MAX_VALUE);
        
        menuPane.getChildren().add(newGame);
        menuPane.getChildren().add(resume);
        menuPane.getChildren().add(quit);
        
        menuPane.setAlignment(Pos.CENTER);
        
        mainPane.setTop(battleships);
        mainPane.setCenter(menuPane);
        
        Scene mainMenuScene = new Scene(mainPane);
        
        // Creating the game
        player1Pane = new GridPane();
        player2Pane = new GridPane();
        BorderPane mainGamePane = new BorderPane();
        shipPane1 = new VBox();
        shipPane2 = new VBox();
        VBox pane = new VBox();
        HBox gameBoardPane = new HBox();
        
        gameBoardPane.setSpacing(100);
        pane.setSpacing(50);
        
        player1 = new Label("Player 1");
        player1.setFont(Font.font("Arial", 30));
        player1.setStyle("-fx-font-weight: bold");
        
        player2 = new Label("Player 2");
        player2.setFont(Font.font("Arial", 30));
        player2.setStyle("-fx-font-weight: bold");
        
        Button back = new Button("Back to menu");
        back.setFont(Font.font("Arial", 30));
        
        turn = new Label();
        turn.setText("Whose turn: Player 1");
        turn.setFont(Font.font("Arial", 30));
        
        gameBoard(player1Pane, shipPane1, 1, 2);
        gameBoard(player2Pane, shipPane2, 2, 1);
        
        gameBoardPane.getChildren().add(shipPane1);
        gameBoardPane.getChildren().add(player1Pane);
        gameBoardPane.getChildren().add(player2Pane);
        gameBoardPane.getChildren().add(shipPane2);
        
        pane.getChildren().add(gameBoardPane);
        pane.getChildren().add(turn);
        
        mainGamePane.setTop(back);
        mainGamePane.setCenter(pane);
        
        gameBoardPane.setAlignment(Pos.CENTER);
        
        pane.setAlignment(Pos.CENTER);
        
        Scene gameScene = new Scene(mainGamePane);
        
        // Button actions
        newGame.setOnAction((event) -> {
            
            try {
                
                newGame();
                
            } catch (SQLException ex) {
                Logger.getLogger(BattleshipsUi.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            switchScene(gameScene);
        });
        
        resume.setOnAction((event) -> {
            
            try {
                
                initializeGameboard(player1Pane, 1);
                initializeGameboard(player2Pane, 2);
                
                if (service.isEmpty(1) || service.isEmpty(2)) {
                    
                    newGame();
                    
                } else {
                
                    shipPane1.getChildren().clear();
                    shipPane2.getChildren().clear();
                    
                    shipPane1.getChildren().add(player1);
                    shipPane1.getChildren().add(new Label(""));
                    
                    for (String ship : service.getSunk(1)) {
                        Label label = new Label(ship);
                        label.setFont(Font.font("Arial", 20));
                        shipPane1.getChildren().add(label);
                    }
                    
                    shipPane2.getChildren().add(player2);
                    shipPane2.getChildren().add(new Label(""));
                    
                    for (String ship : service.getSunk(2)) {
                        Label label = new Label(ship);
                        label.setFont(Font.font("Arial", 20));
                        shipPane2.getChildren().add(label);
                    }
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(BattleshipsUi.class.getName()).log(Level.SEVERE, null, ex);
            }
            
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
    }
    
    private void gameBoard(GridPane pane, VBox ships, int player, int opponent){
        
        for(int i = 1 ; i <= 10 ; i++){
            for(int j = 1 ; j <= 10 ; j++){
                Button button = new Button();
                
                if(turn.getText().endsWith(String.valueOf(player))){
                    
                    button.setFont(Font.font("Arial", 30));
                
                    button.setMinSize(65.0, 65.0);
                    button.setPrefSize(65.0, 65.0);
                    button.setMaxSize(65.0, 65.0);
                    
                } else {
                    
                    button.setFont(Font.font("Arial", 25));
                
                    button.setMinSize(60.0, 60.0);
                    button.setPrefSize(60.0, 60.0);
                    button.setMaxSize(60.0, 60.0);
                    
                }
                
                pane.add(button, i + 1, j + 1);
                
                int x = i;
                int y = j;
                
                button.setOnAction((event) -> {

                    try {
                        
                        int shot = service.isShip(x, y, player);
                        
                        if (turn.getText().endsWith(String.valueOf(player)) && shot > 0 && button.getText().equals("")) {
                            button.setText("O");
                            button.setTextFill(Color.RED);
                            service.sink(x, y, shot);
                            
                            String ship = service.isSunk(shot);
                            
                            turn.setText("Whose turn: Player " + opponent);
                            
                            if (ship != null) {
                                
                                Label label = new Label(ship);
                                label.setFont(Font.font("Arial", 20));
                                
                                ships.getChildren().add(label);
                            }
                            
                            changeTurn(opponent);
                            
                        } else if (turn.getText().endsWith(String.valueOf(player)) && shot < 0 && button.getText().equals("")) {
                            
                            button.setText("X");
                            turn.setText("Whose turn: Player " + opponent);
                            service.addMissed(x, y, player);
                            
                            changeTurn(opponent);
                            
                        } 
                        
                        if (service.isEmpty(player)) {
                            
                            changeButtonAndLabelSize(player1Pane, "normal", 15, 25, 60.0);
                            changeButtonAndLabelSize(player2Pane, "normal", 15, 25, 60.0);
                            
                            turn.setText("Player " + player + " won!");
                        }
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(BattleshipsUi.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                });
                
            }
        }
        
        for(int i = 65 ; i < 75 ; i++){
            Label label = new Label(String.valueOf((char) i));
            GridPane.setHalignment(label, HPos.CENTER);
            pane.add(label, i - 63, 0);
            
            if(turn.getText().endsWith(String.valueOf(player))) {
                label.setStyle("-fx-font-weight: bold");
                label.setFont(Font.font("Arial", 20));
            } else {
                label.setStyle("-fx-font-weight: normal");
                label.setFont(Font.font("Arial", 15));
            }
        }
        
        for(int i = 1 ; i <= 10 ; i++){
            Label label = new Label(String.valueOf(i));
            GridPane.setHalignment(label, HPos.CENTER);
            pane.add(label, 0, i + 1);
            
            if(turn.getText().endsWith(String.valueOf(player))) {
                
                label.setStyle("-fx-font-weight: bold");
                label.setFont(Font.font("Arial", 20));
                
            } else {
                
                label.setStyle("-fx-font-weight: normal");
                label.setFont(Font.font("Arial", 15));
            }
        }
    }
    
    private void switchScene(Scene scene){
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    }
    
    private void clearGameboard(GridPane pane) {
        
        for(Node node : pane.getChildren()) {
            
            if(node instanceof Button){
                ((Button) node).setText("");
                ((Button) node).setTextFill(Color.BLACK);
            }
        }
    }
    
    private void initializeGameboard(GridPane pane, int player) throws SQLException {
        
        Integer[][] hits = service.getHits(player);
        
        for(Node node : pane.getChildren()) {
            
            if(node instanceof Button) {
                
                int hit = hits[GridPane.getColumnIndex(node) - 1][GridPane.getRowIndex(node) - 1];
                
                if(hit == 1) {
                    ((Button) node).setText("X");
                }
                
                if(hit == 2) {
                    ((Button) node).setText("O");
                    ((Button) node).setTextFill(Color.RED);
                }
            }
        }
    }
    
    private void changeTurn(int opponent){
        
        if(opponent == 1){
            
            changeButtonAndLabelSize(player1Pane, "bold", 20, 30, 65.0);
            changeButtonAndLabelSize(player2Pane, "normal", 15, 25, 60.0);
            
        } else if (opponent == 2){
            
            changeButtonAndLabelSize(player2Pane, "bold", 20, 30, 65.0);
            changeButtonAndLabelSize(player1Pane, "normal", 15, 25, 60.0);
            
        }
    }
    
    private void changeButtonAndLabelSize(GridPane pane, String weight, int labelFont, int buttonFont, double buttonSize){
        for(Node node : pane.getChildren()){
                                
                if(node instanceof Label){
                                
                    node.setStyle("-fx-font-weight: " + weight);
                    ((Label) node).setFont(Font.font("Arial", labelFont));
                }
                
                if(node instanceof Button){
                    ((Button) node).setFont(Font.font("Arial", buttonFont));
                
                    ((Button) node).setMinSize(buttonSize, buttonSize);
                    ((Button) node).setPrefSize(buttonSize, buttonSize);
                    ((Button) node).setMaxSize(buttonSize, buttonSize);
                }
            }
    }
    
    private void newGame() throws SQLException {
        
        service.clear();
                
        clearGameboard(player1Pane);
        clearGameboard(player2Pane);
                
        shipPane1.getChildren().clear();
        shipPane2.getChildren().clear();
                
        shipPane1.getChildren().add(player1);
        shipPane1.getChildren().add(new Label(""));
        
        shipPane2.getChildren().add(player2);
        shipPane2.getChildren().add(new Label(""));
                
        turn.setText("Whose turn: Player 1");
                
        service.generateShips();
        
    }
    
    public static void main(String[] args){
        launch(BattleshipsUi.class);
    }
    
}
