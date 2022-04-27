/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semantics;

import com.connection.conclass;
import java.sql.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.tables.nurse_patient_tabs;

import java.io.File;
import java.io.IOException;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Wynxtyn Proautotype
 */
public class semanticsClass {

    static int seman_nums_Main;
    static int seman_nums_Visitors;
    static int seman_nums_Today;
    static int seman_nums_VisFeMales;

    static double seman_Treas_total_offering;

    static double seman_First_Off_Per;
    static double seman_Second_Off_Per;
    static double seman_Spec_Off_Per;
    static double seman_Oth_Off_Per;

    private String pn;
    private String nhis;
    private String crest;

    static Timestamp opd_patient_time_in;
    private static String listened_OpS;//for listening to String from a TitledPane

    public semanticsClass() {
    }

    public semanticsClass(String pn, String nhis, String crest) {
        this.pn = pn;
        this.nhis = nhis;
        this.crest = crest;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public String getNhis() {
        return nhis;
    }

    public void setNhis(String nhis) {
        this.nhis = nhis;
    }

    static void Att_ResAllFields(TextField... ClrdTxtFields) {
        for (TextField txt : ClrdTxtFields) {
            if (txt.getText().isEmpty()) {
                txt.setText("0");
            }
        }
    }

    static boolean checkVoidTextField(JFXTextField... allTextField) {
        boolean hasEmpty;
        int count = 0;
        for (JFXTextField object : allTextField) {
            if (object.getText().isEmpty()) {
                count++;
                System.out.println("The textfield " + object.getId() + " is empty");
                object.setStyle("-fx-background-color: #efa7a7");

            } else {
                object.setStyle("-fx-background-color: none");
            }
        }
        if (count != 0) {
//            notificator.setText("Notification: ");
//            notificator.setText(notificator.getText() + "Empty Textfields Please make sure all textfields are field, Check and try again " + count + " empty text fields OK");
            hasEmpty = true;
        } else {
            hasEmpty = false;
        }

        return hasEmpty;
    }

    static void notificationHandler(String message) {

    }

    static void calCulateAttOnly(TextField numMales, TextField numFemales, TextField numVisMales, TextField numVisFemales) {
        TextField[] neededFields = new TextField[4];
        neededFields[0] = numMales;
        neededFields[1] = numFemales;
        neededFields[2] = numVisMales;
        neededFields[3] = numVisFemales;
        int[] nums = new int[4];
        int i = 0;
        for (TextField txtValues : neededFields) {
            if (!txtValues.getText().isEmpty()) {
                nums[i] = Integer.parseInt(txtValues.getText());
                i++;
                txtValues.setStyle("-fx-background-color: none");
            } else {
                txtValues.setStyle("-fx-background-color: #ff0000");
                txtValues.setText("0");
                return;
            }
        }
        int tot_main_Church = nums[0] + nums[1];
        int tot_Visitors = nums[2] + nums[3];
        int tot_today = tot_main_Church + tot_Visitors;

        seman_nums_Main = tot_main_Church;
        seman_nums_Visitors = tot_Visitors;
        seman_nums_Today = tot_today;

        seman_Treas_total_offering = nums[0] + nums[1] + nums[2] + nums[3];
    }

    static void TresCalcOffering(TextField first_Offering, TextField second_Offering, TextField spec_Offering, TextField other_Offering) {
        TextField[] neededFields = new TextField[4];
        neededFields[0] = first_Offering;
        neededFields[1] = second_Offering;
        neededFields[2] = spec_Offering;
        neededFields[3] = other_Offering;
        double[] nums = new double[4];
        int i = 0;
        for (TextField txtValues : neededFields) {
            if (!txtValues.getText().isEmpty() || !txtValues.getText().matches("( [ 0-9 ]+(\\.[0-9] +) ?)+")) {
                nums[i] = Double.parseDouble(txtValues.getText());
                i++;
                txtValues.setStyle("-fx-background-color: ");
            } else {
                txtValues.setStyle("-fx-background-color: #ff0000");
                txtValues.setText("0");
                return;
            }
        }

        seman_Treas_total_offering = nums[0] + nums[1] + nums[2] + nums[3];

        // total AttToday seman_nums_Today
        //calculating first offering rate relative to total attendace
        seman_First_Off_Per = ((nums[0] / seman_Treas_total_offering) * seman_nums_Today);
        seman_Second_Off_Per = ((nums[1] / seman_Treas_total_offering) * seman_nums_Today);
        seman_Spec_Off_Per = ((nums[2] / seman_Treas_total_offering) * seman_nums_Today);
        seman_Oth_Off_Per = ((nums[3] / seman_Treas_total_offering) * seman_nums_Today);
    }

    static boolean isTextFieldTypeNumber(TextField txtValues) {
        boolean b = false;
        if (txtValues.getText().matches("([0-9]+(\\.[0-9]+)?)+")) {
            b = true;
        }
        return b;
    }

    public static String returneDate() {
        Calendar my = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, YYYY");

        Date dd = my.getTime();

        String ddr = sdf.format(dd);

        return ddr;

    }

    static boolean checkVoidPasswordField(JFXPasswordField... allTextField) {
        boolean hasEmpty;
        int count = 0;
        for (JFXPasswordField object : allTextField) {
            if (object.getText().isEmpty()) {
                count++;
                System.out.println("The textfield " + object.getId() + " is empty");
                object.setStyle("-fx-background-color: #efa7a7");

            } else {
                object.setStyle("-fx-background-color: none");
            }
        }
        if (count != 0) {
//            notificator.setText("Notification: ");
//            notificator.setText(notificator.getText() + "Empty Textfields Please make sure all textfields are field, Check and try again " + count + " empty text fields OK");
            hasEmpty = true;
        } else {
            hasEmpty = false;
        }

        return hasEmpty;
    }

    public Task continues_Opd_check() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for (;;) {
                    Thread.sleep(30000);
                }
            }
        };// list_incoming_op.getItems().addAll(FXCollections.observableArrayList(rs.getString("patient_num")));   
    }

    public static Timestamp nowStamp(Date now) {

        Calendar my = Calendar.getInstance();
        now = my.getTime();
        opd_patient_time_in = new Timestamp(now.getTime());

        return opd_patient_time_in;

    }

    public static Label setNotificator(Label notifi, String message) {
        notifi.setText("");
        notifi.setText("Notification : " + message);
        return notifi;
    }

    public static Alert normal_alert() {
        return new Alert(Alert.AlertType.NONE);
    }
    static String sql = "";

    //specifically generated for the Doctors_NewController() // prompting to release,test, or detain patients
    public static String set_alert_dop(String doctor_id, String opd_id, String on_date) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choose Operation");
        alert.setHeaderText("Select standard procedure below");

        //list of standard procedures
        JFXComboBox cbo_stp_ = new JFXComboBox(FXCollections.observableArrayList("Release Patient", "Detain Patient", "Test Patient", "Refer Patient"));
        cbo_stp_.setPrefWidth(250);

        //GridPane to hold all components
        GridPane g_pane = new GridPane();
        g_pane.add(cbo_stp_, 0, 0);

        alert.getDialogPane().setContent(g_pane);

        boolean worker = false;
        while (worker == false) {//false is being tested to keep the method in a loop so far as standandard procedure is not selected
            Optional<ButtonType> results = alert.showAndWait();
            if (results.get() == ButtonType.OK) {

                switch (cbo_stp_.getSelectionModel().getSelectedIndex()) {
                    case 0:
                        sql = "update doctors_on_patient set status = '" + "RP" + "' where opd_id = '" + opd_id + "'  and on_date = '" + on_date + "'";
                        break;
                    case 1:
                        sql = "update doctors_on_patient set status = '" + "DP" + "' where opd_id = '" + opd_id + "'  and on_date = '" + on_date + "'";
                        break;
                    case 2:
                        sql = "update doctors_on_patient set status = '" + "TP" + "' where opd_id = '" + opd_id + "'  and on_date = '" + on_date + "'";
                        break;
                    case 3:
                        sql = "update doctors_on_patient set status = '" + "Refer" + "' where opd_id = '" + opd_id + "'  and on_date = '" + on_date + "'";
                        break;
                }

                worker = true;
            } else {
                worker = false;
            }
        }
        return sql;
    }

    //specifically generated for the Doctors_NewController() // prompting to release,test, or detain patients
    public static String setStandardProcedure_lab_or_release_Only(String doctor_id, String opd_id, String on_date) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choose Operation");
        alert.setHeaderText("Select standard procedure below");

        //list of standard procedures
        JFXComboBox cbo_stp_ = new JFXComboBox(FXCollections.observableArrayList("Test Patient", "Release Patient"));
        cbo_stp_.setPrefWidth(250);

        //GridPane to hold all components
        GridPane g_pane = new GridPane();
        g_pane.add(cbo_stp_, 0, 0);

        alert.getDialogPane().setContent(g_pane);

        boolean worker = false;
        while (worker == false) {//false is being tested to keep the method in a loop so far as standandard procedure is not selected
            Optional<ButtonType> results = alert.showAndWait();
            if (results.get() == ButtonType.OK) {

                switch (cbo_stp_.getSelectionModel().getSelectedIndex()) {
                    case 0:
                        sql = "update doctors_on_patient set status = '" + "TP" + "' where opd_id = '" + opd_id + "'  and on_date = '" + on_date + "'";
                        break;
                    case 1:
                        sql = "update doctors_on_patient set status = '" + "RP" + "' where opd_id = '" + opd_id + "'  and on_date = '" + on_date + "'";
                        break;
                }

                worker = true;
            } else {
                worker = false;
            }
        }
        return sql;
    }

    //specifically generated for the Doctors_NewController() // prompting to release,test, or detain patients
    public static String setStandardProcedure_testOnly(String doctor_id, String opd_id, String on_date) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choose Operation");
        alert.setHeaderText("This operation will send the patient to the laboratory");

        //list of standard procedures
        JFXComboBox cbo_stp_ = new JFXComboBox(FXCollections.observableArrayList("Test Patient"));
        cbo_stp_.setPrefWidth(250);

        //GridPane to hold all components
        GridPane g_pane = new GridPane();
        g_pane.add(cbo_stp_, 0, 0);

        alert.getDialogPane().setContent(g_pane);

        boolean worker = false;
        while (worker == false) {//false is being tested to keep the method in a loop so far as standandard procedure is not selected
            Optional<ButtonType> results = alert.showAndWait();
            if (results.get() == ButtonType.OK) {

                switch (cbo_stp_.getSelectionModel().getSelectedIndex()) {
                    case 0:
                        sql = "update doctors_on_patient set status = '" + "TP" + "' where opd_id = '" + opd_id + "'  and on_date = '" + on_date + "'";
                        break;
                }

                worker = true;
            } else {
                worker = false;
            }
        }
        return sql;
    }

    public static void set_notification(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
//        alert.setHeaderText(message);
        alert.getDialogPane().setStyle("-fx-background-color:linear-gradient(lightgreen,lightblue)");
        alert.getDialogPane().setContent(new Label(message));
        alert.show();
    }

    public static int num_alph_filter(String str) {
        String dosage = str;
        char[] sing_dosage = dosage.toCharArray();
        String maker = "";

        Pattern pat = Pattern.compile("^[0-9]$");
        Matcher regexMatcher = null;

        ArrayList<Integer> num_list = new ArrayList();
        ArrayList<String> alpha_list = new ArrayList();

        for (char s : sing_dosage) {
            maker = String.valueOf(s);
            regexMatcher = pat.matcher(maker);
            if (regexMatcher.matches()) {
                num_list.add(Integer.parseInt(maker));
            } else {
                alpha_list.add(maker);
            }
        }
        //reuse alpha list
        alpha_list.clear();
        for (int i : num_list) {
            maker = String.valueOf(i);
            alpha_list.add(maker);
        }

        String[] alphas = new String[alpha_list.size()];
        int sizer = alpha_list.size();
        for (int ie = 0; ie < alphas.length; ie++) {
            alphas[ie] = alpha_list.get(ie);
        }
        maker = "";
        for (String i : alphas) {
            maker += i;
        }
        int valuer = Integer.parseInt(maker);
        return valuer;
    }

    public static int calculate_quantity(String dosage, String freguency, String duration) {

        return num_alph_filter(dosage) * num_alph_filter(freguency) * num_alph_filter(duration);
    }

    public static double num_alph_filter_DOUBLE(String str) {
        String dosage = str;
        char[] sing_dosage = dosage.toCharArray();
        String maker = "";

        Pattern pat = Pattern.compile("^[0-9]$");
        Matcher regexMatcher = null;

        ArrayList<Integer> num_list = new ArrayList();
        ArrayList<String> alpha_list = new ArrayList();

        for (char s : sing_dosage) {
            maker = String.valueOf(s);
            regexMatcher = pat.matcher(maker);
            if (regexMatcher.matches()) {
                num_list.add(Integer.parseInt(maker));
            } else {
                alpha_list.add(maker);
            }
        }
        //reuse alpha list
        alpha_list.clear();
        for (int i : num_list) {
            maker = String.valueOf(i);
            alpha_list.add(maker);
        }

        String[] alphas = new String[alpha_list.size()];
        int sizer = alpha_list.size();
        for (int ie = 0; ie < alphas.length; ie++) {
            alphas[ie] = alpha_list.get(ie);
        }
        maker = "";
        for (String i : alphas) {
            maker += i;
        }
        double valuer = Integer.parseInt(maker);
        return valuer;
    }

//    public static void check_num(KeyEvent e, TextField txt) {
//        String letter = e.getText();
//
//        Pattern pat = Pattern.compile("^[0-9]$");
//        Matcher matcher = pat.matcher(e.getText());
//        if (!matcher.matches()) {
//
//            txt.setStyle("-fx-background-color:tomato");
//            txt.setStyle("-fx-text-fill:white");
//            txt.clear();
//        } else {
//            txt.setStyle("-fx-background-color:white");
//            txt.setStyle("-fx-text-fill:black");
//            txt.setText(txt.getText() + letter);
//        }
//    }
    public static void clearTextFields(TextField... txList) {
        for (TextField tx : txList) {
            tx.clear();
        }
    }

    public static void SySoutException(Exception e) {
        System.out.println(e);
    }

    public static int get_years_from_jfxDatepicker(JFXDatePicker birth_picker) {
        int years_between = 0;
        if (!birth_picker.getEditor().getText().isEmpty()) {

            java.sql.Date dob = java.sql.Date.valueOf(birth_picker.getValue());
            //java.sql.Date today = java.sql.Date.valueOf(personal_DOR.getValue());
            Date today_ = new Date();

            long dif = today_.getTime() - dob.getTime();
            long hours = dif / (60 * 60 * 1000);
            long minutes = hours * 60;
            long seconds = minutes * 60;
            long days = hours / 24;
            long weeks = days / 7;
            long months = weeks * 4;
            years_between = (int) ((dif / 1000 / 60 / 60 / 24) / 365);

        } else {

        }
        return years_between;
    }

    public static void numbersOnly(TextField txt) {
        //[\\.]
        txt.textProperty().addListener((observable, oldValue, newValue) -> {
            //"\\d{0,7}([\\.]\\d{0,4})?"
            if (!newValue.matches("\\d{0,10}([\\-]\\d{0,9})?([\\.]\\d{0,9})?")) {
                txt.setText(oldValue);
            }
        });
    }

    public static void liveWell_opd_values(TextField txt) {
        //[\\.]
        txt.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,3}([\\.]\\d{0,2})?")) {
                txt.setText(oldValue);
            }
        });
    }

    public static void bindSliderAndTextField(JFXSlider mySlider, TextField myTextField) {
//        mySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
//            newValue.matches("\\d{0,3}([\\.]\\d{0,2})?")
//            String val = newValue.toString();//"Time: %1.3f seconds", seconds
//            myTextField.setText(String.format("%1.2f", newValue));
//
//        });
        myTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                double value = Double.parseDouble(newValue);
                mySlider.setValue(value);
            }
        });

        myTextField.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 3) {
                double value = mySlider.getValue();
                myTextField.setText(String.format("%1.2f", value));
            }
        });

    }

    public static void locking() {

    }

    public static String getSelectedStringFromJFXListView(JFXListView<String> myListView) {//for getting selected String from a jfxlistView
        String value = "";
        if (!myListView.getSelectionModel().isEmpty()) {
            int i = myListView.getSelectionModel().getSelectedIndex();
            if (myListView.getSelectionModel().isSelected(i)) {
                value = myListView.getSelectionModel().getSelectedItem();
            }
        }
        return value;
    }

    public static String listenToString_TPane(TitledPane... tPane) {

        for (TitledPane s : tPane) {
            s.expandedProperty().addListener((observable, oldValue, newValue) -> {
                if (oldValue) {
                    listened_OpS = "";
                } else {
                    listened_OpS = s.getText();
                    System.out.println(s.getText());
                }
            });
        }
        return listened_OpS;
    }

    public static void listenPositivity(TextField txt, Label toPay) {
        txt.textProperty().addListener((observable, oldValue, newValue) -> {
            double value = Double.parseDouble(newValue);
            try {
                String[] arrvaluer = toPay.getText().split("[' ']");
                double inValuer = Double.parseDouble(arrvaluer[1]);
                System.out.println(inValuer);
                if (value < inValuer) {
                    txt.setStyle("-fx-background-color:tomato");
                } else if (value == inValuer) {
                    txt.setStyle("-fx-background-color:lightblue");
                } else {
                    txt.setStyle("-fx-background-color:lightgreen");
                }
            } catch (NumberFormatException e) {
            }
        });
    }

    public static void alarm(ObservableList<String> lister, String audioPath) {
        try {
            Clip clip = AudioSystem.getClip();
            if (!lister.isEmpty()) {
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(new File(audioPath)));
                clip.start();

            } else {
                clip.stop();
            }
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException exc) {
            exc.printStackTrace(System.out);
        }
    }

//    private static final String VOICENAME  = "kevin16";
    public static void ComputerSpeechAlarm(ObservableList<String> lister, String message) {
        if (!lister.isEmpty()) {
            try {
                Voice voice;
                VoiceManager vm = VoiceManager.getInstance();
                voice = vm.getVoice("kevin16");
                voice.allocate();
                voice.speak(message);

            } catch (Exception exc) {
                exc.printStackTrace(System.out);
            }
        } else {
            //clip.stop();
        }
    }

    //do fadeout and back
    public static void doBlink_Pane(ObservableList<String> lister, Pane pane) {
        Timeline t1 = new Timeline();
        if (!lister.isEmpty()) {
            t1 = new Timeline(
                    new KeyFrame(Duration.seconds(1), e -> {
                        pane.setStyle("-fx-background-color:tomato");
                    }),
                    new KeyFrame(Duration.seconds(2), e -> {
                        pane.setStyle("-fx-background-color:#1cabab");
                    }),
                    new KeyFrame(Duration.seconds(3), e -> {
                        pane.setStyle("-fx-background-color:tomato");
                    }),
                    new KeyFrame(Duration.seconds(4), e -> {
                        pane.setStyle("-fx-background-color:#1cabab");
                    })
            );
            t1.setCycleCount(3);
            t1.play();

        } else {
            t1.stop();
        }
    }

    //do fadeout and back
    public static void doBlink_Pane(ObservableList<String> lister, HBox pane) {
        Timeline t1 = new Timeline();
        if (!lister.isEmpty()) {
            t1 = new Timeline(
                    new KeyFrame(Duration.seconds(1), e -> {
                        pane.setStyle("-fx-background-color:tomato");
                    }),
                    new KeyFrame(Duration.seconds(2), e -> {
                        pane.setStyle("-fx-background-color:lightblue");
                    }),
                    new KeyFrame(Duration.seconds(3), e -> {
                        pane.setStyle("-fx-background-color:tomato");
                    }),
                    new KeyFrame(Duration.seconds(4), e -> {
                        pane.setStyle("-fx-background-color:lightblue");
                    })
            );
            t1.setCycleCount(2);
            t1.play();

        } else {
            t1.stop();
        }
    }

    //do fadeout and back
    public static void doBlink_Pane(ObservableList<String> lister, Label pane) {
        Timeline t1 = new Timeline();
        if (!lister.isEmpty()) {
            t1 = new Timeline(
                    new KeyFrame(Duration.seconds(1), e -> {
                        pane.setStyle("-fx-background-color:tomato");
                    }),
                    new KeyFrame(Duration.seconds(2), e -> {
                        pane.setStyle("-fx-background-color:lightblue");
                    }),
                    new KeyFrame(Duration.seconds(3), e -> {
                        pane.setStyle("-fx-background-color:tomato");
                    }),
                    new KeyFrame(Duration.seconds(4), e -> {
                        pane.setStyle("-fx-background-color:lightblue");
                    })
            );
            t1.setCycleCount(2);
            t1.play();

        } else {
            t1.stop();
        }
    }

    //do fadeout and back
    public static void doBlink_Pane(ObservableList<String> lister, TitledPane pane) {
        Timeline t1 = new Timeline();
        if (!lister.isEmpty()) {
            t1 = new Timeline(
                    new KeyFrame(Duration.seconds(1), e -> {
                        pane.setStyle("-fx-background-color:tomato");
                    }),
                    new KeyFrame(Duration.seconds(2), e -> {
                        pane.setStyle("-fx-background-color:lightblue");
                    }),
                    new KeyFrame(Duration.seconds(3), e -> {
                        pane.setStyle("-fx-background-color:tomato");
                    }),
                    new KeyFrame(Duration.seconds(4), e -> {
                        pane.setStyle("-fx-background-color:lightblue");
                    })
            );
            t1.setCycleCount(2);
            t1.play();

        } else {
            t1.stop();
        }
    }

    public static Double getAverageSystolis(ObservableList<nurse_patient_tabs> npts) {
        int nums = npts.size();
        double value = 0.0;
        for (nurse_patient_tabs npt : npts) {
            value += npt.getSystolis();
        }
        return value / nums;
    }

    public static Double getAverageDystolis(ObservableList<nurse_patient_tabs> npts) {
        int nums = npts.size();
        double value = 0.0;
        for (nurse_patient_tabs npt : npts) {
            value += npt.getDystolis();
        }
        return value / nums;
    }

    public static int getAveragePulse(ObservableList<nurse_patient_tabs> npts) {
        int nums = npts.size();
        int value = 0;
        for (nurse_patient_tabs npt : npts) {
            value += npt.getPulse();
        }
        return value / nums;
    }

    public static double load_actions(Connection conn, String table, String column, String action, String whereColunm, String value) {
        sql = "select " + action + "(" + column + ") from " + table + " where " + whereColunm + " = '" + value+"'";
        double out = 0.0;
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                out = rs.getDouble(action + "(" + column + ")");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return out;
    }
}
