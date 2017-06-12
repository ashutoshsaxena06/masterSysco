package com.edge.Sysco.online;

import javax.mail.MessagingException;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.util.framework.RandomAction;
import com.util.framework.SendMailSSL;

public class TestSyscoOnline extends CommonSysco {

	@BeforeClass
	public void setup() {
		System.out.println("*************Sysco************");

	}

	@AfterClass
	public void AfterClass() {
		System.out.println("************End***********");
	}

	@BeforeMethod
	public void beforeTest() throws InterruptedException {
		System.out.println("***********StartTest*********");
		RandomAction.deleteFiles("C:\\Users\\Edge\\Downloads");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Edge\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver(options);

	}

	@AfterMethod
	public void pretearDown() {
		if (driver != null) {
			driver.quit();
			System.out.println("*******EndTest*********");

		}
	}


	@Test(priority = 1)
	public void Boynton_Sysco() throws InterruptedException, MessagingException {

		System.out.println("28, Hole_Sysco");

		// check if login is success
		startSysco(driver, "032584003", "032584003", "4Flowers");

		// sendMail
		SendMailSSL.sendMailAction("Sysco - Offline GP", "Agliolio Boynton Italian Bistro & Bar");
	}

	@Test(priority = 2)
	public void Wellington_Sysco() throws InterruptedException, MessagingException {

		System.out.println("29, Lucilles_Sysco");

		// check if login is success
		startSysco(driver, "032452235", "032452235", "4Flowers");

		// sendMail
		SendMailSSL.sendMailAction("Sysco - Offline GP", "Agliolio Wellington Fresh Pasta & Wine");
	}

}
