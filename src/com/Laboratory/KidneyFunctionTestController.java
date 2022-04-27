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
import com.tables.lab_KFT;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class KidneyFunctionTestController implements Initializable {

    @FXML
    private MenuItem menu_clr_all;
    @FXML
    private MenuItem menu_save;
    @FXML
    private MenuItem menu_delete;
    @FXML
    private GridPane parent_KFT;
    @FXML
    private JFXSlider slider_sodium;
    @FXML
    private TextField txt_sodium;
    @FXML
    private JFXSlider slider_potassium;
    @FXML
    private TextField txt_potassium;
    @FXML
    private JFXSlider slider_chloride;
    @FXML
    private TextField txt_chloride;
    @FXML
    private JFXSlider slider_Co2;
    @FXML
    private TextField txt_Co2;
    @FXML
    private JFXSlider slider_urea;
    @FXML
    private TextField txt_urea;
    @FXML
    private JFXSlider slider_creatine;
    @FXML
    private TextField txt_creatine;
    @FXML
    private TextField txt_egfr;

    String pid = "";
    Laboratory_Services ls = new Laboratory_Services();
    Connection conn = null;
    PreparedStatement pst = null;
    String sql = "";
    ResultSet rs = null;

    ObservableList<lab_KFT> observe_KFT = null;
    lab_KFT default_kft = null;

    ;
    @FXML
    private TableView<lab_KFT> tableView_KFT;
    @FXML
    private TableColumn<lab_KFT, String> col_pid;
    @FXML
    private TableColumn<lab_KFT, String> col_sodium;
    @FXML
    private TableColumn<lab_KFT, String> col_potassium;
    @FXML
    private TableColumn<lab_KFT, String> col_bicarbonate;
    @FXML
    private TableColumn<lab_KFT, String> col_urea;
    @FXML
    private TableColumn<lab_KFT, String> col_creatine;
    @FXML
    private TableColumn<lab_KFT, String> col_egfr;
    @FXML
    private TableColumn<lab_KFT, String> col_chloride;
    @FXML
    private ComboBox<String> cbo_search_zone;
    @FXML
    private TextField txt_search_item;
    @FXML
    private Label lbl_user_details;
    @FXML
    private TableColumn<?, ?> col_date;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = conclass.livewell_localhost();
        pid = LaboratoryController.pid;
        observe_KFT = FXCollections.observableArrayList();
        default_kft = new lab_KFT();

        settings();
        service_load_kft(semanticsClass.returneDate());
        load_liver_ids();
        load_userDetails(pid);
    }

    void settings() {
        //Pid, sodium, potassium, chloride, total_co2, urea, creatinine, eGFR, On_date;
        col_pid.setCellValueFactory(new PropertyValueFactory<>("Pid"));
        col_sodium.setCellValueFactory(new PropertyValueFactory<>("sodium"));
        col_potassium.setCellValueFactory(new PropertyValueFactory<>("potassium"));
        col_chloride.setCellValueFactory(new PropertyValueFactory<>("chloride"));
        col_bicarbonate.setCellValueFactory(new PropertyValueFactory<>("total_co2"));
        col_urea.setCellValueFactory(new PropertyValueFactory<>("urea"));
        col_creatine.setCellValueFactory(new PropertyValueFactory<>("creatinine"));
        col_egfr.setCellValueFactory(new PropertyValueFactory<>("egfr"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("On_date"));

        cbo_search_zone.setItems(FXCollections.observableArrayList("All", "Today"));

        tableView_KFT.setOnMouseClicked((event) -> {
            if (!tableView_KFT.getSelectionModel().isEmpty()) {
                default_kft = tableView_KFT.getSelectionModel().getSelectedItem();
                txt_sodium.setText(default_kft.getSodium());
                txt_potassium.setText(default_kft.getPotassium());
                txt_chloride.setText(default_kft.getChloride());
                txt_Co2.setText(default_kft.getTotal_co2());
                txt_urea.setText(default_kft.getUrea());
                txt_creatine.setText(default_kft.getCreatinine());
                txt_egfr.setText(default_kft.getEgfr());
            }

        });

        semanticsClass.bindSliderAndTextField(slider_sodium, txt_sodium);
        semanticsClass.bindSliderAndTextField(slider_potassium, txt_potassium);
        semanticsClass.bindSliderAndTextField(slider_chloride, txt_chloride);
        semanticsClass.bindSliderAndTextField(slider_Co2, txt_Co2);
        semanticsClass.bindSliderAndTextField(slider_urea, txt_urea);
        semanticsClass.bindSliderAndTextField(slider_creatine, txt_creatine);

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

    void load_liver_ids() {//load all dates and pids in this table
        cbo_search_zone.setItems(FXCollections.observableArrayList("All", "Today"));
        sql = "select Distinct pid from lab_kft order by record_time asc";
        ObservableList<String> items = FXCollections.observableArrayList();
        ObservableList<String> id_items = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                id_items.add(rs.getString("pid"));
            }
            TextFields.bindAutoCompletion(txt_search_item, id_items);
        } catch (SQLException e) {
            System.out.println(e);
        }

        sql = "select Distinct on_date from lab_kft order by record_time asc";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                items.add(rs.getString("on_date"));
            }
            cbo_search_zone.getItems().addAll(items);
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void service_load_kft(String valuer) {
        Service<ObservableList<lab_KFT>> service = ls.new KFT().kft_list(valuer);
        service.setOnSucceeded((event) -> {
            tableView_KFT.setItems(service.getValue());
        });
        service.start();
    }

    public void load_kft(String value) {
        try {
            sql = "select * from livewell.lab_kft where on_date  = ? ";
            conn = conclass.livewell_localhost();
            pst = conn.prepareStatement(sql);
            pst.setString(1, value);
            rs = pst.executeQuery();
            tableView_KFT.getItems().clear();
            while (rs.next()) {
                default_kft = new lab_KFT();
                default_kft.setPid(rs.getString("pid"));
                default_kft.setSodium(rs.getString("sodium"));
                default_kft.setPotassium(rs.getString("potassium"));
                default_kft.setChloride(rs.getString("chloride"));
                default_kft.setTotal_co2(rs.getString("total_co2"));
                default_kft.setUrea(rs.getString("urea"));
                default_kft.setCreatinine(rs.getString("creatinine"));
                default_kft.setEgfr(rs.getString("egfr"));
                default_kft.setOn_date(rs.getString("on_date"));
                observe_KFT.add(default_kft);
            }
            tableView_KFT.setItems(observe_KFT);
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    @FXML
    private void do_clear_all(ActionEvent event) {
        semanticsClass.set_notification("Info", "Not Yet Implemented, will be available in late update");
    }

    @FXML
    private void do_kidney_save(ActionEvent event) {
        lab_KFT kft
                = new lab_KFT(
                        pid, txt_sodium.getText(), txt_potassium.getText(),
                        txt_chloride.getText(), txt_Co2.getText(), txt_urea.getText(),
                        txt_creatine.getText(), txt_egfr.getText(), semanticsClass.returneDate(),
                        semanticsClass.nowStamp(new Date())
                );
        Service<Boolean> service = ls.new KFT().KFT_add(kft);
        service.setOnCancelled((e) -> {
            semanticsClass.set_notification("Error", service.getMessage());
        });
        service.setOnSucceeded((e) -> {
            if (service.getValue() == true) {
                semanticsClass.set_notification("Success", service.getMessage());
                load_kft(semanticsClass.returneDate());
            } else {
                semanticsClass.set_notification("Error", "An error occured");
            }
        });
        service.start();
    }

    @FXML
    private void do_kidney_delete(ActionEvent event) {
        semanticsClass.set_notification("Info", "Not Yet Implemented, will be available in late update");
    }

    @FXML
    private void do_close_portal(ActionEvent event) {
        semanticsClass.set_notification("Info", "Not Yet Implemented, will be available in late update");
    }

    @FXML
    private void do_kidney_update(ActionEvent event) {
        pid = LaboratoryController.pid;
        lab_KFT kft
                = new lab_KFT(
                        pid, txt_sodium.getText(), txt_potassium.getText(),
                        txt_chloride.getText(), txt_Co2.getText(), txt_urea.getText(),
                        txt_creatine.getText(), txt_egfr.getText(), semanticsClass.returneDate(), semanticsClass.nowStamp(new Date())
                );
        Service<Boolean> service = ls.new KFT().KFT_update(kft);
        service.setOnCancelled((e) -> {
            semanticsClass.set_notification("Error", service.getMessage());
        });
        service.setOnSucceeded((e) -> {
            if (service.getValue() == true) {
                semanticsClass.set_notification("SUCCESS", "KFT RESULT UPDATED SUCCESSFULLY");
                load_kft(semanticsClass.returneDate());
            } else {
                semanticsClass.set_notification("ERROR", "AN ERROR OCCURED");
                load_kft(semanticsClass.returneDate());
            }
        });
        service.start();
    }

    @FXML
    private void initSearch(ActionEvent event) {//this is a make search
        service_load_kft(txt_search_item.getText());
    }

    @FXML
    private void cbo_zoner(ActionEvent event) {// this is a mob search
        String valuer = cbo_search_zone.getSelectionModel().getSelectedItem();
        switch (valuer) {
            case "All":
//                load_kft("*");
                service_load_kft("*");
                break;
            case "Today":
                service_load_kft(semanticsClass.returneDate());
                break;
            default:
                service_load_kft(valuer);
                break;
        }
    }

    private void do_print_kft(ActionEvent event) throws FileNotFoundException {
        try {

            sql = "SELEct lab_kft.*,"
                    + "	patient_info.registration_no,"
                    + "	patient_info.surname,patient_info.other_names"
                    + " from lab_kft,patient_info"
                    + " where lab_kft.on_date = '"+semanticsClass.returneDate()+"' ";

            InputStream is = new FileInputStream("/com/reports/reLab_kft_report.jrxml");

            JasperDesign jd = JRXmlLoader.load(is);

            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);

            jd.setQuery(newQuery);

            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp);
            
        } catch (JRException ex) {
            System.out.println(ex);
        }
    }

}
