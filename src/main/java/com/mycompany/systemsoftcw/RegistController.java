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
    private void confirmRegist(ActionEvent event) throws IOException {
        
        Connection connection = null;
            
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:AccountDB.db");
            System.out.println("connection successful");
            Statement statement = connection.createStatement();
            //statement.setQueryTimeout(30);
            statement.executeUpdate("drop table if exists AccountDB");
        }
        catch(SQLException e){
            //System.err.println(e.getMessage());
            System.out.println("failed");
        }
        App.setRoot("maininterface");
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
