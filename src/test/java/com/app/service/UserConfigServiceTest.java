package com.app.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.app.AppRunner;
import com.app.model.UserConfig;

@ActiveProfiles("test")
@SpringBootTest(classes = AppRunner.class)
public class UserConfigServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserConfigService userConfigService;
    
    @BeforeMethod
    public void clearDb() {
        this.userConfigService._deleteAll();
    }
    
    @Test
    public void insertionTest() {
        UserConfig userConfig = new UserConfig("i@i.com", Arrays.asList("nodejs"), 123456, Arrays.asList("c"));
        this.userConfigService.addUserConfig(userConfig);
        UserConfig userConfigDb = this.userConfigService.getUserConfig("i@i.com");
        Assert.assertNotNull(userConfigDb);
        Assert.assertEquals(userConfigDb.getScheduleTime(), userConfig.getScheduleTime());
    }
    
    @Test
    public void updateTagsTest() {
        UserConfig userConfig = new UserConfig("i@i.com", Arrays.asList("nodejs"), 123456, Arrays.asList("c"));
        this.userConfigService.addUserConfig(userConfig);
        this.userConfigService.updateTags("i@i.com", Arrays.asList("java"));
        userConfig = this.userConfigService.getUserConfig("i@i.com");
        Assert.assertNotNull(userConfig);
        System.out.println(userConfig.getTags());
        Assert.assertEqualsNoOrder(userConfig.getTags().toArray(), new String[] {"nodejs", "java"});
    }
    
    @Test
    public void updateScheduleTest() {
        UserConfig userConfig = new UserConfig("i@i.com", Arrays.asList("nodejs"), 123456, Arrays.asList("c"));
        this.userConfigService.addUserConfig(userConfig);
        this.userConfigService.updateSchedule("i@i.com", 123);
        userConfig = this.userConfigService.getUserConfig("i@i.com");
        Assert.assertNotNull(userConfig);
        Assert.assertEquals(userConfig.getScheduleTime(), 123);
    }
    
    @Test
    public void addChallengesTest() {
        UserConfig userConfig = new UserConfig("i@i.com", Arrays.asList("nodejs"), 123456, Arrays.asList("c"));
        this.userConfigService.addUserConfig(userConfig);
        this.userConfigService.addChallenges("i@i.com", Arrays.asList("h", "a"));
        userConfig = this.userConfigService.getUserConfig("i@i.com");
        Assert.assertNotNull(userConfig);
        Assert.assertEqualsNoOrder(userConfig.getNotifiedChallenges().toArray(), new String[] {"c", "h", "a"});
    }
}
