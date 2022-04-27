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
public class prescription_table {
    private String drug_cod;
    private String drug;
    private String drug_type;
    private String dosage;
    private String freq;
    private String duration;
    private String price;

    public prescription_table(String drug_cod, String drug, String drug_type, String dosage,String price) {
        this.drug_cod = drug_cod;
        this.drug = drug;
        this.drug_type = drug_type;
        this.dosage = dosage;        
        this.price = price;
    } 

    /**
     * @return the drug_type
     */
    public String getDrug_type() {
        return drug_type;
    }

    /**
     * @param drug_type the drug_type to set
     */
    public void setDrug_type(String drug_type) {
        this.drug_type = drug_type;
    }

    /**
     * @return the drug
     */
    public String getDrug() {
        return drug;
    }

    /**
     * @param drug the drug to set
     */
    public void setDrug(String drug) {
        this.drug = drug;
    }

    /**
     * @return the drug_cod
     */
    public String getDrug_cod() {
        return drug_cod;
    }

    /**
     * @param drug_cod the drug_cod to set
     */
    public void setDrug_cod(String drug_cod) {
        this.drug_cod = drug_cod;
    }

    /**
     * @return the dosage
     */
    public String getDosage() {
        return dosage;
    }

    /**
     * @param dosage the dosage to set
     */
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
    /**
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param Price the amount to set
     */
    public void setPrice(String Price) {
        this.price = Price;
    }
    
    
    
}
