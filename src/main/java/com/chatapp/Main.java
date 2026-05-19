package com.chatapp;


import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    static ArrayList<Account> accounts = new ArrayList<>();

    static void main() {
        // variable declaration
        Login login = new Login();
        Message message = new Message();
        Scanner scanner = new Scanner(System.in);
        boolean usernameValid;
        boolean userLoggedIn;
        boolean passwordValid;
        boolean cellNumberValid;
        boolean messageBodyValid;
        String firstName = "";
        String lastName = "";
        String username;
        String password;
        String cellNumber;
        String messageBody;
        int selectedChoice = 0;
        int numberOfMessagesSent = 0;

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
                        System.out.println("Welcome to Quick Chat./n");


                        while (selectedChoice != 3) {
                            System.out.println("Please select an option below:\n" +
                                    "1. Send Messages\n" +
                                    "2. Show recently sent messages\n" +
                                    "3. Quit");

                            selectedChoice = scanner.nextInt();
                            scanner.nextLine(); // clears the newline


                            switch(selectedChoice) {
                                case 1:
                                    System.out.println("Please enter the number of messages you wish to send:");
                                    numberOfMessagesSent = scanner.nextInt();

                                    for(int i = 0; i <= numberOfMessagesSent; i++) {

                                        if(i == numberOfMessagesSent) {
                                            System.out.println("You have sent the maximum number of messages you selected to send.");
                                            break;
                                        }

                                        //gather and validate user input, if valid, set message values
                                        do {
                                            System.out.println("Please enter the recipient cell:/n");
                                            cellNumber = scanner.nextLine();

                                            cellNumberValid = login.checkCellPhoneNumber(cellNumber);
                                        } while (!cellNumberValid);

                                        message.setRecipientCell(cellNumber);

                                        do {
                                            System.out.println("Please enter the recipient cell:/n");
                                            messageBody = scanner.nextLine();

                                            messageBodyValid = message.checkMessageBody(messageBody);
                                        } while(messageBodyValid);

                                        message.setMessageBody(messageBody);

                                    }
                                    //check the local message object's number of sent messages, if equal, leave


                                    break;
                                case 2:
                                    System.out.println("Coming soon");
                                    break;
                                case 3:
                                    System.out.println("Exiting!");
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
