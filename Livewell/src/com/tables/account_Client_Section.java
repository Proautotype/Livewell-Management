/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tables;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class account_Client_Section extends HBox {

    private String cid, client_name;
    private String client_contact, client_address, client_desc, client_type;
  

    public account_Client_Section() {
    }

    public account_Client_Section(String cid, String client_name, String client_contact, String client_address, String client_desc, String client_type) {
        this.cid = cid;
        this.client_name = client_name;
        this.client_contact = client_contact;
        this.client_address = client_address;
        this.client_desc = client_desc;
        this.client_type = client_type;       
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getClient_contact() {
        return client_contact;
    }

    public void setClient_contact(String client_contact) {
        this.client_contact = client_contact;
    }

    public String getClient_address() {
        return client_address;
    }

    public void setClient_address(String client_address) {
        this.client_address = client_address;
    }

    public String getClient_desc() {
        return client_desc;
    }

    public void setClient_desc(String client_desc) {
        this.client_desc = client_desc;
    }

    public String getClient_type() {
        return client_type;
    }

    public void setClient_type(String client_type) {
        this.client_type = client_type;
    }

}
