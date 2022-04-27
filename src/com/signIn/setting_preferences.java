package com.signIn;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.semantics.semanticsClass;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class setting_preferences {

    public static String ip, port, username, password;
    public static final String CONFIG_FILE = "config.txt";

    public setting_preferences() {
        this.ip = "192.168.1.101";
        this.port = "3306";
        this.username = "root";
        this.password = "cjcj1122";
    }

    public setting_preferences(String ip, String port, String username, String password) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void add() {
        JSONObject serverDetails = new JSONObject();
        //Write JSON file
        try {            
             serverDetails.put("ip", AES.AES.encrypt("192.168.1.110", "ProauCJ"));
            serverDetails.put("port",  AES.AES.encrypt("3306", "ProauCJ"));
            serverDetails.put("username", AES.AES.encrypt("root", "ProauCJ"));
            serverDetails.put("password",  AES.AES.encrypt("LiveRoot123!@#", "ProauCJ"));
            
            File serverDetailsJson = new File("serverDetails.json");
            if (!serverDetailsJson.exists()) {
                FileWriter file = new FileWriter(serverDetailsJson);
                file.write(serverDetails.toJSONString());
                file.flush();
            }else{
                //semanticsClass.set_notification("There", "Already Exist");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject read() {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        JSONObject serverDetailsObject = null;

        try ( FileReader reader = new FileReader("serverDetails.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            serverDetailsObject = (JSONObject) obj;
            System.out.println(serverDetailsObject);

            // Iterate over employee array
            // employeeList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );
        } catch (Exception e) {

        }
        return serverDetailsObject;
    }

    public static void WriteServerDetails(setting_preferences sp){
         JSONObject serverDetails = new JSONObject();
        //Write JSON file
        try {      
            serverDetails.put("ip", sp.getIp());
            serverDetails.put("port", sp.getPort());
            serverDetails.put("username", sp.getUsername());
            serverDetails.put("password", sp.getPassword());
                        
            File serverDetailsJson = new File("serverDetails.json");
            if (serverDetailsJson.exists()) {
                FileWriter file = new FileWriter(serverDetailsJson);
                file.write(serverDetails.toJSONString());
                file.flush();
            }else{
                semanticsClass.set_notification("There", "Already Exist");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
//   public static void load(){
//   //Get employee object within list
//        JSONObject employeeObject = (JSONObject) employee.get("employee");
//         
//        //Get employee first name
//        String firstName = (String) employeeObject.get("firstName");    
//        System.out.println(firstName);
//         
//        //Get employee last name
//        String lastName = (String) employeeObject.get("lastName");  
//        System.out.println(lastName);
//         
//        //Get employee website name
//        String website = (String) employeeObject.get("website");    
//        System.out.println(website);
//   }
}
