/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Consults;

import static com.Consults.Doctors_NEWController.home_presc_id;
import com.connection.conclass;
import com.jfoenix.controls.JFXButton;
import com.semantics.semanticsClass;
import java.sql.Connection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.textfield.TextFields;
import com.tables.prescription_table;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class Update_prescriptionController implements Initializable {

    @FXML
    private MaskerPane masker;
    @FXML
    private Label txt_pid;
    @FXML
    private Label txt_name;
    @FXML
    private TextField txt_drug_code;
    @FXML
    private TextField txt_drugs;
    @FXML
    private TextField txt_drug_type;
    @FXML
    private TextField txt_presc_dosage;
    private TextField txt_presc_freque;
    private TextField txt_presc_duration;
    @FXML
    private TextField txt_presc_amount;
    @FXML
    private TableView<prescription_table> table_of_presc;
    @FXML
    private TableColumn col_drug_code;
    @FXML
    private TableColumn col_drug_type;
    @FXML
    private TableColumn<?, ?> col_drug;
    @FXML
    private TableColumn<?, ?> col_dosage;
    @FXML
    private TableColumn<?, ?> col_amount;
    @FXML
    private Label txt_prid;
    @FXML
    private Label txt_pass_id;

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = "";

    ObservableList<com.tables.prescription_table> rows_of_prescription = FXCollections.observableArrayList();

    private ObservableList<String> all_prescription;
    @FXML
    private TextField txt_all_drug_part;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prescription_table_settings();
        conn = conclass.livewell_localhost();
        Timeline_incoming_loader();
        new_load_presc(Doctors_NEWController._in_RP_id);
        load_drugs();

    }

    void prescription_table_settings() {
        col_drug_code.setCellValueFactory(new PropertyValueFactory<>("drug_cod"));
        col_drug.setCellValueFactory(new PropertyValueFactory<>("drug"));
        col_drug_type.setCellValueFactory(new PropertyValueFactory<>("drug_type"));
        col_dosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));  
        col_amount.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    void Timeline_incoming_loader() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), (e) -> {
            txt_pid.setText(Doctors_NEWController._in_RP_id);
            txt_name.setText(Doctors_NEWController._name);
            txt_prid.setText(Doctors_NEWController.home_presc_id);
            txt_pass_id.setText(Doctors_NEWController._doctors_id);
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }

    void load_drugs() {
        sql = "Select * from presc_drugs";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            all_prescription = FXCollections.observableArrayList();
            while (rs.next()) {
                all_prescription.add(rs.getString("code") + " ~ " + rs.getString("drug_name") + " ~ " + rs.getString("drug_type"));
            }

            TextFields.bindAutoCompletion(txt_all_drug_part, all_prescription).setPrefWidth(txt_all_drug_part.getPrefWidth());
            TextFields.bindAutoCompletion(txt_all_drug_part, all_prescription).addEventHandler(EventType.ROOT, e -> {
                //semanticsClass.set_notification("Drug code", txt_drug_code.getText());
                String[] drugcode = txt_all_drug_part.getText().split("[~]");
                txt_all_drug_part.setText("");
                txt_drug_code.setText(drugcode[0].trim());
                //the drugcode[1] is further splitted by [,] seperating drug from dosage
                String[] reSplit_drug = drugcode[1].trim().split("[,]");
                txt_drugs.setText(reSplit_drug[0].trim());

//                txt_presc_dosage.setText(reSplit_drug[1]);
//
                txt_drug_type.setText(drugcode[2].trim());
            });

           // TextFields.bindAutoCompletion(txt_presc_freque, FXCollections.observableArrayList("tds", "tid", "qid", "bd", "bid", "qid", "daily", "nocte"));

        } catch (SQLException e) {
            semanticsClass.set_notification("Error", e.getMessage());
        }
        TextFields.bindAutoCompletion(txt_drugs, all_prescription);
    }

    void new_load_presc(String _in_RP_id) {
        table_of_presc.getItems().clear();
        try {
            sql = "select doctors_on_patient.*,presc_listing.* from doctors_on_patient,presc_listing where doctors_on_patient.opd_id = '" + _in_RP_id + "'"
                    + " and doctors_on_patient.prescription = presc_listing.home_presc_id and doctors_on_patient.on_date = '" + semanticsClass.returneDate() + "' ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                rows_of_prescription.add(new prescription_table(rs.getString("drug_code"), rs.getString("drug_name"), rs.getString("drug_type"), rs.getString("dosage"),  rs.getString("price")));
            }
            table_of_presc.setItems(rows_of_prescription);
        } catch (SQLException e) {
            semanticsClass.set_notification("Error", e.getMessage());
        }
    }

    @FXML
    private void add_single_prescription(ActionEvent event) {
            rows_of_prescription.add(new prescription_table(txt_drug_code.getText(), txt_drugs.getText(), txt_drug_type.getText(), txt_presc_dosage.getText(), txt_presc_amount.getText()));
            table_of_presc.setItems(rows_of_prescription);
            // System.out.println( "Drug: " + row_in.getDrug() + " Dosage: " + row_in.getDosage() + " Frequency: " + row_in.getFreq() + " Duration: " + row_in.getDuration() + " Amount: " + row_in.getAmount());
            txt_presc_dosage.setText("");
            txt_drugs.setText("");
            txt_presc_freque.setText("");
            txt_presc_duration.setText("");
            txt_presc_amount.setText("");
    }

    @FXML
    private void remove_single_prescription(ActionEvent event) {
        int i = table_of_presc.getSelectionModel().getSelectedIndex();
        table_of_presc.getItems().remove(i);
    }

    void deletelist(String home_id) {
        try {
            sql = "delete from presc_listing where home_presc_id = '" + home_id + "' ";
            pst = conn.prepareStatement(sql);
            pst.execute();
        } catch (Exception e) {
            semanticsClass.set_notification("Error", e.getMessage());
        }
    }

    public String pres_row_query(prescription_table row) {
        sql = "insert into presc_listing (home_presc_id,drug_code,drug_name,drug_type,dosage,price,on_date)"
                + " values( '" + home_presc_id + "' ,'" + row.getDrug_cod() + "','" + row.getDrug() + "','" + row.getDrug_type() + "' , '" + row.getDosage() + "','" + row.getPrice() + "', '" + semanticsClass.returneDate() + "')";
        return sql;
    }

    public void batch_presc_entry(ObservableList<prescription_table> rows) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            for (prescription_table row : rows) {
                stmt.addBatch(pres_row_query(row));
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            semanticsClass.set_notification("Error", e.getMessage());
        }
    }

    @FXML
    private void done(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        boolean worker = false;

        alert.setHeaderText("You are about to change a classified information this process cannot be reversed,\nPress OK to continue but press CANCEL to stop \nEnter password");
        PasswordField pass = new PasswordField();
        pass.setPromptText("Enter password to continue");
        alert.getDialogPane().setContent(pass);
        Optional<ButtonType> choice = alert.showAndWait();
        if (choice.get() == ButtonType.OK) {
            sql = "select * from livewell.consulting_deptment where password = ?";
            try {
                pst = conn.prepareCall(sql);
                pst.setString(1, pass.getText());
                rs = pst.executeQuery();
                if (rs.next()) {
                    worker = true;
                } else {
                    worker = false;
                }
            } catch (SQLException ex) {
            }
        }
        if (worker == true) {
            deletelist(home_presc_id);
            batch_presc_entry(rows_of_prescription);
            semanticsClass.set_notification("Success", "Prescription updated successfully");
            new_load_presc(Doctors_NEWController._in_RP_id);
        } else {
            semanticsClass.set_notification("Error", "Password is not correct");
        }
    }

    @FXML
    private void reload(ActionEvent event) {
        new_load_presc(Doctors_NEWController._in_RP_id);
    }

    @FXML
    private void calculate_quantity(ActionEvent event) {
        if (!(txt_presc_dosage.getText().isEmpty() || txt_presc_freque.getText().isEmpty() || txt_presc_duration.getText().isEmpty())) {
            String freq_value = "";
            switch (txt_presc_freque.getText()) {
                case "tds":
                    freq_value = "3";
                    break;
                case "tid":
                    freq_value = "3";
                    break;
                case "qid":
                    freq_value = "4";
                    break;
                case "bd":
                    freq_value = "2";
                    break;
                case "bid":
                    freq_value = "2";
                    break;
                case "daily":
                    freq_value = "1";
                    break;
                case "nocte":
                    freq_value = "1";
                    break;
            }

            int valuer = semanticsClass.calculate_quantity(txt_presc_dosage.getText(), freq_value, txt_presc_duration.getText());
            txt_presc_amount.setText(String.valueOf(valuer));
        } else {
            semanticsClass.set_notification("Error", "Null Value, Check and Retry");
        }
    }

    @FXML
    private void table_click_activity(MouseEvent event) {
        if (event.isShiftDown()) {
            if (!table_of_presc.getSelectionModel().isEmpty()) {
                int i = table_of_presc.getSelectionModel().getSelectedIndex();
                if (table_of_presc.getSelectionModel().isSelected(i)) {
                    prescription_table pt = table_of_presc.getItems().get(i);
                    txt_drug_code.setText(pt.getDrug_cod());
                    txt_drugs.setText(pt.getDrug());
                    txt_drug_type.setText(pt.getDrug_type());
                    txt_presc_dosage.setText(pt.getDosage());                   
                    txt_presc_amount.setText(pt.getPrice());
                    table_of_presc.getItems().remove(i);
                }
            }
        } else {
            if (!table_of_presc.getSelectionModel().isEmpty()) {
                int i = table_of_presc.getSelectionModel().getSelectedIndex();
                if (table_of_presc.getSelectionModel().isSelected(i)) {
                    prescription_table pt = table_of_presc.getItems().get(i);
                    txt_drug_code.setText(pt.getDrug_cod());
                    txt_drugs.setText(pt.getDrug());
                    txt_drug_type.setText(pt.getDrug_type());
                    txt_presc_dosage.setText(pt.getDosage());                    
                    txt_presc_amount.setText(pt.getPrice());

                }
            }
        }

    }

}
