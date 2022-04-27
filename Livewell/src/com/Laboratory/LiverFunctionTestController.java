/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Laboratory;

import com.connection.conclass;
import com.jfoenix.controls.JFXSlider;
import com.semantics.semanticsClass;
import com.services.Laboratory_Services;
import com.tables.lab_LFT;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class LiverFunctionTestController implements Initializable {

    @FXML
    private MenuItem menu_clr_all;
    @FXML
    private MenuItem menu_save;
    @FXML
    private MenuItem menu_delete;
    @FXML
    private GridPane parent_LFT;
    @FXML
    private JFXSlider slider_protein;
    @FXML
    private TextField txt_protien;
    @FXML
    private JFXSlider slider_albumin;
    @FXML
    private TextField txt_albumin;
    @FXML
    private JFXSlider slider_ast;
    @FXML
    private TextField txt_ast;
    @FXML
    private JFXSlider slider_alt;
    @FXML
    private TextField txt_alt;
    @FXML
    private JFXSlider slider_ggt;
    @FXML
    private TextField txt_ggt;
    @FXML
    private JFXSlider slider_alp;
    @FXML
    private TextField txt_alp;
    @FXML
    private JFXSlider slider_TBilirubin;
    @FXML
    private TextField txt_TBilirubin;
    @FXML
    private JFXSlider slider_DBilirubin;
    @FXML
    private TextField txt_HBilirubin;
    @FXML
    private TableView<lab_LFT> tableView_liver;
    @FXML
    private TableColumn<?, ?> col_pid;
    @FXML
    private TableColumn<?, ?> col_protein;
    @FXML
    private TableColumn<?, ?> col_albumin;
    @FXML
    private TableColumn<?, ?> col_ast;
    @FXML
    private TableColumn<?, ?> col_alt;
    @FXML
    private TableColumn<?, ?> col_ggt;
    @FXML
    private TableColumn<?, ?> col_alp;
    @FXML
    private TableColumn<?, ?> col_TBilirubin;
    @FXML
    private TableColumn<?, ?> col_DBilirubin;
    @FXML
    private TableColumn<?, ?> col_onDate;

    Laboratory_Services ls = new Laboratory_Services();
    ObservableList<lab_LFT> observe_LFT = null;
    lab_LFT default_lft = new lab_LFT();
    String pid = "";
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = "";
    @FXML
    private ComboBox<String> cbo_Liver;
    @FXML
    private TextField txt_Liver;
    @FXML
    private Label lbl_user_details;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pid = LaboratoryController.pid;
        conn = conclass.livewell_localhost();
        observe_LFT = FXCollections.observableArrayList();
        column_settings();
        semanticsClass.bindSliderAndTextField(slider_DBilirubin, txt_HBilirubin);
        semanticsClass.bindSliderAndTextField(slider_TBilirubin, txt_TBilirubin);
        semanticsClass.bindSliderAndTextField(slider_albumin, txt_albumin);
        semanticsClass.bindSliderAndTextField(slider_ast, txt_ast);
        semanticsClass.bindSliderAndTextField(slider_alp, txt_alp);
        semanticsClass.bindSliderAndTextField(slider_alt, txt_alt);
        semanticsClass.bindSliderAndTextField(slider_ggt, txt_ggt);
        semanticsClass.bindSliderAndTextField(slider_protein, txt_protien);
        inSql_Settings();
        load_liver_list(semanticsClass.returneDate());
        load_userDetails(pid);
    }

    void column_settings() {
        col_pid.setCellValueFactory(new PropertyValueFactory<>("pid"));
        col_protein.setCellValueFactory(new PropertyValueFactory<>("protein"));
        col_albumin.setCellValueFactory(new PropertyValueFactory<>("albumin"));
        col_ast.setCellValueFactory(new PropertyValueFactory<>("AST"));
        col_alt.setCellValueFactory(new PropertyValueFactory<>("ALT"));
        col_ggt.setCellValueFactory(new PropertyValueFactory<>("GGT"));
        col_alp.setCellValueFactory(new PropertyValueFactory<>("ALP"));
        col_TBilirubin.setCellValueFactory(new PropertyValueFactory<>("T_BILIRUBIN"));
        col_DBilirubin.setCellValueFactory(new PropertyValueFactory<>("H_BILIRUBIN"));
        col_onDate.setCellValueFactory(new PropertyValueFactory<>("on_date"));
    }

    void load_userDetails(String id) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), (event) -> {
            try {
                sql = "select registration_NO,surname,other_names from livewell.patient_info where registration_NO = ? ";
                pst = conn.prepareStatement(sql);
                pst.setString(1, id);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String info = rs.getString("registration_NO") + " : " + rs.getString("surname") + " " + rs.getString("other_names");
                    lbl_user_details.setText(info);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Urine_REController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }));
        timeline.play();
    }

    void load_liver_ids() {
        cbo_Liver.setItems(FXCollections.observableArrayList("All", "Today"));
        sql = "select Distinct pid from lab_urine order by record_time asc";
        ObservableList<String> items = FXCollections.observableArrayList();
        ObservableList<String> id_items = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                id_items.add(rs.getString("pid"));
            }
            TextFields.bindAutoCompletion(txt_Liver, id_items);
        } catch (SQLException e) {
            System.out.println(e);
        }

        sql = "select Distinct on_date from lab_urine order by record_time asc";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                items.add(rs.getString("on_date"));
            }
            cbo_Liver.getItems().addAll(items);
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    void inSql_Settings() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            load_liver_ids();
        }));
        timeline.play();
    }

    void load_liver_list(String valuer) {
        Service<ObservableList<lab_LFT>> lft_list = ls.new LFT().lft_list(valuer);
        lft_list.setOnSucceeded(e -> {
            tableView_liver.setItems(lft_list.getValue());
        });
        lft_list.start();
    }

    @FXML
    private void do_clear_all(ActionEvent event) {
        semanticsClass.clearTextFields(txt_albumin, txt_alp, txt_alt, txt_ast, txt_ggt, txt_protien, txt_HBilirubin, txt_TBilirubin);
    }

    @FXML
    private void do_liver_save(ActionEvent event) {
        default_lft = new lab_LFT(
                pid, txt_protien.getText(), txt_albumin.getText(),
                txt_ast.getText(), txt_alt.getText(), txt_ggt.getText(),
                txt_alp.getText(), txt_TBilirubin.getText(),
                txt_HBilirubin.getText(), semanticsClass.returneDate(),
                semanticsClass.nowStamp(new Date()));
        Service<Boolean> service = ls.new LFT().LFT_add(default_lft);
        service.setOnCancelled((e) -> {
            semanticsClass.set_notification("Error", service.getMessage());
        });
        service.setOnSucceeded((e) -> {
            semanticsClass.set_notification("Success", service.getMessage());
            load_liver_list(semanticsClass.returneDate());

        });
        service.start();
    }

    @FXML
    private void do_liver_update(ActionEvent event) {
        default_lft = new lab_LFT(
                pid, txt_protien.getText(), txt_albumin.getText(),
                txt_ast.getText(), txt_alt.getText(), txt_ggt.getText(),
                txt_alp.getText(), txt_TBilirubin.getText(),
                txt_HBilirubin.getText(), semanticsClass.returneDate(),
                semanticsClass.nowStamp(new Date()));
        Service<Boolean> service = ls.new LFT().LFT_update(default_lft);
        service.setOnCancelled((e) -> {
            semanticsClass.set_notification("Error", service.getMessage());
        });
        service.setOnSucceeded((e) -> {
            load_liver_list(semanticsClass.returneDate());
        });
        service.start();
    }

    @FXML
    private void liver_mob_search(ActionEvent event) {
        switch (cbo_Liver.getSelectionModel().getSelectedItem()) {
            case "All":
                load_liver_list("*");
                break;
            case "Today":
                load_liver_list(semanticsClass.returneDate());
                break;
            default:
                String valuer = cbo_Liver.getSelectionModel().getSelectedItem();
                load_liver_list(valuer);
                break;
        }
    }

    @FXML
    private void liver_make_search(ActionEvent event) {
        load_liver_list(txt_Liver.getText());
    }

}
