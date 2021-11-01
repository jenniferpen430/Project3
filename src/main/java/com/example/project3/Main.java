package com.example.project3;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 This is the main class where the Application is extended from. Everything connects together through
 this class.
 @author jennifer, adams
 */

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainView.fxml"));
            BorderPane bp = (BorderPane) fxmlLoader.load();
            Scene scene = new Scene(bp,600,600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Tuition Manager");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}