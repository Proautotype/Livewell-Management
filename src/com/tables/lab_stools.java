/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tables;

import java.sql.*;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class lab_stools {

    private String pid, macroscopy, microscopy, ocult_blood,on_date;
    private Timestamp record_time;

    public lab_stools() {
    }

    public lab_stools(String pid, String macroscopy, String microscopy, String ocult_blood, String on_date, Timestamp record_time) {
        this.pid = pid;
        this.macroscopy = macroscopy;
        this.microscopy = microscopy;
        this.ocult_blood = ocult_blood;
        this.on_date = on_date;
        this.record_time = record_time;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getMacroscopy() {
        return macroscopy;
    }

    public void setMacroscopy(String macroscopy) {
        this.macroscopy = macroscopy;
    }

    public String getMicroscopy() {
        return microscopy;
    }

    public void setMicroscopy(String microscopy) {
        this.microscopy = microscopy;
    }

    public String getOcult_blood() {
        return ocult_blood;
    }

    public void setOcult_blood(String ocult_blood) {
        this.ocult_blood = ocult_blood;
    }

    public String getOn_date() {
        return on_date;
    }

    public void setOn_date(String on_date) {
        this.on_date = on_date;
    }

    public Timestamp getRecord_time() {
        return record_time;
    }

    public void setRecord_time(Timestamp record_time) {
        this.record_time = record_time;
    }

   
}
