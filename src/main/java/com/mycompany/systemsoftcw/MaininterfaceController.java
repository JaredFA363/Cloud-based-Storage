/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.systemsoftcw;

import java.io.IOException;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
    private ComboBox File_combobox;
    @FXML
    private Button file_submit;
    @FXML
    private TextField fileOrFolderName;
    
    @FXML 
    private void logOut(ActionEvent event) throws IOException{
        App.setRoot("primary");
    }
    
    @FXML
    private void GoToProfile(ActionEvent event) throws IOException{
        App.setRoot("profile");
    }
    
    @FXML
    private void Submit_File_Order(ActionEvent event){
        String fileOrFolder = fileOrFolderName.getText();
        String inputted_Order = File_combobox.getValue().toString();
        
        if (inputted_Order.equals("Create File")){
            CreateFile(fileOrFolder);
        }else if (inputted_Order.equals("Delete File/Folder")){
            deleteFile(fileOrFolder);
        }else if (inputted_Order.equals("Copy File")){
            CopyFile(fileOrFolder);
        }else if (inputted_Order.equals("Create Folder")){
            CreateFolder(fileOrFolder);
        }else if (inputted_Order.equals("Upload File")){
            UploadFile();
        }else if (inputted_Order.equals("Rename File")){
            renameFile();
        }else if (inputted_Order.equals("Move File")){
            moveFile();
        }
    }
    
    private void CreateFile(String filename){
        try{
            File newfileObj = new File(filename);
            if (newfileObj.createNewFile()){
                System.out.println("File Created");
            }else{
                System.out.println("File already Exists");
            }
        }catch(IOException e){
            System.out.println("Error, Unable to create File");
            e.printStackTrace();
        }
    }
    
    private void CreateFolder(String Foldername){
        File newfileObj = new File(Foldername);
        if (newfileObj.mkdir()){
            System.out.println("Folder Created");
        }else{
            System.out.println("Failed create Folder");
        }
    }
    
    private void deleteFile(String filename){
        File newfileObj = new File(filename);
        if (newfileObj.delete()){
            System.out.println("File Deleted");
        }else{
            System.out.println("Failed to delete");
        }
    }
    
    private void CopyFile(String filename){
        File newfileObj = new File(filename);
    }
    
    private void UploadFile(){
        
    }

    private void renameFile(){
        
    }
    
    private void moveFile(){
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        File_combobox.getItems().addAll(
                "Create File",
                "Create Folder",
                "Delete File/Folder",
                "Copy File",
                "Upload File",
                "Rename File",
                "Move File"
        );
    }    
    
}
