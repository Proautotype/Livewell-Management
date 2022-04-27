/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Laboratory;

import com.connection.conclass;
import com.semantics.semanticsClass;
import com.services.urineRE_services;
import com.tables.lab_microscopy;
import com.tables.lab_stools;
import com.tables.lab_urine;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class Urine_REController implements Initializable {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = "";

    @FXML
    private MenuItem menu_save;
    @FXML
    private Accordion parent_anchorDion;
    @FXML
    private GridPane parent_urinalysis;
    @FXML
    private TextField txt_appearance;
    @FXML
    private TextField txt_UROBILINOGEN;
    @FXML
    private TextField txt_colour;
    @FXML
    private TextField txt_leukocytes;
    @FXML
    private TextField txt_BILIRUBIN;
    @FXML
    private TextField txt_BLOOD;
    @FXML
    private TextField txt_NITRITE;
    @FXML
    private TextField txt_PH;
    @FXML
    private TextField txt_SPECIFIC_GRAVITY;
    @FXML
    private TextField txt_PROTEIN;
    @FXML
    private TextField txt_GLUCOSE;
    @FXML
    private TextField txt_KETONES;
    @FXML
    private GridPane parent_microscopy;
    @FXML
    private TextField txt_PLUS_CELLS;
    @FXML
    private TextField txt_EPITH_CELLS;
    @FXML
    private TextField txt_Rbs;
    @FXML
    private TextField txt_YEAST_CELLS;
    @FXML
    private TextField txt_CASTS;
    @FXML
    private TextField txt_CRYSTALS;
    @FXML
    private TextField txt_T_VAGINALIS;
    @FXML
    private TextField txt_S_HAEMATOBIUM;
    @FXML
    private TextField txt_BACTERIA;
    @FXML
    private TextField txt_OTHERS;
    @FXML
    private TableView<lab_microscopy> tableView_microscopy;
    @FXML
    private VBox parent_stools;
    @FXML
    private TextArea txt_MACROSCOPY;
    @FXML
    private TextArea txt_MICROSCOPY;
    @FXML
    private TextArea txt_OCULT_BLOOD;

    String operative_section = "";
    String listened_OpS = "";
    @FXML
    private TitledPane tPane_urine;
    @FXML
    private TitledPane tPane_microscopy;
    @FXML
    private TitledPane tPane_stools;

    urineRE_services ures = new urineRE_services();
    @FXML
    private TableView<lab_urine> table_urine;
    @FXML
    private TableColumn<?, ?> col_urine_pid;
    @FXML
    private TableColumn<?, ?> col_urine_appearance;
    @FXML
    private TableColumn<?, ?> col_urine_colour;
    @FXML
    private TableColumn<?, ?> col_urine_leukocytes;
    @FXML
    private TableColumn<?, ?> col_urine_uroglobin;
    @FXML
    private TableColumn<?, ?> col_urine_bilirubin;
    @FXML
    private TableColumn<?, ?> col_urine_blood;
    @FXML
    private TableColumn<?, ?> col_urine_nitrate;
    @FXML
    private TableColumn<?, ?> col_urine_ph;
    @FXML
    private TableColumn<?, ?> col_urine_spGravity;
    @FXML
    private TableColumn<?, ?> col_urine_protein;
    @FXML
    private TableColumn<?, ?> col_urine_glucose;
    @FXML
    private TableColumn<?, ?> col_urine_ketones;
    @FXML
    private TableColumn<?, ?> col_microscopy_pid;
    @FXML
    private TableColumn<?, ?> col_stool_pid;
    private String pid = "";
    @FXML
    private MenuItem menu_update;

    @FXML
    private Label lbl_urine_user_details;

    lab_urine lbu = new lab_urine();
    @FXML
    private TextField txt_general_srch_box;
    @FXML
    private ComboBox<String> cbo_srch_pattern;
    @FXML
    private TableColumn<?, ?> col_microscopy_pCells;
    @FXML
    private TableColumn<?, ?> col_microscopy_eCells;
    @FXML
    private TableColumn<?, ?> col_microscopy_rbs;
    @FXML
    private TableColumn<?, ?> col_microscopy_yCells;
    @FXML
    private TableColumn<?, ?> col_microscopy_casts;
    @FXML
    private TableColumn<?, ?> col_microscopy_crystals;
    @FXML
    private TableColumn<?, ?> col_microscopy_tVaginals;
    @FXML
    private TableColumn<?, ?> col_microscopy_sHaema;
    @FXML
    private TableColumn<?, ?> col_microscopy_bacteria;
    @FXML
    private TableColumn<?, ?> col_microscopy_others;
    @FXML
    private TableColumn<?, ?> col_microscopy_date;
    @FXML
    private ComboBox<String> cbo_microscopy;
    @FXML
    private TextField txt_microscopy_srch;
    @FXML
    private ComboBox<String> cbo_stools;
    @FXML
    private TextField txt_stools_srch;
    @FXML
    private TableView<lab_stools> tableView_stools;
    @FXML
    private TableColumn<?, ?> col_stools_macroscopy;
    @FXML
    private TableColumn<?, ?> col_stools_microscopy;
    @FXML
    private TableColumn<?, ?> col_stools_ocult_blood;
    @FXML
    private TableColumn<?, ?> col_stools_date;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = conclass.livewell_localhost();
        pid = LaboratoryController.pid;
        load_userDetails(pid);

        column_settings();

        inSql_Settings(); // initialize values for parent comboboxes and text fields
    }

    void load_urine_ids() {
        cbo_srch_pattern.setItems(FXCollections.observableArrayList("All", "Today"));
        sql = "select Distinct pid from lab_urine order by record_time asc";
        ObservableList<String> items = FXCollections.observableArrayList();
        ObservableList<String> id_items = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                id_items.add(rs.getString("pid"));
            }
            TextFields.bindAutoCompletion(txt_general_srch_box, id_items);
        } catch (SQLException e) {
            System.out.println(e);
        }

        sql = "select Distinct on_date from lab_urine order by record_time asc";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                items.add(rs.getString("on_date"));
            }
            cbo_srch_pattern.getItems().addAll(items);
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    void load_microscopy_ids() {
        cbo_microscopy.setItems(FXCollections.observableArrayList("All", "Today"));
        sql = "select Distinct pid from lab_microscopy order by record_time asc";
        ObservableList<String> on_dateItems = FXCollections.observableArrayList();
        ObservableList<String> id_items = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                id_items.add(rs.getString("pid"));
            }
            TextFields.bindAutoCompletion(txt_microscopy_srch, id_items);
        } catch (SQLException e) {
            System.out.println(e);
        }

        sql = "select Distinct on_date from lab_microscopy order by record_time asc";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                on_dateItems.add(rs.getString("on_date"));
            }
            cbo_microscopy.getItems().addAll(on_dateItems);
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    void load_stools_ids() {
        cbo_stools.setItems(FXCollections.observableArrayList("All", "Today"));
        sql = "select Distinct pid from lab_stools order by record_time asc";
        ObservableList<String> on_dateItems = FXCollections.observableArrayList();
        ObservableList<String> id_items = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                id_items.add(rs.getString("pid"));
            }
            TextFields.bindAutoCompletion(txt_stools_srch, id_items);
        } catch (SQLException e) {
            System.out.println(e);
        }

        sql = "select Distinct on_date from lab_stools order by record_time asc";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                on_dateItems.add(rs.getString("on_date"));
            }
            cbo_stools.getItems().addAll(on_dateItems);
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    void inSql_Settings() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            load_urine_ids();
            load_microscopy_ids();
            load_stools_ids();

            urine_tests_loader(semanticsClass.returneDate());  //loads urine table    
            microscopy_tests_loader(semanticsClass.returneDate()); //loads microscopy table 
            stools_tests_loader(semanticsClass.returneDate()); //loads stools table

        }));
        timeline.play();
    }

    void column_settings() {
        //urine table column settings
        col_urine_pid.setCellValueFactory(new PropertyValueFactory<>("pid"));
        col_urine_appearance.setCellValueFactory(new PropertyValueFactory<>("appearance"));
        col_urine_colour.setCellValueFactory(new PropertyValueFactory<>("colour"));
        col_urine_leukocytes.setCellValueFactory(new PropertyValueFactory<>("leukocytes"));
        col_urine_uroglobin.setCellValueFactory(new PropertyValueFactory<>("urobilinogen"));
        col_urine_bilirubin.setCellValueFactory(new PropertyValueFactory<>("bilirubin"));
        col_urine_blood.setCellValueFactory(new PropertyValueFactory<>("blood"));
        col_urine_nitrate.setCellValueFactory(new PropertyValueFactory<>("nitrite"));
        col_urine_ph.setCellValueFactory(new PropertyValueFactory<>("ph"));
        col_urine_spGravity.setCellValueFactory(new PropertyValueFactory<>("spec_gravity"));
        col_urine_protein.setCellValueFactory(new PropertyValueFactory<>("protein"));
        col_urine_glucose.setCellValueFactory(new PropertyValueFactory<>("glucose"));
        col_urine_ketones.setCellValueFactory(new PropertyValueFactory<>("ketones"));
        // microscopy table columns settings
        col_microscopy_pid.setCellValueFactory(new PropertyValueFactory<>("pid"));
        col_microscopy_pCells.setCellValueFactory(new PropertyValueFactory<>("plus_cells"));
        col_microscopy_eCells.setCellValueFactory(new PropertyValueFactory<>("epith_cells"));
        col_microscopy_rbs.setCellValueFactory(new PropertyValueFactory<>("rbs"));
        col_microscopy_yCells.setCellValueFactory(new PropertyValueFactory<>("yeast_cells"));
        col_microscopy_casts.setCellValueFactory(new PropertyValueFactory<>("casts"));
        col_microscopy_crystals.setCellValueFactory(new PropertyValueFactory<>("crystals"));
        col_microscopy_tVaginals.setCellValueFactory(new PropertyValueFactory<>("T_Vaginalis"));
        col_microscopy_sHaema.setCellValueFactory(new PropertyValueFactory<>("S_Haematobium"));
        col_microscopy_bacteria.setCellValueFactory(new PropertyValueFactory<>("bacteria"));
        col_microscopy_others.setCellValueFactory(new PropertyValueFactory<>("others"));
        col_microscopy_date.setCellValueFactory(new PropertyValueFactory<>("on_date"));
        //stool table settings
        /*
         private String pid, macroscopy, microscopy, ocult_blood,on_date;
         private Timestamp record_time;
         */
        col_stool_pid.setCellValueFactory(new PropertyValueFactory<>("pid"));
        col_stools_macroscopy.setCellValueFactory(new PropertyValueFactory<>("macroscopy"));
        col_stools_microscopy.setCellValueFactory(new PropertyValueFactory<>("microscopy"));
        col_stools_ocult_blood.setCellValueFactory(new PropertyValueFactory<>("ocult_blood"));
        col_stools_date.setCellValueFactory(new PropertyValueFactory<>("on_date"));

    }
    String in_userD = "";

    void load_userDetails(String id) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), (event) -> {
            try {
                sql = "select registration_NO,surname,other_names from livewell.patient_info where registration_NO = ? ";
                pst = conn.prepareStatement(sql);
                pst.setString(1, id);
                rs = pst.executeQuery();
                if (rs.next()) {
                    in_userD = rs.getString("registration_NO") + " : " + rs.getString("surname") + " " + rs.getString("other_names");
                    lbl_urine_user_details.setText(in_userD);
                    System.out.println(in_userD + ": my Info");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Urine_REController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }));
        timeline.play();
    }
    int count = 0;

    void urine_tests_loader(String valuer) {//search by all or today or all _date
        Service<ObservableList<lab_urine>> urine_list
                = ures.new urine().urine_test_list(valuer);
        urine_list.setOnSucceeded((event) -> {
            table_urine.setItems(urine_list.getValue());
        });
        urine_list.start();
    }

    void microscopy_tests_loader(String valuer) {//search by all or today or all _date
        Service<ObservableList<lab_microscopy>> microscopy_list
                = ures.new microscopy().microscopy_load(valuer);
        microscopy_list.setOnSucceeded((event) -> {
            tableView_microscopy.setItems(microscopy_list.getValue());
        });
        microscopy_list.start();
    }

    // load stools 
    void stools_tests_loader(String valuer) {//search by all or today or all _date
        Service<ObservableList<lab_stools>> stool_list
                = ures.new stool().stools_load(valuer);
        stool_list.setOnSucceeded((event) -> {
            tableView_stools.setItems(stool_list.getValue());
        });
        stool_list.start();

        //ures.new stool().load_stools(tableView_stools, valuer);
    }

    @FXML
    private void table_urine_mouse_click(MouseEvent event) {
        if (event.getClickCount() == 2) {
            if (!table_urine.getSelectionModel().isEmpty()) {
                lab_urine selectedItem = table_urine.getSelectionModel().getSelectedItem();
                txt_appearance.setText(selectedItem.getAppearance());
                txt_colour.setText(selectedItem.getColour());
                txt_leukocytes.setText(selectedItem.getLeukocytes());
                txt_UROBILINOGEN.setText(selectedItem.getUrobilinogen());
                System.out.println(selectedItem.getUrobilinogen());
                txt_BILIRUBIN.setText(selectedItem.getBilirubin());
                txt_BLOOD.setText(selectedItem.getBlood());
                txt_NITRITE.setText(selectedItem.getNitrite());
                txt_PH.setText(selectedItem.getPh());
                txt_SPECIFIC_GRAVITY.setText(selectedItem.getSpec_gravity());
                txt_PROTEIN.setText(selectedItem.getProtein());
                txt_GLUCOSE.setText(selectedItem.getGlucose());
                txt_KETONES.setText(selectedItem.getKetones());
                load_userDetails(selectedItem.getPid());
                pid = selectedItem.getPid();
            }
        }
    }

    @FXML
    private void do_spec_search(KeyEvent event) {

    }

    @FXML
    private void urine_make_search(ActionEvent event) {
        urine_tests_loader(txt_general_srch_box.getText());
    }

    @FXML
    private void do_urine_mob_search(ActionEvent event) {
        switch (cbo_srch_pattern.getSelectionModel().getSelectedItem()) {
            case "All":
                urine_tests_loader("*");
                menu_update.setDisable(true);
                break;
            case "Today":
                urine_tests_loader(semanticsClass.returneDate());
                menu_update.setDisable(false);
                break;
            default:
                String valuer = cbo_srch_pattern.getSelectionModel().getSelectedItem();
                System.out.println(valuer);
                urine_tests_loader(valuer);
                break;
        }
    }

    @FXML
    private void microscopy_make_search(ActionEvent event) {
        microscopy_tests_loader(txt_microscopy_srch.getText());
    }

    @FXML
    private void do_microscopy_mob_search(ActionEvent event) {
        switch (cbo_microscopy.getSelectionModel().getSelectedItem()) {
            case "All":
                microscopy_tests_loader("*");
                menu_update.setDisable(true);
                break;
            case "Today":
                microscopy_tests_loader(semanticsClass.returneDate());
                break;
            default:
                String valuer = cbo_microscopy.getSelectionModel().getSelectedItem();
                System.out.println(valuer);
                microscopy_tests_loader(valuer);
                break;
        }
    }

    @FXML
    private void stools_make_search(ActionEvent event) {
        stools_tests_loader(txt_stools_srch.getText());
    }

    @FXML
    private void do_stool_mob_search(ActionEvent event) {
        switch (cbo_stools.getSelectionModel().getSelectedItem()) {
            case "All":
                stools_tests_loader("*");
                break;
            case "Today":
                stools_tests_loader(semanticsClass.returneDate());
                break;
            default:
                String valuer = cbo_stools.getSelectionModel().getSelectedItem();
                System.out.println(valuer);
                stools_tests_loader(valuer);
                break;
        }
    }

    @FXML
    private void save_urine(ActionEvent event) {
        Service<Boolean> service = ures.new urine().add_urine_test(new lab_urine(
                pid, txt_appearance.getText(), txt_colour.getText(),
                txt_leukocytes.getText(), txt_UROBILINOGEN.getText(), txt_BILIRUBIN.getText(),
                txt_BLOOD.getText(), txt_NITRITE.getText(), txt_PH.getText(),
                txt_SPECIFIC_GRAVITY.getText(), txt_PROTEIN.getText(), txt_GLUCOSE.getText(),
                txt_KETONES.getText(), semanticsClass.returneDate(), semanticsClass.nowStamp(new Date())));
        service.setOnSucceeded((e) -> {
            semanticsClass.set_notification("Success", service.getMessage());
            urine_tests_loader(semanticsClass.returneDate());
        });
        service.setOnCancelled((e) -> {
            semanticsClass.set_notification("Error", service.getMessage());
        });
        service.start();
    }

    @FXML
    private void update_urine(ActionEvent event) {
        Service<Boolean> service = ures.new urine().update_urine_test(new lab_urine(
                pid, txt_appearance.getText(), txt_colour.getText(),
                txt_leukocytes.getText(), txt_UROBILINOGEN.getText(), txt_BILIRUBIN.getText(),
                txt_BLOOD.getText(), txt_NITRITE.getText(), txt_PH.getText(),
                txt_SPECIFIC_GRAVITY.getText(), txt_PROTEIN.getText(), txt_GLUCOSE.getText(),
                txt_KETONES.getText(), semanticsClass.returneDate(), semanticsClass.nowStamp(new Date())));
        service.setOnSucceeded((e) -> {
            urine_tests_loader(semanticsClass.returneDate());
        });
        service.setOnCancelled((e) -> {
            semanticsClass.set_notification("Error", "An error occured");
        });
        service.start();
    }

    @FXML
    private void save_microscopy(ActionEvent event) {
        System.out.println(operative_section);
        Service<Boolean> Mservice = ures.new microscopy().add_microscopy(new lab_microscopy(
                pid, txt_PLUS_CELLS.getText(), txt_EPITH_CELLS.getText(),
                txt_Rbs.getText(), txt_YEAST_CELLS.getText(), txt_CASTS.getText(),
                txt_CRYSTALS.getText(), txt_T_VAGINALIS.getText(), txt_S_HAEMATOBIUM.getText(),
                txt_BACTERIA.getText(), txt_OTHERS.getText(), semanticsClass.returneDate(),
                semanticsClass.nowStamp(new Date())));
        Mservice.setOnSucceeded((e) -> {
            microscopy_tests_loader(semanticsClass.returneDate());
        });
        Mservice.setOnCancelled((e) -> {
            semanticsClass.set_notification("Error", Mservice.getMessage());
        });
        Mservice.start();
    }

    @FXML
    private void update_microscopy(ActionEvent event) {
        Service<Boolean> Mservice = ures.new microscopy().update_microscopy(new lab_microscopy(
                pid, txt_PLUS_CELLS.getText(), txt_EPITH_CELLS.getText(),
                txt_Rbs.getText(), txt_YEAST_CELLS.getText(), txt_CASTS.getText(),
                txt_CRYSTALS.getText(), txt_T_VAGINALIS.getText(), txt_S_HAEMATOBIUM.getText(),
                txt_BACTERIA.getText(), txt_OTHERS.getText(),
                semanticsClass.returneDate(),
                semanticsClass.nowStamp(new Date())));
        Mservice.setOnSucceeded((e) -> {
            semanticsClass.set_notification("Error", Mservice.getMessage());
            microscopy_tests_loader(semanticsClass.returneDate());
        });
        Mservice.setOnCancelled((e) -> {
            semanticsClass.set_notification("Error", Mservice.getMessage());
            semanticsClass.set_notification("Error", "An error occured");
        });
        Mservice.start();
    }

    @FXML
    private void save_stools(ActionEvent event) {
        Service<Boolean> Sservice = ures.new stool().add_stool(new lab_stools(
                pid, txt_MACROSCOPY.getText(), txt_MICROSCOPY.getText(), txt_OCULT_BLOOD.getText(),
                semanticsClass.returneDate(), semanticsClass.nowStamp(new Date())
        ));
        Sservice.setOnSucceeded((e) -> {
            semanticsClass.set_notification("Success", Sservice.getMessage());
            stools_tests_loader(semanticsClass.returneDate());
        });
        Sservice.setOnCancelled((e) -> {
            semanticsClass.set_notification("Error", Sservice.getMessage());
        });
        Sservice.start();
    }

    @FXML
    private void update_stools(ActionEvent event) {
        Service<Boolean> Sservice = ures.new stool().update_stool(new lab_stools(
                pid, txt_MACROSCOPY.getText(), txt_MICROSCOPY.getText(), txt_OCULT_BLOOD.getText(),
                semanticsClass.returneDate(), semanticsClass.nowStamp(new Date())
        ));
        Sservice.setOnSucceeded((e) -> {
            semanticsClass.set_notification("Success", Sservice.getMessage());
            stools_tests_loader(semanticsClass.returneDate());
        });
        Sservice.setOnCancelled((e) -> {
            semanticsClass.set_notification("Error", Sservice.getMessage());
        });
        Sservice.start();
    }

}
