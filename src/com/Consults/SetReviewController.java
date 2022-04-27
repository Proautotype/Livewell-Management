/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Consults;

import static com.Consults.Doctors_NEWController._doctors_id;
import static com.Consults.Doctors_NEWController._in_RP_id;
import com.connection.conclass;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTimePicker;
import com.semantics.semanticsClass;
import com.tables.doctor_reviews;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Time;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class SetReviewController implements Initializable {

    @FXML
    private JFXDatePicker review_date_picker;
    @FXML
    private JFXTimePicker review_timepicker;
    @FXML
    private JFXTextArea review_txt_descrptor;
    @FXML
    private Label lbl_notification;

    String did, pid;
    String sql = "";
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    doctor_reviews dr = new doctor_reviews();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        did = _doctors_id;//gets the docter id from the 
        pid = _in_RP_id;//gets the selected patient id from the released_listView      
        //checkBackDating();
    }

    @FXML
    private void exit(ActionEvent event) {
        try {

        } catch (Exception e) {
        }
    }

    @FXML
    private void setReview(ActionEvent event) {
        if (checkBackDating()) {
            if (!(review_date_picker.getEditor().getText().isEmpty() || review_timepicker.getEditor().getText().isEmpty())) {
                Date rDate = Date.valueOf(review_date_picker.getValue());
                Time rTime = Time.valueOf(review_timepicker.getValue());
                dr = new doctor_reviews(pid, did, semanticsClass.returneDate(), rTime, rDate, review_txt_descrptor.getText());
                Service<Boolean> service = dr.new rd_services().add_review(dr);
                service.setOnSucceeded(e -> {
                    lbl_notification.setText(service.getMessage());
                    lbl_notification.setStyle("-fx-background-color:lightblue");
                });
                service.setOnCancelled(e -> {
                    lbl_notification.setStyle("-fx-background-color:tomato");
                    lbl_notification.setText(service.getMessage());
                });
                service.start();
            } else {
                lbl_notification.setStyle("-fx-background-color:tomato");
                lbl_notification.setText("Emptry inputs, please fill all necessary input");
            }
        } else {
            lbl_notification.setStyle("-fx-background-color:tomato");
            lbl_notification.setText("Hi, no backdating accepted");
        }
    }

    private boolean checkBackDating() {
        java.sql.Date d1 = java.sql.Date.valueOf(review_date_picker.getValue());
        java.util.Date today = new java.util.Date();
        long dif = today.getTime() - d1.getTime();
        long hours = dif / (60 * 60 * 1000);
        long days = hours / 24;
        if ((days < 0)) {//checking future and disallowing backdating
            return true;
        } else {
            return false;
        }
    }

}
