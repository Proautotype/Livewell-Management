/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sqlObjects;

import java.util.Date;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class Administrator_getUserDetails {
    private String customId;
    private String fname,sname,sex,contact,email,loe,department;
    private Date dob;

    public Administrator_getUserDetails(){    
    } 

    public Administrator_getUserDetails(String customId, String fname, String sname, String sex, String contact, String email, String loe, String department, Date dob) {
        this.customId = customId;
        this.fname = fname;
        this.sname = sname;
        this.sex = sex;
        this.contact = contact;
        this.email = email;
        this.loe = loe;
        this.department = department;
        this.dob = dob;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoe() {
        return loe;
    }

    public void setLoe(String loe) {
        this.loe = loe;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
    
       
    
    
}
