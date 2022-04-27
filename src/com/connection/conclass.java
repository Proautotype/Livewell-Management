/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.connection;

import com.signIn.setting_preferences;
import java.awt.HeadlessException;
import java.io.File;
import java.sql.*;
import javafx.concurrent.ScheduledService;
import javax.swing.JOptionPane;
import javafx.concurrent.Task;
import org.json.simple.JSONObject;

/**
 *
 * @author Wynxtyn Proautotype
 */
public class conclass {

    static Connection conn;
    static String conMsg = "";
    static Connection Str_conn;
    static boolean isConnection_avail;

    //static Random rn = new Random();
    public static Connection localhost() {
        String user = "root";
        String passww = "cjcj1122";
        String url_ = "jdbc:mysql://127.0.0.1:3306/liveWell";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (DriverManager.getConnection(url_, user, passww).isValid(1)) {
                conn = DriverManager.getConnection(url_, user, passww);
                Str_conn = conn;
                isConnection_avail = true;
                System.out.println("Connected");
            }
            return Str_conn;

        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            Str_conn = null;
            isConnection_avail = false;
            //  System.out.println("No Connection");
            return Str_conn;
        }
    }

    public static String ip, port, user,passww,url_ = "";
    
    public static Connection livewell_localhost() {
        if (!new File("serverDetails.json").exists()) {
            setting_preferences.add();       
        } else {
            JSONObject serverDetails = (JSONObject) setting_preferences.read();
            ip = (AES.AES.decrypt((String)serverDetails.get("ip"), "ProauCJ"));
            port = (AES.AES.decrypt((String)serverDetails.get("port"), "ProauCJ"));
            user = (AES.AES.decrypt((String)serverDetails.get("username"), "ProauCJ"));
            passww = (AES.AES.decrypt((String)serverDetails.get("password"), "ProauCJ"));
            
            System.out.println(serverDetails.toJSONString());                   

            url_ = "jdbc:mysql:// " + ip + " : " + port + "/livewell";//           
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url_, user, passww);
            Str_conn = conn;
            isConnection_avail = true;
            System.out.println("Connected");

            return Str_conn;

        } catch (ClassNotFoundException | SQLException e) {
            Str_conn = null;
            isConnection_avail = false;
            System.out.println(e);
            return Str_conn;
        }

    }
    //static Random rn = new Random();

    public static Connection conn_0() {
        String user = "root";
        String passww = "cjcj1122";
        String url_ = "jdbc:mysql://192.168.8.100:3306/oyoko_health_clinic_database";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (DriverManager.getConnection(url_, user, passww).isValid(1)) {
                conn = DriverManager.getConnection(url_, user, passww);
                Str_conn = conn;
                isConnection_avail = true;
                //  System.out.println("Connected");
            }
            return Str_conn;

        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            Str_conn = null;
            isConnection_avail = false;
            //  System.out.println("No Connection");
            return Str_conn;
        }
    }

    public static Connection conn_1() {
        String user = "root";
        String passww = "cjcj1122";
        String url_ = "jdbc:mysql://192.168.8.101:3306/oyoko_health_clinic_database";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (DriverManager.getConnection(url_, user, passww).isValid(1)) {
                conn = DriverManager.getConnection(url_, user, passww);
                Str_conn = conn;
            }
            return Str_conn;

        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            Str_conn = null;
            return Str_conn;
        }
    }

    public static Connection conn_2() {
        String user = "root";
        String passww = "cjcj1122";
        String url_ = "jdbc:mysql://192.168.8.102:3306/oyoko_health_clinic_database";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (DriverManager.getConnection(url_, user, passww).isValid(1)) {
                conn = DriverManager.getConnection(url_, user, passww);
                Str_conn = conn;
            }
            return Str_conn;

        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            Str_conn = null;
            return Str_conn;
        }
    }

    public static Connection conn_3() {
        String user = "root";
        String passww = "cjcj1122";
        String url_ = "jdbc:mysql://192.168.8.103:3306/oyoko_health_clinic_database";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (DriverManager.getConnection(url_, user, passww).isValid(1)) {
                conn = DriverManager.getConnection(url_, user, passww);
                Str_conn = conn;
            }
            return Str_conn;

        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            Str_conn = null;
            return Str_conn;
        }
    }

    public static ScheduledService<Connection> service() {
        return new ScheduledService() {
            @Override
            protected Task<Connection> createTask() {
                return new Task() {
                    @Override
                    protected Connection call() throws Exception {
                        conn_0();
                        Thread.sleep(1000);
                        return conn_0();
                    }
                };
            }
        };

    }

    public static Connection controlledConn(String user, String pass, String ip, String database) {
        String url_ = "jdbc:mysql://" + ip + ":3306/" + database;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (DriverManager.getConnection(url_, user, pass).isValid(1)) {
                conn = DriverManager.getConnection(url_, user, pass);
                System.out.println("Connected");
            }
            return conn;
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            Str_conn = null;
            System.out.println("no Connected");
            return Str_conn;
        }
    }

    public static void main(String args[]) {
        livewell_localhost();
    }

}
