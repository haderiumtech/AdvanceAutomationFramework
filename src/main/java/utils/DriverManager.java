package utils;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import config.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();
	private static ThreadLocal<JavascriptExecutor> jsExecutor = new ThreadLocal<>();

	// Method to initialize WebDriver based on browser type
	public static void setupDriver() {
		String browser = Configuration.get("browser").toLowerCase().trim(); // "chrome", "firefox", "edge"

		// Debugging line to check browser value
		System.out.println("Selected browser after trimming: '" + browser + "'");

		switch (browser) {
		case "chrome":
			setupChromeDriver();
			break;
		case "firefox":
			setupFirefoxDriver();
			break;
		case "edge":
			setupEdgeDriver();
			break;
		default:
			System.out.println("Selected browser after trimming: '" + browser + "'");
			System.out.println("Selected browser: " + browser);
			throw new IllegalArgumentException("Unsupported browser: " + browser);
		}
	}

	// Initialize ChromeDriver with options
	private static void setupChromeDriver() {
		try {
			// Automatically match ChromeDriver to the installed Chrome version
			WebDriverManager.chromedriver().setup();
		} catch (Exception e) {
			System.err.println("Failed to download ChromeDriver automatically: " + e.getMessage());
		}

		ChromeOptions options = new ChromeOptions();
		options.addArguments(/* "--headless", */ "--disable-notifications", "--remote-allow-origins=*", "--no-sandbox",
				"--disable-dev-shm-usage", "--incognito");

		driver.set(new ChromeDriver(options));

		postDriverSetup();
	}

	// Initialize FirefoxDriver with options
	private static void setupFirefoxDriver() {
		try {
			WebDriverManager.firefoxdriver().setup();
		} catch (Exception e) {
			System.err.println("Failed to download FirefoxDriver automatically: " + e.getMessage());
			WebDriverManager.firefoxdriver().driverVersion("specific-version").setup();
		}

		FirefoxOptions options = new FirefoxOptions();
		options.addArguments(/* "--headless", */ "--disable-notifications", "--private");

		driver.set(new FirefoxDriver(options));
		postDriverSetup();
	}

	// Initialize EdgeDriver with options
	private static void setupEdgeDriver() {
		try {
			WebDriverManager.edgedriver().setup();
		} catch (Exception e) {
			System.err.println("Failed to download EdgeDriver automatically: " + e.getMessage());
			WebDriverManager.edgedriver().driverVersion("specific-version").setup();
		}

		EdgeOptions options = new EdgeOptions();
		options.addArguments("--headless", "--disable-notifications", "--inprivate");

		driver.set(new EdgeDriver(options));
		postDriverSetup();
	}

	// Common post-driver setup to maximize window and initialize WebDriverWait and
	// JavascriptExecutor
	private static void postDriverSetup() {
		driver.get().manage().window().maximize();
		wait.set(new WebDriverWait(driver.get(), Duration.ofSeconds(10)));
		jsExecutor.set((JavascriptExecutor) driver.get());
	}

	// Method to get WebDriver instance
	public static WebDriver getDriver() {
		if (driver.get() == null) {
			setupDriver();
		}
		return driver.get();
	}

	// Method to get WebDriverWait instance
	public static WebDriverWait getWait() {
		return wait.get();
	}

	// Method to get JavascriptExecutor instance
	public static JavascriptExecutor getJsExecutor() {
		return jsExecutor.get();
	}

	// Method to quit WebDriver and clean up
	public static void quitDriver() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
			wait.remove();
			jsExecutor.remove();
		}
	}
}
