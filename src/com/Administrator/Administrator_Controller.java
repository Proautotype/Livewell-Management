/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Administrator;

import AES.AES;
import AES.random;
import com.connection.conclass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerNextArrowBasicTransition;
import com.semantics.semanticsClass;
import com.services.AdminAddUserTask;
import com.services.Administrator_load_Use_Task;
import com.services.Administrator_otherSerivices_class;
import com.sms.twilio_services;
import com.sqlObjects.Administrator_getUserDetails;

// Install the Java helper library from twilio.com/docs/libraries/java
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import com.uiObjects.admin_deptMemberList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class Administrator_Controller implements Initializable {

    @FXML
    private ImageView imgView_userImage;
    @FXML
    private TextField txt_fname;
    @FXML
    private TextField txt_sname;
    @FXML
    private TextField txt_age;
    @FXML
    private TextField txt_contact;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_hometown;
    @FXML
    private TextField txt_residence;
    @FXML
    private TextField txt_house_no;
    @FXML
    private ComboBox<String> cbo_level_education;
    @FXML
    private JFXButton btn_user_actions;
    @FXML
    private JFXDatePicker date_dob;
    @FXML
    private JFXComboBox<String> cbo_sex;

    @FXML
    private ImageView imgView_certificat;

    semanticsClass sc = new semanticsClass();
    String sql = "";
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    String CWD = "";
    String CWDi = "";
    String dept_Searched_userId = "";

    @FXML
    private Label lbl_userId;

    @FXML
    private MaskerPane createUserMasker;
    @FXML
    private TableView<Administrator_getUserDetails> table_user_details;
    @FXML
    private TableColumn col_customId;
    @FXML
    private TableColumn col_fname;
    @FXML
    private TableColumn col_sname;
    @FXML
    private TableColumn col_sex;
    @FXML
    private TableColumn col_contact;
    @FXML
    private TableColumn col_email;
    @FXML
    private TableColumn col_loe;
    @FXML
    private TableColumn col_dob;

    private Image certImageItem;
    private Image userImageItem;

    private File userImageFile;
    private File certImageFile;
    @FXML
    private Tab tab_user_mgmt_create_user;
    @FXML
    private JFXTabPane user_mgmt_tabpane;
    @FXML
    private JFXComboBox<String> cbo_viewUser_view_order;
    @FXML
    private JFXButton btn_new;
    @FXML
    private JFXButton btn_clear;
    @FXML
    private Circle imageView_deptMgmt_profileImage_clip;
    @FXML
    private ImageView imageView_deptMgmt_profile;
    @FXML
    private Circle deptMgmt_aboutImageClip;
    @FXML
    private ImageView deptMgmt_aboutImage;
    private JFXTextField deptMgmt_profile_txt_srch_anyUser;
    @FXML
    private MaskerPane parent_masker;
    @FXML
    private Label deptMgmt_lbl_userName;
    @FXML
    private Label deptMgmt_lbl_DOB;
    @FXML
    private Label deptMgmt_lbl_age;
    @FXML
    private Label deptMgmt_lbl_email;
    @FXML
    private Label deptMgmt_lbl_contact;
    @FXML
    private BorderPane deptMgmt_BorderPane;
    @FXML
    private MenuItem deptMgmt_Overall_Menu;
    @FXML
    private JFXHamburger deptMgmt_humberger;
    @FXML
    private JFXDrawer deptMgmt_Drawer;
    @FXML
    private JFXListView<admin_deptMemberList> dept_userListView;
    @FXML
    private JFXButton deptMgmt_addUser_toThisDept;
    @FXML
    private JFXDrawer deptMgmt_down_drawer;
    @FXML
    private Label lbl_department_name;
    @FXML
    private Label lbl_dept_abt;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        semanticsClass.numbersOnly(txt_contact);
        setComboDetails();
        users_table_settings();
        load_user_details();
        conn = conclass.livewell_localhost();     
        initDeptMgmt_Drawer();

        userImageFile = new File("/com/png/avatar.png");
        if (userImageFile.exists()) {
            System.out.println("User Image Exist");
        }
        certImageFile = new File("/com/png/book.png");
        if (certImageFile.exists()) {
            System.out.println("Cert Image Exist");
        }
        getDefaultImages();
    }

    void getDefaultImages() {
        try {
            File UserImage = new File("default_images/avatar.png");
            if (UserImage.exists()) {
                System.out.println("User Image Exist");
                userImageFile = UserImage;
            } else {
                System.out.println("User Image do not Exist");
            }
            File CertImage = new File("default_images/book.png");
            if (CertImage.exists()) {
                System.out.println("Cert Image Exist");
                certImageFile = CertImage;
            } else {
                System.out.println("Cert Image do not Exist");
            }

        } catch (Exception e) {
        }

    }

    void users_table_settings() {
        col_customId.setCellValueFactory(new PropertyValueFactory<>("customId"));
        col_fname.setCellValueFactory(new PropertyValueFactory<>("fname"));
        col_sname.setCellValueFactory(new PropertyValueFactory<>("sname"));
        col_sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        col_contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_loe.setCellValueFactory(new PropertyValueFactory<>("loe"));
        col_dob.setCellValueFactory(new PropertyValueFactory<>("dob"));

    }

    void load_user_details() {
        Service<ObservableList<Administrator_getUserDetails>> alut = new Administrator_load_Use_Task("*");
        alut.setOnSucceeded(e -> {
            table_user_details.setItems(alut.getValue());
        });
        alut.start();
    }

    void setComboDetails() {
        cbo_sex.setItems(FXCollections.observableArrayList("Male", "Female"));
        cbo_level_education.setItems(FXCollections.observableArrayList("Doctorate", "Diploma", "High National Diploma", "Masters", "PHD"));
        cbo_viewUser_view_order.setItems(FXCollections.observableArrayList("Department", "Sex", "Level Of Education"));
    }

    @FXML
    private void date_dob_set_age(ActionEvent event) {
        txt_age.setText(String.valueOf(semanticsClass.get_years_from_jfxDatepicker(date_dob)));
    }

    @FXML
    private void imgView_userImage_drag_over(DragEvent event) throws MalformedURLException {
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            File file = db.getFiles().get(0);
            if (!file.isDirectory()) {
                String theImage = file.toURI().toURL().toString();
                if (theImage.endsWith(".png") || theImage.endsWith(".jpg")) {
                    event.acceptTransferModes(TransferMode.LINK);
                }
            }
        }
    }

    @FXML
    private void imgView_userImage_drag_dropped(DragEvent event) throws URISyntaxException {
        Dragboard db = event.getDragboard();
        if (!btn_user_actions.getText().contentEquals("U")) {
            try {
                if (db.hasFiles()) {
                    String imageLocation = db.getFiles().get(0).toURI().toURL().toString();
                    userImageFile = db.getFiles().get(0);
                    userImageItem = new Image(imageLocation);
                    imgView_userImage.setImage(userImageItem);
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(Administrator_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Continue");
            alert.setContentText("This process will change the user image of this user");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                Service<PreparedStatement> service = new Administrator_otherSerivices_class().updateUserImage(userImageFile, lbl_userId.getText(), "userImage");
                createUserMasker.visibleProperty().bind(service.runningProperty());
                createUserMasker.progressProperty().bind(service.progressProperty());
                createUserMasker.textProperty().bind(service.messageProperty());
                service.setOnSucceeded(e -> {
                    pst = service.getValue();
                    try {
                        pst.execute();
                        doViewInDetail(lbl_userId.getText());
                        semanticsClass.set_notification("Succeeded", service.getMessage());
                    } catch (SQLException ex) {
                        Logger.getLogger(Administrator_Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                service.start();
            }
        }

    }

    private void imgView_certificate_drag_over(DragEvent event) throws MalformedURLException {
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            File file = db.getFiles().get(0);
            if (!file.isDirectory()) {
                String theImage = file.toURI().toURL().toString();
                if (theImage.endsWith(".png") || theImage.endsWith(".jpg")) {
                    event.acceptTransferModes(TransferMode.LINK);
                }
            }
        }
    }

    @FXML
    private void imgView_certificate_drag_dropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (!btn_user_actions.getText().contentEquals("U")) {
            try {
                String imageLocation = db.getFiles().get(0).toURI().toURL().toString();//for locating path of image to be displayed
                certImageFile = db.getFiles().get(0);
                certImageItem = new Image(imageLocation);
                imgView_certificat.setImage(certImageItem);

            } catch (MalformedURLException ex) {
                Logger.getLogger(Administrator_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Continue");
            alert.setContentText("This process will change the Certificate image of this user");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Service<PreparedStatement> service = new Administrator_otherSerivices_class().
                        updateUserImage(certImageFile, lbl_userId.getText(), "certImage");
                createUserMasker.visibleProperty().bind(service.runningProperty());
                createUserMasker.progressProperty().bind(service.progressProperty());
                createUserMasker.textProperty().bind(service.messageProperty());
                service.setOnSucceeded(e -> {
                    semanticsClass.set_notification("Succeeded", service.getMessage());
                    pst = service.getValue();
                    try {
                        pst.execute();
                        doViewInDetail(lbl_userId.getText());
                        semanticsClass.set_notification("Succeeded", service.getMessage());
                    } catch (SQLException ex) {
                        Logger.getLogger(Administrator_Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                service.start();
            }
        }

    }

    @FXML
    private void add_userImage(ActionEvent event) throws MalformedURLException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ALL", "*"),
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg"),
                new FileChooser.ExtensionFilter("Png Only", "*.png"),
                new FileChooser.ExtensionFilter("jpeg Only", "*.jpg")
        );
        userImageFile = fc.showOpenDialog(new Window() {
        });
        if (!(userImageFile == null)) {
            if (!btn_user_actions.getText().contentEquals("U")) {
                String location = userImageFile.getAbsoluteFile().toURI().toURL().toString();
                imgView_userImage.setImage(new Image(location));
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Continue");
                alert.setContentText("This process will change the user image of this user");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    Service<PreparedStatement> service = new Administrator_otherSerivices_class().updateUserImage(userImageFile, lbl_userId.getText(), "userImage");
                    createUserMasker.visibleProperty().bind(service.runningProperty());
                    createUserMasker.progressProperty().bind(service.progressProperty());
                    createUserMasker.textProperty().bind(service.messageProperty());
                    service.setOnSucceeded(e -> {
                        pst = service.getValue();
                        try {
                            pst.execute();
                            doViewInDetail(lbl_userId.getText());
                            semanticsClass.set_notification("Succeeded", service.getMessage());
                        } catch (SQLException ex) {
                            Logger.getLogger(Administrator_Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    service.start();
                }

            }
        }
    }

    @FXML
    private void add_cert_image(ActionEvent event) throws MalformedURLException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ALL", "*"),
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg"),
                new FileChooser.ExtensionFilter("Png Only", "*.png"),
                new FileChooser.ExtensionFilter("jpep Only", "*.jpg")
        );
        certImageFile = fc.showOpenDialog(new Window() {
        });
        if (!(certImageFile == null)) {
            if (!btn_user_actions.getText().contentEquals("U")) {
                String location = certImageFile.getAbsoluteFile().toURI().toURL().toString();
                imgView_certificat.setImage(new Image(location));
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Continue");
                alert.setContentText("This process will change the user image of this user");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Service<PreparedStatement> service = new Administrator_otherSerivices_class().
                            updateUserImage(certImageFile, lbl_userId.getText(), "certImage");
                    createUserMasker.visibleProperty().bind(service.runningProperty());
                    createUserMasker.progressProperty().bind(service.progressProperty());
                    createUserMasker.textProperty().bind(service.messageProperty());
                    service.setOnSucceeded(e -> {
                        semanticsClass.set_notification("Succeeded", service.getMessage());
                        pst = service.getValue();
                        try {
                            pst.execute();
                            doViewInDetail(lbl_userId.getText());
                            semanticsClass.set_notification("Succeeded", service.getMessage());
                        } catch (SQLException ex) {
                            Logger.getLogger(Administrator_Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    service.start();
                }

            }
        }
    }

    void Tasksave() throws SQLException {
        int years = semanticsClass.get_years_from_jfxDatepicker(date_dob);
        int contact = Integer.parseInt(txt_contact.getText());
        Administrator_DLL adll = new Administrator_DLL();
        AES aes = new AES();
        String cuustomId = aes.encrypt(new random().random_String_Value("").substring(0, 9), "1927");
        System.out.println(cuustomId.substring(0, 9));
        Service<PreparedStatement> service = new AdminAddUserTask(cuustomId.substring(0, 9),
                txt_fname.getText(), txt_sname.getText(),
                java.sql.Date.valueOf(date_dob.getValue()),
                years, cbo_sex.getValue(),
                contact,
                txt_hometown.getText(), txt_residence.getText(),
                txt_email.getText().toLowerCase(), cbo_level_education.getValue(),
                txt_house_no.getText(),
                userImageFile, certImageFile
        );

        createUserMasker.visibleProperty().bind(service.runningProperty());
        createUserMasker.progressProperty().bind(service.progressProperty());
        createUserMasker.textProperty().bind(service.messageProperty());
        service.setOnSucceeded(e -> {
            String username = txt_sname.getText();
            load_user_details();
            semanticsClass.clearTextFields(txt_age, txt_contact, txt_email, txt_fname, txt_hometown, txt_house_no, txt_residence, txt_sname);
            cbo_level_education.setValue("");
            cbo_sex.setValue("");
            date_dob.setValue(LocalDate.now());
            date_dob.getEditor().setText("");
            //send an sms to the user
            Service<Boolean> smsService = new twilio_services().sendSms("", "", "Hello " + username + " ");
        });
        service.start();
    }

    @FXML
    private void add_user_details(ActionEvent event) {
        try {
            if (btn_user_actions.getText().contentEquals("S")) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Continue");
                alert.setContentText("Click OK to continue or Cancel to abort");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Tasksave();
                } else {
                    alert.setTitle("Aborted");
                    alert.setContentText("Operation aborted");
                    alert.show();
                }
            } else if (btn_user_actions.getText().contains("U")) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Continue");
                alert.setContentText("Click OK to reconfigure user");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    updateUser();

                } else {
                    alert.setTitle("Aborted");
                    alert.setContentText("Operation aborted");
                    alert.show();
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    void updateUser() throws SQLException {
        Service<PreparedStatement> Service_updater = new Administrator_otherSerivices_class().
                updateUser(lbl_userId.getText(), txt_sname.getText(), txt_fname.getText().trim(),
                        java.sql.Date.valueOf(date_dob.getValue()), semanticsClass.get_years_from_jfxDatepicker(date_dob),
                        cbo_sex.getValue(), Integer.valueOf(txt_contact.getText().trim()),
                        txt_hometown.getText().trim(), txt_residence.getText().trim(), txt_email.getText().trim(),
                        cbo_level_education.getValue(), txt_house_no.getText().trim(),
                        userImageFile, certImageFile);

        createUserMasker.visibleProperty().bind(Service_updater.runningProperty());
        createUserMasker.textProperty().bind(Service_updater.messageProperty());

        Service_updater.setOnSucceeded(e -> {
            try {
                pst = Service_updater.getValue();
                pst.execute();
                semanticsClass.set_notification("Successful", "User reconfiguration successful");
            } catch (SQLException ex) {
                semanticsClass.SySoutException(ex);
            }
        });
        Service_updater.start();

    }

    @FXML
    private void userMgmt_ViewUser_ViewInDetail(ActionEvent event) {
        if (!table_user_details.getSelectionModel().isEmpty()) {
            int i = table_user_details.getSelectionModel().getFocusedIndex();
            if (table_user_details.getSelectionModel().isSelected(i)) {

                String customId = table_user_details.getSelectionModel().getSelectedItem().getCustomId();
                doViewInDetail(customId);
                user_mgmt_tabpane.getSelectionModel().select(tab_user_mgmt_create_user);
            }
        }

    }

    void doViewInDetail(String customId) {
        Administrator_otherSerivices_class aos = new Administrator_otherSerivices_class();
        Service<ResultSet> ser = aos.userInDetails_s(customId);
        ser.setOnSucceeded(e -> {
            rs = ser.getValue();
            try {
                if (rs.next()) {
                    String gender = rs.getString("sex");
                    if (gender.contentEquals("male")) {
                        cbo_sex.setValue("Male");
                    } else {
                        cbo_sex.setValue("Female");
                    }
                    txt_contact.setText(rs.getString("contact"));

                    lbl_userId.setText(rs.getString("customId"));
                    txt_fname.setText(rs.getString("fname"));
                    txt_sname.setText(rs.getString("sname"));
                    java.sql.Date dater = rs.getDate("dob");
                    date_dob.getEditor().setText(String.valueOf(rs.getDate("dob")));
                    date_dob.setValue(dater.toLocalDate());
                    txt_age.setText(rs.getString("age"));

                    cbo_level_education.setValue(rs.getString("loe"));
                    txt_residence.setText(rs.getString("residence"));
                    txt_hometown.setText(rs.getString("hometown"));
                    txt_email.setText(rs.getString("email"));

                    txt_house_no.setText(rs.getString("houseNum"));
                    ////
                    btn_user_actions.setText("U");
                    InputStream Uis = rs.getBinaryStream("userImage");
                    InputStream Cis = rs.getBinaryStream("certImage");
                    ////
                    OutputStream Uos = new FileOutputStream("userImage.jpg");
                    OutputStream Cos = new FileOutputStream("certImage.jpg");

                    byte[] content = new byte[1024];
                    int size = 0;
                    while ((size = Uis.read(content)) != -1) {
                        Uos.write(content, 0, size);
                    }
                    Uos.flush();

                    Image UserImage = new Image("file:userImage.jpg");
                    imgView_userImage.setImage(UserImage);

                    File myUserImg = new File("userImage.jpg");
                    userImageFile = myUserImg;
                    if (userImageFile.exists()) {
                        userImageFile = new File("userImage.jpg");
                        userImageFile.delete();
                        System.out.println("user File exist()");
                    } else {
                        System.out.println("user File don't exist()");
                    }

                    size = 0;
                    content = new byte[1024];
                    if (!(Cis == null)) {
                        while ((size = Cis.read(content)) != -1) {
                            Cos.write(content, 0, size);
                        }
                        Cos.flush();

                        //reusing image variable
                        UserImage = new Image("file:certImage.jpg");
                        imgView_certificat.setImage(UserImage);

                        myUserImg = new File("certImage.jpg");
                        certImageFile = myUserImg;
                        if (certImageFile.exists()) {
                            certImageFile = new File("certImage.jpg");
                            System.out.println("cert File exist()");
                            certImageFile.delete();
                        } else {
                            System.out.println("cert File don't exist()");
                        }
                    }

                }
            } catch (IOException | SQLException ex) {
                semanticsClass.SySoutException(ex);
            }
        });
        ser.start();
    }

    @FXML
    private void do_view_order(ActionEvent event) {
    }

    @FXML
    private void do_new_user(ActionEvent event) {
        semanticsClass.clearTextFields(txt_age, txt_contact, txt_email, txt_fname, txt_hometown, txt_house_no, txt_residence, txt_sname);
        cbo_level_education.setValue("");
        cbo_sex.setValue("");
        date_dob.setValue(LocalDate.now());
        date_dob.getEditor().setText("");
        btn_user_actions.setText("S");
        imgView_certificat.setImage(null);
        imgView_userImage.setImage(null);
        userImageFile = null;
        certImageFile = null;

    }

    @FXML
    private void do_clear_user(ActionEvent event) {
        semanticsClass.clearTextFields(txt_age, txt_contact, txt_email, txt_fname, txt_hometown, txt_house_no, txt_residence, txt_sname);
        cbo_level_education.setValue("");
        cbo_sex.setValue("");
        date_dob.setValue(LocalDate.now());
        date_dob.getEditor().setText("");
        imgView_certificat.setImage(null);
        imgView_userImage.setImage(null);
        userImageFile = null;
        certImageFile = null;
    }

    private void deptMgmt_open_userProfile(ActionEvent event) {
        fill(deptMgmt_profile_txt_srch_anyUser);
    }

    public void fill(TextField txt) {

        dept_Searched_userId = txt.getText();
        Administrator_otherSerivices_class aos = new Administrator_otherSerivices_class();
        Service<ResultSet> ser = aos.userInDetails_s(dept_Searched_userId);
        ser.setOnSucceeded(e -> {
            rs = ser.getValue();
            try {
                if (rs.next()) {

                    byte[] content = new byte[1024];
                    int size = 0;
                    OutputStream ous = new FileOutputStream("out1.jpg");

                    rs.getBinaryStream("userImage");
                    if (!rs.wasNull()) {
                        InputStream is = rs.getBinaryStream("userImage");
                        if (!(is == null)) {
                            while ((size = is.read(content)) != -1) {
                                ous.write(content, 0, size);
                            }
                            ous.flush();
                            Image imager = new Image("file:out1.jpg");
                            ImagePattern imgPa = new ImagePattern(imager);
                            imageView_deptMgmt_profileImage_clip.setFill(imgPa);
                        }
                    }

                    dept_Searched_userId = rs.getString("customId");
                    deptMgmt_lbl_userName.setText(rs.getString("fname") + " " + rs.getString("sname"));
                    deptMgmt_lbl_DOB.setText(rs.getString("dob"));
                    deptMgmt_lbl_email.setText(rs.getString("email"));
                    deptMgmt_lbl_age.setText(rs.getString("age"));
                    deptMgmt_lbl_contact.setText("0" + rs.getString("contact"));
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
        ser.start();
    }

    @FXML
    private void show_controls(ActionEvent event) {
        PopOver pop = new PopOver();
        TextField searchArea = new TextField();
        searchArea.setPromptText("Search any user here");
        Button action = new Button("Press");
        action.setOnAction(e -> {
            fill(searchArea);
        });
        HBox hbox = new HBox(searchArea, action);
        hbox.setAlignment(Pos.CENTER);
        pop.setContentNode(hbox);
        pop.centerOnScreen();
        pop.show(imageView_deptMgmt_profileImage_clip);
    }

    void initDeptMgmt_Drawer() {
        HBox hbox = new HBox();
        JFXCheckBox checkAuto = new JFXCheckBox("Auto");
        checkAuto.setSelected(true);
        ComboBox<String> theCbo = new ComboBox<>(
                FXCollections.observableArrayList(
                        "Consulting Department",
                        "Account Department",
                        "Laboratory Department",
                        "OPD Department",
                        "Pharmacy Department",
                        "Records Department",
                        "Store Department"
                ).sorted()
        );
        theCbo.setPrefWidth(300);
        Button action = new Button("Run");

        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(15);
        hbox.getChildren().addAll(checkAuto, theCbo, action);

        HamburgerNextArrowBasicTransition task = new HamburgerNextArrowBasicTransition(deptMgmt_humberger);
        task.setRate(-1);
        deptMgmt_Drawer.setSidePane(hbox);
        deptMgmt_humberger.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (deptMgmt_Drawer.isOpened()) {
                task.setRate(task.getRate() * -1);
                task.play();
                deptMgmt_Drawer.close();
            } else {
                task.setRate(task.getRate() * -1);
                task.play();
                deptMgmt_Drawer.open();
            }
        });

        theCbo.setOnAction(e -> {
            //  1	dept_rec	dept_opd	dept_lab	dept_consult	dept_pharm	dept_store	dept_finan
            switch (theCbo.getSelectionModel().getSelectedItem()) {

                case "Consulting Department":
                    CWD = "consulting_deptMent";
                    CWDi = "dept_consult";
                    lbl_department_name.setText("Consulting Dept".toUpperCase());
                    lbl_dept_abt.setText("The Consulting department diagnose patients and prescribe drugs to patient, plus other vital operations");
                    break;
                case "Account Department":
                    lbl_department_name.setText("Accounts Dept".toUpperCase());
                    lbl_dept_abt.setText("The Accounts department manages both clients and customers plus other institution's financial operations");
                    CWD = "account_deptMent";
                    CWDi = "dept_acc";
                    break;
                case "Laboratory Department":
                    lbl_department_name.setText("Laboratory Dept".toUpperCase());
                    lbl_dept_abt.setText("The Laboratory department test patients");
                    CWD = "laboratory_deptMent";
                    CWDi = "dept_lab";
                    break;
                case "OPD Department":
                    lbl_department_name.setText("OPD Dept".toUpperCase());
                    lbl_dept_abt.setText("The OPD department is the first department to have clinical encounter with patient");
                    CWD = "opd_deptMent";
                    CWDi = "dept_opd";
                    break;
                case "Pharmacy Department":
                    lbl_department_name.setText("Pharmacy Dept".toUpperCase());
                    lbl_dept_abt.setText("The Pharmacy department manages medicine dispensation");
                    CWD = "pharmacy_deptMent";
                    CWDi = "dept_pharm";
                    break;
                case "Records Department":
                    lbl_department_name.setText("Records Dept".toUpperCase());
                    lbl_dept_abt.setText("The Records department manages incoming clients / patients");
                    CWD = "records_deptMent";
                    CWDi = "dept_rec";
                    break;
                case "Store Department":
                    lbl_department_name.setText("Stores Dept".toUpperCase());
                    lbl_dept_abt.setText("The Stores department manages medicine entry and checks for deficiencies in drug quantity");
                    CWD = "stores_deptMent";
                    CWDi = "dept_store";
                    break;
            }
            if (checkAuto.isSelected()) {
                task.setRate(task.getRate() * -1);
                task.play();
                deptMgmt_Drawer.close();
                Service<ObservableList<admin_deptMemberList>> allMembers
                        = new Administrator_otherSerivices_class().allMembers(CWD);
                allMembers.setOnSucceeded((event) -> {
                    dept_userListView.setItems(allMembers.getValue());
                    allMembers.cancel();
                });
                allMembers.start();
            } else {

            }
        });

        action.setOnAction(e -> {
            task.setRate(task.getRate() * -1);
            task.play();
            deptMgmt_Drawer.close();
            Service<ObservableList<admin_deptMemberList>> allMembers
                    = new Administrator_otherSerivices_class().allMembers(CWD);
            allMembers.setOnSucceeded((event) -> {
                dept_userListView.setItems(allMembers.getValue());
                allMembers.cancel();
            });
            allMembers.start();
        });

    }

    @FXML
    private void add_user_to_dept(ActionEvent event) throws SQLException {
        
        try {
            sql = "insert into " + CWD + " (dept_id,user_id,user_status) values(?,?,?)";
            conn = conclass.livewell_localhost();
            pst = conn.prepareStatement(sql);
            pst.setString(1, CWDi);
            pst.setString(2, dept_Searched_userId);
            pst.setString(3, "Member");
            pst.execute();
            semanticsClass.set_notification("Successful", dept_Searched_userId + " added to " + CWD + " successfully");

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {// 3x45/wcgY
                semanticsClass.set_notification("Successful", "User already belongs to department");
            } else {
                System.out.println(e);
            }
        }

        Load_dept_members(CWD);
    }

    void Load_dept_members(String currentWorkingDept) {
        Service<ObservableList<admin_deptMemberList>> allMembers
                = new Administrator_otherSerivices_class().allMembers(currentWorkingDept);
        allMembers.setOnSucceeded((evt) -> {
            dept_userListView.setItems(allMembers.getValue());
            allMembers.cancel();
        });
        allMembers.start();
    }

    @FXML
    private void do_delete_from_dept(ActionEvent event) throws SQLException {
        if (!dept_userListView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Continue");
            alert.setContentText("Click OK to continue or Cancel to abort");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                admin_deptMemberList selectedItem = dept_userListView.getSelectionModel().getSelectedItem();
                sql = "delete from " + CWD + " where user_id = '" + selectedItem.getUser_id() + "' ";
                pst = conn.prepareStatement(sql);
                pst.execute();
                semanticsClass.set_notification("Succeeded", selectedItem.getUser_id() + " has been removed from " + CWD + " successfully");
                Load_dept_members(CWD);
            }

        }
    }

    @FXML
    private void dept_userListView_mouse_Clicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            if (!dept_userListView.getSelectionModel().isEmpty()) {
                admin_deptMemberList selectedItem = dept_userListView.getSelectionModel().getSelectedItem();
                fill(new TextField(selectedItem.getUser_id()));
            }
        }
    }
    Service<Boolean> msgSender = null;

    @FXML
    private void send_sms(ActionEvent event) {
        // Find your Account Sid and Auth Token at twilio.com/console     
        VBox box = new VBox();
        TextField txt1 = new TextField();
        txt1.setPromptText("Enter senders number");
        txt1.setText("+12563611302");
        TextField txt2 = new TextField();
        txt2.setPromptText("Enter receipients number");
        txt2.setText("+233");
        TextArea body = new TextArea();
        body.setText("Livewell Herbal Centre\nTest Transmission");
        HBox footer = new HBox();
        Button send = new Button("Send Message");
        send.setPrefWidth(120);
        Button cancel = new Button("Cancel Message");
        cancel.setPrefWidth(120);
        footer.getChildren().addAll(send, cancel);
        footer.setSpacing(10);
        box.getChildren().addAll(txt1, txt2, body, footer);
        box.setSpacing(5);
        send.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Press OK to send Message");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                msgSender = new Service<Boolean>() {
                    @Override
                    protected Task<Boolean> createTask() {
                        return new Task<Boolean>() {
                            @Override
                            protected Boolean call() throws Exception {
                                //testing internet availablility
                                try {
                                    Socket sockt = new Socket();
                                    InetSocketAddress addr = new InetSocketAddress("www.google.com", 80);
                                    sockt.connect(addr);
                                    if (sockt.isConnected()) {
                                        System.out.println("Internet connection available");
                                        final String ACCOUNT_SID = "ACce2cfd3c7d7c662090f909aac3875211";
                                        final String AUTH_TOKEN = "08a9dae2eed7056e4a3153ec56836cd7";

                                        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

                                        Message message = Message
                                                .creator(new PhoneNumber(txt2.getText()), // to
                                                        new PhoneNumber(txt1.getText()), // from
                                                        body.getText())
                                                .create();

                                        System.out.println(message.getSid());
                                        System.out.println(message.getNumSegments());
                                    } else {
                                        semanticsClass.set_notification("Error", "No internet connection, please check and retry");
                                    }

                                } catch (Exception e) {
                                    semanticsClass.set_notification("Error", "No internet connection");
                                }

                                return true;
                            }
                        };
                    }
                };
                msgSender.setOnSucceeded(ee -> {
                    semanticsClass.set_notification("Sent", "an msg was sent");
                });
                msgSender.setOnFailed(ee -> {
                    semanticsClass.set_notification("Error", "Couldnt send message to " + txt2.getText());
                });
                msgSender.start();
            }
        });
        cancel.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Press OK to send Message");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (msgSender.isRunning()) {
                    msgSender.cancel();
                    semanticsClass.set_notification("Cancelled", "SMS aborted successfully");
                }
            }
        });

        Stage smsStage = new Stage();
        Scene smsScene = new Scene(box);
        smsStage.setScene(smsScene);
        smsStage.show();

    }

}
