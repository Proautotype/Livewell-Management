/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Accountancy;

import com.connection.conclass;
import com.jfoenix.controls.JFXRadioButton;
import com.semantics.semanticsClass;
import com.services.Acc_Client_Section_Services;
import com.services.Acc_Patient_Section_Services;
import com.services.report_services;
import com.tables.account_Patient_Section;
import com.tables.account_client_section_stmts;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class CreditorsNdebtorsController implements Initializable {
    
    @FXML
    private Color x2;
    @FXML
    private Font x1;
    @FXML
    private ComboBox<String> ps_cbo_search_one;
    @FXML
    private TextField ps_txt_search;
    @FXML
    private ComboBox<String> ps_cbo_search_two;
    @FXML
    private JFXRadioButton ps_radio_creditors;
    @FXML
    private ToggleGroup patient_sec_toggleGroup;
    @FXML
    private JFXRadioButton ps_radio_debtors;
    @FXML
    private JFXRadioButton ps_radio_both;
    @FXML
    private Pane ps_rangeSlider_pane;
    @FXML
    private Color x4;
    @FXML
    private Font x3;
    
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql, pid = "";
    @FXML
    private TableView<account_Patient_Section> ps_tableView;
    @FXML
    private TableColumn ps_col_date;
    @FXML
    private TableColumn ps_col_pid;
    @FXML
    private TableColumn ps_col_balance;
    
    Acc_Patient_Section_Services apss = new Acc_Patient_Section_Services();
    Acc_Client_Section_Services acss = new Acc_Client_Section_Services();
    
    @FXML
    private JFXRadioButton ps_radio_creditors1;
    
    HashMap reportHash;
    HashMap reportHash_stmt;
    @FXML
    private MaskerPane ps_masker;
    @FXML
    private Label ps_lbl_total;
    @FXML
    private Pane ps_rangeSlider_pane1;
    @FXML
    private JFXRadioButton cs_radio_creditors;
    @FXML
    private ToggleGroup client_sec_toggleGroup;
    @FXML
    private JFXRadioButton cs_radio_debtors;
    @FXML
    private JFXRadioButton cs_radio_both;
    @FXML
    private Label cs_lbl_total;
    @FXML
    private TableView<account_client_section_stmts> cs_tableView;
    @FXML
    private TableColumn<?, ?> cs_col_date;
    @FXML
    private TableColumn<?, ?> cs_col_pid;
    @FXML
    private TableColumn<?, ?> cs_col_balance;
    @FXML
    private ComboBox<String> cs_cbo_search_two;
    @FXML
    private ComboBox<String> cs_cbo_search_one;
    @FXML
    private MaskerPane cs_masker;
    @FXML
    private Label gt_debtors_patients;
    @FXML
    private Label gt_creditors_patients;
    @FXML
    private Label gt_debtors_clients;
    @FXML
    private Label gt_creditors_clients;
    @FXML
    private Label gt_debtors_total;
    @FXML
    private Label gt_creditors_total;
    @FXML
    private TextField cs_txt_search;
    @FXML
    private PieChart chart_debtors;
    @FXML
    private PieChart chart_creditors;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        conn = conclass.livewell_localhost();
        sql = "Select * from livewell.account_patientsection";// for initializing the printer
        reportHash = new HashMap<>();
        reportHash.put("report_type", "PATIENTS ACCOUNT BATCH LIST");
        reportHash_stmt = new HashMap<>();
        columnSettings();
        getIDsDate();
        ps_cbo_search_one.getSelectionModel().select(0);
        ps_cbo_search_two.getSelectionModel().select(0);
        sql = "Select * from livewell.account_patientsection";// for initializing the printer

        mySummations();
        cs_cbo_search_one.setValue(semanticsClass.returneDate());
        cs_cbo_search_two.setValue(semanticsClass.returneDate());
        
    }
    
    void columnSettings() {
        ps_cbo_search_one.setItems(FXCollections.observableArrayList(semanticsClass.returneDate()));
        ps_cbo_search_two.setItems(FXCollections.observableArrayList(semanticsClass.returneDate()));
        
        ps_col_pid.setCellValueFactory(new PropertyValueFactory<>("pid"));
        ps_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        ps_col_balance.setCellValueFactory(new PropertyValueFactory<>("lbl_balance"));
        
        cs_col_pid.setCellValueFactory(new PropertyValueFactory<>("stmt_cid"));
        cs_col_date.setCellValueFactory(new PropertyValueFactory<>("on_date"));
        cs_col_balance.setCellValueFactory(new PropertyValueFactory<>("stmt_balance"));
    }
    
    @FXML
    private void ps_mob_search(ActionEvent event) {
        ps_load_Creditors_Debtors(ps_cbo_search_one.getValue(), ps_cbo_search_one.getValue());
    }
    
    void loadSimpleBalance(String valuer) {
        Service<ObservableList<account_Patient_Section>> service = apss.new miniPSservices().load_patientSection_history(valuer);
        service.setOnSucceeded((event) -> {
            ps_tableView.setItems(service.getValue());
        });
        service.start();
    }
    
    void getIDsDate() {
        sql = "select Distinct pid from account_patientsection order by record_time asc";
        ObservableList<String> on_dateItems = FXCollections.observableArrayList();
        ObservableList<String> id_items = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                id_items.add(rs.getString("pid"));
            }
            TextFields.bindAutoCompletion(ps_txt_search, id_items);
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        sql = "select Distinct on_date from account_patientsection order by record_time asc";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                on_dateItems.add(rs.getString("on_date"));
            }
            ps_cbo_search_one.getItems().addAll(on_dateItems);
            ps_cbo_search_two.getItems().addAll(on_dateItems);
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        sql = "select Distinct stmt_cid from account_clientsection_stmts order by record_time asc";
        id_items.clear();
        on_dateItems.clear();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                id_items.add(rs.getString("stmt_cid"));
            }
            TextFields.bindAutoCompletion(cs_txt_search, id_items);
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        sql = "select Distinct on_date from account_clientsection_stmts order by record_time asc";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                on_dateItems.add(rs.getString("on_date"));
            }
            cs_cbo_search_one.getItems().addAll(on_dateItems);
            cs_cbo_search_two.getItems().addAll(on_dateItems);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    Double getBalanceType(String table_name, String column, String sign) {
        double result = 0.0;
        try {
            sql = "select sum(" + column + ") as resulter from " + table_name + " where " + column + " " + sign + " 0.0";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                rs.getString("resulter");
                if (!rs.wasNull()) {
                    result = Double.parseDouble(rs.getString("resulter"));
                } else {
                    result = 0.0;
                }
                
            }
            return result;
        } catch (SQLException e) {
            System.out.println(e);
            return 0.0;
        }
        
    } //1234567890

    void mySummations() {
        conn = conclass.livewell_localhost();
        //get client creditors
        double client_creditors = getBalanceType("account_clientsection_stmts", "stmt_balance", ">");
        System.out.println(client_creditors);
        //get client_debtors
        double client_debtors_r = getBalanceType("account_clientsection_stmts", "stmt_balance", "<");
        System.out.println(client_debtors_r);
        //get patient creditors
        //sql = "Select sum(balance) as tutBalance from livewell.account_patientsection WHERE balance < 0.0";
        double patient_debtors = getBalanceType("account_patientsection", "balance", ">");
        System.out.println(patient_debtors);
        //get patient debtors
        double patient_creditors = getBalanceType("account_patientsection", "balance", "<");
        System.out.println(patient_creditors);
        
        double total_creditors = client_creditors + patient_creditors;
        double total_debtors = client_debtors_r + patient_debtors;
//        
        gt_creditors_clients.setText(String.valueOf(client_creditors));
        gt_creditors_patients.setText(String.valueOf(patient_creditors));
        
        gt_debtors_patients.setText(String.valueOf(patient_debtors));
        gt_debtors_clients.setText(String.valueOf(patient_creditors));
        
        gt_creditors_total.setText(String.valueOf(total_creditors));
        gt_debtors_total.setText(String.valueOf(total_debtors));
        
        ObservableList<PieChart.Data> creditors_chart_data = FXCollections.observableArrayList();
        ObservableList<PieChart.Data> debtors_chart_data = FXCollections.observableArrayList();
        creditors_chart_data = FXCollections.observableArrayList(
                new PieChart.Data("CS", client_creditors),//patient creditors share
                new PieChart.Data("PS", patient_creditors)//client creditors share                
        );
        chart_creditors.setData(creditors_chart_data);
        
        debtors_chart_data = FXCollections.observableArrayList(
                new PieChart.Data("CS", patient_debtors),//patient creditors share
                new PieChart.Data("PS", patient_creditors)//client creditors share                
        );
        chart_debtors.setData(debtors_chart_data);
    }
    
    @FXML
    private void make_search(ActionEvent event) {
        loadSimpleBalance(ps_txt_search.getText());
    }
    
    @FXML
    private void ps_print_outCome(ActionEvent event) {
        Service<Boolean> service = new report_services().load_ps_report(sql, conn, reportHash);
        service.setOnRunning(e -> {
            ps_masker.visibleProperty().bind(service.runningProperty());
            ps_masker.textProperty().bind(service.messageProperty());
        });
        service.start();
    }
    
    @FXML
    private void ps_export_spreadSheet(ActionEvent event) {
    }
    
    void ps_load_Creditors_Debtors(String valuer, String valuer2) {
        Service<ObservableList<account_Patient_Section>> service = apss.new miniPSservices().load_myDebtorsCreditors(valuer, valuer2);
        service.setOnSucceeded((event) -> {
            ps_tableView.setItems(service.getValue());
        });
        service.start();
    }
    
    @FXML
    private void creditors_Only(ActionEvent event) {
        ps_load_Creditors_Debtors("<", "");
        
        try {
            sql = "Select sum(balance) as tutBalance from livewell.account_patientsection WHERE balance < 0.0";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                ps_lbl_total.setText(rs.getString("tutBalance"));
                ps_lbl_total.setStyle("-fx-background-color:tomato");
            }
        } catch (Exception e) {
        }
        reportHash.put("report_type", "PATIENTS ACCOUNT CREDITORS LIST");
        sql = "Select * from livewell.account_patientsection WHERE balance < 0.0";
    }
    
    @FXML
    private void Debtors_Only(ActionEvent event) {
        ps_load_Creditors_Debtors(">", "");
        try {
            sql = "Select sum(balance) as tutBalance from livewell.account_patientsection WHERE balance > 0.0";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                ps_lbl_total.setText(rs.getString("tutBalance"));
                ps_lbl_total.setStyle("-fx-background-color:lightgreen");
            }
        } catch (Exception e) {
        }
        sql = "Select * from livewell.account_patientsection WHERE balance > 0.0";
        reportHash.put("report_type", "PATIENTS ACCOUNT DEBTORS LIST");
    }
    
    @FXML
    private void Settled_Only(ActionEvent event) {
        ps_load_Creditors_Debtors("=", "");
        try {
            sql = "Select sum(balance) as tutBalance from livewell.account_patientsection WHERE balance = 0.0";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                ps_lbl_total.setText(rs.getString("tutBalance"));
                ps_lbl_total.setStyle("-fx-background-color:white");
                
            }
        } catch (Exception e) {
        }
        sql = "Select * from livewell.account_patientsection WHERE balance = 0.0";
        reportHash.put("report_type", "PATIENTS ACCOUNT SETTLED LIST");
    }
    
    @FXML
    private void All_Only(ActionEvent event) {
        loadSimpleBalance("*");
        try {
            sql = "Select sum(balance) as tutBalance from livewell.account_patientsection ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                double balance = Double.parseDouble(rs.getString("tutBalance"));
                ps_lbl_total.setStyle("-fx-background-color:white");
                if (balance > 0.0) {
                    ps_lbl_total.setText(String.valueOf(balance));
                    ps_lbl_total.setStyle("-fx-background-color:lightgreen;");
                } else if (balance == 0.0) {
                    ps_lbl_total.setText(String.valueOf(balance));
                    ps_lbl_total.setStyle("-fx-background-color:lightblue;");
                } else if (balance < 0.0) {
                    ps_lbl_total.setText(String.valueOf(balance));
                    ps_lbl_total.setStyle("-fx-background-color:tomato;");
                }
            }
        } catch (Exception e) {
        }
        sql = "Select * from livewell.account_patientsection";
        reportHash.put("report_type", "PATIENTS ACCOUNT BATCH LIST");
    }
    
    void cs_load_Creditors_Debtors(String valuer, String valuer2) {
        Service<ObservableList<account_client_section_stmts>> service = acss.new client_stmts().load_acss(valuer, valuer2);
        service.setOnSucceeded((event) -> {
            cs_tableView.setItems(service.getValue());
            System.out.println(valuer);
        });
        service.start();
    }
    
    void cs_load_Creditors_Debtors_byID(String valuer) {
        Service<ObservableList<account_client_section_stmts>> service = acss.new client_stmts().load_acss_byID(valuer);
        service.setOnSucceeded((event) -> {
            cs_tableView.setItems(service.getValue());
            System.out.println(valuer);
        });
        service.start();
    }
    
    String stmt_sql = "";
    
    @FXML
    private void cs_print_outCome(ActionEvent event) {
        Service<Boolean> service = new report_services().load_cs_report(stmt_sql, conn, reportHash_stmt);
        service.setOnRunning(e -> {
            cs_masker.visibleProperty().bind(service.runningProperty());
            cs_masker.textProperty().bind(service.messageProperty());
        });
        service.start();
    }
    
    @FXML
    private void cs_mob_search(ActionEvent event) {
        cs_load_Creditors_Debtors(cs_cbo_search_one.getValue(), cs_cbo_search_two.getValue());
        stmt_sql = "select * from livewell.account_clientsection_stmts where on_date between '" + cs_cbo_search_one.getValue()
                + "' and '" + cs_cbo_search_two.getValue()
                + "'  order by record_time";
        reportHash_stmt.put("report_type", "CLIENTS ACCOUNT: BETWEEN " + cs_cbo_search_one.getValue().toUpperCase() + " AND ".toUpperCase() + cs_cbo_search_two.getValue().toUpperCase());
    }
    
    @FXML
    private void client_section_make_search(ActionEvent event) {
        cs_load_Creditors_Debtors_byID(cs_txt_search.getText());
    }
    
    @FXML
    private void cs_creditors_Only(ActionEvent event) {
        cs_load_Creditors_Debtors(">", "");
        reportHash_stmt.put("report_type", "CLIENTS ACCOUNT: CREDITORS LIST");
        stmt_sql = "select * from livewell.account_clientsection_stmts where stmt_balance > 0.0 order by record_time";
        
        try {
            sql = "Select sum(stmt_balance) as tutBalance from livewell.account_clientsection_stmts WHERE stmt_balance > 0.0";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                cs_lbl_total.setText(rs.getString("tutBalance"));
                cs_lbl_total.setStyle("-fx-background-color:white");
                
            }
        } catch (Exception e) {
        }
    }
    
    @FXML
    private void cs_Debtors_Only(ActionEvent event) {
        cs_load_Creditors_Debtors("<", "");
        reportHash_stmt.put("report_type", "CLIENTS ACCOUNT: DEBTORS LIST");
        stmt_sql = "select * from livewell.account_clientsection_stmts where stmt_balance < 0.0 order by record_time";
        try {
            sql = "Select sum(stmt_balance) as tutBalance from livewell.account_clientsection_stmts WHERE stmt_balance < 0.0";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                cs_lbl_total.setText(rs.getString("tutBalance"));
                cs_lbl_total.setStyle("-fx-background-color:white");
                
            }
        } catch (Exception e) {
        }
    }
    
    @FXML
    private void cs_All_Only(ActionEvent event) {
        cs_load_Creditors_Debtors("*", "");
        reportHash_stmt.put("report_type", "CLIENTS ACCOUNT: BATCH LIST");
        stmt_sql = "select * from livewell.account_clientsection_stmts order by record_time";
        try {
            sql = "Select sum(stmt_balance) as tutBalance from livewell.account_clientsection_stmts ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                cs_lbl_total.setText(rs.getString("tutBalance"));
                cs_lbl_total.setStyle("-fx-background-color:white");
                
            }
        } catch (Exception e) {
        }
        
    }
    
    @FXML
    private void cs_Settled_Only(ActionEvent event) {
        cs_load_Creditors_Debtors("=", "");
        reportHash_stmt.put("report_type", "CLIENTS ACCOUNT: SETTLED LIST");
        stmt_sql = "select * from livewell.account_clientsection_stmts where stmt_balance = 0.0 order by record_time";
        try {
            sql = "Select sum(stmt_balance) as tutBalance from livewell.account_clientsection_stmts WHERE stmt_balance = 0.0";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                cs_lbl_total.setText(rs.getString("tutBalance"));
                cs_lbl_total.setStyle("-fx-background-color:white");
                
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
}
