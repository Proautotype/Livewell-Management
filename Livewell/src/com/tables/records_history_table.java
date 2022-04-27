/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tables;

/**
 *
 * @author Wynxtyn Proautotype
 */
public class records_history_table {
    
    private String ccc;
    private String pn;
    private String crest;
    private String nhis;

    public records_history_table() {
    }
    
    public records_history_table(String ccc, String pn, String crest, String nhis) {
        this.ccc = ccc;
        this.pn = pn;
        this.crest = crest;
        this.nhis = nhis;
    }

    /**
     * @return the ccc
     */
    public String getCcc() {
        return ccc;
    }

    /**
     * @param ccc the ccc to set
     */
    public void setCcc(String ccc) {
        this.ccc = ccc;
    }

    /**
     * @return the pn
     */
    public String getPn() {
        return pn;
    }

    /**
     * @param pn the pn to set
     */
    public void setPn(String pn) {
        this.pn = pn;
    }

    /**
     * @return the crest
     */
    public String getCrest() {
        return crest;
    }

    /**
     * @param crest the crest to set
     */
    public void setCrest(String crest) {
        this.crest = crest;
    }

    /**
     * @return the nhis
     */
    public String getNhis() {
        return nhis;
    }

    /**
     * @param nhis the nhis to set
     */
    public void setNhis(String nhis) {
        this.nhis = nhis;
    }
 
    
}
