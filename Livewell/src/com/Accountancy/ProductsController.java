/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Accountancy;

import com.fasterxml.jackson.databind.deser.impl.PropertyValue;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import org.controlsfx.control.MaskerPane;

/**
 * FXML Controller class
 *
 * @author G
 */
public class ProductsController implements Initializable {

    @FXML
    private Circle circ_indicator;
    @FXML
    private TextField txt_pid;
    @FXML
    private TextField txt_pname;
    @FXML
    private TextField txt_uprice;
    @FXML
    private TableView<products_mgmt> table_pmp;
    @FXML
    private TableColumn<?, ?> col_pid;
    @FXML
    private TableColumn<?, ?> col_pname;
    @FXML
    private TableColumn<?, ?> col_uprice;
    @FXML
    private Label lbl_total_records;
    @FXML
    private MaskerPane masker;

    String sql = "";
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        col_pid.setCellValueFactory(new PropertyValueFactory<>("pid"));
        col_pname.setCellValueFactory(new PropertyValueFactory<>("pname"));
        col_uprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        loader();
    }

    @FXML
    private void make_save(ActionEvent event) {
        products_mgmt pmp = new products_mgmt();
        pmp.setPid(txt_pid.getText());
        pmp.setPname(txt_pname.getText());
        pmp.setPrice(Double.parseDouble(txt_uprice.getText()));
        Service<Boolean> addPp = pmp.addPp(pmp);
        addPp.setOnRunning(e -> {
            masker.visibleProperty().bind(addPp.runningProperty());
            masker.textProperty().bind(addPp.messageProperty());
        });
        addPp.setOnSucceeded(e -> {
            loader();
        });
        addPp.setOnCancelled((WorkerStateEvent e) -> {
            String val = addPp.messageProperty().getValue();
            System.out.println(val);
            boolean contentEquals = val.contentEquals("1062");
            if (contentEquals) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Seems this ID is already available, click OK to update or Cancel to abort Operation");
                Optional<ButtonType> showAndWait = alert.showAndWait();
                if (showAndWait.get().equals(ButtonType.OK)) {
                    Service<Boolean> updatePp = pmp.updatePp(pmp);
                    updatePp.setOnRunning(ee -> {
                        masker.visibleProperty().bind(updatePp.runningProperty());
                        masker.textProperty().bind(updatePp.messageProperty());
                    });
                    updatePp.setOnSucceeded(ee->{
                        loader();
                    });
                    updatePp.start();
                }

            } else {
                System.out.println(" -- Something happened -- ");
            }
        });
        addPp.start();
    }

    public void loader() {
        products_mgmt pmp = new products_mgmt();
        Service<ObservableList<products_mgmt>> load = pmp.load("*");
        load.setOnSucceeded(e2 -> {
            table_pmp.getItems().clear();
            table_pmp.setItems(load.getValue());
        });
        load.start();
    }

    @FXML
    private void txt_pname_textIn(KeyEvent event) {
          products_mgmt pmp = new products_mgmt();
        Service<ObservableList<products_mgmt>> load = pmp.load(txt_pname.getText());
        load.setOnSucceeded(e2 -> {
            table_pmp.getItems().clear();
            table_pmp.setItems(load.getValue());
        });
        load.start();
    }
}
