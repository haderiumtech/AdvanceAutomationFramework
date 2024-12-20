package tests;

import org.testng.annotations.Test;

import base.BaseClass;
import pages.LoginXpaths;
import utils.LoggerUtils;
import utils.RetryAnalyzer;

public class LoginTest extends BaseClass {

	LoginXpaths demoXp = new LoginXpaths();

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class)
	public void loginTestCase() throws InterruptedException {
		
		test = extent.createTest("Test case for login functionality");
		
		try {
		super.sendKeys(demoXp.usernmae, demoXp.usernameData);
		super.sendKeys(demoXp.password, demoXp.passData);
		super.forClick(demoXp.loginButton);
		
		String pageTitle = super.getTitle();
		verifyText(demoXp.homeTitle, pageTitle);
		
		
		}catch (AssertionError e) {
			LoggerUtils.logError("Test Failed: homepage title verification failed.");
			LoggerUtils.logError("Error: " + e.getMessage());
			throw e;
			
		} catch (Exception e) {
			LoggerUtils.logError("An error occurred while verifying the homepage title.");
			LoggerUtils.logError("Error: " + e.getMessage());
			throw e; 
		}
	}

}
