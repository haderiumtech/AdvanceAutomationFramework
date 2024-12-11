package tests;

import org.testng.annotations.*;

import base.BaseClass;
import pages.LoginXpaths;

public class DashboardTest extends BaseClass {

	LoginXpaths dMUKXp = new LoginXpaths();

	@Test(priority = 2)
	public void searchBarTestCase() throws InterruptedException {
		test = extent.createTest("Test case for searching functionality");
		gotoUrl(dMUKXp.dMurl);
		sendKeys(dMUKXp.searchBar, dMUKXp.searchData);
		forClick(dMUKXp.searchBtn);
		waitUntilDisplayed(dMUKXp.searchResultsPage);
		verifyText(dMUKXp.searchData, getText(dMUKXp.searchPageFirstRecord));

	}

}
