/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class spec_sign extends Service<Boolean> {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = "";
    
    private String userid, password;

    public spec_sign() {
    }

    public spec_sign(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }

    @Override
    protected Task<Boolean> createTask() {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                try {
                    sql = "select user.* from ";
                } catch (Exception e) {
                    System.out.println(e);
                }
                return null;
            }
        };
    }

}
