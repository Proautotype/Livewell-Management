/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tables;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class pharmacy_table {

    private String drug_code;
    private String drug;
    private String drug_type;
    private String dosage;
    private String price;
    private TextField input;
    private CheckBox checker;

    public pharmacy_table(String drug_code, String drug, String drug_type, String dosage, String price, TextField input, CheckBox checker) {
        this.drug_code = drug_code;
        this.drug = drug;
        this.drug_type = drug_type;
        this.dosage = dosage;
        this.price = price;
        this.input = input;
        this.checker = checker;
    }

    public String getDrug_code() {
        return drug_code;
    }

    public void setDrug_code(String drug_code) {
        this.drug_code = drug_code;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public String getDrug_type() {
        return drug_type;
    }

    public void setDrug_type(String drug_type) {
        this.drug_type = drug_type;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public TextField getInput() {
        return input;
    }

    public void setInput(TextField input) {
        this.input = input;
    }

    public CheckBox getChecker() {
        return checker;
    }

    public void setChecker(CheckBox checker) {
        this.checker = checker;
    }

}
