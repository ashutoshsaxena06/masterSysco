package com.util.framework;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileFilter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.xmlbeans.impl.xb.xsdschema.impl.IncludeDocumentImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class PageAction {

	/*
	 * public static File WaitForNewFile(Path folder, String extension, int
	 * timeout_sec) throws InterruptedException, IOException { long end_time =
	 * System.currentTimeMillis() + timeout_sec * 1000; try (WatchService
	 * watcher = FileSystems.getDefault().newWatchService()) {
	 * folder.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY); for
	 * (WatchKey key; null != (key = watcher.poll(end_time -
	 * System.currentTimeMillis(), TimeUnit.MILLISECONDS)); key.reset()) { for
	 * (WatchEvent<?> event : key.pollEvents()) { File file =
	 * folder.resolve(((WatchEvent<Path>) event).context()).toFile(); if
	 * (file.toString().toLowerCase().endsWith(extension.toLowerCase())) return
	 * file; } } } return null; }
	 */
	// enter email id where you need to send email
	
	public static WebDriver driver;
	
	public static void main(String[] args) {
		
		System.out.println(		getDate());
	}
	
	public static String getDate() {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
		Calendar calobj = Calendar.getInstance();
		System.out.println(df.format(calobj.getTime()));
		String CurrentDate = df.format(calobj.getTime());
		return CurrentDate;
	}

	public static String setdownloadDir(String path) {
		File files = new File(path + getDate());

		if (!files.exists()) {
			if (files.mkdir()) {
				System.out.println("Multiple directory are created!");
			} else {
				System.out.println("Failed to create multiple directory!.. The directory might exist");
			}
		}
		String filepath = files.getPath();
		return filepath;

	}

	public static WebDriver setDownloadFilePath() {
		// TODO Auto-generated method stub
		String downloadFilepath = setdownloadDir("");
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		WebDriver driver = new ChromeDriver(cap);
		return driver;
	}

	public static File getLatestFilefromDir(String dirPath) {

		File getLatestFilefromDir = null;
		File dir = new File(dirPath);
		FileFilter fileFilter = new WildcardFileFilter("*." + "xlsx");
		File[] files = dir.listFiles(fileFilter);

		if (files.length > 0) {
			/** The newest file comes first **/
			Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
			getLatestFilefromDir = files[0];
		}

		return getLatestFilefromDir;

		/*
		 * File dir = new File(dirPath); File[] files = dir.listFiles(); if
		 * (files == null || files.length == 0) { return null; }
		 * 
		 * File lastModifiedFile = files[0]; for (int i = 1; i < files.length;
		 * i++) { if (lastModifiedFile.lastModified() < files[i].lastModified())
		 * { lastModifiedFile = files[i]; } } return lastModifiedFile;
		 */
	}

	public static void DownloadOG(Robot robot, WebDriver driver) {
		try {
			robot = new Robot();
			Thread.sleep(2000); // Thread.sleep throws InterruptedException
			robot.keyPress(KeyEvent.VK_DOWN); // press arrow down key of
												// keyboard to navigate and
												// select Save radio button

			Thread.sleep(2000); // sleep has only been used to showcase each
								// event separately
			robot.keyPress(KeyEvent.VK_TAB);
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_TAB);
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_TAB);
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_ENTER);
			// press enter key of keyboard to perform above selected action
			System.out.println("File is downloaded");

		} catch (AWTException e) {
			ErrRemedy.ErrScreenshotCapture(driver);
			e.printStackTrace();
		} catch (InterruptedException e) {
			ErrRemedy.ErrReportingMail();
			e.printStackTrace();
		}
	}

	public static boolean isFramePresent(WebDriver driver) throws InterruptedException {
		//
		// driver.findElement(By.xpath("//html/body/table/tbody/tr[2]/td[1]/div/div[2]/table/tbody/tr[1]/td/input")).click();
		Thread.sleep(3000);
		// List to get & store frame
		List<WebElement> ele = driver.findElements(By.tagName("frame"));
		System.out.println("Number of frames in a page :" + ele.size()); // ele.size
																			// -
																			// size
																			// of
																			// frame
																			// list

		if (ele.size() == 0) {
			System.out.println("No frames on this page");
			return false; // No frames
		} else {
			System.out.println("Frames present on this page, Below are the details -");

			for (WebElement el : ele) {
				// Returns the Id of a frame
				System.out.println("Frame Id :" + el.getAttribute("id"));
				// Returns the Name of a frame.
				System.out.println("Frame name :" + el.getAttribute("name"));
			}
			return true; // frames present
		}

	}

	public static void deleteFiles(String path) {
		File dir = new File(path);
		// FileUtils.cleanDirectory(dir);
		for (File file : dir.listFiles())
			if (!file.isDirectory())
				file.delete();
		System.out.println("All files deleted from folder :-" + path);

	}
	
	public static WebDriver openBrowser(String browser, String path){
		if (browser.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.ie.driver",
					"C:\\Users\\Edge\\Downloads\\chromedriver_win32\\ie.exe");
			driver = new InternetExplorerDriver();
			}
		else if (browser.equalsIgnoreCase("ff")) {
			driver = new FirefoxDriver();
		}
		else {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			System.setProperty("webdriver.chrome.driver",
					path);
//
//			System.setProperty("webdriver.chrome.driver",
//					"C:\\Users\\Edge\\Downloads\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver(options);
		}
		return driver;		
	}

}
