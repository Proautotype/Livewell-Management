/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Consults;

import com.connection.conclass;
import com.jfoenix.controls.JFXButton;
import com.semantics.semanticsClass;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.controlsfx.control.ListSelectionView;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class Diagnostic_update_panelController implements Initializable {

    @FXML
    private VBox root;
    @FXML
    private TextArea txt_complaint;
    @FXML
    private TextArea history;
    @FXML
    private TextField txt_diagnosis;
    @FXML
    private JFXButton btn_add;
    @FXML
    private Label lbl_notification;
    @FXML
    private ListSelectionView<String> list_diagnosis;
    @FXML
    private JFXButton btn_done;

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = "";
    String opd_id = "";

    ObservableList<String> all_prescription;
    boolean worker = false;
    String diagnose_id = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        conn = conclass.livewell_localhost();
        opd_id = Doctors_NEWController._in_RP_id;

        list_diagnosis.setSourceHeader(new Label("DIAGNOSIS POOL"));
        list_diagnosis.setTargetHeader(new Label("DIAGNOSIS CONFIRMED" + "\n"));
        load_diagnos();
        check_diagnosis_avbl();
        load_diagnostics(opd_id);
        lbl_notification.setTextFill(Color.WHITE);
    }

    void load_diagnos() {
        sql = "Select * from diagnosis_nhis_codes";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            all_prescription = FXCollections.observableArrayList();
            while (rs.next()) {
                all_prescription.add(rs.getString("diagnosis"));
            }
        } catch (SQLException e) {
            semanticsClass.set_notification("Error", e.getMessage());
        }
        TextFields.bindAutoCompletion(txt_diagnosis, all_prescription);
    }

    boolean check_diagnosis_avbl() {
        try {
            sql = "select diagnostic from doctors_on_patient where opd_id = '" + opd_id + "' and on_date = '" + semanticsClass.returneDate() + "' ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                rs.getString("diagnostic");
                if (!rs.wasNull()) {
                    diagnose_id = rs.getString("diagnostic");
                    worker = true;
                    lbl_notification.setText(diagnose_id);
                }
            }
            rs.close();
            pst.close();
        } catch (SQLException ex) {
            semanticsClass.set_notification("Error", ex.getMessage());
        }
        return worker;
    }

    void load_diagnostics(String _in_RP_id) {
        try {
            sql = "select doctors_on_patient.*,patient_diag_list.* from "
                    + "doctors_on_patient,patient_diag_list where doctors_on_patient.opd_id = '" + _in_RP_id + "' "
                    + "and doctors_on_patient.diagnostic = patient_diag_list.id "
                    + "and doctors_on_patient.on_date = '" + semanticsClass.returneDate() + "' ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            all_prescription = FXCollections.observableArrayList();
            list_diagnosis.getSourceItems().clear();
            while (rs.next()) {
//                all_prescription.add(rs.getString("patient_diag_list.diagnostic"));
                list_diagnosis.getSourceItems().add(rs.getString("patient_diag_list.diagnosis"));

            }
//            list_diagnostics.getItems().setAll(all_prescription);
        } catch (SQLException e) {
            semanticsClass.set_notification("Error", e.getMessage());
        }
    }

    @FXML
    private void add_to_diag_list(ActionEvent event) {
        list_diagnosis.getSourceItems().add(txt_diagnosis.getText());
    }
//the previous prescription is held in a variable but deleted here
    void deletelist(String home_id) {
        try {
            sql = "delete from patient_diag_list where id = '" + home_id + "' ";
            pst = conn.prepareStatement(sql);
            pst.execute();
        } catch (SQLException e) {
            semanticsClass.setNotificator(lbl_notification, e.getMessage());
        }
    }

    String Single_PL_insertion(String drug_name) {
        sql = "insert into patient_diag_list (id, diagnosis) values ('" + diagnose_id + "','" + drug_name + "' )";
        return sql;
    }
    //inserts list of prescription by each item in the listView component

    void batching_diagnose(ListSelectionView<String> lister) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            for (String diagnos : lister.getTargetItems()) {
                statement.addBatch(Single_PL_insertion(diagnos));
            }
            statement.executeBatch();
            semanticsClass.setNotificator(lbl_notification, "EDITED SUCCESSFULLY");
        } catch (SQLException e) {
            semanticsClass.setNotificator(lbl_notification, e.getMessage());
            System.out.println(e);
        } finally {
            try {
                statement.clearBatch();
                statement.close();
            } catch (SQLException e) {
            }
        }
    }

    @FXML
    private void attach_diagnostic(ActionEvent event) {
        deletelist(diagnose_id);
        batching_diagnose(list_diagnosis);
    }

}
