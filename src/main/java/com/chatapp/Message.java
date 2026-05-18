package com.chatapp;

import java.util.ArrayList;

public class Message {
    public int messageID;
    public int numberOfMessagesSent;
    public String messageBody;
    public String recipientCell;
    public String messageHash;
    public ArrayList<Message> sentMessages;
    public String messageStatus; // this will hold a value determining if a message was sent, is a draft etc

    public Message(String messageBody, String recipientCell) {
        this.messageBody = messageBody;
        this.recipientCell = recipientCell;

        // insert call to methods to generate hash, ID, etc.
    }

    // need to figure out how to get the ID sent here..
    public boolean checkMessageID() {

        //This method ensures that the message ID is not more than 10 characters long

        return false;
    }

    public String checkRecipientCell(String cellNumber) {

        // This method ensures that the recipient cell is no more than 10 characters long and starts with a code

        return "";
    }

    // ideally utility methods like this would be private and access to the class variables would be controlled using the getter
    // This ensures better security and good design architecture, but we are leaving it public for testing with unit tests
    public String createMessageHash() {

        // This method creates and returns the Message hash

        return messageHash;
    }

    public String sendMessage() {

        // This method should allow the user to choose if they want to send, store, or disregard the message.

        return "";
    }

    public String printMessages() {

        // This method returns all the messages sent whilst the program is running.

        return "";
    }

    public int returnTotalMessages() {

        // This method returns the total number of messages sent

        return 1;
    }

    public boolean storeMessages() {

        // This method will store the messages in the class array and current draft in a JSON file

        return false;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public int getNumberOfMessagesSent() {
        return numberOfMessagesSent;
    }

    public void setNumberOfMessagesSent(int numberOfMessagesSent) {
        this.numberOfMessagesSent = numberOfMessagesSent;
    }

    public String getRecipientCell() {
        return recipientCell;
    }

    public void setRecipientCell(String recipientCell) {
        this.recipientCell = recipientCell;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public void setMessageHash(String messageHash) {
        this.messageHash = messageHash;
    }

    public ArrayList<Message> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(ArrayList<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }
}
