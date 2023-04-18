/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.systemsoftcw;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
//import java.lang.reflect.InvocationTargetException;
import java.security.spec.InvalidKeySpecException;

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
 * @brief Login Controller Class 
 * 
 * @author ntu-user
 * 
 * @details Login Controller connects to Login view. Takes in the email and password of the user.
 * Allows Users to go into the main program if details are correct.
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
    String inPass;
    
    @FXML 
    private void confirmLogins(ActionEvent event) throws IOException, InvalidKeySpecException{
        LoginController obj = new LoginController();
        RegistController RegObj = new RegistController();
        loginemail = logEmail.getText();
        loginpass = logPassword.getText();
        RegObj.generateOrLoadSalt();
        inPass = RegObj.generateSecurePass(loginpass);
        validated = obj.validateUser(loginemail, inPass);
        if (validated == true){
            setEmail(loginemail);
            System.out.println(getEmail());
            App.setRoot("maininterface");
        }
        else{
            System.out.println("Incorrect Email pr Passwprd");
            App.setRoot("primary");
        }
    }
    
    /**
    *
    * @brief Validate User Function
    * 
    * @details Takes in the username and password combination. Compares it to the database.
    * If matches returns true to the confirmLogins procedure.
    * 
    * @param[in] The email inputted by the user
    * @param[in] password input by the user after it has been hashed
    * 
    * @returns The Boolean variable flag which shows if the verification is successful or not 
    */
    
    public boolean validateUser(String email, String pass){
        Boolean flag = false;
        try{
            connection = Dbcon.conDb();
            var statement = connection.createStatement();
            statement.setQueryTimeout(30);
            try{
                //String inPass = RegistController.generateSecurePass(pass);
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
    
    private void setEmail(String email){
        this.loginemail = email;
    }
    
    public String getEmail(){
        return loginemail;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
