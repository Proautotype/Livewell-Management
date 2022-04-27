/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Accountancy;

import com.connection.conclass;
import com.jfoenix.controls.JFXDatePicker;
import com.semantics.semanticsClass;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author G
 */
public class PaymentController implements Initializable {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String sql = "";

    @FXML
    private TableColumn<?, ?> col_pDate;
    @FXML
    private TableColumn<?, ?> col_deficit;
    @FXML
    private TableColumn<?, ?> col_payment;
    @FXML
    private TableColumn<?, ?> col_mop;
    @FXML
    private TableColumn<?, ?> col_balance;
    @FXML
    private Label lbl_id;
    @FXML
    private Label lbl_name;
    @FXML
    private Label lbl_contact;
    @FXML
    private ComboBox<String> cbo_pm;
    @FXML
    private TextField txt_deficit;
    @FXML
    private TextField txt_payment;
    @FXML
    private JFXDatePicker date_payment;
    @FXML
    private TextField txt_srch_client;
    @FXML
    private TableView<acc_client_payment> table_payment;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbo_pm.setItems(FXCollections.observableArrayList("Cash", "Cheque"));
        loadCustomers();
        table_settings();
    }

    private void table_settings() {  
        col_mop.setCellValueFactory(new PropertyValueFactory<>("mop"));
        col_pDate.setCellValueFactory(new PropertyValueFactory<>("pay_date"));
        col_balance.setCellValueFactory(new PropertyValueFactory<>("balance"));        
        col_deficit.setCellValueFactory(new PropertyValueFactory<>("deficit"));
        col_payment.setCellValueFactory(new PropertyValueFactory<>("payment"));
      
    }

    void loadCustomers() {
        try {
            sql = "select * from account_clientsection";
            conn = conclass.livewell_localhost();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            ObservableList<String> client_list = FXCollections.observableArrayList();
            while (rs.next()) {
                client_list.add(rs.getString("cid") + ":" + rs.getString("client_name"));
            }
            TextFields.bindAutoCompletion(txt_srch_client, client_list).addEventHandler(EventType.ROOT, e -> {
                if (!txt_srch_client.getText().isEmpty()) {
                    String[] n_id = txt_srch_client.getText().split("[:]");
                    sql = "select * from account_clientsection where cid = '" + n_id[0] + "' ";
                    try {
                        pst = conn.prepareStatement(sql);
                        rs = pst.executeQuery();
                        if (rs.next()) {
                            lbl_id.setText(rs.getString("cid"));
                            lbl_name.setText(rs.getString("client_name"));
                            lbl_contact.setText(rs.getString("client_contact"));

                            try {
//                                sql = "Select sum(stmt_balance) as tutBalance from livewell.account_clientsection_stmts "
//                                        + "WHERE stmt_balance < 0.0 and stmt_cid = '"+n_id[0]+"'  ";
                                sql = "Select sum(stmt_balance) as tutBalance from livewell.account_clientsection_stmts "
                                        + "where stmt_balance > 0.0 and stmt_cid = '" + n_id[0] + "' order by record_time";
                                pst = conn.prepareStatement(sql);
                                rs = pst.executeQuery();
                                if (rs.next()) {
                                    txt_deficit.setText(rs.getString("tutBalance"));
                                    load_payments(n_id[0]);
                                }
                            } catch (SQLException eee) {
                            }
                        }
                    } catch (SQLException ee) {
                        System.out.println(ee);
                    }

                }
            });

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    void load_payments(String query) {
       // Service<ObservableList<acc_client_payment>> load_payments = 
       // new acc_client_payment().load_payments(query);
        table_payment.getItems().clear();
        table_payment.setItems(new acc_client_payment().load_payments(query).getValue());
    }

    @FXML
    private void do_payment(ActionEvent event) {
        java.sql.Date dd = java.sql.Date.valueOf(date_payment.getValue());
        Double balance = Double.parseDouble(txt_deficit.getText()) - Double.parseDouble(txt_payment.getText());
        acc_client_payment acp = new acc_client_payment(
                txt_srch_client.getText().split("[:]")[0],
                Double.parseDouble(txt_deficit.getText()),
                cbo_pm.getValue(), Double.parseDouble(txt_payment.getText()), balance,
                dd, semanticsClass.nowStamp(dd));

        acp.do_payment(acp);
    }

}
