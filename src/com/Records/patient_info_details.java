/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Records;

import com.connection.conclass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author G
 */
public class patient_info_details {

    public Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = "";

    private String reg_code, surname, O_names, sex, marital_status, occupation, religion, home_addr, telephone, email, n_rel, n_rel_con, district, locality, insu_no, insu_id;
    private Date dob;

    public patient_info_details() {
    }

    public patient_info_details(String reg_code, String surname, String O_names, String sex, String marital_status, String occupation, String religion, String home_addr, String telephone, String email, String n_rel, String n_rel_con, String distinct, String locality, String insu_no, String insu_id, Date dob, ObservableList<patient_info_details> ob_paID) {
        this.reg_code = reg_code;
        this.surname = surname;
        this.O_names = O_names;
        this.sex = sex;
        this.marital_status = marital_status;
        this.occupation = occupation;
        this.religion = religion;
        this.home_addr = home_addr;
        this.telephone = telephone;
        this.email = email;
        this.n_rel = n_rel;
        this.n_rel_con = n_rel_con;
        this.district = distinct;
        this.locality = locality;
        this.insu_no = insu_no;
        this.insu_id = insu_id;
        this.dob = dob;
     
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public PreparedStatement getPst() {
        return pst;
    }

    public void setPst(PreparedStatement pst) {
        this.pst = pst;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getReg_code() {
        return reg_code;
    }

    public void setReg_code(String reg_code) {
        this.reg_code = reg_code;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getO_names() {
        return O_names;
    }

    public void setO_names(String O_names) {
        this.O_names = O_names;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getHome_addr() {
        return home_addr;
    }

    public void setHome_addr(String home_addr) {
        this.home_addr = home_addr;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getN_rel() {
        return n_rel;
    }

    public void setN_rel(String n_rel) {
        this.n_rel = n_rel;
    }

    public String getN_rel_con() {
        return n_rel_con;
    }

    public void setN_rel_con(String n_rel_con) {
        this.n_rel_con = n_rel_con;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String distinct) {
        this.district = distinct;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getInsu_no() {
        return insu_no;
    }

    public void setInsu_no(String insu_no) {
        this.insu_no = insu_no;
    }

    public String getInsu_id() {
        return insu_id;
    }

    public void setInsu_id(String insu_id) {
        this.insu_id = insu_id;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    

    
   

    public Service<patient_info_details> loadPatient_details(String pid) {
        Service<patient_info_details> service = new Service<patient_info_details>() {
            @Override
            protected Task<patient_info_details> createTask() {
                return new Task<patient_info_details>() {
                    @Override
                    protected patient_info_details call() throws Exception {
                        conn = conclass.livewell_localhost();
                        sql = "select * from patient_info where registration_no = ? ";
                         patient_info_details paID = new patient_info_details();
                        try {
                            pst = conn.prepareStatement(sql);
                            pst.setString(1, pid);
                            rs = pst.executeQuery();
                            if (rs.next()) {
                                paID = new patient_info_details();
                                paID.setReg_code(rs.getString("registration_no"));//registration code                                 
                                paID.setSurname(rs.getString("surname"));//surname
                                paID.setO_names(rs.getString("other_names"));//other names
                                paID.setSex(rs.getString("sex"));
                                paID.setMarital_status(rs.getString("marital_status"));//marital status
                                paID.setOccupation(rs.getString("occupation"));//occupation
                                paID.setReligion(rs.getString("religion"));//religion
                                paID.setHome_addr(rs.getString("home_address"));//home address
                                paID.setTelephone(rs.getString("telephone"));//phone
                                paID.setEmail(rs.getString("email"));//email
                                paID.setN_rel(rs.getString("nearest_relative"));//nearest_relative
                                paID.setN_rel_con(rs.getString("contact_nearest_relative"));//contact_nearest_relative
                                paID.setDistrict(rs.getString("district"));//district
                                paID.setLocality(rs.getString("locality"));//locality / sub-district
                                paID.setInsu_no(rs.getString("health_insu_no"));//health_insu_no
                                paID.setInsu_id(rs.getString("health_insu_id"));//health id;
                                updateMessage("Loading details of client: " + pid);
                                updateValue(paID);
                            }
                        } catch (SQLException e) {
                        }
                        return paID;
                    }
                };
            }
        };
        return service;
    }
}
