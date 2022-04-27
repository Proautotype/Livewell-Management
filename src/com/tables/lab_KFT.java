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
public class lab_KFT {

    private String Pid, sodium, potassium, chloride, total_co2, urea, creatinine, egfr, On_date;
    private Timestamp record_time;
    public lab_KFT() {
    }

    public lab_KFT(String Pid, String sodium, String potassium, String chloride, String total_co2, String urea, String creatinine, String egfr, String On_date,Timestamp record_time) {
        this.Pid = Pid;
        this.sodium = sodium;
        this.potassium = potassium;
        this.chloride = chloride;
        this.total_co2 = total_co2;
        this.urea = urea;
        this.creatinine = creatinine;
        this.egfr = egfr;
        this.On_date = On_date;
        this.record_time = record_time;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String Pid) {
        this.Pid = Pid;
    }

    public String getSodium() {
        return sodium;
    }

    public void setSodium(String sodium) {
        this.sodium = sodium;
    }

    public String getPotassium() {
        return potassium;
    }

    public void setPotassium(String potassium) {
        this.potassium = potassium;
    }

    public String getChloride() {
        return chloride;
    }

    public void setChloride(String chloride) {
        this.chloride = chloride;
    }

    public String getTotal_co2() {
        return total_co2;
    }

    public void setTotal_co2(String total_co2) {
        this.total_co2 = total_co2;
    }

    public String getUrea() {
        return urea;
    }

    public void setUrea(String urea) {
        this.urea = urea;
    }

    public String getCreatinine() {
        return creatinine;
    }

    public void setCreatinine(String creatinine) {
        this.creatinine = creatinine;
    }

    public String getEgfr() {
        return egfr;
    }

    public void setEgfr(String egfr) {
        this.egfr = egfr;
    }

    public String getOn_date() {
        return On_date;
    }

    public void setOn_date(String On_date) {
        this.On_date = On_date;
    }

    public Timestamp getRecord_time() {
        return record_time;
    }

    public void setRecord_time(Timestamp record_time) {
        this.record_time = record_time;
    }

   
  
}
