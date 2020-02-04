package com.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.app.AppRunner;
import com.app.model.User;

@ActiveProfiles("test")
@SpringBootTest(classes = AppRunner.class)
public class UserServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserService userService;
    
    @BeforeMethod
    public void deleteDb() {
        this.userService._deleteAll();
    }
    
    @Test
    public void addUserTest() {
        User user = new User("i@i.com", "pass", "1234", false, false);
        this.userService.addUser(user);
        User userDb = this.userService.getUser(user.getEmail());
        Assert.assertNotNull(userDb);
        Assert.assertFalse(userDb.isConfirmed());
    }
    
    @Test
    public void confirmUserTest() {
        User user = new User("i@i.com", "pass", "1234", false, false);
        this.userService.addUser(user);
        this.userService.confirmUser("i@i.com", "1234");
        user = this.userService.getUser(user.getEmail());
        Assert.assertTrue(user.isConfirmed());
    }
}
