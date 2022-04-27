/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.connection.conclass;
import com.tables.account_Client_Section;
import com.tables.account_client_section_stmts;
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
public class Acc_Client_Section_Services {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql, cid = "";

    public Acc_Client_Section_Services() {
    }

    public Service<Boolean> add_service(account_Client_Section acs) {
        conn = conclass.livewell_localhost();
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        try {
                            sql = "insert into account_clientsection "
                                    + "(client_type,cid,client_name,client_contact,client_address,client_desc) "
                                    + " values "
                                    + "(?,?,?,?,?,?)";
                            pst = conn.prepareStatement(sql);
                            pst.setString(1, acs.getClient_type());
                            pst.setString(2, acs.getCid());
                            pst.setString(3, acs.getClient_name());
                            pst.setString(4, acs.getClient_contact());
                            pst.setString(5, acs.getClient_address());
                            pst.setString(6, acs.getClient_desc());
                            System.out.println("ready to execute");
                            pst.execute();
                            System.out.println("Executeds");
                            updateMessage("CLIENT ADDED");
                            Thread.sleep(500);
                            return true;
                        } catch (SQLException e) {
                            if (e.getErrorCode() == 1062) {
                                updateMessage("This record already exist");
                                 Thread.sleep(1500);
                            } else {
                                updateMessage("An occured with code: " + e.getErrorCode());
                                System.out.println(e);
                                updateMessage("A fatal error occured,\nError code " + e.getErrorCode());
                                Thread.sleep(1000);
                                cancel();
                            }

                            return null;
                        }

                    }
                };
            }
        ;
        };
        return service;
    }

    ObservableList<String> observeACS = FXCollections.observableArrayList();
    account_Client_Section acss = new account_Client_Section();

    public Service<ObservableList<String>> load_all_clients() {
        Service<ObservableList<String>> service = new Service<ObservableList<String>>() {
            @Override
            protected Task<ObservableList<String>> createTask() {
                return new Task<ObservableList<String>>() {
                    @Override
                    protected ObservableList<String> call() throws Exception {
                        try {
                            conn = conclass.livewell_localhost();
                            sql = "select * from account_clientsection";
                            pst = conn.prepareStatement(sql);
                            rs = pst.executeQuery();
                            String cid, client_name ="";
                            while (rs.next()) {
                                cid = rs.getString("cid");                                
                                client_name = rs.getString("client_name");                            

                                observeACS.add(rs.getString("cid"));
                                updateValue(observeACS);
                            }
                        } catch (SQLException e) {
                            System.out.println(e);
                        }
                        return observeACS;
                    }
                };
            }
        };
        return service;
    }

    public Service<Boolean> update_service(account_Client_Section acs) {
        conn = conclass.livewell_localhost();
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        try {
                            sql = "update livewell.account_clientsection set "
                                    + "client_type = '" + acs.getClient_type() + "' , "                                  
                                    + "client_name = '" + acs.getClient_name()+ "' , "
                                    + "client_contact = '" + acs.getClient_contact() + "' , "
                                    + "client_address = '" + acs.getClient_address() + "' , "
                                    + "client_desc = '" + acs.getClient_desc() + "' "
                                    + "where cid = '" + acs.getCid() + "' ";                        

                            pst = conn.prepareStatement(sql);
                            pst.execute();
                            updateMessage("CLIENT DETAILS CHANGED");
                            Thread.sleep(1000);
                            return true;
                        } catch (SQLException e) {                           
                                updateMessage("An occured with code: " + e.getErrorCode());
                                System.out.println(e);
                                updateMessage("A fatal error occured,\nError code " + e.getErrorCode());
                                Thread.sleep(1000);
                                cancel();      
                                return null;
                        }

                    }
                };
            }
        ;
        };
        return service;
    }

    public class client_stmts {

        public Service<Boolean> add_stmt(account_client_section_stmts accStmt) {
            Service<Boolean> add_service = new Service<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            try {
                                sql = "insert into account_clientsection_stmts"
                                        + "(accountant_id,stmt_cid, stmt_item, stmt_item_type, "
                                        + "stmt_qty, stmt_cost, stmt_amt_paid, stmt_balance,on_date,record_time)"
                                        + "values(?,?,?,?,?,?,?,?,?,?)";
                                conn = conclass.livewell_localhost();
                                pst = conn.prepareStatement(sql);
                                pst.setString(1, accStmt.getAccountant_id());
                                pst.setString(2, accStmt.getStmt_cid());
                                pst.setString(3, accStmt.getStmt_item());
                                pst.setString(4, accStmt.getStmt_item_type());
                                pst.setString(5, accStmt.getStmt_qty());
                                pst.setString(6, accStmt.getStmt_cost());
                                pst.setString(7, accStmt.getStmt_amt_paid());
                                pst.setString(8, accStmt.getStmt_balance().getText());
                                pst.setString(9, accStmt.getOn_date());
                                pst.setTimestamp(10, accStmt.getRec_date());
                                pst.execute();
                                updateMessage(accStmt.getStmt_item() + " has been received from client " + accStmt.getStmt_cid());
                                Thread.sleep(1000);
                                sql = "update account_clientsection_stmts set stmt_balance = (stmt_cost - stmt_amt_paid) "
                                        + "where stmt_cid = '" + accStmt.getStmt_cid() + "' "
                                        + "and "
                                        + "on_date = '" + accStmt.getOn_date() + "'";

                                pst = conn.prepareStatement(sql);
                                if (pst.execute()) {
                                    updateMessage("Altenate services initialized and completed");
                                    Thread.sleep(500);
                                } else {
                                    updateMessage("Altenate services couldn't initialize or complete");
                                    Thread.sleep(500);
                                }
                                return true;
                            } catch (SQLException e) {
                                if (e.getErrorCode() == 1062) {
                                    updateMessage("This record already exist");
                                } else {
                                    updateMessage("An occured with code: " + e.getErrorCode());
                                    System.out.println(e);
                                    updateMessage("A fatal error occured,\nError code " + e.getErrorCode());
                                    Thread.sleep(1000);
                                    cancel();
                                }
                            }
                            return null;
                        }
                    };
                }
            };
            return add_service;
        }

        public Service<Boolean> update_stmt(account_client_section_stmts accStmt) {
            Service<Boolean> add_service = new Service<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            try {
                                sql = "update account_clientsection_stmts set "
                                        + "stmt_item = '" + accStmt.getStmt_item() + "', "
                                        + "stmt_item_type = '" + accStmt.getStmt_item_type() + "', "
                                        + "stmt_amt_paid = '" + accStmt.getStmt_amt_paid() + "' "
                                        + "where "
                                        + "stmt_cid = '" + accStmt.getStmt_cid() + "' "
                                        + "and "
                                        + "on_date = '" + accStmt.getOn_date() + "' "
                                        + "and "
                                        + "stmt_item = '" + accStmt.getStmt_item() + "' ";
                                conn = conclass.livewell_localhost();
                                pst = conn.prepareStatement(sql);
                                pst.execute();
                               
                                updateMessage("Update complete");
                                Thread.sleep(1000);
                                sql = "update account_clientsection_stmts set stmt_balance = (stmt_cost - stmt_amt_paid) "
                                        + "where stmt_cid = '" + accStmt.getStmt_cid() + "' "
                                        + "and "
                                        + "on_date = '" + accStmt.getOn_date() + "' "
                                        + "and "
                                        + "stmt_item = '" + accStmt.getStmt_item() + "' ";
                                pst = conn.prepareStatement(sql);
                                pst.execute();
                               
                                updateMessage("Altenate services initialized and completed");
                                Thread.sleep(500);
                                return true;
                            } catch (SQLException e) {
                                System.out.println(e);
                                return null;
                            }
                        }
                    };
                }
            };
            return add_service;
        }

        ObservableList<account_client_section_stmts> observe_acss = FXCollections.observableArrayList();
        account_client_section_stmts default_acss = new account_client_section_stmts();

        public Service<ObservableList<account_client_section_stmts>> load_acss(String query) {
            Service<ObservableList<account_client_section_stmts>> service = new Service<ObservableList<account_client_section_stmts>>() {
                @Override
                protected Task<ObservableList<account_client_section_stmts>> createTask() {
                    return new Task<ObservableList<account_client_section_stmts>>() {
                        @Override
                        protected ObservableList<account_client_section_stmts> call() throws Exception {
                            try {
                                if (query.contentEquals("*")) {
                                    sql = "select * from account_clientsection_stmts order by record_time";
                                } else {
                                    sql = "select * from account_clientsection_stmts where "
                                            + "stmt_item = '" + query + "' or stmt_item_type = '" + query + "' "
                                            + "or accountant_id = '" + query + "' "
                                            + "or stmt_cid = '" + query + "' or on_date = '" + query + "' "
                                            + "order by record_time ";
                                }

                                pst = conn.prepareStatement(sql);
                                rs = pst.executeQuery();
                                while (rs.next()) {
                                    default_acss = new account_client_section_stmts();
                                    default_acss.setAccountant_id(rs.getString("accountant_id"));
                                    default_acss.setStmt_cid(rs.getString("stmt_cid"));
                                    default_acss.setStmt_item(rs.getString("stmt_item"));
                                    default_acss.setStmt_item_type(rs.getString("stmt_item_type"));
                                    default_acss.setStmt_qty(rs.getString("stmt_qty"));
                                    default_acss.setStmt_cost(rs.getString("stmt_cost"));
                                    default_acss.setOn_date(rs.getString("on_date"));
                                    default_acss.setRec_date(rs.getTimestamp("record_time"));
                                    default_acss.setStmt_amt_paid(rs.getString("stmt_amt_paid"));
                                    Label lbl_balance = new Label();
                                    lbl_balance.setPrefWidth(100);
                                    Double value = Double.parseDouble(rs.getString("stmt_balance"));
                                    if (value < 0.0) {
                                        lbl_balance.setStyle("-fx-background-color:green");
                                        lbl_balance.setText(String.valueOf(value));
                                    } else if (value == 0.0) {
                                        lbl_balance.setStyle("-fx-background-color:lightblue");
                                        lbl_balance.setText(String.valueOf(value));
                                    } else {
                                        lbl_balance.setStyle("-fx-background-color:tomato");
                                        lbl_balance.setText(String.valueOf(value));
                                    }
                                    default_acss.setStmt_balance(lbl_balance);
                                    observe_acss.add(default_acss);
                                    updateValue(observe_acss);
                                }
                            } catch (SQLException e) {
                                System.out.println(e);
                            }
                            return observe_acss;//To change body of generated methods, choose Tools | Templates.
                        }
                    };
                }
            };
            return service;
        }
        ObservableList<account_client_section_stmts> observe_acss_r = FXCollections.observableArrayList();
        account_client_section_stmts default_acss_r = new account_client_section_stmts();

        public Service<ObservableList<account_client_section_stmts>> load_acss(String query1, String query2) {//for client section 
            Service<ObservableList<account_client_section_stmts>> service = new Service<ObservableList<account_client_section_stmts>>() {
                @Override
                protected Task<ObservableList<account_client_section_stmts>> createTask() {
                    return new Task<ObservableList<account_client_section_stmts>>() {
                        @Override
                        protected ObservableList<account_client_section_stmts> call() throws Exception {
                            try {
                                switch (query1) {
                                    case "*":
                                        System.out.println(query1);
                                        sql = "select *,sum(stmt_balance) as tBalance from livewell.account_clientsection_stmts group by stmt_cid";
                                        break;
                                    case "<":
                                        System.out.println(query1);
                                        sql = "select *,sum(stmt_balance) as tBalance from livewell.account_clientsection_stmts where stmt_balance < 0.0 group by stmt_cid";
                                        break;
                                    case ">":
                                        System.out.println(query1);
                                        sql = "select *,sum(stmt_balance) as tBalance from livewell.account_clientsection_stmts where stmt_balance > 0.0 group by stmt_cid";
                                        break;
                                    case "=":
                                        System.out.println(query1);
                                        sql = "select *,sum(stmt_balance) as tBalance from livewell.account_clientsection_stmts where stmt_balance = 0.0 group by stmt_cid";
                                        break;
                                    default:
                                        System.out.println(query1);
                                        sql = "select *,sum(stmt_balance) as tBalance from livewell.account_clientsection_stmts where on_date between '" + query1 + "' and '" + query2
                                                + "' group by stmt_cid";
                                        break;
                                }

                                conn = conclass.livewell_localhost();
                                pst = conn.prepareStatement(sql);
                                rs = pst.executeQuery();
                                while (rs.next()) {
                                    System.out.println(rs.getString("stmt_cid"));
                                    default_acss_r = new account_client_section_stmts();
                                    default_acss_r.setAccountant_id(rs.getString("accountant_id"));
                                    default_acss_r.setStmt_cid(rs.getString("stmt_cid"));
                                    default_acss_r.setStmt_item(rs.getString("stmt_item"));
                                    default_acss_r.setStmt_item_type(rs.getString("stmt_item_type"));
                                    default_acss_r.setStmt_qty(rs.getString("stmt_qty"));
                                    default_acss_r.setStmt_cost(rs.getString("stmt_cost"));
                                    default_acss_r.setOn_date(rs.getString("on_date"));
                                    default_acss_r.setRec_date(rs.getTimestamp("record_time"));
                                    default_acss_r.setStmt_amt_paid(rs.getString("stmt_amt_paid"));
                                    Label lbl_balance = new Label();
                                    lbl_balance.setPrefWidth(100);
                                    Double value = Double.parseDouble(rs.getString("tBalance"));
                                    if (value < 0.0) {
                                        lbl_balance.setStyle("-fx-background-color:green");
                                        lbl_balance.setText(String.valueOf(value));
                                    } else if (value == 0.0) {
                                        lbl_balance.setStyle("-fx-background-color:lightblue");
                                        lbl_balance.setText(String.valueOf(value));
                                    } else {
                                        lbl_balance.setStyle("-fx-background-color:tomato");
                                        lbl_balance.setText(String.valueOf(value));
                                    }
                                    default_acss_r.setStmt_balance(lbl_balance);
                                    observe_acss_r.add(default_acss_r);
                                    updateValue(observe_acss_r);
                                }
                            } catch (SQLException e) {
                                System.out.println(e);
                            }
                            return observe_acss_r;//To change body of generated methods, choose Tools | Templates.
                        }
                    };
                }
            };
            return service;
        }

        public Service<ObservableList<account_client_section_stmts>> load_acss_byID(String pid) {//for client section 
            Service<ObservableList<account_client_section_stmts>> service = new Service<ObservableList<account_client_section_stmts>>() {
                @Override
                protected Task<ObservableList<account_client_section_stmts>> createTask() {
                    return new Task<ObservableList<account_client_section_stmts>>() {
                        @Override
                        protected ObservableList<account_client_section_stmts> call() throws Exception {
                            try {
                                sql = "select *,sum(stmt_balance) as tBalance from livewell.account_clientsection_stmts where stmt_cid = '" + pid + "' order by record_time";

                                conn = conclass.livewell_localhost();
                                pst = conn.prepareStatement(sql);
                                rs = pst.executeQuery();
                                while (rs.next()) {
                                    System.out.println(rs.getString("stmt_cid"));
                                    default_acss_r = new account_client_section_stmts();
                                    default_acss_r.setAccountant_id(rs.getString("accountant_id"));
                                    default_acss_r.setStmt_cid(rs.getString("stmt_cid"));
                                    default_acss_r.setStmt_item(rs.getString("stmt_item"));
                                    default_acss_r.setStmt_item_type(rs.getString("stmt_item_type"));
                                    default_acss_r.setStmt_qty(rs.getString("stmt_qty"));
                                    default_acss_r.setStmt_cost(rs.getString("stmt_cost"));
                                    default_acss_r.setOn_date(rs.getString("on_date"));
                                    default_acss_r.setRec_date(rs.getTimestamp("record_time"));
                                    default_acss_r.setStmt_amt_paid(rs.getString("stmt_amt_paid"));
                                    Label lbl_balance = new Label();
                                    lbl_balance.setPrefWidth(100);
                                    Double value = Double.parseDouble(rs.getString("tBalance"));
                                    if (value < 0.0) {
                                        lbl_balance.setStyle("-fx-background-color:green");
                                        lbl_balance.setText(String.valueOf(value));
                                    } else if (value == 0.0) {
                                        lbl_balance.setStyle("-fx-background-color:lightblue");
                                        lbl_balance.setText(String.valueOf(value));
                                    } else {
                                        lbl_balance.setStyle("-fx-background-color:tomato");
                                        lbl_balance.setText(String.valueOf(value));
                                    }
                                    default_acss_r.setStmt_balance(lbl_balance);
                                    observe_acss_r.add(default_acss_r);
                                    updateValue(observe_acss_r);
                                }
                            } catch (SQLException e) {
                                System.out.println(e);
                            }
                            return observe_acss_r;//To change body of generated methods, choose Tools | Templates.
                        }
                    };
                }
            };
            return service;
        }

    }
}
