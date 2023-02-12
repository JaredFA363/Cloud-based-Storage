/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.systemsoftcw;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
//import java.sql.Statement;

import java.util.Base64;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
/**
 * FXML Controller class
 *
 * @author ntu-user
 * 
 * @brief Register Controller Class
 * 
 * @details The register controller takes in the user input from the controller view. Creates the table if it doesn't exist hashes the password.
 * Then adds both the email and password to the database.
 */
public class RegistController implements Initializable {
    
    @FXML
    private Button confirmRegis;
    @FXML
    private TextField regEmail;
    @FXML
    private TextField regPassword;
    
    Connection connection = null;
    String registeremail;
    String registerpass;
    private Random random = new SecureRandom();
    private String saltValue;
    private String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private int timeout = 30;
    private int iterations = 10000;
    private int keylength = 256;
    private String passHashKey = "PBKDF2WithHmacSHA1";
    
    @FXML
    private void confirmRegist(ActionEvent event) throws IOException, InvalidKeySpecException {
 
        RegistController obj = new RegistController();
        registeremail = regEmail.getText();
        registerpass = regPassword.getText();
        if (validateEmail(registeremail)==false) {
            System.out.println("Incorrect email");
            App.setRoot("primary");
        }
        else{
            obj.generateOrLoadSalt();
            obj.createTable("Account");
            obj.addUser(registeremail, registerpass);
            App.setRoot("maininterface");
        }
    }
    
    /**
    *
    * @brief Create Table Procedure
    * 
    * @details Takes the table name. If the table doesn't exist. Then creates it.
    * @param[in] The table name
    */
    
    public void createTable(String tableName){
        try{
            connection = Dbcon.conDb();
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            System.out.println("Trying to create " + tableName + " table");
            statement.executeUpdate("create table if not exists " + tableName + "( id integer primary key autoincrement, email string, password string)");
            System.out.println("Success");
        }
        catch (SQLException ex){
            System.out.println("Unable to create table " + ex);            
        }
        finally{
            try{
                if (connection != null){
                    connection.close();
                }
            }
            catch (SQLException e){
                System.err.println(e.getMessage());
            }
        }
    }
    
    /**
    *
    * @brief Add User Function
    * 
    * @details Takes in the username and password combination. Hashes the password.
    * Then stores the email and hashed password into the database.
    * 
    * @param[in] The email inputted by the user
    * @param[in] password input 
    * 
    */
    
    public void addUser(String email, String password){
        try{
            connection = Dbcon.conDb();
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            System.out.println("Attempting to add user");
            statement.executeUpdate("insert into Account(email,password) values('"+email+"','"+generateSecurePass(password)+"')");
        }
        catch (SQLException | InvalidKeySpecException ex){
            System.out.println("Failed to add user" + ex);
        }
        finally{
            try{
                if (connection != null){
                    connection.close();
                }
            }
            catch (SQLException e){
                System.err.println(e.getMessage());
            }
        }
    }
    
    private String getSaltvalue(int length) throws InvalidKeySpecException{
        StringBuilder finalval = new StringBuilder(length);
        for (int i =0; i<length; i++){
            finalval.append(characters.charAt(random.nextInt(characters.length())));
        }
        return new String(finalval);
    }
    
    private byte[] hash(char[] password, byte[] salt) throws InvalidKeySpecException{
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keylength);
        Arrays.fill(password, Character.MIN_VALUE);
        try{
            SecretKeyFactory skf = SecretKeyFactory.getInstance(passHashKey);
            return skf.generateSecret(spec).getEncoded();
        }
        catch(NoSuchAlgorithmException | InvalidKeySpecException e){
            throw new AssertionError("Error while hashing a password" + e.getMessage(), e);
        }
        finally{
            spec.clearPassword();
        }
    }
    
    /**
    *
    * @brief Generate Secure Passwword Function
    * 
    * @details Takes in the password. Uses the hash function to hash the password and create the secure password
    * 
    * @param[in] password input by the user
    * 
    * @returns The hashed password
    */
    
    public String generateSecurePass(String password) throws InvalidKeySpecException {
        String finalval = null;
        byte[] securePassword = hash(password.toCharArray(), saltValue.getBytes());
        finalval = Base64.getEncoder().encodeToString(securePassword);
        return finalval;
    }
    
    /**
    *
    * @brief Generate or Load Salt 
    * 
    * @details Generates salt file if it doesn't exist or loads it form the .salt file
    */
    
    public void generateOrLoadSalt() throws IOException, InvalidKeySpecException{
        try{
            File fp = new File(".salt");
            if (!fp.exists()){
                saltValue = this.getSaltvalue(30);
                FileWriter myWriter = new FileWriter(fp);
                myWriter.write(saltValue);
                myWriter.close();
            }
            else{
                Scanner myReader = new Scanner(fp);
                while (myReader.hasNextLine()) {
                    saltValue = myReader.nextLine();
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public boolean validateEmail(String email){
        boolean confirm_email= true;
        if (!(Pattern.matches("^[a-zA-Z0-9]+[@]{1}+[a-zA-Z0-9]+[.]{1}+[a-zA-Z0-9]+$", email))) {
            System.out.println("Not in email format");
            confirm_email= false;
        }
        return confirm_email;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
