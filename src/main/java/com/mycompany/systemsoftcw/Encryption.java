/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.systemsoftcw;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.*;
import javax.crypto.spec.*;
/**
 *
 * @author ntu-user
 */
public class Encryption {
    private static final int KEY_SIZE = 256;
    private static final int SALT_SIZE = 10;
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    
    public static void encrypt(File inputFile, File outputFile, String password) throws Exception{
        byte[] salt = generateSalt();
    }

    private static byte[] generateSalt() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
