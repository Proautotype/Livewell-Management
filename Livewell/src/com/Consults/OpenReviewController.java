/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Consults;

import com.connection.conclass;
import com.jfoenix.controls.JFXListView;
import com.semantics.semanticsClass;
import com.tables.prescription_table;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class OpenReviewController implements Initializable {

    @FXML
    private Label lbl_temp;
    @FXML
    private Label lbl_weight;
    @FXML
    private Label lbl_systolis;
    @FXML
    private Label lbl_dystolis;
    @FXML
    private Label lbl_pulse;
    @FXML
    private Label lbl_sex11;
    @FXML
    private Label lbl_sex111;
    @FXML
    private Label lbl_systolis_td;
    @FXML
    private Label lbl_dystolis_td;
    @FXML
    private Label lbl_pulse_td;
    @FXML
    private Label lbl_temp_td;
    @FXML
    private Label lbl_weight_td;
    @FXML
    private JFXListView<Label> list_diagnostics;
    @FXML
    private TableView<prescription_table> table_of_presc;
    ObservableList<prescription_table> rows_of_prescription;
    @FXML
    private TableColumn<?, ?> col_drug_code;
    @FXML
    private TableColumn<?, ?> col_drug;
    @FXML
    private TableColumn<?, ?> col_drug_type;
    @FXML
    private TableColumn<?, ?> col_dosage;
    @FXML
    private TableColumn<?, ?> col_freg;
    @FXML
    private TableColumn<?, ?> col_duration;
    @FXML
    private TableColumn<?, ?> col_amount;
    @FXML
    private TextArea txt_complaint;
    @FXML
    private TextArea txt_history;
    @FXML
    private Label lbl_opd_id;
    @FXML
    private Label lbl_dob;
    @FXML
    private Label lbl_contact;

    String pid, on_Date;
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = "";
    @FXML
    private Label lbl_name;
    @FXML
    private Label lbl_sex;
    @FXML
    private Label lbl_age;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = conclass.livewell_localhost();
        prescription_table_settings();
        pid = Doctors_NEWController.review_pid;
        on_Date = Doctors_NEWController.review_onDate;

        System.out.println(pid + " " + on_Date);

        Timeline tm = new Timeline(new KeyFrame(Duration.seconds(3), ee -> {
            load_id(pid, on_Date);
            load_vitals(pid, on_Date);
            load_diagnostics(pid, on_Date);
            new_load_presc(pid, on_Date);
        }));

        tm.play();

    }

    @FXML
    private void list_diagnostic_fill(MouseEvent event) {
    }

    void load_id(String incoming_patient, String on_date) {
        sql = "select patient_info.* "
                + "from patient_info "
                + "where patient_info.Registration_no = '" + incoming_patient + "' ";

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                lbl_opd_id.setText(rs.getString("Registration_no"));
                System.out.println(rs.getString("Registration_no"));
                lbl_name.setText(rs.getString("surname") + " " + rs.getString("other_names"));
                lbl_sex.setText(rs.getString("sex"));
                lbl_age.setText(rs.getString("_age"));
                lbl_dob.setText(rs.getString("_dob"));
                lbl_contact.setText(rs.getString("patient_info.Telephone"));
            }

        } catch (Exception e) {
//            semanticsClass.set_notification("Error", e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {

            }
        }
    }

    void prescription_table_settings() {
        col_drug_code.setCellValueFactory(new PropertyValueFactory<>("drug_cod"));
        col_drug.setCellValueFactory(new PropertyValueFactory<>("drug"));
        col_drug_type.setCellValueFactory(new PropertyValueFactory<>("drug_type"));
        col_dosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
    }

    void load_vitals(String id, String on_date) {
        sql = "select patient_info.*,nurses_on_patient.* from patient_info,nurses_on_patient where "
                + "patient_info.registration_no = nurses_on_patient.opd_id and nurses_on_patient.opd_id = '" + id + "' "
                + "and nurses_on_patient.on_date = '" + on_date + "' ";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                lbl_weight.setText(rs.getString("nurses_on_patient.weight"));
                lbl_temp.setText(rs.getString("nurses_on_patient.temperature"));
                lbl_systolis.setText(rs.getString("nurses_on_patient.systolis"));
                lbl_dystolis.setText(rs.getString("nurses_on_patient.dystolis"));
                lbl_pulse.setText(rs.getString("nurses_on_patient.pulse"));

            } else {

            }

        } catch (SQLException e) {
            semanticsClass.set_notification("Error", e.getMessage());
        }
    }

    void new_load_presc(String pid, String on_date) {
        rows_of_prescription = FXCollections.observableArrayList();
        try {
            sql = "select doctors_on_patient.*,presc_listing.* from doctors_on_patient,presc_listing where doctors_on_patient.opd_id = '" + pid + "' and doctors_on_patient.prescription = presc_listing.home_presc_id and doctors_on_patient.on_date = '" + on_date + "' ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
//            rows_of_prescription.clear();
            while (rs.next()) {
                rows_of_prescription.add(new prescription_table(rs.getString("drug_code"), rs.getString("drug_name"), rs.getString("drug_type"), rs.getString("dosage"), rs.getString("price")));

                txt_complaint.setText(rs.getString("doctors_on_patient.complaint"));
                txt_history.setText(rs.getString("doctors_on_patient.history"));
            }
            table_of_presc.setItems(rows_of_prescription);
        } catch (SQLException e) {
            semanticsClass.set_notification("Error", e.getMessage());
        }
    }

    void load_diagnostics(String _in_RP_id, String on_date) {
        try {
            sql = "select doctors_on_patient.*,patient_diag_list.* from doctors_on_patient,patient_diag_list where doctors_on_patient.opd_id = '" + _in_RP_id + "' and doctors_on_patient.diagnostic = patient_diag_list.id and doctors_on_patient.on_date = '" + on_date + "' ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            list_diagnostics.getItems().clear();
            while (rs.next()) {
//                all_prescription.add(rs.getString("patient_diag_list.diagnostic"));
                list_diagnostics.getItems().add(new Label(rs.getString("patient_diag_list.diagnosis")));
                System.out.println(rs.getString("patient_diag_list.diagnosis"));
            }
//            list_diagnostics.getItems().setAll(all_prescription);
        } catch (Exception e) {
            semanticsClass.set_notification("Error", e.getMessage());
        }
    }
}
