package com.chatapp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public ArrayList<Message> sentMessages        = new ArrayList<>();
    public ArrayList<Message> disregardedMessages = new ArrayList<>();
    public ArrayList<Message> storedMessages      = new ArrayList<>();
    public ArrayList<String> messageHashes        = new ArrayList<>();
    public ArrayList<Long> messageIDs             = new ArrayList<>();
    public String messageStatus; // this will hold a value determining if a message was sent, is a draft etc
    private boolean messagesLoaded;

    public Message() {

    }

    public void initializeMessage(Message newMessage) {
        createMessageID(newMessage);
        createMessageHash(newMessage);
    }

    public void clearValues() {
        this.sentMessages.clear();
        this.numberOfMessagesSent = 0;
    }

    public String createMessageID(Message newMessage) {
        Random random = new Random();
        String result;
        // nextLong() gives a value across the entire long range, so we use a bound, this will ensure it's a random 10 digit number
        long randomNum = (long) Math.abs((random.nextDouble() * 9_000_000_000L) + 1_000_000_000L);

        newMessage.messageID = randomNum;
        this.messageIDs.add(newMessage.messageID);

        result = "Message ID generated: " + randomNum;

        return result;
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
            result = "Cell phone number successfully captured.\n";
        }else {
            result = "Cell phone number incorrectly formatted or does not contain an international code. Please correct the number and try again.\n";
        }

        return result;
    }

    // ideally utility methods like this would be private, called from a constructor or initialize method, and access to the class variables would be controlled using the getter
    // This ensures better security and good design architecture, but we are leaving it public for testing with unit tests
    public void createMessageHash(Message newMessage) {
        // This method creates and returns the Message hash
        String firstWord = newMessage.messageBody.toUpperCase().substring(0, newMessage.messageBody.indexOf(" ")).replaceAll("[^a-zA-Z0-9]", "");
        String lastWord = newMessage.messageBody.toUpperCase().substring(newMessage.messageBody.lastIndexOf(" ") + 1).replaceAll("[^a-zA-Z0-9]", "");
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(newMessage.messageID), 0, 2);
        sb.append(":");
        sb.append(this.numberOfMessagesSent);
        sb.append(":");
        sb.append(firstWord);
        sb.append(lastWord);


        newMessage.messageHash = sb.toString();
        this.messageHashes.add(newMessage.messageHash);

    }

    public String sendMessage(Message newMessage, int option) {
        // This method should allow the user to choose if they want to send, store, or disregard the message.

        int selectedChoice;
        String result = "";
        Scanner scanner = new Scanner(System.in);

        if (option != 0) {
            selectedChoice = option;
        } else {
            System.out.println("Please select an option below:\n" +
                    "1. Send Message\n" +
                    "2. Disregard Message\n" +
                    "3. Store Message to send later");

            selectedChoice = scanner.nextInt();
        }
        switch (selectedChoice) {
            case 1:
                newMessage.messageStatus = "Sent";
                this.numberOfMessagesSent += 1;
                newMessage.numberOfMessagesSent = this.numberOfMessagesSent; // Sets the number of messages on the object we pass in, java is pass-by-reference for objects
                this.sentMessages.add(newMessage);

                this.displaySentMessage(newMessage);
                result = "Message successfully sent.";
                break;
            case 2:
                System.out.println("Press 0 to delete the message.\n");
                result = "Press 0 to delete the message.\n"; // this line is to satisfy requirement...
                selectedChoice = option != 0 ? option : scanner.nextInt();

                if (selectedChoice == 0) {
                    newMessage.messageStatus = "Disregarded.\n";
                    this.disregardedMessages.add(newMessage);
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

    public String printSentMessages() {
        // This method returns all the messages sent whilst the program is running.
        String result = "";

        for (int i = 0; i <= this.sentMessages.size() - 1; i++) {
            System.out.println("====================="); // separator for readability
            System.out.println("Message ID: "        + this.sentMessages.get(i).messageID);
            System.out.println("Message Hash: "      + this.sentMessages.get(i).messageHash);
            System.out.println("Message Recipient: " + this.sentMessages.get(i).recipientCell);
            System.out.println("Message Body: "      + this.sentMessages.get(i).messageBody);

            if (!result.isEmpty()) {
                result = result + "," + this.sentMessages.get(i).getMessageBody();
            }else {
                result = this.sentMessages.get(i).getMessageBody();
            }

        }

        System.out.println("=====================\n"); // again a separator
        return result;
    }

    // this method satisfies requirement 7, not sure why we'd have this and the method above but anyway...
    public String displaySentMessage(Message newMessage) {
        // This method returns all the messages sent whilst the program is running.

        System.out.println("====================="); // separator for readability
        System.out.println("Message ID: "        + newMessage.messageID);
        System.out.println("Message Hash: "      + newMessage.messageHash);
        System.out.println("Message Recipient: " + newMessage.recipientCell);
        System.out.println("Message Body: "      + newMessage.messageBody);

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

    private void loadStoredMessages() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            File dir = new File(".");
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    if (file.getName().substring(file.getName().indexOf(".") + 1).equals("json")) {
                        Message storedMessage = mapper.readValue(file, Message.class);

                        if (!this.storedMessages.contains(storedMessage)) {
                            this.storedMessages.add(storedMessage);
                        }
                    }
                }
            }
        }catch (IOException e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }

        messagesLoaded = true;
    }

    public boolean checkMessageBody(String messageBody) {

        if(messageBody.length() > 250) {
            // requirement of class method and unit test clash, went with the unit test output
            System.out.println("Message exceeds 250 characters by " + (messageBody.length() - 250) + "; please reduce the size.");
            return false;
        }else {
            return true;
        }
    }

    public String getLongestStoredMessage() {
        String result;
        int length = 0;
        int max = 0;
        int index = 0;

        if (!messagesLoaded) loadStoredMessages();

        for (Message message : this.storedMessages) {
            length = message.getMessageBody().length();

            if (length > max) {
                max = length;

                index  = storedMessages.indexOf(message);
            }
        }

        result = storedMessages.get(index).getMessageBody();

        return result;
    }

    public void displayStoredMessageData() {
        if (!messagesLoaded) loadStoredMessages();

        for (Message message : this.storedMessages) {
            System.out.println("========");
            System.out.println("Sender: "    + message.getSenderCell());
            System.out.println("Recipient: " + message.getRecipientCell());
            System.out.println("========/n");
        }
    }

    public void displayStoredReport() {
        if (!messagesLoaded) loadStoredMessages();

        for (Message message : this.storedMessages) {
            System.out.println("============");
            System.out.println("Message Hash: "      + message.getMessageHash());
            System.out.println("Message Recipient: " + message.getRecipientCell());
            System.out.println("Message Hash: "      + message.getMessageBody());
            System.out.println("============\n");
        }
    }

    public String searchForMessage(long messageID) {
        String result = "";

        if (!messagesLoaded) loadStoredMessages();

        for (Message message : this.storedMessages) {
            if (message.getMessageID() == messageID) {
                System.out.println("\nRecipient: " + message.getRecipientCell());
                System.out.println("Message Body: " + message.getMessageBody() + "\n");

                // print out the required values above and return the value indicated by the unit test requirement
                result = message.getMessageBody();
            }
        }

        if (result.isEmpty()) result = "Unable to find message with ID: " + messageID;

        return result;
    }

    public String getMessagesForRecipient(String recipientCell) {

        if (!messagesLoaded) loadStoredMessages();

        String result = "";
        for (Message message : this.storedMessages) {
            if (message.getRecipientCell().equals(recipientCell)) {
                if (!result.isEmpty()) {
                    result = result + " " + message.getMessageBody();
                }else {
                    result = message.getMessageBody();
                }
                System.out.println("\n" + message.getMessageBody());
            }
        }

        if (result.isEmpty()) result = "No messages found for recipient: " + recipientCell;

        return result;
    }

    public String deleteMessageByHash(String messageHash) {
        int indexToRemove = -1;
        String result = "";

        if (!messagesLoaded) loadStoredMessages();

        for (Message message : this.storedMessages) {
            if (message.getMessageHash().equals(messageHash)) indexToRemove = this.storedMessages.indexOf(message);
        }

        if (indexToRemove >= 0) {
            result = "Message: \"" + this.storedMessages.get(indexToRemove).getMessageBody() + "\" successfully deleted.";
            this.storedMessages.remove(indexToRemove);
        }else {
            result = "Cannot find message with hash: " + messageHash;
        }

        return result;
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

    public String getMessageBody() {
        return this.messageBody;
    }

    public String getMessageStatus() {
        return this.messageStatus;
    }

    public void setMessageID(long messageID) {
        this.messageID = messageID;
    }
}
