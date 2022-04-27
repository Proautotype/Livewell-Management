/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.connection.conclass;
import com.oracle.tools.packager.Log;
import com.semantics.semanticsClass;
import com.sqlObjects.Administrator_getUserDetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class Administrator_load_Use_Task extends Service< ObservableList< Administrator_getUserDetails>> {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = "";

    public Administrator_load_Use_Task(String type) {
        if (type.isEmpty() || type.length() <= 0) {
            sql = "select * FROM users";
        } else {
            sql = "select " + type + "  FROM users";
        }

    }

    ObservableList<Administrator_getUserDetails> ee = null;
    Administrator_getUserDetails e = null;

    @Override
    protected Task<ObservableList< Administrator_getUserDetails>> createTask() {

        return new Task<ObservableList< Administrator_getUserDetails>>() {
            @Override
            protected ObservableList<Administrator_getUserDetails> call() throws Exception {

                ee = FXCollections.observableArrayList();
                try {
                    conn = conclass.livewell_localhost();
                    pst = conn.prepareStatement(sql);
                    rs = pst.executeQuery();

                    while (rs.next()) {

                        e = new Administrator_getUserDetails();
                        e.setCustomId(rs.getString("customId"));
                        e.setFname(rs.getString("fname"));
                        e.setSname(rs.getString("sname"));
                        e.setSex(rs.getString("sex"));
                        e.setContact("0" + rs.getString("contact"));
                        e.setEmail(rs.getString("email"));
                        e.setLoe(rs.getString("loe"));
                        e.setDob(rs.getDate("dob"));
                        ee.add(e);
                        updateValue(ee);
                        updateMessage("hellow");
                    }

                } catch (SQLException e) {
                    System.out.println(e);
                }
                return ee;
            }
        };
    }

}
