/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class twilio_services {

    public Service<Boolean> sendSms(String to, String from, String msg) {
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        final String ACCOUNT_SID = "ACce2cfd3c7d7c662090f909aac3875211";
                        final String AUTH_TOKEN = "08a9dae2eed7056e4a3153ec56836cd7";
                        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                        PhoneNumber _to = new PhoneNumber(to);
                        PhoneNumber _from = new PhoneNumber(from);
                        Message message = Message.creator(_to, _from, msg).create();
                        updateMessage("Message successfully sent to patient " + _to);
                        return true;
                    }
                };
            }
        };
        return service;
    }
    
    public Service<Boolean> sendWhatSpp(String to, String from, String msg) {
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        final String ACCOUNT_SID = "ACce2cfd3c7d7c662090f909aac3875211";
                        final String AUTH_TOKEN = "08a9dae2eed7056e4a3153ec56836cd7";
                        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                        PhoneNumber _to = new PhoneNumber("+whatsapp:"+to);
                        PhoneNumber _from = new PhoneNumber(from);
                        Message message = Message.creator(_to, _from, msg).create();
                        updateMessage("Message successfully sent to patient " + _to);
                        return true;
                    }
                };
            }
        };
        return service;
    }
}
