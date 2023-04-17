/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.systemsoftcw;

import java.io.IOException;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

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
    private Button btnTerminal;
    @FXML
    private ComboBox File_combobox;
    @FXML
    private Button file_submit;
    @FXML
    private TextField fileOrFolderName;
    @FXML
    private TextField newFilenameOrPath;
    
    @FXML 
    private void logOut(ActionEvent event) throws IOException{
        App.setRoot("primary");
    }
    
    @FXML
    private void GoToProfile(ActionEvent event) throws IOException{
        App.setRoot("profile");
    }
    
    @FXML
    private void GoToTerminal(ActionEvent event) throws IOException{
        App.setRoot("terminal");
    }
    
    @FXML
    private void Submit_File_Order(ActionEvent event){
        String fileOrFolder = fileOrFolderName.getText();
        String newFileOrPath;
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
            newFileOrPath = newFilenameOrPath.getText();
            UploadFile(newFileOrPath);
            //System.out.println("");
        }else if (inputted_Order.equals("Rename File")){
            newFileOrPath = newFilenameOrPath.getText();
            renameFile(fileOrFolder,newFileOrPath);
        }else if (inputted_Order.equals("Move File")){
            newFileOrPath = newFilenameOrPath.getText();
            moveFile(fileOrFolder,newFileOrPath);
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
        if (!newfileObj.Files.copy()){
            System.out.println("Failed to copy");
        }else{
            System.out.println("File Copied");
        }
    }
    
    private void UploadFile(String destination){
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            try{
                Files.copy(selectedFile.toPath(),Paths.get(destination, selectedFile.getName()), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File has been uploaded!");
            }
            catch(IOException e) {
                System.out.println("Cannot upload File");
                e.printStackTrace();
            }
        } else{
            System.out.println("You have not selected a file");
        }
      
            
    }

    private void renameFile(String currentfilename, String newfilename){
        File currentfileObj = new File(currentfilename);
        File newfileObj = new File(newfilename);
        if (currentfileObj.renameTo(newfileObj)){
            System.out.println("File Renamed");
        }else{
            System.out.println("Failed to Rename");
        }
    }
    
    private void moveFile(String currentfilename, String newfilename){
        File currentfileObj = new File(currentfilename);
        File newfileObj = new File(newfilename);
        if (currentfileObj.renameTo(newfileObj)){
            System.out.println("File moved");
        }else{
            System.out.println("Failed to Move");
        }
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
