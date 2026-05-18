package com.chatapp;


import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    static ArrayList<Account> accounts = new ArrayList<>();

    static void main() {
        Login login = new Login();
        Scanner scanner = new Scanner(System.in);
        boolean usernameValid;
        boolean userLoggedIn;
        boolean passwordValid;
        boolean cellNumberValid;
        String firstName = "";
        String lastName = "";
        String username;
        String password;
        String cellNumber;
        int selectedChoice = 0;

        while (selectedChoice != 3) {

            System.out.println("Please select an option below:\n" +
                    "1. Login\n" +
                    "2. Register\n" +
                    "3. Exit");

            selectedChoice = scanner.nextInt();
            scanner.nextLine(); // clears the newline

            switch (selectedChoice) {
                case 1:
                    System.out.println("Please enter your username:");
                    username = scanner.nextLine();

                    String tempUsername = username;
                    Optional<Account> user = accounts.stream()
                            .filter(p -> tempUsername.equals(p.getUsername()))
                            .findFirst();

                    if (user.isEmpty()) {
                        System.out.println("Username not found, please register first!\n");
                        break;
                    }

                    System.out.println("Please enter your password:");
                    password = scanner.nextLine();

                    userLoggedIn = login.loginUser(accounts, username, password);
                    System.out.println(login.returnLoginStatus().replace("$1", firstName).replace("$2", lastName));

                    if (userLoggedIn) {
                        System.out.println("Welcome to Quick Chat.");


                        while (selectedChoice != 3) {
                            System.out.println("Please select an option below:\n" +
                                    "1. Send Messages\n" +
                                    "2. Show recently sent messages\n" +
                                    "3. Quit");

                            selectedChoice = scanner.nextInt();
                            scanner.nextLine(); // clears the newline

                            switch(selectedChoice) {
                                case 1:
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    break;
                            }

                        }
                    }


                    break;
                case 2:
                    System.out.println("Please enter your first name:");
                    firstName = scanner.nextLine();

                    System.out.println("Please enter your last name:");
                    lastName = scanner.nextLine();

                    do {
                        System.out.println("Please enter your username:");
                        username = scanner.nextLine();

                        usernameValid = login.checkUserName(username);

                        for (Account a : accounts) {
                            if (a.getUsername().equals(username)) {
                                System.out.println("Username already exists! Please try again!");
                                usernameValid = false;
                            }
                        }
                    } while (!usernameValid);

                    do {
                        System.out.println("Please enter your password:");
                        password = scanner.nextLine();

                        passwordValid = login.checkPasswordComplexity(password);
                    } while (!passwordValid);

                    do {
                        System.out.println("Please enter your cell phone number (including the country code):");
                        cellNumber = scanner.nextLine();

                        cellNumberValid = login.checkCellPhoneNumber(cellNumber);
                    } while (!cellNumberValid);


                    System.out.println(login.registerUser(username, password));
                    accounts.add(new Account(firstName, lastName, username, password, cellNumber));

                    break;
                case 3:
                    System.out.println("Come back soon!");

            }

        }


        scanner.close();
    }
}
