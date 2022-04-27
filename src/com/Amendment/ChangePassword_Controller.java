/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Amendment;

import com.connection.conclass;
import com.semantics.semanticsClass;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class ChangePassword_Controller implements Initializable {

    @FXML
    private Label lbl_noMatch;
    @FXML
    private PasswordField txt_new_password;
    @FXML
    private PasswordField txt_new_password_r;
    @FXML
    private TextField txt_username;
    @FXML
    private Label txt_yourPassword;
    @FXML
    private Label lbl_id;
    private int count = 0;
    private String sql;
    public static String CWD = "";
    public static String username = "";
    public String goodPassword = "";

    Connection conn = conclass.livewell_localhost();
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    @FXML
    private Button btn_change;
    @FXML
    private Button btn_recover;
    @FXML
    private VBox root;
    @FXML
    private PasswordField txt_old_password;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        checkMatch();
        btn_change.setDisable(true);
        root.setDisable(true);
    }

    void checkMatch() {
        txt_old_password.setOnAction(e -> {
//           sql = "UPDATE livewell." + CWD + " SET password = '" + goodPassword + "'  WHERE user_id = '" + username + "' ";
            sql = "select * from livewell." + CWD + " where user_id = '" + username + "' and password = ?";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, txt_old_password.getText());
                rs = pst.executeQuery();
                if (rs.next()) {
                    root.setDisable(false);
                } else {
                    root.setDisable(true);
                }
            } catch (SQLException ex) {
            }

        });
        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            lbl_id.setText(lbl_id.getText() + " " + username);
        }));
        timer.play();

        txt_new_password_r.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.contentEquals(txt_new_password.getText())) {
                lbl_noMatch.setVisible(true);
                btn_change.setDisable(true);
            } else {
                lbl_noMatch.setVisible(false);
                btn_change.setDisable(false);
                goodPassword = newValue;
            }
        });

        txt_new_password.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.contentEquals(txt_new_password_r.getText())) {
                lbl_noMatch.setVisible(true);
                btn_change.setDisable(true);
            } else {
                lbl_noMatch.setVisible(false);
                btn_change.setDisable(false);
                goodPassword = newValue;
            }
        });
    }

    @FXML
    private void changePassword(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Press OK to continue");
        alert.setTitle("Continue");
        Optional<ButtonType> results = alert.showAndWait();
        if (results.get() == ButtonType.OK) {
            sql = "UPDATE livewell." + CWD + " SET password = '" + goodPassword + "'  WHERE user_id = '" + username + "' ";
            try {
                pst = conn.prepareStatement(sql);
                pst.execute();
                semanticsClass.set_notification("Success", "Password successfully changed to " + goodPassword);
            } catch (SQLException e) {
                semanticsClass.SySoutException(e);
            }
        }

    }

    @FXML
    private void recoverPassword(ActionEvent event) {
    }

}
