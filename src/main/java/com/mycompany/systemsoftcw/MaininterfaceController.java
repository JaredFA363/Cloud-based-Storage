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
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;

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
    private ListView listView;
    @FXML 
    private TextField viewPath;
    @FXML
    private Button confirmbtn;
    
    @FXML
    private void ConfirmPath(ActionEvent event){
        String path = viewPath.getText();
        showFolder(path);
    }
    
    @FXML 
    private void logOut(ActionEvent event) throws IOException{
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
        Parent root = FXMLLoader.load(getClass().getResource("profile.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void GoToTerminal(ActionEvent event) throws IOException{
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
    private void Submit_File_Order(ActionEvent event) throws IOException{
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
        }else if (inputted_Order.equals("Share File")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sharefiles.fxml"));
            Parent root = loader.load();
            
            SharefilesController Sharefilescontroller = loader.getController();
            Sharefilescontroller.setEmails(email);
            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else if (inputted_Order.equals("Download File")){
            downloadFile(fileOrFolder,email);
        }
    }
    
    private void CreateFile(String filename, String email){
        try{
            File newfileObj = new File("/data/"+email+"/"+filename);
            if (newfileObj.createNewFile()){
                System.out.println("File Created");
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success ");
                alert.setHeaderText("Created File");
                alert.setContentText("File created");
                alert.showAndWait();
            }else{
                System.out.println("File already Exists");
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error ");
                alert.setHeaderText("Error");
                alert.setContentText("File already exists");
                alert.showAndWait();
            }
        }catch(IOException e){
            System.out.println("Error, Unable to create File");
            e.printStackTrace();
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error");
            alert.setContentText("Failed to create file");
            alert.showAndWait();
        }
    }
    
    public void CreateFolder(String Foldername, String email){
        File newfileObj = new File("/data/"+email+"/"+Foldername);
        if (newfileObj.mkdir()){
            System.out.println("Folder Created");
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success ");
            alert.setHeaderText("Folder");
            alert.setContentText("Folder created");
            alert.showAndWait();
        }else{
            System.out.println("Failed create Folder");
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error");
            alert.setContentText("Failed to create Folder");
            alert.showAndWait();
        }
    }
    
    private void deleteFile(String filename, String email){
        File newfileObj = new File("/data/"+email+"/"+filename);
        if (newfileObj.delete()){
            System.out.println("File Deleted");
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success ");
            alert.setHeaderText("File deleted");
            alert.setContentText("File deleted");
            alert.showAndWait();
        }else{
            System.out.println("Failed to delete");
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error");
            alert.setContentText("Failed to delete");
            alert.showAndWait();
        }
    }
    
    public void CopyFile(String filename, String newfilename, String email){
        File newfileObj = new File("/data/"+email+"/"+filename);
        File copiedfile = new File("/data/"+email+"/"+newfilename);
        try{
            Files.copy(newfileObj.toPath(), copiedfile.toPath());
            System.out.println("File Copied");
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success ");
            alert.setHeaderText("Copied File");
            alert.setContentText("File copied successfully");
            alert.showAndWait();
        }catch(Exception e){
            System.out.println("Failed to copy file");
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error");
            alert.setContentText("Failed to copy make sure both filepaths are correct");
            alert.showAndWait();
        }
    }
    
    private void UploadFile(String destination){
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            try{
                Files.copy(selectedFile.toPath(),Paths.get(destination, selectedFile.getName()), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File has been uploaded!");
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success ");
                alert.setHeaderText("Upload File");
                alert.setContentText("File uploaded to cloud");
                alert.showAndWait();
            }
            catch(IOException e) {
                System.out.println("Cannot upload File");
                e.printStackTrace();
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error ");
                alert.setHeaderText("Error");
                alert.setContentText("Failed to upload file make sure second textfield has correct destination");
                alert.showAndWait();
            }
        } else{
            System.out.println("You have not selected a file");
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error");
            alert.setContentText("Failed to upload file");
            alert.showAndWait();
        }
      
            
    }

    private void renameFile(String currentfilename, String newfilename, String email){
        File currentfileObj = new File("/data/"+email+"/"+currentfilename);
        File newfileObj = new File("/data/"+email+"/"+newfilename);
        if (currentfileObj.renameTo(newfileObj)){
            System.out.println("File Renamed");
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success ");
            alert.setHeaderText("File renamed");
            alert.setContentText("File renamed");
            alert.showAndWait();
            
        }else{
            System.out.println("Failed to Rename");
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error");
            alert.setContentText("Failed to rename file");
            alert.showAndWait();
        }
    }
    
    public void moveFile(String currentfilename, String newfilename, String email){
        File currentfileObj = new File("/data/"+email+"/"+currentfilename);
        File newfileObj = new File("/data/"+email+"/"+newfilename);
        if (currentfileObj.renameTo(newfileObj)){
            System.out.println("File moved");
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success ");
            alert.setHeaderText("Moved File");
            alert.setContentText("File moved successfully");
            alert.showAndWait();
        }else{
            System.out.println("Failed to Move");
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error");
            alert.setContentText("Failed to move file make sure both source and destination exist");
            alert.showAndWait();
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
   
    private void showFolder(String destination){
        String email = label.getText();
        String path = "/data/"+email+destination;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        ObservableList<String> items = FXCollections.observableArrayList();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                items.add(file.getName());
            } else if (file.isDirectory()) {
            items.add(file.getName() + "/");
            }
        }
        
        listView.getItems().addAll(items);
        
    }
    
    private void downloadFile(String filename, String email) throws IOException{
        String userfilepath = "/data/"+email+"/"+filename;
        String destfilepath = filename;
        
        File source = new File(userfilepath);
        File destination = new File(destfilepath);
        
        try{
            Files.copy(source.toPath(), destination.toPath());
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success ");
            alert.setHeaderText("Download");
            alert.setContentText("File downloaded");
            alert.showAndWait();
        }catch(Exception e){
            System.out.println("Failed to download");
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error");
            alert.setContentText("Failed to download file make sure file exists");
            alert.showAndWait();
            
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
                "Move File",
                "Share File",
                "Download File"
        );

        
    }    
    
}
