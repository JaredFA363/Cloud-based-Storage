/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.systemsoftcw;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.lang.reflect.InvocationTargetException;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class LoginController implements Initializable{

    @FXML
    private Button confirmLogin;
    @FXML
    private TextField logEmail;
    @FXML
    private TextField logPassword;
    
    Connection connection = null;
    String loginemail;
    String loginpass;
    Boolean validated;
    
    @FXML 
    private void confirmLogins(ActionEvent event) throws IOException{
        LoginController obj = new LoginController();
        loginemail = logEmail.getText();
        loginpass = logPassword.getText();
        validated = obj.validateUser(loginemail, loginpass);
        if (validated == true){
            App.setRoot("maininterface");
        }
        else{
            System.out.println("Incorrect Email pr Passwprd");
            App.setRoot("primary");
        }
    }
    
    public boolean validateUser(String email, String pass){
        Boolean flag = false;
        try{
            connection = Dbcon.conDb();
            var statement = connection.createStatement();
            statement.setQueryTimeout(30);
            try{
                ResultSet rs = statement.executeQuery("select email,password from Account");
            
                while (rs.next()){
                    if (email.equals(rs.getString("email"))&&rs.getString("password").equals(pass)){
                        flag = true;
                        break;
                    }
                }
            }
            catch(RuntimeException e) {
                System.out.println("No email");
            }
        }
        catch (SQLException ex){
            System.out.println("Unable to validate");
        }
        finally{
            try{
                if (connection != null){
                    connection.close();
                }
            }
            catch(SQLException e){
                System.err.println(e.getMessage());
            }
        }
        return flag;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
