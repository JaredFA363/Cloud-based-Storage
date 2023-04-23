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
    private static final int KEY_SIZE = 256; //number of bits
    private static final int SALT_SIZE = 10; // makes sure passwords of different users do not derive the same key. 
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding"; /** The encryption algorithm. */
    private static final String DB_URL = "jdbc:sqlite:AccountDB.db";/** The database URL */
  /**
     * uses the AES technique to encrypt the supplied input file using the user's password as the key and a salt that is created at random.
     * 
     * @param inputFilePath the path of the input file
     * @param outputFilePath the path of the output file
     * @param username the username of the user whose password is used to derive the encryption key
     * @throws IOException if there is an I/O error while reading or writing the files
     * @throws NoSuchAlgorithmException if the specified encryption algorithm is not available
     * @throws InvalidKeySpecException if the specified key specification isn't valid
     * @throws SQLException if there is an error accessing the database
     */
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
     /**
     * Decrypts the input file using AES algorithm with a key derived from the user's password and a generated salt stored in the file.
     * 
     * @param inputFilePath the path of the input file
     * @param outputFilePath the path of the output file
     * @param username the username of the user whose password is used to derive the encryption key
     * @throws IOException if there is an I/O error while reading or writing the files
     * @throws NoSuchAlgorithmException if the specified encryption algorithm is not available
     */
    public static void decript(Path inputFilePath, Path outputFilePath, String username) throws Exception{
        String password = getEncryptedPasswordFromDatabase(username);
        byte[] saltAndEncryptedBytes = Files.readAllBytes(inputFilePath);//takes as input the path of the encrypted file
        byte[] salt = Arrays.copyOfRange(saltAndEncryptedBytes, 0, SALT_SIZE);
        byte[] iv = Arrays.copyOfRange(saltAndEncryptedBytes, SALT_SIZE, SALT_SIZE + 16);
        byte[] encryptedBytes = Arrays.copyOfRange(saltAndEncryptedBytes, SALT_SIZE + iv.length, saltAndEncryptedBytes.length);
        //derive get secret ket from pasword
        SecretKey key = deriveKey(decryptPassword(password), salt);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // decrypt the input file using the initialized cipher 
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        
        Files.write(outputFilePath, decryptedBytes); //and writes the decrypted bytes to the output file.
    }
       /**
        Generates a random salt of the specified length.
        @return a byte array containing the salt
       */
    private static byte[] generateSalt() {
         byte[] salt = new byte[SALT_SIZE];
         SecureRandom random = new SecureRandom(); //SecureRandom object to generate a random salt of the specified length.
         random.nextBytes(salt);
         return salt;
    }
    /**
Retrieves the encrypted password for a given username from the database.
*/
    private static String getEncryptedPasswordFromDatabase(String username) throws SQLException{//@param username the username to retrieve the password
     try (Connection conn = DriverManager.getConnection(DB_URL)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT password FROM users WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password");//return the encrypted password as a String
            } else {
                throw new IllegalArgumentException("Username cannot found: " + username);//throws SQLException if there is an error retrieving the password from the database
            } 
     }
    }
     /**
     Derives secret key using PBKDF2WithHmacSHA256 algorithm.
     @param password uses the password to derive the key from
     @param salt uses the salt to use for key derivation
     @return returns the secret key as a SecretKey object
     @throws an Exception if there is an error deriving the key
     */
    private static SecretKey deriveKey(String password, byte[] salt) throws Exception { //exception if error
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt,65536, KEY_SIZE);// derives the secret key using PBKDF2WithHmacSHA256 algorithm 
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }
    /**
    Decrypts a given encrypted password using AES encryption.
    @param encryptedPassword the encrypted password as a String
    @return the decrypted password as a String
    @throws Exception if there is an error decrypting the password
    */
     private static String decryptPassword(String encryptedPassword) throws Exception {
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Arrays.copyOf(encryptedPassword.getBytes("UTF-8"), 16), "AES"));
    byte[] decryptedPassword = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword.substring(16)));
    return new String(decryptedPassword);//returns the decrypted password as a String.
}

    }
 
