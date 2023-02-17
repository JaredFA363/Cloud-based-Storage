/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.systemsoftcw;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * FXML Controller class
 *
 * @author ntu-user
 * 
 * @brief Profile Controller class
 * 
 * @details Allows a user to either delete or modify their account.
 */
public class ProfileController implements Initializable {
    
    @FXML
    private Button updateButton;
    
    @FXML
    private Button deleteButton;
    
    @FXML
    private TextField det_Email;
    
    @FXML
    private TextField det_Password;
    
    @FXML
    private TextField original_Email;
    
    Connection connection = null;
    String entered_Email;
    String entered_Password;
    String entered_Original_Email;
    String new_pass;
    
    /**
    * @brief update Details procedure
    * 
    * @details Allows a user to update their accounts in the database. Takes the original email and updated details.
    * Compares the email to the one in the database. If correct, stores the username and password combo. 
    */
    
    @FXML
    private void updateDetails(ActionEvent event) throws IOException, InvalidKeySpecException{
        entered_Email = det_Email.getText();
        entered_Password = det_Password.getText();
        entered_Original_Email = original_Email.getText();
        
        RegistController RegObj = new RegistController();
        RegObj.generateOrLoadSalt();
        new_pass = RegObj.generateSecurePass(entered_Password);
        
        
        try{
            connection = Dbcon.conDb();
            var statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("UPDATE Account SET email = '" +entered_Email+"', password = '" +new_pass+"' WHERE email = '" +entered_Original_Email+"'");
            App.setRoot("maininterface");
        }
        catch(SQLException e){
            System.out.println("Failed to update" + e);
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
    * @brief delete Details procedure
    * 
    * @details Allows a user to delete their accounts in the database. Takes the original email to see who's account to delete.
    * Then removes the row of accounts.
    */
    
    @FXML
    private void deleteDetails(ActionEvent event) throws IOException{
        entered_Original_Email = original_Email.getText();
        try{
            connection = Dbcon.conDb();
            var statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("DELETE FROM Account WHERE email = '"+entered_Original_Email+"'");
            App.setRoot("primary");
        }
        catch (SQLException e){
            System.out.println("Failed to delete" + e);
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
