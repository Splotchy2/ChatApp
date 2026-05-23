package com.chatapp;


import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    static ArrayList<Account> accounts = new ArrayList<>();

    static void main() {
        // variable declaration
        Login login = new Login();
        Message utilityMessage = new Message();
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
        String cellNumber = "";
        String senderCellNumber;
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
                        System.out.println("Welcome to Quick Chat.\n");

                        while (selectedChoice != 3) {
                            System.out.println("Please select an option below:\n" +
                                    "1. Send Messages\n" +
                                    "2. Show recently sent messages\n" +
                                    "3. Quit");

                            selectedChoice = scanner.nextInt();
                            scanner.nextLine(); // clears the newline


                            switch(selectedChoice) {
                                case 1:
                                    utilityMessage.clearValues();
                                    System.out.println("Please enter the number of messages you wish to send:");
                                    numberOfMessagesSent = scanner.nextInt();
                                    scanner.nextLine();
                                    senderCellNumber = accounts.getFirst().cellnumber; // keep track of sender cell and reuse objects for efficiency.

                                    for(int i = 1; i <= numberOfMessagesSent; i++) {

                                        Message newMessage = new Message();

                                        //gather and validate user input, if valid, set message values
                                        do {
                                            System.out.println("Please enter the recipient cell for message " + i + ":");
                                            cellNumber = scanner.nextLine();

                                            cellNumberValid = login.checkCellPhoneNumber(cellNumber);
                                        } while (!cellNumberValid);

                                        newMessage.setRecipientCell(cellNumber);

                                        do {
                                            System.out.println("Please enter the message body for message " + i + ":");
                                            messageBody = scanner.nextLine();

                                            messageBodyValid = newMessage.checkMessageBody(messageBody);
                                        } while(!messageBodyValid);

                                        System.out.println("\nDetails Captured!\n");

                                        newMessage.setSenderCell(senderCellNumber);
                                        newMessage.setMessageBody(messageBody);
                                        utilityMessage.initializeMessage(newMessage);
                                        System.out.println(utilityMessage.sendMessage(newMessage, 0));
                                    }

                                    System.out.println("Total messages sent: " + utilityMessage.numberOfMessagesSent + "\n");

                                    break;
                                case 2:
                                    System.out.println("Coming soon!\n");
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
