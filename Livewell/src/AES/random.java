/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AES;

import com.semantics.semanticsClass;
import java.time.LocalDate;
import java.util.Date;

import java.util.Random;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class random {

    private String random_var;
    static String random_vari;

    public random() {
    }

    public random(String random_var) {
        this.random_var = random_var;
    }

    public static String random_String_Value(String id) {
        String random_Str = "";
        Random rn = new Random();
        LocalDate ld = LocalDate.now();
        random_Str = ld.getDayOfYear() + 
                String.valueOf(ld.getDayOfWeek()) + 
                rn.nextInt(ld.getDayOfYear()) + "" + ld.getDayOfYear() + "OH" + "|" + ld.lengthOfMonth() +                
                ld.getDayOfWeek().name().substring((ld.getDayOfWeek().name().length() - ld.getDayOfWeek().name().length()) + 2, ld.getDayOfWeek().name().length() - 2);
        random_Str += semanticsClass.nowStamp(new Date());
        random_Str += id;
        String l_rn = AES.encrypt(random_Str, id);
        return l_rn;
    }

    public static String pharm_payment_id(String uid) {
        Random rn = new Random();
        LocalDate ld = LocalDate.now();
        random_vari = ld.toString() + "|" + (rn.nextInt(9999) + "|" + uid);
        return random_vari;
    }

    /**
     * @return the random_var
     */
    public String getRandom_var() {
        Random rn = new Random();
        LocalDate ld = LocalDate.now();
        random_var = ld.getDayOfYear() + rn.nextInt(ld.getDayOfYear()) + "" + ld.getDayOfYear() + "OH" + "|" + ld.lengthOfMonth() + ld.getDayOfWeek().name().substring((ld.getDayOfWeek().name().length() - ld.getDayOfWeek().name().length()) + 2, ld.getDayOfWeek().name().length() - 2);

        return random_var;
    }
    
    public static void main(String args[]){
        System.out.println(random_String_Value("LHC-TEST-201").length());
        System.out.println(random_String_Value("LHC-TEST-201"));
        System.out.println(random_String_Value("3"));
    }

}
