/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.signIn;

import com.connection.conclass;
import com.signIn.Signer_Main;
import static com.signIn.Signer_Main.rootStage;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.sql.Connection;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class signMain extends Application {

    public static Connection mainConn = null;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/signIn/new_sign.fxml"));

//        Image icon = new Image(getClass().getResourceAsStream("/com/signIn/livelogo.png"));

//         rootStage.getIcons().add(new Image(new File(file)).to);
        //Image image = new Image(file.getAbsolutePath());
        //rootStage.getIcons().add(image);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        Signer_Main.rootStage = primaryStage;
        rootStage.initStyle(StageStyle.TRANSPARENT);
        
//        rootStage.getIcons().add(icon);
                
        rootStage.setTitle("Sign In");
        rootStage.setScene(scene);
        
        rootStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
