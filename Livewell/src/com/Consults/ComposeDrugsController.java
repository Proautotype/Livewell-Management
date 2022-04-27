/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Consults;

import static com.Consults.Doctors_NEWController.composeThis;
import com.connection.conclass;
import com.jfoenix.controls.JFXListView;
import com.semantics.semanticsClass;
import com.services.Store_Services;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.web.HTMLEditor;
import org.controlsfx.control.MaskerPane;

/**
 * FXML Controller class
 *
 * @author G
 */
public class ComposeDrugsController implements Initializable {

    @FXML
    private JFXListView<String> list_existing_drugs;
    @FXML
    private HBox vbox_controllers;
    @FXML
    private TextField txt_gname;
    @FXML
    private TextField txt_med_type;
    @FXML
    private HTMLEditor htmlEditor_compo;
    @FXML
    private TextField txt_drugCode;

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = "";
    String patient_id = "";
    @FXML
    private MaskerPane maskerPane;
    String pComposeThis = "";
    @FXML
    private TextField txt_price;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pComposeThis = composeThis;
        txt_gname.setText(pComposeThis);
        doLoading();
    }

    void doLoading() {
        {
            Service<Connection> service = new Service<Connection>() {
                @Override
                protected Task<Connection> createTask() {
                    //To change body of generated methods, choose Tools | Templates.
                    return new Task<Connection>() {
                        @Override
                        protected Connection call() throws Exception {
                            Connection conn = conclass.livewell_localhost();
                            updateValue(conn);
                            return conn;
                        }
                    };
                }
            };

            service.setOnRunning(e -> {

            });
            service.setOnSucceeded(e -> {
                if (conn != null) {
                    getLastId();
                } else {
                    conn = conclass.livewell_localhost();
                    getLastId();
                }

            });
            service.start();
        }

    }

    void getLastId() {

        try {
            sql = "select * from presc_drugs";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            String valuer = "";
            list_existing_drugs.getItems().clear();
            while (rs.next()) {
                valuer = rs.getString("code");
                list_existing_drugs.getItems().add(valuer);
                txt_drugCode.setText(valuer);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    void composer() {
        try {
            sql = "select * from presc_drugs where code = ?";
            conn = conclass.livewell_localhost();
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_drugCode.getText().trim());
            rs = pst.executeQuery();
            if (!rs.next()) {
                interum_doc_storeServices sS
                        = new interum_doc_storeServices(
                                txt_drugCode.getText(),
                                txt_gname.getText(),
                                txt_med_type.getText(),
                                htmlEditor_compo.getHtmlText(),
                                Double.valueOf(txt_price.getText())
                        );
                Service<Boolean> service = sS.insert_interum_drug();
                service.setOnRunning(e -> {
                    maskerPane.textProperty().bind(service.messageProperty());
                    maskerPane.visibleProperty().bind(service.runningProperty());
                });
                service.setOnSucceeded(e -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Successful");
                    alert.setHeaderText("New medicine is added, close this window and \n click the Refresh button on the top-left corner of the main consulting window");
                    Optional<ButtonType> results = alert.showAndWait();

                });
                service.setOnCancelled(e -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Cancelled");
                    alert.setHeaderText("Seems this medicine already exist,\nClick Ok to rather update");
                    Optional<ButtonType> results = alert.showAndWait();
                });
                service.start();
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    void updater() {
        String value = "";
        if (txt_price.getText().isEmpty()) {
            value = "0.00";
        } else {
            value = txt_price.getText();
        }

        interum_doc_storeServices sS = new interum_doc_storeServices(
                txt_drugCode.getText(),
                txt_gname.getText(),
                txt_med_type.getText(),
                htmlEditor_compo.getHtmlText(),
                Double.valueOf(value)
        );
        Service<Boolean> service = sS.update_interum_drug();
        service.setOnRunning(e -> {
            maskerPane.textProperty().bind(service.messageProperty());
            maskerPane.visibleProperty().bind(service.runningProperty());
        });
        service.setOnSucceeded(e -> {
            semanticsClass.set_notification("Success", "Medicine has been updated");
        });
        service.setOnCancelled(e -> {
            semanticsClass.set_notification("Error", "Seemed an error occured,\nContact your IT personnel");
        });
        service.start();
    }

    @FXML
    private void compose(ActionEvent event) {
        composer();
    }

    @FXML
    private void updateDrug(ActionEvent event) {
        updater();
    }

    @FXML
    private void removeDrug(ActionEvent event) {
    }

    @FXML
    private void list_mouseClicked(MouseEvent event) {
        if (!list_existing_drugs.getSelectionModel().isEmpty()) {
            String id = list_existing_drugs.getSelectionModel().getSelectedItem();
            getDrugData(id);
        }
    }

    void getDrugData(String did) {
        try {
            sql = "select * from presc_drugs where code = '" + did + "' ";
            try {

                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                if (rs.next()) {
                    txt_drugCode.setText(rs.getString("code"));
                    txt_gname.setText(rs.getString("drug_name"));
                    txt_med_type.setText(rs.getString("drug_type"));
                    rs.getString("drug_composition");
                    if (!rs.wasNull()) {
                        if (!rs.getString("drug_composition").isEmpty()) {
                            htmlEditor_compo.setHtmlText(rs.getString("drug_composition"));
                        } else {
                            System.out.println("this should be empty");
                            htmlEditor_compo.setHtmlText("<html><head></head><body>");
                        }
                    } else {
                        System.out.println("Ok this is null");
                        htmlEditor_compo.setHtmlText("<html><head></head><body>");
                    }
                    txt_price.setText(rs.getString("price"));

                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (Exception e) {
        }
    }
}
