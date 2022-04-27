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
public class lab_LFT {

    private String pid, protein, albumin, AST, ALT, GGT, ALP, T_BILIRUBIN, H_BILIRUBIN, on_date;
    private Timestamp record_time;

    public lab_LFT() {
    }

    public lab_LFT(String pid, String protein, String albumin, String AST, String ALT, String GGT, String ALP, String T_BILIRUBIN, String H_BILIRUBIN, String on_date, Timestamp record_time) {
        this.pid = pid;
        this.protein = protein;
        this.albumin = albumin;
        this.AST = AST;
        this.ALT = ALT;
        this.GGT = GGT;
        this.ALP = ALP;
        this.T_BILIRUBIN = T_BILIRUBIN;
        this.H_BILIRUBIN = H_BILIRUBIN;
        this.on_date = on_date;
        this.record_time = record_time;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getAlbumin() {
        return albumin;
    }

    public void setAlbumin(String albumin) {
        this.albumin = albumin;
    }

    public String getAST() {
        return AST;
    }

    public void setAST(String AST) {
        this.AST = AST;
    }

    public String getALT() {
        return ALT;
    }

    public void setALT(String ALT) {
        this.ALT = ALT;
    }

    public String getGGT() {
        return GGT;
    }

    public void setGGT(String GGT) {
        this.GGT = GGT;
    }

    public String getALP() {
        return ALP;
    }

    public void setALP(String ALP) {
        this.ALP = ALP;
    }

    public String getT_BILIRUBIN() {
        return T_BILIRUBIN;
    }

    public void setT_BILIRUBIN(String T_BILIRUBIN) {
        this.T_BILIRUBIN = T_BILIRUBIN;
    }

    public String getH_BILIRUBIN() {
        return H_BILIRUBIN;
    }

    public void setH_BILIRUBIN(String H_BILIRUBIN) {
        this.H_BILIRUBIN = H_BILIRUBIN;
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
