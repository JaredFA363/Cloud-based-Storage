/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.systemsoftcw;

import java.io.IOException;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class TerminalController implements Initializable {

    @FXML
    private Button GoBack;
    @FXML
    private Button btnCommand;
    @FXML
    private TextArea CommandLine;
    @FXML
    private TextArea CommandLineInput;
    @FXML
    private Label label;
   
    
    @FXML
    private void BackToFiles(ActionEvent event) throws IOException{
        //App.setRoot("maininterface");
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
    
    @FXML
    private void Send_Command() throws IOException{
        String email = label.getText();
        String CommandArea = CommandLineInput.getText();
        String[] Commands = CommandArea.split("\n");
        String lastLine = Commands[Commands.length - 1];
        
        String[] WordsinCommand = lastLine.trim().split("\\s+");
        
        if (WordsinCommand[0].equals("mkdir") && WordsinCommand.length == 2){
            String foldername = WordsinCommand[1];
            MaininterfaceController obj = new MaininterfaceController();
            obj.CreateFolder(foldername,email);
            CommandLine.appendText("\nDirectory made");
            
        }else if(WordsinCommand[0].equals("ls")){
            runLsCommand();
            
        }else if(WordsinCommand[0].equals("mv") && WordsinCommand.length == 3){
            String source = WordsinCommand[1];
            String destination = WordsinCommand[2];
            MaininterfaceController obj = new MaininterfaceController();
            obj.moveFile(source,destination,email);
            CommandLine.appendText("\nFile moved");
            
        }else if(WordsinCommand[0].equals("cp") && WordsinCommand.length == 3){
            String currentfile = WordsinCommand[1];
            String copiedfile = WordsinCommand[2];
            MaininterfaceController obj = new MaininterfaceController();
            obj.CopyFile(currentfile,copiedfile,email);
            CommandLine.appendText("\nFile copied");
            
        }else if(WordsinCommand[0].equals("ps")){
            runPsCommand();
            
        }else if(WordsinCommand[0].equals("whoami")){
            CommandLine.appendText("\n"+email);
            
        }else if(WordsinCommand[0].equals("tree")){
            runTreeCommand();
            
        }else if(WordsinCommand[0].equals("nano") && WordsinCommand.length == 2){
            String filename = WordsinCommand[1];
            runNanoCommand(filename);
            
        }else if(WordsinCommand[0].equals("--help")){
            runHelp();
            
        }else{
            CommandLine.appendText("\nIncorrect Command Error type --help to see commands");
        }
    }
    
    private void runPsCommand(){
        try{
            ProcessBuilder processBuilder = new ProcessBuilder("ps");
            Process process = processBuilder.start();
            
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                CommandLine.appendText(line + "\n");
            }
        }catch (IOException e){
            System.out.println("\nFailed to show processes");
        }
    }
    
    private void runTreeCommand(){
        try{
            ProcessBuilder processBuilder = new ProcessBuilder("tree");
            Process process = processBuilder.start();
            
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                CommandLine.appendText(line + "\n");
            }
        }catch (IOException e){
            System.out.println("\nFailed to show tree");
        }
    }
    
    private void runLsCommand(){       
        try{
            ProcessBuilder processBuilder = new ProcessBuilder("ls");
            Process process = processBuilder.start();
            
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                CommandLine.appendText(line + "\n");
            }
        }catch (IOException e){
            System.out.println("\nFailed to list files");
        }
    }
    
    // To run need to do sudo chmod 777 /data
    private void runNanoCommand(String filename) throws IOException{
        String email = label.getText();
        String filenamestore = "/data/"+email+"/"+filename;
        
        ProcessBuilder processBuilder = new ProcessBuilder("terminator", "-x", "nano " + filenamestore);
        processBuilder.redirectInput(ProcessBuilder.Redirect.INHERIT);
        processBuilder.start();
    }
    
    private void runHelp(){
        CommandLine.appendText("\n\n"+ "Make sure you are on a new line when running command"
                + "\nCommand Syntax: "
                + "\nps"
                + "\nls"
                + "\ntree"
                + "\nwhoami"
                + "\nnano + filepathexample"
                + "\ncp filename newfilename"
                + "\nmkdir foldername"
                + "\nmv filepath destinationpath ");
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
