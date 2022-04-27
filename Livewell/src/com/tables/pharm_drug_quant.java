/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class pharm_drug_quant {
    
    private String drug_code ;
    private String drug_name ;
    private int drug_quantity;
    
    public pharm_drug_quant() {
    }

    public pharm_drug_quant(String drug_code, String drug_name, int drug_quantity) {
        this.drug_code = drug_code;
        this.drug_name = drug_name;
        this.drug_quantity = drug_quantity;
    }

    public String getDrug_code() {
        return drug_code;
    }

    public void setDrug_code(String drug_code) {
        this.drug_code = drug_code;
    }

    public String getDrug_name() {
        return drug_name;
    }

    public void setDrug_name(String drug_name) {
        this.drug_name = drug_name;
    }

    public int getDrug_quantity() {
        return drug_quantity;
    }

    public void setDrug_quantity(int drug_quantity) {
        this.drug_quantity = drug_quantity;
    }
    
        
}
