/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tables;

import javafx.scene.control.Label;
import java.sql.Timestamp;
/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class account_Patient_Section {

    private String pid, date, particulas, cost, amt_paid;
    private Label lbl_balance;
    private Timestamp record_time;

    public account_Patient_Section() {
    }

    public account_Patient_Section(String pid, String date, String particulas, String cost, String amt_paid, Label lbl_balance, Timestamp record_time) {
        this.pid = pid;
        this.date = date;
        this.particulas = particulas;
        this.cost = cost;
        this.amt_paid = amt_paid;
        this.lbl_balance = lbl_balance;
        this.record_time = record_time;
    }

   

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getParticulas() {
        return particulas;
    }

    public void setParticulas(String particulas) {
        this.particulas = particulas;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getAmt_paid() {
        return amt_paid;
    }

    public void setAmt_paid(String amt_paid) {
        this.amt_paid = amt_paid;
    }

    public Label getLbl_balance() {
        return lbl_balance;
    }

    public void setLbl_balance(Label lbl_balance) {
        this.lbl_balance = lbl_balance;
    }

    public Timestamp getRecord_time() {
        return record_time;
    }

    public void setRecord_time(Timestamp record_time) {
        this.record_time = record_time;
    }
}
