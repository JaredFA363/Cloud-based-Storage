/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.systemsoftcw;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author ntu-user
 * 
 * @brief Main Interface Controller Class
 * 
 * @details The main interface is where the user would be able to see there files and interact with them.
 * Furthermore it has the profile button so the user can either change their details or delete there entire account.
 */
public class MaininterfaceController implements Initializable {
    
    @FXML
    private Button logoutButton;
    
    @FXML
    private Button profileButton;
    
    @FXML 
    private void logOut(ActionEvent event) throws IOException{
        App.setRoot("primary");
    }
    
    @FXML
    private void GoToProfile(ActionEvent event) throws IOException{
        App.setRoot("profile");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
