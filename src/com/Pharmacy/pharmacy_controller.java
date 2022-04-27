/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pharmacy;

import AES.payment_id_generate;
import com.connection.conclass;
import com.jfoenix.controls.JFXButton;
import static com.semantics.semanticsClass.SySoutException;
import com.jfoenix.controls.JFXListView;
import com.semantics.semanticsClass;
import com.services.Acc_Patient_Section_Services;
import com.services.concurrent_check_connection;
import com.signIn.nSign_Controller;
import com.tables.account_Patient_Section;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import com.tables.pharm_drug_costing;
import com.tables.pharmacy_table;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.ScheduledService;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.util.Duration;
import org.controlsfx.control.MaskerPane;
import java.sql.Statement;
import java.util.Date;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class pharmacy_controller implements Initializable {

    @FXML
    private JFXListView<String> list_incoming;
    @FXML
    private Label lbl_opd_id;
    @FXML
    private Label lbl_name;
    @FXML
    private Label lbl_sex;
    private Pane pane_crest;
    @FXML
    private Label lbl_crest;
    @FXML
    private TableView<pharmacy_table> table_of_presc;
    @FXML
    private TableColumn<?, ?> col_drug;
    @FXML
    private TableColumn<?, ?> col_dosage;
    private TableColumn<?, ?> col_freq;
    private TableColumn<?, ?> col_duration;
    @FXML
    private TableColumn<?, ?> col_amount;

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = "";
    String patient_id = "";
    String pharmacist_id = "";

    ObservableList<pharmacy_table> rows_of_prescription = FXCollections.observableArrayList();
    @FXML
    private JFXListView<String> list_out_going;
    @FXML
    private Label lbl_contact;
    @FXML
    private Label lbl_age;
    @FXML
    private TableColumn<?, ?> col_input;
    @FXML
    private TableColumn<?, ?> col_code;
    @FXML
    private TableColumn<?, ?> col_type;
    @FXML
    private TableColumn<?, ?> col_trigger;
    private TextArea txt_receipt_list;
    @FXML
    private TableView<pharm_drug_costing> table_costing;
    ObservableList<pharm_drug_costing> obs_drug_costing = FXCollections.observableArrayList();
    @FXML
    private TableColumn<pharm_drug_costing, String> col_drug_code;
    private TableColumn<pharm_drug_costing, Integer> col_costing_quantity;
    @FXML
    private TableColumn<pharm_drug_costing, Integer> col_price;
    @FXML
    private Label lbl_total;
    @FXML
    private TableColumn<?, ?> col_costing_total;
    @FXML
    private MaskerPane parent_Masker;
    private String payment_id;
    private JFXListView<String> list_deficiency;
    @FXML
    private Label pane_notica;
    @FXML
    private JFXListView<String> list_history;
    private ComboBox<String> cbo_history;
    @FXML
    private JFXListView<String> list_history_dates;
    @FXML
    private TextField txt_history_srch;
    @FXML
    private JFXButton btn_enque;
    @FXML
    private TableView<pharm_drug_costing> table_costing1;
    @FXML
    private TableColumn<?, ?> col_drug_code1;
    @FXML
    private TableColumn<?, ?> col_price1;
    @FXML
    private TableColumn<?, ?> col_costing_total1;
    @FXML
    private TextField txt_pay;
    @FXML
    private TextField txt_balance;
    @FXML
    private JFXButton btn_attach;
    @FXML
    private ComboBox<String> cbo_search;
    @FXML
    private TextField txt_search;
    @FXML
    private Button btn_search;
    @FXML
    private TableView<account_Patient_Section> tableView_today_history;
    @FXML
    private TableColumn<?, ?> col_history_pid;
    @FXML
    private TableColumn<?, ?> col_history_date;
    @FXML
    private TableColumn<?, ?> col_history_particulas;
    @FXML
    private TableColumn<?, ?> col_history_cost;
    @FXML
    private TableColumn<?, ?> col_history_amt_paid;
    @FXML
    private TableColumn<?, ?> col_history_balance;
    @FXML
    private Label ps_lbl_debt;
    @FXML
    private Label ps_lbl_curr_cost;
    double toPay, debt, balance = 0.0;
    double current_cost, current_debt, current_toPay = 0.;
    @FXML
    private Label ps_lbl_total;
    @FXML
    private LineChart<?, ?> chart_line;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = conclass.livewell_localhost();
        prescription_table_settings();
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
//           
//        }));
//        timeline.setCycleCount(Animation.INDEFINITE);
//        timeline.play();
//        get_incoming();
        load_payment_history(semanticsClass.returneDate());
        chart_loader(semanticsClass.returneDate());
        get_incoming();
        pharmacist_id = nSign_Controller.username;
        load_prescription_history_thru_dates("*");
        cbo_search.setItems(FXCollections.observableArrayList("All", "Today"));
        load_stools_ids();
//        silence_Incoming_Load();
    }

    void load_stools_ids() {
        cbo_search.setItems(FXCollections.observableArrayList("All", "Today"));
        sql = "select Distinct pid from account_patientsection order by record_time asc";
        ObservableList<String> on_dateItems = FXCollections.observableArrayList();
        ObservableList<String> id_items = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                id_items.add(rs.getString("pid"));
            }
            TextFields.bindAutoCompletion(txt_search, id_items);
        } catch (SQLException e) {
            System.out.println(e);
        }

        sql = "select Distinct on_date from account_patientsection order by record_time asc";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                on_dateItems.add(rs.getString("on_date"));
            }
            cbo_search.getItems().addAll(on_dateItems);
            //cbo_chart.getItems().addAll(on_dateItems);
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    void load_prescription_history_thru_dates(String date) {
        switch (date) {
            case "*":
                sql = "select distinct on_date from doctors_on_patient where prescription IS NOT NULL;";
                try {
                    pst = conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    list_history_dates.getItems().clear();
                    while (rs.next()) {
                        list_history_dates.getItems().add(rs.getString("on_date"));
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }
                break;
            default:
                sql = "select distinct on_date from doctors_on_patient where prescription IS NOT NULL and opd_id = ?";
                try {
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, date);
                    rs = pst.executeQuery();
                    list_history_dates.getItems().clear();
                    while (rs.next()) {
                        list_history_dates.getItems().add(rs.getString("on_date"));
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }
                break;
        }

    }

    void myConConnection() {
        ScheduledService<Connection> conConn = new concurrent_check_connection();
        conConn.setRestartOnFailure(true);
        conConn.setPeriod(Duration.seconds(4));
        conConn.setOnRunning(e -> {

        });

        conConn.setOnFailed(e -> {
            parent_Masker.visibleProperty().bind(conConn.runningProperty());
            parent_Masker.textProperty().bind(conConn.messageProperty());
        });

        conConn.setOnSucceeded(e -> {
            try {
                conn = conConn.getValue();
                if (!conn.isClosed()) {
                    parent_Masker.setVisible(false);

                    //System.out.println("am available i mean connected");
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
    ObservableList<String> _released = FXCollections.observableArrayList();
    ObservableList<String> _done = FXCollections.observableArrayList();

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
                            sql = "select status,opd_id,prescription from doctors_on_patient where on_date = '" + semanticsClass.returneDate() + "'"; //where on_date ' 

                            pst = conn.prepareStatement(sql);
                            rs = pst.executeQuery();

                            while (rs.next()) {
                                rs.getString("prescription");
                                if (!rs.wasNull()) {
                                    String states = rs.getString("status") + ":" + rs.getString("opd_id");
                                    String[] _id_states = states.split("[:]");
                                    switch (_id_states[0]) {
                                        case "PUSH":
                                            _released.add(_id_states[1]);
                                            break;
                                        case "DONE":
                                            _done.add(_id_states[1]);
                                            break;
                                    }
                                }
                            }
                            
                            Thread.sleep(1000);
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
            list_incoming.setItems(serrive.getValue());
            int size = list_incoming.getItems().size();
        });
        serrive.start();
    }

    void get_incoming() { 
        conn = conclass.livewell_localhost();
        sql = "select * from doctors_on_patient where on_date = '" + semanticsClass.returneDate() + "'"; 
        ScheduledService<ObservableList<String>> service = new ScheduledService<ObservableList<String>>() {
            @Override
            protected Task<ObservableList<String>> createTask() {
                return new Task<ObservableList<String>>() {
                    @Override
                    protected ObservableList<String> call() throws Exception {                     
                        try {
                           
                            pst = conn.prepareStatement(sql);
                            rs = pst.executeQuery();                   
                            while (rs.next()) {
                                rs.getString("status");
                                if (!rs.wasNull()) {
                                    String states = rs.getString("status") + ":" + rs.getString("opd_id");
                                    String[] _id_states = states.split("[:]");
                                    switch (_id_states[0]) {
                                        case "PUSH":
                                            _released.add(_id_states[1]);
                                            break;
                                        case "DONE":
                                            _done.add(_id_states[1]);
                                            break;
                                    }
                                }                                  
                            }                                                     
                            return _released;
                        } catch (SQLException e) {
                            System.out.println("getIncoming Error: "+e);
                            return null;
                        }
                    }
                };
            }
        };

       
        
        service.setOnSucceeded(e -> {
            list_incoming.setItems(_released);
            list_out_going.setItems(_done);

            semanticsClass.doBlink_Pane(list_incoming.getItems(), pane_notica);
            semanticsClass.alarm(list_incoming.getItems(), "audio/notic3.wav");
        });
        service.start();

    }

    void prescription_table_settings() {
        //drug_code
        col_code.setCellValueFactory(new PropertyValueFactory<>("drug_code"));
        col_drug.setCellValueFactory(new PropertyValueFactory<>("drug"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("drug_type"));
        col_dosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        col_amount.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_input.setCellValueFactory(new PropertyValueFactory<>("input"));
        col_trigger.setCellValueFactory(new PropertyValueFactory<>("checker"));

        //payment section table 
        //costing table
        col_drug_code1.setCellValueFactory(new PropertyValueFactory<>("drug_code"));
        col_price1.setCellValueFactory(new PropertyValueFactory<>("drug_price"));
        col_costing_total1.setCellValueFactory(new PropertyValueFactory<>("costing_total"));
        //

        //payment table
        col_history_pid.setCellValueFactory(new PropertyValueFactory<>("pid"));
        col_history_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_history_particulas.setCellValueFactory(new PropertyValueFactory<>("particulas"));
        col_history_cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        col_history_amt_paid.setCellValueFactory(new PropertyValueFactory<>("amt_paid"));
        col_history_balance.setCellValueFactory(new PropertyValueFactory<>("lbl_balance"));
        //

        col_drug_code.setCellValueFactory(new PropertyValueFactory<>("drug_code"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("drug_price"));
        col_costing_total.setCellValueFactory(new PropertyValueFactory<>("costing_total"));
    }

    @FXML
    private void incoming_selected_mouse(MouseEvent event) {
        String incoming_patient;
        if (!list_incoming.getSelectionModel().isEmpty()) {
            incoming_patient = list_incoming.getSelectionModel().getSelectedItem();
            patient_id = incoming_patient;
            load_id(incoming_patient, semanticsClass.returneDate());
            new_load_presc(incoming_patient, semanticsClass.returneDate());
            load_prescription_history_thru_dates(incoming_patient);
            btn_enque.setDisable(false);
        }
    }

    @FXML
    private void doneWith_mouseClicked(MouseEvent event) {
        String incoming_patient;
        if (!list_out_going.getSelectionModel().isEmpty()) {
            incoming_patient = list_out_going.getSelectionModel().getSelectedItem();
            patient_id = incoming_patient;
            load_id(incoming_patient, semanticsClass.returneDate());
            new_load_presc(incoming_patient, semanticsClass.returneDate());
            btn_enque.setDisable(true);

            loadItemPurchased(incoming_patient, semanticsClass.returneDate(), 0);
            loadItemPurchased(incoming_patient, semanticsClass.returneDate(), 1);
            load_in_debt(incoming_patient);
        }
    }
    String nhis_stat = "";

    void load_id(String incoming_patient, String date) {
        sql = "select patient_info.*,opd_history.*"
                + "from patient_info,opd_history "
                + "where patient_info.Registration_no = '" + incoming_patient + "' "
                + "and opd_history.last_date = '" + date + "' "
                + "and patient_info.Registration_no = opd_history.patient_num";

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println("redido");
                System.out.println(rs.getString("Registration_no"));
                lbl_opd_id.setText(rs.getString("Registration_no"));
                lbl_name.setText(rs.getString("surname") + " " + rs.getString("other_names"));
                lbl_sex.setText(rs.getString("sex"));
                lbl_age.setText(rs.getString("_age"));
                lbl_contact.setText(rs.getString("patient_info.Telephone"));
                switch (rs.getString("opd_history.nhis_stat")) {
                    case "YES":
                        lbl_crest.setStyle("-fx-background-color:lightgreen");

                        lbl_crest.setText("YES");

                        break;
                    case "NO":
                        lbl_crest.setStyle("-fx-background-color:#ff9b9b");

                        lbl_crest.setText("NO");

                        break;
                }
            } else {
                System.out.println("none redido");
            }

        } catch (SQLException e) {
//            semanticsClass.set_notification("Error", e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {

            }
        }
    }

    void new_load_presc(String incoming_patient, String date) {
        table_of_presc.getItems().clear();
        try {
            sql = "select doctors_on_patient.*, presc_listing.*,presc_drugs.* "
                    + "from doctors_on_patient,presc_listing,presc_drugs where "
                    + "doctors_on_patient.opd_id = '" + incoming_patient + "' and "
                    + "doctors_on_patient.prescription = presc_listing.home_presc_id and "
                    + "doctors_on_patient.on_date = '" + date + "' "
                    + "and "
                    + "presc_drugs.code = presc_listing.drug_code";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println("check here");
                rows_of_prescription.add(
                        new pharmacy_table(
                                rs.getString("drug_code"),
                                rs.getString("drug_name"),
                                rs.getString("drug_type"),
                                rs.getString("dosage"),
                                rs.getString("price"),
                                new TextField(rs.getString("price")),
                                new CheckBox()
                        ));
            }
            table_of_presc.setItems(rows_of_prescription);
            System.out.println("check there");
            table_costing.getItems().clear();
        } catch (SQLException e) {
            // semanticsClass.set_notification("Error", e.getMessage());
            SySoutException(e);
            System.out.println(e);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {

            }
        }
    }

    @FXML
    private void enqueue(MouseEvent event) {
        ObservableList<String> list_deficiency = FXCollections.observableArrayList();
        obs_drug_costing = FXCollections.observableArrayList();
        int count = 0;
        double price = 0.0;
        double lineTotal = 0.0;
        Double initPrice = 0.0;//the price given to the specific drug is taken
        Double initlineTotal = 0.0; //the value of this variable is added and rolled over +=
        for (pharmacy_table row : rows_of_prescription) {
            if (row.getChecker().isSelected()) {
                String drug_code = row.getDrug_code();
                String drug_quantity = row.getInput().getText();
                initPrice = Double.parseDouble(row.getPrice());
                initlineTotal = initlineTotal + initPrice;
                System.out.println(initlineTotal);
                lbl_total.setText(String.valueOf(initlineTotal));
                try {
                    sql = "select * from presc_drugs where code = ? ";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, drug_code);
                    rs = pst.executeQuery();

                    if (rs.next()) {
                        pharm_drug_costing pdci = new pharm_drug_costing();

                        pdci.setPay_id(new payment_id_generate().pharm_payment_id(patient_id));//set payment id
                        pdci.setPharmacist_id(pharmacist_id);//set pharmacist id
                        pdci.setPat_id(patient_id);//set pharmacist id
                        pdci.setDrug_code(rs.getString("code"));//set                      
                        pdci.setDrug_price(String.valueOf(row.getPrice()));
                        pdci.setCosting_total(String.valueOf(initlineTotal));
                        pdci.setOn_date(semanticsClass.returneDate());

                        obs_drug_costing.add(pdci);
                    }
                } catch (SQLException e) {
                    SySoutException(e);
                }
            }
        }

        table_costing.setItems(obs_drug_costing);
    }

    @FXML
    private void do_enqueue(ActionEvent event) {
    }

    @FXML
    private void table_clicked(MouseEvent event) {
        if (!table_of_presc.getSelectionModel().isEmpty()) {
            int i = table_of_presc.getSelectionModel().getFocusedIndex();
            pharmacy_table pt = table_of_presc.getItems().get(i);
            CheckBox cb = pt.getChecker();
            cb.setOnAction(e -> {
                int inQty = 0;//the quantity of drugs left in the database`
                try {
                    sql = "select quantity from presc_drugs where code = '" + pt.getDrug_code() + "'";
                    pst = conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        inQty = Integer.parseInt(rs.getString("quantity"));
                        System.out.println("In database qty: " + inQty);
                    }
                    int myQty = Integer.parseInt(pt.getInput().getText());
                    if (myQty <= inQty) {
                        System.out.println("In well database qty: " + inQty);
                    } else {
                        semanticsClass.set_notification("Error", "Not enough : Only " + inQty + " Available");
                        cb.setSelected(false);
                    }
                } catch (SQLException ee) {
                }

            });
        }
    }

    @FXML
    private void do_sell(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setContentText("Proceed to issue drugs/medicine to patient");
        Optional<ButtonType> results = alert.showAndWait();
        if (results.get() == ButtonType.OK) {
            if (!table_costing.getItems().isEmpty()) {
                Statement stm = conn.createStatement();
                Statement stm_pharm = conn.createStatement();
                try {
//                    stm.executeBatch();
                    addToPayment();
                    semanticsClass.set_notification("Ready", "Patient ready to receive drugs");
                  //  get_incoming();
                    table_of_presc.getItems().clear();
                    table_costing.getItems().clear();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

        }
    }

    void addToPayment() {
        String payId = createPaymentId();
        createPaymentBatch(table_costing.getItems(), payId);
        updateDoctorReleased();
    }

    private String createPaymentId() {
        String valu = new payment_id_generate().pharm_payment_id(patient_id);
        try {
            sql = "insert into pharmacy_payment_home (payment_id,on_date) values(?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, valu);
            pst.setString(2, semanticsClass.returneDate());
            pst.execute();
        } catch (SQLException e) {
            System.out.println("createHome error :" + e);
        }
        return valu;
    }

    private void createPaymentBatch(ObservableList<pharm_drug_costing> items, String payId) {
        try {
            Statement stm = conn.createStatement();
            for (pharm_drug_costing item : items) {
                sql = "insert into pharmacy_payment (pay_id,pharmacist,pid,drug_code,drug_price,line_total,on_date) "
                        + "values('" + payId + "','" + item.getPharmacist_id() + "','" + item.getPat_id() + "','" + item.getDrug_code()
                        + "','" + item.getDrug_price() + "','" + item.getCosting_total() + "','" + item.getOn_date() + "')";
                stm.addBatch(sql);
            }
            stm.executeBatch();
        } catch (SQLException ex) {
            System.out.println("Batch insertion error :" + ex);
            Logger.getLogger(pharmacy_controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateDoctorReleased() {
        if (!patient_id.isEmpty()) {
            sql = "update doctors_on_patient set status = 'DONE' where opd_id = '" + patient_id + "' and on_date = '" + semanticsClass.returneDate() + "' ";
            try {
                pst = conn.prepareStatement(sql);
                pst.execute();
            } catch (SQLException e) {
                System.out.println("update_status error :" + e);
            }
        }
    }

    private void search_history(ActionEvent event) {
    }

    @FXML
    private void history_selected_mouse_clicked(MouseEvent event) {
        if (!list_history.getSelectionModel().isEmpty()) {
            String pid = list_history.getSelectionModel().getSelectedItem();
            String date = list_history_dates.getSelectionModel().getSelectedItem();
            load_id(pid, date);
            new_load_presc(pid, date);
            System.out.println("history redido");
        }
    }

    @FXML
    private void cbo_load_avbl_prescription_ids(MouseEvent event) {
        sql = "select * from doctors_on_patient where prescription IS NOT NULL and on_date = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, list_history_dates.getSelectionModel().getSelectedItem());
            rs = pst.executeQuery();
            list_history.getItems().clear();
            while (rs.next()) {
                list_history.getItems().add(rs.getString("opd_id"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void history_find_by_pid(ActionEvent event) {
        if (!txt_history_srch.getText().trim().isEmpty()) {
            sql = "select * from patient_info where Registration_NO = ?";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, txt_history_srch.getText().trim());
                rs = pst.executeQuery();
                if (rs.next()) {
                    load_prescription_history_thru_dates(txt_history_srch.getText().toUpperCase());
                } else {
                    semanticsClass.set_notification("Wrong Entry", "Patient does not exist");
                }
            } catch (SQLException e) {
                System.out.println(e);
            }

        } else {

        }
    }

    @FXML
    private void do_select_all(ActionEvent event) {
        if (!table_of_presc.getItems().isEmpty()) {
            ObservableList<pharmacy_table> items = table_of_presc.getItems();
            items.forEach((pt) -> {
                if (pt.getChecker().isSelected()) {
                    pt.getChecker().setSelected(false);
                } else {
                    pt.getChecker().setSelected(true);
                }
            });
        }
    }

    @FXML
    private void do_curl(ActionEvent event) {
        if (!(txt_pay.getText().isEmpty())) {//&& !(pid.isEmpty()) 
            toPay = Double.parseDouble(txt_pay.getText());//this is the entry, meaning the cash paid
            //the debt is set when the patient is clicked
            balance = (debt) - (toPay);
            System.out.println(balance);
            if (balance < 0.0) {
                txt_balance.setText(String.valueOf(balance));
                txt_balance.setStyle("-fx-background-color:tomato;");
            } else if (balance == 0.0) {
                txt_balance.setText(String.valueOf(balance));
                txt_balance.setStyle("-fx-background-color:lightblue;");
            } else if (balance > 0.0) {
                txt_balance.setText(String.valueOf(balance));
                txt_balance.setStyle("-fx-background-color:lightgreen;");
            }

        }
    }

    @FXML
    private void do_attach(ActionEvent event) {
        if (!btn_attach.getText().contentEquals("Update")) {
            if (!table_costing1.getItems().isEmpty()) {
                if (!table_costing1.getItems().isEmpty() || !(txt_balance.getText().isEmpty())) {// checking if not costing is empty
                    account_Patient_Section aps = new account_Patient_Section();
                    aps.setPid(pdci.getPat_id());//geting the client_id
                    aps.setDate(pdci.getOn_date());// getting the dateOfEntry
                    aps.setParticulas(pdci.getPay_id());// getting the particulas, in our case the payment id
                    aps.setCost(payable);// the cost of items
                    aps.setAmt_paid(String.valueOf(toPay)); //the amount paid by client
                    Label lbl_balance = new Label();
//                aps.setLbl_balance(lbl_balance); being commented to remove such calculation since the deduction will be done explicitly within the sql query
                    aps.setRecord_time(semanticsClass.nowStamp(new Date()));
                    Service<Boolean> service = apsServices.addToHistory(aps);
                    service.setOnCancelled(e -> {
                        semanticsClass.set_notification("save error".toUpperCase(), service.getMessage());
                    });
                    service.setOnSucceeded(e -> {
                        if (service.getValue() == true) {
                            semanticsClass.set_notification("Success", service.getMessage());
                            chart_loader(semanticsClass.returneDate());
                            load_payment_history(semanticsClass.returneDate());
                        } else {
                            semanticsClass.set_notification("Error", "An error occured");
                        }
                    });
                    service.start();
                }
            }
        }
    }

    @FXML
    private void mob_search(ActionEvent event) {
        switch (cbo_search.getSelectionModel().getSelectedIndex()) {
            case 0:
                load_history("*");
                chart_loader("*");
                break;
            case 1:
                load_history(semanticsClass.returneDate());
                chart_loader(semanticsClass.returneDate());
                break;
            default:
                load_history(cbo_search.getValue());
                chart_loader(cbo_search.getValue());
                break;
        }
    }

    @FXML
    private void make_search(ActionEvent event) {
        load_history(txt_search.getText());
    }
    String payable = "";

    @FXML
    private void listView_History_mouseClicked(MouseEvent event) {

    }
    Acc_Patient_Section_Services apsServices = new Acc_Patient_Section_Services();

    private void load_history(String valuer) {
        tableView_today_history.getItems().clear();
        ObservableList<account_Patient_Section> obs = FXCollections.observableArrayList();
        Service<ObservableList<account_Patient_Section>> listAps = apsServices.load_patientSection_history(valuer);
        listAps.setOnSucceeded(e -> {
            tableView_today_history.setItems(listAps.getValue());
        });
        listAps.setOnFailed(e -> {
            tableView_today_history.getItems().clear();
            listAps.restart();
        });
        listAps.start();

    }

    private void load_payment_history(String valuer) {
        tableView_today_history.getItems().clear();
        ObservableList<account_Patient_Section> obs = FXCollections.observableArrayList();
        Service<ObservableList<account_Patient_Section>> listAps = apsServices.load_patientSection_history(valuer);
        listAps.setOnSucceeded(e -> {
            tableView_today_history.setItems(listAps.getValue());
        });
        listAps.setOnFailed(e -> {
            tableView_today_history.getItems().clear();
            listAps.restart();
        });
        listAps.start();

    }
    double costs, balances, paids = 0.0;

    private void chart_loader(String valuer) {

        if (valuer.contentEquals("*")) {//cost,amt_paid,balance,record_time
            sql = "select sum(cost) as costs, sum(amt_paid) as paid, sum(balance) as balance from livewell.account_patientsection ";
        } else {
            sql = "select sum(cost) as costs, sum(amt_paid) as paid, sum(balance) as balance from livewell.account_patientsection "
                    + "where on_date = '" + valuer + "' ";
        }
        XYChart.Series headers = new XYChart.Series<>();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                chart_line.getData().clear();
                rs.getString("costs");
                if (!rs.wasNull()) {
                    costs = Double.parseDouble(rs.getString("costs"));
                } else {
                    costs = 0.0;
                }
                rs.getString("paid");
                if (!rs.wasNull()) {
                    paids = Double.parseDouble(rs.getString("paid"));
                } else {
                    paids = 0.0;
                }
                rs.getString("balance");
                if (!rs.wasNull()) {
                    balances = Double.parseDouble(rs.getString("balance"));
                } else {
                    balances = 0.0;
                }

                headers.getData().add(new XYChart.Data("Cost", costs));
                headers.getData().add(new XYChart.Data("Paid", paids));
                headers.getData().add(new XYChart.Data("Balance", balances));

                chart_line.getData().addAll(headers);

            }
        } catch (SQLException e) {
        }
    }

    ObservableList<pharm_drug_costing> observe_pdci = FXCollections.observableArrayList();
    pharm_drug_costing pdci = new pharm_drug_costing();

    private void loadItemPurchased(String pid, String on_date, int which) {
        conn = conclass.livewell_localhost();
        table_costing.getItems().clear();
        table_costing1.getItems().clear();
        sql = "select pharmacy_payment.* from pharmacy_payment where pid = ? and on_date = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, pid);
            pst.setString(2, on_date);
            rs = pst.executeQuery();
            while (rs.next()) {
                pdci = new pharm_drug_costing();
                pdci.setPay_id(rs.getString("pay_id"));//set payment id
                pdci.setPharmacist_id(rs.getString("pharmacist"));//set pharmacist id
                pdci.setPat_id(rs.getString("pid"));//set pharmacist id
                pdci.setDrug_code(rs.getString("drug_code"));//set
                pdci.setDrug_price(String.valueOf(rs.getString("drug_price")));
                pdci.setCosting_total(String.valueOf(rs.getString("line_total")));
                ps_lbl_curr_cost.setText(String.valueOf(rs.getString("line_total")));
                txt_pay.setText(String.valueOf(rs.getString("line_total")));
                pdci.setOn_date(rs.getString("on_date"));
                observe_pdci.add(pdci);
            }
            switch (which) {
                case 0:
                    table_costing.setItems(observe_pdci);
                    break;
                case 1:
                    table_costing1.setItems(observe_pdci);
                    break;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        observe_pdci = FXCollections.observableArrayList();
        pdci = new pharm_drug_costing();
    }

    void load_in_debt(String pid) {
        sql = "select sum(balance) as bal from livewell.account_patientsection where pid = ? order by record_time";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, pid);
            //  pst.setString(2, on_date);
            rs = pst.executeQuery();
            if (rs.next()) {
                rs.getString("bal");

                if (!rs.wasNull()) {
                    ps_lbl_debt.setText(rs.getString("bal"));
                } else {
                    ps_lbl_debt.setText("0");
                }
            }

            if (!ps_lbl_curr_cost.getText().trim().isEmpty() && !ps_lbl_debt.getText().trim().isEmpty()) {
                current_cost = Double.parseDouble(ps_lbl_curr_cost.getText());
                current_debt = Double.parseDouble(ps_lbl_debt.getText());
                current_toPay = current_cost + current_debt;
                ps_lbl_total.setText(String.valueOf(current_toPay));
                txt_pay.setText(String.valueOf(current_toPay));//here the value of txt_pay is overriden by current_toPay
                debt = current_toPay * 1;
            } else {

            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
