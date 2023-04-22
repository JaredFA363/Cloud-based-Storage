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
 *
 * @author ntu-user
 */


public class ErrorWarningMonitor {
    
    private static final String LOG_FILE_PATH = "/data/logs/error_warning.log";
    private static final String ADMIN_LOG_FILE_PATH = "/data/logs/admin_error_warning.log";
    
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
    
    private static void ErrorWarning(String message) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(LOG_FILE_PATH, true)))) {
            // Write the error/warning message to log file
            writer.println(message);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(ADMIN_LOG_FILE_PATH, true)))) {
            // Write the error/warning message to admin log file
            writer.println(message);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
         EmailNotification.sendEmailToAdmin("Error/Warning occurred: " + message);
    }

    private static class EmailNotification {

        private static void sendEmailToAdmin(String string) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public EmailNotification() {
        }
    }
}
