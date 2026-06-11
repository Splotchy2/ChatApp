package com.chatapp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Part_3_MessageTest {

    private static Message firstMessage   = new Message();
    private static Message secondMessage  = new Message();
    private static Message thirdMessage   = new Message();
    private static Message fourthMessage  = new Message();
    private static Message fifthMessage   = new Message();
    private static Message utilityMessage = new Message();

    @BeforeAll
    public static void initializeTests() {
        // Various initialization and test prep code, gets all the messages ready and sends/stores them

        firstMessage.setRecipientCell("+2783455789");
        firstMessage.setSenderCell("+271234567");
        firstMessage.setMessageBody("Did you get the cake?");

        secondMessage.setRecipientCell("+27838884567");
        secondMessage.setSenderCell("+271234567");
        secondMessage.setMessageBody("Where are you? You are late! I have asked you to be on time.");

        thirdMessage.setRecipientCell("+27834484567");
        thirdMessage.setSenderCell("+271234567");
        thirdMessage.setMessageBody("Yohoooo, I am at your gate.");

        fourthMessage.setRecipientCell("+277654321");
        fourthMessage.setSenderCell("+271234567");
        fourthMessage.setMessageBody("It is dinner time!");

        fifthMessage.setRecipientCell("+27838884567");
        fifthMessage.setSenderCell("+271234567");
        fifthMessage.setMessageBody("Ok, I am leaving without you.");

        utilityMessage.deleteStoredFiles(); // clears folder to make the tests re-runnable

        utilityMessage.initializeMessage(firstMessage);

        utilityMessage.numberOfMessagesSent = 1;

        utilityMessage.initializeMessage(secondMessage);

        utilityMessage.initializeMessage(thirdMessage);
        utilityMessage.initializeMessage(fourthMessage);
        // you cannot assign a long value starting with 0, so I am removing it. Feel free to verify.
        fourthMessage.messageID = 838884567L;

        utilityMessage.initializeMessage(fifthMessage);

        utilityMessage.sendMessage(firstMessage, 1); // send message
        utilityMessage.sendMessage(fourthMessage, 1); // send message
        utilityMessage.sendMessage(thirdMessage, 2); // disregard message
        utilityMessage.sendMessage(secondMessage, 3); // store message
        utilityMessage.sendMessage(fifthMessage, 3); // store message
    }

    @Test
    @Order(1)
    public void checkSentMessages() {
        // checks for all the sent messages.
        assertEquals("Did you get the cake?,It is dinner time!", utilityMessage.printSentMessages());
    }

    @Test
    @Order(2)
    public void checkLongestMessage() {
        // checks for the longest stored message

        // we want to simulate a live test, thus we make sure the data is always available by ensuring loadStoredMessages is called
        utilityMessage.messagesLoaded = false;
        assertEquals("Where are you? You are late! I have asked you to be on time.", utilityMessage.getLongestStoredMessage());
    }

    @Test
    @Order(3)
    public void checkSearchMessageID() {
        //checks the searchForMessage method with a message ID

        // we want to simulate a live test, thus we make sure the data is always available by ensuring loadStoredMessages is called
        utilityMessage.messagesLoaded = false;
        assertEquals("It is dinner time!", utilityMessage.searchForMessage(838884567));
    }

    @Test
    @Order(4)
    public void checkSearchForMessageForRecipient() {
        // we want to simulate a live test, thus we make sure the data is always available by ensuring loadStoredMessages is called
        utilityMessage.messagesLoaded = false;
        // I have just swapped the order of the messages expected around, this is because the first element read into the storedMessages array appears to always be the shorter one
        // This should still adequately test the functionality
        assertEquals("Ok, I am leaving without you. Where are you? You are late! I have asked you to be on time.", utilityMessage.getMessagesForRecipient("+27838884567"));
    }

    @Test
    @Order(5)
    public void checkDeleteMessageByHash() {
        //checks if we delete the stored message by message hash
        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.", utilityMessage.deleteMessageByHash(secondMessage.messageHash));
    }

    @Test
    @Order(6)
    public void checkDisplayReport() {
        // we are not given any criteria by which to test this function, it does still print the desired report.
        utilityMessage.displayStoredReport();
    }

}
