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
public class lab_microscopy {

    private String pid, plus_cells, epith_cells, rbs, yeast_cells, casts, crystals, T_Vaginalis, S_Haematobium, bacteria, others, on_date;
    private Timestamp record_time;
    public lab_microscopy() {
    }

    public lab_microscopy(String pid, String plus_cells, String epith_cells, String rbs, String yeast_cells, String casts, String crystals, String T_Vaginalis, String S_Haematobium, String bacteria, String others, String on_date,Timestamp record_time) {
        this.pid = pid;
        this.plus_cells = plus_cells;
        this.epith_cells = epith_cells;
        this.rbs = rbs;
        this.yeast_cells = yeast_cells;
        this.casts = casts;
        this.crystals = crystals;
        this.T_Vaginalis = T_Vaginalis;
        this.S_Haematobium = S_Haematobium;
        this.bacteria = bacteria;
        this.others = others;
        this.on_date = on_date;
        this.record_time = record_time;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPlus_cells() {
        return plus_cells;
    }

    public void setPlus_cells(String plus_cells) {
        this.plus_cells = plus_cells;
    }

    public String getEpith_cells() {
        return epith_cells;
    }

    public void setEpith_cells(String epith_cells) {
        this.epith_cells = epith_cells;
    }

    public String getRbs() {
        return rbs;
    }

    public void setRbs(String rbs) {
        this.rbs = rbs;
    }

    public String getYeast_cells() {
        return yeast_cells;
    }

    public void setYeast_cells(String yeast_cells) {
        this.yeast_cells = yeast_cells;
    }

    public String getCasts() {
        return casts;
    }

    public void setCasts(String casts) {
        this.casts = casts;
    }

    public String getCrystals() {
        return crystals;
    }

    public void setCrystals(String crystals) {
        this.crystals = crystals;
    }

    public String getT_Vaginalis() {
        return T_Vaginalis;
    }

    public void setT_Vaginalis(String T_Vaginalis) {
        this.T_Vaginalis = T_Vaginalis;
    }

    public String getS_Haematobium() {
        return S_Haematobium;
    }

    public void setS_Haematobium(String S_Haematobium) {
        this.S_Haematobium = S_Haematobium;
    }

    public String getBacteria() {
        return bacteria;
    }

    public void setBacteria(String bacteria) {
        this.bacteria = bacteria;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
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
