/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.signIn;

import com.connection.conclass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.payAtten.RegisterController;
import com.semantics.semanticsClass;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.CustomPasswordField;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class nSign_Controller implements Initializable {

    @FXML
    private JFXTextField txt_userName;
    @FXML
    private JFXPasswordField txt_password;
    private JFXComboBox<String> cbo_deptment;

    Connection conn = null;
    public static String username, password, sql;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private String CWD;
    private String CWDi;
    @FXML
    private Button btn_sign;
    @FXML
    private Label lbl_note;

    ObservableList<Parent> allRoot = null;
    HashMap<String, String> hm = new HashMap();
    String userID = "";
    @FXML
    private JFXButton btn_acc;
    @FXML
    private JFXButton btn_consult;
    @FXML
    private JFXButton btn_lab;
    @FXML
    private JFXButton btn_opd;
    @FXML
    private JFXButton btn_pharm;
    @FXML
    private JFXButton btn_records;
    @FXML
    private JFXButton btn_stores;
    @FXML
    private Pane pane_selector;
    @FXML
    private VBox container_vbox;
    @FXML
    private MaskerPane masker;
    @FXML
    private JFXButton btn_production;
    @FXML
    private JFXButton btnAdmin;
    @FXML
    private JFXButton btn_settings;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnActions();
        work();
    }

    void work() {
        hm.put("account_deptMent", "/com/Accountancy/Accountancy_dash.fxml");
        hm.put("admin_dept", "/com/Administrator/ThisAdministrator12.fxml");
        hm.put("records_deptMent", "/com/Records/Records_1.fxml");
        hm.put("opd_deptMent", "/com/Nurses/nurses.fxml");
        hm.put("consulting_deptMent", "/com/Consults/Doctors_NEW.fxml");
        hm.put("laboratory_deptMent", "/com/Laboratory/laboratory.fxml");
        hm.put("pharmacy_deptMent", "/com/Pharmacy/pharmacy.fxml");
        hm.put("stores_deptMent", "/com/Stores/stores.fxml");
    }

    void btnActions() {
        btn_acc.setOnAction(e -> {
            CWD = "account_deptMent";
            System.out.println(CWD);
            pane_selector.setStyle(btn_acc.getStyle());
        });
        btn_consult.setOnAction(e -> {
            CWD = "consulting_deptMent";
            System.out.println(CWD);
            pane_selector.setStyle(btn_consult.getStyle());
        });
        btn_lab.setOnAction(e -> {
            CWD = "laboratory_deptMent";
            System.out.println(CWD);
            pane_selector.setStyle(btn_lab.getStyle());
        });
        btn_opd.setOnAction(e -> {
            CWD = "opd_deptMent";
            System.out.println(CWD);
            pane_selector.setStyle(btn_opd.getStyle());
        });
        btn_pharm.setOnAction(e -> {
            CWD = "pharmacy_deptMent";
            System.out.println(CWD);
            pane_selector.setStyle(btn_pharm.getStyle());
        });
        btn_records.setOnAction(e -> {
            CWD = "records_deptMent";
            System.out.println(CWD);
            pane_selector.setStyle(btn_records.getStyle());

        });
        btn_stores.setOnAction(e -> {
            CWD = "stores_deptMent";
            System.out.println(CWD);
            pane_selector.setStyle(btn_stores.getStyle());
        });
        btn_production.setOnAction(e -> {
            CWD = "stores_deptMent";
            System.out.println(CWD);
            pane_selector.setStyle(btn_production.getStyle());
        });
    }

    void whatIsSelected(JFXButton btn, Pane pa) {
        btn.setOnAction(e -> {
            pa.setStyle(btn.getStyle());
        });
    }

    boolean worker = false;

    private boolean check() throws SQLException {
        conn = conclass.livewell_localhost();
        if (!(conn == null)) {
            sql = "select * from users where customId = ? or email = ? ";
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_userName.getText());
            pst.setString(2, txt_userName.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                username = rs.getString("customId");
                System.out.println(username);
//            semanticsClass.set_notification("good", username);
                worker = true;
            } else {
                worker = false;

            }
            return worker;
        } else {
            semanticsClass.set_notification("Error", "Connection was lost");
            return false;
        }

    }

    boolean canOpen = false;

    @FXML
    private void do_sign(ActionEvent event) throws SQLException {
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                //To change body of generated methods, choose Tools | Templates.
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        if (check()) {
                            if (username.isEmpty() || txt_password.getText().isEmpty()) {

                            } else {
                                try {

                                    sql = "select users.*," + CWD + ".* from users," + CWD + " where " + CWD + ".user_id = ? and " + CWD + ".password = ? ";
                                    pst = conn.prepareStatement(sql);
                                    pst.setString(1, username);
                                    pst.setString(2, txt_password.getText());
                                    rs = pst.executeQuery();
                                    if (rs.next()) {
//                                        lbl_note.setText("Correct credentials");
                                        updateMessage("Correct credentials");
                                        canOpen = true;
                                        Thread.sleep(5);
                                    } else {
                                        canOpen = false;
//                                        lbl_note.setText("Incorrect credentials");
                                        updateMessage("Incorrect credentials");
                                        Thread.sleep(1000);
                                        cancel();
                                    }

                                } catch (SQLException ex) {
                                    updateMessage(ex.getMessage());
                                    System.out.println(ex);
                                    Thread.sleep(250);
                                }
                            }
                        } else {
                            updateMessage("This user doesnt exist, Please check and try again");
                            Thread.sleep(500);
                            cancel();
                        }
                        return canOpen; //To change body of generated methods, choose Tools | Templates.
                    }
                };
            }
        };
        service.setOnRunning(e -> {
            masker.visibleProperty().bind(service.runningProperty());
            masker.textProperty().bind(service.messageProperty());
        });
        service.setOnSucceeded(e -> {
            openNow(canOpen);
        });
        service.start();
        //conn = conclass.livewell_localhost();

    }
    Stage stage = new Stage();
    int stage_count = 0;

    void openNow(boolean can) {
        if (can) {
            try {
                if (!stage.isShowing()) {
                    //  System.out.println(hm.get(CWD));
                    Parent root = FXMLLoader.load(getClass().getResource(hm.get(CWD)));
                    // System.out.println("Loaded");
                    Scene scene = new Scene(root);
                    // System.out.println("scene ready");
                    stage = new Stage();
                    // System.out.println("stage set");
                    stage.setScene(scene);
                    // System.out.println("scene set to stage");
                    stage.show();
                    //   System.out.println("stage should be showin");
                } else {
                    Parent root = FXMLLoader.load(getClass().getResource(hm.get(CWD)));

                    Scene scene = new Scene(root);

                    Stage stager = new Stage();

                    stager.setScene(scene);
                    stager.show();

                }
            } catch (IOException e) {
                System.out.println(e);
            }
        } else {
            System.out.println("It couldn't");
        }
    }
    int count = 0;

    @FXML
    private void open_admin(ActionEvent event) {
        PopOver pop = new PopOver();
        final String pasHere = "TrendAdmin";
        PasswordField tx = new PasswordField();
        tx.setPromptText("Enter Password");
        HBox box = new HBox(new Label("Password"), tx);
        box.setSpacing(5);
        pop.setContentNode(box);
        tx.setOnAction(ee -> {
            if (tx.getText().contentEquals(pasHere)) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/com/Administrator/ThisAdministrator12.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    //stage.setAlwaysOnTop(true);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                }
            } else {
                if (count >= 3) {
                    btnAdmin.setDisable(true);
                }
                count++;
            }
        });
        pop.show(btnAdmin);

    }

    @FXML
    private void open_settings(ActionEvent event) {
        PopOver pop = new PopOver();
        final String pasHere = "SetByAdmin";
        PasswordField tx = new PasswordField();
        tx.setPromptText("Enter Password");
        HBox box = new HBox(new Label("Password"), tx);
        box.setSpacing(5);
        pop.setContentNode(box);
        tx.setOnAction(ee -> {
            if (tx.getText().contentEquals(pasHere)) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/com/signIn/settings.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setAlwaysOnTop(true);
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception e) {
                }
            } else {
                if (count >= 3) {
                    btnAdmin.setDisable(true);
                }
                count++;
            }
        });
        pop.show(btn_settings);

    }

    @FXML
    private void closeApplication(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Press ok to continue");
        alert.setTitle("Confirm");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            RegisterController.rootStage.close();
        }

    }
}
