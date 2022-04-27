/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.connection.conclass;
import com.semantics.semanticsClass;
import com.sqlObjects.Administrator_getUserDetails;
import com.uiObjects.admin_deptMemberList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.image.Image;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class Administrator_otherSerivices_class {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = "";

    public Administrator_otherSerivices_class() {
    }

    /*
        UPDATE livewell.users
        SET
        customId = '"++"',    fname = '"++"',    sname = '"++"',      dob = '"++"',
        age = '"++"',      sex = '"++"',     contact = '"++"',     hometown = '"++"',
        residence = '"++"',     email = '"++"',    loe = '"++"',      houseNum = '"++"',
        userImage = '"++"',      certImage = '"++"'      WHERE customId = ?;
     */
    public Service<PreparedStatement> updateUser(
            String customId, String fname, String sname,
            java.sql.Date dob, int age, String sex, int contact,
            String hometown, String residence, String email, String loe, String houseNum,
            File UserImageFile, File CertImageFile
    ) {
        conn = conclass.livewell_localhost();
        Service<PreparedStatement> updateService = new Service<PreparedStatement>() {
            @Override
            protected Task<PreparedStatement> createTask() {
                return new Task<PreparedStatement>() {
                    @Override
                    protected PreparedStatement call() throws Exception {
                        this.updateMessage("User reconfigurating");
                        FileInputStream UserImagefis = new FileInputStream(UserImageFile); //prepare user image file
                        FileInputStream CertImagefis = new FileInputStream(CertImageFile); //prepare cert image file
                        Thread.sleep(200);
                        sql = "UPDATE users"
                                + "        SET"
                                + "        customId = '" + customId + "',    fname = '" + fname + "',    sname = '" + sname + "', dob = '" + dob + "',"
                                + "        age = '" + age + "',      sex = '" + sex + "',     contact = '" + contact + "',     hometown = '" + hometown + "',"
                                + "        residence = '" + residence + "',  email = '" + email + "',    loe = '" + loe + "', houseNum = '" + houseNum + "' "
                                + "        WHERE customId = ?";
                        Thread.sleep(100);
                        pst = conn.prepareStatement(sql);
                        pst.setString(1, customId);
                        return pst;
                    }
                };
            }
        };
        return updateService;
    }

    public Service<ResultSet> userInDetails_s(String value) {
        Service<ResultSet> userInDetailsi = new Service<ResultSet>() {
            @Override
            protected Task<ResultSet> createTask() {
                return new Task<ResultSet>() {
                    @Override
                    protected ResultSet call() throws Exception {
                        conn = conclass.livewell_localhost();
                        if (!conn.isClosed()) {
                            pst = conn.prepareStatement("select * from users where customId = ? or email = ? or contact = ?");
                            try {
                                pst.setString(1, value);
                                pst.setString(2, value);
                                pst.setString(3, value);
                                rs = pst.executeQuery();
                            } catch (SQLException e) {
                                System.out.println(e);
                            }

                        }
                        return rs;
                    }
                };
            }
        };
        return userInDetailsi;
    }

    public Service<ObservableList<admin_deptMemberList>> allMembers(String CWD) {
        Service<ObservableList<admin_deptMemberList>> userInDetailsi = new Service<ObservableList<admin_deptMemberList>>() {
            ObservableList<admin_deptMemberList> memberItem = null;

            @Override
            protected Task<ObservableList<admin_deptMemberList>> createTask() {
                return new Task<ObservableList<admin_deptMemberList>>() {
                    @Override
                    protected ObservableList<admin_deptMemberList> call() throws Exception {
                        sql = "select " + CWD + ".*, users.customId,users.fname,users.sname,users.userImage from " + CWD + ",users where " + CWD + ".user_id = users.customId";
                        conn = conclass.livewell_localhost();
                        pst = conn.prepareStatement(sql);
                        updateMessage("hello");
                        try {
                            rs = pst.executeQuery();
                            memberItem = FXCollections.observableArrayList();
                            byte[] content = new byte[1024];
                            int size = 0;
                            OutputStream ous = null;
                            while (rs.next()) {
                                ous = new FileOutputStream("out.jpg");
                                InputStream is = rs.getBinaryStream("userImage");
                                while ((size = is.read(content)) != -1) {
                                    ous.write(content, 0, size);
                                }
                                ous.flush();
                                Image imager = new Image("file:out.jpg");
                                memberItem.add(new admin_deptMemberList(
                                        rs.getString(CWD + ".user_id"),
                                        rs.getString("users.fname") + " " + rs.getString("users.sname"),
                                        rs.getString(CWD + ".user_status"),
                                        imager));
                                updateValue(memberItem);
                            }
                        } catch (SQLException e) {
                            semanticsClass.SySoutException(e);
                        }

                        return memberItem;
                    }
                };
            }
        };
        return userInDetailsi;
    }

    public Service<PreparedStatement> updateUserImage(File imageFile, String userId, String col_type) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userImage", "User Image");
        map.put("certImage", "Certificate Image");
        conn = conclass.livewell_localhost();
        Service<PreparedStatement> updateService = new Service<PreparedStatement>() {
            @Override
            protected Task<PreparedStatement> createTask() {
                return new Task<PreparedStatement>() {
                    @Override
                    protected PreparedStatement call() throws Exception {
                        this.updateMessage("User reconfigurating: Image configuration");
                        FileInputStream Imagefis = new FileInputStream(imageFile); //prepare user image file

                        Thread.sleep(200);
                        sql = "UPDATE users set " + col_type + " = ? where customId = '" + userId + "' ";
                        Thread.sleep(100);
                        pst = conn.prepareStatement(sql);
                        pst.setBinaryStream(1, Imagefis);
                        updateMessage(map.get(col_type) + " changed successfully");
                        return pst;
                    }
                };
            }
        };
        return updateService;
    }

}
