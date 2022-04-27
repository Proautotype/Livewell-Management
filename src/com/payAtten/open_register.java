/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payAtten;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class open_register extends Application {

    public static Stage rootStage = null;
    double xOffset = 0;
    double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/payAtten/register.fxml"));
        Scene scene = new Scene(root);
        open_register.rootStage = primaryStage;

      
        rootStage.initStyle(StageStyle.UNDECORATED);
        rootStage.setTitle("Sign In");
        rootStage.setScene(scene);
        rootStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
