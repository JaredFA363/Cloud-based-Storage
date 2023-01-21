/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.systemsoftcw;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

/**
 *
 * @author ntu-user
 */
public class RegisterController {
    
    @FXML
    private Button confirmReg;
    
    @FXML
    private void confirmRegister(ActionEvent event) throws IOException {
        App.setRoot("maininterface");
    }
    
}
