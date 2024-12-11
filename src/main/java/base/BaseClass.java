package base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import config.Configuration;
import utils.DriverManager;
import utils.ExtentManager;

public class BaseClass {

	protected WebDriver driver;
	protected WebDriverWait wait;
	protected JavascriptExecutor js;
	protected ExtentReports extent;
	protected ExtentTest test;
	ExtentManager extentManager = new ExtentManager();

	@BeforeClass
	public void setupTestSession() {

	}

	@BeforeSuite
	public void setupSuite() {
		// Delete old screenshots
		extentManager.deleteOldScreenshots();
	}

	@BeforeClass
	public void setupTestEnvironment() {

		/*
		 * This will restore a login session, so there is no need to log in repeatedly
		 * after each test commit for now, as the login functionality has not been
		 * implemented yet.
		 */

		// driver = SessionManager.getInstance().getDriver(); // Use the singleton
		// driver instance

		// Initialize Extent Reports
		ExtentManager.setupExtentReports();
		extent = ExtentManager.getExtent();
		// Set up WebDriver and WebDriverWait from DriverManager
		DriverManager.setupDriver(); // This will initialize the driver based on the config
		driver = DriverManager.getDriver();
		wait = DriverManager.getWait();
	}

	@AfterClass
	public void teardownTestEnvironment() {
		// Close browser and report after all tests
		DriverManager.quitDriver(); // Quit the driver using DriverManager
		ExtentManager.closeExtentReports();
	}

	@BeforeMethod
	public void setupBeforeEachTest() {
		// Retrieve baseUrl from the config.properties file
		String baseUrl = Configuration.get("baseUrl");
		driver.get(baseUrl); // Navigate to the specified URL
	}

	@AfterMethod
	public void logTestResultsAndCaptureScreenshot(ITestResult result) {
		if (test == null) {
			System.err.println("ExtentTest instance is null for method: " + result.getMethod().getMethodName());
			return;
		}
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, "Test Case Failed: " + result.getName());
			test.log(Status.FAIL, "Error: " + result.getThrowable());
			// Capture screenshot and directly attach to Extent report within takeScreenshot
			// method
			ExtentManager.takeScreenshot(driver, result.getName(), test);
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, "Test Case Skipped: " + result.getName());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, "Test Case Passed: " + result.getName());
		}
		extent.flush(); // Ensure ExtentReports is flushed after each test method
	}

	@AfterSuite
	public void tearDownSuite() {
		ExtentManager.closeExtentReports(); // Ensures Extent reports are finalized
		// Clean up the test-output folder, keeping only Reports folder

	}

	@AfterClass
	public void tearDownSession() {
		extentManager.deleteFilesExceptFolder();
		// commit for the moment beacause logout functionality is not been implemented

//        if (driver != null) {
//            SessionManager.getInstance().logout(); // Logout and clean session if needed
//        }
	}

	public void setupBeforeEachTest(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		test = ExtentManager.createTest(testName); // Create a new ExtentTest instance for each test
	}

	public void closeExtentReports() {
		if (extent != null) {
			extent.flush();
		}
	}

	public void testcaseName(String testName) {
		test = extent.createTest(testName);
	}

	public void gotoUrl(String url) {
		driver.get(url);
	}

	public void pageRefresh() {
		driver.navigate().refresh();
	}

	public void navigateback() {
		driver.navigate().back();
	}

	public void navigateforward() {
		driver.navigate().forward();
	}

	public void CloseBrowser() {
		if (driver != null) {
			driver.quit();
			driver.close();
		}
	}

	public String getTitle() {
		String title = driver.getTitle();
		return title;
	}

	public String getUrl() {
		String url = driver.getCurrentUrl();
		return url;
	}

	public WebElement findElement(String locator) {
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
		return element;

	}

	public void forClick(String locator) {
		WebElement element = this.findElement(locator);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator))).click();
		} catch (Exception e) {

			System.out.println("Standard click failed, attempting JavaScript click: " + e.getMessage());
			js.executeScript("arguments[0].click();", element);
		}
	}

	public String getText(String locator) {
		String text = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator))).getText();
		return text;

	}

	public void sendKeys(String locator, String value) {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator))).sendKeys(value);
	}

	public void attributeContains(String locator, String attribute, String value) {
		WebElement element = this.findElement(locator);
		wait.until(ExpectedConditions.attributeContains(element, attribute, value));
	}

	public void verifySize(String locator, int expected) {
		int size = 0;
		try {
			size = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator))).size();
		} catch (Exception e) {

			System.out.println("Standard click failed, attempting JavaScript click: " + e.getMessage());
			size = 0;
		}
		Assert.assertEquals(expected, size);
	}

	public String getCssValue(String attribute, String locator) {
		WebElement element = this.findElement(locator);
		String value = element.getCssValue(attribute);
		return value;
	}

	public void byLinkText(String locator) {
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText(locator))).click();
	}

	public void verifyText(String expected, String actual) {
		Assert.assertEquals(expected, actual);
	}

	public void verifyCurrentUrl(String expected, String actual) {
		Assert.assertEquals(expected, actual);
	}

	public void verifyTrue(Boolean actual) {
		Assert.assertTrue(actual);
	}

	public void verifyFalse(Boolean actual) {
		Assert.assertFalse(actual);
	}

	public void waitUntilUrl(String url) {
		wait.until(ExpectedConditions.urlToBe(url));
	}

	public void waitUrlContains(String value) {
		wait.until(ExpectedConditions.urlContains(value));
	}

	public boolean waitUntilDisplayed(String locator) {
		boolean element;
		try {
			element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator))).isDisplayed();
		} catch (Exception e) {

			System.out.println("Standard click failed, attempting JavaScript click: " + e.getMessage());
			element = false;
		}
		return element;
	}

	public void clear(String locator) {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator))).clear();
	}

	// Function to select an option from a dropdown by visible text
	public void selectDropdownByVisibleText(String locator, String visibleText) {
		WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		Select dropdown = new Select(dropdownElement);
		dropdown.selectByVisibleText(visibleText);
	}

	// Function to select an option from a dropdown by value
	public void selectDropdownByValue(String locator, String value) {
		WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		Select dropdown = new Select(dropdownElement);
		dropdown.selectByValue(value);
	}

	// Function to select an option from a dropdown by index
	public void selectDropdownByIndex(String locator, int index) {
		WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		Select dropdown = new Select(dropdownElement);
		dropdown.selectByIndex(index);
	}

	public void switchToIframe(String locator) {
		WebElement iframe = driver.findElement(By.xpath(locator));
		driver.switchTo().frame(iframe);
	}

	public void switchToParentFrame() {
		driver.switchTo().defaultContent();
	}

	public boolean waitUntilClickable(String locator) {
		boolean element;
		try {
			element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator))).isEnabled();
		} catch (Exception e) {

			System.out.println("Standard click failed, attempting JavaScript click: " + e.getMessage());
			element = false;
		}
		return element;
	}

}
