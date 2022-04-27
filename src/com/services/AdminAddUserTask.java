/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.connection.conclass;
import java.io.File;
import java.io.FileInputStream;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import java.sql.*;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class AdminAddUserTask extends Service<PreparedStatement> {

    String sql = "";
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private FileInputStream UserImagefis;
    private FileInputStream CertImagefis;
    private final File UserImageFile;
    private final File CertImageFile;

    String customId, fname, sname, sex, email, loe, hometown, residence, houseNo;
    Date dob;
    int age, contact;

    public AdminAddUserTask(String customId, String fname, String sname, Date dob, int age, String sex, int contact, String hometown, String residence, String email, String houseNo, String loe, File UserImageFile, File CertImageFile) {
        this.customId = customId;
        this.UserImageFile = UserImageFile;
        this.CertImageFile = CertImageFile;
        this.fname = fname;
        this.sname = sname;
        this.sex = sex;
        this.email = email;
        this.loe = loe;
        this.hometown = hometown;
        this.residence = residence;
        this.houseNo = houseNo;
        this.dob = dob;
        this.age = age;
        this.contact = contact;
    }

    @Override
    protected Task<PreparedStatement> createTask() {
        return new Task<PreparedStatement>() {
            @Override
            protected PreparedStatement call() throws Exception {
                updateMessage("Please wait... we will complete shortly");
                conn = conclass.livewell_localhost();// get connection to database
                int i = 0;
                sql = " INSERT INTO users (customId,fname,sname,  dob,  age,  sex, contact, hometown, "
                        + "residence,email, loe,houseNum, userImage, certImage)"
                        + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
                try {
                    pst = conn.prepareStatement(sql);
                    UserImagefis = new FileInputStream(UserImageFile);//prepare user image file
                    CertImagefis = new FileInputStream(CertImageFile);//prepare cert image file
                    pst.setString(1, customId);
                    pst.setString(2, fname.trim());
                    updateMessage(fname + " added");
                    Thread.sleep(100);
                    pst.setString(3, sname);
                    updateMessage(sname + " added");
                    Thread.sleep(100);
                    pst.setDate(4, dob);
                    updateMessage(dob + " added");
                    Thread.sleep(100);
                    pst.setInt(5, age);
                    updateMessage("Age is" + " added");
                    Thread.sleep(100);
                    pst.setString(6, sex);
                    updateMessage("Gender is" + " added");
                    Thread.sleep(100);
                    pst.setInt(7, contact);
                    updateMessage("Contact is" + " added");
                    Thread.sleep(100);
                    pst.setString(8, hometown);
                    updateMessage("Hometown is" + " added");
                    Thread.sleep(100);
                    pst.setString(9, residence);
                    updateMessage("Residence is" + " added");
                    Thread.sleep(100);
                    pst.setString(10, email);
                    updateMessage("Email " + " added");
                    Thread.sleep(100);
                    pst.setString(11, houseNo);
                    updateMessage("House number" + " added");
                    Thread.sleep(100);
                    pst.setString(12, loe);
                    updateMessage("Level of Education" + " added");
                    Thread.sleep(100);
                    updateMessage("Adding User images");
                    pst.setBinaryStream(13, UserImagefis, UserImageFile.length());
                    Thread.sleep(100);
                    updateMessage("user image" + " added");
                    updateMessage("Adding User images");
                    Thread.sleep(100);
                    pst.setBinaryStream(14, CertImagefis, CertImageFile.length());
                    updateMessage("Certificate Image" + " added");
                    Thread.sleep(100);
                    updateMessage("Compiling user details");
                    pst.execute();
                    updateMessage("Successfully Added");
                    Thread.sleep(100);
                } catch (SQLException e) {
                    System.out.println(e);
                }
                return pst;
            }
        };
    }

}
