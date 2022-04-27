/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sms;

import com.jfoenix.controls.JFXDatePicker;
//import java.sql.Date;
import java.util.Date;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class checkDate extends Application {

    @Override
    public void start(Stage primaryStage) {
        JFXDatePicker jdp = new JFXDatePicker();
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        Label lbl = new Label("Notification");
        jdp.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                java.sql.Date d1 = java.sql.Date.valueOf(jdp.getValue());
                Date today = new Date();
                long dif = today.getTime() - d1.getTime();
                long hours = dif / (60 * 60 * 1000);
                long days = hours / 24;
                
                lbl.setText("The difference is: " + days);
            }
        });

        StackPane root = new StackPane();
        root.getChildren().addAll(new VBox(jdp,lbl));

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
