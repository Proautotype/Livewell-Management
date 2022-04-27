/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.connection.conclass;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class SignIn_doSignIn extends Service<Boolean> {

    Connection conn = conclass.livewell_localhost();
    private String username, password, sql;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private String CWD;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SignIn_doSignIn() {
    }

    public SignIn_doSignIn(String username, String password, String CWD) {
        this.username = username;
        this.password = password;
        this.CWD = CWD;//get the current working deparment
    }
    boolean worker = false;

    private boolean check() throws SQLException {
        conn = conclass.livewell_localhost();

        sql = "select * from users where customId = ? or email = ? or contact = ?";
        pst = conn.prepareStatement(sql);
        pst.setString(1, this.username);
        pst.setString(2, this.username);
        pst.setString(3, this.username);
        rs = pst.executeQuery();
        if (rs.next()) {
            this.username = rs.getString("customId");
            worker = true;
        } else {
            worker = false;
        }
        return worker;
    }

    public boolean isSignable() throws InterruptedException {
        try {
            sql = "select users.*," + CWD + ".* from users," + CWD + " where " + CWD + ".user_id = ? and " + CWD + ".password = ? ";

            conn = conclass.livewell_localhost();
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException ex) {           
            cancel();
            Thread.sleep(100);
            Logger.getLogger(SignIn_doSignIn.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    protected Task<Boolean> createTask() {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                updateMessage("Please wait... ");
                updateProgress(1, 1);
                Thread.sleep(100);               
                return isSignable();
            }
        };

    }

}
