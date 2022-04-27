/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generator;

import com.connection.conclass;
import java.util.Date;
import java.util.Random;
import java.sql.*;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class generatIDs {
    
String sql = "";
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public  String adminUserId() {
        int nums = 0;
        conn  = conclass.localhost();
        try {
            sql = "select count(customId) from users";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
               nums = rs.getInt("count(customId)");
            }
             System.out.println(nums);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        Random rn = new Random();
        Date date = new Date();
        String inst = "LW";           
        if(nums <= 0){
            nums = 1;
        }
        String coder = date.getMonth()+ inst + rn.nextInt(nums) + date.getDay();
        return coder;
    }

    public static void main(String[] args) {
       
    }
    
}
