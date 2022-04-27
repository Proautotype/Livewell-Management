/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.Laboratory.LaboratoryController;
import com.connection.conclass;
import com.semantics.semanticsClass;
import com.tables.lab_haematology_table;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class haematology_services {

    Connection conn = null;
    PreparedStatement pst = null;
    String sql = "";
    ResultSet rs = null;

    private String user_id;

    private String pid, blood_groupin, rhesus, hb, hepatitis,
            sickling, blood_film, HB_Elec, g6pd, vdrl,
            H_pylori, FBS, RBS, WBC, ESR, cholestrol, on_date;

    public haematology_services() {
    }

    public haematology_services(String pid, String blood_groupin, String rhesus, String hb, String hepatitis, String sickling, String blood_film, String HB_Elec, String g6pd, String vdrl, String H_pylori, String FBS, String RBS, String WBC, String ESR, String cholestrol, String on_date) {
        this.pid = pid;
        this.blood_groupin = blood_groupin;
        this.rhesus = rhesus;
        this.hb = hb;
        this.hepatitis = hepatitis;
        this.sickling = sickling;
        this.blood_film = blood_film;
        this.HB_Elec = HB_Elec;
        this.g6pd = g6pd;
        this.vdrl = vdrl;
        this.H_pylori = H_pylori;
        this.FBS = FBS;
        this.RBS = RBS;
        this.WBC = WBC;
        this.ESR = ESR;
        this.cholestrol = cholestrol;
        this.on_date = on_date;
    }

    public class Haema {

        public Haema() {
        }

        public Service<Boolean> Haematology_add() {
            user_id = LaboratoryController.pid;
            Service<Boolean> service = new Service<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            try {
                                sql = "insert into LAB_HAEMATOLOGY"
                                        + " (pid,blood_grouping,rhesus,HB,hepatitis_B,"
                                        + "sickling_screening,blood_film,hb_electrophoresis,"
                                        + "g6pd,vdrl,h_pyroli,fbs,rbs,wbc,esr,cholesterol,on_date) "
                                        + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                                conn = conclass.livewell_localhost();
                                pst = conn.prepareStatement(sql);
                                pst.setString(1, pid);
                                pst.setString(2, blood_groupin);
                                pst.setString(3, rhesus);
                                pst.setString(4, hb);
                                pst.setString(5, hepatitis);
                                pst.setString(6, sickling);
                                pst.setString(7, blood_film);
                                pst.setString(8, HB_Elec);
                                pst.setString(9, g6pd);
                                pst.setString(10, vdrl);
                                pst.setString(11, H_pylori);
                                pst.setString(12, FBS);
                                pst.setString(13, RBS);
                                pst.setString(14, WBC);
                                pst.setString(15, ESR);
                                pst.setString(16, cholestrol);
                                pst.setString(17, on_date);
                                updateValue(pst.execute());
                                return true;
                            } catch (SQLException e) {
                                switch (e.getErrorCode()) {
                                    case 1062:
                                        updateMessage("Earlier haematology test is available for this patient... \nUpdating...");
                                        cancel();

                                        break;
                                    case 1048:
                                        System.out.println(e);
                                        updateMessage("Plese, press SHIFT+A , then a select patient by double \nclicking the patient's id, from the list");
                                        cancel();
                                        break;
                                    default:
                                        updateMessage("An error occured");
                                        System.out.println(e);
                                        System.out.println(e.getErrorCode());
                                        break;
                                }

                                return null;
                            }
                        }
                    };
                }
            };
            return service;
        }

        public Service<Boolean> Haematology_update() {
            user_id = LaboratoryController.pid;
            Service<Boolean> service = new Service<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            System.out.println("state on working");
                            try {
                                sql = " UPDATE livewell.lab_haematology SET "
                                        + "blood_grouping = '" + blood_groupin + "',"
                                        + "rhesus = '" + rhesus + "', HB = '" + hb + "', "
                                        + "hepatitis_B = '" + hepatitis + "', sickling_screening = '" + sickling + "', "
                                        + "blood_film = '" + blood_film + "', hb_electrophoresis = '" + HB_Elec + "', "
                                        + "g6pd = '" + g6pd + "', vdrl = '" + vdrl + "', "
                                        + "h_pyroli = '" + H_pylori + "', FBS = '" + FBS + "', "
                                        + "RBS = '" + RBS + "', WBC = '" + WBC + "', "
                                        + "ESR = '" + ESR + "', cholesterol = '" + cholestrol + "' "
                                        + "WHERE pid = '" + user_id + "' AND on_date = '" + semanticsClass.returneDate() + "' ";

                                conn = conclass.livewell_localhost();
                                pst = conn.prepareStatement(sql);
                                updateValue(pst.execute());
                                System.out.println("working");
                                return true;
                            } catch (SQLException e) {
                                switch (e.getErrorCode()) {
                                    case 1048:
                                        updateMessage("Plese, press SHIFT+A , then a select patient by double \nclicking the patient's id, from the list");
                                        cancel();
                                        break;
                                    default:
                                        updateMessage("An error occured");
                                        System.out.println(e);
                                        System.out.println(e.getErrorCode());
                                        break;
                                }
                                System.out.println(e);
                                System.out.println(e.getErrorCode());
                                return null;
                            }
                        }
                    };
                }
            };
            return service;

        }

        lab_haematology_table lht = new lab_haematology_table();
        ObservableList<lab_haematology_table> observe_lht = FXCollections.observableArrayList();

        public Service<ObservableList<lab_haematology_table>> load_haema(String valuer) {
            Service<ObservableList<lab_haematology_table>> service = new Service<ObservableList<lab_haematology_table>>() {
                @Override
                protected Task<ObservableList<lab_haematology_table>> createTask() {
                    return new Task<ObservableList<lab_haematology_table>>() {
                        @Override
                        protected ObservableList<lab_haematology_table> call() throws Exception {
                            try {                                

                                if (valuer.contentEquals("*")) {
                                    sql = "select * from livewell.lab_haematology order by record_time";
                                } else {
                                    sql = "select * from livewell.lab_haematology where on_date = '" + valuer + "' or pid = '" + valuer + "' order by record_time";
                                }

                                conn = conclass.livewell_localhost();
                                pst = conn.prepareStatement(sql);
                                rs = pst.executeQuery();
                                while (rs.next()) {
                                    lht = new lab_haematology_table(
                                            rs.getString("pid"), rs.getString("blood_grouping"),
                                            rs.getString("rhesus"),
                                            rs.getString("HB"), rs.getString("hepatitis_B"),
                                            rs.getString("sickling_screening"),
                                            rs.getString("blood_film"), rs.getString("hb_electrophoresis"),
                                            rs.getString("g6pd"),
                                            rs.getString("vdrl"), rs.getString("h_pyroli"),
                                            rs.getString("FBS"),
                                            rs.getString("RBS"), rs.getString("WBC"),
                                            rs.getString("ESR"),
                                            rs.getString("cholesterol"),
                                            rs.getString("on_date"), rs.getTimestamp("record_time"));
                                    observe_lht.add(lht);
                                    updateValue(observe_lht);
                                }
                            } catch (SQLException e) {
                                System.out.println(e);
                            }
                            return observe_lht;
                        }
                    };
                }
            };
            return service;
        }

    }
}
