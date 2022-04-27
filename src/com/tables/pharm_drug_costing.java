/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tables;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class pharm_drug_costing {

    private String pay_id;//this is the payment id
    private String pat_id;// this is the patient id
    private String pharmacist_id; // this is the pharmacist id
    
    private String drug_code;
//    private String quantity;
    private String drug_price;
    private String costing_total;
    
    private String on_date;

    public pharm_drug_costing() {
    }

    public pharm_drug_costing(String pay_id, String pat_id, String pharmacist_id, String drug_code, String drug_price, String costing_total, String on_date) {
        this.pay_id = pay_id;
        this.pat_id = pat_id;
        this.pharmacist_id = pharmacist_id;
        this.drug_code = drug_code;        
        this.drug_price = drug_price;
        this.costing_total = costing_total;
        this.on_date = on_date;
    }


    public String getPay_id() {
        return pay_id;
    }

    public void setPay_id(String pay_id) {
        this.pay_id = pay_id;
    }

    public String getPat_id() {
        return pat_id;
    }

    public void setPat_id(String pat_id) {
        this.pat_id = pat_id;
    }

    public String getPharmacist_id() {
        return pharmacist_id;
    }

    public void setPharmacist_id(String pharmacist_id) {
        this.pharmacist_id = pharmacist_id;
    }

    public String getDrug_code() {
        return drug_code;
    }

    public void setDrug_code(String drug_code) {
        this.drug_code = drug_code;
    }

    public String getDrug_price() {
        return drug_price;
    }

    public void setDrug_price(String drug_price) {
        this.drug_price = drug_price;
    }

    public String getCosting_total() {
        return costing_total;
    }

    public void setCosting_total(String costing_total) {
        this.costing_total = costing_total;
    }

    public String getOn_date() {
        return on_date;
    }

    public void setOn_date(String on_date) {
        this.on_date = on_date;
    }

   

}
