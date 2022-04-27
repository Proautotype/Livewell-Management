/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payAtten;

import com.connection.conclass;
import com.semantics.semanticsClass;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.controlsfx.control.MaskerPane;
import java.sql.*;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.PasswordField;

/**
 * FXML Controller class
 *
 * @author G
 */
public class SettingsController implements Initializable {

    @FXML
    private TextField txt_hour;
    @FXML
    private TextField txt_min;
    @FXML
    private TextField txt_sec;
    int htimer, mtimer, stimer;
    @FXML
    private MaskerPane parent_masker;

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = "";
    @FXML
    private TextField txt_user;
    @FXML
    private PasswordField txt_pword;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        htimer = RegisterController.htimer;
        mtimer = RegisterController.mtimer;
        stimer = RegisterController.stimer;

        intToTextField(htimer, txt_hour);
        intToTextField(mtimer, txt_min);
        intToTextField(stimer, txt_sec);
    }

    void intToTextField(int i, TextField txt) {
        txt.setText(String.valueOf(i));
    }

    int TextFieldToInt(TextField txt) {
        return Integer.parseInt(txt.getText().trim());
    }

    @FXML
    private void do_setTimer(ActionEvent event) {
        htimer = TextFieldToInt(txt_hour);
        mtimer = TextFieldToInt(txt_min);
        stimer = TextFieldToInt(txt_sec);

        RegisterController.htimer = htimer;
        RegisterController.mtimer = mtimer;
        RegisterController.stimer = stimer;
    }

    @FXML
    private void do_createUser(ActionEvent event) {
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        conn = conclass.livewell_localhost();
                        try {
                            sql = "INSERT INTO `livewell`.`registrar_users`"
                                    + "(`contact`,`pword`) VALUES (?,?)";
                            pst = conn.prepareStatement(sql);
                            pst.setString(1, txt_user.getText());
                            pst.setString(2, txt_pword.getText());
                            pst.execute();
                            updateMessage("Created successfully");
                            return true;
                        } catch (SQLException e) {
                            if (e.getErrorCode() == 1062) {
                                updateMessage("user already exist");
                                Thread.sleep(2000);
                                cancel();
                            } else {
                                updateMessage(e.getMessage());
                                cancel();
                                Thread.sleep(2000);
                            }
                        }
                        return false;
                    }
                };
            }
        };
        service.setOnRunning(e -> {
            parent_masker.visibleProperty().bind(service.runningProperty());
        });
        service.setOnCancelled(e -> {
            semanticsClass.set_notification("Error", service.getMessage());
        });
        service.setOnSucceeded(e -> {
            semanticsClass.set_notification("Success ", "User created successfully");
        });
        service.start();
    }

    @FXML
    private void do_updateuser(ActionEvent event) {
    }

}
