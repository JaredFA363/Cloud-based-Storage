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
        App.setRoot("register");
    }
            
    @FXML
    private Button signInButton;
    
    @FXML 
    private void switchToSignIn(ActionEvent event) throws IOException{
        App.setRoot("signin");
    }    

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
