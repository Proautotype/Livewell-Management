/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payAtten;

import AES.random;
import com.connection.conclass;
import com.semantics.semanticsClass;
import com.sun.mail.imap.ACL;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.concurrent.ScheduledService;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author G
 */
public class RegisterController implements Initializable {

    @FXML
    private Label lbl_date;
    @FXML
    private Label lbl_hour;
    @FXML
    private Label lbl_min;
    @FXML
    private Label lbl_secs;
    int counter = 0;
    public static int stimer, mtimer, htimer;
    @FXML
    private TextField txt_contact;
    @FXML
    private PasswordField txt_pword;

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = "";
    @FXML
    private MaskerPane parent_masker;
    boolean isAvbl;
    @FXML
    private Button btn_settings;
    public static Stage rootStage = new Stage();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myTimer();
        lbl_date.setText(semanticsClass.returneDate());
        setDefaultTime();
        createListHome();
    }
    String random_value = "";

    void createListHome() {
        if (random_value.length() > 100) {
            random_value = random_value.substring(0, 99);
        }
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        random_value = random.random_String_Value(semanticsClass.returneDate());
                        conn = conclass.livewell_localhost();
                        try {
                            sql = "INSERT INTO `livewell`.`registrar_home`"
                                    + "(`date`,`rec_id`) VALUES (?,?)";

                            pst = conn.prepareStatement(sql);
                            pst.setString(1, semanticsClass.returneDate());
                            pst.setString(2, random_value);
                            pst.execute();
                            updateMessage("Created successfully");
                            Thread.sleep(2000);
                            isAvbl = false;
                            return true;
                        } catch (SQLException e) {
                            if (e.getErrorCode() == 1062) {
                                updateMessage("Home ready");
                                System.out.println("Home ready");
                                isAvbl = true;
                                Thread.sleep(2000);
                                cancel();
                            }
                        }
                        return false;
                    }
                };
            }
        };
        service.setOnRunning(e -> {
            parent_masker.visibleProperty().bind(service.runningProperty());
        });
        service.setOnSucceeded(e -> {
            semanticsClass.set_notification("", "");
        });
        service.setOnCancelled(e -> {
            if (isAvbl) {
                try {
                    pst = conn.prepareStatement("select * from registrar_home where date = '" + semanticsClass.returneDate() + "' ");
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        random_value = rs.getString("rec_id");
                    }
                } catch (SQLException ee) {
                    System.out.println();
                }
            }
        });
        service.start();
    }

    void setDefaultTime() {
        Date date = new Date();
        Calendar cal = GregorianCalendar.getInstance();
        date = cal.getTime();
        int hour = date.getHours();
        htimer = hour;
        int minute = date.getMinutes();
        mtimer = minute;
        int second = date.getSeconds();
        stimer = second;
        lbl_hour.setText(String.valueOf(hour));
        lbl_min.setText(String.valueOf(minute));
        lbl_secs.setText(String.valueOf(second));
    }

    void myTimer() {
        ScheduledService<Integer> service = new ScheduledService<Integer>() {
            @Override
            protected Task<Integer> createTask() {
                return new Task<Integer>() {
                    @Override
                    protected Integer call() throws Exception {
                        try {
                            Thread.sleep(1000);
                            counter++;
                            updateValue(counter);
                        } catch (InterruptedException e) {
                        }
                        return counter;
                    }
                };
            }
        };
        service.setOnRunning(e -> {
            if (counter >= 0) {
                if (counter == 60) {
                    mtimer += 1;

                    if (mtimer == 60) {
                        htimer += 1;
                        mtimer = 0;
                        if (htimer == 24) {
                            htimer = 0;
                        }
                    }
                    counter = 0;
                }
                lbl_hour.setText(String.valueOf(htimer));
                lbl_min.setText(String.valueOf(mtimer));
                lbl_secs.setText(String.valueOf(counter));
            }
        });
        service.start();
    }

    @FXML
    private void openSettings(ActionEvent event) {
        TextField txter = new TextField();
        PopOver pop = new PopOver();
        pop.setContentNode(txter);
        txter.setOnAction(e -> {
            if (txter.getText().contentEquals("TrendAdmin")) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/com/payAtten/settings.fxml"));
                    Scene scene = new Scene(root);
                    Stage rootStage = new Stage();

                    rootStage.initStyle(StageStyle.DECORATED);
                    rootStage.setTitle("Timer settings");
                    rootStage.setScene(scene);
                    rootStage.show();
                } catch (IOException ee) {
                }
            }
        });

        pop.show(btn_settings);

    }

    @FXML
    private void do_authenticate(ActionEvent event) {
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        Timestamp nowStamp = semanticsClass.nowStamp(new Date());
                        conn = conclass.livewell_localhost();
                        try {
                            sql = "select * from registrar_users where contact = ? and pword = ?";
                            pst = conn.prepareStatement(sql);
                            pst.setString(1, txt_contact.getText().trim());
                            pst.setString(2, txt_pword.getText().trim());
                            rs = pst.executeQuery();
                            if (rs.next()) {
                                updateMessage("User authenticated successfully ......");
                                Thread.sleep(2000);
                                updateMessage("Checking in ...... ");
                                Thread.sleep(2000);

                                try {
                                    sql = "insert into registrar_list(user_contact,reg_id,time_in) "
                                            + "values (?,?,?)";
                                    pst = conn.prepareStatement(sql);
                                    pst.setString(1, txt_contact.getText().trim());
                                    pst.setString(2, random_value);
                                    pst.setTimestamp(3, nowStamp);
                                    pst.execute();
                                    updateMessage("Checked");
                                    Thread.sleep(2000);
                                } catch (SQLException e) {
                                    if (e.getErrorCode() == 1062) {
                                        updateMessage("user already checked ");
                                        Thread.sleep(2000);
                                    } else {
                                        updateMessage("Couldnt check an error occured, send the code " + nowStamp + " to IT Admin ");
                                        Thread.sleep(2000);
                                    }
                                }

                            } else {
                                updateMessage("Unauthorized user ");
                                Thread.sleep(2000);
                            }
                            updateMessage("Available");
                            return true;
                        } catch (SQLException e) {
                            if (e.getErrorCode() == 1062) {
                                updateMessage("user already exist");
                                Thread.sleep(2000);
                            } else {
                                updateMessage(e.getMessage());
                                Thread.sleep(2000);
                            }

                        }
                        return false;
                    }
                };
            }
        };
        service.setOnRunning(e -> {
            parent_masker.visibleProperty().bind(service.runningProperty());
            parent_masker.textProperty().bind(service.messageProperty());
        });
        service.setOnSucceeded(e -> {

        });
        service.start();
    }

    @FXML
    private void openMgmt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/signIn/new_sign.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
         rootStage = new Stage();
        rootStage.initStyle(StageStyle.TRANSPARENT);
        rootStage.setTitle("Sign In");
        rootStage.setScene(scene);
        rootStage.show();
    }

}
