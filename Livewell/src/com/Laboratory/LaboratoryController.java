/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Laboratory;

import com.connection.conclass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import com.semantics.semanticsClass;
import com.services.Laboratory_Services;
import com.signIn.nSign_Controller;
import com.tables.doc_tests;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class LaboratoryController implements Initializable {

    @FXML
    private Font x1;
    @FXML
    private JFXSlider slider_sodium;
    @FXML
    private TextField txt_sodium;
    @FXML
    private JFXSlider slider_potassium;
    @FXML
    private TextField txt_potassium;
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
    private TextField txt_DBilirubin;
    @FXML
    private Font x11;
    @FXML
    private Color x4;
    @FXML
    private Font x3;
    String arr[] = {"", ""};
    @FXML
    private GridPane parent_KFT;
    @FXML
    private JFXSlider slider_chloride;
    @FXML
    private JFXToggleButton tog_kidneyFT;
    @FXML
    private GridPane parent_LFT;
    @FXML
    private JFXToggleButton tog_liverFT;
    @FXML
    private GridPane parent_urinalysis;
    @FXML
    private TextField txt_appearance;
    @FXML
    private Label txt_UROBILINOGEN;
    @FXML
    private TextField txt_colour;
    @FXML
    private TextField txt_leukocytes;
    @FXML
    private TextField txt_Co21;
    @FXML
    private TextField txt_BILIRUBIN;
    @FXML
    private TextField txt_BLOOD;
    @FXML
    private TextField txt_NITRITE;
    @FXML
    private TextField txt_PH;
    @FXML
    private TextField txt_SPECIFIC_GRAVITY;
    @FXML
    private TextField txt_PROTEIN;
    @FXML
    private TextField txt_GLUCOSE;
    @FXML
    private TextField txt_KETONES;
    @FXML
    private JFXToggleButton tog_urinalysis;
    @FXML
    private GridPane parent_microscopy;
    @FXML
    private TextField txt_PLUS_CELLS;
    @FXML
    private TextField txt_EPITH_CELLS;
    @FXML
    private TextField txt_Rbs;
    @FXML
    private TextField txt_YEAST_CELLS;
    @FXML
    private TextField txt_CASTS;
    @FXML
    private TextField txt_CRYSTALS;
    @FXML
    private TextField txt_T_VAGINALIS;
    @FXML
    private TextField txt_S_HAEMATOBIUM;
    @FXML
    private TextField txt_BACTERIA;
    @FXML
    private TextField txt_OTHERS;
    @FXML
    private Font x12;
    @FXML
    private JFXToggleButton tog_microscopy;
    @FXML
    private Font x13;
    @FXML
    private JFXToggleButton tog_stools;
    @FXML
    private VBox parent_stools;
    @FXML
    private TextArea txt_MACROSCOPY;
    @FXML
    private TextArea txt_MICROSCOPY;
    @FXML
    private TextArea txt_OCULT_BLOOD;
    @FXML
    private Font x14;
    @FXML
    private GridPane parent_haematology;
    @FXML
    private TextField txt_sodium12;
    @FXML
    private Label txt_HB;
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
    private JFXToggleButton tog_haematology;
    @FXML
    private TextField txt_ast;
    @FXML
    private Font x121;
    @FXML
    private JFXToggleButton tog_widalReaction;
    @FXML
    private VBox parent_widal;
    @FXML
    private TextArea txt_salmonella_typhi_O;
    @FXML
    private TextArea txt_salmonella_typhi_H;
    @FXML
    private TextArea txt_rapid_hiv_sport_test;
    @FXML
    private AnchorPane parent_grand;
    @FXML
    private VBox parent_VBox;
    @FXML
    private HBox parent_HBox;
    @FXML
    private Circle circle_image;
    @FXML
    private Label lbl_name;
    @FXML
    private Label lbl_level_of_education;
    @FXML
    private JFXProgressBar progressBar_load_incoming;
    private JFXListView<String> listView_imcoming_patients;

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = "";

    public String patient_id;
    public static String pid;
    @FXML
    private Label txt_HB1;
    @FXML
    private Label txt_HB11;
    @FXML
    private JFXButton btn_haematology_add;
    @FXML
    private TextField txt_blood_grouping;
    @FXML
    private TextField txt_rheuses;
    Laboratory_Services ls;
    boolean isUpdatable = false;
    @FXML
    private JFXButton btn_haematology_add1;
    @FXML
    private JFXButton btn_haematology;
    @FXML
    private JFXButton btn_urinalysis;
    @FXML
    private JFXButton btn_KFT;
    @FXML
    private JFXButton btn_LFT;

    public static Stage haemaStage;
    public static Stage uriStage;
    public static Stage kftStage;
    public static Stage lftStage;
    private boolean worker;
    @FXML
    private Label lbl_selected_patient;
    @FXML
    private TableView<doc_tests> table_incoming;
    @FXML
    private TableColumn<?, ?> col_pid;
    @FXML
    private TableColumn<?, ?> col_test;
    ObservableList<doc_tests> myList = FXCollections.observableArrayList();
    doc_tests dt = new doc_tests();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = conclass.livewell_localhost();
        settings();
        get_incoming();
        ls = new Laboratory_Services();
        pid =  nSign_Controller.username;
        col_pid.setCellValueFactory(new PropertyValueFactory<>("pid"));
        col_test.setCellValueFactory(new PropertyValueFactory<>("test_type"));
        income_bundle();

    }

    public void settings() {
        Timeline timer = new Timeline(
                new KeyFrame(Duration.seconds(2), (e) -> {
                    tog_kidneyFT.selectedProperty().bindBidirectional(parent_KFT.disableProperty());
                    tog_liverFT.selectedProperty().bindBidirectional(parent_LFT.disableProperty());
                    tog_urinalysis.selectedProperty().bindBidirectional(parent_urinalysis.disableProperty());
                    tog_microscopy.selectedProperty().bindBidirectional(parent_microscopy.disableProperty());
                    tog_stools.selectedProperty().bindBidirectional(parent_stools.disableProperty());
                    tog_haematology.selectedProperty().bindBidirectional(parent_haematology.disableProperty());
                    tog_widalReaction.selectedProperty().bindBidirectional(parent_widal.disableProperty());

                    //col_pid.setCellValueFactory(new PropertyValueFactory<>("Pid"));
                })
        );
        timer.play();

    }

    void get_incoming() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(20), ee -> {
            income_bundle();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    void income_bundle() {
        table_incoming.getItems().clear();
        try {
            sql = "select * from doc_pat_test_list where on_date = '" + semanticsClass.returneDate() + "' and status = 'ON' ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
//                System.out.println(rs.getString("on_date"));
                dt = new doc_tests(rs.getString("pid"), rs.getString("test_type"), rs.getString("on_date"), rs.getString("status"), rs.getTimestamp("record_time"));
                myList.add(dt);
            }
            table_incoming.setItems(myList);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void go_to_lab(ActionEvent event) {
        if (!pid.isEmpty()) {

        }
    }

    private void listView_incoming_MouseClicked(MouseEvent event) {

    }

    @FXML
    private void switchBtween(ActionEvent event) {

    }

    @FXML
    private void apply_haematology(ActionEvent event) {
        ls = new Laboratory_Services(
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
        Service<Boolean> service
                = ls.Haematology_add();
        service.setOnCancelled(e -> {
            semanticsClass.set_notification("Error", service.getMessage());
            service.cancel();
        });

        service.setOnRunning(e -> {

        });

        service.setOnSucceeded((event1) -> {
            semanticsClass.set_notification("Success", "Haematology results done taken");
        });

        service.start();

    }

    void doUpdate() {

        Laboratory_Services ss = new Laboratory_Services(
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

        Service<Boolean> update_service
                = ss.Haematology_update();
        update_service.setOnCancelled(e -> {
            semanticsClass.set_notification("Error", update_service.getMessage());
            update_service.cancel();
        });

        update_service.setOnRunning(e -> {
        });

        update_service.setOnSucceeded((event1) -> {
            semanticsClass.set_notification("Success", "Haematology results done updating");
        });

        update_service.start();

    }

    @FXML
    private void do_update_du(ActionEvent event) {
        doUpdate();
    }

    @FXML
    private void open_haematology(ActionEvent event) throws IOException {
        if (worker) {
            Parent root = FXMLLoader.load(getClass().getResource("/com/Laboratory/haematology_test.fxml"));
            Scene scene = new Scene(root);
            haemaStage = new Stage();
            haemaStage.setScene(scene);
            haemaStage.setFullScreen(true);
            worker = false;
            //open_lab_now.myStage.setIconified(true);
            haemaStage.setOnCloseRequest((ee) -> {
                //open_lab_now.myStage.setIconified(false);
                lbl_selected_patient.setText("none");

            });
            haemaStage.show();
        }
    }

    @FXML
    private void open_urinalysis(ActionEvent event) throws IOException {
        if (worker) {
            Parent root = FXMLLoader.load(getClass().getResource("/com/Laboratory/urine_RE.fxml"));
            Scene scene = new Scene(root);
            uriStage = new Stage();
            uriStage.setScene(scene);
            uriStage.setFullScreen(true);
            worker = false;
            //open_lab_now.myStage.setIconified(true);
            uriStage.setOnCloseRequest((ee) -> {
                //open_lab_now.myStage.setIconified(false);
                lbl_selected_patient.setText("none");
            });
            uriStage.show();
        }
    }

    @FXML
    private void open_KFT(ActionEvent event) throws IOException {
        if (worker) {
            Parent root = FXMLLoader.load(getClass().getResource("/com/Laboratory/kidneyFunctionTest.fxml"));
            Scene scene = new Scene(root);
            kftStage = new Stage();
            kftStage.setScene(scene);
//            kftStage.setFullScreen(true);    
            worker = false;
            //open_lab_now.myStage.setIconified(true);
            kftStage.setOnCloseRequest((ee) -> {
                //open_lab_now.myStage.setIconified(false);
                lbl_selected_patient.setText("none");
            });
            kftStage.show();
        }
    }

    @FXML
    private void open_LFT(ActionEvent event) throws IOException {
        if (worker) {
            Parent root = FXMLLoader.load(getClass().getResource("/com/Laboratory/liverFunctionTest.fxml"));
            Scene scene = new Scene(root);
            lftStage = new Stage();
            lftStage.setScene(scene);
//            lftStage.setFullScreen(true);    
            worker = false;
            //open_lab_now.myStage.setIconified(true);
            lftStage.setOnCloseRequest((ee) -> {
                //open_lab_now.myStage.setIconified(false);
                lbl_selected_patient.setText("none");
            });
            lftStage.show();
        }
    }

    @FXML
    private void do_markDone(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setContentText("Press OK to continue, \nThis process will return the selected patient to the consulting room or \nCANCEL to abort");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (!(pid.isEmpty())) {
                String value = table_incoming.getSelectionModel().getSelectedItem().getPid();
                System.out.println(value);
                String test_type = table_incoming.getSelectionModel().getSelectedItem().getTest_type();
                System.out.println(test_type);
                try {
                    Statement stmt = conn.createStatement();
                    sql = "update doctors_on_patient set status = 'LR' where opd_id = '" + value + "' and on_date = '" + semanticsClass.returneDate() + "' ";
                    stmt.addBatch(sql);
                    sql = "update doc_pat_test_list set status = 'OFF' where pid = '" + value + "' and on_date = '" + semanticsClass.returneDate() + "' and test_type  = '" + test_type + "'  ";
                    stmt.addBatch(sql);
                    stmt.executeBatch();
                    income_bundle();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
        }

    }

    @FXML
    private void table_incoming_mouseClicked(MouseEvent event) {
        if (!table_incoming.getSelectionModel().isEmpty()) {
            pid = table_incoming.getSelectionModel().getSelectedItem().getPid();
            lbl_selected_patient.setText(pid);
            Haematology_testController.pid = pid;
            if (!pid.isEmpty()) {
                btn_KFT.setDisable(false);
                btn_LFT.setDisable(false);
                btn_haematology.setDisable(false);
                btn_urinalysis.setDisable(false);
                btn_haematology_add.setDisable(false);
                worker = true;
            }
        }
    }
}
