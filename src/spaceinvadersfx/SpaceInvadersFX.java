/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvadersfx;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author sedri
 */
public class SpaceInvadersFX extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    Timeline gameLoop;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        List<InvaderGroup> groups = new ArrayList();
        
        InvaderGroup group1 = new InvaderGroup(4);
        group1.setInitLocation(50, 100);
        groups.add(group1);
        
        Pane gameBoard = new Pane(group1);
        VBox.setVgrow(gameBoard, Priority.ALWAYS);
        group1.setGameBoard(gameBoard);
        
        
        
        gameLoop = new Timeline(new KeyFrame(Duration.seconds(.16), (event) -> {
            groups.forEach((group) -> {
                group.moveGroup();
            });
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);        
        
        Button button = new Button("Start");
        button.setOnAction((event) -> {
            InvaderGroup invaderGroup = new InvaderGroup(9);
            invaderGroup.setInitLocation(50, 100);
            invaderGroup.setGameBoard(gameBoard);
            groups.add(invaderGroup);
            gameBoard.getChildren().add(invaderGroup);
        });
        gameLoop.play();
        
        VBox root = new VBox(gameBoard, button);
        Scene scene = new Scene(root, 1080, 720);
    
        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
