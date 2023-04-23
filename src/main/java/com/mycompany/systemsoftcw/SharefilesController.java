/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.systemsoftcw;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.nio.file.*;
import java.io.File;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class SharefilesController implements Initializable {

    @FXML
    private Button btnBack;
    @FXML
    private TextField Recipientemail;
    @FXML
    private TextField Filepath;
    @FXML
    private Button ReadOnly;
    @FXML
    private Button ReadWrite;
    @FXML
    private Label label;
    
    @FXML
    private void AllowRead(ActionEvent event) throws IOException{
        String useremail = label.getText();
        String recipemail = Recipientemail.getText();
        String filepath = Filepath.getText();
        
        String userfilepath = "/data/"+useremail+"/"+filepath;
        String destfilepath = "/data/"+recipemail+"/"+filepath;
        
        try{
            File source = new File(userfilepath);
            File destination = new File(destfilepath);
            
            Files.copy(source.toPath(), destination.toPath());
            destination.setReadOnly();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success ");
            alert.setHeaderText("Shared File");
            alert.setContentText("Files shared with Read-Only access");
            alert.showAndWait();
            
        }catch(Exception e){
            e.printStackTrace();
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error");
            alert.setContentText("Failed to share file");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void AllowReadWrite(ActionEvent event) throws IOException{
       String useremail = label.getText();
       String recipemail = Recipientemail.getText();
        String filepath = Filepath.getText();
        
        String userfilepath = "/data/"+useremail+"/"+filepath;
        String destfilepath = "/data/"+recipemail+"/"+filepath;
        
        try{
            File source = new File(userfilepath);
            File destination = new File(destfilepath);
            
            Files.copy(source.toPath(), destination.toPath());
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success ");
            alert.setHeaderText("Shared File");
            alert.setContentText("Files shared with Read-Write access");
            alert.showAndWait();
        }catch(Exception e){
            e.printStackTrace();
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error");
            alert.setContentText("Failed to share file");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void GoBack(ActionEvent event) throws IOException{
        String email = label.getText();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("maininterface.fxml"));
        Parent root = loader.load();
            
        MaininterfaceController maininterfacecontroller = loader.getController();
        maininterfacecontroller.setEmail(email);
            
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void setEmails(String email){
        label.setText(email);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
