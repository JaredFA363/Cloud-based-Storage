/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.systemsoftcw;

import java.sql.*;

/**
 * @brief Database Connection Class
 * 
 * @author ntu-user
 * 
 * @details This function creates the connection between the program and the AccountDB database
 * return connection to be used with the other controllers. E.g. Login & Register Controllers
 *
 * @returns the connection if correct if not returns error.
 * 
 */
public class Dbcon {
    
    public static Connection conDb(){
        Connection connection = null;
    
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:AccountDB.db");
            System.out.println("connection successful");
            //Statement statement = connection.createStatement();
            //statement.setQueryTimeout(30);
            //statement.executeUpdate("drop table if exists AccountDB");
            return connection;
        }catch (SQLException ex){
            System.out.println("failed" + ex);
            return null;
        }
    }
    
}
