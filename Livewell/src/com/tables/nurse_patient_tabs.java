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
public class nurse_patient_tabs {

    private String nurse_id;
    private String opd_id;
    private Double weight;
    private Double height;
    private Double temperature;
    private int systolis;
    private int dystolis;
    private int pulse;
    private String preg;
    private String on_date;

    public nurse_patient_tabs(String nurse_id, String opd_id, Double weight, Double height, Double temperature, int systolis, int dystolis, int pulse, String preg, String on_date) {
        this.nurse_id = nurse_id;
        this.opd_id = opd_id;
        this.weight = weight;
        this.height = height;
        this.temperature = temperature;
        this.systolis = systolis;
        this.dystolis = dystolis;
        this.pulse = pulse;
        this.preg = preg;
        this.on_date = on_date;
    }
    
    public nurse_patient_tabs() {

    }

    public String getNurse_id() {
        return nurse_id;
    }

    public void setNurse_id(String nurse_id) {
        this.nurse_id = nurse_id;
    }

    public String getOpd_id() {
        return opd_id;
    }

    public void setOpd_id(String opd_id) {
        this.opd_id = opd_id;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public int getSystolis() {
        return systolis;
    }

    public void setSystolis(int systolis) {
        this.systolis = systolis;
    }

    public int getDystolis() {
        return dystolis;
    }

    public void setDystolis(int dystolis) {
        this.dystolis = dystolis;
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public String getPreg() {
        return preg;
    }

    public void setPreg(String preg) {
        this.preg = preg;
    }

    public String getOn_date() {
        return on_date;
    }

    public void setOn_date(String on_date) {
        this.on_date = on_date;
    }

}
