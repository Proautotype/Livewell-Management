/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Accountancy;

import com.connection.conclass;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;

/**
 *
 * @author G
 */
public class products_mgmt {

    private String pid;
    private String pname;
    private double price;

    String sql = "";
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public products_mgmt() {
        conn = conclass.livewell_localhost();
    }

    public products_mgmt(String pid, String pname, double price) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        conn = conclass.livewell_localhost();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Service<Boolean> addPp(products_mgmt pmgmt) {
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        conn = conclass.livewell_localhost();

                        try {
                            sql = "insert into account_pmp (pid,pname,uprice) values(?,?,?)";
                            pst = conn.prepareStatement(sql);
                            pst.setString(1, pmgmt.pid);
                            pst.setString(2, pmgmt.pname);
                            pst.setDouble(3, pmgmt.price);
                            pst.execute();
                            updateMessage("Save complete");
                            Thread.sleep(500);
                            return true;
                        } catch (SQLException e) {
                            if (e.getErrorCode() == 1062) {
                                updateMessage("1062");
                                System.out.println("Already availables");
                                cancel();
                                Thread.sleep(500);
                            } else {
                                System.out.println(e.getMessage() + " ::: " + e.getErrorCode());
                                updateMessage(e.getMessage() + " ::: " + e.getErrorCode());
                                Thread.sleep(500);
                            }

                            return false;
                        }
                    }
                };
            }
        };
        return service;
    }

    public Service<Boolean> updatePp(products_mgmt pmgmt) {
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {

                        try {
                            sql = "update account_pmp set pid = '" + pmgmt.pid + "',pname = '"
                                    + pmgmt.pname + "' ,uprice = '"
                                    + pmgmt.price + "'  where pid = '"
                                    + pmgmt.pid + "' ";
                            pst = conn.prepareStatement(sql);
                            pst.execute();
                            updateMessage("Update complete");
                            Thread.sleep(500);
                            return true;
                        } catch (SQLException e) {
                            updateMessage(e.getMessage() + ":::" + e.getErrorCode());
                            System.out.println(e);
                            return false;
                        }
                    }
                };
            }
        };
        return service;
    }

    ObservableList<products_mgmt> list_pmp = FXCollections.observableArrayList();
    products_mgmt pimp_pmp;

    public Service<ObservableList<products_mgmt>> load(String query) {
        return new Service<ObservableList<products_mgmt>>() {
            @Override
            protected Task<ObservableList<products_mgmt>> createTask() {
                return new Task<ObservableList<products_mgmt>>() {
                    @Override
                    protected ObservableList<products_mgmt> call() throws Exception {
                        try {
                            switch (query) {
                                case "*":
                                    sql = "select * from account_pmp";
                                    break;
                                default:
                                    sql = "select * from account_pmp where pname like '%" + query + "%' ";
                                    break;
                            }
                            conn = conclass.livewell_localhost();
                            pst = conn.prepareStatement(sql);
                            rs = pst.executeQuery();
                            while (rs.next()) {
                                pimp_pmp = new products_mgmt();
                                pimp_pmp.setPid(rs.getString("pid"));
                                pimp_pmp.setPname(rs.getString("pname"));
                                pimp_pmp.setPrice(rs.getDouble("uprice"));
//                                System.out.println(rs.getString("pid") +" "+rs.getString("pname"));
                                list_pmp.add(pimp_pmp);
                                updateValue(list_pmp);
                            }
                        } catch (SQLException e) {
                            System.out.println(e);
                            return null;
                        }
                        return list_pmp;
                    }
                };
            }
        };
    }
}
