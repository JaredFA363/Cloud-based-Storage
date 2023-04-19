package com.mycompany.systemsoftcw;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class PrimaryController {
    
    @FXML
    private Button registerButton;
    
    @FXML 
    private void switchToRegister(ActionEvent event) throws IOException{
        //App.setRoot("Regist");
        Parent root = FXMLLoader.load(getClass().getResource("Regist.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
            
    @FXML
    private Button loginButton;
    
    @FXML 
    private void switchToLogin(ActionEvent event) throws IOException{
        //App.setRoot("Login");
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }    

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
