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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * FXML Controller class
 *
 * @author ntu-user
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
    
    @FXML
    private void confirmRegist(ActionEvent event) throws IOException {
 
        RegistController obj = new RegistController();
        registeremail = regEmail.getText();
        registerpass = regPassword.getText();
        obj.createTable("Account");
        obj.addUser(registeremail, registerpass);
        App.setRoot("maininterface");
    }
    
    public void createTable(String tableName){
        try{
            connection = Dbcon.conDb();
            var statement = connection.createStatement();
            statement.setQueryTimeout(30);
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
    
    public void addUser(String email, String password){
        try{
            connection = Dbcon.conDb();
            var statement = connection.createStatement();
            statement.setQueryTimeout(30);
            System.out.println("Attempting to add user");
            statement.executeUpdate("insert into Account(email,password) values('"+email+"','"+password+"')");
        }
        catch (SQLException ex){
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
