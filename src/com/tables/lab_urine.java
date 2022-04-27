/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tables;

import java.util.logging.Logger;
import java.sql.Timestamp;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class lab_urine {

    private String pid,appearance, colour, leukocytes, urobilinogen, bilirubin, blood, nitrite, ph, spec_gravity, protein, glucose, ketones,on_date;
    private Timestamp record_time;

    public lab_urine(){}

   public lab_urine(String pid, String appearance, String colour, String leukocytes, String urobilinogen, String bilirubin, String blood, String nitrite, String ph, String spec_gravity, String protein, String glucose, String ketones, String on_date, Timestamp record_time) {
        this.pid = pid;
        this.appearance = appearance;
        this.colour = colour;
        this.leukocytes = leukocytes;
        this.urobilinogen = urobilinogen;
        this.bilirubin = bilirubin;
        this.blood = blood;
        this.nitrite = nitrite;
        this.ph = ph;
        this.spec_gravity = spec_gravity;
        this.protein = protein;
        this.glucose = glucose;
        this.ketones = ketones;
        this.on_date = on_date;
        this.record_time = record_time;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getAppearance() {
        return appearance;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getLeukocytes() {
        return leukocytes;
    }

    public void setLeukocytes(String leukocytes) {
        this.leukocytes = leukocytes;
    }

    public String getUrobilinogen() {
        return urobilinogen;
    }

    public void setUrobilinogen(String urobilinogen) {
        this.urobilinogen = urobilinogen;
    }

    public String getBilirubin() {
        return bilirubin;
    }

    public void setBilirubin(String bilirubin) {
        this.bilirubin = bilirubin;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getNitrite() {
        return nitrite;
    }

    public void setNitrite(String nitrite) {
        this.nitrite = nitrite;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getSpec_gravity() {
        return spec_gravity;
    }

    public void setSpec_gravity(String spec_gravity) {
        this.spec_gravity = spec_gravity;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getGlucose() {
        return glucose;
    }

    public void setGlucose(String glucose) {
        this.glucose = glucose;
    }

    public String getKetones() {
        return ketones;
    }

    public void setKetones(String ketones) {
        this.ketones = ketones;
    }
    private static final Logger LOG = Logger.getLogger(lab_urine.class.getName());

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
