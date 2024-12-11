package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	private static ExtentReports extent;
	private static ExtentTest test;

	public static ExtentReports getExtent() {
		if (extent == null) {
			setupExtentReports();
		}
		return extent;
	}

	// Initialize ExtentReports
	public static void setupExtentReports() {
		try {
			String reportDirPath = System.getProperty("user.dir") + "/test-output/Reports";
			String reportFileName = "CustomReport.html";
			String reportFilePath = reportDirPath + "/" + reportFileName;

			File reportDir = new File(reportDirPath);
			if (!reportDir.exists()) {
				reportDir.mkdirs();
			}

			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFilePath);
			sparkReporter.config().setDocumentTitle("Automation Report");
			sparkReporter.config().setReportName("Functional Report");
			sparkReporter.config().setTheme(Theme.STANDARD);

			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);
			extent.setSystemInfo("Tester Name", "Ali Hammad");
			extent.setSystemInfo("Browser", "Chrome");

			System.out.println("Report Path: " + reportFilePath);
		} catch (Exception e) {
			System.err.println("Error setting up the Extent Reports: " + e.getMessage());
			throw new RuntimeException("Failed to set up Extent Reports", e);
		}
	}

	// Close ExtentReports
	public static void closeExtentReports() {
		if (extent != null) {
			extent.flush();
		}
	}

	public static ExtentTest createTest(String testName) {
		test = extent.createTest(testName);
		return test;
	}
	// Create a test case in the report
//	public static createTest(String testName) {
//		test = extent.createTest(testName);
//		return test;
//	}

	// Get the current ExtentTest instance
	public static ExtentTest getTest() {
		return test;
	}

	public static void takeScreenshot(WebDriver driver, String screenshotName, ExtentTest test) {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		String destinationPath = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
		File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(screenshotFile, new File(destinationPath));
			test.addScreenCaptureFromPath(destinationPath, screenshotName); // Attach to Extent report directly
		} catch (IOException e) {
			System.err.println("Failed to save screenshot: " + e.getMessage());
		}
	}

	// Capture a screenshot and attach it to the Extent report
	/*
	 * public static String takeScreenshot(WebDriver driver, String screenshotName,
	 * ExtentTest test) { String dateName = new
	 * SimpleDateFormat("yyyyMMddhhmmss").format(new Date()); String destinationPath
	 * = System.getProperty("user.dir") + "/Screenshots/" + screenshotName +
	 * dateName + ".png"; File screenshotFile = ((TakesScreenshot)
	 * driver).getScreenshotAs(OutputType.FILE);
	 * 
	 * try { FileUtils.copyFile(screenshotFile, new File(destinationPath));
	 * test.addScreenCaptureFromPath(destinationPath, screenshotName); // Attach to
	 * Extent report } catch (IOException e) {
	 * System.err.println("Failed to save screenshot: " + e.getMessage()); } return
	 * destinationPath; }
	 */

	// Delete screenshots older than a week
	public void deleteOldScreenshots() {
		String screenshotsDirPath = System.getProperty("user.dir") + "/Screenshots";
		File screenshotsDir = new File(screenshotsDirPath);

		if (screenshotsDir.exists() && screenshotsDir.isDirectory()) {
			File[] files = screenshotsDir.listFiles();
			if (files != null) {
				for (File file : files) {
					try {
						Path filePath = Paths.get(file.getAbsolutePath());
						BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
						Instant fileTime = attr.creationTime().toInstant();
						Instant oneWeekAgo = Instant.now().minus(2, ChronoUnit.DAYS);

						if (fileTime.isBefore(oneWeekAgo)) {
							Files.delete(filePath);
						}
					} catch (IOException e) {
						System.err.println("Error deleting old screenshot: " + e.getMessage());
					}
				}
			}
		}
	}

	// delete reports which are not necessary
	public void deleteFilesExceptFolder() {
		String directoryPath = System.getProperty("user.dir") + "/test-output";
		String exceptFolderPath = System.getProperty("user.dir") + "/test-output/Reports";

		System.out.println("Directory Path: " + directoryPath);
		System.out.println("Reports Folder Path: " + exceptFolderPath);

		File directory = new File(directoryPath);
		File reportsFolder = new File(exceptFolderPath);

		if (!directory.exists() || !directory.isDirectory()) {
			System.out.println("Invalid directory path: " + directoryPath);
			return;
		}

		File[] files = directory.listFiles();
		if (files == null) {
			System.out.println("No files found in directory.");
			return;
		}

		for (File file : files) {
			if (file.equals(reportsFolder)) {
				System.out.println("Skipping Reports folder: " + file.getAbsolutePath());
				continue;
			}
			deleteRecursively(file);
		}
	}

	private void deleteRecursively(File file) {
		if (file.isDirectory()) {
			File[] contents = file.listFiles();
			if (contents != null) {
				for (File f : contents) {
					deleteRecursively(f);
				}
			}
		}

		boolean deleted = file.delete();
		if (deleted) {
			System.out.println("Deleted: " + file.getAbsolutePath());
		} else {
			System.out.println("Failed to delete: " + file.getAbsolutePath() + " (may be in use or locked)");
		}
	}

}
