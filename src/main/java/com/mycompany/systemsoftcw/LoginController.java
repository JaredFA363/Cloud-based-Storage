/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.systemsoftcw;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.security.spec.InvalidKeySpecException;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 * FXML Controller class
 *
 * @brief Login Controller Class 
 * 
 * @author N0992216
 * 
 * @details Login Controller connects to Login view. Takes in the email and password of the user.
 * Allows Users to go into the main program if details are correct.
 */
public class LoginController implements Initializable{

    @FXML
    private Button confirmLogin;
    @FXML
    private TextField logEmail;
    @FXML
    private TextField logPassword;
    
    Connection connection = null;
    String loginemail;
    String loginpass;
    Boolean validated;
    String inPass;
    
    /**
    *
    * @brief Confirm Function
    * 
    * @details When the login button is pressed, this function checks if a valid email and password has been inputted.
    * If it has the user will be allowed access to the main interface. If not then will be shown an error message.
    * 
    * @param[in] Action event event
    * 
    */
    
    @FXML 
    private void confirmLogins(ActionEvent event) throws IOException, InvalidKeySpecException{
        LoginController obj = new LoginController();
        RegistController RegObj = new RegistController();
        loginemail = logEmail.getText();
        loginpass = logPassword.getText();
        RegObj.generateOrLoadSalt();
        inPass = RegObj.generateSecurePass(loginpass);
        validated = obj.validateUser(loginemail, inPass);
        if (validated == true){
            if (checkUserInstance(loginemail) == true){
                System.out.println("Already Logged In");
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error ");
                alert.setHeaderText("Error Already Logged in");
                alert.setContentText("Cannot be logged in twice");
                alert.showAndWait();
                
                Parent root = FXMLLoader.load(getClass().getResource("primary.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else{
                setFolder(loginemail);
                appendUser(loginemail);
            
                FXMLLoader loader = new FXMLLoader(getClass().getResource("maininterface.fxml"));
                Parent root = loader.load();
            
                MaininterfaceController maininterfacecontroller = loader.getController();
                maininterfacecontroller.setEmail(loginemail);
            
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
        else{
            System.out.println("Incorrect Email or Passwprd");
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error Incorrect");
            alert.setContentText("Incorrect Username or password");
            alert.showAndWait();
            
            Parent root = FXMLLoader.load(getClass().getResource("primary.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    
    /**
    *
    * @brief Validate User Function
    * 
    * @details Takes in the username and password combination. Compares it to the database.
    * If matches returns true to the confirmLogins procedure.
    * 
    * @param[in] The email inputted by the user
    * @param[in] password input by the user after it has been hashed
    * 
    * @returns The Boolean variable flag which shows if the verification is successful or not 
    */
    
    public boolean validateUser(String email, String pass){
        Boolean flag = false;
        try{
            connection = Dbcon.conDb();
            var statement = connection.createStatement();
            statement.setQueryTimeout(30);
            try{
                //String inPass = RegistController.generateSecurePass(pass);
                ResultSet rs = statement.executeQuery("select email,password from Account");
            
                while (rs.next()){
                    if (email.equals(rs.getString("email"))&&rs.getString("password").equals(pass)){
                        flag = true;
                        break;
                    }
                }
            }
            catch(RuntimeException e) {
                System.out.println("No email");
            }
        }
        catch (SQLException ex){
            System.out.println("Unable to validate");
        }
        finally{
            try{
                if (connection != null){
                    connection.close();
                }
            }
            catch(SQLException e){
                System.err.println(e.getMessage());
            }
        }
        return flag;
    }
    
    /**
    *
    * @brief Set Folder Function
    * 
    * @details Sets the folder that the users files will be stored in in the docker container.
    * 
    * @param[in] The email inputted by the user
    * 
    */
    
    private void setFolder(String email){
        Path path = Paths.get("/data/"+email);
        
        if (!Files.exists(path))
        {
            try{
                Files.createDirectories(path);
            }catch(Exception e){
                System.out.println("Erro "+e);
            }
        }
    }
    
    /**
    *
    * @brief Append User Function
    * 
    * @details Appends a user's email to a text file when they are logged in and active
    * 
    * @param[in] The email inputted by the user 
    * 
    */
    
    private void appendUser(String email){
        String data = email;
        String filename = "currentusers.txt";
        
        try {
            FileWriter writer = new FileWriter(filename, true); // open the file in append mode
            writer.write(data + "\n"); // write the data to the end of the file
            writer.close(); // close the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
    *
    * @brief Validate User Function
    * 
    * @details Checks if user's email is already in text file if not then they are allowed to login.
    * If it is then an error message is displayed.
    * 
    * @param[in] The email inputted by the user
    * 
    * @returns The Boolean variable flag which shows if the user is already logged in or not 
    */
    
    private boolean checkUserInstance(String email) throws IOException{
        String filePath = "currentusers.txt";
        String user = email;

        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains(user)) {
                return true;
            }
        }

        reader.close();
        return false;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
