/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.connection.conclass;
import javafx.concurrent.ScheduledService;
import java.sql.Connection;
import javafx.concurrent.Task;
/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class concurrent_check_connection extends ScheduledService<Connection>{
    Connection conn = null;
    @Override
    protected Task<Connection> createTask() {
        return new Task<Connection>() {
            @Override
            protected Connection call() throws Exception {
                try {
                    updateMessage("Connecting please wait...");
                    updateProgress(10, 10);
                    conn = conclass.livewell_localhost();
                    updateValue(conn);
                    return conn;
                } catch (Exception e) {
                    return null;
                }
            }
        };
    }
    
}
