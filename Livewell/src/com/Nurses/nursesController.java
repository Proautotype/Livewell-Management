/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Nurses;

import com.connection.conclass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.semantics.semanticsClass;
import com.signIn.nSign_Controller;
import com.tables.nurse_patient_tabs;
import com.tables.records_history_table;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class nursesController implements Initializable {

    private TextArea COMPLAINT;
    public Task nurse_task;
    public Thread nurse_th;
    private int optNums = 0;
    private int optNums1 = 4;
    String sql = "";
    PreparedStatement pst = null;
    ResultSet rs = null;
    Connection conn = null;
    private String opd_current_selected_patient = "";
    public static String time_out, time_in = "";
    public static int available_int = 0;

    @FXML
    private TitledPane ttl_pane_Awaiting;
    @FXML
    private JFXListView<String> list_incoming_op;
    @FXML
    private JFXListView<String> list_transit;
    @FXML
    private TextField txt_weight;
    @FXML
    private TextField txt_temperature;

    @FXML
    private TextField txt_pulse;
    private JFXComboBox<String> cbo_pregnancy;
    private Label lbl_patient_info;
    private Label lbl_last_attended;
    private JFXTextArea notificator;
    @FXML
    private TextField txt_sys;
    @FXML
    private TextField txt_dys;
    @FXML
    private TableView<nurse_patient_tabs> table_patient_history;
    ObservableList<nurse_patient_tabs> to_table;

    @FXML
    private TableColumn col_nurse_id;
    @FXML
    private TableColumn col_patient_id;
    @FXML
    private TableColumn col_pat_weight;
    @FXML
    private TableColumn col_pat_tmp;
    @FXML
    private TableColumn col_pat_sys;
    @FXML
    private TableColumn col_pat_dys;
    @FXML
    private TableColumn col_pat_pulse;
    @FXML
    private TableColumn col_pat_pregn;
    @FXML
    private TableColumn col_pat_time_in;
    @FXML
    private Label lbl_welcome;
    @FXML
    private SplitPane spliter;
    @FXML
    private Label lbl_fname;
    @FXML
    private Label lbl_other_names;
    @FXML
    private Label lbl_sex;
    @FXML
    private Label lbl_age;
    @FXML
    private Label lbl_incoming_size;
    @FXML
    private Label lbl_outgoing_size;
    @FXML
    private Circle circle_con_child;

    private Pane pane_wait;
    @FXML
    private JFXListView<String> list_registered;
    @FXML
    private Label lbl_registered_size;
    @FXML
    private Pane pane_notic;
    @FXML
    private Label lbl_dob;
    @FXML
    private TextField txt_queryHistory;
    @FXML
    private TextField txt_height;

    String nurse_id = " ";
    @FXML
    private MaskerPane masker;
    @FXML
    private ComboBox<String> cbo_search_history;
    @FXML
    private JFXButton btn_dateRequester;
    @FXML
    private TableColumn<?, ?> col_height;
    @FXML
    private TextField txt_pregnancy;
    @FXML
    private PieChart attend_pie_chart;
    @FXML
    private Label lbl_row_avble;
    @FXML
    private HTMLEditor txt_complains;
    @FXML
    private Label lbl_systolis;
    @FXML
    private Label lbl_dystolis;
    @FXML
    private Label lbl_pulse;

    String avg_opd_id = "";

    public static String vpch_id = "";

    @FXML
    private TextArea txt_history;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        nurse_id = nSign_Controller.username;
        conn = conclass.livewell_localhost();

        to_table = FXCollections.observableArrayList();
        list_incoming_op.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        TextFields.bindAutoCompletion(txt_pregnancy, FXCollections.observableArrayList("YES", "NO"));

        setColsToTabel();
        prevent_charatext();
        populateSearchCbo();

        _load_nurse_details();
//        load_averages();//this timeline is set to delay the system  a while in waiting the connection to be established by the service method at initialization

        try {
            silence_Incoming_Load();
            silence_Transit_load();
            silence_load_registered();
             load_attendance_chart();
        } catch (Exception ex) {
            Logger.getLogger(nursesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void prevent_charatext() {
        semanticsClass.liveWell_opd_values(txt_dys);
        semanticsClass.liveWell_opd_values(txt_sys);
        semanticsClass.liveWell_opd_values(txt_pulse);
        semanticsClass.liveWell_opd_values(txt_temperature);
        semanticsClass.liveWell_opd_values(txt_weight);
        semanticsClass.liveWell_opd_values(txt_height);
    }

    void populateSearchCbo() {
        cbo_search_history.setItems(FXCollections.observableArrayList("All", "Today"));
        if (conn != null) {
            table_patient_history.getItems().clear();
            sql = "select * from nurses_on_patient group by on_date order by time_in";
            try {
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    cbo_search_history.getItems().add(rs.getString("on_date"));
                }
            } catch (SQLException e) {
                semanticsClass.set_notification("Error", e.getMessage());
            } finally {
                try {
                    pst.close();
                    rs.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    void _load_nurse_details() {//this method will delay a while to load this is to manage the fx thread in order to allow
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), (e) -> {
            try {
                sql = "select * from users where customId = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, nSign_Controller.username);
                rs = pst.executeQuery();
                if (rs.next()) {
                    lbl_welcome.setText(rs.getString("sname") + " " + rs.getString("fname"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(nursesController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }));
        timeline.setCycleCount(2);
        timeline.play();
    }

    void Timeline_incoming_loader() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), (e) -> {
//            incoming_record_list();
            load_transit();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    void setColsToTabel() {
        col_nurse_id.setCellValueFactory(new PropertyValueFactory<>("nurse_id"));
        col_patient_id.setCellValueFactory(new PropertyValueFactory<>("opd_id"));
        col_pat_weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        col_height.setCellValueFactory(new PropertyValueFactory<>("height"));
        col_pat_tmp.setCellValueFactory(new PropertyValueFactory<>("temperature"));
        col_pat_sys.setCellValueFactory(new PropertyValueFactory<>("systolis"));
        col_pat_dys.setCellValueFactory(new PropertyValueFactory<>("dystolis"));
        col_pat_pulse.setCellValueFactory(new PropertyValueFactory<>("pulse"));
        col_pat_pregn.setCellValueFactory(new PropertyValueFactory<>("preg"));
        col_pat_time_in.setCellValueFactory(new PropertyValueFactory<>("on_date"));
    }

    ObservableList<String> npo_obs = FXCollections.observableArrayList();
    records_history_table rht = new records_history_table();

    int count = 0;
    ObservableList<String> Incoming_Vals = FXCollections.observableArrayList();

    void silence_Incoming_Load() {
        ScheduledService<ObservableList<String>> serrive = new ScheduledService<ObservableList<String>>() {
            @Override
            protected Task<ObservableList<String>> createTask() {
                return new Task<ObservableList<String>>() {
                    @Override
                    protected ObservableList<String> call() throws Exception {
                        Incoming_Vals = FXCollections.observableArrayList();
                        try {
                            sql = "select * from opd_history "
                                    + "where "
                                    + "last_date = ? "
                                    + "and "
                                    + "on_transit = ? order by opd_out_time";
                            pst = conn.prepareStatement(sql);
                            pst.setString(1, semanticsClass.returneDate());
                            pst.setString(2, "yes");
                            rs = pst.executeQuery();

                            while (rs.next()) {
                                Incoming_Vals.add(rs.getString("opd_history.patient_num"));
                              
                            }
                        } catch (SQLException e) {
                            System.out.println("Incoming error : " + e);
                        }
                        return Incoming_Vals;
                    }
                };
            }
        };
        serrive.setDelay(Duration.ONE);
        serrive.setPeriod(Duration.seconds(10));
        serrive.setOnSucceeded((WorkerStateEvent e) -> {
            //            System.out.println(serrive.getValue());           
            list_incoming_op.setItems(serrive.getValue());
            int size = list_incoming_op.getItems().size();
            lbl_incoming_size.setText(String.valueOf(size));
        });
        serrive.start();
    }

    ObservableList<String> Transit_Vals = FXCollections.observableArrayList();

    void silence_Transit_load() {
        ScheduledService<ObservableList<String>> serriver = new ScheduledService<ObservableList<String>>() {
            @Override
            protected Task<ObservableList<String>> createTask() {
                return new Task<ObservableList<String>>() {
                    @Override
                    protected ObservableList<String> call() throws Exception {
                        Transit_Vals = FXCollections.observableArrayList();
                        try {
                            sql = "select nurses_on_patient.opd_id from nurses_on_patient "
                                    + "where nurses_on_patient.on_date = '" + semanticsClass.returneDate() + "' "
                                    + "and "
                                    + "nurses_on_patient.on_transit = 'ON' order by time_in";

                            pst = conn.prepareStatement(sql);
                            rs = pst.executeQuery();
                            if (rs.next()) {
                                Transit_Vals.add(rs.getString("nurses_on_patient.opd_id"));
//                                System.out.println(rs.getString("nurses_on_patient.opd_id"));
                            } else {
                                System.out.println("Transit error : " + "no registered value");
                            }
                        } catch (SQLException e) {
                            System.out.println("Transit error : " + e);
                        }
                        return Transit_Vals;
                    }
                };
            }
        };
        serriver.setDelay(Duration.seconds(2));
        serriver.setPeriod(Duration.seconds(30));
        serriver.setOnSucceeded((WorkerStateEvent e) -> {
            //            System.out.println(serrive.getValue());           
            list_transit.setItems(serriver.getValue());
            int size = list_transit.getItems().size();
            lbl_outgoing_size.setText(String.valueOf(size));
        });
        serriver.start();
    }
    ObservableList<String> Registered_Vals = FXCollections.observableArrayList();

    void silence_load_registered() {
        ScheduledService<ObservableList<String>> serriver = new ScheduledService<ObservableList<String>>() {
            @Override
            protected Task<ObservableList<String>> createTask() {
                return new Task<ObservableList<String>>() {
                    @Override
                    protected ObservableList<String> call() throws Exception {
                        Registered_Vals = FXCollections.observableArrayList();
                        try {
                            sql = "select nurses_on_patient.opd_id from nurses_on_patient "
                                    + "where "
                                    + "nurses_on_patient.on_date = '" + semanticsClass.returneDate() + "'  "
                                    + "and "
                                    + "nurses_on_patient.registered = 'yes'  order by time_in";

                            pst = conn.prepareStatement(sql);
                            rs = pst.executeQuery();
                            while (rs.next()) {
                                Registered_Vals.add(rs.getString("nurses_on_patient.opd_id"));
//                                System.out.println(rs.getString("nurses_on_patient.opd_id"));
                            } 
                        } catch (SQLException e) {
                            System.out.println("registered error : " + e);
                        }
                        return Registered_Vals;
                    }
                };
            }
        };
        serriver.setDelay(Duration.seconds(3));
        serriver.setPeriod(Duration.seconds(20));
        serriver.setOnSucceeded((WorkerStateEvent e) -> {
            //            System.out.println(serrive.getValue());           
            list_registered.setItems(serriver.getValue());
            int size = list_registered.getItems().size();
            lbl_registered_size.setText(String.valueOf(size));
        });
        serriver.start();
    }

    @FXML
    void current_patient_select(KeyEvent event) {
        if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
//            opd_current_selected_patient = list_incoming_op.getSelectionModel().getSelectedItem();
            vpch_id = opd_current_selected_patient;
            time_in = Time.from(Instant.now()).toString();//this feeds the save method 
            sql = "select surname,other_names,SEX,_AGE,_dob from patient_info where registration_no = ?";
            ResultSet inRs = null;
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, opd_current_selected_patient);
                inRs = pst.executeQuery();
                if (inRs.next()) {
                    lbl_fname.setText(inRs.getString("surname"));
                    lbl_other_names.setText(inRs.getString("other_names"));
                    lbl_sex.setText(inRs.getString("sex"));
                    lbl_dob.setText(inRs.getString("_dob"));
                    lbl_age.setText(inRs.getString("_age"));
                    if (lbl_sex.getText().contentEquals("male".toUpperCase())) {
                        cbo_pregnancy.setValue("NO");
                        cbo_pregnancy.setItems(FXCollections.observableArrayList("NO"));
                    } else {
                        cbo_pregnancy.setItems(FXCollections.observableArrayList("YES", "NO"));
                    }

                    txt_dys.setText("");
                    txt_pulse.setText("");
                    txt_sys.setText("");
                    txt_temperature.setText("");
                    txt_weight.setText("");
                    txt_height.setText("");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }

        }
    }

    @FXML
    void current_patient_select_MOUSE(MouseEvent event) {
        opd_current_selected_patient = list_incoming_op.getSelectionModel().getSelectedItem();
        time_in = Time.from(Instant.now()).toString();//here the time 
        sql = "select surname,other_names,SEX,_AGE,_dob from patient_info where registration_no = ?";
        ResultSet inRs = null;
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, opd_current_selected_patient);
            inRs = pst.executeQuery();
            if (inRs.next()) {
                lbl_fname.setText(inRs.getString("surname"));
                lbl_other_names.setText(inRs.getString("other_names"));
                lbl_sex.setText(inRs.getString("sex"));
                lbl_dob.setText(inRs.getString("_dob"));
                lbl_age.setText(inRs.getString("_age"));

                if (lbl_sex.getText().contentEquals("male".toUpperCase())) {
                    txt_pregnancy.setText("NO");
                    TextFields.bindAutoCompletion(txt_pregnancy, FXCollections.observableArrayList("NO"));
                } else {
                    TextFields.bindAutoCompletion(txt_pregnancy, FXCollections.observableArrayList("YES", "NO"));
                }

                txt_dys.setText("");
                txt_pulse.setText("");
                txt_sys.setText("");
                txt_temperature.setText("");
                txt_weight.setText("");
                txt_height.setText("");
                txt_complains.setHtmlText(" <html><head><head/><body><p><p/><body/></html> ");
                txt_history.setText("");

                pst.close();
                rs.close();

                sql = "select height from nurses_on_patient where opd_id = '" + opd_current_selected_patient + "' ";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                if (rs.last()) {
                    txt_height.setText(rs.getString("height"));
                }
                load_to_table(opd_current_selected_patient);
                averages(opd_current_selected_patient);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
            }
        }

    }

    @FXML
    void attend_patient() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Press ok to continue");
        alert.setTitle("Confirm");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            sql = "insert into nurses_on_patient "
                    + "(nurse_id,opd_id,weight,height,temperature,systolis,dystolis,pulse,preg,time_in,on_date,on_transit,registered,complains,history) "
                    + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            try {
                conn = conclass.livewell_localhost();
                pst = conn.prepareStatement(sql);
                pst.setString(1, nurse_id);
                pst.setString(2, opd_current_selected_patient);
                pst.setDouble(3, Double.parseDouble(txt_weight.getText()));
                pst.setDouble(4, Double.parseDouble(txt_height.getText()));
                pst.setDouble(5, Double.parseDouble(txt_temperature.getText()));
                pst.setDouble(6, Integer.parseInt(txt_sys.getText()));
                pst.setDouble(7, Integer.parseInt(txt_dys.getText()));
                pst.setDouble(8, Integer.parseInt(txt_pulse.getText()));
                pst.setString(9, txt_pregnancy.getText().trim());
                pst.setTimestamp(10, semanticsClass.nowStamp(new Date()));
                pst.setString(11, semanticsClass.returneDate());
                pst.setString(12, "ON"); // this line is to say the patient is on transit to go consultation
                pst.setString(13, "YES");//this line is to say that the patient is registered
                pst.setString(14, txt_complains.getHtmlText());
                pst.setString(15, txt_history.getText());
                pst.execute();
                load_attendance_chart();
                averages(avg_opd_id);
            } catch (NumberFormatException | SQLException e) {
                semanticsClass.set_notification("Error", e.getMessage());
            } finally {
                try {
                    pst.close();
                    rs.close();

                    updating_opd_transit();
                    load_transit();
                    load_registerd();
                    System.out.println();//this is to release pressure on the pst and conn variables
//                    incoming_record_list();
                } catch (Exception e) {
                }
            }
        }
    }

    //UPDATING SELECTED PATIENT TRANSIT TO ON
    void updating_opd_transit() {
        sql = "update opd_history set on_transit = 'OFF' where opd_history.patient_num = '" + opd_current_selected_patient + "' and last_date = '" + semanticsClass.returneDate() + "' ";
        try {
            conn = conclass.livewell_localhost();
            pst = conn.prepareStatement(sql);
            pst.execute();

        } catch (SQLException e) {
            notificator.setText(e.getMessage());
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException e) {

            }
        }
    }

//SELECTED PATIENTS TO DOCTOR
    void load_transit() {
        if (!(conn == null)) {
            sql = "select nurses_on_patient.opd_id from nurses_on_patient where nurses_on_patient.on_date = '" + semanticsClass.returneDate() + "' and nurses_on_patient.on_transit = 'ON' order by time_in";
            try {
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                list_transit.getItems().removeAll(list_transit.getItems());
                while (rs.next()) {
                    list_transit.getItems().addAll(FXCollections.observableArrayList(rs.getString("nurses_on_patient.opd_id")));
                }
                int size = +list_transit.getItems().size();
                lbl_outgoing_size.setText(String.valueOf(size));
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    //get all patients that are rolled to the consulting
    void load_registerd() {
        if (!(conn == null)) {
            sql = "select nurses_on_patient.opd_id from nurses_on_patient where nurses_on_patient.on_date = '" + semanticsClass.returneDate() + "'  and nurses_on_patient.registered = 'yes'  order by time_in";
            //select count(sex) from patient_info,opd_history where patient_info.registration_no = opd_history.patient_num and patient_info.sex = 'female' and opd_history.last_date = ? 
            try {
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                list_registered.getItems().removeAll(list_registered.getItems());
                while (rs.next()) {
                    // list_incoming_op.getItems().addAll(FXCollections.observableArrayList(rs.getString("patient_num")));
                    list_registered.getItems().addAll(FXCollections.observableArrayList(rs.getString("nurses_on_patient.opd_id")));
                }
                int size = +list_registered.getItems().size();
                // pane_wait.setVisible(false);
                lbl_registered_size.setText(String.valueOf(size));
            } catch (SQLException e) {
                //notificator.setText(e.getMessage());
            }
        }
    }

    private void update_nurse_patient_timeOut() {

    }

    void searchFree() {
        txt_queryHistory.textProperty().addListener((observable, oldValue, newValue) -> {
            load_to_table(newValue.trim());
        });
    }

    void load_to_table(String lister) {
        table_patient_history.getItems().clear();
        sql = "select * from nurses_on_patient where opd_id like '%" + lister + "%' order by time_in";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                to_table.add(new nurse_patient_tabs(rs.getString("nurse_id"), rs.getString("opd_id"), rs.getDouble("weight"), rs.getDouble("height"), rs.getDouble("temperature"), rs.getInt("systolis"), rs.getInt("dystolis"), rs.getInt("pulse"), rs.getString("preg"), rs.getString("on_date")));
            }

            table_patient_history.setItems(to_table);
            lbl_row_avble.setText(table_patient_history.getItems().size() + "");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException e) {
            }

        }
    }

    Double temp_value;

    @FXML
    private void list_transit_mouse_click(MouseEvent event) {
        conn = conclass.livewell_localhost();
        avg_opd_id = list_transit.getSelectionModel().getSelectedItem();
        time_in = Time.from(Instant.now()).toString();
        sql = "select surname,other_names,_age,sex,_dob from patient_info where registration_no = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, avg_opd_id);
            rs = pst.executeQuery();
            if (rs.next()) {
                lbl_fname.setText(rs.getString("surname"));
                lbl_other_names.setText(rs.getString("other_names"));
                lbl_sex.setText(rs.getString("sex"));
                lbl_dob.setText(rs.getString("_dob"));
                lbl_age.setText(rs.getString("_age"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        load_to_table(avg_opd_id);
        averages(avg_opd_id);
    }

    @FXML
    private void list_REGISTERD_mouse_click(MouseEvent event) {
        conn = conclass.livewell_localhost();
        avg_opd_id = list_registered.getSelectionModel().getSelectedItem();
        time_in = Time.from(Instant.now()).toString();
        sql = "select surname,other_names,_age,sex,_dob from patient_info where registration_no = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, avg_opd_id);
            rs = pst.executeQuery();
            if (rs.next()) {
                lbl_fname.setText(rs.getString("surname"));
                lbl_other_names.setText(rs.getString("other_names"));
                lbl_sex.setText(rs.getString("sex"));
                lbl_dob.setText(rs.getString("_dob"));
                lbl_age.setText(rs.getString("_age"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        load_to_table(avg_opd_id);
        averages(avg_opd_id);
    }

    void constantNetworkCheck(Connection connect) {
        ScheduledService<Connection> service = new ScheduledService<Connection>() {
            @Override
            protected Task<Connection> createTask() {
                return new Task<Connection>() {
                    @Override
                    protected Connection call() throws Exception {
                        if (connect == null) {
                            updateMessage("Connection lost, trying to reconnect");
                            updateValue(conclass.livewell_localhost());
                        }
                        return connect;
                    }
                };
            }
        };
        service.setOnRunning(e -> {
        });
        service.setOnSucceeded((WorkerStateEvent e) -> {
            if (service.getValue() != null) {
                conn = service.getLastValue();
                try {
                    System.out.println(conn.getMetaData());
                } catch (Exception e3) {
                }

                masker.setVisible(false);
            } else {
                masker.setVisible(true);
            }
        });
        service.start();
    }

    void load_to_table_byDate(String lister) {
        if (lister.contentEquals("All")) {
            sql = "select * from nurses_on_patient order by time_in";
        } else if (lister.contentEquals("Today")) {
            sql = "select * from nurses_on_patient where on_date = '" + semanticsClass.returneDate() + "' order by time_in";
        } else {
            sql = "select * from nurses_on_patient where on_date = '" + lister + "' order by time_in";
        }

        table_patient_history.getItems().clear();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                to_table.add(new nurse_patient_tabs(rs.getString("nurse_id"), rs.getString("opd_id"), rs.getDouble("weight"), rs.getDouble("height"), rs.getDouble("temperature"), rs.getInt("systolis"), rs.getInt("dystolis"), rs.getInt("pulse"), rs.getString("preg"), rs.getString("on_date")));
            }

            table_patient_history.setItems(to_table);
            lbl_row_avble.setText(table_patient_history.getItems().size() + "");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException e) {
            }

        }
    }

    @FXML
    private void cbo_dateSearch(ActionEvent event) {
        if (!cbo_search_history.getSelectionModel().isEmpty() && conn != null) {
            String searchThis = cbo_search_history.getSelectionModel().getSelectedItem();
            load_to_table_byDate(searchThis);
        }
    }

    @FXML
    private void doDateRequest(ActionEvent event) {
        populateSearchCbo();
    }

    void load_attendance_chart() {
        int females = 0;
        int males = 0;
        if (conn != null) {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            try {

                sql = "select count(sex) from patient_info,nurses_on_patient where patient_info.registration_no = nurses_on_patient.opd_id and patient_info.sex = 'female' and nurses_on_patient.on_date = ? ";
                pst = conn.prepareStatement(sql);
                pst.setString(1, semanticsClass.returneDate());
                rs = pst.executeQuery();
                if (rs.next()) {
                    females = Integer.parseInt(rs.getString("count(sex)"));
                }
            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                try {
                    rs.close();
                    pst.close();
                } catch (SQLException e) {
                }
            }

            //number of males
            try {

                sql = "select count(sex) from patient_info,nurses_on_patient where patient_info.registration_no = nurses_on_patient.opd_id and patient_info.sex = 'male' and nurses_on_patient.on_date = ? ";
                pst = conn.prepareStatement(sql);
                pst.setString(1, semanticsClass.returneDate());
                rs = pst.executeQuery();
                if (rs.next()) {
                    males = Integer.parseInt(rs.getString("count(sex)"));
                }
                attend_pie_chart.setData(pieChartData);
            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                try {
                    rs.close();
                    pst.close();
                } catch (SQLException e) {
                }
            }

            pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Females " + females, females),
                    new PieChart.Data("Males " + males, males)
            );

            attend_pie_chart.setData(null);
            attend_pie_chart.setData(pieChartData);

        }
    }

    @FXML
    private void table_mouse_clicked(MouseEvent event) {
        if (!table_patient_history.getSelectionModel().isEmpty()) {
            nurse_patient_tabs npt = table_patient_history.getSelectionModel().getSelectedItem();
            avg_opd_id = npt.getOpd_id();
            String on_date = npt.getOn_date();

            sql = "select * from nurses_on_patient where opd_id = '" + avg_opd_id + "' and on_date = '" + on_date + "'  ";
            try {
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                if (rs.next()) {
                    rs.getString("complains");
                    if (!rs.wasNull()) {
                        txt_complains.setHtmlText(rs.getString("complains"));
                    } else {
                        txt_complains.setHtmlText("<html><head></head><body>");
                    }

                    txt_weight.setText(String.valueOf(rs.getDouble("weight")));
                    txt_height.setText(String.valueOf(rs.getDouble("height")));
                    txt_temperature.setText(String.valueOf(rs.getDouble("temperature")));

                    txt_sys.setText(String.valueOf(rs.getDouble("systolis")));
                    txt_dys.setText(String.valueOf(rs.getDouble("dystolis")));
                    txt_pulse.setText(String.valueOf(rs.getInt("pulse")));
                    txt_pregnancy.setText(rs.getString("preg"));
                    averages(avg_opd_id);
//                    txt_complains.setHtmlText(rs.getString("co"));
                }
            } catch (SQLException e) {
            }
        }
    }

    public void load_averages() {
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        load_transit();
                        load_registerd();
                        Timeline_incoming_loader(); //THIS METHOD IS CONTAINS AN INDEFINATE TIMELINE FOR CHECKING INCOMING ENTRY  
                        searchFree();
                        return true;
                    }
                };
            }
        };
        service.start();
    }

    void averages(String valuer) {
        String med_valuer = String.format("%1.2f", semanticsClass.load_actions(conn, "nurses_on_patient", "systolis", "avg", "opd_id", valuer));
        lbl_systolis.setText(med_valuer);
        med_valuer = String.format("%1.2f", semanticsClass.load_actions(conn, "nurses_on_patient", "dystolis", "avg", "opd_id", valuer));
        lbl_dystolis.setText(med_valuer);
        med_valuer = String.format("%1.2f", semanticsClass.load_actions(conn, "nurses_on_patient", "pulse", "avg", "opd_id", valuer));
        lbl_pulse.setText(med_valuer);
    }

    private void open_vpch(ActionEvent event) {
        try {
            //  System.out.println(hm.get(CWD));
            Parent root = FXMLLoader.load(getClass().getResource("vlch.fxml"));
            // System.out.println("Loaded");
            Scene scene = new Scene(root);
            // System.out.println("scene ready");
            Stage stage = new Stage();
            // System.out.println("stage set");
            stage.setScene(scene);
            // System.out.println("scene set to stage");
            stage.show();
            //   System.out.println("stage should be showin");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
