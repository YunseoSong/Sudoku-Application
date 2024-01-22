package com.example.sudokugui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class SudokuApplication extends Application {
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SudokuApplication.class.getResource("Sudoku.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 600);
        stage.setTitle("Sudoku Game");
        stage.setScene(scene);
        scene.getStylesheets().add(SudokuApplication.class.getResource("Sudoku.css").toExternalForm());
        stage.show();
    }

    public static void main(String args[]) {
        launch();
    }
}



