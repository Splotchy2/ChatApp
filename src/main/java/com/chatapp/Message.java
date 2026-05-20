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
    public String senderCell;
    public String messageHash;
    public ArrayList<Message> sentMessages = new ArrayList<>();
    public String messageStatus; // this will hold a value determining if a message was sent, is a draft etc

    public Message() {

    }

    public void initializeMessage(Message newMessage) {

        System.out.println("Message recipient = " + newMessage.recipientCell);
        System.out.println("Message body = " + newMessage.messageBody + "\n");

        createMessageID(newMessage);
        createMessageHash(newMessage);
    }

    public void clearValues() {
        this.sentMessages.clear();
        this.numberOfMessagesSent = 0;
    }

    public String createMessageID(Message newMessage) {
        Random random = new Random();
        // nextLong() gives a value across the entire long range, so we use a bound
        long randomNum = (long) Math.abs((random.nextDouble() * 9_000_000_000L) + 1_000_000_000L);

        newMessage.messageID = randomNum;

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
    public void createMessageHash(Message newMessage) {
        // This method creates and returns the Message hash
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(newMessage.messageID), 0, 2);
        sb.append(":");
        sb.append(this.numberOfMessagesSent + 1);
        sb.append(":");
        sb.append(newMessage.messageBody.toUpperCase(), 0, newMessage.messageBody.indexOf(" "));
        sb.append(newMessage.messageBody.substring(newMessage.messageBody.lastIndexOf(" ") + 1).toUpperCase());

        newMessage.messageHash = sb.toString();

    }

    public String sendMessage(Message newMessage) {
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
                newMessage.messageStatus = "Sent";
                this.numberOfMessagesSent += 1;
                newMessage.numberOfMessagesSent = this.numberOfMessagesSent; // Sets the number of messages on the object we pass in, java is pass-by-reference for objects
                this.sentMessages.add(newMessage);

                result = "Message successfully sent.\n";
                break;
            case 2:
                System.out.println("Press 0 to delete the message.\n");
                result = "Press 0 to delete the message.\n"; // this line is to satisfy the requirement...
                selectedChoice = scanner.nextInt();

                if (selectedChoice == 0) {
                    newMessage.messageStatus = "Disregarded.\n";
                }
                break;
            case 3:
                // store messages in JSON file
                try {
                    newMessage.messageStatus = "Stored";
                    storeMessage(newMessage);
                }catch (IOException e) {
                    System.out.println("Exception occurred: " + e.getMessage());
                }
                result = "Message successfully stored.\n";
                break;
        }

        return result;
    }

    public String printMessages() {
        // This method returns all the messages sent whilst the program is running.

        for (int i = 0; i <= sentMessages.size() - 1; i++) {
            System.out.println("====================="); // separator for readability
            System.out.println("Message ID: "        + sentMessages.get(i).messageID);
            System.out.println("Message Hash: "      + sentMessages.get(i).messageHash);
            System.out.println("Message Recipient: " + sentMessages.get(i).recipientCell);
            System.out.println("Message Body: "      + sentMessages.get(i).messageBody);
        }

        System.out.println("=====================\n"); // again a separator
        return "";
    }

    public int returnTotalMessages() {
        // This method returns the total number of messages sent

        return sentMessages.size();
    }

    public boolean storeMessage(Message newMessage) throws IOException {
        // This method will store the messages in the class array and current draft in a JSON file

        ObjectMapper mapper = new ObjectMapper();

        // Write to a unique file in the project directory
        mapper.writeValue(new File("StoredMessage_" + newMessage.getMessageID() + ".json"), newMessage);

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

    public String getSenderCell() {
        return senderCell;
    }

    public void setSenderCell(String senderCell) {
        this.senderCell = senderCell;
    }
}
