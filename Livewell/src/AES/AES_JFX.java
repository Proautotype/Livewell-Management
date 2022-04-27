/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AES;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES_JFX {

    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(JFXTextArea strToEncrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getText().trim().getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(JFXTextArea strToDecrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt.getText().trim())));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public static void main(String[] args) {
        final String Sikrit = "3425";

        String originalString = "howtodoinjava.com";
        /*String encryptedString = AES_JFX.encrypt(originalString, Sikrit);
        String decryptedString = AES_JFX.decrypt(encryptedString, Sikrit);*/

        System.out.println(originalString);
//        System.out.println(encryptedString);
//        System.out.println(decryptedString);
    }
    
    public static JFXTextArea run_Ecryption(JFXTextArea field, String secret){
         if(!field.getText().isEmpty()){
             String encryptedString = AES_JFX.encrypt(field, secret);
        field.setText(encryptedString);
         }
        return field;
    }
    
    public static JFXTextArea run_DEcryption(JFXTextArea field, String secret){
        if(!field.getText().isEmpty()){
             String decryptedString = AES_JFX.decrypt(field, secret);
          field.setText(decryptedString);
        }return field;
        
    }
}
