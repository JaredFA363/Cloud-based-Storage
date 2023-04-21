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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;

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
    private Label label;
    
    @FXML 
    private void logOut(ActionEvent event) throws IOException{
        //App.setRoot("primary");
        String email = label.getText();
        removeUserInstance(email);
        
        Parent root = FXMLLoader.load(getClass().getResource("primary.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void GoToProfile(ActionEvent event) throws IOException{
        //App.setRoot("profile");
        Parent root = FXMLLoader.load(getClass().getResource("profile.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void GoToTerminal(ActionEvent event) throws IOException{
        //App.setRoot("terminal");
        /*Parent root = FXMLLoader.load(getClass().getResource("terminal.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/
        String email = label.getText();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("terminal.fxml"));
        Parent root = loader.load();
            
        TerminalController terminalcontroller = loader.getController();
        terminalcontroller.setEmails(email);
            
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }
    
    @FXML
    private void Submit_File_Order(ActionEvent event){
        String fileOrFolder = fileOrFolderName.getText();
        String newFileOrPath;
        String inputted_Order = File_combobox.getValue().toString();
        String email = label.getText();
        
        if (inputted_Order.equals("Create File")){
            CreateFile(fileOrFolder,email);
        }else if (inputted_Order.equals("Delete File/Folder")){
            deleteFile(fileOrFolder,email);
        }else if (inputted_Order.equals("Copy File")){
            newFileOrPath = newFilenameOrPath.getText();
            CopyFile(fileOrFolder,newFileOrPath,email);
        }else if (inputted_Order.equals("Create Folder")){
            CreateFolder(fileOrFolder,email);
        }else if (inputted_Order.equals("Upload File")){
            newFileOrPath = newFilenameOrPath.getText();
            String Uplaod_desination = "/data/"+email+"/"+newFileOrPath;
            UploadFile(Uplaod_desination);
        }else if (inputted_Order.equals("Rename File")){
            newFileOrPath = newFilenameOrPath.getText();
            renameFile(fileOrFolder,newFileOrPath,email);
        }else if (inputted_Order.equals("Move File")){
            newFileOrPath = newFilenameOrPath.getText();
            moveFile(fileOrFolder,newFileOrPath,email);
        }
    }
    
    private void CreateFile(String filename, String email){
        try{
            File newfileObj = new File("/data/"+email+"/"+filename);
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
    
    public void CreateFolder(String Foldername, String email){
        File newfileObj = new File("/data/"+email+"/"+Foldername);
        if (newfileObj.mkdir()){
            System.out.println("Folder Created");
        }else{
            System.out.println("Failed create Folder");
        }
    }
    
    private void deleteFile(String filename, String email){
        File newfileObj = new File("/data/"+email+"/"+filename);
        if (newfileObj.delete()){
            System.out.println("File Deleted");
        }else{
            System.out.println("Failed to delete");
        }
    }
    
    public void CopyFile(String filename, String newfilename, String email){
        File newfileObj = new File("/data/"+email+"/"+filename);
        File copiedfile = new File("/data/"+email+"/"+newfilename);
        try{
            Files.copy(newfileObj.toPath(), copiedfile.toPath());
            System.out.println("File Copied");
        }catch(Exception e){
            System.out.println("Failed to copy file");
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

    private void renameFile(String currentfilename, String newfilename, String email){
        File currentfileObj = new File("/data/"+email+"/"+currentfilename);
        File newfileObj = new File("/data/"+email+"/"+newfilename);
        if (currentfileObj.renameTo(newfileObj)){
            System.out.println("File Renamed");
        }else{
            System.out.println("Failed to Rename");
        }
    }
    
    public void moveFile(String currentfilename, String newfilename, String email){
        File currentfileObj = new File("/data/"+email+"/"+currentfilename);
        File newfileObj = new File("/data/"+email+"/"+newfilename);
        if (currentfileObj.renameTo(newfileObj)){
            System.out.println("File moved");
        }else{
            System.out.println("Failed to Move");
        }
    }
    
    public void setEmail(String email){
        label.setText(email);
    }
    
    private void removeUserInstance(String email) throws IOException {
        File file = new File("currentusers.txt");
        String target = email;
        
        // Read the contents of the file
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder stringBuilder = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            stringBuilder.append(line);
            stringBuilder.append(System.lineSeparator());
            line = reader.readLine();
        }
        reader.close();
        String content = stringBuilder.toString();

        String updatedContent = content.replaceAll(target, "");

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(updatedContent);
        writer.close();
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
