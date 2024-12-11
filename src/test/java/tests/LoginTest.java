package tests;

import org.testng.annotations.Test;

import base.BaseClass;
import pages.LoginXpaths;
import utils.LoggerUtils;
import utils.RetryAnalyzer;

public class LoginTest extends BaseClass {

	LoginXpaths dMUKXp = new LoginXpaths();

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class)
	public void homePageTitleVerification() throws InterruptedException {
		// Create the test case in ExtentReports
		test = extent.createTest("Test case for verifying homepage");

		try {
			// Log the start of the test case
			LoggerUtils.logInfo("Starting the test case: homePageTitleVerification");

			// Log the current page title
			String pageTitle = getTitle();
			LoggerUtils.logInfo("Current page title: " + pageTitle);

			// Verify the title using the page's title and the expected title from the
			// xpaths
			verifyText(dMUKXp.homeTitle, pageTitle);

			// If the test passes, log the success
			LoggerUtils.logInfo("Test Passed: homepage title verification successful.");

		} catch (AssertionError e) {
			// Log any assertion failure
			LoggerUtils.logError("Test Failed: homepage title verification failed.");
			LoggerUtils.logError("Error: " + e.getMessage());
			throw e; // Rethrow the exception to ensure TestNG captures it as a failure
		} catch (Exception e) {
			// Log any other exceptions
			LoggerUtils.logError("An error occurred while verifying the homepage title.");
			LoggerUtils.logError("Error: " + e.getMessage());
			throw e; // Rethrow the exception to ensure TestNG captures it as a failure
		}

	}

//	@Test(priority = 2)
//	public void searchBarTestCase() throws InterruptedException {
//		test = extent.createTest("Test case for searching functionality");
//		gotoUrl(dMUKXp.dMurl);
//		sendKeys(dMUKXp.searchBar, dMUKXp.searchData);
//		forClick(dMUKXp.searchBtn);
//		waitUntilDisplayed(dMUKXp.searchResultsPage);
//		verifyText(dMUKXp.searchData, getText(dMUKXp.searchPageFirstRecord));
//
//	}

}
