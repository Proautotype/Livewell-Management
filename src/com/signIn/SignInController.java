/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.signIn;

import com.connection.conclass;
import static com.connection.conclass.controlledConn;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import com.semantics.semanticsClass;
import com.services.SignIn_doSignIn;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class SignInController implements Initializable {

    @FXML
    private JFXTextField txt_username;
    @FXML
    private JFXPasswordField txt_password;
    ObservableList<Parent> allRoot = null;
    HashMap<String, String> hm = new HashMap();
    @FXML
    private MaskerPane parentMasker;

    boolean isNotFound = true;

    public static String username = "";
    @FXML
    private JFXProgressBar progress_signer;
    Connection conn = conclass.livewell_localhost();
    private String password, sql;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    private boolean worker;
    @FXML
    private Label lbl_notice;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hm.put("account_deptMent", "/com/Accountancy/Accountancy_dash.fxml");
        hm.put("admin_dept", "/com/Administrator/ThisAdministrator12.fxml");
        hm.put("records_deptMent", "/com/Records/Records_1.fxml");
        hm.put("opd_deptMent", "/com/Nurses/nurses.fxml");
        hm.put("consulting_deptMent", "/com/Consults/Doctors_NEW.fxml");
        hm.put("laboratory_deptMent", "/com/Laboratory/laboratory.fxml");
        hm.put("pharmacy_deptMent", "/com/Pharmacy/pharmacy.fxml");
        hm.put("stores_deptMent", "/com/Stores/stores.fxml");

        parentMasker.setVisible(false);
    }

    @FXML
    private void recoverCredentials(ActionEvent event) {
    }
    int count = 0;
    int sign_count = 0;

    @FXML
    private void signIn(ActionEvent event) throws SQLException, IOException {
        if (check()) {
            other_log(username);
        }
    }

    @FXML
    private void closeApplication(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Press ok to continue");
        alert.setTitle("Confirm");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Signer_Main.rootStage.close();
        }
        
    }

    private boolean check() throws SQLException {
        conn = conclass.livewell_localhost();
        if (!(conn == null)) {
            sql = "select * from users where customId = ? or email = ? ";
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_username.getText());
            pst.setString(2, txt_username.getText());
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

    void other_log(String valuer) {
        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            ObservableList<String> CWDs = FXCollections.observableArrayList(
                    "records_deptMent", "opd_deptMent", "laboratory_deptMent",
                    "consulting_deptMent", "pharmacy_deptMent", "stores_deptMent",
                    "account_deptMent", "admin_dept");

            conn = conclass.livewell_localhost();

            CWDs.stream().map((CWD) -> {
                System.out.println(CWD + " : " + hm.get(CWD));
                SignIn_doSignIn sds = new SignIn_doSignIn(valuer, txt_password.getText().trim(), CWD);
                sds.setOnRunning(qe -> {
                    parentMasker.textProperty().bind(sds.messageProperty());
                    parentMasker.visibleProperty().bind(sds.runningProperty());
                });
                sds.setOnFailed(qe -> {
                    count++;
                    if (!(count >= 5)) {
                        sds.restart();
                    } else {
                        sds.cancel();
                    }
                });
                sds.setOnCancelled(ee -> {
                    semanticsClass.set_notification("Wrong credentials please check and try again", username);
                });
                sds.setOnSucceeded((ew) -> {
                    boolean workere = sds.getValue();
                    if (workere) {
                        try {
                            System.out.println("\n" + CWD + " " + hm.get(CWD));
                            Parent root = FXMLLoader.load(getClass().getResource(hm.get(CWD)));
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.show();
                            lbl_notice.setVisible(false);
                            stage.setOnCloseRequest((event) -> {
                                Signer_Main.rootStage.setIconified(false);
                            });
                            Signer_Main.rootStage.setIconified(true);
                            isNotFound = false;
                        } catch (IOException ex) {
                            System.out.println(e);
                        }
                    } else {
                        lbl_notice.setText("Invalid username or password or user doesn't belong to any department");
                        lbl_notice.setStyle("-fx-background-color:tomato; -fx-text-fill:white;");
                        lbl_notice.setVisible(true);
                    }
                });
                return sds;
            }).forEachOrdered((sds) -> {
                sds.start();
            });
        }));

        timer.setCycleCount(0);
        timer.play();
    }

    @FXML
    private void openConnMgmt(ActionEvent event) {
       // openConn();
        try {
        Parent root = FXMLLoader.load(getClass().getResource("/com/signIn/settings.fxml"));
        Scene scene = new Scene(root);        

        Stage stage = new Stage();
        stage.setAlwaysOnTop(true);
        stage.setScene(scene);
        stage.show();
        
       // setting_preferences.initConfiq();
        
        } catch (Exception e) {
        }
    }
//
//    void openConn() {
//        PopOver pop = new PopOver();
//        VBox main_container = new VBox();
//        Pane header = new Pane();
//        Pane body = new Pane();
//        Pane footer = new Pane();
//
//        Label lbl_head = new Label("Connection Setting");
//        lbl_head.setStyle("-fx-text-alignment:center;");
//
//        ComboBox<String> conCombo = new ComboBox<>(FXCollections.observableArrayList("Orange", "Mango", "Pineapple"));
//        int i = conCombo.getSelectionModel().getSelectedIndex();
//       
//        PasswordField user = new PasswordField();
//        user.setPromptText("User");
//        PasswordField password = new PasswordField();
//        password.setPromptText("Password");
//        TextField txt_ip = new TextField();
//        txt_ip.setPromptText("IP Address");
//        TextField txt_database = new TextField();
//        txt_database.setPromptText("Database");
//        Button work = new Button("Test Connection");
//        Label tester = new Label();
//        tester.setVisible(false);
//        work.setOnAction(e -> {
//            try {
//                Connection conn = controlledConn(user.getText().trim(), password.getText().trim(), txt_ip.getText().trim(), txt_database.getText().trim());
//                if(!conn.isClosed()){                    
//                    tester.setText("Connection Open");
//                }else{
//                    tester.setText("Connection is closed");
//                }
//            } catch (SQLException ex) {
//                System.out.println(ex);
//            }
//        });
//
//        header.getChildren().add(lbl_head);
//
//        main_container.setAlignment(Pos.CENTER);
//        main_container.getChildren().addAll(header,conCombo, user,password,txt_ip,txt_database,work,new Pane(),tester);
//
//        pop.setContentNode(main_container);
//        pop.show(lbl_notice);
//    }

}
