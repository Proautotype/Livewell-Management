/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Nurses;

import com.Consults.*;
import com.Laboratory.*;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class open_nurse extends Application {
    public static Stage myStage = null;

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("nurses.fxml"));
        Scene scene = new Scene(root);       
        myStage = new Stage();
        myStage.setScene(scene);
        myStage.show();
    }

    /**
     * @param args the command line arguments
    public static void main(String[] args) {
     */
    public static void main(String[] args) {
        launch(args);
    }

}
