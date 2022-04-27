/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Stores;

import com.Pharmacy.pharmacy_controller;
import com.connection.conclass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTabPane;
import com.semantics.semanticsClass;
import com.services.Store_Services;
import com.services.concurrent_check_connection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.controlsfx.control.MaskerPane;
import com.services.Store_Services;
import com.tables.stores_allDrugs;
import java.util.Optional;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class StoresController implements Initializable {

    @FXML
    private JFXTabPane parent_tab;
    @FXML
    private Tab tab_newDrug;
    @FXML
    private MenuItem menu_update;
    @FXML
    private MenuItem menu_save;
    @FXML
    private TextField txt_drug_code;
    @FXML
    private TextField txt_drug_name;
    @FXML
    private TextField txt_drug_category;
    @FXML
    private TextField txt_drug_price;
    @FXML
    private JFXButton btn_save_update;
    @FXML
    private Tab tab_viewDrug;
    @FXML
    private TableView<stores_allDrugs> table_allDrugs;
    @FXML
    private TableColumn col_drug_id;
    @FXML
    private TableColumn col_drug_name;
    @FXML
    private TableColumn col_drug_category;
    @FXML
    private TableColumn col_drug_uPrice;
    @FXML
    private TableColumn col_drug_qty;
    @FXML
    private TextField txt_search_box;
    @FXML
    private TextField txt_drug_qty;
    @FXML
    private ComboBox<String> cbo_category;
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = "";
    String patient_id = "";
    private int drug_avbl = 0;
    @FXML
    private MaskerPane parent_Masker;
    @FXML
    private JFXSpinner conn_spin_check;
    stores_allDrugs store_drug = null;
    ObservableList<stores_allDrugs> drug_list = FXCollections.observableArrayList();

    FilteredList<stores_allDrugs> filteredData = null;
    ObservableList<stores_allDrugs> indrugs = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myConConnection();
        settings();
        load_allDrugs();
        allDrugs("*");

        indrugs = table_allDrugs.getItems();
        filteredData = new FilteredList<>(indrugs, e -> true);

        inSearch();
        cbo_category.setItems(FXCollections.observableArrayList("All"));
    }

    void settings() {
        semanticsClass.liveWell_opd_values(txt_drug_qty);
        semanticsClass.liveWell_opd_values(txt_drug_code);
        semanticsClass.liveWell_opd_values(txt_drug_price);

        col_drug_id.setCellValueFactory(new PropertyValueFactory<>("code"));
        col_drug_name.setCellValueFactory(new PropertyValueFactory<>("drug_name"));
        col_drug_category.setCellValueFactory(new PropertyValueFactory<>("drug_type"));
        col_drug_uPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_drug_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    void load_allDrugs() {

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(25), ee -> {
            allDrugs("*");
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setDelay(Duration.seconds(5));
        timeline.play();
    }

    void allDrugs(String valuer) {
        Store_Services sS = new Store_Services();
        Service<ObservableList<stores_allDrugs>> select_all_drugs = sS.select_all_drugs(valuer);
        select_all_drugs.setOnSucceeded(e -> {
            table_allDrugs.setItems(select_all_drugs.getValue());
            indrugs = table_allDrugs.getItems();
            filteredData = new FilteredList<>(indrugs, eee -> true);
        });
        select_all_drugs.start();
    }

    void myConConnection() {
        ScheduledService<Connection> conConn = new concurrent_check_connection();
        conConn.setRestartOnFailure(true);
        conConn.setPeriod(Duration.seconds(4));
        conConn.setOnRunning(e -> {
            conn_spin_check.progressProperty().bind(conConn.progressProperty());
            conn_spin_check.visibleProperty().bind(conConn.runningProperty());
        });

        conConn.setOnFailed(e -> {
            parent_Masker.visibleProperty().bind(conConn.runningProperty());
            parent_Masker.textProperty().bind(conConn.messageProperty());
        });

        conConn.setOnSucceeded(e -> {
            try {
                conn = conConn.getValue();
                if (!conn.isClosed()) {
                    parent_Masker.visibleProperty().unbind();
                    parent_Masker.setVisible(false);
                } else {
                    parent_Masker.setVisible(true);
                    parent_Masker.setText("Connecting please wait");
                }
            } catch (SQLException ex) {
                Logger.getLogger(pharmacy_controller.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        conConn.start();
    }

    @FXML
    private void do_clear(ActionEvent event) {
        semanticsClass.clearTextFields(txt_drug_code, txt_drug_name, txt_drug_category,
                txt_drug_price, txt_drug_qty
        );
    }

    @FXML
    private void update(ActionEvent event) {
        try {
            do_update();
        } catch (Exception e) {
        }

    }

    @FXML
    private void save_update(ActionEvent event) {
        try {
            sql = "select * from presc_drugs where code = ?";
            conn = conclass.livewell_localhost();
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_drug_code.getText().trim());
            rs = pst.executeQuery();
            if (!rs.next()) {
                Store_Services sS
                        = new Store_Services(
                                txt_drug_code.getText(),
                                txt_drug_name.getText(),
                                txt_drug_category.getText(),
                                txt_drug_price.getText(),
                                txt_drug_qty.getText());
                Service<Boolean> service = sS.insert_drug();
                service.setOnRunning(e -> {
                    parent_Masker.textProperty().bind(service.messageProperty());
                    parent_Masker.visibleProperty().bind(service.runningProperty());
                });
                service.setOnSucceeded(e -> {
                    load_allDrugs();
                    semanticsClass.clearTextFields(txt_drug_code, txt_drug_name, txt_drug_category,
                            txt_drug_price, txt_drug_qty
                    );
                });
                service.setOnCancelled(e -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Successfull");
                    alert.setHeaderText("Seems this medicine already exist,\nClick Ok to rather update");
                    Optional<ButtonType> results = alert.showAndWait();
                    if (results.get() == ButtonType.OK) {
                        do_update();
                    }
                });
                service.start();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Successfull");
                alert.setHeaderText("Seems this medicine already exist,\nClick Ok to rather update");
                Optional<ButtonType> results = alert.showAndWait();
                if (results.get() == ButtonType.OK) {
                    do_update();
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void view_in_detail(ActionEvent event) {
        if (!table_allDrugs.getSelectionModel().isEmpty()) {
            int i = table_allDrugs.getSelectionModel().getSelectedIndex();
            if (table_allDrugs.getSelectionModel().isSelected(i)) {
                stores_allDrugs selectedItem = table_allDrugs.getSelectionModel().getSelectedItem();
                txt_drug_code.setText(selectedItem.getCode());
                txt_drug_name.setText(selectedItem.getDrug_name());
                txt_drug_category.setText(selectedItem.getDrug_type());
                txt_drug_price.setText(selectedItem.getPrice());
                txt_drug_qty.setText(selectedItem.getQuantity().getText());
                parent_tab.getSelectionModel().select(tab_newDrug);
            }
        }
    }

    void do_update() {
        Store_Services sS
                = new Store_Services(
                        txt_drug_code.getText(),
                        txt_drug_name.getText(),
                        txt_drug_category.getText(),
                        txt_drug_price.getText(),
                        txt_drug_qty.getText());
        Service<Boolean> service = sS.update_drug();
        service.setOnRunning(e -> {
            parent_Masker.textProperty().bind(service.messageProperty());
            parent_Masker.visibleProperty().bind(service.runningProperty());
        });
        service.setOnSucceeded(e -> {
            semanticsClass.set_notification("Success", "Medicine has been updated");
            load_allDrugs();
            semanticsClass.clearTextFields(txt_drug_code, txt_drug_name, txt_drug_category,
                    txt_drug_price, txt_drug_qty
            );
        });
        service.setOnCancelled(e -> {
            semanticsClass.set_notification("Error", "Seemed an error occured,\nContact your IT personnel");
        });
        service.start();
    }

    void inSearch() {
        txt_search_box.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);

            indrugs = table_allDrugs.getItems();
            filteredData = new FilteredList<>(indrugs, eee -> true);

            filteredData.setPredicate((t) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerValue = newValue.toLowerCase();
                if (t.getDrug_name().toLowerCase().indexOf(lowerValue) != -1) {
                    return true;
                } else if (t.getCode().toLowerCase().indexOf(lowerValue) != -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });

        SortedList<stores_allDrugs> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_allDrugs.comparatorProperty());
        table_allDrugs.setItems(sortedData);

    }

    @FXML
    private void _view_by_category(ActionEvent event) {
        String query = "";
        switch (cbo_category.getSelectionModel().getSelectedItem()) {
            case "All":
                query = "*";
                allDrugs(query);
                break;
            case "Tonic":
                query = "Tonic";
                allDrugs(query);
                break;

        }
//        Store_Services sS = new Store_Services();
//        Service<ObservableList<stores_allDrugs>> select_all_drugs = sS.select_all_drugs();
//        select_all_drugs.setOnSucceeded(e -> {
//            table_allDrugs.setItems(select_all_drugs.getValue());
//            indrugs = table_allDrugs.getItems();
//            filteredData = new FilteredList<>(indrugs, eee -> true);
//        });
//        select_all_drugs.start();
    }

    @FXML
    private void view_deficiency(ActionEvent event) {
        Store_Services sS = new Store_Services();
        Service<ObservableList<stores_allDrugs>> select_all_drugs = sS.select_all_drugs("0");
        select_all_drugs.setOnSucceeded(e -> {
            table_allDrugs.setItems(select_all_drugs.getValue());
            indrugs = table_allDrugs.getItems();
            filteredData = new FilteredList<>(indrugs, eee -> true);
        });
        select_all_drugs.start();
    }
}
