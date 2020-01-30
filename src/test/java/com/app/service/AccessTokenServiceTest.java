package com.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.app.AppRunner;

@ActiveProfiles("test")
@SpringBootTest(classes = AppRunner.class)
public class AccessTokenServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private AccessTokenService accessTokenService;
	
	@Test
	public void tokenTest() throws Exception {
		String accessToken = this.accessTokenService.createToken();
		Assert.assertTrue(this.accessTokenService.verifyToken(accessToken));
	}
}
