/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payAtten;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author G
 */
public class Attend_list_Controller implements Initializable {

    @FXML
    private TableView<list_class> table_attend;
    @FXML
    private TableColumn<?, ?> col_date;
    @FXML
    private TableColumn<?, ?> col_name;
    @FXML
    private TableColumn<?, ?> col_day;
    @FXML
    private TableColumn<?, ?> col_remark;
    @FXML
    private ComboBox<String> cbo_date;
    @FXML
    private JFXRadioButton rd_all;
    @FXML
    private JFXRadioButton rd_early;
    @FXML
    private JFXRadioButton rd_late;
    @FXML
    private JFXTextField txt_names;
    @FXML
    private ToggleGroup filterBy;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        table_settings();
    }    

    @FXML
    private void reload(ActionEvent event) {
        load("");
    }

    private void table_settings() {
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_day.setCellValueFactory(new PropertyValueFactory<>("day"));
        col_remark.setCellValueFactory(new PropertyValueFactory<>("remark"));
    }
    
    public void load(String e){
       
    }
    
}
