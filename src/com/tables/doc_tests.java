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
public class doc_tests {
    private String pid,test_type,on_date,status;
    private Timestamp record_time;
    public doc_tests() {
    }

    public doc_tests(String pid, String test_type, String on_date, String status, Timestamp record_time) {
        this.pid = pid;
        this.test_type = test_type;
        this.on_date = on_date;
        this.status = status;
        this.record_time = record_time;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTest_type() {
        return test_type;
    }

    public void setTest_type(String test_type) {
        this.test_type = test_type;
    }

    public String getOn_date() {
        return on_date;
    }

    public void setOn_date(String on_date) {
        this.on_date = on_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getRecord_time() {
        return record_time;
    }

    public void setRecord_time(Timestamp record_time) {
        this.record_time = record_time;
    }

       
    
    
}
