/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import static com.Accountancy.Accountancy_dashController.account_id;
import com.connection.conclass;
import com.semantics.semanticsClass;
import com.tables.account_Patient_Section;
import com.tables.pharm_drug_costing;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class Acc_Patient_Section_Services {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql, pid = "";

    public Acc_Patient_Section_Services() {
        conn = conclass.livewell_localhost();
    }

    public Service<Boolean> addToHistory(account_Patient_Section aps) {
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        try {
                            sql = "insert into account_patientsection (pid,on_date,particulas,cost,amt_paid,record_time,account_id) values(?,?,?,?,?,?,?)";
                            pst = conn.prepareStatement(sql);
                            pst.setString(1, aps.getPid());
                            pst.setString(2, aps.getDate());
                            pst.setString(3, aps.getParticulas());
                            pst.setString(4, aps.getCost());
                            pst.setString(5, aps.getAmt_paid());
                            pst.setTimestamp(6, aps.getRecord_time());
                            pst.setString(7, account_id);
                            pst.execute();
                            updateMessage("Added to vault");
                            sql = "update account_patientsection set balance = (cost - amt_paid) "
                                    + "where on_date = '" + aps.getDate() + "' and pid = '" + aps.getPid() + "'";
                            pst = conn.prepareStatement(sql);
                            pst.execute();
                            updateMessage("Other vault services completed");
                            Thread.sleep(500);
                            return true;
                        } catch (SQLException e) {
                            if (e.getErrorCode() == 1062) {
                                updateMessage("This record already exist");
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

    public Service<Boolean> UpdateHistory(account_Patient_Section aps) {
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        try {
                            System.out.println("\nbalance: " + aps.getLbl_balance().getText());
                            sql = "update account_patientsection "
                                    + " set balance = '" + aps.getLbl_balance().getText() + "' "
                                    + "where on_date = '" + aps.getDate() + "' and pid = '" + aps.getPid() + "' ";
                            pst = conn.prepareStatement(sql);
                            pst.execute();
                            updateMessage("vault update successful");
                            Thread.sleep(500);
                            sql = "update account_patientsection set amt_paid = (cost - balance) "
                                    + "where on_date = '" + aps.getDate() + "' and pid = '" + aps.getPid() + "'";
                            pst = conn.prepareStatement(sql);
                            pst.execute();
                            updateMessage("Other vault services completed");
                            Thread.sleep(500);
                            return true;
                        } catch (SQLException e) {
                            if (e.getErrorCode() == 1062) {
                                updateMessage("This record already exist");
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

    ObservableList<account_Patient_Section> observe_aps = FXCollections.observableArrayList();
    account_Patient_Section default_aps = new account_Patient_Section();

    public Service<ObservableList<account_Patient_Section>> load_patientSection_history(String valuer) {
        Service<ObservableList<account_Patient_Section>> service = new Service<ObservableList<account_Patient_Section>>() {
            @Override
            protected Task<ObservableList<account_Patient_Section>> createTask() {
                return new Task<ObservableList<account_Patient_Section>>() {
                    @Override
                    protected ObservableList<account_Patient_Section> call() throws Exception {
                        try {
                            if (valuer.contentEquals("*")) {
                                sql = "select * from livewell.account_patientsection order by record_time";
                            } else {
                                sql = "select * from livewell.account_patientsection where on_date = '" + valuer + "' or pid = '" + valuer + "' order by record_time";
                            }
                            //pid,on_date,particulas,cost,amt_paid,balance,record_time
                            pst = conn.prepareStatement(sql);
                            rs = pst.executeQuery();
                            while (rs.next()) {
                                default_aps = new account_Patient_Section();
                                default_aps.setPid(rs.getString("pid"));
                                default_aps.setDate(rs.getString("on_date"));
                                default_aps.setParticulas(rs.getString("particulas"));
                                default_aps.setCost(rs.getString("cost"));
                                default_aps.setAmt_paid(rs.getString("amt_paid"));
                                double balance = Double.parseDouble(rs.getString("balance"));
                                Label lbl_balance = new Label();
                                lbl_balance.setPrefWidth(100);
                                if (balance > 0.0) {
                                    lbl_balance.setText(String.valueOf(balance));
                                    lbl_balance.setStyle("-fx-background-color:lightgreen;");
                                } else if (balance == 0.0) {
                                    lbl_balance.setText(String.valueOf(balance));
                                    lbl_balance.setStyle("-fx-background-color:lightblue;");
                                } else if (balance < 0.0) {
                                    lbl_balance.setText(String.valueOf(balance));
                                    lbl_balance.setStyle("-fx-background-color:tomato;");
                                }
                                default_aps.setLbl_balance(lbl_balance);
                                observe_aps.add(default_aps);
                                updateValue(observe_aps);
                            }
                            return observe_aps;
                        } catch (Exception e) {
                            return null;
                        }

                    }
                };
            }
        };
        return service;
    }
    ObservableList<pharm_drug_costing> observe_pdci = FXCollections.observableArrayList();
    pharm_drug_costing pdci = new pharm_drug_costing();

    public Service<ObservableList<pharm_drug_costing>>
            load_selectedHistory_listOfItems(String pid, String on_date, String pay_id) {
        Service<ObservableList<pharm_drug_costing>> service = new Service<ObservableList<pharm_drug_costing>>() {
            @Override
            protected Task<ObservableList<pharm_drug_costing>> createTask() {
                return new Task<ObservableList<pharm_drug_costing>>() {
                    @Override
                    protected ObservableList<pharm_drug_costing> call() throws Exception {
                        try {
//                            sql = "select pharmacy_payment.*,account_patientsection.*"
//                                    + " from pharmacy_payment,account_patientsection"
//                                    + " where pharmacy_payment.pay_id = '" + pay_id + "' and pharmacy_payment.on_date = '" + on_date + "' "
//                                    + " and ";

                            sql = "select * from pharmacy_payment inner join account_patientsection "
                                    + "on pharmacy_payment.pay_id = account_patientsection.particulas "
                                    + "where pharmacy_payment.pay_id = '" + pay_id + "' ";
                            //pid,on_date,particulas,cost,amt_paid,balance,record_time
                            pst = conn.prepareStatement(sql);
                            rs = pst.executeQuery();
                            while (rs.next()) {
                                pdci = new pharm_drug_costing();
                                pdci.setPay_id(rs.getString("pharmacy_payment.pay_id"));//set payment id
                                pdci.setPharmacist_id(rs.getString("pharmacy_payment.pharmacist"));//set pharmacist id
                                pdci.setPat_id(rs.getString("pharmacy_payment.pid"));//set pharmacist id
                                pdci.setDrug_code(rs.getString("pharmacy_payment.drug_code"));//set 
                                pdci.setCosting_total(rs.getString("pharmacy_payment.line_total"));//set 
//                                pdci.setQuantity(rs.getString("pharmacy_payment.drug_quantity"));
                                pdci.setDrug_price(String.valueOf(rs.getString("pharmacy_payment.drug_price")));
//                                pdci.setCosting_total(String.valueOf(rs.getString("account_patientsection.balance")));
                                pdci.setOn_date(rs.getString("pharmacy_payment.on_date"));
                                observe_pdci.add(pdci);
                            }
                            updateValue(observe_pdci);
                            return observe_pdci;
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

    public class miniPSservices {

        public miniPSservices() {
        }

        ObservableList<account_Patient_Section> observe_aps = FXCollections.observableArrayList();
        account_Patient_Section default_aps = new account_Patient_Section();

        public Service<ObservableList<account_Patient_Section>> load_patientSection_history(String cbo1_value) {
            Service<ObservableList<account_Patient_Section>> service = new Service<ObservableList<account_Patient_Section>>() {
                @Override
                protected Task<ObservableList<account_Patient_Section>> createTask() {
                    return new Task<ObservableList<account_Patient_Section>>() {
                        @Override
                        protected ObservableList<account_Patient_Section> call() throws Exception {
                            try {
                                if (cbo1_value.contentEquals("*")) {
                                    sql = "select * from livewell.account_patientsection order by record_time";
                                } else {
                                    sql = "select * from livewell.account_patientsection where on_date = '" + cbo1_value + "' or pid = '" + cbo1_value + "' order by record_time";
                                }
                                //pid,on_date,particulas,cost,amt_paid,balance,record_time
                                pst = conn.prepareStatement(sql);
                                rs = pst.executeQuery();
                                while (rs.next()) {
                                    default_aps = new account_Patient_Section();
                                    default_aps.setPid(rs.getString("pid"));
                                    default_aps.setDate(rs.getString("on_date"));
                                    default_aps.setParticulas(rs.getString("particulas"));
                                    default_aps.setCost(rs.getString("cost"));
                                    default_aps.setAmt_paid(rs.getString("amt_paid"));
                                    double balance = Double.parseDouble(rs.getString("balance"));
                                    Label lbl_balance = new Label();
                                    lbl_balance.setPrefWidth(100);
                                    if (balance < 0.0) {
                                        lbl_balance.setText(String.valueOf(balance));
                                        lbl_balance.setStyle("-fx-background-color:tomato;");
                                    } else if (balance == 0.0) {
                                        lbl_balance.setText(String.valueOf(balance));
                                        lbl_balance.setStyle("-fx-background-color:lightblue;");
                                    } else if (balance > 0.0) {
                                        lbl_balance.setText(String.valueOf(balance));
                                        lbl_balance.setStyle("-fx-background-color:lightgreen;");
                                    }
                                    default_aps.setLbl_balance(lbl_balance);
                                    observe_aps.add(default_aps);
                                    updateValue(observe_aps);
                                }
                                return observe_aps;
                            } catch (Exception e) {
                                return null;
                            }

                        }
                    };
                }
            };
            return service;
        }

        public Service<ObservableList<account_Patient_Section>> load_myDebtorsCreditors(String cbo1_value, String cbo2_value) {
            Service<ObservableList<account_Patient_Section>> service = new Service<ObservableList<account_Patient_Section>>() {
                @Override
                protected Task<ObservableList<account_Patient_Section>> createTask() {
                    return new Task<ObservableList<account_Patient_Section>>() {
                        @Override
                        protected ObservableList<account_Patient_Section> call() throws Exception {
                            try {
                                switch (cbo1_value) {
                                    case "*":
                                        sql = "select * from livewell.account_patientsection ";
                                        break;
                                    case "<":
                                        sql = "select * from livewell.account_patientsection where balance < 0.0 order by record_time";
                                        break;
                                    case ">":
                                        sql = "select * from livewell.account_patientsection where balance > 0.0 order by record_time";
                                        break;
                                    case "=":
                                        sql = "select * from livewell.account_patientsection where balance = 0.0 order by record_time";
                                        break;
                                    default:
                                        sql = "select * from livewell.account_patientsection where on_date between '" + cbo1_value + "' and '" + cbo2_value
                                                + "'  order by record_time";
                                        break;
                                }

                                //pid,on_date,particulas,cost,amt_paid,balance,record_time
                                pst = conn.prepareStatement(sql);
                                rs = pst.executeQuery();
                                while (rs.next()) {
                                    default_aps = new account_Patient_Section();
                                    default_aps.setPid(rs.getString("pid"));
                                    default_aps.setDate(rs.getString("on_date"));
                                    default_aps.setParticulas(rs.getString("particulas"));
                                    default_aps.setCost(rs.getString("cost"));
                                    default_aps.setAmt_paid(rs.getString("amt_paid"));
                                    double balance = Double.parseDouble(rs.getString("balance"));
                                    Label lbl_balance = new Label();
                                    lbl_balance.setPrefWidth(200);
                                    if (balance < 0.0) {
                                        lbl_balance.setText(String.valueOf(balance));
                                        lbl_balance.setStyle("-fx-background-color:tomato;");
                                    } else if (balance == 0.0) {
                                        lbl_balance.setText(String.valueOf(balance));
                                        lbl_balance.setStyle("-fx-background-color:lightblue;");
                                    } else if (balance > 0.0) {
                                        lbl_balance.setText(String.valueOf(balance));
                                        lbl_balance.setStyle("-fx-background-color:lightgreen;");
                                    }
                                    default_aps.setLbl_balance(lbl_balance);
                                    observe_aps.add(default_aps);
                                    updateValue(observe_aps);
                                }
                                return observe_aps;
                            } catch (Exception e) {
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
