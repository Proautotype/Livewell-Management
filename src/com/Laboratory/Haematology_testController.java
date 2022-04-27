/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Laboratory;

import static com.Laboratory.LaboratoryController.haemaStage;
import com.semantics.semanticsClass;
import com.services.Laboratory_Services;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import com.connection.conclass;
import com.services.Laboratory_Services.widal;
import com.tables.lab_haematology_table;
import com.tables.lab_widal;
import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Date;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class Haematology_testController implements Initializable {

    @FXML
    private MenuItem menu_clr_all;
    @FXML
    private MenuItem menu_save;
    @FXML
    private MenuItem menu_delete;
    @FXML
    private GridPane parent_haematology;
    @FXML
    private TextField txt_HB;
    @FXML
    private TextField txt_blood_grouping;
    @FXML
    private TextField txt_rheuses;
    @FXML
    private TextField txt_HEPATITIS_B;
    @FXML
    private TextField txt_SICKLING_SCREENING;
    @FXML
    private TextField txt_BLOOD_FILM;
    @FXML
    private TextField txt_HB_ELECTROPHORESIS;
    @FXML
    private TextField txt_G6PD;
    @FXML
    private TextField txt_VDRL;
    @FXML
    private TextField txt_PYLORI;
    @FXML
    private TextField txt_FASTING_BLOOD_SUGAR;
    @FXML
    private TextField txt_RANDOM_BLOOD_SUGAR;
    @FXML
    private TextField txt_WBC;
    @FXML
    private TextField txt_ESR;
    @FXML
    private TextField txt_CHOLESTEROL;
    @FXML
    private TableView<lab_haematology_table> tableView_haematology;
    @FXML
    private TableColumn<?, ?> col_haema_pid;
    @FXML
    private TableColumn<?, ?> col_bg;
    @FXML
    private TableColumn<?, ?> col_rheuses;
    @FXML
    private TableColumn<?, ?> col_hb;
    @FXML
    private TableColumn<?, ?> col_HBsAg;
    @FXML
    private TableColumn<?, ?> col_SScr;
    @FXML
    private TableColumn<?, ?> col_G6PD;
    @FXML
    private TableColumn<?, ?> col_VDRL;
    @FXML
    private TableColumn<?, ?> col_H_PYROLI;
    private TableColumn<?, ?> col_fbs;
    private TableColumn<?, ?> col_rbs;
    private TableColumn<?, ?> col_wbc;
    private TableColumn<?, ?> col_ESR;
    private TableColumn<?, ?> col_CHOLEST;
    @FXML
    private VBox parent_widal;
    @FXML
    private TextArea txt_salmonella_typhi_O;
    @FXML
    private TextArea txt_salmonella_typhi_H;
    @FXML
    private TextArea txt_rapid_hiv_sport_test;
    @FXML
    private TableView<lab_widal> tableView_liver;
    @FXML
    private TableColumn<?, ?> col_widal_pid;
    @FXML
    private TableColumn<?, ?> col_STO;
    @FXML
    private TableColumn<?, ?> col_STH;
    @FXML
    private TableColumn<?, ?> col_RHST;
    @FXML
    private Accordion parent_anchordion;
    @FXML
    private TitledPane Haema;
    @FXML
    private TitledPane widal;
    String operative_section = "";

    public static String pid = "";

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = "";
    @FXML
    private MenuItem menu_update;
    Laboratory_Services parent_ls = new Laboratory_Services();
    @FXML
    private TableColumn<?, ?> col_widal_onDate;
    @FXML
    private Button btn_widal_search_me;
    @FXML
    private Label lbl_selected_patient;
    @FXML
    private ComboBox<String> cbo_HAEMA;
    @FXML
    private TextField txt_HAEMA;
    @FXML
    private ComboBox<String> cbo_WIDAL;
    @FXML
    private TextField txt_WIDAL;
    String getPid;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        parent_ls = new Laboratory_Services();
        pid = getPid = LaboratoryController.pid;
        conn = conclass.livewell_localhost();
        Settings();
        inSql_Settings();
        load_userDetails(pid);
    }

    void load_userDetails(String id) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), (event) -> {
            try {
                sql = "select registration_NO,surname,other_names from livewell.patient_info where registration_NO = ? ";
                pst = conn.prepareStatement(sql);
                pst.setString(1, id);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String in_userD = rs.getString("registration_NO") + " : " + rs.getString("surname") + " " + rs.getString("other_names");
                    lbl_selected_patient.setText(in_userD);
                }
            } catch (SQLException ex) {
            }
        }));
        timeline.play();
    }

    void load_haema_list() {
        cbo_HAEMA.setItems(FXCollections.observableArrayList("All", "Today"));
        sql = "select Distinct pid from lab_haematology order by record_time asc";
        ObservableList<String> items = FXCollections.observableArrayList();
        ObservableList<String> id_items = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                id_items.add(rs.getString("pid"));
            }
            TextFields.bindAutoCompletion(txt_HAEMA, id_items);
        } catch (SQLException e) {
            System.out.println(e);
        }

        sql = "select Distinct on_date from lab_haematology order by record_time asc";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                items.add(rs.getString("on_date"));
            }
            cbo_HAEMA.getItems().addAll(items);
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    void load_widal_list() {
        cbo_WIDAL.setItems(FXCollections.observableArrayList("All", "Today"));
        sql = "select Distinct pid from lab_widal order by record_time asc";
        ObservableList<String> on_dateItems = FXCollections.observableArrayList();
        ObservableList<String> id_items = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                id_items.add(rs.getString("pid"));
            }
            TextFields.bindAutoCompletion(txt_WIDAL, id_items);
        } catch (SQLException e) {
            System.out.println(e);
        }

        sql = "select Distinct on_date from lab_widal order by record_time asc";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                on_dateItems.add(rs.getString("on_date"));
            }
            cbo_WIDAL.getItems().addAll(on_dateItems);
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    void inSql_Settings() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            load_haema_list();
            load_widal_list();
        }));
        timeline.play();
    }

    lab_haematology_table lht = new lab_haematology_table();
    ObservableList<lab_haematology_table> observable_lht = FXCollections.observableArrayList();

    void Settings() {
        col_haema_pid.setCellValueFactory(new PropertyValueFactory<>("pid"));
        col_bg.setCellValueFactory(new PropertyValueFactory<>("blood_groupin"));
        col_rheuses.setCellValueFactory(new PropertyValueFactory<>("rhesus"));
        col_hb.setCellValueFactory(new PropertyValueFactory<>("hb"));
        col_HBsAg.setCellValueFactory(new PropertyValueFactory<>("hepatitis"));
        col_SScr.setCellValueFactory(new PropertyValueFactory<>("sickling"));//       
        col_G6PD.setCellValueFactory(new PropertyValueFactory<>("g6pd"));
        col_VDRL.setCellValueFactory(new PropertyValueFactory<>("vdrl"));
        col_H_PYROLI.setCellValueFactory(new PropertyValueFactory<>("H_pylori"));

        col_widal_pid.setCellValueFactory(new PropertyValueFactory<>("pid"));
        col_STO.setCellValueFactory(new PropertyValueFactory<>("salmonellaTO"));
        col_STH.setCellValueFactory(new PropertyValueFactory<>("salmonellaTH"));
        col_RHST.setCellValueFactory(new PropertyValueFactory<>("RHSt"));
        col_widal_onDate.setCellValueFactory(new PropertyValueFactory<>("on_date"));

        tableView_haematology.setOnMouseClicked((event) -> {
            if (!tableView_haematology.getSelectionModel().isEmpty()) {
                lab_haematology_table myLHT = tableView_haematology.getSelectionModel().getSelectedItem();
                txt_blood_grouping.setText(myLHT.getBlood_groupin());
                txt_rheuses.setText(myLHT.getRhesus());
                txt_HB.setText(myLHT.getHb());
                txt_HEPATITIS_B.setText(myLHT.getHB_Elec());
                txt_SICKLING_SCREENING.setText(myLHT.getSickling());
                txt_BLOOD_FILM.setText(myLHT.getBlood_film());
                txt_HB_ELECTROPHORESIS.setText(myLHT.getHB_Elec());
                txt_G6PD.setText(myLHT.getG6pd());
                txt_VDRL.setText(myLHT.getVdrl());
                txt_PYLORI.setText(myLHT.getH_pylori());
                txt_FASTING_BLOOD_SUGAR.setText(myLHT.getFBS());
                txt_RANDOM_BLOOD_SUGAR.setText(myLHT.getRBS());
                txt_WBC.setText(myLHT.getWBC());
                txt_ESR.setText(myLHT.getESR());
                txt_CHOLESTEROL.setText(myLHT.getCholestrol());
            }
        });

        tableView_liver.setOnMouseClicked((event1) -> {
            lab_widal lbw = tableView_liver.getSelectionModel().getSelectedItem();
            txt_salmonella_typhi_O.setText(lbw.getSalmonellaTO());
            txt_salmonella_typhi_H.setText(lbw.getSalmonellaTH());
            txt_rapid_hiv_sport_test.setText(lbw.getRHSt());
        });

        load_haematologyList(semanticsClass.returneDate());
        Haematology_testController.this.load_widal_list(semanticsClass.returneDate());
    }

    void load_haematologyList(String valuer) {//load haematology list
        try {
            if (valuer.contentEquals("*")) {
                sql = "select * from livewell.lab_haematology order by record_time";
            } else {
                sql = "select * from livewell.lab_haematology where on_date = '" + valuer + "' or pid = '" + valuer + "' order by record_time";
            }
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tableView_haematology.getItems().clear();
            while (rs.next()) {
                lht = new lab_haematology_table(
                        rs.getString("pid"), rs.getString("blood_grouping"),
                        rs.getString("rhesus"),
                        rs.getString("HB"), rs.getString("hepatitis_B"),
                        rs.getString("sickling_screening"),
                        rs.getString("blood_film"), rs.getString("hb_electrophoresis"),
                        rs.getString("g6pd"),
                        rs.getString("vdrl"), rs.getString("h_pyroli"),
                        rs.getString("FBS"),
                        rs.getString("RBS"), rs.getString("WBC"),
                        rs.getString("ESR"),
                        rs.getString("cholesterol"),
                        rs.getString("on_date"), rs.getTimestamp("record_time"));
                observable_lht.add(lht);
            }
            tableView_haematology.setItems(observable_lht);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    lab_widal lW = new lab_widal();
    ObservableList<lab_widal> observable_LW = FXCollections.observableArrayList();

    void load_widal_list(String valuer) {
        try {
            if (valuer.contentEquals("*")) {
                sql = "select * from livewell.lab_widal order by record_time";
            } else {
                sql = "select * from livewell.lab_widal where on_date = '" + valuer + "' or pid = '" + valuer + "' order by record_time";
            }
            conn = conclass.livewell_localhost();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tableView_liver.getItems().clear();
            while (rs.next()) {
                lW = new lab_widal(
                        rs.getString("pid"), rs.getString("sal_typhi_O"),
                        rs.getString("sal_typhi_H"),
                        rs.getString("RHST"), rs.getString("on_date"), rs.getTimestamp("record_time")
                );
                observable_LW.add(lW);
            }
            tableView_liver.setItems(observable_LW);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void do_clear_all(ActionEvent event) {

    }

    @FXML
    private void do_close_portal(ActionEvent event) throws IOException {
//        haemaStage.close();
//        open_lab_now.myStage.setIconified(false);
    }

    @FXML
    private void do_delete(ActionEvent event) {
    }

    @FXML
    private void haema_save(ActionEvent event) {
        Laboratory_Services ls = new Laboratory_Services(
                pid, txt_blood_grouping.getText(),
                txt_rheuses.getText().trim(), txt_HB.getText().trim(),
                txt_HEPATITIS_B.getText().trim(), txt_SICKLING_SCREENING.getText().trim(),
                txt_BLOOD_FILM.getText().trim(), txt_HB_ELECTROPHORESIS.getText().trim(),
                txt_G6PD.getText().trim(), txt_VDRL.getText().trim(),
                txt_PYLORI.getText().trim(), txt_FASTING_BLOOD_SUGAR.getText().trim(),
                txt_RANDOM_BLOOD_SUGAR.getText().trim(), txt_WBC.getText().trim(),
                txt_ESR.getText().trim(), txt_CHOLESTEROL.getText().trim(),
                semanticsClass.returneDate()
        );
        Service<Boolean> service = ls.Haematology_add();
        service.setOnCancelled(e -> {
            semanticsClass.set_notification("Error", service.getMessage());
            service.cancel();
        });

        service.setOnRunning(e -> {

        });

        service.setOnSucceeded((event1) -> {
            semanticsClass.set_notification("Success", service.getMessage());
            load_haematologyList(semanticsClass.returneDate());
        });

        service.start();
    }

    @FXML
    private void haema_update(ActionEvent event) {
        Laboratory_Services rss = new Laboratory_Services(
                pid, txt_blood_grouping.getText(),
                txt_rheuses.getText().trim(), txt_HB.getText().trim(),
                txt_HEPATITIS_B.getText().trim(), txt_SICKLING_SCREENING.getText().trim(),
                txt_BLOOD_FILM.getText().trim(), txt_HB_ELECTROPHORESIS.getText().trim(),
                txt_G6PD.getText().trim(), txt_VDRL.getText().trim(),
                txt_PYLORI.getText().trim(), txt_FASTING_BLOOD_SUGAR.getText().trim(),
                txt_RANDOM_BLOOD_SUGAR.getText().trim(), txt_WBC.getText().trim(),
                txt_ESR.getText().trim(), txt_CHOLESTEROL.getText().trim(),
                semanticsClass.returneDate()
        );

        Service<Boolean> update_service = rss.Haematology_update();
        update_service.setOnCancelled(e -> {
            semanticsClass.set_notification("Error", update_service.getMessage());
            update_service.cancel();
        });

        update_service.setOnRunning(e -> {
            System.out.println(pid);
        });

        update_service.setOnSucceeded((e) -> {
            semanticsClass.set_notification("Success", update_service.getMessage());
            load_haematologyList(semanticsClass.returneDate());
        });

        update_service.start();
    }

    @FXML
    private void widal_save(ActionEvent event) {
        Laboratory_Services lsW = new Laboratory_Services();

        Service<Boolean> Wservice = lsW.new widal().widal_add(new lab_widal(pid, txt_salmonella_typhi_O.getText(),
                txt_salmonella_typhi_H.getText(),
                txt_rapid_hiv_sport_test.getText(), semanticsClass.returneDate(), semanticsClass.nowStamp(new Date())));

        Wservice.setOnCancelled(e -> {
            semanticsClass.set_notification("Error", Wservice.getMessage());
            Wservice.cancel();
        });

        Wservice.setOnRunning(e -> {

        });

        Wservice.setOnSucceeded((event1) -> {
            semanticsClass.set_notification("Success", "Widal reaction results, done taken");
            Haematology_testController.this.load_widal_list(semanticsClass.returneDate());
        });

        Wservice.start();
    }

    @FXML
    private void update_widal(ActionEvent event) {
        Laboratory_Services myLabWidal_Service = new Laboratory_Services();
        lab_widal _wid = new lab_widal(pid, txt_salmonella_typhi_O.getText(), txt_salmonella_typhi_H.getText(),
                txt_rapid_hiv_sport_test.getText(), semanticsClass.returneDate(),
                semanticsClass.nowStamp(new Date()));
        Service<Boolean> LabWidal_service = myLabWidal_Service.new widal().widal_update(_wid);
        LabWidal_service.setOnCancelled((e) -> {
            semanticsClass.set_notification("Error", LabWidal_service.getMessage());
        });
        LabWidal_service.setOnSucceeded((e) -> {
            semanticsClass.set_notification("Success", LabWidal_service.getMessage());
            Haematology_testController.this.load_widal_list(semanticsClass.returneDate());
        });
        LabWidal_service.start();
    }

    @FXML
    private void haema_mob_search(ActionEvent event) {
        switch (cbo_HAEMA.getSelectionModel().getSelectedItem()) {
            case "All":
                load_haematologyList("*");
                break;
            case "Today":
                load_haematologyList(semanticsClass.returneDate());
                break;
            default:
                load_haematologyList(cbo_HAEMA.getSelectionModel().getSelectedItem());
                break;
        }
    }

    @FXML
    private void haema_make_search(ActionEvent event) {
        load_haematologyList(txt_HAEMA.getText());
    }

    @FXML
    private void widal_mob_search(ActionEvent event) {
        switch (cbo_WIDAL.getSelectionModel().getSelectedItem()) {
            case "All":
                Haematology_testController.this.load_widal_list("*");
                break;
            case "Today":
                Haematology_testController.this.load_widal_list(semanticsClass.returneDate());
                break;
            default:
                Haematology_testController.this.load_widal_list(cbo_WIDAL.getSelectionModel().getSelectedItem());
                break;
        }
    }

    @FXML
    private void widal_make_search(ActionEvent event) {
        load_widal_list(txt_WIDAL.getText());
    }

}
