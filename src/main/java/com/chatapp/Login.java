package com.chatapp;

import java.util.ArrayList;

public class Login {

    public  String loginMessage;

    public boolean checkUserName(String username) {

        boolean isValid = username.matches("^(?=.*_).{1,5}$");
        String result;

        if (isValid) {
            result = "Username successfully captured.";
        }else {
            result = "Username is not correctly formatted please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        System.out.println(result);

        return isValid;
    }

    public boolean checkPasswordComplexity(String password) {
        boolean isValid = password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$");
        String result;

        if (isValid) {
            result = "Password successfully captured.";
        }else {
            result = "Password is not correctly formatted please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        System.out.println(result);

        return isValid;
    }

    public String registerUser(String username, String password) {
        boolean usernameValid;
        boolean passwordValid;
        String result = "";

        usernameValid = checkUserName(username);
        passwordValid = checkPasswordComplexity(password);

        if (passwordValid && usernameValid) {
            result = "\n------User registered!------\n";
        }

        return result;
    }

    public boolean loginUser(ArrayList<Account> accounts, String username, String password) {

        for (Account a : accounts) {

            if (a.getUsername().equals(username) && a.getPassword().equals(password)) {
                this.loginMessage = "\nWelcome " + a.getFirstName() + " " + a.getLastName() + ", it is great to see you again.\n";

                // in a production system, it is at this point that we would bring the user's account into focus application wide
                return true;

            }
        }

        // if we get here, we didn't have an account with those credentials. Message returned is defined by project requirement.
        this.loginMessage = "Username or password incorrect, please try again.\n";

        return false;
    }

    public String returnLoginStatus() {
        return this.loginMessage;
    }

    public boolean checkCellPhoneNumber(String cellnumber) {
        boolean isValid = cellnumber.matches("^\\+27\\d{1,9}$");
        String result;

        if (isValid) {
            result = "Cell phone number successfully added.\n";
        }else {
            result = "Cell phone number incorrectly formatted or does not contain international code.\n";
        }

        return isValid;
    }
}
