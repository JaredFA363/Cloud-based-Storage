/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.systemsoftcw;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
/**
 *Monitors and lets users view logs errors and warnings and sends email notifications to admin.
 * @author n1004932
 */


public class ErrorWarningMonitor {
    
    private static final String LOG_FILE_PATH = "/data/logs/error_warning.log"; //path to the error/warning file
    private static final String ADMIN_LOG_FILE_PATH = "/data/logs/admin_error_warning.log"; //path to the admin error/warning file
    
    public static void main(String[] args) {
        // Monitor errors and warnings
        try {
            // Write the error/warning message to log file
            ErrorWarning("Error: Invalid input");

        } catch (Exception e) {
            // Write the error message to log file
            ErrorWarning("Error: " + e.getMessage());
        }
    }
    /**
    * Logs the given error or warning message to the error/warning log file and admin error/warning log file
    * and sends an email notification to the admin to view the errors
    * @param message sends the error or warning message to log and user
    */
    private static void ErrorWarning(String message) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(LOG_FILE_PATH, true)))) {
            // Writes error/warning message to log file
            writer.println(message);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(ADMIN_LOG_FILE_PATH, true)))) {
            // Writes error/warning message to admin log file
            writer.println(message);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
         EmailNotification.sendEmailToAdmin("Error/Warning occurred: " + message);
    }
    /**
    method sends an email to the admin
    */
    private static class EmailNotification {

        private static void sendEmailToAdmin(String string) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public EmailNotification() {
        }
    }
}
