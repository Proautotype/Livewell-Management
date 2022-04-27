/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Nurses;

import com.connection.conclass;
import com.semantics.semanticsClass;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.web.HTMLEditor;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author G
 */
public class VlchController implements Initializable {

    @FXML
    private Label lbl_id;
    @FXML
    private Label lbl_name;
    @FXML
    private HTMLEditor txt_complaint;
    @FXML
    private TextArea txt_history;

    String sql = "";
    PreparedStatement pst = null;
    ResultSet rs = null;
    Connection conn = null;
    String id = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = conclass.livewell_localhost();
        id = nursesController.vpch_id;
        System.out.println(id);
        load_names("LHC-045-19");        
    }

    void load_names(String id) {
        sql = "select * from patient_info where Registration_NO = ? ";
        Timeline timer = new Timeline(new KeyFrame(Duration.ONE, e -> {
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, id);
                rs = pst.executeQuery();
                if (rs.next()) {
                    lbl_id.setText(rs.getString("Registration_NO"));
                    lbl_name.setText(rs.getString("surname") + " " + rs.getString("other_names"));
                }load_health_details("LHC-045-19");
            } catch (SQLException ee) {
                System.out.println(ee);
            }
        })); 
        timer.play();
    }

    void load_health_details(String id) {
        sql = "select * from nurses_on_patient where opd_id = ? ";

        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, id);
            rs = pst.executeQuery();
            if (rs.last()) {
                txt_complaint.setHtmlText(rs.getString("complains"));
                txt_history.setText(rs.getString("history"));            
            }
        } catch (SQLException e) {
           System.out.println(e);
        }
    }
}
