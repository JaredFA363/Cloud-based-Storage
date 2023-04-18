/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.systemsoftcw;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.sql.*;
/**
 *
 * @author ntu-user
 */
public class Encryption {
    private static final int KEY_SIZE = 256;
    private static final int SALT_SIZE = 10;
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String DB_URL = "jdbc:sqlite:AccountDB.db";
    
    public static void encrypt(Path inputFilePath, Path outputFilePath, String username) throws Exception{
        String password = retrieveEncryptedPasswordFromDatabase(username);
        byte[] salt = generateSalt();
        SecretKey key;
        key = deriveKey(decryptPassword(password), salt);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        
        byte[] inputBytes = Files.readAllBytes(inputFilePath);
        byte[] encryptedBytes = cipher.doFinal(inputBytes);
        
        byte [] iv = cipher.getIV();
        byte [] saltAndEncryptedBytes = new byte [SALT_SIZE + iv.length + encryptedBytes.length];
    }

    private static byte[] generateSalt() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static String retrieveEncryptedPasswordFromDatabase(String username) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    

}
