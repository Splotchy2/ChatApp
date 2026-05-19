package com.chatapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Message {
    public long messageID;
    public int numberOfMessagesSent;
    public String messageBody;
    public String recipientCell;
    public String messageHash;
    public ArrayList<Message> sentMessages;
    public String messageStatus; // this will hold a value determining if a message was sent, is a draft etc

    public Message() {

    }

    public void initializeMessage() {
        createMessageID();
        createMessageHash();
    }

    public String createMessageID() {
        Random random = new Random();
        // nextLong() gives a value across the entire long range, so we use a bound
        long randomNum = (long) Math.abs((random.nextDouble() * 9_000_000_000L) + 1_000_000_000L);

        this.messageID = randomNum;

        return "Message ID generated: " + randomNum;
    }

    public boolean checkMessageID() {
        //This method ensures that the message ID is not more than 10 characters long

        if(String.valueOf(this.messageID).length() > 10) {
            System.out.println("The Message ID cannot be greater than 10 characters.");
            return false;
        }else {
            return true;
        }
    }

    public String checkRecipientCell(String cellNumber) {
        //This method is a duplicate of the login utility method, why is this a requirement?
        boolean isValid = cellNumber.matches("^\\+27\\d{1,9}$");
        String result;

        if (isValid) {
            result = "Cell phone number successfully added.\n";
        }else {
            result = "Cell phone number incorrectly formatted or does not contain international code.\n";
        }

        return result;
    }

    // ideally utility methods like this would be private, called from a constructor or initialize method, and access to the class variables would be controlled using the getter
    // This ensures better security and good design architecture, but we are leaving it public for testing with unit tests
    public void createMessageHash() {
        // This method creates and returns the Message hash
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(this.messageID), 0, 2);
        sb.append(":");
        sb.append(numberOfMessagesSent + 1);
        sb.append(":");
        sb.append(this.messageBody, 0, this.messageBody.indexOf(" "));
        sb.append(this.messageBody.substring(this.messageBody.lastIndexOf(" ") + 1));

        this.messageHash = sb.toString();

    }

    public String sendMessage() {
        // This method should allow the user to choose if they want to send, store, or disregard the message.

        int selectedChoice;
        String result = "";
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please select an option below:\n" +
                "1. Send Message\n" +
                "2. Disregard Message\n" +
                "3. Store Message to send later");

        selectedChoice = scanner.nextInt();

        switch (selectedChoice) {
            case 1:
                messageStatus = "Sent";
                numberOfMessagesSent += 1;
                sentMessages.add(this);

                result = "Message successfully sent.";
                break;
            case 2:
                System.out.println("Press 0 to delete the message.");
                result = "Press 0 to delete the message."; // this line is to satisfy the requirement...
                selectedChoice = scanner.nextInt();

                if (selectedChoice == 0) {
                    messageStatus = "Disregarded.";
                }
                break;
            case 3:
                // store messages in JSON file
                try {
                    storeMessages();
                }catch (IOException e) {
                    System.out.println("Exception occurred: " + e.getMessage());
                }
                result = "Message successfully stored.";
                break;
        }

        return result;
    }

    public String printMessages() {
        // This method returns all the messages sent whilst the program is running.

        for (int i = 0; i <= sentMessages.size() - 1; i++) {
            System.out.println("Message ID: "        + sentMessages.get(i).messageID);
            System.out.println("Message Hash: "      + sentMessages.get(i).messageHash);
            System.out.println("Message Recipient: " + sentMessages.get(i).recipientCell);
            System.out.println("Message Body: "      + sentMessages.get(i).messageBody);
        }
        return "";
    }

    public int returnTotalMessages() {
        // This method returns the total number of messages sent

        return sentMessages.size();
    }

    public boolean storeMessages() throws IOException {
        // This method will store the messages in the class array and current draft in a JSON file

        ObjectMapper mapper = new ObjectMapper();

        // Write to file
        mapper.writeValue(new File("StoredMessages.json"), sentMessages);

        return false;
    }

    public boolean checkMessageBody(String messageBody) {

        System.out.println("Number of chars = " + messageBody.length());

        if(messageBody.length() > 250) {
            System.out.println("Please enter a message of less than 250 characters.");
            return false;
        }else {
            return true;
        }
    }

    public long getMessageID() {
        return messageID;
    }

    public int getNumberOfMessagesSent() {
        return numberOfMessagesSent;
    }

    public String getRecipientCell() {
        return recipientCell;
    }

    public void setRecipientCell(String recipientCell) {
        this.recipientCell = recipientCell;
    }

    public void setMessageBody(String messageBody) { this.messageBody = messageBody;}

    public String getMessageHash() {
        return messageHash;
    }

    public ArrayList<Message> getSentMessages() {
        return sentMessages;
    }

}
