/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Administrator;

import com.connection.conclass;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class Administrator_DLL {

    String sql = "";
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private FileInputStream UserImagefis;
    private FileInputStream CertImagefis;
    private File UserImageFile;
    private File CertImageFile;

    public Administrator_DLL() {
        conn = conclass.localhost();
    }

    public PreparedStatement insertUser(String fname, String sname, Date dob, int age, String sex, int contact, String hometown, String residence, String email, String houseNo, String loe, File UserImageFile, File CertImageFile) {
        this.UserImageFile = UserImageFile;
        this.CertImageFile = CertImageFile;
        sql = " INSERT INTO livewell.users (fname,sname,  dob,  age,  sex, contact, hometown, residence,email, loe,houseNum, userImage, certImage)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        try {
            pst = conn.prepareStatement(sql);
            UserImagefis = new FileInputStream(this.UserImageFile);//prepare user image file
            CertImagefis = new FileInputStream(this.CertImageFile);//prepare cert image file

            pst.setString(1, fname.trim());
            pst.setString(2, sname);
            pst.setDate(3, dob);
            pst.setInt(4, age);
            pst.setString(5, sex);
            pst.setInt(6, Integer.valueOf(contact));
            pst.setString(7, hometown);
            pst.setString(8, residence);
            pst.setString(9, email);
            pst.setString(10, houseNo);
            pst.setString(11, loe);
            pst.setBinaryStream(12, UserImagefis, this.UserImageFile.length());
            pst.setBinaryStream(13, CertImagefis, this.CertImageFile.length());

        } catch (Exception e) {
            System.out.println(e);
        }
        return pst;
    }

    public Service<PreparedStatement> addUserTask(String fname, String sname, Date dob, int age, String sex, int contact, String hometown, String residence, String email, String houseNo, String loe, File UserImageFile, File CertImageFile) {
        return new Service<PreparedStatement>() {
            @Override
            protected Task<PreparedStatement> createTask() {
                return new Task<PreparedStatement>() {
                    @Override
                    protected PreparedStatement call() throws Exception {
                        sql = " INSERT INTO livewell.users (fname,sname,  dob,  age,  sex, contact, hometown, residence,email, loe,houseNum, userImage, certImage)"
                                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) ";
                        try {
                            pst = conn.prepareStatement(sql);
                            UserImagefis = new FileInputStream(UserImageFile);//prepare user image file
                            CertImagefis = new FileInputStream(CertImageFile);//prepare cert image file

                            pst.setString(1, fname.trim());
                            pst.setString(2, sname);
                            pst.setDate(3, dob);
                            pst.setInt(4, age);
                            pst.setString(5, sex);
                            pst.setInt(6, contact);
                            pst.setString(7, hometown);
                            pst.setString(8, residence);
                            pst.setString(9, email);
                            pst.setString(10, houseNo);
                            pst.setString(11, loe);
                            pst.setBinaryStream(12, UserImagefis, UserImageFile.length());
                            pst.setBinaryStream(13, CertImagefis, CertImageFile.length());
                            pst.execute();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        return pst;
                    }
                };
            }
        };
    }
}
