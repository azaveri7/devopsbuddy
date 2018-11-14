package com.devopsbuddy;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.devopsbuddy.web.i18n.I18NService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DevopsbuddyApplicationTests {

	@Autowired
	private I18NService i18NService;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testMessageByLocaleService() throws Exception{
		String expectedResult = "Bootstrap starter template";
		String messageId = "index.main.callout";
		String actualMessage = this.i18NService.getMessage(messageId);
		Assert.assertEquals("The expected and actual results do not match" 
				,expectedResult, actualMessage);
	}

}
