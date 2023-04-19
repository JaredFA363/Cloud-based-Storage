/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.systemsoftcw;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.sql.*;
import java.util.Base64;
/**
 *method for encryption and decryption using AES ALGO
 * @author n1004932
 */
public class Encryption {
    private static final int KEY_SIZE = 256;
    private static final int SALT_SIZE = 10;
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String DB_URL = "jdbc:sqlite:AccountDB.db";
  
    public static void encrypt(Path inputFilePath, Path outputFilePath, String username) throws Exception{
        String password = getEncryptedPasswordFromDatabase(username);
        byte[] salt = generateSalt();
        SecretKey key = deriveKey(decryptPassword(password), salt);
        Cipher cipher = Cipher.getInstance(ALGORITHM); //Initialize the cipher object in encryption mode with the derived key and a randomly generated IV iteration
        cipher.init(Cipher.ENCRYPT_MODE, key);
        
        byte[] inputBytes = Files.readAllBytes(inputFilePath);
        byte[] encryptedBytes = cipher.doFinal(inputBytes);  // Encrypts input bytes by using the initialized cipher
        
        byte [] iv = cipher.getIV();
        byte [] saltAndEncryptedBytes = new byte [SALT_SIZE + iv.length + encryptedBytes.length];
        System.arraycopy(salt, 0, saltAndEncryptedBytes, 0, SALT_SIZE);
        System.arraycopy(iv, 0, saltAndEncryptedBytes, SALT_SIZE, iv.length);
        System.arraycopy(encryptedBytes, 0, saltAndEncryptedBytes, SALT_SIZE + iv.length, encryptedBytes.length);
        
        Files.write(outputFilePath, saltAndEncryptedBytes);
    }
    
    public static void decript(Path inputFilePath, Path outputFilePath, String username) throws Exception{
        String password = getEncryptedPasswordFromDatabase(username);
        byte[] saltAndEncryptedBytes = Files.readAllBytes(inputFilePath);
        byte[] salt = Arrays.copyOfRange(saltAndEncryptedBytes, 0, SALT_SIZE);
        byte[] iv = Arrays.copyOfRange(saltAndEncryptedBytes, SALT_SIZE, SALT_SIZE + 16);
        byte[] encryptedBytes = Arrays.copyOfRange(saltAndEncryptedBytes, SALT_SIZE + iv.length, saltAndEncryptedBytes.length);
        //derive get secret ket from pasword
        SecretKey key = deriveKey(decryptPassword(password), salt);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        
        Files.write(outputFilePath, decryptedBytes);
    }

    private static byte[] generateSalt() {
         byte[] salt = new byte[SALT_SIZE];
         SecureRandom random = new SecureRandom();
         random.nextBytes(salt);
         return salt;
    }
    
    private static String getEncryptedPasswordFromDatabase(String username) throws SQLException{
     try (Connection conn = DriverManager.getConnection(DB_URL)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT password FROM users WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            } else {
                throw new IllegalArgumentException("Username not found: " + username);
            } 
     }
    }
     
    private static SecretKey deriveKey(String password, byte[] salt) throws Exception {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, KEY_SIZE);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }
    
     private static String decryptPassword(String encryptedPassword) throws Exception {
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Arrays.copyOf(encryptedPassword.getBytes("UTF-8"), 16), "AES"));
    byte[] decryptedPassword = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword.substring(16)));
    return new String(decryptedPassword);
}

    }
 
