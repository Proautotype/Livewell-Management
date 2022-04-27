/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Accountancy;

import static com.Accountancy.Accountancy_dashController.account_id;
import com.connection.conclass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.semantics.semanticsClass;
import com.services.Acc_Client_Section_Services;
import com.tables.account_Client_Section;
import com.tables.account_client_section_stmts;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class Client_sectionController implements Initializable {

    @FXML
    private JFXListView<String> listView_myClient;
    @FXML
    private MaskerPane stmt_masker;
    @FXML
    private JFXTextField txt_id;
    @FXML
    private JFXTextField txt_client_name;
    @FXML
    private JFXTextField txt_client_contact;
    @FXML
    private JFXTextField txt_client_addr;
    @FXML
    private JFXTextArea txt_client_desc;
    @FXML
    private ToggleGroup client_type;
    @FXML
    private JFXButton txt_apply;
    @FXML
    private AnchorPane client_statement_masker;
    @FXML
    private ToggleGroup stmt_radio_grup;
    Acc_Client_Section_Services acss = new Acc_Client_Section_Services();
    @FXML
    private JFXRadioButton rd_client_type_personal;
    @FXML
    private JFXRadioButton rd_client_type_company;
    @FXML
    private MaskerPane parent_masker;

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql, cid, client_type_str, stmt_item_type = "";

    @FXML
    private ComboBox<String> txt_stmt_product;
    @FXML
    private TextField txt_stmt_qty;
    @FXML
    private TextField txt_stmt_cost;
    @FXML
    private TextField txt_stmt_amtPaid;
    @FXML
    private TextField txt_stmt_balance;
    @FXML
    private ComboBox<String> cbo_stmt_search;
    @FXML
    private TextField txt_stmt_search;
    @FXML
    private TableColumn<?, ?> col_stmt_cid;
    @FXML
    private TableColumn<?, ?> col_stmt_date;
    @FXML
    private TableColumn<?, ?> col_stmt_item;
    @FXML
    private TableColumn<?, ?> col_stmt_item_type;
    @FXML
    private TableColumn<?, ?> col_stmt_qty;
    @FXML
    private TableColumn<?, ?> col_stmt_cost;
    @FXML
    private TableColumn<?, ?> col_stmt_amt_paid;
    @FXML
    private TableColumn<?, ?> col_stmt_balance;
    @FXML
    private TableView<account_client_section_stmts> tableView_stmts;
    @FXML
    private JFXRadioButton rd_stmt_rm;
    @FXML
    private JFXRadioButton rd_stmt_sfp;
    @FXML
    private JFXButton btn_stmt_add;
    @FXML
    private JFXButton btn_update;
    @FXML
    private TextField txt_pid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = conclass.livewell_localhost();
        client_type_str = "Personal";
        stmt_item_type = rd_stmt_rm.getText();
        tableSettings();
        rd_settings();
        load_clients();
        load_products();
        quatityToCost();
        general_stmt_loader(semanticsClass.returneDate());
        btn_stmt_add.setDisable(false);
        btn_update.setDisable(true);
    }
    ObservableList<String> pids = FXCollections.observableArrayList();
    ObservableList<String> ppnames = FXCollections.observableArrayList();

    void load_products() {
        try {
            products_mgmt pmp = new products_mgmt();
            Service<ObservableList<products_mgmt>> load = pmp.load("*");
            load.setOnSucceeded(e2 -> {
                for (products_mgmt pmpp : load.getValue()) {
                    pids.add(pmpp.getPid());
                    ppnames.add(pmpp.getPname());
                }
                TextFields.bindAutoCompletion(txt_pid, pids).addEventHandler(EventType.ROOT, txt_pid_act -> {
                    String pid = txt_pid.getText().trim();                   
                    try {
                        sql = "select * from account_pmp where pid = '" + pid + "' ";
                        pst = conn.prepareStatement(sql);
                        rs = pst.executeQuery();
                        if (rs.next()) {
                            txt_stmt_product.setValue(rs.getString("pname")); 
                        }                        
                    } catch (SQLException ee) {
                    }
                });
                txt_stmt_product.setItems(ppnames);
            });
            load.start();
        } catch (Exception e) {
        }
    }

    void quatityToCost() {
        txt_stmt_qty.setOnAction(e -> {
            String pid = txt_pid.getText().trim();
            Double uprice = 0.0;
            Double qty = 0.0;
            Double cost = 0.0;
            try {
                sql = "select * from account_pmp where pid = '" + pid + "' ";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                if (rs.next()) {
                    uprice = rs.getDouble("uprice");
                }
                if (uprice != 0.0) {
                    qty = Double.parseDouble(txt_stmt_qty.getText());
                    cost = uprice * qty;
                    txt_stmt_cost.setText(String.valueOf(cost));
                }
            } catch (SQLException ee) {
            }
        });

    }

    void rd_settings() {
        rd_client_type_company.selectedProperty().addListener((observable) -> {
            client_type_str = rd_client_type_company.getText();
            System.out.println(client_type_str);
        });
        rd_client_type_personal.selectedProperty().addListener((observable) -> {
            client_type_str = rd_client_type_personal.getText();
            System.out.println(client_type_str);
        });
        rd_stmt_rm.selectedProperty().addListener((observable) -> {
            stmt_item_type = rd_stmt_rm.getText();
            System.out.println(stmt_item_type);
        });
        rd_stmt_sfp.selectedProperty().addListener((observable) -> {
            stmt_item_type = rd_stmt_sfp.getText();
            System.out.println(stmt_item_type);
        });
    }

    void stmt_loaders() {
        ObservableList<String> search_items = FXCollections.observableArrayList();
        ObservableList<String> cbo_search_items = FXCollections.observableArrayList();
        cbo_stmt_search.getItems().clear();
        cbo_stmt_search.setItems(FXCollections.observableArrayList("All", "Today"));
        try {
            sql = "select Distinct stmt_item as item from account_clientsection_stmts";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                search_items.add(rs.getString("item"));
                System.out.println(rs.getString("item"));
            }
            sql = "select Distinct accountant_id as item from account_clientsection_stmts";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                search_items.add(rs.getString("item"));
                System.out.println(rs.getString("item"));
            }
            sql = "select Distinct on_date as item from account_clientsection_stmts";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                search_items.add(rs.getString("item"));
                cbo_search_items.add(rs.getString("item"));
                System.out.println(rs.getString("item"));
            }
            cbo_stmt_search.getItems().addAll(cbo_search_items);
            sql = "select Distinct stmt_cid as item from account_clientsection_stmts";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                search_items.add(rs.getString("item"));
                System.out.println(rs.getString("item"));
            }
            sql = "select Distinct stmt_item_type as item from account_clientsection_stmts";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                search_items.add(rs.getString("item"));
                System.out.println(rs.getString("item"));
            }
            TextFields.bindAutoCompletion(txt_stmt_search, search_items);
        } catch (SQLException e) {

        }

    }

    /*String accountant_id, String stmt_cid, String stmt_item, 
    String stmt_item_type, String stmt_qty, String stmt_cost, 
    String stmt_amt_paid, Label stmt_balance, String on_date, Timestamp rec_date*/
    void tableSettings() {
        col_stmt_cid.setCellValueFactory(new PropertyValueFactory<>("stmt_cid"));
        col_stmt_date.setCellValueFactory(new PropertyValueFactory<>("on_date"));
        col_stmt_item.setCellValueFactory(new PropertyValueFactory<>("stmt_item"));
        col_stmt_item_type.setCellValueFactory(new PropertyValueFactory<>("stmt_item_type"));
        col_stmt_qty.setCellValueFactory(new PropertyValueFactory<>("stmt_qty"));
        col_stmt_cost.setCellValueFactory(new PropertyValueFactory<>("stmt_cost"));
        col_stmt_amt_paid.setCellValueFactory(new PropertyValueFactory<>("stmt_amt_paid"));
        col_stmt_balance.setCellValueFactory(new PropertyValueFactory<>("stmt_balance"));

    }

    @FXML
    private void do_apply(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Click Ok to continue");
        alert.setTitle("Continue");
        Optional<ButtonType> results = alert.showAndWait();
        if (results.get() == ButtonType.OK) {
            Service<Boolean> service;
            account_Client_Section acs = new account_Client_Section();
            acs.setClient_type(client_type_str);
            acs.setCid(txt_id.getText().trim());
            acs.setClient_name(txt_client_name.getText());
            acs.setClient_contact(txt_client_contact.getText());
            acs.setClient_address(txt_client_addr.getText());
            acs.setClient_desc(txt_client_desc.getText());
            service = acss.add_service(acs);
            service.setOnRunning(e -> {
                parent_masker.visibleProperty().bind(service.runningProperty());
                parent_masker.textProperty().bind(service.messageProperty());
            });
            service.setOnSucceeded(e -> {
                load_clients();
            });
            service.start();
        }

    }

    void load_clients() {
        listView_myClient.getItems().removeAll(listView_myClient.getItems());
        Service<ObservableList<String>> load_all_clients = acss.load_all_clients();
        load_all_clients.setOnSucceeded(e -> {
            ObservableList<String> acsValin = load_all_clients.getValue();
            listView_myClient.setItems(acsValin);
        });
        load_all_clients.start();
    }

    @FXML
    private void myClient_mouse_clicked(MouseEvent event) {
        if (event.getClickCount() == 2) {//double click to load single client details
            if (!listView_myClient.getSelectionModel().isEmpty()) {
                String selectedItem = listView_myClient.getSelectionModel().getSelectedItem();
                fill_opens(selectedItem);
            }
        }
    }

    //fill
    void fill_opens(String cidd) {
        try {
            sql = "select * from account_clientsection where cid = '" + cidd + "' ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                String clientType = rs.getString("client_type");
                System.out.println(clientType);
                if (clientType.contentEquals(rd_client_type_personal.getText())) {
                    rd_client_type_personal.setSelected(true);
                } else {
                    rd_client_type_company.setSelected(true);
                }
                cid = rs.getString("cid");
                txt_id.setText(cid);
                txt_client_name.setText(rs.getString("client_name"));
                txt_client_contact.setText(rs.getString("client_contact"));
                txt_client_addr.setText(rs.getString("client_address"));
                txt_client_desc.setText(rs.getString("client_desc"));
                btn_update.setDisable(false);
            } else {
                btn_update.setDisable(true);
            }
        } catch (SQLException e) {
        }
    }

    @FXML
    private void do_new_client(ActionEvent event) {
        txt_apply.setText("Apply");
        txt_id.setText("");
        txt_client_name.setText("");
        txt_client_contact.setText("");
        txt_client_addr.setText("");
        txt_client_desc.setText("");
    }

    @FXML
    private void do_stmt_curl(ActionEvent event) {
        try {
            double stmt_cost = Double.parseDouble(txt_stmt_cost.getText());
            double pay = Double.parseDouble(txt_stmt_amtPaid.getText());
            if ((pay <= stmt_cost)) {
                double stmt_balance = stmt_cost - pay;
                txt_stmt_balance.setText(String.valueOf(stmt_balance));
                txt_stmt_amtPaid.setStyle("-fx-background-color:none");
                btn_stmt_add.setDisable(false);
            } else {
                txt_stmt_amtPaid.setStyle("-fx-background-color:tomato");
                btn_stmt_add.setDisable(true);
            }

        } catch (NumberFormatException e) {
        }

    }
    String svalue = "";

    private void stmt_mob_search(ActionEvent event) {
        String svalue = cbo_stmt_search.getSelectionModel().getSelectedItem();
        if (!svalue.isEmpty()) {
            switch (svalue) {
                case "All":
                    general_stmt_loader("*");
                    break;
                case "Today":
                    general_stmt_loader(semanticsClass.returneDate());
                    break;
                default:
                    general_stmt_loader(svalue);
                    break;
            }
        }
    }

    @FXML
    private void stmt_make_search(ActionEvent event) {
        general_stmt_loader(txt_stmt_search.getText());
    }

    void general_stmt_loader(String query) {
        Service<ObservableList<account_client_section_stmts>> service = acss.new client_stmts().load_acss(query);
        service.setOnRunning(e -> {
            tableView_stmts.setItems(service.getValue());
        });
        service.setOnSucceeded(e -> {
            tableView_stmts.setItems(service.getValue());
            stmt_loaders();
        });
        service.start();
    }

    @FXML
    private void do_stmt_add(ActionEvent event) {
        if (btn_stmt_add.getText().contentEquals("ADD")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Click Ok to continue");
            alert.setTitle("Continue");
            Optional<ButtonType> results = alert.showAndWait();
            if (results.get() == ButtonType.OK) {
                if (!txt_id.getText().isEmpty()) {
                    account_client_section_stmts accStmt = new account_client_section_stmts(
                            account_id,//this is static string variable from the account dashboard controller
                            txt_id.getText(), txt_stmt_product.getValue(),
                            stmt_item_type, txt_stmt_qty.getText(), txt_stmt_cost.getText(),
                            txt_stmt_amtPaid.getText(), new Label(txt_stmt_balance.getText()),
                            semanticsClass.returneDate(), semanticsClass.nowStamp(new Date())
                    );
                    if (!(accStmt.getStmt_item().isEmpty()
                            || accStmt.getStmt_item_type().isEmpty()
                            || accStmt.getStmt_qty().isEmpty()
                            || accStmt.getStmt_cost().isEmpty()
                            || accStmt.getStmt_amt_paid().isEmpty()
                            || accStmt.getStmt_balance().getText().isEmpty())) {

                        Service<Boolean> service = acss.new client_stmts().add_stmt(accStmt);
                        service.setOnRunning(e -> {
                            stmt_masker.textProperty().bind(service.messageProperty());
                            stmt_masker.visibleProperty().bind(service.runningProperty());
                        });
                        service.setOnSucceeded(e -> {
                            general_stmt_loader(semanticsClass.returneDate());
                            stmt_loaders();//set items to stmt combo box and stmt textfields
                        });
                        service.setOnCancelled(e -> {
                            semanticsClass.set_notification("Error", service.getMessage());
                        });
                        service.start();
                    } else {
                        semanticsClass.set_notification("Operation disallowed", "Make sure you complete all entry");
                    }
                } else {
                    semanticsClass.set_notification("Error", "No selected client");
                }
            }
        } else {
            System.out.println("First " + txt_stmt_product.getValue());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Click Ok to payout ");
            alert.setTitle("Continue");
            Optional<ButtonType> results = alert.showAndWait();
            if (results.get() == ButtonType.OK) {
                if (!txt_id.getText().isEmpty()) {
                    account_client_section_stmts accStmt = new account_client_section_stmts(
                            "0",//replace with accounts id here
                            txt_id.getText(), txt_stmt_product.getValue(),
                            stmt_item_type, txt_stmt_qty.getText(), txt_stmt_cost.getText(),
                            txt_stmt_amtPaid.getText(), new Label(txt_stmt_balance.getText()),
                            myAcs.getOn_date(), /*when the is selected, the selected is stored in the myAcs object, this object contains the getter of on_date to the specific record, 
                            this on_date helps update the record*/
                            semanticsClass.nowStamp(new Date())
                    );
                    System.out.println("Second " + txt_stmt_product.getValue());
                    Service<Boolean> service = acss.new client_stmts().update_stmt(accStmt);
                    service.setOnRunning(e -> {
                        stmt_masker.textProperty().bind(service.messageProperty());
                        stmt_masker.visibleProperty().bind(service.runningProperty());
                    });
                    service.setOnSucceeded(e -> {
                        // general_stmt_loader(accStmt.getOn_date());
                    });
                    service.setOnCancelled(e -> {
                        semanticsClass.set_notification("Error", service.getMessage());
                    });
                    service.start();
                } else {
                    semanticsClass.set_notification("Error", "No selected client");
                }
            }
        }
    }
    account_client_section_stmts myAcs = new account_client_section_stmts();

    @FXML
    private void tableView_stmts_mouse_clicked(MouseEvent event) {
        if (event.getClickCount() == 2) {//double click to load single client details
            if (!tableView_stmts.getSelectionModel().isEmpty()) {
                myAcs = tableView_stmts.getSelectionModel().getSelectedItem();
                fill_opens(myAcs.getStmt_cid());
                btn_stmt_add.setText("Update");
                btn_stmt_add.setDisable(false);

                txt_stmt_amtPaid.setText("");
                txt_stmt_balance.setText("");
                txt_stmt_cost.setText(myAcs.getStmt_balance().getText());
                txt_stmt_qty.setText(myAcs.getStmt_qty());
                txt_stmt_product.setValue(myAcs.getStmt_item());

                stmt_item_type = myAcs.getStmt_item_type();
                System.out.println(stmt_item_type);
                if (stmt_item_type.contentEquals(rd_stmt_rm.getText())) {
                    rd_stmt_rm.setSelected(true);
                } else if (stmt_item_type.contentEquals(rd_stmt_sfp.getText())) {
                    rd_stmt_sfp.setSelected(true);
                } else {
                    rd_stmt_rm.setSelected(false);
                    rd_stmt_sfp.setSelected(false);
                }
            }
        }
    }

    @FXML
    private void do_stmt_make_new(ActionEvent event) {
        txt_stmt_amtPaid.setText("");
        txt_stmt_balance.setText("");
        txt_stmt_cost.setText("");
        txt_stmt_product.setValue("");
        txt_stmt_qty.setText("");
        btn_stmt_add.setDisable(false);
        btn_stmt_add.setText("ADD");
    }

    @FXML
    private void do_update(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Click Ok to continue change");
        alert.setTitle("Continue");
        Optional<ButtonType> results = alert.showAndWait();
        if (results.get() == ButtonType.OK) {

            Service<Boolean> service;
            account_Client_Section acs = new account_Client_Section();
            acs.setClient_type(client_type_str);
            acs.setCid(txt_id.getText().trim());
            acs.setClient_name(txt_client_name.getText());
            acs.setClient_contact(txt_client_contact.getText());
            acs.setClient_address(txt_client_addr.getText());
            acs.setClient_desc(txt_client_desc.getText());
            service = acss.update_service(acs);
            service.setOnRunning(e -> {
                parent_masker.visibleProperty().bind(service.runningProperty());
                parent_masker.textProperty().bind(service.messageProperty());
            });
            service.setOnSucceeded(e -> {
                load_clients();
            });
            service.start();
        }
    }
}
