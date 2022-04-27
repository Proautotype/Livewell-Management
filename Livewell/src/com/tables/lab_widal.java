/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tables;

import java.sql.Timestamp;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class lab_widal {

    private String pid, salmonellaTO, salmonellaTH, RHSt, on_date;
    private Timestamp record_time;

    public lab_widal() {
    }

    public lab_widal(String pid, String salmonellaTO, String salmonellaTH, String RHSt, String on_date, Timestamp record_time) {
        this.pid = pid;
        this.salmonellaTO = salmonellaTO;
        this.salmonellaTH = salmonellaTH;
        this.RHSt = RHSt;
        this.on_date = on_date;
        this.record_time = record_time;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSalmonellaTO() {
        return salmonellaTO;
    }

    public void setSalmonellaTO(String salmonellaTO) {
        this.salmonellaTO = salmonellaTO;
    }

    public String getSalmonellaTH() {
        return salmonellaTH;
    }

    public void setSalmonellaTH(String salmonellaTH) {
        this.salmonellaTH = salmonellaTH;
    }

    public String getRHSt() {
        return RHSt;
    }

    public void setRHSt(String RHSt) {
        this.RHSt = RHSt;
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
