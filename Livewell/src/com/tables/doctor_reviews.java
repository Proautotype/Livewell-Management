/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tables;

import com.connection.conclass;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class doctor_reviews extends VBox {

    private String pid;
    private String did, on_date;
    private Time review_time;
    private Date review_date;
    private String note;

    public doctor_reviews() {
       
    }

    public doctor_reviews(String pid, String did, String on_date, Time review_time, Date review_date, String note) {
        this.pid = pid;
        this.did = did;
        this.on_date = on_date;
        this.review_time = review_time;
        this.review_date = review_date;
        this.note = note;
        
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getOn_date() {
        return on_date;
    }

    public void setOn_date(String on_date) {
        this.on_date = on_date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Time getReview_time() {
        return review_time;
    }

    public void setReview_time(Time review_time) {
        this.review_time = review_time;
    }

    public Date getReview_date() {
        return review_date;
    }

    public void setReview_date(Date review_date) {
        this.review_date = review_date;
    }

    public class rd_services {

        String did, pid;
        String sql = "";
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        doctor_reviews d_r = new doctor_reviews();

        public Service<Boolean> add_review(doctor_reviews dr) {
            sql = "insert into doctor_reviews (pid,did,review_date,review_time,on_date,note) values(?,?,?,?,?,?)";
            Service<Boolean> service = new Service<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            try {
                                conn = conclass.livewell_localhost();
                                pst = conn.prepareStatement(sql);
                                pst.setString(1, dr.getPid());
                                pst.setString(2, dr.getDid());
                                pst.setDate(3, dr.getReview_date());
                                pst.setTime(4, dr.getReview_time());
                                pst.setString(5, dr.getOn_date());
                                pst.setString(6, dr.getNote());
                                pst.execute();
                                updateMessage("Review Scheduled");
                            } catch (SQLException e) {
                                if (e.getErrorCode() == 1062) {
                                    updateMessage("Review already scheduled");
                                } else {
                                    updateMessage("An occured with code: " + e.getErrorCode());
                                    System.out.println(e);
                                }

                                cancel();
                            }
                            return true;
                        }
                    };
                }
            };
            return service;

        }
    }
}
