/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tables;

import java.sql.Timestamp;
import javafx.scene.control.Label;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class account_client_section_stmts {

    private String accountant_id,stmt_cid , stmt_item, stmt_item_type, stmt_qty, stmt_cost, stmt_amt_paid,on_date;
    private Label stmt_balance;
    private Timestamp rec_date;

    public account_client_section_stmts() {
    }

    public account_client_section_stmts(String accountant_id, String stmt_cid, String stmt_item, String stmt_item_type, String stmt_qty, String stmt_cost, String stmt_amt_paid, Label stmt_balance, String on_date, Timestamp rec_date) {
        this.accountant_id = accountant_id;
        this.stmt_cid = stmt_cid;
        this.stmt_item = stmt_item;
        this.stmt_item_type = stmt_item_type;
        this.stmt_qty = stmt_qty;
        this.stmt_cost = stmt_cost;
        this.stmt_amt_paid = stmt_amt_paid;
        this.stmt_balance = stmt_balance;
        this.on_date = on_date;
        this.rec_date = rec_date;
    }

    public String getAccountant_id() {
        return accountant_id;
    }

    public void setAccountant_id(String accountant_id) {
        this.accountant_id = accountant_id;
    }

    public String getStmt_cid() {
        return stmt_cid;
    }

    public void setStmt_cid(String stmt_cid) {
        this.stmt_cid = stmt_cid;
    }

    public String getStmt_item() {
        return stmt_item;
    }

    public void setStmt_item(String stmt_item) {
        this.stmt_item = stmt_item;
    }

    public String getStmt_item_type() {
        return stmt_item_type;
    }

    public void setStmt_item_type(String stmt_item_type) {
        this.stmt_item_type = stmt_item_type;
    }

    public String getStmt_qty() {
        return stmt_qty;
    }

    public void setStmt_qty(String stmt_qty) {
        this.stmt_qty = stmt_qty;
    }

    public String getStmt_cost() {
        return stmt_cost;
    }

    public void setStmt_cost(String stmt_cost) {
        this.stmt_cost = stmt_cost;
    }

    public String getStmt_amt_paid() {
        return stmt_amt_paid;
    }

    public void setStmt_amt_paid(String stmt_amt_paid) {
        this.stmt_amt_paid = stmt_amt_paid;
    }

    public Label getStmt_balance() {
        return stmt_balance;
    }

    public void setStmt_balance(Label stmt_balance) {
        this.stmt_balance = stmt_balance;
    }

    public String getOn_date() {
        return on_date;
    }

    public void setOn_date(String on_date) {
        this.on_date = on_date;
    }

    public Timestamp getRec_date() {
        return rec_date;
    }

    public void setRec_date(Timestamp rec_date) {
        this.rec_date = rec_date;
    }

    
    
}
