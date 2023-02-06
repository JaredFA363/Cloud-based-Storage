/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

package validateanemail;

/**
 *
 * @author ntu-user
 */
public class ValidateAnEmail{

public static void main(String[] args){
   inputEmail();
}

public boolean checkEmailvalidity(String emailaddress){
    String email_regex = "[A-Z0-9]+@([a-zA-Z0-9
   return b;
}

public void inputEmail(){
        System.out.println("Please enter your email address ex:xyz@gmail.com");
        String emailaddress=name.nextLine();
        boolean a = checkEmailvalidity(emailaddress);
       if(a){
          System.out.println("Valid email");
        } else {
          System.out.println("InValid ema");
           inputEmail();
       }
}