/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Accountancy;

import com.connection.conclass;
import com.semantics.semanticsClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author G
 */
public class acc_client_payment {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String sql = "";

    private String cid;
    private double deficit;
    private String mop;
    private Double payment;
    private Double balance;
    private Date pay_date;
    private Timestamp rec_date;

    public acc_client_payment() {
    }

    public acc_client_payment(String cid, double deficit, String mop, Double payment, Double balance, Date pay_date, Timestamp rec_date) {
        this.cid = cid;
        this.deficit = deficit;
        this.mop = mop;
        this.payment = payment;
        this.pay_date = pay_date;
        this.rec_date = rec_date;
        this.balance = balance;
    }

    public void do_payment(acc_client_payment acp) {
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        System.out.println(acp.getBalance());
                        sql = "INSERT INTO livewell.account_client_payment"
                                + "(cid,deficit,mop,payment,balance,pay_date,rec_date) "
                                + "VALUES (?,?,?,?,?,?,?)";
                        try {
                            conn = conclass.livewell_localhost();
                            pst = conn.prepareStatement(sql);
                            pst.setString(1, acp.cid);
                            pst.setDouble(2, acp.deficit);
                            pst.setString(3, acp.mop);
                            pst.setDouble(4, acp.payment);
                            pst.setDouble(5, acp.getBalance());
                            pst.setDate(6, acp.getPay_date());
                            pst.setTimestamp(7, semanticsClass.nowStamp(new java.util.Date()));
                            return pst.execute();
                        } catch (SQLException e) {
                            System.out.println(e);
                            cancel();
                            return false;
                        }
                    }
                };
            }
        };

        service.setOnSucceeded(e -> {
            semanticsClass.set_notification("success", "Good");
        });

        service.setOnCancelled(e -> {
            semanticsClass.set_notification("success", "An error occured");
        });
        service.start();
    }
    ObservableList<acc_client_payment> batch_acp = FXCollections.observableArrayList();

    public Service<ObservableList<acc_client_payment>> load_payments(String query) {
        Service<ObservableList<acc_client_payment>> service = new Service<ObservableList<acc_client_payment>>() {
            @Override
            protected Task<ObservableList<acc_client_payment>> createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        try {
                            conn = conclass.livewell_localhost();
                            acc_client_payment acp = new acc_client_payment();
                            sql = "select * from account_client_payment where cid = '"+query+"'";//rs.getString("mop")
                            pst = conn.prepareStatement(sql);
                            rs = pst.executeQuery();
                            while (rs.next()) {
                                acp.setCid(rs.getString("cid"));
                                System.out.println(rs.getString("mop"));
                                acp.setMop(rs.getString("mop"));
                                acp.setDeficit(rs.getDouble("deficit"));
                                acp.setPayment(rs.getDouble("payment"));
                                acp.setBalance(rs.getDouble("balance"));
                                acp.setPay_date(rs.getDate("pay_date"));
                                acp.setRec_date(rs.getTimestamp("rec_date"));
                                batch_acp.add(acp);
                            }
                            updateValue(batch_acp);
                            System.out.println(batch_acp.get(0).deficit);
                            return true;
                        } catch (SQLException e) {
                            System.out.println(e);
                            cancel();
                            return false;
                        }
                    }
                };
            }
        };

        service.setOnSucceeded(e -> {
            // semanticsClass.set_notification("success", "Good");
        });

        service.setOnCancelled(e -> {
            semanticsClass.set_notification("success", "An error occured");
        });
        service.start();
        return service;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public double getDeficit() {
        return deficit;
    }

    public void setDeficit(double deficit) {
        this.deficit = deficit;
    }

    public String getMop() {
        return mop;
    }

    public void setMop(String mop) {
        this.mop = mop;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Date getPay_date() {
        return pay_date;
    }

    public void setPay_date(Date pay_date) {
        this.pay_date = pay_date;
    }

    public Timestamp getRec_date() {
        return rec_date;
    }

    public void setRec_date(Timestamp rec_date) {
        this.rec_date = rec_date;
    }

}
