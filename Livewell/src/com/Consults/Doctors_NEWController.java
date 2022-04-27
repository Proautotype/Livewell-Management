/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Consults;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.connection.conclass;
import static com.semantics.semanticsClass.SySoutException;
import com.jfoenix.controls.JFXButton;
import com.semantics.semanticsClass;
import static com.signIn.SignInController.username;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.Optional;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.ListSelectionView;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.TextFields;
import com.tables.prescription_table;
import AES.random;
import com.jfoenix.controls.JFXProgressBar;
import com.services.report_services;
import com.signIn.nSign_Controller;
import com.sms.twilio_services;
import com.tables.doctor_reviews;
import java.time.LocalDate;
import java.util.HashMap;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.web.HTMLEditor;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class Doctors_NEWController implements Initializable {

    @FXML
    private JFXListView<String> list_incoming_Patients;
    @FXML
    private Label lbl_name;
    @FXML
    private Label lbl_age;
    @FXML
    private Label lbl_sex;
    @FXML
    private Label lbl_temp;
    @FXML
    private Label lbl_weight;

    ObservableList lister = null;

    @FXML
    private TextArea txt_history;
    @FXML
    private HTMLEditor txt_complaint;
    private TextArea txt_prescription;
    @FXML
    private Label lbl_systolis;
    @FXML
    private Label lbl_dystolis;
    @FXML
    private Label lbl_pulse;

    public static Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = "";

    String incoming_value;
    public static String _in_RP_id;
    public static String _name;
    public static String home_presc_id;
    public static String _doctors_id;

    public Task doc_task;
    public Thread doc_th;

    private ListSelectionView<String> list_prescription;
    String random_value = "";
    @FXML
    private JFXButton btn_prescribe;
    private JFXButton btn_un_prescribe;
    @FXML
    private JFXButton btn_detach;
    @FXML
    private JFXButton btn_commit;
    @FXML
    private JFXListView<String> list_lab;
    @FXML
    private JFXListView<String> list_refered;
    @FXML
    private JFXListView<String> list_released;
    @FXML
    private JFXListView<String> list_unAttended;
    @FXML
    private Label root_lbl_notificator;
    @FXML
    private Pane pane_presc_ctrl;
    private JFXButton btn_view_prescription;
    @FXML
    private JFXListView<Label> list_diagnostics;
    @FXML
    private JFXListView<String> list_returnees;
    @FXML
    private TextField txt_diagnostic_input;
    @FXML
    private TextField txt_drug_src_input;

    TextArea input = new TextArea();//this is instantiated at every anytime a diagnostic is attached to diagnosis

    TextField _input = new TextField();//this text is used in editing prescription of selected patient
    JFXComboBox<String> cbo_input = new JFXComboBox<>();//this  combobox is used in editing prescription of selected patient
    Label diag_item = new Label();
    Notifications notePane;
    @FXML
    private TextField txt_presc_amount;
    @FXML
    private TextField txt_presc_dosage;
    private TextField txt_presc_freque;
    private TextField txt_presc_duration;
    @FXML
    private JFXListView<String> list_detained;
    @FXML
    private JFXButton r_btn_commit;

    public static PopOver popper;
    @FXML
    private Label lbl_O_names;
    @FXML
    private MaskerPane masker;
    @FXML
    private Accordion semi_root;
    @FXML
    private TitledPane _semi_root_ch_1;
    @FXML
    private TitledPane _semi_root_ch_2;
    @FXML
    private TableView<prescription_table> table_of_presc;
    ObservableList<prescription_table> rows_of_prescription;
    @FXML
    private TableColumn col_drug;
    @FXML
    private TableColumn col_dosage;
    private TableColumn col_freg;
    private TableColumn col_duration;
    @FXML
    private TableColumn col_amount;
    @FXML
    private JFXButton btn_attach;
    @FXML
    private TitledPane refered_pane;
    @FXML
    private TextField txt_drug_code;
    @FXML
    private TextField txt_drug_type;
    @FXML
    private TextField txt_all_drug_part;
    @FXML
    private TableColumn<?, ?> col_drug_code;
    @FXML
    private TableColumn<?, ?> col_drug_type;
    @FXML
    private MaskerPane masker_lab_re;
    private JFXListView<doctor_reviews> review_lister;
    @FXML
    private JFXProgressBar review_progress_bar;
    /////////////////
    public static String review_pid;
    public static String review_onDate;
    ////////////////   
    @FXML
    private MaskerPane review_masker;
    @FXML
    private TableView<doctor_reviews> table_review;
    @FXML
    private TableColumn<?, ?> col_review_pid;
    @FXML
    private TableColumn<?, ?> col_review_date;
    @FXML
    private TableColumn<?, ?> col_review_time;
    @FXML
    private TableColumn<?, ?> col_review_note;
    @FXML
    private TableColumn<?, ?> col_review_onDate;
    @FXML
    private ComboBox<java.sql.Date> cbo_review_filter_date;
    @FXML
    private Label lbl_dob;
    @FXML
    private TitledPane tPane_incoming;
    @FXML
    private HBox hbox_bottom;
    @FXML
    private JFXButton btn_held;
    @FXML
    private JFXListView<String> list_held;
    @FXML
    private Label lbl_held_pid;
    @FXML
    private MaskerPane mask_conn_refreshn;
    private Button btn_conn_refresh;
    @FXML
    private Label lbl_height;

    public static String composeThis = "";  // this value is populate by the drug which is not available in the current presc_drug table from which all drugs to be prescribed are put
    @FXML
    private ComboBox<String> cbo_enrollment;
    @FXML
    private JFXListView<String> list_enrollment;
    @FXML
    private Label lbl_contact;
    @FXML
    private Label lbl_location;
    @FXML
    private Label lbl_occupation;
    @FXML
    private Label lbl_marital_status;
    @FXML
    private Label lbl_pregnancy;
    Timeline timeline_loader = new Timeline();
    @FXML
    private JFXListView<String> list_personal_history;
    @FXML
    private MaskerPane masker_personalHistory;

    HashMap<String, String> pH_hashMap; //personalHistory hashMap

    String pid_history = "";
    @FXML
    private JFXButton btn_list_lab;
    @FXML
    private ComboBox<?> cbo_review_filter_pid;
    @FXML
    private ComboBox<String> cbo_months;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        diag_item.setPrefHeight(30);
        diag_item.setWrapText(true);
        _doctors_id = nSign_Controller.username;
        rows_of_prescription = FXCollections.observableArrayList();
        table_settings();
        pH_hashMap = new HashMap<>();
        r_btn_commit.setDisable(true);// disable commit buttons which is used to attach diagnostic this should only be enabled when lab_returned or release button is clicked
        btn_attach.setDisable(true);// disable commit buttons which is used to attach diagnostic this should only be enabled when lab_returned or release button is clicked
        testing_holdablility();
        btn_held.setDisable(true);
        load_held();
        batch_loader();
        loadEnrollment();

        silence_Incoming_Load();
    }

    void testing_holdablility() {//
        txt_history.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !txt_complaint.getHtmlText().isEmpty()) {
                btn_held.setDisable(false);
            } else {
                btn_held.setDisable(true);
            }

        });
    }

    void table_settings() {
        col_drug_code.setCellValueFactory(new PropertyValueFactory<>("drug_cod"));
        col_drug.setCellValueFactory(new PropertyValueFactory<>("drug"));
        col_drug_type.setCellValueFactory(new PropertyValueFactory<>("drug_type"));
        col_dosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        col_amount.setCellValueFactory(new PropertyValueFactory<>("price"));

        col_review_pid.setCellValueFactory(new PropertyValueFactory<>("pid"));
        col_review_date.setCellValueFactory(new PropertyValueFactory<>("review_date"));
        col_review_time.setCellValueFactory(new PropertyValueFactory<>("review_time"));
        col_review_note.setCellValueFactory(new PropertyValueFactory<>("note"));
        col_review_onDate.setCellValueFactory(new PropertyValueFactory<>("on_date"));

        col_drug.setCellFactory(TextFieldTableCell.forTableColumn());
        col_dosage.setCellFactory(TextFieldTableCell.forTableColumn());
        col_amount.setCellFactory(TextFieldTableCell.forTableColumn());

        cbo_months.setItems(FXCollections.observableArrayList("January", "Februry", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));
    }

    @FXML
    public void changeDrugNameCellEvent(CellEditEvent edit) {
        prescription_table pt = table_of_presc.getSelectionModel().getSelectedItem();
        pt.setDrug(edit.getNewValue().toString());
    }

    @FXML
    public void changeDosageCellEvent(CellEditEvent edit) {
        prescription_table pt = table_of_presc.getSelectionModel().getSelectedItem();
        pt.setDosage(edit.getNewValue().toString());
    }

    @FXML
    public void changePriceCellEvent(CellEditEvent edit) {
        prescription_table pt = table_of_presc.getSelectionModel().getSelectedItem();
        pt.setPrice(edit.getNewValue().toString());
    }

    void loadEnrollment() {
        sql = " Select distinct on_date from doctors_on_patient order by opd_id";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            cbo_enrollment.getItems().removeAll(cbo_enrollment.getItems());
            while (rs.next()) {
                cbo_enrollment.getItems().addAll(FXCollections.observableArrayList(rs.getString("on_date")));
            }
        } catch (SQLException e) {
        }
    }

    ObservableList<String> diag_codes = FXCollections.observableArrayList();
    int batch_loader_counter = 0;

    void batch_loader() {
        java.sql.Date today = java.sql.Date.valueOf(LocalDate.now());// this date value 
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        conn = conclass.livewell_localhost();
                        if (conn != null) {
                            //TextFields.bindAutoCompletion(txt_presc_freque, FXCollections.observableArrayList("tds", "tid", "qid", "bd", "bid", "qid", "daily", "nocte"));
                            load_nhis_diagnostic_code();
                            _sharing_procedures();
                            load_all_drugs();
                            load_reviews(today);
                            load_existing_reviews_by_dates();
                        } else {
                            cancel();
                        }
                        return false;
                    }
                };
            }
        };
        service.setOnCancelled(e -> {
            if (batch_loader_counter != 3) {
                service.restart();
            }
            batch_loader_counter++;

        });
        service.start();
    }

    void load_nhis_diagnostic_code() {
        try {
            if (!(conn.isClosed() || conn == null)) {
                sql = "select * from diagnosis_nhis_codes";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();

                while (rs.next()) {
                    diag_codes.add(rs.getString("diagnosis") + " :: " + rs.getString("nhis_code"));
                }
                TextFields.bindAutoCompletion(txt_diagnostic_input, diag_codes).setPrefWidth(txt_diagnostic_input.getPrefWidth());
            }
        } catch (SQLException e) {

        }
    }

    private final ObservableList<String> incoming_list = FXCollections.observableArrayList();

    void Timeline_incoming_loader() {
        //this is put here to refresh connection thirty seconds
        timeline_loader = new Timeline(new KeyFrame(Duration.seconds(10), (e) -> {
            load_incoming();
            load_held();
            _sharing_procedures();
            lab_returnees();
        }));
        timeline_loader.setCycleCount(Animation.INDEFINITE);
        timeline_loader.play();
    }

    ObservableList<String> all_prescription = FXCollections.observableArrayList();
    ObservableList<String> all_diagnosis = FXCollections.observableArrayList();

    ObservableList<String> drug_code = FXCollections.observableArrayList();
    ObservableList<String> drug_type = FXCollections.observableArrayList();
    ObservableList<String> drug_name = FXCollections.observableArrayList();

    //loading all necessary lists in components
    void load_all_drugs() {

        drug_code.clear();
        sql = "Select * from presc_drugs";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            all_prescription = FXCollections.observableArrayList();
            while (rs.next()) {
                drug_code.add(rs.getString("code") + " ~ " + rs.getString("drug_name") + " ~ " + rs.getString("drug_type") + " ~ " + rs.getString("price"));
            }
            cbo_input.setItems(all_prescription);
            _input.setPrefColumnCount(20);

            TextFields.bindAutoCompletion(txt_all_drug_part, drug_code).addEventHandler(EventType.ROOT, e -> {
                //semanticsClass.set_notification("Drug code", txt_drug_code.getText());
                String[] drugcode = txt_all_drug_part.getText().split("[~]");
                txt_all_drug_part.setText("");
                txt_drug_code.setText(drugcode[0].trim());
                //the drugcode[1] is further splitted by [,] seperating drug from dosage
                //  String[] reSplit_drug = drugcode[1].trim().split("[~]");
                txt_drug_src_input.setText(drugcode[1].trim());

                // txt_presc_dosage.setText(reSplit_drug[1]);
                txt_drug_type.setText(drugcode[2].trim());
                txt_presc_amount.setText(drugcode[3].trim());
            });

//////            TextFields.bindAutoCompletion(_input, all_prescription).setPrefWidth(txt_drug_src_input.getPrefWidth() + 100.0);
        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    @FXML
    private void do_save(ActionEvent event) {
        timeline_loader.pause();
        if (!r_btn_commit.getText().equals("EDIT")) {
            //semanticsClass.set_notification("Alert", New_connection_panelController.passing_id + " :=:::=: " + _doctors_id);
            if (!(selected_held_pid.isEmpty())) {
                if (!(list_diagnostics.getItems().isEmpty()) && !(txt_complaint.getHtmlText().trim().isEmpty() && txt_history.getText().trim().isEmpty() && _doctors_id.isEmpty())) {
                    create_diagnostic_home();// random_value is generated here
                    create_batch_diagnosis(list_diagnostics); // random_value exist here as well
                    boolean worker = false;
                    try {
                        sql = "update doctors_on_patient set diagnostic = '" + random_value + "' "
                                + "where on_date = '" + semanticsClass.returneDate() + "' and opd_id = ? ";//diagnostic

                        pst = conn.prepareStatement(sql);
                        pst.setString(1, selected_held_pid);
                        pst.execute();
                        //THE LINE BELOW IS COMMENTED AS THE DOCTOR MIGHT TAKE A DECISION TO EITHER REFER,TEST,DETAIN,OR RELEASE THE PATIENT
                        /* IT IS WHEN THE DOCTOR MADE A DECISION BEFORE PRESCRIPTION CAN BE ATTACHED TO THE 
                        PATIENT SESSION*/
                        worker = true;
                        if (worker) {
                            pst.close();
                            rs.close();
                            selected_held_pid = "";
                        }
                        rm_worked_patient(incoming_value);//removes patient from the incoming list
//                        load_incoming();

                    } catch (SQLException e) {
                        semanticsClass.set_notification("Error", e.getMessage());
                    }
                } else {
                    semanticsClass.set_notification("Error", "No History, Complaint or Diagnosis available \nPlease Provide them before COMMIT");
                }
            } else {
                semanticsClass.set_notification("Info", "Please select a patient from the held list, at the bottom left,\n or drag and drop patient from the lab return list to the blue bar on top");
            }
        } else {
            try {
                VBox vbox = FXMLLoader.load(getClass().getResource("/com/Consults/diagnostic_update_panel.fxml"));
                popper = new PopOver(vbox);
                popper.show(btn_commit);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        timeline_loader.play();
    }
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
                            sql = "select nurses_on_patient.opd_id from nurses_on_patient where nurses_on_patient.on_date = '" + semanticsClass.returneDate() + "'  and nurses_on_patient.on_transit = 'ON' and nurses_on_patient.registered = 'yes'  order by time_in";

                            pst = conn.prepareStatement(sql);
                            rs = pst.executeQuery();

                            while (rs.next()) {
                                Incoming_Vals.add(rs.getString("nurses_on_patient.opd_id"));
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
            list_incoming_Patients.setItems(serrive.getValue());
            semanticsClass.doBlink_Pane(list_incoming_Patients.getItems(), hbox_bottom);
            lab_returnees();
        });
        serrive.start();
    }

    void load_incoming() {
        sql = "select nurses_on_patient.opd_id from nurses_on_patient where nurses_on_patient.on_date = '" + semanticsClass.returneDate() + "'  and nurses_on_patient.on_transit = 'ON' and nurses_on_patient.registered = 'yes'  order by time_in";
        try {
            if (!(conn.isClosed() || conn == null)) {
                try {
                    pst = conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    list_incoming_Patients.getItems().removeAll(list_incoming_Patients.getItems());
                    while (rs.next()) {
                        // list_incoming_op.getItems().addAll(FXCollections.observableArrayList(rs.getString("patient_num")));
                        list_incoming_Patients.getItems().
                                addAll(FXCollections.observableArrayList(rs.getString("nurses_on_patient.opd_id")));
                    }
                    semanticsClass.doBlink_Pane(list_incoming_Patients.getItems(), hbox_bottom);
//            semanticsClass.alarm(list_incoming_Patients.getItems(), "audio/notic3.wav");
                } catch (SQLException e) {
                    //  JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        } catch (Exception e) {
        }

    }

    @FXML
    private void list_incoming_mouse_clicked(MouseEvent event
    ) {
        if (event.isShiftDown()) {

        } else {
            r_btn_commit.setText("PRESS TO COMMIT");
            btn_prescribe.setDisable(false);
            btn_attach.setDisable(true);
            btn_attach.setText("ATTACH");
            incoming_value = list_incoming_Patients.getSelectionModel().getSelectedItem();

            loadSavedDetails(incoming_value, semanticsClass.returneDate()); // get patients details today          
            load_Personal_history_list(incoming_value);//loads the list on this patients incomings           
            list_diagnostics.getItems().clear();
            table_of_presc.getItems().clear();
            btn_held.setText("Hold");
            pid_history = incoming_value;
        }
    }
    //THIS METHOD ADDS A SINGLE ITEM TO LIST OF PRESCRIPTION

    private void add_prescrip(ActionEvent event) {
        if (!txt_drug_src_input.getText().isEmpty()) {
            //String drug, String amt, String dose, String freg, String duration

            String drug = txt_drug_src_input.getText();

            list_prescription.getSourceItems().add(drug);
            txt_drug_src_input.setText("");

        }
    }

    @FXML
    private void load_RP_patient(MouseEvent event) {
        if (!list_released.getSelectionModel().isEmpty()) {
            selected_held_pid = list_released.getSelectionModel().getSelectedItem(); // this is bcos i want both history / complanit to be amendable from the released list
            load_Personal_history_list(selected_held_pid);
            loadSavedDetails_fromHold_com_his(selected_held_pid, semanticsClass.returneDate());
            pid_history = selected_held_pid; // assigns an id to pid_history which is used to easily load personal history information

            btn_held.setText("Change");

            String id = list_released.getSelectionModel().getSelectedItem();
            loadHistory_Complaint(id, semanticsClass.returneDate());
            Timeline timeline = new Timeline(new KeyFrame(Duration.ONE, e -> {
                loadSavedDetails_fromHold(selected_held_pid, semanticsClass.returneDate());

                if (check_diagnosis_avbl(selected_held_pid, semanticsClass.returneDate())) {
                    load_diagnostics(selected_held_pid, semanticsClass.returneDate());
                }

                if (check_prescription_avbl(selected_held_pid, semanticsClass.returneDate())) {//this method will check if there's prescription available
                    new_load_presc(selected_held_pid, semanticsClass.returneDate());
                }
            }));
            timeline.setDelay(Duration.ONE);
            timeline.play();

            btn_attach.setDisable(false);
            table_of_presc.getItems().clear();

            PopOver pp = new PopOver();
            VBox vb = new VBox();
            Button b1 = new Button("Make Review");
            b1.setOnAction(ee -> {
                try {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Add Review Date");
                    alert.setContentText("Press OK to set a review Date and Time");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        Stage stage = new Stage();
                        Parent rooter = FXMLLoader.load(getClass().getResource("/com/Consults/SetReview.fxml"));
                        Scene scene = new Scene(rooter);

                        stage.setScene(scene);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.show();
                    }

                } catch (IOException e) {
                    System.out.println(e);
                    semanticsClass.set_notification("Loader Error", e.getMessage());

                }
            });
            Button b2 = new Button("Test patient");
            b2.setOnAction(ee -> {
                try {
                    Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                    al.setContentText("Press ok to test patient");
                    al.setTitle("Error");
                    Optional<ButtonType> result = al.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        String query = semanticsClass.setStandardProcedure_testOnly(username, id, semanticsClass.returneDate());
                        pst = conn.prepareStatement(query);
                        pst.execute();
                        _sharing_procedures();
                        selected_held_pid = "";
                    }
                } catch (SQLException e) {
                    semanticsClass.set_notification("Error", e.getMessage());
                }
            });
            Label title = new Label("Available Actions");
            title.setStyle("-fx-background-color: lightgreen");
            vb.getChildren().addAll(b1, b2);
            vb.setPrefWidth(120);
            vb.setSpacing(5);
            vb.setAlignment(Pos.TOP_CENTER);
            pp.setContentNode(vb);
            pp.show(list_released);

            r_btn_commit.setDisable(false);//enable dianosis button
            btn_attach.setDisable(false);//enable prescribe button

        }
    }

    void loadSavedDetails(String a_pid, String date) {
        _semi_root_ch_1.setExpanded(true);
        _in_RP_id = a_pid;
        sql = "select patient_info.*,nurses_on_patient.* from patient_info,nurses_on_patient where "
                + "patient_info.registration_no = nurses_on_patient.opd_id and nurses_on_patient.opd_id = '"
                + _in_RP_id + "' and nurses_on_patient.on_date = '" + date + "' ";
//        sql = "select * from nurses_on_patient where on_date = '" + semanticsClass.returneDate() + "' and opd_id = '" + incoming_value + "' ";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                lbl_name.setText(rs.getString("patient_info.surname"));
                lbl_O_names.setText(rs.getString("patient_info.other_names"));
                lbl_age.setText(rs.getString("patient_info._age"));
                lbl_dob.setText(rs.getString("patient_info._dob"));
                lbl_sex.setText(rs.getString("patient_info.sex"));
                lbl_contact.setText(rs.getString("patient_info.telephone"));
                lbl_marital_status.setText(rs.getString("patient_info.marital_status"));
                lbl_occupation.setText(rs.getString("patient_info.occupation"));
                lbl_location.setText(rs.getString("patient_info.locality"));

                lbl_pregnancy.setText(rs.getString("nurses_on_patient.preg"));
                lbl_weight.setText(rs.getString("nurses_on_patient.weight"));
                lbl_height.setText(rs.getString("nurses_on_patient.height"));
                lbl_temp.setText(rs.getString("nurses_on_patient.temperature"));
                lbl_systolis.setText(rs.getString("nurses_on_patient.systolis"));
                lbl_dystolis.setText(rs.getString("nurses_on_patient.dystolis"));
                lbl_pulse.setText(rs.getString("nurses_on_patient.pulse"));
                txt_complaint.setHtmlText(rs.getString("nurses_on_patient.complains"));
                txt_history.setText(rs.getString("nurses_on_patient.history"));
            } else {
                System.out.println("Rs didnt move");
            }
        } catch (SQLException e) {
            semanticsClass.set_notification("Error", e.getMessage());
        }
    }

    void loadSavedDetails_fromHold(String a_pid, String date) {
        _semi_root_ch_1.setExpanded(true);
        _in_RP_id = a_pid;
        sql = "select patient_info.*,nurses_on_patient.* from patient_info,nurses_on_patient where "
                + "patient_info.registration_no = nurses_on_patient.opd_id and nurses_on_patient.opd_id = '"
                + _in_RP_id + "' and nurses_on_patient.on_date = '" + date + "' ";
//        sql = "select * from nurses_on_patient where on_date = '" + semanticsClass.returneDate() + "' and opd_id = '" + incoming_value + "' ";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                lbl_name.setText(rs.getString("patient_info.surname"));
                lbl_O_names.setText(rs.getString("patient_info.other_names"));
                lbl_age.setText(rs.getString("patient_info._age"));
                lbl_dob.setText(rs.getString("patient_info._dob"));
                lbl_sex.setText(rs.getString("patient_info.sex"));
                lbl_contact.setText(rs.getString("patient_info.telephone"));
                lbl_marital_status.setText(rs.getString("patient_info.marital_status"));
                lbl_occupation.setText(rs.getString("patient_info.occupation"));
                lbl_location.setText(rs.getString("patient_info.locality"));

                lbl_pregnancy.setText(rs.getString("nurses_on_patient.preg"));
                lbl_weight.setText(rs.getString("nurses_on_patient.weight"));
                lbl_height.setText(rs.getString("nurses_on_patient.height"));
                lbl_temp.setText(rs.getString("nurses_on_patient.temperature"));
                lbl_systolis.setText(rs.getString("nurses_on_patient.systolis"));
                lbl_dystolis.setText(rs.getString("nurses_on_patient.dystolis"));
                lbl_pulse.setText(rs.getString("nurses_on_patient.pulse"));
            }
        } catch (SQLException e) {
            semanticsClass.set_notification("Error", e.getMessage());
        }
    }

    void loadSavedDetails_fromHold_com_his(String a_pid, String date) {
        _semi_root_ch_1.setExpanded(true);
        _in_RP_id = a_pid;

        sql = "select * from doctors_on_patient where opd_id = '" + _in_RP_id + "' and on_date = '" + date + "' ";

//        sql = "select * from nurses_on_patient where on_date = '" + semanticsClass.returneDate() + "' and opd_id = '" + incoming_value + "' ";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txt_complaint.setHtmlText(rs.getString("complaint"));
                txt_history.setText(rs.getString("history"));
            }
        } catch (SQLException e) {
            semanticsClass.set_notification("Error", e.getMessage());
        }
    }

    void new_load_presc(String _in_RP_id, String date) {

        int counter = 0;
        table_of_presc.getItems().clear();
        System.out.println("Loading prescription list.... " + counter++);
        try {
            sql = "select doctors_on_patient.*,presc_listing.* from doctors_on_patient,presc_listing where doctors_on_patient.opd_id = '" + _in_RP_id + "'"
                    + " and doctors_on_patient.prescription = presc_listing.home_presc_id and doctors_on_patient.on_date = '" + date + "' ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            System.out.println("rs executed Loading prescription list.... " + counter++);
            while (rs.next()) {
                rows_of_prescription.add(new prescription_table(
                        rs.getString("drug_code"),
                        rs.getString("drug_name"),
                        rs.getString("drug_type"),
                        rs.getString("dosage"),
                        rs.getString("price")));
//                System.out.println(rows_of_prescription.get(0).getDrug());
            }
            table_of_presc.setItems(rows_of_prescription);
//                System.out.println(table_of_presc.getItems().get(0).getDrug_cod());
        } catch (SQLException ee) {
            semanticsClass.set_notification("Error", ee.getMessage());
        }

    }

    void load_diagnostics(String pid, String date) {
        try {
            sql = "select doctors_on_patient.*,patient_diag_list.* from doctors_on_patient,patient_diag_list "
                    + "where doctors_on_patient.opd_id = '" + pid + "' "
                    + "and doctors_on_patient.diagnostic = patient_diag_list.id and "
                    + "doctors_on_patient.on_date = '" + date + "' ";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            all_prescription = FXCollections.observableArrayList();
            list_diagnostics.getItems().clear();
            while (rs.next()) {
//                all_prescription.add(rs.getString("patient_diag_list.diagnostic"));
                list_diagnostics.getItems().add(new Label(rs.getString("patient_diag_list.diagnosis")));
//                System.out.println(rs.getString("patient_diag_list.diagnosis"));
            }
//            list_diagnostics.getItems().setAll(all_prescription);
        } catch (SQLException e) {
            semanticsClass.set_notification("Error", e.getMessage());
        }
    }

    //generates the sql string for inserting into presc_lising this method use the random value received from 
    //() prepare_PH_home 
    String Single_PL_insertion(String drug_name) {
        sql = "insert into presc_listing (home_presc_id, drug_name,on_date) values ('" + random_value + "','" + drug_name + "','" + semanticsClass.returneDate() + "')";
        return sql;
    }

    //ON REMOVE PRESCRIPTION, THIS METHOD WILL REMOVE THE LIST OF PRESCRIPTION ATTACHED TO THIS PATIENT SESSION
    @FXML
    private void remove_data_prescription(ActionEvent event) {

    }

    //this method releases a worked on patient from the incoming list by updating it nurse column on_transit from ON to Off
    void rm_worked_patient(String id) {
        sql = "update nurses_on_patient set on_transit = 'OFF' where opd_id = '" + id + "' and on_date = '" + semanticsClass.returneDate() + "' ";
        try {
            pst = conn.prepareStatement(sql);
            pst.execute();
            load_incoming();
        } catch (SQLException e) {
            semanticsClass.set_notification("Error", e.getMessage());
        }
    }

    void _sharing_procedures() {
        try {
            if (!(conn.isClosed() || conn == null)) {
                ObservableList<String> _released = FXCollections.observableArrayList();
                ObservableList<String> _detained = FXCollections.observableArrayList();
                ObservableList<String> _refered = FXCollections.observableArrayList();
                ObservableList<String> _labbed = FXCollections.observableArrayList();
                ObservableList<String> _lab_returnee = FXCollections.observableArrayList();
                ObservableList<String> _unattended = FXCollections.observableArrayList();
                sql = "select status,opd_id from doctors_on_patient where on_date = '" + semanticsClass.returneDate() + "'"; //where on_date ' 
                try {
                    pst = conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        String states = rs.getString("status") + ":" + rs.getString("opd_id");
                        String[] _id_states = states.split("[:]");
//                System.out.println(_id_states[0] + "_-_" + _id_states[1]);
                        switch (_id_states[0]) {
                            case "RP"://release the patient
                                _released.add(_id_states[1]);
//                        System.out.println(_id_states[0] + "_-_" + _id_states[1]);

                                break;
                            case "DP"://detain patient
                                _detained.add(_id_states[1]);
//                        System.out.println(_id_states[0] + "_-_" + _id_states[1]);
                                break;
                            case "TP"://test patient meaning transfer patient to lab
                                _labbed.add(_id_states[1]);
//                        System.out.println(_id_states[0] + "_-_" + _id_states[1]);
                                break;
                            case "Refer"://refer patient
                                _refered.add(_id_states[1]);
//                        System.out.println(_id_states[0] + "_-_" + _id_states[1]);
                                break;
//                    case "LR"://lab returnees
//                        _lab_returnee.add( _id_states[1] + ":" +_id_states[0] );
//                        System.out.println(_id_states[0] + "_-_" + _id_states[1]);
//                        break;
                            case "Unattended"://this is the default when none of the above procedure is applied, which can be as a result of physical or internal factors.
                                _unattended.add(_id_states[1] + ":" + _id_states[0]);
//                        System.out.println(_id_states[0] + "_-_" + _id_states[1]);
                                break;
                        }
                    }

                    list_released.setItems(_released);
                    list_detained.setItems(_detained);
                    list_lab.setItems(_labbed);
                    list_refered.setItems(_refered);
                    list_unAttended.setItems(_unattended);
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    void lab_returnees() {
        ObservableList<String> _lab_returnee = FXCollections.observableArrayList();
        try {
            sql = "select * from doc_pat_test_list where on_date = '" + semanticsClass.returneDate() + "' and status = 'OFF' ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("on_date"));
                _lab_returnee.add(rs.getString("pid") + ":" + rs.getString("test_type"));
            }
            list_returnees.setItems(_lab_returnee);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    boolean worker = false;

    boolean check_prescription_avbl(String pid, String date) {
        try {
            sql = "select prescription from doctors_on_patient where opd_id = '" + pid + "' and on_date = '" + date + "' ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                rs.getString("prescription");
                if (!rs.wasNull()) {

                    semanticsClass.setNotificator(root_lbl_notificator, "Patient " + pid + " is given a prescription");

//                    btn_attach.setDisable(false);
                    btn_attach.setText("EDIT");
                    btn_prescribe.setDisable(true);

                    home_presc_id = rs.getString("prescription");
                    worker = true;

                } else {
                    semanticsClass.setNotificator(root_lbl_notificator, "Patient " + pid + " has no prescription");

//////                    btn_attach.setDisable(false);
                    btn_attach.setText("ATTACH");
                    btn_prescribe.setDisable(false);

                    worker = false;
                }

            }
            rs.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Doctors_NEWController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return worker;
    }

    void load_com_his() {
        try {

            sql = "select * from doctors_on_patient where opd_id = '" + _in_RP_id + "' and on_date = '" + semanticsClass.returneDate() + "' ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txt_complaint.setHtmlText(rs.getString("doctors_on_patient.complaint"));
                txt_history.setText(rs.getString("doctors_on_patient.history"));

            } else {
                txt_complaint.setHtmlText("<html><head></head><body>");
                txt_history.setText("");
            }
            pst.close();
            rs.close();

        } catch (SQLException e) {
        }
    }

    @FXML
    private void list_diagnostic_fill(MouseEvent event) {

    }

    @FXML
    private void add_diagnostics(ActionEvent event) {
        if (!txt_diagnostic_input.getText().trim().isEmpty()) {
            diag_item = new Label();
            diag_item.setText(txt_diagnostic_input.getText());

            //clear txt_diagnostic_input
            txt_diagnostic_input.setText("");

            list_diagnostics.getItems().add(diag_item);
        }

    }

    @FXML
    private void remove_diagnostics(ActionEvent event) {
        if (!list_diagnostics.getSelectionModel().isEmpty()) {
            int i = list_diagnostics.getSelectionModel().getSelectedIndex();
            list_diagnostics.getItems().remove(i);
        }
    }

    @FXML
    private void clear_list_diag_selection(MouseEvent event) {
        list_diagnostics.getSelectionModel().clearSelection();
    }

    @FXML
    private void view_selected(ActionEvent event) {
        if (!list_diagnostics.getSelectionModel().isEmpty()) {
            Label this_input = list_diagnostics.getSelectionModel().getSelectedItem();
            txt_diagnostic_input.setText(this_input.getText());
        }
    }

    @FXML
    private void edit_selected(ActionEvent event) {
        if (!list_diagnostics.getSelectionModel().isEmpty()) {
            int i = list_diagnostics.getSelectionModel().getSelectedIndex();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edit");
            alert.setHeaderText("Please edit this diagnostic, enter below");
            _input = new TextField();
            alert.getDialogPane().setContent(_input);

            TextFields.bindAutoCompletion(_input, diag_codes);

            Optional<ButtonType> results = alert.showAndWait();
            if (results.get() == ButtonType.OK) {
                diag_item.setText(_input.getText());
                list_diagnostics.getSelectionModel().getSelectedItem().setText(_input.getText());
            }

        }
    }

    @FXML
    private void list_add_diagnostics(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Add Diagnostic");
        alert.setHeaderText("Please enter the new diagnostic in the area below");
        diag_item = new Label();

        _input = new TextField();
        TextFields.bindAutoCompletion(_input, diag_codes);
        alert.getDialogPane().setContent(_input);
        Optional<ButtonType> results = alert.showAndWait();

        if (results.get() == ButtonType.OK) {
            diag_item.setText(_input.getText());
            list_diagnostics.getItems().add(diag_item);
        }

    }

    //below processes diagnosis to be added
    void create_diagnostic_home() {
        random_value = random.random_String_Value(selected_held_pid);
        if (random_value.length() > 100) {
            random_value = random_value.substring(0, 99);
        }

        sql = "insert into PATIENT_DIAG_HOME (id,on_date) values ('" + random_value + "','" + semanticsClass.returneDate() + "' )";
        try {
            pst = conn.prepareStatement(sql);
            pst.execute();
            semanticsClass.set_notification("Diagnostic Prepared", "Successful");
        } catch (SQLException e) {
            semanticsClass.set_notification("Error", e.getMessage());
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException e) {
                semanticsClass.set_notification("Error", e.getMessage());
            }
        }
    }

    String create_single_diagnos(String diagnostic) {
        sql = "insert into patient_diag_list (id,diagnosis,cod) values('" + random_value + "', '" + diagnostic + "', '" + "" + "'  )";
        return sql;
    }

    //method to batch diagnosis
    void create_batch_diagnosis(JFXListView<Label> lister) {
        Statement batch = null;
        try {
            batch = conn.createStatement();
            String diag_code;
            for (Label diag : lister.getItems()) {
                diag_code = diag.getText();
                // System.out.println(diag_code[0] + " " + diag_code[2]);
                batch.addBatch(create_single_diagnos(diag_code));
            }
            batch.executeBatch();
            //  semanticsClass.set_notification("Success", "Done");
        } catch (SQLException e) {
            semanticsClass.set_notification("Error", e.getMessage());
        } finally {
            try {
                batch.clearBatch();
                batch.close();
            } catch (SQLException e) {
                semanticsClass.set_notification("Error", e.getMessage());
            }
        }
    }

    //method to attach diagnosis to patient
    void attach_diagnostic_id() {
        sql = "update doctors_on_patient set doctors_on_patient.diagnostic = '" + random_value + "' where doctors_on_patient.opd_id = '" + incoming_value + " '";
        try {
            pst = conn.prepareStatement(sql);
            pst.execute();
        } catch (SQLException e) {
            semanticsClass.set_notification("Error", e.getMessage());
        } finally {
            try {
                pst.close();
            } catch (SQLException e) {
                semanticsClass.set_notification("Error", e.getMessage());
            }
        }
    }

    void compile_diagnostic() {
        create_diagnostic_home();//create a single record in patient_diag_home using a random value   ...line x
        create_batch_diagnosis(list_diagnostics);//creates single or multiple record(s) in the patient_diag_list ... line y
        attach_diagnostic_id();//attach the diagnosis base on the single random value from line x  ... line z
    }

    boolean check_diagnosis_avbl(String pid, String date) {
        try {
            sql = "select diagnostic from doctors_on_patient where opd_id = '" + pid + "' and on_date = '" + date + "' ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                rs.getString("diagnostic");
                if (!rs.wasNull()) {
//                    System.out.println("This is the diagnostic ID" + rs.getString("diagnostic"));
                    semanticsClass.setNotificator(root_lbl_notificator, "Patient " + pid + " is given diagnostics");
                    r_btn_commit.setText("EDIT");
                    worker = true;

                } else {
                    semanticsClass.setNotificator(root_lbl_notificator, "Patient " + pid + " has no diagnostics");
                    all_diagnosis = FXCollections.observableArrayList();
                    list_diagnostics.getItems().clear();
                    worker = false;
                    r_btn_commit.setText("Press to Commit".toUpperCase());
                }
            }
            rs.close();
            pst.close();
        } catch (SQLException ex) {
            semanticsClass.set_notification("Error", ex.getMessage());
        }
        return worker;
    }

    @FXML
    private void setFullScreen(ActionEvent event) {

    }

    @FXML
    private void un_attended_clicked(MouseEvent e) {

        if (list_unAttended.getSelectionModel().isEmpty()) {

        } else {
            String id = list_unAttended.getSelectionModel().getSelectedItem();
            try {
                String query = semanticsClass.set_alert_dop(username, id, semanticsClass.returneDate());
                pst = conn.prepareStatement(query);
                pst.execute();
                _sharing_procedures();
                rm_worked_patient(id);//removes patient from the incoming list
            } catch (SQLException ex) {
                semanticsClass.set_notification("Error", ex.getMessage());
            }
        }
    }

    @FXML
    private void _lab_return_clicked(MouseEvent event) {
        if (!list_returnees.getSelectionModel().isEmpty()) {
            String[] valuer = semanticsClass.getSelectedStringFromJFXListView(list_returnees).split("[:]");
            report_services report = new report_services();
            System.out.println(valuer[0] + " --- " + valuer[1]);

            btn_attach.setDisable(false);
            r_btn_commit.setDisable(false);
            PopOver pp = new PopOver();
            VBox hb = new VBox();
            Button b1 = new Button("View Report");
            b1.setPrefWidth(305);
            Button b2 = new Button("Release Patient");
            b2.setPrefWidth(305);
            b1.setOnAction(ee -> {
                switch (valuer[1]) {
                    case "Haematology":
                        sql = "SELECT * from LAB_HAEMATOLOGY inner join patient_info "
                                + "on "
                                + "LAB_HAEMATOLOGY.pid = patient_info.registration_no "
                                + "where LAB_HAEMATOLOGY.pid = '" + valuer[0] + "' "
                                + " and "
                                + " LAB_HAEMATOLOGY.on_date = '" + semanticsClass.returneDate() + "'";
                        Service<Boolean> service = report.load_lab_report(sql, conn, "LAB_HAEMA");
                        service.setOnRunning(e -> {
                            masker_lab_re.visibleProperty().bind(service.runningProperty());
                        });
                        service.start();
                        break;
                    case "Kidney Function Test":
                        System.out.println("This is KFT");
                        sql = "SELECT * from lab_kft inner join patient_info "
                                + "on "
                                + "lab_kft.pid = patient_info.registration_no "
                                + "where lab_kft.pid = '" + valuer[0] + "' "
                                + " and "
                                + " lab_kft.on_date = '" + semanticsClass.returneDate() + "'";
                        Service<Boolean> kft_service = report.load_lab_report(sql, conn, "LAB_KFT");
                        kft_service.setOnRunning(e -> {
                            masker_lab_re.visibleProperty().bind(kft_service.runningProperty());
                        });
                        kft_service.start();
                        break;
                    case "Liver Function Test":
                        System.out.println("This is Liver Function Test");
                        sql = "SELECT * from lab_lft inner join patient_info "
                                + "on "
                                + "lab_lft.pid = patient_info.registration_no "
                                + "where lab_lft.pid = '" + valuer[0] + "' "
                                + " and "
                                + " lab_lft.on_date = '" + semanticsClass.returneDate() + "'";
                        Service<Boolean> lft_service = report.load_lab_report(sql, conn, "LAB_LFT");
                        lft_service.setOnRunning(e -> {
                            masker_lab_re.visibleProperty().bind(lft_service.runningProperty());
                        });
                        lft_service.start();
                        break;
                    case "Microscopy":
                        System.out.println("This is Microscopy");
                        sql = "SELECT * from lab_microscopy inner join patient_info "
                                + "on "
                                + "lab_microscopy.pid = patient_info.registration_no "
                                + "where lab_microscopy.pid = '" + valuer[0] + "' "
                                + " and "
                                + " lab_microscopy.on_date = '" + semanticsClass.returneDate() + "'";
                        Service<Boolean> microscopy_service = report.load_lab_report(sql, conn, "LAB_MICROSCOPY");
                        microscopy_service.setOnRunning(e -> {
                            masker_lab_re.visibleProperty().bind(microscopy_service.runningProperty());
                        });
                        microscopy_service.start();
                        break;
                    case "Stools":
                        System.out.println("This is stools");
                        sql = "SELECT * from lab_stools inner join patient_info "
                                + "on "
                                + "lab_stools.pid = patient_info.registration_no "
                                + "where lab_stools.pid = '" + valuer[0] + "' "
                                + " and "
                                + " lab_stools.on_date = '" + semanticsClass.returneDate() + "'";
                        Service<Boolean> stools_service = report.load_lab_report(sql, conn, "LAB_STOOLS");
                        stools_service.setOnRunning(e -> {
                            masker_lab_re.visibleProperty().bind(stools_service.runningProperty());
                        });
                        stools_service.start();
                        break;
                    case "Urine R/E":
                        System.out.println("This is Urine R/E");
                        sql = "SELECT * from lab_stools inner join patient_info "
                                + "on "
                                + "lab_stools.pid = patient_info.registration_no "
                                + "where lab_stools.pid = '" + valuer[0] + "' "
                                + " and "
                                + " lab_stools.on_date = '" + semanticsClass.returneDate() + "'";
                        Service<Boolean> urine_service = report.load_lab_report(sql, conn, "LAB_URINE");
                        urine_service.setOnRunning(e -> {
                            masker_lab_re.visibleProperty().bind(urine_service.runningProperty());
                        });
                        urine_service.start();
                        break;
                    case "Widal Reaction":
                        System.out.println("Widal");
                        sql = "SELECT * from lab_widal inner join patient_info "
                                + "on "
                                + "lab_widal.pid = patient_info.registration_no "
                                + "where lab_widal.pid = '" + valuer[0] + "' "
                                + " and "
                                + " lab_widal.on_date = '" + semanticsClass.returneDate() + "'";
                        Service<Boolean> widal_service = report.load_lab_report(sql, conn, "LAB_WIDAL");
                        widal_service.setOnRunning(e -> {
                            masker_lab_re.visibleProperty().bind(widal_service.runningProperty());
                        });
                        widal_service.start();
                        break;
                }

            });
            b2.setOnAction(ee -> {
                try {
                    Statement stmt = conn.createStatement();
                    String query = semanticsClass.set_alert_dop(username, valuer[0], semanticsClass.returneDate());
                    stmt.addBatch(query);
                    stmt.addBatch("Delete from  doc_pat_test_list where pid = '" + valuer[0] + "' and on_date =  '" + semanticsClass.returneDate() + "' ");
                    stmt.executeBatch();
                    _sharing_procedures();
//                rm_worked_patient(valuer[0]);//removes patient from the incoming list

                } catch (SQLException ex) {
                    semanticsClass.set_notification("Error", ex.getMessage());
                    System.out.println(ex.getMessage());
                }
            });
            hb.setAlignment(Pos.CENTER);
            hb.getChildren().addAll(b1, b2);
            hb.setPrefWidth(350);
            hb.setSpacing(10);
            pp.setAutoHide(true);

            pp.setContentNode(hb);
            pp.show(list_returnees);

//        alert.getDialogPane().getChildren().add(hb);
//        
//        alert.show();
//
        }
    }

    @FXML
    private void add_single_prescription(ActionEvent event) {
        try {
            composeThis = txt_drug_code.getText(); //composeThis is passed the txt_drug_code.getText() value to be available for the composeDrugsController which needs it
            sql = "select * from presc_drugs where code = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, composeThis);
            rs = pst.executeQuery();
            boolean allow = false;
            if (rs.next()) {
                allow = true;
            } else {
                allow = false;
            }
            if (allow == true) {
                rows_of_prescription.add(new prescription_table(txt_drug_code.getText(), txt_drug_src_input.getText(), txt_drug_type.getText(), txt_presc_dosage.getText(), txt_presc_amount.getText()));
                table_of_presc.setItems(rows_of_prescription);
                //clear all textfields
                semanticsClass.clearTextFields(txt_drug_code, txt_drug_src_input, txt_drug_type, txt_presc_dosage, txt_presc_amount);
            } else {
                //semanticsClass.set_notification("Error", "The drug does not exit");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cancelled");
                alert.setHeaderText("The drug does not exit, press OK to compose a new Drug");
                Optional<ButtonType> results = alert.showAndWait();
                if (results.get() == ButtonType.OK) {
                    try {
                        if (results.get() == ButtonType.OK) {
                            Stage stage = new Stage();
                            Parent rooter = FXMLLoader.load(getClass().getResource("/com/Consults/ComposeDrugs.fxml"));
                            Scene scene = new Scene(rooter);

                            stage.setScene(scene);
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.show();
                        }
                    } catch (Exception e) {
                    }
                }
            }
        } catch (SQLException e) {
            SySoutException(e);
        }
    }

    @FXML
    private void remove_single_prescription(ActionEvent event) {
        int i = table_of_presc.getSelectionModel().getSelectedIndex();
        table_of_presc.getItems().remove(i);
    }

    public String pres_row_query(prescription_table row) {
        sql = "insert into presc_listing (home_presc_id,drug_code,drug_name,drug_type,dosage,price,on_date)"
                + " values( '" + random_value + "' ,'" + row.getDrug_cod() + "',"
                + "'" + row.getDrug() + "','" + row.getDrug_type() + "' , "
                + "'" + row.getDosage() + "', '" + row.getPrice() + "', "
                + "'" + semanticsClass.returneDate() + "')";
        return sql;
    }

    public void batch_presc_entry(ObservableList<prescription_table> rows) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            for (prescription_table row : rows) {
                stmt.addBatch(pres_row_query(row));
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            semanticsClass.set_notification("Error", e.getMessage());
        }
    }

    //generates the random value for the column presc_home.presc_id   
    void prepare_PH_home() {
        random_value = random.random_String_Value(selected_held_pid);
        sql = "insert into presc_home (presc_id,on_date) values('" + random_value + "' , '" + semanticsClass.returneDate() + "' )";

        try {
            pst = conn.prepareStatement(sql);
            pst.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                pst.close();
            } catch (SQLException e) {
            }
        }
    }

    //the method below will attach the presc id to the selected patient from the released listview
    void set_presc_id_to_pid() {
        sql = "update doctors_on_patient set prescription = '" + random_value + "' where opd_id = '" + _in_RP_id + "' and on_date = '" + semanticsClass.returneDate() + "' ";
        try {
            pst = conn.prepareStatement(sql);
            pst.execute();
            semanticsClass.set_notification("Success", "Patient is given prescription");
            table_of_presc.getItems().clear();//clear the table
            btn_attach.setText("Edit");
        } catch (SQLException e) {
        }
    }

    //THIS METHOD  COMBINES THE METHOD TO PREPARE A SINGLE RECORD IN THE PRESCRIPTION HOME TABLE
    //THEN THE SECOND METHOD REFERENCES THE ID GENERATED FROM THAT SINGLE RECORD TO CREATE MULTIPLE
    //ITEMS OF PRESCRIPTION FOR THE PATIENT ON DOCTOR SESSION
    public void new_presc_attach() {
        prepare_PH_home();
        batch_presc_entry(rows_of_prescription);
        set_presc_id_to_pid();
    }

    @FXML
    private void new_attach(ActionEvent event) {
        if (!btn_attach.getText().contentEquals("EDIT")) {
            timeline_loader.pause();
            if (!table_of_presc.getItems().isEmpty()) {
                new_presc_attach();
            }
            timeline_loader.play();
        } else {
            try {
                Stage stage = new Stage();
                Parent rooter = FXMLLoader.load(getClass().getResource("/com/Consults/update_prescription.fxml"));
                Scene scene = new Scene(rooter);
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException e) {
                semanticsClass.set_notification("Loader Error", e.getMessage());
            }
        }

    }

    @FXML
    private void refered_list_clicked(MouseEvent event) {
        if (!list_refered.getSelectionModel().isEmpty()) {
            Button btn = new Button("Print Referal Letter");
            PopOver pops = new PopOver(btn);
            btn.setOnAction(e -> {

            });
            pops.show(list_refered);
        }
    }

    private void calculate_quantity(ActionEvent event) {
        if (!(txt_presc_dosage.getText().isEmpty() || txt_presc_freque.getText().isEmpty() || txt_presc_duration.getText().isEmpty())) {
            String freq_value = "";
            switch (txt_presc_freque.getText()) {
                case "tds":
                    freq_value = "3";
                    break;
                case "tid":
                    freq_value = "3";
                    break;
                case "qid":
                    freq_value = "4";
                    break;
                case "bd":
                    freq_value = "2";
                    break;
                case "bid":
                    freq_value = "2";
                    break;
                case "daily":
                    freq_value = "1";
                    break;
                case "nocte":
                    freq_value = "1";
                    break;
            }

            String dosages = txt_presc_dosage.getText();

            int valuer = semanticsClass.calculate_quantity(dosages, freq_value, txt_presc_duration.getText());
            int divs = semanticsClass.num_alph_filter(dosages);
            txt_presc_amount.setText(String.valueOf(valuer / divs));
        } else {
            semanticsClass.set_notification("Error", "Null Value, Check and Retry");
        }
    }

    @FXML
    private void _lab_out_clicked(MouseEvent event) {
        if (!list_lab.getSelectionModel().isEmpty()) {
            btn_list_lab.setDisable(false);
            String _pid = semanticsClass.getSelectedStringFromJFXListView(list_lab);
            VBox vb = new VBox();
            ListView<String> listview = new ListView<>(FXCollections.observableArrayList("Kidney Function Test", "Liver Function Test", "Microscopy", "Stools", "Urine R/E", "Haematology", "Widal Reaction").sorted());
            listview.setPrefHeight(300);
            Button button_engue = new Button("Enque");
            Button button_sub = new Button("Subscribe");
            Label label = new Label("Subscribe Patient Test");
            vb.getChildren().addAll(label, listview, button_sub);
            button_sub.setOnAction(e -> {
                try {
                    sql = "insert into doc_pat_test_list (pid,test_type,on_date,record_time,status) values (?,?,?,?,?)";
                    conn = conclass.livewell_localhost();
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, _pid);
                    pst.setString(2, listview.getSelectionModel().getSelectedItem());
                    pst.setString(3, semanticsClass.returneDate());
                    pst.setTimestamp(4, semanticsClass.nowStamp(new java.util.Date()));
                    pst.setString(5, "ON");
                    pst.execute();
                    label.setStyle("-fx-background-color:lightgreen");
                } catch (SQLException ee) {
                    if (ee.getErrorCode() == 1062) {
                        System.out.println(ee);
                        label.setText(_pid + " is already subscribed to " + listview.getSelectionModel().getSelectedItem());
                        label.setStyle("-fx-background-color:tomato");

                    } else {
                        System.out.println(ee);
                        label.setText("An error occured");
                        label.setStyle("-fx-background-color:tomato");
                    }
                }
            });
            PopOver popper = new PopOver();
            popper.setContentNode(vb);
            popper.setArrowLocation(PopOver.ArrowLocation.LEFT_TOP);
            popper.show(list_lab);
        }
    }

    @FXML
    private void _lab_detained_clicked(MouseEvent event) {
        if (list_detained.getSelectionModel().isEmpty()) {

        } else {
            String id = list_detained.getSelectionModel().getSelectedItem();
            try {
                String query = semanticsClass.set_alert_dop(username, id, semanticsClass.returneDate());
                pst = conn.prepareStatement(query);
                pst.execute();
                _sharing_procedures();
                rm_worked_patient(id);//removes patient from the incoming list
            } catch (SQLException ex) {
                System.out.println(ex);
                semanticsClass.set_notification("Error", ex.getMessage());
            }
        }
    }

    private void load_reviews(java.sql.Date dater) {
        sql = "select * from doctor_reviews where status = 'on' and review_date = ? ";
//        conn = conclass.livewell_localhost();
        try {
            pst = conn.prepareStatement(sql);
            pst.setDate(1, dater);
            rs = pst.executeQuery();
            ObservableList<doctor_reviews> observe_dr = FXCollections.observableArrayList();
            ObservableList<String> theOb = FXCollections.observableArrayList();
            while (rs.next()) {
                doctor_reviews dr = new doctor_reviews();
                System.out.println(rs.getString("pid"));
                dr.setPid(rs.getString("pid"));
                dr.setNote(rs.getString("note"));
                dr.setOn_date(rs.getString("on_date"));
                dr.setReview_date(rs.getDate("review_date"));
                dr.setReview_time(rs.getTime("review_time"));
                observe_dr.add(dr);
            }
            table_review.setItems(observe_dr);
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    private void load_existing_reviews_by_dates() {
        sql = "select distinct review_date from doctor_reviews where status = 'on' order by review_date";
//        conn = conclass.livewell_localhost();
        try {
            pst = conn.prepareStatement(sql);
            java.sql.Date today = java.sql.Date.valueOf(LocalDate.now());
            rs = pst.executeQuery();
            ObservableList<String> observe_dr = FXCollections.observableArrayList();
            ObservableList<String> theOb = FXCollections.observableArrayList();
            while (rs.next()) {
                cbo_review_filter_date.getItems().add(rs.getDate("review_date"));
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

    }
    Parent rooter = null;
    PopOver poper = null;

    private void _review_lister_clicked(MouseEvent event) {
        if (!review_lister.getSelectionModel().isEmpty()) {
//            review_pid
            doctor_reviews dr = (doctor_reviews) review_lister.getSelectionModel().getSelectedItem();
            try {
                review_pid = dr.getPid();

                Stage stage = new Stage();
                Parent rooter = FXMLLoader.load(getClass().getResource("/com/Consults/openReview.fxml"));
                Scene scene = new Scene(rooter);
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
                _semi_root_ch_1.setExpanded(true);
            } catch (IOException e) {
                semanticsClass.set_notification("Loader Error", e.getMessage());
            }
        }

    }

    @FXML
    private void review_table_clicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            if (!table_review.getSelectionModel().isEmpty()) {
                doctor_reviews dr = (doctor_reviews) table_review.getSelectionModel().getSelectedItem();

                try {
                    review_pid = dr.getPid();
                    review_onDate = dr.getOn_date();

                    Stage stage = new Stage();
                    Parent rooter = FXMLLoader.load(getClass().getResource("/com/Consults/openReview.fxml"));
                    Scene scene = new Scene(rooter);
                    stage.setScene(scene);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();
                    _semi_root_ch_1.setExpanded(true);
                } catch (IOException e) {
                    semanticsClass.set_notification("Loader Error", e.getMessage());
                }
            }
        }
    }

    @FXML
    private void review_reminder(ActionEvent event) {
        if (!table_review.getSelectionModel().isEmpty()) {
            ObservableList<doctor_reviews> selected_list = table_review.getSelectionModel().getSelectedItems();
            twilio_services twilio_services = new twilio_services();
            Service<Boolean> service;
            String contact = "";
            for (doctor_reviews dr : selected_list) {

                try {
                    sql = "select telephone from patient_info where registration_no = '" + dr.getPid() + "' ";
                    pst = conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        contact = rs.getString("telephone");
                        if (contact.startsWith("+233")) {
                            System.out.println(contact);
                        } else if (contact.startsWith("0")) {
                            contact = contact.substring(1);
                            contact = "+233" + contact;
                            System.out.println(contact);
                        } else {
                            contact = "+233" + contact;
                            System.out.println(contact);
                        }
                        service = twilio_services.sendSms(
                                contact,
                                "+12563611302",
                                "Livewell Herbal Centre: \nYou have a review on " + dr.getReview_date() + " at " + dr.getReview_time());
                        service.setOnRunning((event1) -> {
//                           review_progress_bar.visibleProperty().bind(service.runningProperty());
                        });

                        service.start();

                    }
                } catch (Exception e) {
                }

            }
        }
    }

    @FXML
    private void review_cancel(ActionEvent event) {
    }

    @FXML
    private void review_filter(ActionEvent event) {
        load_reviews(cbo_review_filter_date.getValue());
    }

    @FXML
    void review_get_today(ActionEvent event) {
        java.sql.Date today = java.sql.Date.valueOf(LocalDate.now());
        load_reviews(today);
    }

    @FXML
    private void review_reminder_whatsapp(ActionEvent event) {
        if (!table_review.getSelectionModel().isEmpty()) {
            ObservableList<doctor_reviews> selected_list = table_review.getSelectionModel().getSelectedItems();
            twilio_services twilio_services = new twilio_services();
            Service<Boolean> service;
            String contact = "";
            for (doctor_reviews dr : selected_list) {

                try {
                    sql = "select telephone from patient_info where registration_no = '" + dr.getPid() + "' ";
                    pst = conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        contact = rs.getString("telephone");
                        if (contact.startsWith("+233")) {
                            System.out.println(contact);
                        } else if (contact.startsWith("0")) {
                            contact = contact.substring(1);
                            contact = "+233" + contact;
                            System.out.println(contact);
                        } else {
                            contact = "+233" + contact;
                            System.out.println(contact);
                        }
                        service = twilio_services.sendWhatSpp(contact, "+12563611302",
                                "Livewell Herbal Centre: \nYou have a review on " + dr.getReview_date() + " at " + dr.getReview_time());
                        service.setOnSucceeded(e -> {
                        });
                        service.start();
                    }
                } catch (Exception e) {
                }

            }
        }
    }

    String selected_held_pid = "";

    @FXML
    private void action_hold(ActionEvent event) {
        timeline_loader.pause();
        if (!btn_held.getText().equals("Change")) {
            if (!(txt_complaint.getHtmlText().trim().isEmpty() && txt_history.getText().trim().isEmpty() && _doctors_id.isEmpty())) {
                boolean worker = false;
                try {
                    sql = "insert into doctors_on_patient "
                            + "(doctor_id,opd_id,on_date,weight,temp,systolis,dystolis,pulse,history,complaint,status,time_in) "
                            + "value (?,?,?,?,?,?,?,?,?,?,?,?)";

                    pst = conn.prepareStatement(sql);
                    pst.setString(1, _doctors_id);//doctors_id
                    pst.setString(2, incoming_value);//patient_id
                    pst.setString(3, semanticsClass.returneDate());
                    pst.setDouble(4, Double.parseDouble(lbl_weight.getText()));//weight
                    pst.setDouble(5, Double.parseDouble(lbl_temp.getText()));//temperature
                    pst.setInt(6, Integer.parseInt(lbl_systolis.getText()));//systolis
                    pst.setInt(7, Integer.parseInt(lbl_dystolis.getText()));//dystolis
                    pst.setInt(8, Integer.parseInt(lbl_pulse.getText()));//pulse
                    pst.setString(9, txt_history.getText());//history
                    pst.setString(10, txt_complaint.getHtmlText());//complaint
                    pst.setString(11, "hold");//make status hold
                    pst.setTimestamp(12, semanticsClass.nowStamp(new java.util.Date()));//make status hold
                    pst.execute();
                    //THE LINE BELOW IS COMMENTED AS THE DOCTOR MIGHT TAKE A DECISION TO EITHER REFER,TEST,DETAIN,OR RELEASE THE PATIENT
                    /* IT IS WHEN THE DOCTOR MADE A DECISION BEFORE PRESCRIPTION CAN BE ATTACHED TO THE 
                    PATIENT SESSION*/
                    worker = true;
                    if (worker) {
                        pst.close();
                        rs.close();
                        rm_worked_patient(incoming_value);//removes patient from the incoming list

                        try {

                        } catch (Exception e) {
                        }

                    } else {
                        semanticsClass.set_notification("Error", "Sth happened");
                    }

                } catch (SQLException e) {
                    semanticsClass.set_notification("Error", e.getMessage());
                }

            } else {
                semanticsClass.set_notification("Error", "No History, or Complaint  \nPlease Provide them before Hold");
            }
        } else {
            try {
                sql = "update doctors_on_patient set history = '" + txt_history.getText()
                        + "' , complaint = '" + txt_complaint.getHtmlText() + "' where on_date = '" + semanticsClass.returneDate() + "' and opd_id = ? ";
                pst = conn.prepareStatement(sql);
                pst.setString(1, selected_held_pid);
                pst.execute();

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        load_held();
        timeline_loader.play();
    }

    void load_held() {
        sql = "select * from doctors_on_patient where status = 'hold' and on_date = '" + semanticsClass.returneDate() + "'  ";
        try {
            if (!(conn.isClosed() || conn == null)) {
                try {
                    pst = conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    list_held.getItems().clear();
                    while (rs.next()) {
                        list_held.getItems().addAll(FXCollections.observableArrayList(rs.getString("doctors_on_patient.opd_id")));
                    }
                } catch (SQLException e) {
                    System.out.println("held errors : " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @FXML
    private void list_held_mouse_clicked(MouseEvent event) {

        if (!list_held.getSelectionModel().isEmpty()) {
            selected_held_pid = list_held.getSelectionModel().getSelectedItem();
            sql = "select * from doctors_on_patient "
                    + "where opd_id = '" + selected_held_pid + "' "
                    + "and on_date = '" + semanticsClass.returneDate() + "'  ";
            try {
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                if (rs.next()) {//history,complaint

                    btn_held.setText("Change");
//                  loadSavedDetails(selected_held_pid, semanticsClass.returneDate());
//                  loadSavedDetails_fromHold(selected_held_pid, semanticsClass.returneDate());

                    txt_history.setText(rs.getString("history"));
                    txt_complaint.setHtmlText(rs.getString("complaint"));
                    loadSavedDetails_fromHold(selected_held_pid, semanticsClass.returneDate());

                    list_diagnostics.getItems().clear();
                    table_of_presc.getItems().clear();

                    r_btn_commit.setDisable(true);//disable dianosis button
                    btn_attach.setDisable(true);//disable prescribe button

                    if (check_prescription_avbl(selected_held_pid, semanticsClass.returneDate())) {//this method will check if there's prescription available
                        new_load_presc(selected_held_pid, semanticsClass.returneDate());
                    }
                    if (check_diagnosis_avbl(selected_held_pid, semanticsClass.returneDate())) {
                        load_diagnostics(selected_held_pid, semanticsClass.returneDate());
                    }
                    lbl_held_pid.setText(selected_held_pid);
                    try {
                        Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                        al.setContentText("Press ok to proceed");
                        al.setTitle("Error");
                        Optional<ButtonType> result = al.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            String query = semanticsClass.setStandardProcedure_lab_or_release_Only(username, selected_held_pid, semanticsClass.returneDate());
                            pst = conn.prepareStatement(query);
                            pst.execute();
                            _sharing_procedures();
                            selected_held_pid = "";
                        }
                    } catch (SQLException e) {
                        semanticsClass.set_notification("Error", e.getMessage());
                    }
                } else {

                }
            } catch (SQLException e) {
                //  JOptionPane.showMessageDialog(null, e.getMessage());
                System.out.println(e.getMessage());
            }

        }

    }

    @FXML
    private void lbl_Held_Dragged_over(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasString()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    @FXML
    private void lbl_Held_DraggedDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            System.out.println("Dropped: " + db.getString());
            selected_held_pid = db.getString();
            loadSavedDetails(selected_held_pid, semanticsClass.returneDate());
            lbl_held_pid.setText(db.getString());
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    private void _lab_returned_dragDetection(MouseEvent event) {
        System.out.println("listcell setOnDragDetected");
        Dragboard db = list_returnees.startDragAndDrop(TransferMode.COPY);
        ClipboardContent content = new ClipboardContent();
        String[] selectedItem = list_returnees.getSelectionModel().getSelectedItem().split(":");
        String strContent = selectedItem[0];
        content.putString(strContent);
        db.setContent(content);
        System.out.println(strContent);
        event.consume();
    }

    @FXML
    private void released_dragDetection(MouseEvent event) {
        System.out.println("listcell setOnDragDetected");
        Dragboard db = list_released.startDragAndDrop(TransferMode.COPY);
        ClipboardContent content = new ClipboardContent();
        String selectedItem = list_released.getSelectionModel().getSelectedItem();
        String strContent = selectedItem;
        content.putString(strContent);
        db.setContent(content);
        System.out.println(strContent);
        event.consume();
    }

    @FXML
    private void doConnRefresh(ActionEvent event) {
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
            mask_conn_refreshn.visibleProperty().bind(service.runningProperty());
        });
        service.setOnSucceeded(e -> {
            if ((service.getValue() != null)) {
                conn = service.getValue();
                // btn_conn_refresh.setStyle("-fx-border-color:lightgreen;");
                load_all_drugs();
            } else {
                // btn_conn_refresh.setStyle("-fx-border-color:tomato;");
                semanticsClass.set_notification("Error", "Server might be down please advice administtrator");
            }
        });
        service.start();
    }

    @FXML
    private void openComposeDrugs(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Ready");
            alert.setHeaderText("Press OK to continue");
            Optional<ButtonType> results = alert.showAndWait();
            if (results.get() == ButtonType.OK) {
                Stage stage = new Stage();
                Parent rooter = FXMLLoader.load(getClass().getResource("/com/Consults/ComposeDrugs.fxml"));
                Scene scene = new Scene(rooter);

                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void getEnrollment(ActionEvent event) {
        sql = " Select opd_id from doctors_on_patient where on_date = ? order by time_in";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, cbo_enrollment.getValue());
            rs = pst.executeQuery();
            list_enrollment.getItems().removeAll(list_enrollment.getItems());
            while (rs.next()) {
                list_enrollment.getItems().addAll(FXCollections.observableArrayList(rs.getString("doctors_on_patient.opd_id")));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void list_enrollment_clicked(MouseEvent event) {
        String list_value = list_enrollment.getSelectionModel().getSelectedItem();

        loadSavedDetails(list_value, cbo_enrollment.getValue());
        loadHistory_Complaint(list_value, cbo_enrollment.getValue());
        list_diagnostics.getItems().clear();
        table_of_presc.getItems().clear();
        if (check_prescription_avbl(list_value, cbo_enrollment.getValue())) {//this method will check if there's prescription available
            //load_prescription(_in_RP_id);
            new_load_presc(list_value, cbo_enrollment.getValue());
        }
        if (check_diagnosis_avbl(list_value, cbo_enrollment.getValue())) {
            load_diagnostics(list_value, cbo_enrollment.getValue());
        }

//  pane_presc_ctrl.setDisable(true);//disabling pane containing the prescription components
    }

    void loadHistory_Complaint(String pid, String date) {
        sql = "select * from doctors_on_patient where opd_id = '" + pid + "' and on_date = '" + date + "'  ";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txt_history.setText(rs.getString("doctors_on_patient.history"));
                txt_complaint.setHtmlText(rs.getString("doctors_on_patient.complaint"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    void load_Personal_history_list(String ids) {
        sql = "select * from doctors_on_patient where opd_id = '" + ids + "' ";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            list_personal_history.getItems().clear();
            while (rs.next()) {
                list_personal_history.getItems().addAll(rs.getString("on_date"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void getPersonalHistory_Report(ActionEvent event) {
        if (!list_personal_history.getSelectionModel().isEmpty()) {
            String date = list_personal_history.getSelectionModel().getSelectedItem();
            String id = list_incoming_Patients.getSelectionModel().getSelectedItem();
            String diagnosis = "";
            String prescription = "";

            sql = "SELECT patient_info.surname,"
                    + "	patient_info.other_names,"
                    + "	patient_info.registration_no,"
                    + "	doctors_on_patient.*"
                    + "     FROM livewell.patient_info,"
                    + "	livewell.doctors_on_patient"
                    + "     WHERE "
                    + "	 patient_info.registration_no = '" + incoming_value + "' "
                    + "	 AND patient_info.registration_no = doctors_on_patient.opd_id "
                    + "	 AND doctors_on_patient.on_date = '" + date + "'";
            try {
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                if (rs.next()) {
                    prescription = rs.getString("prescription");
                    diagnosis = rs.getString("diagnosis");
//                    System.out.println(diagnosis);
//                    System.out.println(prescription);
                }
                pH_hashMap.put("diagnosis", diagnosis);
                pH_hashMap.put("prescription", prescription);
                pH_hashMap.put("SUBREPORT_DIR", "/com/reports/diag_only.jasper");
            } catch (SQLException e) {

            }
            Service<Boolean> service = new report_services().new doctorS_report().patient_report_init(sql, conn, pH_hashMap);
            service.setOnRunning(e -> {
                masker_personalHistory.visibleProperty().bind(service.runningProperty());
            });
            service.start();
        }
    }

    @FXML
    private void list_personal_history(MouseEvent event) {
        Timeline timer = new Timeline(new KeyFrame(Duration.ONE, e -> {
            String Dater = list_personal_history.getSelectionModel().getSelectedItem();
            loadHistory_Complaint(pid_history, Dater);
            System.out.println("The date is " + Dater + " the id is " + pid_history);
            loadSavedDetails(pid_history, Dater);
            if (check_prescription_avbl(pid_history, Dater)) {//this method will check if there's prescription available
                new_load_presc(pid_history, Dater);
            }
            if (check_diagnosis_avbl(pid_history, Dater)) {
                load_diagnostics(pid_history, Dater);
            }
        }));
        timer.setDelay(Duration.ONE);
        timer.play();
    }

    @FXML
    private void change_list_lab_(ActionEvent event) {
        if (!list_lab.getSelectionModel().isEmpty()) {
            String _pid = semanticsClass.getSelectedStringFromJFXListView(list_lab);
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Add Review Date");
                alert.setContentText("Press OK to set a review Date and Time");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    String query = semanticsClass.setStandardProcedure_lab_or_release_Only(username, _pid, semanticsClass.returneDate());
                    pst = conn.prepareStatement(query);
                    pst.execute();
                    _sharing_procedures();
                    btn_list_lab.setDisable(true);
                }
            } catch (Exception e) {
            }

        }
    }

    @FXML
    private void get_months_Enrollment(ActionEvent event) {
        String month = cbo_months.getValue();
        sql = "select * from doctors_on_patient where on_date like '%" + month + "%' group by on_date";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            cbo_enrollment.getItems().clear();
            while (rs.next()) {
                cbo_enrollment.getItems().add(rs.getString("on_date"));
            }
        } catch (SQLException e) {
        }

    }

    @FXML
    private void toPush_pharma(ActionEvent event) {
        //this event clears patient to pharmacy
        if (!list_released.getSelectionModel().isEmpty()) {
            String scope = list_released.getSelectionModel().getSelectedItem();
            Service<Boolean> serv = new Service<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            sql = "UPDATE livewell.doctors_on_patient SET "
                                    + "status = 'PUSH' "
                                    + "WHERE opd_id = ? AND on_date = '" + semanticsClass.returneDate() + "'  ";

                            try {
                                pst = conn.prepareStatement(sql);
                                pst.setString(1, scope);
                                pst.execute();
                                System.out.println("__Executed___");
                            } catch (SQLException e) {
                                System.out.println(e);
                            }
                            return false;
                        }
                    };
                }
            };

            serv.setOnSucceeded(e -> {
                _sharing_procedures();
            });
            serv.start();

        } else {
            semanticsClass.set_notification("Error", "Nothing to push");
        }
    }
}
