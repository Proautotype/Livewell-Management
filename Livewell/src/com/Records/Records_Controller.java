/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Records;

import com.Amendment.ChangePassword_Controller;
import com.connection.conclass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.semantics.semanticsClass;
import com.signIn.SignInController;
import com.tables.records_history_table;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class Records_Controller implements Initializable {

    @FXML
    private AnchorPane root_Pane;
    @FXML
    private MenuItem fullScreen;
    @FXML
    private JFXToggleButton root_tog_en;
    @FXML
    private JFXComboBox<String> root_cbo_search_by;
    @FXML
    private Pane search_Pane;
    @FXML
    private Label search_lbl_items_found;
    @FXML
    private JFXListView<String> search_list_results;
    @FXML
    private AnchorPane pane_records;
    @FXML
    private JFXTextField personal_txt_regis_cod;
    @FXML
    private JFXTextField personal_txt_surname;
    @FXML
    private JFXTextField personal_txt_Onames;
    @FXML
    private JFXDatePicker personal_DOB;
    @FXML
    private JFXComboBox<String> personal_cbo_Mar_Stat;
    @FXML
    private JFXComboBox<String> personal_cbo_gender;
    @FXML
    private JFXComboBox<String> about_txt_occupation;
    @FXML
    private JFXComboBox<String> about_cbo_religion;
    @FXML
    private JFXTextField about_txt_phone;
    @FXML
    private JFXTextField about_txt_HD;
    @FXML
    private JFXTextField txt_gps;
    @FXML
    private JFXTextField about_txt_email;
    @FXML
    private JFXTextField about_txt_NR;
    @FXML
    private JFXTextField about_txt_NR_con;
    @FXML
    private JFXTextField loc_txt_district;
    @FXML
    private JFXComboBox<String> loc_cbo_SDis;
    @FXML
    private JFXTextField loc_txt_HIS_num;
    @FXML
    private JFXTextField loc_txt_HIS_ID;
    @FXML
    private JFXRadioButton opd_rb_insurance_yes;
    @FXML
    private ToggleGroup rb_insurance_poses1;
    @FXML
    private JFXRadioButton opd_rb_insurance_no;
    @FXML
    private JFXRadioButton opd_rb_crest_new;
    @FXML
    private ToggleGroup rb_crest_old_new1;
    @FXML
    private JFXRadioButton opd_rb_crest_old;
    @FXML
    private JFXTextField opd_txt_ccc;
    @FXML
    private Pane control_pane111;
    @FXML
    private Label opd_lbl_patient_no;
    @FXML
    private Label opd_lbl_todays_id;
    @FXML
    private JFXButton opd_add_pat_history;
    @FXML
    private JFXButton opd_update_pat_history;
    @FXML
    private JFXButton opd_delete_pat_history;
    @FXML
    private Label opd_lbl_count;
    @FXML
    private JFXListView<String> opd_list_nums_here;
    @FXML
    private PieChart chart_front_today_hist;
    @FXML
    private TableView<records_history_table> table_hist;
    @FXML
    private TableColumn<?, ?> table_hist_ccc;
    @FXML
    private TableColumn<?, ?> table_hist_pn;
    @FXML
    private TableColumn<?, ?> table_hist_crest;
    @FXML
    private TableColumn<?, ?> table_hist_nhis;
    @FXML
    private JFXComboBox<String> hist_cbo_hist_home;
    @FXML
    private JFXButton btn_print;
    @FXML
    private JFXProgressBar web_process_progress;
    @FXML
    private WebView webview_ccc;
    @FXML
    private TextField txt_web_search;
    String yesNoNhis, yesNoCrest = "";
    String sql = "";
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    //user Credentials
    public static String username, password = "";

    int InsertfailedCount = 0;
    @FXML
    private MaskerPane parentMasker;
    private String setLoadHisDetails = "";
    private String rec_string;
    private String date_of_rec;
    private int females;
    private int males;
    private String CWD = "records_deptment";
    private ObservableList<records_history_table> event;
    @FXML
    private MenuItem menu_create_patient;
    @FXML
    private MenuItem menu_update_patient;
    @FXML
    private MaskerPane load_masker;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(4), e -> {
            username = SignInController.username;
            conn = conclass.livewell_localhost();
            parentMasker.setVisible(false);
            event = FXCollections.observableArrayList();
            setAllComboBoxesIniValues();
            YesNoNhisCrest();
            tryToCreateNewOPDhome();
            Load_pie_chart_female();
            loadOPDhome_history();
            setCellTable();
            loadPatientHistoryToTable();
            semanticsClass.numbersOnly(about_txt_phone);
        }));
        timer.play();

    }

    void setCellTable() {
        table_hist_ccc.setCellValueFactory(new PropertyValueFactory<>("ccc"));
        table_hist_pn.setCellValueFactory(new PropertyValueFactory<>("pn"));
        table_hist_nhis.setCellValueFactory(new PropertyValueFactory<>("nhis"));
        table_hist_crest.setCellValueFactory(new PropertyValueFactory<>("crest"));
    }

    @FXML
    void quick_load() {
        sql = "select Registration_NO from livewell.patient_info where Registration_NO like '%" + personal_txt_regis_cod.getText() + "%' ";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            search_list_results.getItems().removeAll(search_list_results.getItems());
            while (rs.next()) {
                search_list_results.getItems().addAll(rs.getString("registration_no"));
                search_lbl_items_found.setText("");
                search_lbl_items_found.setText("" + search_list_results.getItems().size());
            }
        } catch (SQLException e) {
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
            }
        }
    }

    @FXML
    void quick_load_by_names() {
        sql = "select Registration_NO from livewell.patient_info where surname like '%" + personal_txt_surname.getText() + "%' ";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            search_list_results.getItems().removeAll(search_list_results.getItems());
            while (rs.next()) {
                search_list_results.getItems().addAll(rs.getString("registration_no"));
                search_lbl_items_found.setText("");
                search_lbl_items_found.setText("" + search_list_results.getItems().size());
            }
        } catch (SQLException e) {
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
            }
        }
    }

    void loadPatientHistoryToTable() {
        table_hist.getItems().clear();
        sql = "select * from opd_history where last_date = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, semanticsClass.returneDate());
            rs = pst.executeQuery();
            while (rs.next()) {
                event.add(new records_history_table(rs.getString("ccc"), rs.getString("patient_num"), rs.getString("crest"), rs.getString("nhis_stat")));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        table_hist.setItems(event);
    }

    void loadOPDhome_history() {
        try {
            sql = "select * from opd_history_home where rec_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, semanticsClass.returneDate());
            rs = pst.executeQuery();
//            hist_cbo_hist_home.getItems().clear();
            if (rs.next()) {
                opd_lbl_todays_id.setText(rs.getString("date_of_rec"));
                date_of_rec = rs.getString("date_of_rec");
            }
            pst.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    void checkCrestNHIS_RB_Selection() {
        yesNoNhis = "YES";
        yesNoCrest = "OLD";
    }

    void setAllComboBoxesIniValues() {
        ObservableList<String> list = null;
        personal_cbo_gender.setItems(list = FXCollections.observableArrayList("Male", "Female"));
        personal_cbo_Mar_Stat.setItems(list = FXCollections.observableArrayList("Single", "Married", "Divorced", "Widowed"));
        about_cbo_religion.setItems(list = FXCollections.observableArrayList("Christian", "Muslim", "Others"));
//        loc_cbo_HIS.setItems(list = FXCollections.observableArrayList("National Health Insurance Scheme"));
        root_cbo_search_by.setItems(list = FXCollections.observableArrayList("ID", "Insurance NO."));

        loc_cbo_SDis.setItems(list = FXCollections.observableArrayList("Oyoko", "Suhyen", "Jumapo", "Asokore", "Effiduase", "Koforidua").sorted());
        about_txt_occupation.setItems(list = FXCollections.observableArrayList("Hair Dresser", "Trader", "Clothing", "Teacher", "Nurse", "Cook", "Repairs", "Student", "Banker", "Plumber", "Pupil").sorted());
    }

    void YesNoNhisCrest() {
        opd_rb_insurance_yes.setOnAction((arg0) -> {
            yesNoNhis = "YES";
            opd_txt_ccc.setEditable(true);
            // setNotificator(root_lbl_notificator, yesNoNhis);
        });
        opd_rb_insurance_no.setOnAction((arg0) -> {
            yesNoNhis = "NO";
            opd_txt_ccc.setEditable(false);
            opd_txt_ccc.setText("0");
            /// setNotificator(root_lbl_notificator, yesNoNhis);
        });
        opd_rb_crest_old.setOnAction((arg0) -> {
            yesNoCrest = "OLD";
            //setNotificator(root_lbl_notificator, yesNoCrest);
        });
        opd_rb_crest_new.setOnAction((arg0) -> {
            yesNoCrest = "NEW";
            // setNotificator(root_lbl_notificator, yesNoCrest);
        });

    }

    void tryToCreateNewOPDhome() {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        int day = LocalDate.now().getDayOfMonth();
        Random rn = new Random();
        try {
            sql = "insert into opd_history_home(rec_id,date_Of_Rec) values(?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, semanticsClass.returneDate());
            pst.setString(2, String.valueOf(year) + String.valueOf(month) + String.valueOf(rn.nextInt(9999)));
            pst.execute();
            // setNotificator(root_lbl_notificator, "New OPD history is opened");
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
//                semanticsClass.set_notification("Error", "Already availables");
                System.out.println("Already availables");
            }
//             semanticsClass.set_notification("Error", e.getMessage() +":::" + e.getErrorCode());
            System.out.println(e.getMessage() + ":::" + e.getErrorCode());
        }
    }

    @FXML
    private void setFullScreen(ActionEvent event) {
    }

    @FXML
    private void pane_enableMent(ActionEvent event) {
    }

    @FXML
    private void load_listBy_Selection(ActionEvent event) {
        switch (root_cbo_search_by.getSelectionModel().getSelectedIndex()) {
            case 0:
                sql = "select * from patient_info order by registration_no";
                conn = conclass.livewell_localhost();
                try {
                    pst = conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    ObservableList<String> list = null;
                    search_list_results.getItems().removeAll(search_list_results.getItems());
                    String currents;
                    while (rs.next()) {
                        currents = (rs.getString("registration_no"));
                        list = FXCollections.observableArrayList(currents).sorted();
                        list.sorted();
                        search_list_results.getItems().addAll(list);
                        search_lbl_items_found.setText("");
                        search_lbl_items_found.setText("" + search_list_results.getItems().size());
                    }
                } catch (SQLException e) {
                }
                break;

            case 1:
                sql = "select * from patient_info order by health_Insu_no";
                try {
                    conn = conclass.livewell_localhost();
                    pst = conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    ObservableList<String> list = null;
                    search_list_results.getItems().removeAll(search_list_results.getItems());
                    String currents;
                    while (rs.next()) {
                        currents = (rs.getString("health_Insu_no"));
                        list = FXCollections.observableArrayList(currents).sorted();
                        list.sorted();
                        search_list_results.getItems().addAll(list);
                        search_lbl_items_found.setText("");
                        search_lbl_items_found.setText("" + search_list_results.getItems().size());
                    }
                } catch (SQLException e) {
                }
                break;
            default:
                break;
        }
    }

    @FXML
    private void srch_List_toFields_keyMove(KeyEvent event) {
        if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
            String selectedItem = search_list_results.getSelectionModel().getSelectedItem();
            sql = "select * from patient_info where registration_no = ? ";
            try {
                pst = conn.prepareStatement(sql);
//                pst.setString(1, selectedItem);
//                pst.setString(2, selectedItem);
//                pst.setString(3, selectedItem);
//                pst.setString(4, selectedItem);
                rs = pst.executeQuery();
                if (rs.next()) {
                    menu_create_patient.setDisable(true);
                    menu_update_patient.setDisable(false);
                    personal_txt_regis_cod.setText(rs.getString("registration_no"));//registration code
                    opd_lbl_patient_no.setText(rs.getString("registration_no"));
                    setLoadHisDetails = rs.getString("registration_no");

                    personal_txt_surname.setText(rs.getString("surname"));//surname
                    personal_txt_Onames.setText(rs.getString("other_names"));//other names

                    personal_cbo_gender.setValue(rs.getString("sex"));
                    personal_cbo_Mar_Stat.setValue(rs.getString("marital_status"));//marital status
                    about_txt_occupation.setValue(rs.getString("occupation"));//occupation
                    about_cbo_religion.setValue(rs.getString("religion"));//religion
                    about_txt_HD.setText(rs.getString("home_address"));//home address
                    about_txt_phone.setText(rs.getString("telephone"));//phone
                    about_txt_email.setText(rs.getString("email"));//email
                    about_txt_NR.setText(rs.getString("nearest_relative"));//nearest_relative
                    about_txt_NR_con.setText(rs.getString("contact_nearest_relative"));//contact_nearest_relative
                    loc_txt_district.setText(rs.getString("district"));//district
                    loc_cbo_SDis.setValue(rs.getString("locality"));//locality / sub-district
                    //               loc_cbo_HIS.setValue(FXCollections.observableArrayList(rs.getString("name_his")));
                    loc_txt_HIS_num.setText(rs.getString("health_insu_no"));//health_insu_no
                    loc_txt_HIS_ID.setText(rs.getString("health_insu_id"));//health id;

                    rs.getDate("_dob");//date of birth
                    if (!rs.wasNull()) //testing if resultset is null
                    {
                        personal_DOB.getEditor().setText(rs.getDate("_dob").toString());
                        personal_DOB.setValue(rs.getDate("_dob").toLocalDate());
                    } else {
                        personal_DOB.getEditor().setText("");
                        personal_DOB.setValue(null);
                    }

                }

            } catch (SQLException e) {
                // setNotificator(root_lbl_notificator, e.getMessage());
            } finally {
                try {
                    pst.close();
                    rs.close();
                } catch (Exception e) {
                }
            }
            updateOPDsummary();
        }
    }
    patient_info_details pd = new patient_info_details();

    @FXML
    private void srch_List_toFields_mouse(MouseEvent event) {
        String idd = search_list_results.getSelectionModel().getSelectedItem();
        Service<patient_info_details> servicer = pd.loadPatient_details(idd);
        servicer.setOnRunning(e -> {
        });
        servicer.setOnSucceeded(e -> {
            patient_info_details paID = servicer.getValue();
            menu_create_patient.setDisable(true);
            menu_update_patient.setDisable(false);

            setLoadHisDetails = paID.getReg_code();
            personal_txt_regis_cod.setText(paID.getReg_code());//registration code
            opd_lbl_patient_no.setText(paID.getReg_code());
            personal_txt_surname.setText(paID.getSurname());//surname

            personal_txt_Onames.setText(paID.getO_names());//other names

            personal_cbo_gender.setValue(paID.getSex());
            personal_cbo_Mar_Stat.setValue(paID.getMarital_status());//marital status
            about_txt_occupation.setValue(paID.getOccupation());//occupation
            about_cbo_religion.setValue(paID.getReligion());//religion
            about_txt_HD.setText(paID.getHome_addr());//home address
            about_txt_phone.setText(paID.getTelephone());//phone
            about_txt_email.setText(paID.getEmail());//email
            about_txt_NR.setText(paID.getN_rel());//nearest_relative
            about_txt_NR_con.setText(paID.getN_rel_con());//contact_nearest_relative
            loc_txt_district.setText(paID.getDistrict());//district
            loc_cbo_SDis.setValue(paID.getLocality());//locality / sub-district
//                    loc_cbo_HIS.setValue(FXCollections.observableArrayList(rs.getString("name_his")));
            loc_txt_HIS_num.setText(paID.getInsu_no());//health_insu_no
            loc_txt_HIS_ID.setText(paID.getInsu_id());//health id;

            root_tog_en.setSelected(false);
        });
        servicer.setOnRunning(ee -> {
            load_masker.visibleProperty().bind(servicer.runningProperty());
            load_masker.textProperty().bind(servicer.messageProperty());
        });
        servicer.start();
    }

    @FXML
    private void add_NewPatient(ActionEvent event) {
        Service<Boolean> server = add_Patient_task();
        server.setOnSucceeded(e -> {
            PopOver pop = new PopOver(new Label("Patient created successfully"));
            pop.setArrowSize(20);
            pop.setHeaderAlwaysVisible(true);
            pop.setTitle("Success");
            pop.show(this.about_txt_HD.getParent());
        });
        server.setOnRunning(e -> {
            parentMasker.visibleProperty().bind(server.runningProperty());
            parentMasker.textProperty().bind(server.messageProperty());
        });
        server.setOnFailed(e -> {
            InsertfailedCount++;
            if (!(InsertfailedCount >= 3)) {
                server.restart();
            } else {
                server.cancel();
            }
        });
        server.start();
    }

    @FXML
    private void up(ActionEvent event) {
    }

    @FXML
    private void check_relation_exist(KeyEvent event) {
    }

    @FXML
    private void open_search_scene(MouseEvent event) {
    }

    @FXML
    private void insur_num_up(KeyEvent event) {
    }

    @FXML
    private void insur_id_up(KeyEvent event) {
    }

    @FXML
    private void rec_typing(MouseEvent event) {
    }
    String sql_insert = "";

    @FXML
    private void opd_addToHistory(ActionEvent event) {
        conn = conclass.livewell_localhost();
        Alert rec_type = new Alert(Alert.AlertType.CONFIRMATION);

        GridPane gPane = new GridPane();
        RadioButton folder = new RadioButton("Folder");
        RadioButton tracer = new RadioButton("Tracer");
        folder.setSelected(true);

        ToggleGroup rec_togler = new ToggleGroup();
        folder.setToggleGroup(rec_togler);
        tracer.setToggleGroup(rec_togler);

        gPane.add(folder, 0, 0);
        gPane.add(tracer, 1, 0);
        gPane.setPrefSize(100, 50);
        gPane.setHgap(10);

        rec_type.getDialogPane().setContent(gPane);

        rec_string = "Folder";
        folder.setOnAction((arg0) -> {
            rec_string = "Folder";
            // setNotificator(root_lbl_notificator, rec_string);
        });
        tracer.setOnAction((arg0) -> {
            rec_string = "Tracer";
            // setNotificator(root_lbl_notificator, rec_string);
        });
        boolean worker = false;

        Optional<ButtonType> result = rec_type.showAndWait();
        if (result.get() == ButtonType.OK) {
            worker = true;
            sql_insert = "insert into opd_history(ccc,patient_num,nhis_stat,crest,rec_id,last_date,on_transit,opd_out_time,rec_type) values(?,?,?,?,?,?,?,?,'" + rec_string + "')";
        } else {
            worker = false;
            sql_insert = "insert into opd_history(ccc,patient_num,nhis_stat,crest,rec_id,last_date,on_transit,opd_out_time,rec_type) values(?,?,?,?,?,?,?,?,'" + rec_string + "')";
        }
        Date now = new Date();
        if (worker) {
            Service<Boolean> service = new Service<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            try {
                                sql = "select * from patient_info where registration_no = ? or registration_no = ?";
                                pst = conn.prepareStatement(sql);
                                pst.setString(1, personal_txt_regis_cod.getText());
                                pst.setString(2, setLoadHisDetails);
                                rs = pst.executeQuery();
                                if (rs.next()) {
                                    pst.close();
                                    rs.close();
                                    try {
                                        pst = conn.prepareStatement(sql_insert);
                                        pst.setString(1, opd_txt_ccc.getText());
                                        pst.setString(2, setLoadHisDetails);
                                        pst.setString(3, yesNoNhis);
                                        pst.setString(4, yesNoCrest);
                                        pst.setString(5, date_of_rec);
                                        pst.setString(6, semanticsClass.returneDate());
                                        pst.setString(7, "yes");
                                        pst.setTimestamp(8, semanticsClass.nowStamp(now));
                                        
                                        if (pst.execute()) {
                                        }

                                        opd_txt_ccc.setText("0");
                                        //  setNotificator(root_lbl_notificator, "Added to today's OPD history");
                                        //semanticsClass.set_notification("Success", setLoadHisDetails + " added to today's OPD history");                                       

                                    } catch (SQLException e) {
                                        //  setNotificator(root_lbl_notificator, e.getMessage());
                                        System.out.println(e + " | " + e.getErrorCode());
                                        if (e.getErrorCode() == 1062) {                                          
                                           cancel();
                                            updateMessage("Patient -> "+ setLoadHisDetails + " Patient already added to today's history");
                                            Thread.sleep(1500);
                                        }
                                    } finally {
                                        try {
                                            pst.close();
                                            rs.close();
                                        } catch (SQLException e) {
                                        }
                                    }
                                }
                            } catch (SQLException e) {
                            }
                            return true;
                        }
                    };

                }
            };
            service.setOnRunning(e -> {
            });
            service.setOnSucceeded(e -> {
                updateOPDsummary();
                Load_pie_chart_female();
                loadPatientHistoryToTable();
            });
            service.start();
        }
    }

    @FXML

    private void opd_update_ToHistory(ActionEvent event) {
    }

    @FXML
    private void opd_delete_ToHistory(ActionEvent event) {
        String myVal = table_hist.getSelectionModel().getSelectedItem().getPn();
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        //To change body of generated methods, choose Tools | Templates.
                        if (!(date_of_rec.isEmpty()) && !table_hist.getSelectionModel().isEmpty()) {
                            sql = "DELETE FROM opd_history WHERE patient_num = '" + myVal + "' AND rec_id = '" + date_of_rec + "' ";
                            try {
                                pst = conn.prepareStatement(sql);
                                pst.execute();

                            } catch (SQLException e) {
                                semanticsClass.SySoutException(e);
                                cancel();
                            }
                        }
                        return true;
                    }
                };
            }
        };

        service.setOnRunning(e -> {
            parentMasker.visibleProperty().bind(service.runningProperty());
        });
        service.setOnCancelled(e -> {
            semanticsClass.set_notification("Success", "Patient with ID " + myVal + " has been removed from todays record");;
        });
        service.setOnSucceeded(e -> {
            updateOPDsummary();
            Load_pie_chart_female();
            loadPatientHistoryToTable();
        });
        service.start();
    }

    @FXML
    private void fill_all_from_table(MouseEvent event) {
    }

    @FXML
    private void load_previous_history_mouse(MouseEvent event) {
    }

    @FXML
    private void load_previous_history(ActionEvent event) {
    }

    @FXML
    private void print_sth_hist(ActionEvent event) {
    }

    @FXML
    private void update_old_patient(ActionEvent event) {
        udate_Patient();
    }

    @FXML
    private void reset(ActionEvent event) {
        resetter();
    }

    @FXML
    private void web_zoomer(ScrollEvent event) {
    }

    @FXML
    private void go_search(ActionEvent event) {
    }

    @FXML
    private void open_ccc_webpage(ActionEvent event) {
    }

    @FXML
    private void switch_toggle(KeyEvent event) {
    }

    public Service<Boolean> add_Patient_task() {
        Service<Boolean> server = new Service() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        updateMessage("Please wait while we create patient account");
                        Thread.sleep(200);
                        new_Patient();
                        return null;
                    }
                };
            }
        };
        return server;
    }

    @FXML
    private void new_patient(ActionEvent event) {
        menu_create_patient.setDisable(false);
        resetter();
        menu_update_patient.setDisable(true);
    }

    @FXML
    private void load_password_Manager(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/Amendment/changePassword.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
//            stage.setAlwaysOnTop(true);
//            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            Timeline timer = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                ChangePassword_Controller.username = username;
                ChangePassword_Controller.CWD = CWD;
            }));
            timer.play();
            stage.show();

        } catch (IOException e) {
            semanticsClass.SySoutException(e);
        }
    }

    private void new_Patient() {
        if ((!personal_DOB.getValue().toString().isEmpty())) {
            sql = "insert into patient_info(registration_no,dor_firsAtt,surname,other_names,_dob,_age,sex,marital_status"
                    + ",occupation,religion,home_address,telephone,email,nearest_relative, contact_nearest_relative"
                    + ",district,locality,health_insu_no, health_insu_id) "
                    + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            int age = semanticsClass.get_years_from_jfxDatepicker(personal_DOB);
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, personal_txt_regis_cod.getText().trim().toUpperCase());//registration code
                pst.setString(2, semanticsClass.returneDate());//date of first attendance
                pst.setString(3, personal_txt_surname.getText().trim().toUpperCase());//surname
                pst.setString(4, personal_txt_Onames.getText().trim().toUpperCase());//other names
                pst.setDate(5, java.sql.Date.valueOf(personal_DOB.getValue()));//date of birth
                pst.setInt(6, age);//age
                pst.setString(7, personal_cbo_gender.getSelectionModel().getSelectedItem().toUpperCase());//gender
                pst.setString(8, personal_cbo_Mar_Stat.getSelectionModel().getSelectedItem().toUpperCase());//marital status
                pst.setString(9, about_txt_occupation.getValue().trim().toUpperCase());//occupation
                pst.setString(10, about_cbo_religion.getSelectionModel().getSelectedItem().toUpperCase());//religion
                pst.setString(11, about_txt_HD.getText().trim().toUpperCase());//home address
                pst.setString(12, about_txt_phone.getText().trim().toUpperCase());//phone
                pst.setString(13, about_txt_email.getText().trim().toUpperCase());//email
                pst.setString(14, about_txt_NR.getText().trim().toUpperCase());//nearest_relative
                pst.setString(15, about_txt_NR_con.getText().trim().toUpperCase());//contact_nearest_relative
                pst.setString(16, loc_txt_district.getText().trim().toUpperCase());//district
                pst.setString(17, loc_cbo_SDis.getSelectionModel().getSelectedItem().trim().toUpperCase());//locality / sub-district

                pst.setString(18, loc_txt_HIS_num.getText().trim().toUpperCase());//health_insu_no
                pst.setString(19, loc_txt_HIS_ID.getText().trim().toUpperCase());//health id;
                pst.execute();
                //personal_DOB.setStyle("-fx-background-color:none;");

            } catch (SQLException e) {
                if (e.getErrorCode() == 1062) {

                }

            } finally {
                try {
                    pst.close();
                    rs.close();
                } catch (Exception e) {
                }
            }
        } else {
            personal_DOB.setStyle("-fx-background-color:red;");

            JOptionPane.showMessageDialog(null, "ok this is error");
        }
    }

    private void udate_Patient() {
        if ((!personal_DOB.getValue().toString().isEmpty())) {
            sql = "UPDATE livewell.patient_info "
                    + "SET Registration_NO = ?, Facility_name = ?, "
                    + "DOR_firsAtt = ?, surname = ?>, "
                    + "other_names = ?>, _DOB = ?,"
                    + "_AGE = ?, SEX = ?, Marital_Status = ?, "
                    + "occupation = ?, religion = ?, "
                    + "Postal_address = ?, Home_address = ?,"
                    + "Telephone = ?, email = ?, "
                    + "nearest_relative =?, contact_nearest_relative = ?, "
                    + "District = ?, locality = ?,"
                    + "Name_HIS = ?, health_Insu_no = ?, "
                    + "health_insu_id = ?"
                    + "WHERE Registration_NO = ?;";
            int age = semanticsClass.get_years_from_jfxDatepicker(personal_DOB);
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, personal_txt_regis_cod.getText().trim().toUpperCase());//registration code
                pst.setString(2, semanticsClass.returneDate());//date of first attendance
                pst.setString(3, personal_txt_surname.getText().trim().toUpperCase());//surname
                pst.setString(4, personal_txt_Onames.getText().trim().toUpperCase());//other names
                pst.setDate(5, java.sql.Date.valueOf(personal_DOB.getValue()));//date of birth
                pst.setInt(6, age);//age
                pst.setString(7, personal_cbo_gender.getSelectionModel().getSelectedItem().toUpperCase());//gender
                pst.setString(8, personal_cbo_Mar_Stat.getSelectionModel().getSelectedItem().toUpperCase());//marital status
                pst.setString(9, about_txt_occupation.getValue().trim().toUpperCase());//occupation
                pst.setString(10, about_cbo_religion.getSelectionModel().getSelectedItem().toUpperCase());//religion
                pst.setString(11, about_txt_HD.getText().trim().toUpperCase());//home address
                pst.setString(12, about_txt_phone.getText().trim().toUpperCase());//phone
                pst.setString(13, about_txt_email.getText().trim().toUpperCase());//email
                pst.setString(14, about_txt_NR.getText().trim().toUpperCase());//nearest_relative
                pst.setString(15, about_txt_NR_con.getText().trim().toUpperCase());//contact_nearest_relative
                pst.setString(16, loc_txt_district.getText().trim().toUpperCase());//district
                pst.setString(17, loc_cbo_SDis.getSelectionModel().getSelectedItem().trim().toUpperCase());//locality / sub-district

                pst.setString(18, loc_txt_HIS_num.getText().trim().toUpperCase());//health_insu_no
                pst.setString(19, loc_txt_HIS_ID.getText().trim().toUpperCase());//health id;
                pst.execute();
                //personal_DOB.setStyle("-fx-background-color:none;");

            } catch (SQLException e) {
                if (e.getErrorCode() == 1062) {

                }

            } finally {
                try {
                    pst.close();
                    rs.close();
                } catch (Exception e) {
                }
            }
        } else {
            personal_DOB.setStyle("-fx-background-color:red;");

            JOptionPane.showMessageDialog(null, "ok this is error");
        }
    }

    void addToHistory(String pid) {
        
    }

    private void updateOPDsummary() {
        if (!setLoadHisDetails.isEmpty()) {
            sql = "select * from opd_history where patient_num = ? order by opd_out_time";

            try {
                pst.close();
                rs.close();
                pst = conn.prepareStatement(sql);
                pst.setString(1, setLoadHisDetails);
                rs = pst.executeQuery();
                ObservableList list = FXCollections.observableArrayList();
                String value = "";
                int count = 0;
//                 opd_txt_ccc.setText("0");
                while (rs.next()) {
                    //opd_lbl_count.setText(rs.getString("count(patient_num)"));
                    count++;
                    value = rs.getString("last_date") + " : " + rs.getString("rec_type");
                    list.add(value);
                }
                if (count <= 0) {
//                    opd_rb_crest_old.setSelected(false);
                    opd_rb_crest_new.setSelected(true);
                } else {
                    opd_rb_crest_old.setSelected(true);
//                      opd_rb_crest_new.setSelected(false);
                }
                opd_lbl_count.setText("" + count);
                opd_list_nums_here.setItems(list);
            } catch (SQLException ex) {
//                Logger.getLogger(DashBoardController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    void Load_pie_chart_female() {
//        number of females
//        chart_front_today_hist.setS
        if (conn != null) {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            try {

                sql = "select count(sex) from patient_info,opd_history where patient_info.registration_no = opd_history.patient_num and patient_info.sex = 'female' and opd_history.last_date = ? ";
                pst = conn.prepareStatement(sql);
                pst.setString(1, semanticsClass.returneDate());
                rs = pst.executeQuery();
                while (rs.next()) {
                    females = Integer.parseInt(rs.getString("count(sex)"));
                }
            } catch (SQLException e) {
                //  setNotificator(root_lbl_notificator, e.getMessage());
            } finally {
                try {
                    rs.close();
                    pst.close();
                } catch (SQLException e) {
                }
            }

            //number of males
            try {

                sql = "select count(sex) from patient_info,opd_history where patient_info.registration_no = opd_history.patient_num and patient_info.sex = 'male' and opd_history.last_date = ? ";
                pst = conn.prepareStatement(sql);
                pst.setString(1, semanticsClass.returneDate());
                rs = pst.executeQuery();
                while (rs.next()) {
                    males = Integer.parseInt(rs.getString("count(sex)"));
                }
                chart_front_today_hist.setData(pieChartData);
            } catch (SQLException e) {
                //  setNotificator(root_lbl_notificator, e.getMessage());
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

            chart_front_today_hist.setData(null);
            chart_front_today_hist.setData(pieChartData);            
        }
    }

    private void resetter() {
        semanticsClass.clearTextFields(personal_txt_Onames, personal_txt_regis_cod, personal_txt_surname);
        semanticsClass.clearTextFields(about_txt_HD, about_txt_NR, about_txt_NR_con, about_txt_email, about_txt_phone);
        semanticsClass.clearTextFields(loc_txt_HIS_ID, loc_txt_HIS_num, loc_txt_district);
        personal_DOB.getEditor().clear();
        personal_DOB.setValue(LocalDate.now());
        personal_cbo_Mar_Stat.setValue("");
        personal_cbo_gender.setValue("");
        about_cbo_religion.setValue("");
    }

}
