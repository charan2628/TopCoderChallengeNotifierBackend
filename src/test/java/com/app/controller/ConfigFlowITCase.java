package com.app.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.app.AppRunner;

@ActiveProfiles("test")
@SpringBootTest(classes = AppRunner.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ConfigFlowITCase extends AbstractTestNGSpringContextTests {
}
