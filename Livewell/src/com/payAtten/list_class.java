/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payAtten;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author G
 */
public class list_class {

    private String date, name, day, remark;

    public list_class() {
    }

    public list_class(String date, String name, String day, String remark) {
        this.date = date;
        this.name = name;
        this.day = day;
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setOn_date(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    class operations {

        ObservableList<list_class> list_att = FXCollections.observableArrayList();
        list_class cl = new list_class();

        String sql = "";
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = null;

        Service<ObservableList<list_class>> singleLoad(String filter) {
            Service<ObservableList<list_class>> service = new Service<ObservableList<list_class>>() {
                @Override
                protected Task<ObservableList<list_class>> createTask() {
                    return new Task<ObservableList<list_class>>() {
                        @Override
                        protected ObservableList<list_class> call() throws Exception {
                            try {
                                sql = "select * from ";
                                pst = conn.prepareStatement(sql);
                                pst.setString(0, sql);
                            } catch (SQLException e) {
                                System.out.println(e);
                            }
                            return list_att;
                        }
                    };
                }
            };
            return service;
        }

    }
}
