/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Accountancy;

import AES.payment_id_generate;
import com.connection.conclass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.semantics.semanticsClass;
import static com.semantics.semanticsClass.SySoutException;
import com.services.Acc_Patient_Section_Services;
import com.tables.account_Patient_Section;
import com.tables.pharm_drug_costing;
import com.tables.pharmacy_table;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class Patient_SectionController implements Initializable {

    @FXML
    private JFXListView<String> listView_incoming;
    @FXML
    private Label lbl_id;
    @FXML
    private Label lbl_name;
    @FXML
    private Label lbl_isInsured;
    @FXML
    private Label lbl_toPay;
    @FXML
    private TextField txt_pay;
    @FXML
    private TextField txt_balance;
    @FXML
    private TableView<pharm_drug_costing> table_costing;
    @FXML
    private TableColumn<?, ?> col_drug_code;
    @FXML
    private TableColumn<?, ?> col_costing_quantity;
    @FXML
    private TableColumn<?, ?> col_price;
    @FXML
    private TableColumn<?, ?> col_costing_total;
    @FXML
    private TableView<account_Patient_Section> tableView_today_history;
    @FXML
    private TableColumn<?, ?> col_history_pid;
    @FXML
    private TableColumn<?, ?> col_history_date;
    @FXML
    private TableColumn<?, ?> col_history_particulas;
    @FXML
    private TableColumn<?, ?> col_history_cost;
    @FXML
    private TableColumn<?, ?> col_history_amt_paid;
    @FXML
    private TableColumn<?, ?> col_history_balance;

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql, pid = "";
    String payable = "";
    @FXML
    private LineChart chart_line;
    Acc_Patient_Section_Services apsServices = new Acc_Patient_Section_Services();

    double toPay, debt, balance = 0.0;
    @FXML
    private ComboBox<String> cbo_search;
    @FXML
    private TextField txt_search;
    @FXML
    private Button btn_search;
    @FXML
    private JFXButton btn_attach;
    @FXML
    private ComboBox<String> cbo_chart;
    @FXML
    private Label lbl_chart_cost;
    @FXML
    private Label lbl_chart_balance;
    @FXML
    private Label lbl_chart_amtPaid;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = conclass.livewell_localhost();
        incoming();
        items_table_settings();
        semanticsClass.numbersOnly(txt_pay);
        load_history(semanticsClass.returneDate());
        load_stools_ids();
        chart_loader(semanticsClass.returneDate());
        semanticsClass.listenPositivity(txt_balance, lbl_toPay);//listenPositivity
//        semanticsClass.listenPositivity(txt_pay,);//listenPositivity
    }

    void items_table_settings() {
        //        
        cbo_search.setItems(FXCollections.observableArrayList("All", "Today"));
        cbo_chart.setItems(FXCollections.observableArrayList("All", "Today"));
        col_drug_code.setCellValueFactory(new PropertyValueFactory<>("drug_code"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("drug_price"));
        col_costing_total.setCellValueFactory(new PropertyValueFactory<>("costing_total"));

        col_history_pid.setCellValueFactory(new PropertyValueFactory<>("pid"));
        col_history_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_history_particulas.setCellValueFactory(new PropertyValueFactory<>("particulas"));
        col_history_cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        col_history_amt_paid.setCellValueFactory(new PropertyValueFactory<>("amt_paid"));
        col_history_balance.setCellValueFactory(new PropertyValueFactory<>("lbl_balance"));

    }

    @FXML
    private void do_curl(ActionEvent event) {
        if (!(txt_pay.getText().isEmpty())) {//&& !(pid.isEmpty()) 
            toPay = Double.parseDouble(txt_pay.getText());//this is the entry, meaning the cash paid
            //the debt is set when the patient is clicked
            balance = (debt) - (toPay);
            System.out.println(balance);
            if (balance < 0.0) {
                txt_balance.setText(String.valueOf(balance));
                txt_balance.setStyle("-fx-background-color:tomato;");
            } else if (balance == 0.0) {
                txt_balance.setText(String.valueOf(balance));
                txt_balance.setStyle("-fx-background-color:lightblue;");
            } else if (balance > 0.0) {
                txt_balance.setText(String.valueOf(balance));
                txt_balance.setStyle("-fx-background-color:lightgreen;");
            }

        }
    }

    void incoming() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> {
            getIncoming();
        })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    void load_stools_ids() {
        cbo_search.setItems(FXCollections.observableArrayList("All", "Today"));
        sql = "select Distinct pid from account_patientsection order by record_time asc";
        ObservableList<String> on_dateItems = FXCollections.observableArrayList();
        ObservableList<String> id_items = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                id_items.add(rs.getString("pid"));
            }
            TextFields.bindAutoCompletion(txt_search, id_items);
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
            cbo_search.getItems().addAll(on_dateItems);
            cbo_chart.getItems().addAll(on_dateItems);
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    private void getIncoming() {
        ObservableList<String> incomingList = FXCollections.observableArrayList();
        Timeline t = new Timeline(new KeyFrame(Duration.seconds(10), e -> {
            try {
                sql = "select distinct pid from pharmacy_payment where on_date = '"
                        + semanticsClass.returneDate() + "' ";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                listView_incoming.getItems().clear();
                while (rs.next()) {
                    incomingList.add(rs.getString("pid"));
                }
                listView_incoming.setItems(incomingList); 
            } catch (SQLException ex) {
                Logger.getLogger(Patient_SectionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }));
        t.setCycleCount(Animation.INDEFINITE);
        t.play();
    }

    void load_id(String incoming_patient) {
        sql = "select patient_info.*,opd_history.*"
                + "from patient_info,opd_history "
                + "where patient_info.Registration_no = '" + incoming_patient + "' "
                + "and opd_history.last_date = '" + semanticsClass.returneDate() + "' "
                + "and patient_info.Registration_no = opd_history.patient_num";

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                lbl_id.setText(rs.getString("Registration_no"));
                lbl_name.setText(rs.getString("surname") + " " + rs.getString("other_names"));
                switch (rs.getString("opd_history.nhis_stat")) {
                    case "YES":
                        lbl_isInsured.setStyle("-fx-background-color:lightgreen");
                        lbl_isInsured.setText("YES");

                        break;
                    case "NO":
                        lbl_isInsured.setStyle("-fx-background-color:#ff9b9b");
                        lbl_isInsured.setText("NO");
                        break;
                }
                btn_attach.setDisable(false);
                txt_pay.setDisable(false);
                btn_attach.setText("Attach");
                loadItemPurchased(incoming_patient, semanticsClass.returneDate());
            }

        } catch (SQLException e) {
            semanticsClass.set_notification("Error", e.getMessage());
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {

            }
        }
    }

    void no_String_load_id(String incoming_patient, String on_date) {
        sql = "select patient_info.*,opd_history.*"
                + "from patient_info,opd_history "
                + "where patient_info.Registration_no = '" + incoming_patient + "' "
                + "and opd_history.last_date = '" + on_date + "' "
                + "and patient_info.Registration_no = opd_history.patient_num";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                lbl_id.setText(rs.getString("Registration_no"));
                lbl_name.setText(rs.getString("surname") + " " + rs.getString("other_names"));
                switch (rs.getString("opd_history.nhis_stat")) {
                    case "YES":
                        lbl_isInsured.setStyle("-fx-background-color:lightgreen");
                        lbl_isInsured.setText("YES");
                        break;
                    case "NO":
                        lbl_isInsured.setStyle("-fx-background-color:#ff9b9b");
                        lbl_isInsured.setText("NO");
                        break;
                }
                btn_attach.setDisable(false);
            }

        } catch (SQLException e) {
            semanticsClass.set_notification("Error", e.getMessage());
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {

            }
        }
    }

    @FXML
    private void listView_incoming_mouseClicked(MouseEvent event) {
        String valuer = semanticsClass.getSelectedStringFromJFXListView(listView_incoming);
        load_id(valuer);
    }

    ObservableList<pharm_drug_costing> observe_pdci = FXCollections.observableArrayList();
    pharm_drug_costing pdci = new pharm_drug_costing();

    private void loadItemPurchased(String pid, String on_date) {
        conn = conclass.livewell_localhost();
        table_costing.getItems().clear();
        sql = "select pharmacy_payment.* from pharmacy_payment where pid = ? and on_date = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, pid);
            pst.setString(2, on_date);
            rs = pst.executeQuery();
            while (rs.next()) {
                pdci = new pharm_drug_costing();
                pdci.setPay_id(rs.getString("pay_id"));//set payment id
                pdci.setPharmacist_id(rs.getString("pharmacist"));//set pharmacist id
                pdci.setPat_id(rs.getString("pid"));//set pharmacist id
                pdci.setDrug_code(rs.getString("drug_code"));//set 
//                pdci.setQuantity(rs.getString("drug_quantity"));
                pdci.setDrug_price(String.valueOf(rs.getString("drug_price")));
                pdci.setCosting_total(String.valueOf(rs.getString("line_total")));
                pdci.setOn_date(rs.getString("on_date"));
                observe_pdci.add(pdci);
            }
            table_costing.setItems(observe_pdci);
            // for calculating the grand total from the line total
            sql = "select sum(line_total) as total from pharmacy_payment where pid = ? and on_date = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, pid);
            pst.setString(2, semanticsClass.returneDate());
            rs = pst.executeQuery();
            if (rs.next()) {
                payable = rs.getString("total"); //the payable is simply the total cost of the client
                lbl_toPay.setText("GH₵ " + payable);
                txt_pay.setText(payable);
                debt = Double.parseDouble(payable) * 1;//this is the debt of the user
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    void addToFinanceRecord() {

    }

    @FXML
    private void do_attach(ActionEvent event) {
        //pid,on_date,particulas,cost,amt_paid,balance,record_time
        if (!btn_attach.getText().contentEquals("Update")) {
            if (!table_costing.getItems().isEmpty() || !(txt_balance.getText().isEmpty())) {// checking if not costing is empty
                account_Patient_Section aps = new account_Patient_Section();
                aps.setPid(pdci.getPat_id());//geting the client_id
                aps.setDate(pdci.getOn_date());// getting the dateOfEntry
                aps.setParticulas(pdci.getPay_id());// getting the particulas, in our case the payment id
                aps.setCost(payable);// the cost of items
                aps.setAmt_paid(String.valueOf(toPay)); //the amount paid by client
                Label lbl_balance = new Label();
//                aps.setLbl_balance(lbl_balance); being commented to remove such calculation since the deduction will be done explicitly within the sql query
                aps.setRecord_time(semanticsClass.nowStamp(new Date()));
                Service<Boolean> service = apsServices.addToHistory(aps);
                service.setOnCancelled(e -> {
                    semanticsClass.set_notification("save error".toUpperCase(), service.getMessage());
                });
                service.setOnSucceeded(e -> {
                    if (service.getValue() == true) {
                        semanticsClass.set_notification("Success", service.getMessage());
                        load_history(semanticsClass.returneDate());
                        chart_loader(semanticsClass.returneDate());
                        cbo_chart.setValue(semanticsClass.returneDate());
                        cbo_search.setValue(semanticsClass.returneDate());
                        clearRequires();
                    } else {
                        semanticsClass.set_notification("Error", "An error occured");
                    }
                });
                service.start();
            } else {
                semanticsClass.set_notification("Error ", "No record available or nothing is curled");
            }
        } else {
            account_Patient_Section aps = new account_Patient_Section();
            aps.setPid(table_costing.getItems().get(0).getPat_id());//geting the client_id
            aps.setDate(table_costing.getItems().get(0).getOn_date());// getting the dateOfEntry
            aps.setParticulas(table_costing.getItems().get(0).getPay_id());// getting the particulas, in our case the payment id
            aps.setCost(payable);// the cost of items
            aps.setAmt_paid(String.valueOf(toPay)); //the amount paid by client
            Label lbl_balance = new Label();
            lbl_balance.setText(String.valueOf(balance));
            aps.setLbl_balance(lbl_balance);
            System.out.println("\n" + aps.getDate());
            System.out.println(aps.getLbl_balance().getText());
            System.out.println(aps.getPid());
            System.out.println(aps.getParticulas());

            Service<Boolean> service = apsServices.UpdateHistory(aps);
            service.setOnCancelled(e -> {
                semanticsClass.set_notification("update Error", service.getMessage());
            });
            service.setOnSucceeded(e -> {
                if (service.getValue() == true) {
                    semanticsClass.set_notification("Success", service.getMessage());
                    load_history(aps.getDate());
                    load_history(aps.getDate());
                    chart_loader(aps.getDate());
                    cbo_chart.setValue(aps.getDate());
                    cbo_search.setValue(aps.getDate());
                } else {
                    semanticsClass.set_notification("Error", "An error occured");
                }
            });
            service.start();
        }
    }

    private void load_history(String valuer) {
        tableView_today_history.getItems().clear();
        ObservableList<account_Patient_Section> obs = FXCollections.observableArrayList();
        Service<ObservableList<account_Patient_Section>> listAps = apsServices.load_patientSection_history(valuer);
        listAps.setOnSucceeded(e -> {
            tableView_today_history.setItems(listAps.getValue());
        });
        listAps.setOnFailed(e -> {
            tableView_today_history.getItems().clear();
            listAps.restart();
        });
        listAps.start();

    }

    @FXML
    private void mob_search(ActionEvent event) {
        switch (cbo_search.getSelectionModel().getSelectedIndex()) {
            case 0:
                load_history("*");
                chart_loader("*");
                break;
            case 1:
                load_history(semanticsClass.returneDate());
                chart_loader(semanticsClass.returneDate());
                break;
            default:
                load_history(cbo_search.getValue());
                chart_loader(cbo_search.getValue());
                break;
        }

    }

    @FXML
    private void make_search(ActionEvent event) {
        load_history(txt_search.getText());
    }

    @FXML
    private void listView_History_mouseClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            if (!tableView_today_history.getSelectionModel().isEmpty()) {
                table_costing.getItems().clear();
                account_Patient_Section selectedItem = tableView_today_history.getSelectionModel().getSelectedItem();
                Service<ObservableList<pharm_drug_costing>> service
                        = apsServices.load_selectedHistory_listOfItems(selectedItem.getPid(), selectedItem.getDate(), selectedItem.getParticulas());
                service.setOnSucceeded(e -> {
                    table_costing.setItems(service.getValue());
                });
                service.start();
                payable = selectedItem.getLbl_balance().getText();
                txt_pay.setText(payable);
                lbl_toPay.setText("GH₵ " + payable);
                debt = Double.parseDouble(payable);
                if (!payable.isEmpty()) {
                    btn_attach.setText("Update");
                    System.out.println(selectedItem.getPid());
                    no_String_load_id(selectedItem.getPid(), selectedItem.getDate());
                    double value = Double.parseDouble(payable);
                    if (value == 0.0) {
                        txt_pay.setDisable(true);
                    } else {
                        txt_pay.setDisable(false);
                    }
                } else {
                    btn_attach.setText("Attach");
                }
            }
        }

    }

    @FXML
    private void load_chart(ActionEvent event) {
        switch (cbo_chart.getSelectionModel().getSelectedIndex()) {
            case 0:
                chart_loader("*");
                break;
            case 1:
                chart_loader(semanticsClass.returneDate());
                break;
            default:
                chart_loader(cbo_chart.getValue());
                ;
        }
    }
    double costs, balances, paids = 0.0;

    private void chart_loader(String valuer) {

        if (valuer.contentEquals("*")) {//cost,amt_paid,balance,record_time
            sql = "select sum(cost) as costs, sum(amt_paid) as paid, sum(balance) as balance from livewell.account_patientsection ";
        } else {
            sql = "select sum(cost) as costs, sum(amt_paid) as paid, sum(balance) as balance from livewell.account_patientsection "
                    + "where on_date = '" + valuer + "' ";
        }
        XYChart.Series headers = new XYChart.Series<>();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                chart_line.getData().clear();
                rs.getString("costs");
                if (!rs.wasNull()) {
                    costs = Double.parseDouble(rs.getString("costs"));
                } else {
                    costs = 0.0;
                }
                rs.getString("paid");
                if (!rs.wasNull()) {
                    paids = Double.parseDouble(rs.getString("paid"));
                } else {
                    paids = 0.0;
                }
                rs.getString("balance");
                if (!rs.wasNull()) {
                    balances = Double.parseDouble(rs.getString("balance"));
                } else {
                    balances = 0.0;
                }

                headers.getData().add(new XYChart.Data("Cost", costs));
                headers.getData().add(new XYChart.Data("Paid", paids));
                headers.getData().add(new XYChart.Data("Balance", balances));

                chart_line.getData().addAll(headers);
                lbl_chart_cost.setText(rs.getString("costs"));
                lbl_chart_amtPaid.setText(rs.getString("paid"));
                lbl_chart_balance.setText(rs.getString("balance"));

            }
        } catch (SQLException e) {
        }
    }

    private void clearRequires() {
        lbl_toPay.setText("Balance");
        lbl_id.setText("ID");
        lbl_name.setText("Name");
        lbl_isInsured.setText("Insured?");
        lbl_isInsured.setStyle("-fx-background-color:rgb(0, 179, 202)");
        table_costing.getItems().clear();
        toPay = 0.0;
        debt = 0.0;
        balance = 0.0;
    }
}
