
package com.mycompany.systemsoftcw;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileCreate {
    public static void createNewFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter file name: ");
        String fileName = scanner.nextLine();
        File file = new File(fileName);
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
            System.out.println("Enter content (press q to save and quit): ");
            FileWriter writer = new FileWriter(file);
            String line;
            do {
                line = scanner.nextLine();
                if (!line.equalsIgnoreCase("q")) {
                    writer.write(line + "\n");
                }
            } while (!line.equalsIgnoreCase("q"));
            writer.close();
            System.out.println("File saved.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

/**
 *
 * @author ntu-user
 */

