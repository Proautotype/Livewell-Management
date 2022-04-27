/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.connection.conclass;
import com.tables.stores_allDrugs;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
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
public class Store_Services {

    Connection conn = null;
    PreparedStatement pst = null;
    String sql = "";
    ResultSet rs = null;

    private String code;
    private String drug_name;
    private String drug_type;
    private String price;
    private String quantity;

    public Store_Services() {
    }

    public Store_Services(String code, String drug_name, String drug_type, String price, String quantity) {
        this.code = code;
        this.drug_name = drug_name;
        this.drug_type = drug_type;
        this.price = price;
        this.quantity = quantity;
    }

    public Service<Boolean> insert_drug() {
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        try {
                            
                            conn = conclass.livewell_localhost();
                            sql = "insert into presc_drugs(code,drug_name,drug_type,price,quantity) "
                                    + "values(?,?,?,?,?)";
                            pst = conn.prepareStatement(sql);
                            pst.setString(1, code.trim());
                            pst.setString(2, drug_name.trim());
                            pst.setString(3, drug_type.trim());
                            pst.setDouble(4, Double.valueOf(price));
                            pst.setInt(5, Integer.valueOf(quantity));
                           return pst.execute();                          
                        } catch (SQLException e) {
                            System.out.println(e + " " + e.getErrorCode());
                            if (e.getErrorCode() == 1062) {
                                cancel();
                                updateMessage("Oops, drug already exist, it will rather be updated");
                                Thread.sleep(400);
                            }

                        }
                        return null; //To change body of generated methods, choose Tools | Templates.
                    }
                };
            }
        };
        return service;
    }

    public Service<Boolean> update_drug() {
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        updateMessage("Updating drugs... ");
                        try {
                            sql = "update presc_drugs set "
                                    + "code = '" + code.trim() + "', "
                                    + "drug_name = '" + drug_name.trim() + "',"
                                    + "drug_type= '" + drug_type.trim() + "',"
                                    + "price = '" + Double.valueOf(price) + "',"
                                    + "quantity = '" + Integer.valueOf(quantity) + "' "
                                    + "where presc_drugs.code = '" + code.trim() + "' ";

                            conn = conclass.livewell_localhost();
                            pst = conn.prepareStatement(sql);
                            pst.execute();
                            updateMessage("Updating drugs... ");
                            Thread.sleep(500);
                        } catch (InterruptedException | NumberFormatException | SQLException e) {
                            cancel();
                            System.out.println(e);
                        }
                        return null;
                    }
                };
            }
        };
        return service;
    }

    stores_allDrugs store_drug = null;
    ObservableList<stores_allDrugs> drug_list = FXCollections.observableArrayList();

    public Service<ObservableList<stores_allDrugs>> select_all_drugs(String query) {
        Service<ObservableList<stores_allDrugs>> service = new Service<ObservableList<stores_allDrugs>>() {
            @Override
            protected Task<ObservableList<stores_allDrugs>> createTask() {
                return new Task<ObservableList<stores_allDrugs>>() {
                    @Override
                    protected ObservableList<stores_allDrugs> call() throws Exception {
                        if(query.contentEquals("*")){
                         sql = "select * from presc_drugs";
                        }else{
                         sql = "select * from presc_drugs where drug_name = '"+query+"' or price = '"+query+"' or drug_type = '"+query+"' or quantity <= '"+query+"'";
                        }
//sql = "select * from presc_drugs";
                        drug_list = FXCollections.observableArrayList();
                        try {
                           
                            conn = conclass.livewell_localhost();
                            pst = conn.prepareStatement(sql);
                            rs = pst.executeQuery();
                            while (rs.next()) {
                                store_drug = new stores_allDrugs();
                                store_drug.setCode(rs.getString("code"));
                                store_drug.setDrug_name(rs.getString("drug_name"));
                                store_drug.setDrug_type(rs.getString("drug_type"));
                                store_drug.setPrice(rs.getString("price"));
                                 Label myLabel = new Label(rs.getString("quantity"));
                                 myLabel.setPrefWidth(400);
                                int qty = Integer.parseInt(rs.getString("quantity"));
                                if(qty <= 0){
                                    myLabel.setStyle("-fx-background-color:tomato;");
                                }else{
                                    myLabel.setStyle("-fx-background-color:none;");
                                } 
                                store_drug.setQuantity(myLabel);

                                drug_list.add(store_drug);//get a single drug value
                                updateValue(drug_list); //update the value of the service
                            }
                        } catch (SQLException e) {
                            System.out.println(e);
                        }
                        return drug_list;
                    }
                };
            }

        };
        return service;
    }
}
