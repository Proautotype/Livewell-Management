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
public class lab_haematology_table {

    private String pid, blood_groupin, rhesus, hb, hepatitis,
            sickling, blood_film, HB_Elec, g6pd, vdrl,
            H_pylori, FBS, RBS, WBC, ESR, cholestrol, on_date;
    private Timestamp record_time;

    public lab_haematology_table() {
    }

    public lab_haematology_table(String pid, String blood_groupin, String rhesus, String hb, String hepatitis, String sickling, String blood_film, String HB_Elec, String g6pd, String vdrl, String H_pylori, String FBS, String RBS, 
            String WBC, String ESR, String cholestrol, String on_date,Timestamp record_time) {
        this.pid = pid;
        this.blood_groupin = blood_groupin;
        this.rhesus = rhesus;
        this.hb = hb;
        this.hepatitis = hepatitis;
        this.sickling = sickling;
        this.blood_film = blood_film;
        this.HB_Elec = HB_Elec;
        this.g6pd = g6pd;
        this.vdrl = vdrl;
        this.H_pylori = H_pylori;
        this.FBS = FBS;
        this.RBS = RBS;
        this.WBC = WBC;
        this.ESR = ESR;
        this.cholestrol = cholestrol;
        this.on_date = on_date;
        this.record_time = record_time;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getBlood_groupin() {
        return blood_groupin;
    }

    public void setBlood_groupin(String blood_groupin) {
        this.blood_groupin = blood_groupin;
    }

    public String getRhesus() {
        return rhesus;
    }

    public void setRhesus(String rhesus) {
        this.rhesus = rhesus;
    }

    public String getHb() {
        return hb;
    }

    public void setHb(String hb) {
        this.hb = hb;
    }

    public String getHepatitis() {
        return hepatitis;
    }

    public void setHepatitis(String hepatitis) {
        this.hepatitis = hepatitis;
    }

    public String getSickling() {
        return sickling;
    }

    public void setSickling(String sickling) {
        this.sickling = sickling;
    }

    public String getBlood_film() {
        return blood_film;
    }

    public void setBlood_film(String blood_film) {
        this.blood_film = blood_film;
    }

    public String getHB_Elec() {
        return HB_Elec;
    }

    public void setHB_Elec(String HB_Elec) {
        this.HB_Elec = HB_Elec;
    }

    public String getG6pd() {
        return g6pd;
    }

    public void setG6pd(String g6pd) {
        this.g6pd = g6pd;
    }

    public String getVdrl() {
        return vdrl;
    }

    public void setVdrl(String vdrl) {
        this.vdrl = vdrl;
    }

    public String getH_pylori() {
        return H_pylori;
    }

    public void setH_pylori(String H_pylori) {
        this.H_pylori = H_pylori;
    }

    public String getFBS() {
        return FBS;
    }

    public void setFBS(String FBS) {
        this.FBS = FBS;
    }

    public String getRBS() {
        return RBS;
    }

    public void setRBS(String RBS) {
        this.RBS = RBS;
    }

    public String getWBC() {
        return WBC;
    }

    public void setWBC(String WBC) {
        this.WBC = WBC;
    }

    public String getESR() {
        return ESR;
    }

    public void setESR(String ESR) {
        this.ESR = ESR;
    }

    public String getCholestrol() {
        return cholestrol;
    }

    public void setCholestrol(String cholestrol) {
        this.cholestrol = cholestrol;
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
