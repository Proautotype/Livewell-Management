/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AES;

import java.time.LocalDate;
import java.util.Random;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class payment_id_generate {

    private String random_var;

    public payment_id_generate() {
    }

    public String pharm_payment_id(String uid) {
        Random rn = new Random();
        LocalDate ld = LocalDate.now();
        random_var = ld.toString() + "|" + (rn.nextInt(9999) + "|" + uid);
        return random_var;
    }

}
