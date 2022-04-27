/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Pharmacy;

import com.connection.conclass;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

/**
 *
 * @author G
 */
public class pharmacy_incomingLoader extends ScheduledService<String>{
  
    @Override
    protected Task<String> createTask() {
        //To change body of generated methods, choose Tools | Templates.
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                
                return null;               
            }
        };
    }
   
}
