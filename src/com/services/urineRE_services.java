/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.connection.conclass;
import com.tables.lab_urine;
import com.tables.lab_microscopy;
import com.tables.lab_stools;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TableView;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class urineRE_services {

    Connection conn = null;
    PreparedStatement pst = null;
    String sql = "";
    ResultSet rs = null;

    public urineRE_services() {
        conn = conclass.livewell_localhost();
    }

    public class urine {

        public Service<Boolean> add_urine_test(lab_urine lab_urine) {
            rs = null;
            pst = null;
            Service<Boolean> service = new Service<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            try {

                                sql = "insert into lab_urine (pid,appearance, colour, leukocytes,urobilinogen, bilirubin, blood, nitrite, ph,spec_gravity, protein, glucose, ketones,on_date,record_time)"
                                        + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                                conn = conclass.livewell_localhost();
                                pst = conn.prepareStatement(sql);
                                pst.setString(1, lab_urine.getPid());
                                pst.setString(2, lab_urine.getAppearance());
                                pst.setString(3, lab_urine.getColour());
                                pst.setString(4, lab_urine.getLeukocytes());
                                pst.setString(5, lab_urine.getUrobilinogen());
                                pst.setString(6, lab_urine.getBilirubin());
                                pst.setString(7, lab_urine.getBlood());
                                pst.setString(8, lab_urine.getNitrite());
                                pst.setString(9, lab_urine.getPh());
                                pst.setString(10, lab_urine.getSpec_gravity());
                                pst.setString(11, lab_urine.getProtein());
                                pst.setString(12, lab_urine.getGlucose());
                                pst.setString(13, lab_urine.getKetones());
                                pst.setString(14, lab_urine.getOn_date());
                                pst.setTimestamp(15, lab_urine.getRecord_time());
                                pst.execute();
                                updateMessage("Urine test result taken");
                                return true;
                            } catch (SQLException e) {
                                if (e.getErrorCode() == 1062) {
                                    updateMessage("URINE TEST RESULTS IS ALREADY AVAILABLE\nKINDLY UPDATE");
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

        public Service<Boolean> update_urine_test(lab_urine lab_urine) {
            rs = null;
            pst = null;
            Service<Boolean> service = new Service<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            try {
                                sql = "update lab_urine set "
                                        + "appearance = '" + lab_urine.getAppearance() + "', "
                                        + "colour = '" + lab_urine.getColour() + "', "
                                        + "leukocytes = '" + lab_urine.getLeukocytes() + "', "
                                        + "urobilinogen = '" + lab_urine.getUrobilinogen() + "', "
                                        + "bilirubin = '" + lab_urine.getBilirubin() + "', "
                                        + "blood = '" + lab_urine.getBlood() + "',"
                                        + "nitrite = '" + lab_urine.getNitrite() + "', "
                                        + "ph = '" + lab_urine.getPh() + "',"
                                        + "spec_gravity = '" + lab_urine.getSpec_gravity() + "',"
                                        + "protein = '" + lab_urine.getProtein() + "',"
                                        + "glucose = '" + lab_urine.getGlucose() + "',"
                                        + "ketones = '" + lab_urine.getKetones() + "'"
                                        + "where pid = '" + lab_urine.getPid() + "' "
                                        + "and on_date = '" + lab_urine.getOn_date() + "' ";
//                                conn = conclass.livewell_localhost();
                                pst = conn.prepareStatement(sql);
                                pst.execute();
                                updateMessage("Urine test result updated");
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

        ObservableList<lab_urine> observe_urineTest_list = FXCollections.observableArrayList();
        lab_urine default_utl = new lab_urine();

        public Service<ObservableList<lab_urine>> urine_test_list(String valuer) {
            rs = null;
            pst = null;
            Service<ObservableList<lab_urine>> service = new Service<ObservableList<lab_urine>>() {
                @Override
                protected Task<ObservableList<lab_urine>> createTask() {
                    return new Task<ObservableList<lab_urine>>() {
                        @Override
                        protected ObservableList<lab_urine> call() throws Exception {
                            if (valuer.contentEquals("*")) {
                                sql = "select * from livewell.lab_urine order by record_time";
                            } else {
                                sql = "select * from livewell.lab_urine where on_date = '" + valuer + "' or pid = '" + valuer + "' order by record_time";
                            }
                            try {
//                                conn = conclass.livewell_localhost();
                                pst = conn.prepareStatement(sql);
                                rs = pst.executeQuery();
                                while (rs.next()) {
                                    default_utl = new lab_urine();
                                    default_utl.setPid(rs.getString("lab_urine.pid"));
                                    default_utl.setAppearance(rs.getString("lab_urine.appearance"));
                                    default_utl.setColour(rs.getString("lab_urine.colour"));
                                    default_utl.setLeukocytes(rs.getString("lab_urine.leukocytes"));
                                    default_utl.setUrobilinogen(rs.getString("lab_urine.urobilinogen"));
                                    default_utl.setBilirubin(rs.getString("lab_urine.bilirubin"));
                                    default_utl.setBlood(rs.getString("lab_urine.blood"));
                                    default_utl.setNitrite(rs.getString("lab_urine.nitrite"));
                                    default_utl.setPh(rs.getString("lab_urine.ph"));
                                    default_utl.setSpec_gravity(rs.getString("lab_urine.spec_gravity"));
                                    default_utl.setProtein(rs.getString("lab_urine.protein"));
                                    default_utl.setGlucose(rs.getString("lab_urine.glucose"));
                                    default_utl.setKetones(rs.getString("lab_urine.ketones"));
                                    default_utl.setOn_date(rs.getString("lab_urine.on_date"));

                                    observe_urineTest_list.add(default_utl);
                                    updateValue(observe_urineTest_list);
                                }
                                return observe_urineTest_list;
                            } catch (SQLException e) {
                                cancel();
                                updateMessage(e.getMessage());
                                System.out.println(e);
                                System.out.println("urine " + e);
                                return null;
                            } finally {
                                try {
                                    rs.close();
                                    pst.close();
                                } catch (SQLException e) {
                                }
                            }
                        }
                    };
                }
            };
            return service;
        }

        public Service<ObservableList<lab_urine>> urine_test_combo_search(String valuer) {

            Service<ObservableList<lab_urine>> service = new Service<ObservableList<lab_urine>>() {
                @Override
                protected Task<ObservableList<lab_urine>> createTask() {
                    return new Task<ObservableList<lab_urine>>() {
                        @Override
                        protected ObservableList<lab_urine> call() throws Exception {

                            sql = "select * from livewell.lab_urine where pid = ? ";
                            try {
//                                conn = conclass.livewell_localhost();
                                pst = conn.prepareStatement(sql);
                                pst.setString(1, valuer);
                                rs = pst.executeQuery();
                                while (rs.next()) {
                                    default_utl = new lab_urine();
                                    default_utl.setPid(rs.getString("pid"));
                                    default_utl.setAppearance(rs.getString("appearance"));
                                    default_utl.setColour(rs.getString("colour"));
                                    default_utl.setLeukocytes(rs.getString("leukocytes"));
                                    default_utl.setUrobilinogen(rs.getString("urobilinogen"));
                                    default_utl.setBilirubin(rs.getString("bilirubin"));
                                    default_utl.setBlood(rs.getString("blood"));
                                    default_utl.setNitrite(rs.getString("nitrite"));
                                    default_utl.setPh(rs.getString("ph"));
                                    default_utl.setSpec_gravity(rs.getString("spec_gravity"));
                                    default_utl.setProtein(rs.getString("protein"));
                                    default_utl.setGlucose(rs.getString("glucose"));
                                    default_utl.setKetones(rs.getString("ketones"));
                                    default_utl.setOn_date(rs.getString("on_date"));

                                    observe_urineTest_list.add(default_utl);
                                    updateValue(observe_urineTest_list);
                                }
                                return observe_urineTest_list;
                            } catch (SQLException e) {
                                cancel();
                                updateMessage(e.getMessage());
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

    public class microscopy {

        ObservableList<lab_microscopy> observe_microscopy = FXCollections.observableArrayList();
        lab_microscopy default_microscopy;

        public microscopy() {
        }

        public Service<Boolean> add_microscopy(lab_microscopy lm) {
            rs = null;
            pst = null;
            Service<Boolean> service = new Service<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            sql = "INSERT INTO livewell.lab_microscopy"
                                    + "(pid,plus_cells,epith_cells,rbs,yeast_cells,casts,crystals,T_Vaginalis,S_Haematobium,bacteria,others,on_date,record_time) VALUES "
                                    + "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                            try {
//                                conn = conclass.livewell_localhost();
                                pst = conn.prepareStatement(sql);
                                pst.setString(1, lm.getPid());
                                pst.setString(2, lm.getPlus_cells());
                                pst.setString(3, lm.getEpith_cells());
                                pst.setString(4, lm.getRbs());
                                pst.setString(5, lm.getYeast_cells());
                                pst.setString(6, lm.getCasts());
                                pst.setString(7, lm.getCrystals());
                                pst.setString(8, lm.getT_Vaginalis());
                                pst.setString(9, lm.getS_Haematobium());
                                pst.setString(10, lm.getBacteria());
                                pst.setString(11, lm.getOthers());
                                pst.setString(12, lm.getOn_date());
                                pst.setTimestamp(13, lm.getRecord_time());
                                pst.execute();
                                updateMessage("Microscopy results taken successfully");
                                return true;
                            } catch (SQLException e) {
                                if (e.getErrorCode() == 1062) {
                                    System.out.println(e.getErrorCode() + ":" + "Microscopy results already exist for this patient");
                                    updateMessage(e.getErrorCode() + ":" + e.getMessage());
                                    cancel();
                                } else {
                                    System.out.println(e.getErrorCode() + ":" + e.getMessage());
                                    System.out.println(e.getErrorCode() + ":" + e);
                                    updateMessage(e.getErrorCode() + ":" + e.getMessage());
                                    cancel();
                                }
                                return false;
                            }

                        }
                    };
                }
            };
            return service;
        }

        public Service<Boolean> update_microscopy(lab_microscopy lm) {
            rs = null;
            pst = null;
            Service<Boolean> service = new Service<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            /* */

                            sql = "update livewell.lab_microscopy set "
                                    + ""
                                    + "plus_cells = '" + lm.getPlus_cells() + "' , "
                                    + "epith_cells = '" + lm.getEpith_cells() + "' , "
                                    + "rbs = '" + lm.getRbs() + "' ,"
                                    + "yeast_cells = '" + lm.getRbs() + "',"
                                    + "casts = '" + lm.getRbs() + "', "
                                    + "crystals = '" + lm.getRbs() + "', "
                                    + "T_Vaginalis = '" + lm.getRbs() + "', "
                                    + "S_Haematobium = '" + lm.getRbs() + "', "
                                    + "bacteria = '" + lm.getRbs() + "', "
                                    + "others = '" + lm.getRbs() + "' where pid = ? and on_date = ?";

                            try {
//                                conn = conclass.livewell_localhost();
                                pst = conn.prepareStatement(sql);
                                pst.setString(1, lm.getPid());
                                pst.setString(2, lm.getOn_date());
                                pst.execute();
                                updateMessage("Microscopy results updated");
                                return true;
                            } catch (SQLException e) {
                                if (e.getErrorCode() == 1062) {
                                    System.out.println(e.getErrorCode() + ":" + "Microscopy results already exist for this patient");
                                    updateMessage(e.getErrorCode() + ":" + e.getMessage());
                                    cancel();
                                } else {
                                    System.out.println(e.getErrorCode() + " : " + e.getMessage());
                                    updateMessage(e.getErrorCode() + " : " + "An error occured");
                                    cancel();
                                }
                                return false;
                            }

                        }
                    };
                }
            };
            return service;
        }

        public Service<ObservableList<lab_microscopy>> microscopy_load(String valuer) {
            rs = null;
            pst = null;
            Service<ObservableList<lab_microscopy>> service = new Service<ObservableList<lab_microscopy>>() {
                @Override
                protected Task<ObservableList<lab_microscopy>> createTask() {
                    return new Task<ObservableList<lab_microscopy>>() {
                        @Override
                        protected ObservableList<lab_microscopy> call() throws Exception {
                            if (valuer.contentEquals("*")) {
                                sql = "select * from livewell.lab_microscopy order by record_time";
                            } else {
                                sql = "select * from livewell.lab_microscopy where pid = '" + valuer + "' or on_date = '" + valuer + "' order by record_time asc";
                            }
                            try {
//                                conn = conclass.livewell_localhost();
                                pst = conn.prepareStatement(sql);
                                rs = pst.executeQuery();
                                while (rs.next()) {
                                    default_microscopy = new lab_microscopy();
                                    default_microscopy.setPid(rs.getString("lab_microscopy.pid"));
                                    default_microscopy.setPlus_cells(rs.getString("lab_microscopy.plus_cells"));
                                    default_microscopy.setEpith_cells(rs.getString("lab_microscopy.epith_cells"));
                                    default_microscopy.setRbs(rs.getString("lab_microscopy.rbs"));
                                    default_microscopy.setYeast_cells(rs.getString("lab_microscopy.yeast_cells"));
                                    default_microscopy.setCasts(rs.getString("lab_microscopy.casts"));
                                    default_microscopy.setCrystals(rs.getString("lab_microscopy.crystals"));
                                    default_microscopy.setT_Vaginalis(rs.getString("lab_microscopy.T_Vaginalis"));
                                    default_microscopy.setS_Haematobium(rs.getString("lab_microscopy.S_Haematobium"));
                                    default_microscopy.setBacteria(rs.getString("lab_microscopy.bacteria"));
                                    default_microscopy.setOthers(rs.getString("lab_microscopy.others"));
                                    default_microscopy.setOn_date(rs.getString("lab_microscopy.on_date"));
                                    observe_microscopy.add(default_microscopy);
                                    updateValue(observe_microscopy);
                                }
                                return observe_microscopy;
                            } catch (SQLException e) {
                                cancel();
                                updateMessage(e.getMessage());
                                System.out.println("mic: " + e);
                                return null;
                            } finally {
                                try {
                                    rs.close();
                                    pst.close();
                                } catch (SQLException e) {
                                }
                            }
                        }
                    };
                }
            };
            return service;
        }

    }

    public class stool {

        public stool() {
        }

        public Service<Boolean> add_stool(lab_stools ls) {
            rs = null;
            pst = null;
            Service<Boolean> service = new Service<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            try {
                                sql = "insert into livewell.lab_stools (pid,macroscopy,microscopy,ocult_blood,on_date,record_time)"
                                        + "values(?,?,?,?,?,?) ";
//                                conn = conclass.livewell_localhost();
                                pst = conn.prepareStatement(sql);
                                pst.setString(1, ls.getPid());
                                pst.setString(2, ls.getMacroscopy());
                                pst.setString(3, ls.getMicroscopy());
                                pst.setString(4, ls.getOcult_blood());
                                pst.setString(5, ls.getOn_date());
                                pst.setTimestamp(6, ls.getRecord_time());
                                pst.execute();
                                updateMessage("Stools results taken");
                                return true;
                            } catch (SQLException e) {
                                if (e.getErrorCode() == 1062) {
                                    updateMessage("STOOLS TEST RESULTS IS ALREADY AVAILABLE\nKINDLY UPDATE");
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

        public Service<Boolean> update_stool(lab_stools ls) {
            rs = null;
            pst = null;
            //To change body of generated methods, choose Tools | Templates.
            Service<Boolean> service = new Service<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            try {
//                                conn = conclass.livewell_localhost();
                                sql = "update  livewell.lab_stools set"
                                        + " macroscopy = '" + ls.getMacroscopy() + "', "
                                        + " microscopy = '" + ls.getMicroscopy() + "',"
                                        + " ocult_blood = '" + ls.getOcult_blood() + "' where "
                                        + "on_date =  '" + ls.getOn_date() + "' and "
                                        + "pid = '" + ls.getPid() + "'";

                                pst = conn.prepareStatement(sql);
                                pst.execute();
                                updateMessage("Stools results updated");
                                return true;
                            } catch (SQLException e) {
                                switch (e.getErrorCode()) {
                                    case 1062:
                                        updateMessage("STOOLS TEST RESULTS IS ALREADY AVAILABLE\nKINDLY UPDATE");
                                        break;
                                    case 0:
                                        updateMessage("Parametre errors: " + e.getErrorCode());
                                        System.out.println(e);
                                        break;
                                    default:
                                        updateMessage("An occured with code: " + e.getErrorCode());
                                        System.out.println(e);
                                        break;
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

        ObservableList<lab_stools> observe_stools = FXCollections.observableArrayList();
        lab_stools default_stools;

        public Service<ObservableList<lab_stools>> stools_load(String valuer) {
            rs = null;
            pst = null;
            Service<ObservableList<lab_stools>> service = new Service<ObservableList<lab_stools>>() {
                @Override
                protected Task<ObservableList<lab_stools>> createTask() {
                    return new Task<ObservableList<lab_stools>>() {
                        @Override
                        protected ObservableList<lab_stools> call() throws Exception {
                            try {
                                if (valuer.contentEquals("*")) {
                                    sql = "select * from livewell.lab_stools order by record_time";
                                } else {
                                    sql = "select * from livewell.lab_stools where pid = '" + valuer + "' or on_date = '" + valuer + "' order by record_time asc";
                                }
//                                conn = conclass.livewell_localhost();
                                pst = conn.prepareStatement(sql);
                                rs = pst.executeQuery();
                                while (rs.next()) {
                                    default_stools = new lab_stools();
                                    default_stools.setPid(rs.getString("lab_stools.pid"));
                                    default_stools.setMacroscopy(rs.getString("lab_stools.macroscopy"));
                                    default_stools.setMicroscopy(rs.getString("lab_stools.microscopy"));
                                    default_stools.setOcult_blood(rs.getString("lab_stools.ocult_blood"));
                                    default_stools.setOn_date(rs.getString("lab_stools.on_date"));
                                    default_stools.setRecord_time(rs.getTimestamp("lab_stools.record_time"));
                                    observe_stools.add(default_stools);
                                    updateValue(observe_stools);
                                }
                            } catch (SQLException e) {
                                System.out.println(e);
                                System.out.println("st: " + e);
                            } finally {
                                try {
                                    rs.close();
                                    pst.close();
                                } catch (SQLException e) {
                                }
                            }
                            return observe_stools;
                        }
                    };
                }
            };
            return service;
        }

        public void load_stools(TableView<lab_stools> st, String valuer) {
            rs = null;
            pst = null;
            try {
                if (valuer.contentEquals("*")) {
                    sql = "select * from livewell.lab_stools order by record_time";
                } else {
                    sql = "select * from livewell.lab_stools where pid = '" + valuer + "' or on_date = '" + valuer + "' order by record_time asc";
                }
//                                conn = conclass.livewell_localhost();
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    default_stools = new lab_stools();
                    default_stools.setPid(rs.getString("lab_stools.pid"));
                    default_stools.setMacroscopy(rs.getString("lab_stools.macroscopy"));
                    default_stools.setMicroscopy(rs.getString("lab_stools.microscopy"));
                    default_stools.setOcult_blood(rs.getString("lab_stools.ocult_blood"));
                    default_stools.setOn_date(rs.getString("lab_stools.on_date"));
                    default_stools.setRecord_time(rs.getTimestamp("lab_stools.record_time"));
                    observe_stools.add(default_stools);
                }
                st.setItems(observe_stools);
            } catch (SQLException e) {
                System.out.println(e);
                System.out.println("st: " + e);
            } finally {
                try {
                    rs.close();
                    pst.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}
