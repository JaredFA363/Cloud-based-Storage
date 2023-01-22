package com.mycompany.systemsoftcw;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;


public class PrimaryController {
    
    @FXML
    private Button registerButton;
    
    @FXML 
    private void switchToRegister(ActionEvent event) throws IOException{
        App.setRoot("Regist");
    }
            
    @FXML
    private Button loginButton;
    
    @FXML 
    private void switchToLogin(ActionEvent event) throws IOException{
        App.setRoot("Login");
    }    

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
