package com.chatapp;

public class Login {

    private String username;
    private String password;
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
            this.username = username;
            this.password = password;
            result = "\n------User registered!------\n";
        }

        return result;
    }

    public boolean loginUser(String username, String password) {

        if (this.username.equals(username) && this.password.equals(password)) {
            this.loginMessage = "\nWelcome $1 $2, it is great to see you again.\n";
            return true;
        }else {
            this.loginMessage = "Username or password incorrect, please try again.\n";
        }
        // in a production system, it is at this point that we would bring the user's account into focus application wide

        return false;
    }

    public String returnLoginStatus() {
        return this.loginMessage;
    }

    public boolean checkCellPhoneNumber(String cellnumber) {
        boolean isValid = cellnumber.matches("^\\+27\\d{1,9}$");
        String result;

        if (isValid) {
            result = "Cell phone number successfully added.";
        }else {
            result = "Cell phone number incorrectly formatted or does not contain international code.";
        }

        System.out.println(result);

        return isValid;
    }
}
