/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Nurses;

import com.connection.conclass;
import com.semantics.semanticsClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author G
 */
public class nurse_patient_object {
//    nurse_id,opd_id,weight,height,temperature,systolis,dystolis,pulse,preg,time_in,on_date,on_transit,registered,complains,history

    /*
                pst.setString(1, nurse_id);
                pst.setString(2, opd_current_selected_patient);
                pst.setDouble(3, Double.parseDouble(txt_weight.getText()));
                pst.setDouble(4, Double.parseDouble(txt_height.getText()));
                pst.setDouble(5, Double.parseDouble(txt_temperature.getText()));
                pst.setDouble(6, Integer.parseInt(txt_sys.getText()));
                pst.setDouble(7, Integer.parseInt(txt_dys.getText()));
                pst.setDouble(8, Integer.parseInt(txt_pulse.getText()));
                pst.setString(9, txt_pregnancy.getText().trim());
                pst.setTimestamp(10, semanticsClass.nowStamp(new Date()));
                pst.setString(11, semanticsClass.returneDate());
                pst.setString(12, "ON"); // this line is to say the patient is on transit to go consultation
                pst.setString(13, "YES");//this line is to say that the patient is registered
                pst.setString(14, txt_complains.getHtmlText());
                pst.setString(15, txt_history.getText());    
     */
    private String nurse_id, opd_id, preg, on_date, on_transit, registered, complains, history;
    private Double weight, height, temperature;
    private int systolis, dystolis, pulse;
    private Timestamp time_in;

    public nurse_patient_object() {
    }

    public nurse_patient_object(String nurse_id, String opd_id, String preg, String on_date, String on_transit, String registered, String complains, String history, Double weight, Double height, Double temperature, int systolis, int dystolis, int pulse, Timestamp time_in) {
        this.nurse_id = nurse_id;
        this.opd_id = opd_id;
        this.preg = preg;
        this.on_date = on_date;
        this.on_transit = on_transit;
        this.registered = registered;
        this.complains = complains;
        this.history = history;
        this.weight = weight;
        this.height = height;
        this.temperature = temperature;
        this.systolis = systolis;
        this.dystolis = dystolis;
        this.pulse = pulse;
        this.time_in = time_in;
    }

    public String getNurse_id() {
        return nurse_id;
    }

    public void setNurse_id(String nurse_id) {
        this.nurse_id = nurse_id;
    }

    public String getOpd_id() {
        return opd_id;
    }

    public void setOpd_id(String opd_id) {
        this.opd_id = opd_id;
    }

    public String getPreg() {
        return preg;
    }

    public void setPreg(String preg) {
        this.preg = preg;
    }

    public String getOn_date() {
        return on_date;
    }

    public void setOn_date(String on_date) {
        this.on_date = on_date;
    }

    public String getOn_transit() {
        return on_transit;
    }

    public void setOn_transit(String on_transit) {
        this.on_transit = on_transit;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getComplains() {
        return complains;
    }

    public void setComplains(String complains) {
        this.complains = complains;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public int getSystolis() {
        return systolis;
    }

    public void setSystolis(int systolis) {
        this.systolis = systolis;
    }

    public int getDystolis() {
        return dystolis;
    }

    public void setDystolis(int dystolis) {
        this.dystolis = dystolis;
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public Timestamp getTime_in() {
        return time_in;
    }

    public void setTime_in(Timestamp time_in) {
        this.time_in = time_in;
    }

    public class patientOperations {

        String sql = "";
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = null;

        public Service<Boolean> addPatient(nurse_patient_object npo) {
            Service<Boolean> service = new Service<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            conn = conclass.livewell_localhost();
                            sql = "insert into nurses_on_patient "
                                    + "(nurse_id,opd_id,weight,height,temperature,systolis,dystolis,pulse,preg,time_in,on_date,on_transit,registered,complains,history) "
                                    + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                            try {
                                pst = conn.prepareStatement(sql);
                                pst.setString(1, npo.nurse_id);
                                pst.setString(2, npo.opd_id);
                                pst.setDouble(3, npo.getWeight());
                                pst.setDouble(4, npo.getHeight());
                                pst.setDouble(5, npo.getTemperature());
                                pst.setInt(6, npo.getSystolis());
                                pst.setInt(7, npo.getDystolis());
                                pst.setInt(8, npo.getPulse());
                                pst.setString(9, npo.getPreg());
                                pst.setTimestamp(10, semanticsClass.nowStamp(new Date()));
                                pst.setString(11, semanticsClass.returneDate());
                                pst.setString(12, "ON"); // this line is to say the patient is on transit to go consultation
                                pst.setString(13, "YES");//this line is to say that the patient is registered
                                pst.setString(14, npo.getComplains());
                                pst.setString(15, npo.getHistory());
                                pst.execute();
                                return true;
                            } catch (SQLException e) {
                                cancel();
                                updateMessage(e.getMessage());
                                System.out.println(e);
                                return false;
                            }
                        }
                    };
                }
            };
            return service;
        }

      

    }

}
