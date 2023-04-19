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
import javafx.scene.control.TextArea;

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
    private void BackToFiles() throws IOException{
        App.setRoot("maininterface");
    }
    
    @FXML
    private void Send_Command(){
        String CommandArea = CommandLine.getText();
        String[] Commands = CommandArea.split("\n");
        String lastLine = Commands[Commands.length - 1];
        
        String[] WordsinCommand = lastLine.trim().split("\\s+");
        
        if (WordsinCommand[0].equals("mkdir") && WordsinCommand.length == 2){
            String foldername = WordsinCommand[1];
            MaininterfaceController obj = new MaininterfaceController();
            obj.CreateFolder(foldername);
            CommandLine.appendText("\nDirectory made");
            
        }else if(WordsinCommand[0].equals("ls")){
            runLsCommand();
            
        }else if(WordsinCommand[0].equals("mv") && WordsinCommand.length == 3){
            String source = WordsinCommand[1];
            String destination = WordsinCommand[2];
            MaininterfaceController obj = new MaininterfaceController();
            obj.moveFile(source,destination);
            CommandLine.appendText("\nFile moved");
            
        }else if(WordsinCommand[0].equals("cp") && WordsinCommand.length == 3){
            String currentfile = WordsinCommand[1];
            String copiedfile = WordsinCommand[2];
            MaininterfaceController obj = new MaininterfaceController();
            obj.CopyFile(currentfile,copiedfile);
            CommandLine.appendText("\nFile copied");
            
        }else if(WordsinCommand[0].equals("ps")){
            runPsCommand();
            
        }else if(WordsinCommand[0].equals("whoami")){
            System.out.println("whoami");
            LoginController obj = new LoginController();
            String user = obj.getEmail();
            System.out.println(user);
            
        }else if(WordsinCommand[0].equals("tree")){
            runTreeCommand();
            
        }else if(WordsinCommand[0].equals("nano") && WordsinCommand.length == 2){
            String filename = WordsinCommand[1];
            runNanoCommand(filename);
            
        }else{
            CommandLine.appendText("\nIncorrect Command Error");
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
    
    private void runNanoCommand(String filename){
        try{
            ProcessBuilder processBuilder = new ProcessBuilder("nano "+ filename);
            processBuilder.redirectInput(ProcessBuilder.Redirect.INHERIT);
            Process process = processBuilder.start();
            
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                CommandLine.appendText(line + "\n");
            }
        }catch (IOException e){
            System.out.println("\nFailed to run nano");
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
