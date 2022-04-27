/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Stores;

import com.Accountancy.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class open_stores extends Application { 

    public static Stage rootStage = null;
    double xOffset = 0;
    double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/Stores/stores.fxml")); 
        Scene scene = new Scene(root);
        open_stores.rootStage = primaryStage;
//        rootStage.initStyle(StageStyle.UNDECORATED);
        rootStage.setTitle("Sign In");
        rootStage.setScene(scene);
        rootStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
