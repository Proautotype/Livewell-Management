/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.Laboratory.Haematology_testController;
import com.connection.conclass;
import com.semantics.semanticsClass;
import com.tables.lab_KFT;
import com.tables.lab_LFT;
import com.tables.lab_haematology_table;
import com.tables.lab_widal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class Laboratory_Services {

    Connection conn = null;
    PreparedStatement pst = null;
    String sql = "";
    ResultSet rs = null;

    ObservableList<String> myList = FXCollections.observableArrayList();
    String user_id = "";
    int counter = 0;

    private String pid, blood_groupin, rhesus, hb, hepatitis,
            sickling, blood_film, HB_Elec, g6pd, vdrl,
            H_pylori, FBS, RBS, WBC, ESR, cholestrol, on_date;

    public Laboratory_Services() {
        conn = conclass.livewell_localhost();
    }

    public Laboratory_Services(String pid, String blood_groupin, String rhesus, String hb, String hepatitis, String sickling, String blood_film, String HB_Elec, String g6pd, String vdrl, String H_pylori, String FBS, String RBS, String WBC, String ESR, String cholestrol, String on_date) {
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
        user_id = Haematology_testController.pid;
    }

    public ScheduledService<ObservableList<String>> myLabIncoming() {
        ScheduledService<ObservableList<String>> service = new ScheduledService<ObservableList<String>>() {
            @Override
            protected Task<ObservableList<String>> createTask() {
                return new Task<ObservableList<String>>() {
                    @Override
                    protected ObservableList<String> call() throws Exception {
                        try {
                            sql = "select status,opd_id,diagnostic from doctors_on_patient where on_date = '" + semanticsClass.returneDate() + "'";
                            conn = conclass.livewell_localhost();
                            pst = conn.prepareStatement(sql);
                            rs = pst.executeQuery();
                            while (rs.next()) {
                                rs.getString("diagnostic");
                                if (!rs.wasNull()) {
                                    String states = rs.getString("status") + ":" + rs.getString("opd_id");
                                    String[] _id_states = states.split("[:]");
                                    switch (_id_states[0]) {
                                        case "TP":
                                            myList.add(_id_states[1]);
                                            updateProgress(counter + 1, rs.getFetchSize());
                                            updateValue(myList);
                                            System.out.println(_id_states[0] + "_-_" + _id_states[1]);
                                            break;
                                    }
                                }
                            }

                        } catch (SQLException e) {
                            System.out.println(e);
                        }
                        return myList;
                    }
                };
            }
        };
        return service;
    }

    public Service<Boolean> Haematology_add() {
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
                            System.out.println(user_id);
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
                            pst.execute();
                            updateMessage("Save successful");
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
                                    + "WHERE pid = ? AND on_date = ?";

                            conn = conclass.livewell_localhost();
                            System.out.println(user_id + " " + blood_film + " " + blood_groupin + " " + rhesus);
                            pst = conn.prepareStatement(sql);
                            pst.setString(1, user_id);
                            pst.setString(2, semanticsClass.returneDate());
                            pst.execute();
                            updateMessage("Update succesful");
                            return true;
                        } catch (SQLException e) {
                            System.out.println(e);
                            System.out.println(e.getErrorCode());
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
                            return null;
                        }
                    }
                };
            }
        };
        return service;
    }

    lab_haematology_table lht = new lab_haematology_table();
    ObservableList<lab_haematology_table> observable_lht = FXCollections.observableArrayList();

    public Service<ObservableList<lab_haematology_table>> load_haematology_list() {

        Service<ObservableList<lab_haematology_table>> service = new Service<ObservableList<lab_haematology_table>>() {
            @Override
            protected Task<ObservableList<lab_haematology_table>> createTask() {
                return new Task<ObservableList<lab_haematology_table>>() {
                    @Override
                    protected ObservableList<lab_haematology_table> call() throws Exception {
                        try {
                            sql = "select * from lab_haematology";
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
                                        rs.getString("cholesterol"), rs.getString("on_date"), semanticsClass.nowStamp(new Date()));
                                observable_lht.add(lht);
                                updateValue(observable_lht);
                            }
                        } catch (SQLException e) {
                            System.out.println(e);
                        }
                        return null;
                    }
                };
            }
        };
        return service;
    }

    public class widal {

        public widal() {
        }

        public Service<Boolean> widal_add(lab_widal lw) {
            Service<Boolean> service = new Service<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    //To change body of generated methods, choose Tools | Templates.
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            //To change body of generated methods, choose Tools | Templates.
                            try {
                                if (lw.getPid().isEmpty()) {
                                    cancel();
                                    updateMessage("Patient ID is invalid or not available");
                                } else {
                                    sql = "insert into lab_widal (pid,sal_typhi_O,sal_typhi_H,RHST,on_date) values (?,?,?,?,?)";
                                    conn = conclass.livewell_localhost();
                                    pst = conn.prepareStatement(sql);
                                    pst.setString(1, lw.getPid());
                                    pst.setString(2, lw.getSalmonellaTO());
                                    pst.setString(3, lw.getSalmonellaTH());
                                    pst.setString(4, lw.getRHSt());
                                    pst.setString(5, lw.getOn_date());
                                    System.out.println(lw.getPid() + " " + lw.getOn_date());
                                    pst.execute();
                                    updateMessage("Save complete");
                                    return true;
                                }
                            } catch (SQLException e) {
                                cancel();
                                semanticsClass.SySoutException(e);
                                return null;
                            }
                            return null;
                        }
                    };
                }

            };
            return service;
        }

        public Service<Boolean> widal_update(lab_widal lw) {
            Service<Boolean> service = new Service<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    //To change body of generated methods, choose Tools | Templates.
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            if (lw.getPid().isEmpty()) {
                                cancel();
                                updateMessage("Patient ID is invalid or not available");
                            } else {
                                try {
                                    sql = "update lab_widal set sal_typhi_O = '" + lw.getSalmonellaTO() + "',sal_typhi_H = '" + lw.getSalmonellaTH() + "',RHST = '" + lw.getRHSt() + "' "
                                            + "where pid = '" + lw.getPid() + "' and on_date = '" + lw.getOn_date() + "'";
                                    conn = conclass.livewell_localhost();
                                    pst = conn.prepareStatement(sql);
                                    System.out.println(lw.getPid() + " " + lw.getOn_date());
                                    pst.execute();
                                    updateMessage("Update complete");
                                    return true;

                                } catch (SQLException e) {
                                    semanticsClass.SySoutException(e);
                                    cancel();
                                    return null;
                                }
                            }

                            return null;
                        }
                    };
                }

            };
            return service;
        }

    }

    public class KFT {

        public KFT() {
            conn = conclass.livewell_localhost();
        }

        public Service<Boolean> KFT_add(lab_KFT kft) {
            Service<Boolean> service = new Service<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            try {
//                                Pid,sodium, potassium, chloride, total_co2, urea, creatinine, eGFR,On_date
                                sql = "insert into lab_kft (pid,sodium, potassium, chloride, total_co2, urea, creatinine, eGFR,on_date,record_time)"
                                        + " values (?,?,?,?,?,?,?,?,?,?)";

                                pst = conn.prepareStatement(sql);
                                pst.setString(1, kft.getPid());
                                pst.setString(2, kft.getSodium());
                                pst.setString(3, kft.getPotassium());
                                pst.setString(4, kft.getChloride());
                                pst.setString(5, kft.getTotal_co2());
                                pst.setString(6, kft.getUrea());
                                pst.setString(7, kft.getCreatinine());
                                pst.setString(8, kft.getEgfr());
                                pst.setString(9, kft.getOn_date());
                                pst.setTimestamp(10, kft.getRecord_time());
                                pst.execute();
                                updateMessage("Save complete");
                                return true;
                            } catch (SQLException e) {
                                if (e.getErrorCode() == 1062) {
                                    updateMessage("KIDNEY FUNCTION TEST RESULTS IS ALREADY AVAILABLE\nKINDLY UPDATE");
                                } else {
                                    updateMessage("An occured with code: " + e.getErrorCode());
                                    System.out.println(e);
                                }

                                cancel();
                                return null;
                            }
                        }
                    };
                }
            };
            return service;
        }

        public Service<Boolean> KFT_update(lab_KFT kft) {
            Service<Boolean> service = new Service<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            try {//                               
                                sql = "update lab_kft set "
                                        + "sodium = '" + kft.getSodium() + "' ,"
                                        + " potassium ='" + kft.getPotassium() + "', "
                                        + "chloride ='" + kft.getChloride() + "' , "
                                        + "total_co2 = '" + kft.getTotal_co2() + "', "
                                        + "urea = '" + kft.getUrea() + "',"
                                        + " creatinine = '" + kft.getCreatinine() + "', "
                                        + "eGFR = '" + kft.getEgfr() + "' "
                                        + "where on_date = '" + kft.getOn_date() + "' and pid = '" + kft.getPid() + "' ";

                                System.out.println(kft.getPid());
                                pst = conn.prepareStatement(sql);
                                pst.execute();
                                updateMessage("Update complete");
                                return true;
                            } catch (SQLException e) {
                                updateMessage("An occured with code:" + e.getErrorCode());
                                System.out.println(e);
                                cancel();
                                return false;
                            }
                        }
                    };
                }
            };
            return service;
        }

        ObservableList<lab_KFT> observe_kft_list = FXCollections.observableArrayList();
        lab_KFT default_kft = new lab_KFT();

        public Service<ObservableList<lab_KFT>> kft_list(String valuer) {
            Service<ObservableList<lab_KFT>> service = new Service<ObservableList<lab_KFT>>() {
                @Override
                protected Task<ObservableList<lab_KFT>> createTask() {
                    return new Task<ObservableList<lab_KFT>>() {
                        @Override
                        protected ObservableList<lab_KFT> call() throws Exception {

                            if (valuer.contentEquals("*")) {
                                sql = "select * from livewell.lab_kft order by record_time";
                            } else {
                                sql = "select * from livewell.lab_kft where on_date = '" + valuer + "' or pid = '" + valuer + "'  order by record_time";
                            }

                            try {
                                conn = conclass.livewell_localhost();
                                pst = conn.prepareStatement(sql);
                                rs = pst.executeQuery();
                                while (rs.next()) {
                                    default_kft = new lab_KFT(
                                            rs.getString("pid"), rs.getString("sodium"),
                                            rs.getString("potassium"), rs.getString("chloride"),
                                            rs.getString("total_co2"), rs.getString("urea"),
                                            rs.getString("creatinine"), rs.getString("egfr"),
                                            rs.getString("on_date"),
                                            rs.getTimestamp("record_time")
                                    );
                                    observe_kft_list.add(default_kft);
                                    updateValue(observe_kft_list);
                                }
                                return observe_kft_list;
                            } catch (SQLException e) {
                                System.out.println(e);
                                return null;
                            }
                        }
                    };
                }
            };
            return service;
        }

    }

    public class LFT {

        public LFT() {
            conn = conclass.livewell_localhost();
        }

        public Service<Boolean> LFT_add(lab_LFT lft) {
            Service<Boolean> service = new Service<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            try {
                                pst = conn.prepareStatement(""
                                        + "insert into lab_lft "
                                        + "(pid,protein,albumin,AST,ALT,GGT,ALP,T_BILIRUBIN,H_BILIRUBIN,on_date,record_time) "
                                        + "values (?,?,?,?,?,?,?,?,?,?,?)");
                                pst.setString(1, lft.getPid());
                                pst.setString(2, lft.getProtein());
                                pst.setString(3, lft.getAlbumin());
                                pst.setString(4, lft.getAST());
                                pst.setString(5, lft.getALT());
                                pst.setString(6, lft.getGGT());
                                pst.setString(7, lft.getALP());
                                pst.setString(8, lft.getT_BILIRUBIN());
                                pst.setString(9, lft.getH_BILIRUBIN());
                                pst.setString(10, lft.getOn_date());
                                pst.setTimestamp(11, lft.getRecord_time());
                                pst.execute();
                                updateMessage("Saved successfully");
                                return true;
                            } catch (SQLException e) {
                                updateMessage("An occured with code:" + e.getErrorCode());
                                System.out.println(e);
                                cancel();
                                return null;
                            }
                        }
                    };
                }
            };
            return service;
        }

        public Service<Boolean> LFT_update(lab_LFT lft) {
            Service<Boolean> service = new Service<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            try {
                                pst = conn.prepareStatement("update lab_lft set "
                                        + "protein = '" + lft.getProtein() + "',"
                                        + "albumin = '" + lft.getAlbumin() + "' ,AST = '" + lft.getAST() + "',"
                                        + "ALT = '" + lft.getALT() + "',GGT = '" + lft.getGGT() + "',"
                                        + "ALP = '" + lft.getALP() + "',T_BILIRUBIN = '" + lft.getT_BILIRUBIN() + "',"
                                        + "H_BILIRUBIN = '" + lft.getH_BILIRUBIN() + "' "
                                        + "where pid = '" + lft.getPid() + "' and on_date =  '" + lft.getOn_date() + "'");
                                pst.execute();
                                updateMessage("Saved successfully");
                                return true;
                            } catch (SQLException e) {
                                updateMessage("An occured with code:" + e.getErrorCode());
                                System.out.println(e);
                                cancel();
                                return null;
                            }
                        }
                    };
                }
            };
            return service;
        }

        ObservableList<lab_LFT> observe_lft_list = FXCollections.observableArrayList();
        lab_LFT default_lft = new lab_LFT();

        public Service<ObservableList<lab_LFT>> lft_list(String valuer) {
            Service<ObservableList<lab_LFT>> service = new Service<ObservableList<lab_LFT>>() {
                @Override
                protected Task<ObservableList<lab_LFT>> createTask() {
                    return new Task<ObservableList<lab_LFT>>() {
                        @Override
                        protected ObservableList<lab_LFT> call() throws Exception {
                            if (valuer.contentEquals("*")) {
                                sql = "select * from livewell.lab_lft";
                            } else {
                                sql = "select * from livewell.lab_lft where on_date = '" + valuer + "' or pid = '" + valuer + "'";
                            }
                            try {

                                pst = conn.prepareStatement(sql);
                                rs = pst.executeQuery();
                                while (rs.next()) {
                                    default_lft = new lab_LFT();
                                    default_lft.setPid(rs.getString("pid"));
                                    default_lft.setProtein(rs.getString("protein"));
                                    default_lft.setAlbumin(rs.getString("albumin"));
                                    default_lft.setAST(rs.getString("ast"));
                                    default_lft.setGGT(rs.getString("ggt"));
                                    default_lft.setALP(rs.getString("alp"));
                                    default_lft.setT_BILIRUBIN(rs.getString("T_Bilirubin"));
                                    default_lft.setH_BILIRUBIN(rs.getString("H_Bilirubin"));
                                    default_lft.setOn_date(rs.getString("on_date"));
                                    default_lft.setRecord_time(rs.getTimestamp("record_time"));
                                    observe_lft_list.add(default_lft);
                                    updateValue(observe_lft_list);
                                }
                                return observe_lft_list;
                            } catch (SQLException e) {
                                System.out.println(e);
                                return null;
                            }
                        }
                    };
                }
            };
            return service;
        }
    }

}
