/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class MakePhoneCall {

    // Find your Account Sid and Token at twilio.com/console
    static final String ACCOUNT_SID = "ACce2cfd3c7d7c662090f909aac3875211";
    static final String AUTH_TOKEN = "08a9dae2eed7056e4a3153ec56836cd7";

    public static void main(String[] args) throws URISyntaxException {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String from = "+12563611302";
        String to = "+233543184000";

        Call call = Call.creator(new PhoneNumber(to), new PhoneNumber(from),
                new URI("http://demo.twilio.com/docs/voice.xml")).create();

        System.out.println(call.getSid());
    }
}
