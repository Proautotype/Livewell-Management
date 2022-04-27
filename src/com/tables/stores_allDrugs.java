/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tables;

import javafx.scene.control.Label;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class stores_allDrugs {

    private String code;
    private String drug_name;
    private String drug_type;
    private String price;
//    private String quantity;
    private Label quantity;

    public stores_allDrugs() {
    }

    public stores_allDrugs(String code, String drug_name, String drug_type, String price, Label quantity) {
        this.code = code;
        this.drug_name = drug_name;
        this.drug_type = drug_type;
        this.price = price;
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDrug_name() {
        return drug_name;
    }

    public void setDrug_name(String drug_name) {
        this.drug_name = drug_name;
    }

    public String getDrug_type() {
        return drug_type;
    }

    public void setDrug_type(String drug_type) {
        this.drug_type = drug_type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Label getQuantity() {
        return quantity;
    }

    public void setQuantity(Label quantity) {
        this.quantity = quantity;
    }

}
