package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import config.Configuration;

public class SessionManager {
	private static SessionManager instance;
	private WebDriver driver;

	// Private constructor to prevent instantiation from other classes
	private SessionManager() {
		driver = DriverManager.getDriver(); // Get WebDriver instance from DriverManager
		driver.get(Configuration.get("baseUrl"));
		performLogin(); // Login once when SessionManager is initialized
	}

	// Singleton instance retrieval method
	public static SessionManager getInstance() {
		if (instance == null) {
			instance = new SessionManager();
		}
		return instance;
	}

	// Method to get the WebDriver instance
	public WebDriver getDriver() {
		return driver;
	}

	// Perform login operation (you may need to adjust this for your login process)
	private void performLogin() {
		// Add the login logic here, e.g.:
		driver.findElement(By.id("username")).sendKeys(Configuration.get("username"));
		driver.findElement(By.id("password")).sendKeys(Configuration.get("password"));
		driver.findElement(By.id("loginButton")).click();
	}

	// Logout method if needed
	public void logout() {
		// Perform logout logic here, if necessary
		driver.findElement(By.id("logoutButton")).click();
		instance = null; // Reset the instance
	}
}
