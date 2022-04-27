/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.signIn;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class SettingsController implements Initializable {

    @FXML
    private JFXComboBox<String> cbo_connections;
    @FXML
    private TextField txt_ip;
    @FXML
    private TextField txt_port;
    @FXML
    private PasswordField txt_username;
    @FXML
    private PasswordField txt_password;

    JSONObject serverDetails = new JSONObject();
    setting_preferences sp = new setting_preferences();

    public void setCbo_connections(JFXComboBox<String> cbo_connections) {
        this.cbo_connections = cbo_connections;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       cbo_connections.setItems(FXCollections.observableArrayList("Orange","Mango","Guava"));
       setting_preferences.add();
       readServerDetails();
    }    

    void readServerDetails(){       
        serverDetails = setting_preferences.read();
        txt_ip.setText((String)serverDetails.get("ip"));
        txt_port.setText((String)serverDetails.get("port"));
        txt_username.setText((String)serverDetails.get("username"));
        txt_password.setText((String)serverDetails.get("password"));
    }
    
    @FXML
    private void do_(ActionEvent event) {
        int i = cbo_connections.getSelectionModel().getSelectedIndex();
       switch (i) {
            case 0:
//                controlledConn("root", "cjcj1122", "192.168.8.100", "liveWell");
                break;
            case 1:
//                controlledConn("root", "cjcj1122", "192.168.8.101", "liveWell");
                break;
            case 2:
//                controlledConn("root", "cjcj1122", "192.168.8.102", "liveWell");
                break;
        }
    }

    @FXML
    private void do_save(ActionEvent event) {
        String ip = txt_ip.getText();
        String port = txt_port.getText();
        String user = txt_username.getText();
        String password = txt_password.getText();
        
        setting_preferences sp = new setting_preferences();
        sp.setIp(ip);
        sp.setPort(port);
        sp.setUsername(user);
        sp.setPassword(password);
        
        System.out.println(sp.getIp() + " " + sp.getUsername());
        setting_preferences.WriteServerDetails(sp);
         readServerDetails();
        
    }
    
    void initDefaultValues(){
        setting_preferences sp = new setting_preferences();
        txt_ip.setText(sp.getIp());
        txt_port.setText(sp.getPort());
        txt_username.setText(sp.getUsername());
        txt_password.setText(sp.getPassword());
    }
    
}
