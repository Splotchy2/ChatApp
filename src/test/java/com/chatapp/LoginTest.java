package com.chatapp;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    private Login login = new Login();

    @Test
    void checkUserName() {

        assertTrue(login.checkUserName("kyl_1"));
        assertTrue(login.checkUserName("ryan_"));
        assertFalse(login.checkUserName("Ryan1_"));
    }

    @Test
    void checkPasswordComplexity() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    void checkCellPhoneNumber() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    @Test
    void loginUserTest() {
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(new Account("ryan", "shelton", "rya_1", "Ch$$23235", "+2734500483"));

        assertTrue(login.loginUser(accounts, "rya_1", "Ch$$23235"));
        assertFalse(login.loginUser(accounts, "kyl_1", "Ch$$22222"));

    }
}