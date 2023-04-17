/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.systemsoftcw;

import java.io.IOException;
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
        //System.out.println("Last line: " + lastLine);
        
        String[] WordsinCommand = lastLine.trim().split("\\s+");
        //for (String word : WordsinCommand) {
        //    System.out.println(word);
        //}
        System.out.println(WordsinCommand[1]);
        if (WordsinCommand[0].equals("mkdir") && WordsinCommand.length == 2){
            String foldername = WordsinCommand[1];
            MaininterfaceController obj = new MaininterfaceController();
            obj.CreateFolder(foldername);
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
