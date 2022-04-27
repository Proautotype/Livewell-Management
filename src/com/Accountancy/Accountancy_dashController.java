/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Accountancy;

import com.connection.conclass;
import com.jfoenix.controls.JFXListView;
import com.semantics.semanticsClass;
import com.signIn.SignInController;
import com.signIn.nSign_Controller;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class Accountancy_dashController implements Initializable {

    @FXML
    private Circle lbl_imageView;
    @FXML
    private Label lbl_name;
    @FXML
    private Label lbl_id;
    @FXML
    private JFXListView<?> listView_incoming;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private Connection conn = null;
    private String sql = "";
    public static String account_id = "";
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        account_id = nSign_Controller.username;
        loaded_user_details(account_id);
    }

    void loaded_user_details(String id) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            fill(id);
        }));
        timeline.play();
    }

    public void fill(String id) {        
        conn = conclass.livewell_localhost();
        try {
            sql = "select * from livewell.users where customId = ? or contact = ? or email = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, id);
            pst.setString(2, id);
            pst.setString(3, id);
            rs = pst.executeQuery();
            byte[] content = new byte[1024];
            int size = 0;
            OutputStream ous = new FileOutputStream("out.jpg");
            if (rs.next()) {
                InputStream is = rs.getBinaryStream("userImage");
                while ((size = is.read(content)) != -1) {
                    ous.write(content, 0, size);
                }
                ous.flush();
                Image imager = new Image("file:out.jpg");
//                imageView_deptMgmt_profile.setImage(imager);
                lbl_id.setText(rs.getString("customId")); 
                lbl_imageView.setFill(new ImagePattern(imager));
                lbl_name.setText(rs.getString("fname") + " " + rs.getString("sname"));
            }

        } catch (IOException | SQLException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void load_patient_accounts(ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/Accountancy/Patient_Section.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
        }
    }

    @FXML
    private void load_client_accounts(ActionEvent event) {
         try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/Accountancy/client_section.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
        }
    }

    @FXML
    private void load_deptors_creditors(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/Accountancy/creditorsNdebtors.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
        }
    }


    @FXML
    private void do_log_out(ActionEvent event) {
        semanticsClass.set_notification("Info", "Not Yet Implemented, will be available in late update");
    }

    @FXML
    private void change_password(ActionEvent event) {
        semanticsClass.set_notification("Info", "Not Yet Implemented, will be available in late update");
    }

    @FXML
    private void load_memberAcc(ActionEvent event) {
    }

}
